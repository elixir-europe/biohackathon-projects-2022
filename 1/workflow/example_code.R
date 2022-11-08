#get required packages
library('httr')
library('jsonlite')

# Documentation:
# https://www.ebi.ac.uk/spot/zooma/docs/api

# now let's try the same thing with several items
unique_list_df <- read_csv('./1/workflow/unique_list_df.csv')

unique_list_df %>% URLencode(reserved = TRUE)
#adding links
base <- "www.ebi.ac.uk/spot/zooma/v2/api"
endpoint <- "/services/annotate"

inputTerm <- "Honey, raw" # rows from input dataframe
food <- inputTerm %>% URLencode(reserved = TRUE)

call1 <- paste(base,endpoint,"?","propertyValue","=", food, sep="")
call1

# if we want to limit the sources & ontologies:
required_sources <- c('ebisc', 'uniprot') # this could be set by the user
required_ontologies <- c('foodon')

# we need this to be formatted in a specific way:

format_sources <- paste0('[', paste(required_sources, collapse = ','), ']')
format_ontologies <- paste0('[', paste(required_ontologies, collapse = ','), ']')


url_filters <- paste0('&filter=', 
                      'required:', format_sources, ',',
                      'ontologies:', format_ontologies)

call1 <- paste0(call1, url_filters)


r <- GET(url = call1)

food_text <- content(r, 'text')

food_json <- fromJSON(food_text, flatten = TRUE)

food_df <- as.data.frame(food_json)


View(food_df)

summary_result <- food_df[ , c('semanticTags',
                               'confidence',
                               'annotatedProperty.propertyValue',
                               'derivedFrom.provenance.source.type')]

summary_result$searchTerm <- inputTerm
