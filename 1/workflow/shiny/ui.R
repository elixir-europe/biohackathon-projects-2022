
ui <- fluidPage(
  title = 'Lookup ontologies',
  # h3("Source:", tags$a("Yihui Xie", href = "https://yihui.shinyapps.io/DT-radio/")),
 


  
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
                              tabPanel("Output", verbatimTextOutput('sel')) #tableOutput("tableout")
                            
          )
  

  
)


