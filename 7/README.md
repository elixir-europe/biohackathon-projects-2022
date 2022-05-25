# Project 7: Connecting and visualizing FAIR data with TogoID and MetaStanza

## Abstract

Connecting Data: ID conversion plays an essential role in data integration. We recently launched the TogoID service (https://togoid.dbcls.jp/), which provides ID conversion among a variety of life science databases covering, but not limited to, genes, transcripts, variants, orthologs, proteins, structures, compounds, glycans, interactions, pathways, diseases, taxonomy, and literature. Key features of the TogoID include 1) supporting a wide range of databases, 2) multi-step ID conversions, 3) API for automated high-throughput conversions, 4) cloud-based hosting for the stable operation, 5) an ontology to semantically represent biological meanings of the conversion, and 6) an open-source development model for expanding supported databases. The last feature is enabled by the TogoID-config tool (https://github.com/dbcls/togoid-config) that defines a workflow to generate ID pairs from original databases, so that inclusion of a new database can be easily accomplished.

Visualizing Data: Database providers have been suffering from the development cost of visualization modules that effectively represents the database contents. MetaStanza provides a set of generic visualization modules that take data from any API returning the contents in JSON, CSV, TSV, or SPARQL query results format. A list of currently available visualizations is available in the MetaStanza showcase at http://togostanza.org/metastanza/, and many other visualizations are under development as open-source software. MetaStanza has a variety of customization options including parameters and styles. As each visualization is implemented as WebComponents, a database user can also benefit from reusing the visualization in the user’s page just by copying a few lines of the HTML code.

In this hackathon, we plan to invite ELIXIR data providers and seek use cases that meet the requirements of data integration and visualization. With TogoID, we expect new ELIXIR database identifiers to be connected with external database resources for accelerating integrated use. To support this procedure, we will improve the TogoID-config tool to provide a generic set of converters for major data formats like CSV, JSON, XML, RDF, and flat files that are often used in the life science databases. As for the MetaStanza, we will update the implementation of existing visualization components to better support ELIXIR data. We also plan to develop additional visualizations, especially for biological data such as heatmaps, time series, and geographic maps.


## Topics

Tools Platform
Interoperability Platform
Data Platform

**Project Number:** 7

### Lead(s)

Toshiaki katayama (Database Center for Life Science; DBCLS) - ktym@dbcls.jp

## Expected outcomes

Integration of several FAIR datasets into the TogoID conversion service
Visualization of FAIR datasets with MetaStanza framework

## Expected audience

FAIR data holders
Data scientists

**Number of expected hacking days**: 4 days

