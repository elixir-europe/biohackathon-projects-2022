# Package -----------------------------------------------------------------
# There is an R package for metabolights but it seems like it is obadoned. We can use the REST [API](https://www.ebi.ac.uk/metabolights/ws/api/spec.html#!/spec) directly instead.


#BiocManager::install("aberHRML/metabolighteR")

library(metabolighteR)
library(tidyverse)
library(httr)

# API key -----------------------------------------------------------------
# Some data requires an API key. So you need an account and then go to https://www.ebi.ac.uk/metabolights/myAccount

# this will then set it as a global option that is re-used by the functions to query the data.
mtbls_key('API-key-here')

# to read it back you can use:
options("MTBLS_API_KEY")$MTBLS_API_KEY

# We will also use this later:
BASE_URL <- "https://www.ebi.ac.uk:443/metabolights/ws"


# User centered functions -------------------------------------------------
# Get a list of your own private studies
get_private_studies()



# Studies -----------------------------------------------------------------
# list all public studies
get_studies()

# include a column with the tech used, e.g. NMR or MS
get_study_tech()


# Overall info on a study -------------------------------------------------
study <- 'MTBLS375'

get_study_title(study)

# Get and write text file with study description.
# This would need a lot of massaging to be useful...
ISA <- get_isa_investigation(study)
writeLines(ISA, con = paste0('ISA.txt'))


# study metadata
# just gives the data
get_study_meta(study)


# Just the free text description
get_study_desc(study)


# Get the contacts
get_study_contacts(study)


# The organism used. This is empty here...
# but is only specified in the sample metadata
get_study_org(study)


# the publications
get_study_pubs(study)



# More specific info ------------------------------------------------------
# get the protocols used. This corresponds to the tab on the website.
get_study_protocols(study)


# Show the files attached to the study
# FALSE gives the isa-tab files and exludes the raw files.
get_study_files(study, raw_data = FALSE)
get_study_files(study, raw_data = TRUE)


# Get the factors in the study
get_study_factors(study)




# Samples -----------------------------------------------------------------
# Get the samples
# this is just a list of the files. Not their metadata as on the website
get_study_samples(study)


# Download the action files
file <- get_study_files(study, raw_data = FALSE)$file[4] %>% URLencode(reserved = TRUE) # the latter is needed to escape spaces
#this fails
#download_study_file(study, get_study_files(study, raw_data = FALSE)$file[4])

# We can use REST instead with one of two methods:
## 1
path <- paste0(BASE_URL,"/studies/",study,"/download/public?file=",file)

sample_metadata <- GET(url = path) %>% 
                    content(as = "text") %>% 
                    read_delim(delim = "\t") %>% 
                    as_tibble()


## 2
path <- paste0(BASE_URL,"/studies/",study,"/sample?sample_filename=",file)

sample_metadata <- GET(url = path, httr::add_headers(user_token = getOption('MTBLS_API_KEY'))) %>% 
                    content(as = "text") %>% 
                    read_delim(delim = "\t") %>% 
                    as_tibble()


sample_metadata


# Not that the ISA-tab format has column names with identical names. That is not allowed in a tibble! But we can force it. like this.
names(sample_metadata) <- names(sample_metadata) %>% gsub("\\.\\.\\..*","",.)



# Assays ------------------------------------------------------------------
path <- paste0(BASE_URL,"/studies/",study,"assays?list_only=false")

assays <- GET(url = path) %>% 
                    content(as = "text") %>% 
                    read_delim(delim = "\t") %>% 
                    as_tibble()



# Metabolites -------------------------------------------------------------
file <- get_study_files(study, raw_data = FALSE)$file[3] %>% URLencode(reserved = TRUE)

path <- paste0(BASE_URL,"/studies/",study,"/sample?sample_filename=",file)

metabolites <- GET(url = path, httr::add_headers(user_token = getOption('MTBLS_API_KEY'))) %>% 
                    content(as = "text") %>% 
                    read_delim(delim = "\t") %>% 
                    as_tibble()


metabolites

