library(shiny)
library(DT)
library(dplyr)
library(purrr)
library(glue)
library(tidyr)

 read_table_back_callback <- " table.rows().every(function(i, tab, row) {
                                var $this = $(this.node());
                                $this.attr('id', this.data()[0]);
                                $this.addClass('shiny-input-container');
                                });
                                Shiny.unbindAll(table.table().node());
                                Shiny.bindAll(table.table().node());
                              "



 
 
lookup_term <- function(x, required_ontologies, required_sources){
  
  require('httr')
  require('jsonlite')
  require(glue)
  require(tidyr)
  
  base <- "www.ebi.ac.uk/spot/zooma/v2/api"
  endpoint <- "/services/annotate"
  
  
  
  format_sources    <- paste0('[', paste(required_sources,    collapse = ','), ']')
  format_ontologies <- paste0('[', paste(required_ontologies, collapse = ','), ']')

  
  url_filters <- glue("&filter=required:{format_sources},ontologies:{format_ontologies}") %>% as.character()
  

  
  # x <- "Honey, raw" # not important now
  # x <- input_terms[1:3,1] 
  
  
  # make table, split, unnest
  search_table <- tibble(user_query = x) %>%
                    mutate(search_query = tolower(user_query)) %>%
                    mutate(search_query = map(search_query, ~ strsplit(..1, "[&, ()/]+")[[1]])) %>%
                    mutate(search_query = map(search_query, ~ ..1[!grepl("^[0-9]",..1)])) %>% 
                    unnest(search_query) %>% 
                    mutate(type="split") %>% 
                    bind_rows(tibble(user_query = x, search_query = x, type = "full"), .) %>% 
                    distinct(user_query, search_query, .keep_all = TRUE)

  
  
search_table <- search_table %>% 
                  mutate(search_query = URLencode(search_query, reserved = TRUE)) %>% 
                  mutate(search_query = as.character(glue("{base}{endpoint}?propertyValue={search_query}"))) %>% 
                  mutate(search_query = as.character(glue("{search_query}{url_filters}")))



results <- search_table %>% 
              mutate(result = map(search_query, ~ GET(url = ..1))) %>% 
              mutate(result = map(result, ~ content(..1, 'text'))) %>%
              mutate(result = map(result, ~ fromJSON(..1, flatten = TRUE))) %>% 
              mutate(result = map(result, ~ as_tibble(..1)))


results <- results %>% 
               unnest(result) %>%
               mutate(result_id = 1:n()) %>% 
               unnest(semanticTags) %>% 
               filter(!duplicated(semanticTags)) %>% 
               nest(semanticTags = semanticTags) %>% 
               mutate(semanticTags = map(semanticTags, ~unname(unlist(..1))))
  

if(nrow(results)>0){
results <- results %>% 
             select(result_id, user_query, search_query, type, semanticTags, confidence, annotatedProperty.propertyValue,derivedFrom.provenance.source.type)
}
  
  return(results)
  
}

 
 

clean_user_input <- function(x){
  
  cleaned <- x %>% 
                  {strsplit(., "\n")[[1]]} %>% # split by new line
                  tibble(Query = .) %>% 
                  mutate(Query_cleaned = trimws(Query)) %>%
                  filter(!(row_number() == 1   & Query_cleaned == "")) %>% # remove first row if empty
                  filter(!(row_number() == n() & Query_cleaned == "")) %>% # remove last row if empty
                  mutate(Query_cleaned = iconv(Query_cleaned, to='ASCII//TRANSLIT'))
    
  return(cleaned)
  
}



get_zooma_sources <- function(){
  require('jsonlite')
  require('httr')
  require(dplyr)
  
  base <- "www.ebi.ac.uk/spot/zooma/v2/api"
  endpoint <- "/services/annotate"
  
  zooma_sources <- GET(url = paste0(base, "/sources")) %>% 
                    content('text') %>% 
                    fromJSON(flatten = TRUE) %>% 
                    as.data.frame() %>% 
                    as_tibble
                    
   zooma_sources

}


  
zooma_sources <- get_zooma_sources()  
  

  
ontology_link_to_name <- function(x)  gsub(".*/(.*)/.*$","\\1",x)



ontology_to_link_html <- function(link){
 
  name <- ontology_link_to_name(link)
       
  glue('<a href="{link}" target="_blank" rel="noopener noreferrer">{name}</a>')
}



ontologies_to_links_html <- function(links){
 
  map_chr(links, ontology_to_link_html) %>% paste0(collapse = ", ")

}




onto_names <- zooma_sources %>% filter(type == "ONTOLOGY") %>% pull(name)
onto_titles <- zooma_sources %>% filter(type == "ONTOLOGY") %>% pull(title)
onto_choices <- onto_names %>% as.list() %>% setNames(onto_titles)

db_names <- zooma_sources %>% filter(type == "DATABASE") %>% pull(name)
db_choices <- db_names %>% as.list() %>% setNames(db_names)

