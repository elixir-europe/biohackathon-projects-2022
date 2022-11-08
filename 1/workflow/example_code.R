library('httr')
library('jsonlite')

base <- "www.ebi.ac.uk/spot/zooma/v2/api"
endpoint <- "/services/annotate"
search_term <- '?propertyValue'
ontology_filter <- 'propertyType'

food <- "honey raw" # 'Honey, raw'


call1 <- paste(base,endpoint,"?","propertyValue","=", food, sep="")
call1

r <- GET(url = call1)

food_text <- content(r, 'text')

food_json <- fromJSON(food_text, flatten = TRUE)

food_df <- as.data.frame(food_json)


View(food_df)

summary_result <- food_df[ , c('semanticTags',
                               'confidence',
                               'annotatedProperty.propertyValue')]

summary_result$searchTerm <- 'honey, raw'

# for each row we need to lowercase and then convert the ', ' into a '+' sign
# i.e. from "Honey, raw" to "honey+raw"
## OR use the 'URLencode' function to directly get the right format
# we can send that to the ZOOMA API
# then we get a results dataframe 
# we would need to include the search term there before merging all the results
