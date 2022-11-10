library('httr')
library('jsonlite')
library('tidyverse')
library('glue')

# API links:
base <- "http://www.ebi.ac.uk/ols/api/"
endpoint <- "search"

# let's use the same example:
unique_list_df <- read_csv('./1/workflow/unique_list_df.csv') %>%
                    rename(inputTerm = List)

# URL-encode and build the queries for exact matching:
## we can add: "&groupField=TRUE" as a parameter to get 
## exactly the same results as with the webpage:
search_table <- unique_list_df %>%
  mutate(query = URLencode(inputTerm, reserved = TRUE)) %>%
  mutate(query = as.character(glue("{base}{endpoint}?q={query}&exact=TRUE"))) %>% 
  mutate(query_group = as.character(glue("{query}&groupField=TRUE")))

# now get all the results:
# TODO: limit ontologies
results <- search_table %>% 
  mutate(result = map(query, ~ GET(url = ..1))) %>% 
  mutate(result = map(result, ~ content(..1, 'text'))) %>%
  mutate(result = map(result, ~ fromJSON(..1, flatten = TRUE))) %>% 
  mutate(result = map(result, ~ as_tibble(..1[["response"]][["docs"]])))


final_results <- results %>% 
                  unnest(result) %>%
                  select(inputTerm, iri, obo_id, label,
                         ontology_name, description)
View(final_results)

## another possibility - grouped results:
results_group <- search_table %>% 
  mutate(result = map(query_group, ~ GET(url = ..1))) %>% 
  mutate(result = map(result, ~ content(..1, 'text'))) %>%
  mutate(result = map(result, ~ fromJSON(..1, flatten = TRUE))) %>% 
  mutate(result = map(result, ~ as_tibble(..1[["response"]][["docs"]])))


final_results_group <- results_group %>% 
  unnest(result) %>%
  select(inputTerm, iri, obo_id, label,
         ontology_name, description)

View(final_results_group)

# what's the difference?:
setdiff(final_results, final_results_group)

