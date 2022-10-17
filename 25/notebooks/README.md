To query EDAM or bio.tools from a GraphDb SPARQL end point using these template you need to setup

1) **Install requirements**
    ```
    pip install requirements.txt 
    ```

2) **Jupyter-lab**

    It was installed with the requirements. You can launch it using ```jupyter-lab```  command from your terminal and opening the *.ipynb in this directory.
    You can also use VisualSudioCode to visualise and run the *.ipynb notebook files.

3) **GraphDB**

    Here is the [link](https://graphdb.ontotext.com/documentation/10.0/quick-start-guide.html) to the quick start.
    First you will need to create an account in order to download the installer file at [this](https://www.ontotext.com/products/graphdb/download/) adress.

    Once installed you need to upload the needed data. In the "Import" tab you can load using a local file or a URL e.g. "https://raw.githubusercontent.com/edamontology/edamontology/main/EDAM_dev.owl"

Now you can test querying you dataset using the example notebook "EDAM_query_graphdb.ipynb"
