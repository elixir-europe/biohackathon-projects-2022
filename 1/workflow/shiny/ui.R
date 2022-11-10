
ui <- fluidPage(
  title = 'Lookup ontologies',
 
 
  tabsetPanel(type = "tabs",
                              tabPanel("Input",
                                                
                                                textAreaInput(
                                                              inputId = "userinput",
                                                              label = "Copy paste the terms to search",
                                                              width = "80%",
                                                              height = "20em"
                                                             ),


                                                br(),
                                                actionButton("button", "Lookup"),

                                                br(),br(),

                                                DT::dataTableOutput('foo')
                                
                                               
                                       
                                       ),
                              tabPanel("Output", DT::dataTableOutput('sel'))
                            
          )
  

  
)


