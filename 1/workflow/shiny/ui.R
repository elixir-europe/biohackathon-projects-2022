
ui <- fluidPage(
  
  tags$style(type='text/css',
             "
             .selectize-input {
              max-height: 10em;
              overflow-y: auto;
}
             "
             ), 
  
  
  
  title = 'Lookup ontologies',
 
 
  tabsetPanel(type = "tabs",
                              tabPanel("Input",
                                       
                                                fluidRow(
                                                          column(8,
                                                               
                                                                textAreaInput(
                                                                    inputId = "userinput",
                                                                    label = "Copy paste the terms to search",
                                                                    width = "100%",
                                                                    height = "20em"
                                                                    )
                                                               
                                                               ),
                                                          column(4,
                                                               
                                                                selectInput("select_ontologies","Selected ontologies",c("NONE"),selected="NONE", multiple = TRUE),
                                                                selectInput("select_databases","Selected databases",c("NONE"),selected="NONE", multiple = TRUE)
                                                               
                                                               )
                                                  
                                                      ),                                       
                                       
                                       
                                       
                                       


                                                br(),
                                                actionButton("button", "Lookup"),

                                                br(),br(),
                                       
                                                

                                                DT::dataTableOutput('foo')
                                
                                               
                                       
                                       ),
                              tabPanel("Output", 
                                       br(),
                                       downloadButton('downloadData', 'Download'),
                                       DT::dataTableOutput('sel'),
                                       br()
                                       )
                            
          )
  

  
)


