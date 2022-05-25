# Project 24: Quantitative bias assessment in ELIXIR - EuropePMC biomedical publications resources

## Abstract

Last year, during the Biohackathon 2021, under the project "FAIRX: Quantitative bias assessment in ELIXIR biomedical data resources", we assessed the partition of sex within two databases, EGA and dbGaP. The evolution of the project can be found in https://github.com/elixir-europe/biohackathon-projects-2021/tree/main/projects/35) with an article to be submitted for publication. Rather than analysing the available datasets, this time we concentrate on the scientific literature to uncover sex imbalance in the published research. We will leverage the EuroPMC repository (https://europepmc.org/) and their available API to access and mine the content of free-text articles published there.
The end result would be an automated text parser to extract the mention of the sex in the reported information of preclinical and clinical studies if any and thus provide insights on the current state of sex imbalance in the research publications.
The project will combine several strategies to ensure access to the data. First, it will concentrate on specific parts of the articles where data should be located (Material and methods, as well as additional files). We will prioritise article types of interest, such as publications linked to recent clinical trials and preclinical studies. We will also review the status of the policies and guidelines on sex disclosure in scientific publications adopted by the different journals (such as Key Resources Tables, STAR methods and SAGER guidelines). Recommendations for a fairer reporting of sex in scientific publications will be drawn from the analysis of the results, which will be presented in a form that is suitable for future publication.
We would like to invite Aravind Venkatesan - Senior Data Scientist - EMBL-EBI. He is working on the data science side for the EuropePMC.

## Topics

Data Platform
Federated Human Data
Interoperability Platform
Machine learning

**Project Number:** 24

### Lead(s)

Olivier Philippe - olivier.philippe@bsc.es
Blanca Calvo - bcalvo.bsc@gmail.com

## Expected outcomes

Four outcomes for the weeks
* Parsing the portion of the text provided by Europe PMC and automatically detecting sex reporting of samples and/or humans or animal models involved in the studies
* Aggregating the metrics for the subset of journals used during the Biohackathon
* Providing a report giving the state of the research
* Extending the text analysis to include other variables such as the publisher, the type of publication, etc.

Three long-term expected outcomes
* Writing an article in line with the SAGER and KPR principles
* Extending the sample detection to a larger subset
* Developing a pipeline to be run daily/weekly on the EuroPMC to provide this information as a service (under their API service)

## Expected audience

Researchers in social sciences with interests in biomedicine and technology
Data scientists with strong analytical and statistical knowledge
Biostatisticians with interests in bias and data mining
Researchers and practitioners in academic or industrial fields devoted to social equity

**Number of expected hacking days**: 4

