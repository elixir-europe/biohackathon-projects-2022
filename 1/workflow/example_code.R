#get required packages
library('httr')
library('jsonlite')

# Documentation:
# https://www.ebi.ac.uk/spot/zooma/docs/api

#adding links
base <- "www.ebi.ac.uk/spot/zooma/v2/api"
endpoint <- "/services/annotate"

inputTerm <- "Honey, raw" # rows from input dataframe
food <- inputTerm %>% URLencode(reserved = TRUE)

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

summary_result$searchTerm <- inputTerm

# for each row we need to lowercase and then convert the ', ' into a '+' sign
# i.e. from "Honey, raw" to "honey+raw"
## OR use the 'URLencode' function to directly get the right format
# we can send that to the ZOOMA API
# then we get a results dataframe 
# we would need to include the search term there before merging all the results
