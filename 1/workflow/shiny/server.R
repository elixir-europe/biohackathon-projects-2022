
server <- function(input, output, session) {



# we need this because a new search would fail when same id was used
radio_id_prefix <- eventReactive(input$button,{
                                                c(65:90,97:122) %>% sample(5, replace=TRUE) %>% as.raw() %>% rawToChar() %>% tolower()
                                              }
                                 )
  
  

  data <- eventReactive(input$button,
                        {
                          df <- input$userinput %>% clean_user_input() %>% distinct()

                          df <- df %>% mutate(returns = map(Query_cleaned, lookup_term))
                          
                          df
                        }
  )

                        
                        

# When the button is pushed get ontologies and create a table with select boxes.
# Boxes are created in HTML and rendered as such by renderDataTable
  data_selector_table <- eventReactive(input$button,
                        {

                          df <- data()
                          if(nrow(unnest(df, returns))==0) showNotification("No results returned", type = "error")

                          

                          checked <- ""
                            # i <- 1
                            for(i in 1:nrow(df)){
                              df$returns[[i]]$radio <- NA
                              df$minitable[i] <- NA

                              choiceNames <- character()
                              choices <- list()

                              for(j in 1:nrow(df$returns[[i]])){

                                choices[[j]] <- df$returns[[i]]$annotatedProperty.propertyValue[[j]]
                                choiceNames[j] <- df$returns[[i]]$semanticTags[[j]] %>% { paste0('<div style="display:flex;flex-flow:row;width:800px"><div style="width:50%">', choices[[j]], '</div><div style="width:50%"><a href="', ., gsub("http://.*/.*/(.*)_.*", '" target="_blank" rel="noopener noreferrer">\\1</a></div></div>', .)) }

                              }
                              selected <- df$returns[[i]]$annotatedProperty.propertyValue[[1]]

                              df$minitable[i] <- radioButtons(glue("{radio_id_prefix()}_radio_{i}"), "",  choiceValues=choices, selected=selected,choiceNames = lapply(choiceNames,HTML),width = 800) %>% as.character()


                            }



                         df %>% select(Query, Matches = minitable)

  })



  output$foo <- DT::renderDataTable(

    data_selector_table(),
    escape = FALSE,
    selection = 'none',
    server = FALSE,
    options = list(dom = 't',
                   paging = FALSE,
                   ordering = FALSE
                   ),
    callback = JS(read_table_back_callback)

  )


  # runs through the inputs and creates a string with the selected choices
  selections <- reactive({
    selected <- sapply(1:nrow(data_selector_table()), function(i) input[[glue("{radio_id_prefix()}_radio_{i}")]]) %>% unlist
    selected
  })


  # write to the text box
  output$sel <- DT::renderDataTable({
    str(selections())
    
    selections_tab <- tibble(selection = selections()) %>% mutate(id = 1:n())
    
    data() %>% 
      mutate(id = 1:n()) %>% 
      unnest(returns) %>% 
      left_join(selections_tab,., by = c(selection = "annotatedProperty.propertyValue", "id")) %>% 
      mutate(ontoname = {gsub("http://.*/.*/(.*)_.*","\\1", semanticTags)}) %>% 
      mutate( Ontology = glue('<a href="{semanticTags}" target="_blank" rel="noopener noreferrer">{ontoname}</a>')) %>% 
      select(Query_no = id, `Ontology term` = selection, Ontology)
    
    
  }, escape = FALSE, rownames = FALSE)


}
