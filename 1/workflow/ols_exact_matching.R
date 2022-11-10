library('httr')
library('jsonlite')
library('tidyverse')
library('glue')

# API links:
base <- "http://www.ebi.ac.uk/ols/api/"
endpoint <- "search"

# user input to restrict the search to some ontologies:
limit_ontologies <- c("foodon", "fobi") # for example

# the complete list of ontologies can be accessed at:
r_ontol <- GET(paste0(base, 'ontologies'))
r_ontol_text <- content(r_ontol, 'text')
r_ontol_json <- fromJSON(r_ontol_text, flatten = TRUE)
ontologies <- r_ontol_json[['_embedded']][['ontologies']]


# let's use the same example:
unique_list_df <- read_csv('./1/workflow/unique_list_df.csv') %>%
                    rename(inputTerm = List)

# URL-encode and build the queries for exact matching:
## NOTE: we can add "&groupField=TRUE" as a parameter to get 
## exactly the same results as with the webpage
search_table <- unique_list_df %>%
  mutate(query = URLencode(inputTerm, reserved = TRUE)) %>%
  mutate(query = as.character(glue("{base}{endpoint}?q={query}&exact=TRUE")))# %>% 
  # mutate(query = as.character(glue("{query}&groupField=TRUE")))

# add parameter to limit the results to some ontologies:
if (length(limit_ontologies) > 0) {
  search_table <- search_table %>%
    mutate(query = as.character(glue("{query}&ontology={paste(limit_ontologies, collapse = ',')}")))
  }

# now get all the results:
results <- search_table %>% 
  mutate(result = map(query, ~ GET(url = ..1))) %>% 
  mutate(result = map(result, ~ content(..1, 'text'))) %>%
  mutate(result = map(result, ~ fromJSON(..1, flatten = TRUE))) %>% 
  mutate(result = map(result, ~ as_tibble(..1[["response"]][["docs"]])))


final_results <- results %>% 
                  unnest(result) %>%
                  select(inputTerm, iri, obo_id, label, 
                         ontology_name)
View(final_results)
