#get required packages
library('httr')
library('jsonlite')
library('tidyverse')

# ZOOMA API documentation:
# https://www.ebi.ac.uk/spot/zooma/docs/api

#adding links
base <- "www.ebi.ac.uk/spot/zooma/v2/api"
endpoint <- "/services/annotate"

# load the (example) list of unique items:
unique_list_df <- read_csv('./1/workflow/unique_list_df.csv')

# we need to URL-encode the terms:
unique_list_df$input <- apply(unique_list_df, 1, 
                              function(x) URLencode(x, reserved = TRUE))

# build all the calls:
unique_list_df <- unique_list_df %>%
  mutate(call = paste0(base, endpoint, "?propertyValue=", input))

# if we want to limit the sources & ontologies:
# (this could be set by the user)
required_sources <- c() 
required_ontologies <- c('foodon')

# to get the complete list of ontologies and databases:
r <- GET(url = paste0(base, "/sources"))
r_text <- content(r, 'text')
r_json <- fromJSON(r_text, flatten = TRUE)
sources_df <- as.data.frame(r_json)

# we need this to be formatted in a specific way:
if (length(required_sources) > 0){
  format_sources <- paste0('[', paste(required_sources, collapse = ','), ']')
  } else {
  format_sources <- '[none]'}

if (length(required_ontologies) > 0){
  format_ontologies <- paste0('[', paste(required_ontologies, collapse = ','), ']')
  } else {
  format_ontologies <- '[none]'}

# now we can build our filtered calls:
unique_list_df <- unique_list_df %>%
  mutate(call_filters = paste0(call, 
                              '&filter=', 
                              'required:', format_sources, ',',
                              'ontologies:', format_ontologies))

# prepare an empty dataframe for results:
results_df <- data.frame(inputTerm=character(),
                         semanticTags=character(),
                         confidence=character(),
                         annotatedProperty.propertyValue=character(),
                         derivedFrom.provenance.source.type=character(),
                         stringsAsFactors=FALSE)

# loop over all the terms and query the ZOOMA API:
# this is a bit slow - can we improve it?
for (i in 1:dim(unique_list_df)[1]){
  # use the call with filters:
  res <- GET(url = unique_list_df$call_filters[i])
  
  res_text <- content(res, 'text')
  res_json <- fromJSON(res_text, flatten = TRUE)
  res_df <- as.data.frame(res_json)
  
  # we need to avoid empty results somehow:
  if (dim(res_df)[1] > 0){
  summary_res <- res_df[ , c('semanticTags',
                                 'confidence',
                                 'annotatedProperty.propertyValue',
                                 'derivedFrom.provenance.source.type')]
  
  # add the original search term:
  summary_res$searchTerm <- unique_list_df$List[i]
  
  # done! merge it with the previous results:
  results_df <- rbind(results_df, summary_res)}
}

# let's relocate the search term column to the 1st place
# to make the data a bit more understandable for us:
results_df <- results_df %>% relocate(searchTerm)

# done! we can take a look...
View(results_df)