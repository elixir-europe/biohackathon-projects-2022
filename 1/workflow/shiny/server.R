
server <- function(input, output, session) {
  
  observe(print(str(input$userinput))) 
   
 # observe(print(str(input_data_cleaned()))) 
  
 # observe(print(str(data())))
  
  
  
  
# When the button is pushed get ontologies and create a table with select boxes.
# Boxes are created in HTML and rendered as such by renderDataTable
  data <- eventReactive(input$button,
                        {
                          df <- input$userinput %>% clean_user_input() %>% distinct()
                          
                          df <- df %>% mutate(returns = map(Query_cleaned, lookup_term))
                          
                          
                          if(nrow(unnest(df, returns))==0) showNotification("No results returned", type = "error")
                          
                          
                          for (i in 1:nrow(df)) {
                            
                            if(df$Query_cleaned[i]!=""){
                            
                              df$`Ontology term`[i] <- as.character(selectInput(paste0("sel", i),
                                                                                   "",
                                                                                   choices = df$returns[[i]]$annotatedProperty.propertyValue,
                                                                                   width = "20em")
                                                                     )
                            }else{
                              df$`Ontology term`[i] <- ""
                            }
                            
                            
                        }
                          
                         df %>% select(Query, `Ontology term`)
                         
  })
  
  
  
  output$foo = DT::renderDataTable(
    data(), 
    escape = FALSE, 
    selection = 'none', 
    server = FALSE,
    options = list(dom = 't', 
                   paging = FALSE, 
                   ordering = FALSE#,
                   #language = list(emptyTable = 'My Custom No Data Message')
                   ),
    callback = JS(read_table_back_callback)
  )
  
  
  # runs through the inputs and creates a string with the selected choices
  selections <- reactive({
  selected <- sapply(1:nrow(data()), function(i) input[[paste0("sel", i)]]) %>% unlist
  selected
  })
  
  
  # write to the text box
  output$sel = renderPrint({
    str(selections())
  })
  
  
}
