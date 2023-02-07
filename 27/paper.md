---
title: 'Streamlining data brokering from Research Data Management platforms to ELIXIR Repositories'
title_short: 'Streamlining data brokering from RDM platforms to ELIXIR Repositories'
tags:
  - brokering
  - ISA-JSON
  - EBI
  - ENA
  - Biosamples
authors:
  - name: Flora D’Anna
    orcid: 0000-0003-4665-6673
    affiliation: 1
  - name: Zahra Waheed
    orcid: 0000-0002-7224-5280
    affiliation: 2
  - name: Anliat Mohamed
    orcid: 0000-0002-1105-8262
    affiliation: 3
  - name: Dipayan Gupta
    orcid: 0000-0001-8753-7369
    affiliation: 4
  - name: Pedram A Keyvani
    affiliation: 5
  - name: Rafael Andrade Buono
    orcid: 0000-0002-6675-3836
    affiliation: 1
  - name: Sara El-Gebali
    orcid: 0000-0003-1378-5495
    affiliation: 7 
  - name: Sveinung Gundersen
    orcid: 0000-0001-9888-7954
    affiliation: 6
  - name: Vahid Kiani
    affiliation: 1
affiliations:
  - name: VIB-UGent Center for Plant Systems Biology, Ghent, Belgium
    index: 1
  - name: European Nucleotide Archive, European Bioinformatics Institute, Wellcome Genome Campus, Hinxton, Cambridgeshire, CB10 1SD, United Kingdom
    index: 2
  - name: CNRS, Institut Français de Bioinformatique, IFB-core, UAR 3601, Evry, France
    index: 3
  - name: Biosamples, European Bioinformatics Institute, Wellcome Genome Campus, Hinxton, Cambridgeshire, CB10 1SD, United Kingdom
    index: 4
  - name: University of Bergen (Computational Biology Unit), Datablokk 5th Floor, Thormøhlensgate 55 N-5008 Bergen, Bergen, Norway
    index: 5
  - name: Centre for Bioinformatics, University of Oslo, Boks 1072 Blindern, NO-0316 OSLO, Norway
    index: 6
  - name: Department of Immunology, Genetics and Pathology, SciLifeLab Data Centre, SciLifeLab, Tomtebodavägen 23, 171 65 Solna, Sweden
    index: 7
date: 06 February 2023
cito-bibliography: final_project_27_report.bib
event: BioHackathon Europe 2022
biohackathon_name: "BioHackathon Europe 2022"
biohackathon_url:   "https://biohackathon-europe.org/"
biohackathon_location: "Paris, France, 2022"
group: Project 27: Streamlining data brokering from RDM platforms to ELIXIR Repositories
# URL to project git repo --- should contain the actual paper.md:
git_url: https://github.com/elixir-europe/biohackathon-projects-2022/tree/main/27
# This is the short authors description that is used at the
# bottom of the generated paper (typically the first two authors):
authors_short: Flora D’Anna & Zahra Waheed \emph{et al.}
---

# Abstract

Mobilizing data from data producers to data deposition databases is an integral service that research data management (RDM) platforms could offer. However, brokering the heterogeneous mixture of scientific data requires systems that are compatible with the diverse (meta)data models of the different RDM platforms, and diverse submission routes of different domain/techniques-specific repositories.

Existing tools for brokering of research (meta)data in life sciences often are technique or domain specific and aimed at only one specific deposition database at a time, which does not reflect the way scientific projects are often conducted. As a result, infrastructure providers or research laboratories have to invest resources in manual curation and mapping of (meta)data in order to help researchers deposit their outputs into specialized repositories.

This BioHackathon 2022 project specifically focused on designing and implementing a prototype of a data brokering system from ISA-JSON to multiple ELIXIR Deposition Databases, starting with the European Nucleotide Archive (ENA). Specifically, we started from a ISA-JSON file exported from the DataHub, a metadata management platform (an instance of the FAIRDOM-SEEK software) which uses the well-established ISA (Investigation Study Assay) framework to describe multi-omics metadata and link to the location of data files.

During this project we performed a high-level mapping of the ISA-JSON schema to the ENA XML files necessary for metadata submission. We also described a flexible, sustainable and domain/technique-agnostic brokering strategy from ISA-JSON to multiple ELIXIR deposition databases and developed a prototype of an EBI multi-repositories converter tool.

# Introduction

The Investigation, Study, Assay (ISA) Framework was established in 2008 to describe and capture experimental metadata from multi-omics experiments (https://pubmed.ncbi.nlm.nih.gov/18447634/). It is categorized into a hierarchical structure composed of: Investigation (project context), Study (a unit of research) and Assay (analytical measurements) elements, and was specifically designed to reflect the way scientific projects are conducted - where multiple hypotheses and experimental conditions are often tested on the same observation unit or cohort by applying multiple techniques (e.g., nucleic acid sequencing, proteomic, metabolomics etc). 

ISA-Tab was the first file serialization that was based on the ISA Framework, but the ISA-JSON serialization was then developed for improved machine readability. Recently, ISA API (a python library) has been developed providing programmatic metadata-handling and increased interoperability with other life science data formats (https://academic.oup.com/gigascience/article/10/9/giab060/6371038). The ISA-JSON format is commonly used by the plant community (such as NFDI4Plants (a national plant data platform in Germany) and MIAPPE Standards group (Minimum Information about Plant Phenotyping Experiments).

DataHub is a research data management platform, which utilizes the FAIRDOM-SEEK software,  and is currently under development by ELIXIR Belgium. DataHub aims to act as a brokering platform for metadata, having integrated metadata templates of several EBI repositories to facilitate FAIR data by design. Metadata about a scientific project is exported in ISA-JSON, however, this file format is not well supported for submission to EBI repositories. Although conversion tools to convert between ISA-JSON and repository-compliant metadata submission file formats exist (https://isa-tools.org/isa-api/content/exportdata.html#), they are specific to one single repository at a time. This approach would require a user to perform multiple conversions starting from the same ISA-JSON containing heterogeneous (or multi-omics) experimental metadata, each time. Moreover, these tools are often maintained by communities or infrastructure providers and they are not always in sync with the requirements of EBI repositories, adding further complexity to potential data brokering systems.

The aim of this Biohackathon project is to unblock ISA-JSON export to EBI repositories, primarily focussing on ENA and Biosamples submission. We aim to build a new converter tool which is agnostic to the target repository - i.e, able to handle multi-repository submission, in order to broker metadata to several target databases from a single ISA-JSON. As we first focus on BioSamples and ENA, the converter will map attributes of the ISA JSON to the ENA Metadata model, and create XML files that are suitable for a successful ENA and BioSamples submission.

# Results

## High level mapping between ISA-JSON model and BioSamples-ENA model

In order to develop a brokering tool that can handle multi-repository submission, a high level mapping between the ISA-JSON structure and the BioSamples - ENA XML objects was needed. Although the ISA framework can be used for multi-omics experiments, we first focused on a simple use case with only one technology type, that is “nucleotide sequencing” to ease the design. We also limited our work on submission of raw data to ENA, not taking into account the genome assembly as a data type.

First, we verified that all the metadata about ENA XML objects could be allocated and found in one of the ISA-JSON schemas. Then, we mapped ISA-JSON schema paths to specific ENA XML objects. The final mapping is as follows: 
the paths *studies/assays/dataFiles* (from data_schema.json) and the paths *studies/protocols* (from protocol_schema.json) for protocol type “nucleic acid sequencing” have been mapped to the RUN XML object
the paths *studies/assays/materials/otherMaterials* (from material_schema.json) and the paths *studies/protocols* (protocol_schema.json) for protocol type “library construction” have been mapped to the EXPERIMENT XML object
the paths *studies/materials/sources* (from source_schema.json) and the paths *studies/materials/samples* (from sample_schema.json) have been mapped to the SAMPLES XML object. The paths *studies/protocols* (from protocol_schema.json) for protocol type “sample collection” have been mapped to the SAMPLES XML object, and they have been included in the prototype.
The paths *studies* (from study_schema.json) have been mapped to the STUDY XML object.
The paths *investigation* (from investigation_schema.json) have been mapped to the PROJECT XML object.
The details of the mapping can be found in tabular format in the [mapping.tsv](https://github.com/elixir-europe/biohackathon-projects-2022/blob/main/27/BH2022_ISAJSON_ENA_mapping.tsv) file. Additional effort is required to define the fine grained mapping of specific attributes (see grayed-out cells in the mapping file) and to finalize the naming of hard coded variables, such as “library name” (see cells in bold in the mapping file).

## ISA-JSON containing checklists and attributes required by EBI/ELIXIR repositories

ELIXIR Belgium focuses on developing a web-based metadata management platform (DataHub) to allow experimentalists and researchers to collect (meta)data that are FAIR-by-design. The platform will provide the users with templates defined based on requirements from ELIXIR Deposition Databases. Currently, the requirements for each data repository are manually tracked, interpreted, and transformed into templates. To guarantee better sustainability and compliance with changes in requirements, we aim to implement programmatic and automated solutions for the conversion of requirements into templates in the future. A first step towards this automated conversion is to have programmatic access to EBI checklists and requirements. Possible strategies to expose repositories’ requirements programmatically are the use of:
* GitHub repositories used by EBI databases
* API for checklists provided and used by EBI databases
* EBI Pub/Sub service
Repositories’ templates in DataHub, and other metadata management platforms, will have to be embedded in a way that follows the high level mapping between ISA-JSON model and EBI repositories model. It is essential that the mapping between ISA-JSON and EBI repositories’ models is done in collaboration with the repositories to avoid making parallel mapping efforts and to reduce potential conflicts due to divergence in interpretation of different metadata fields. Currently, DataHub can export ISA-JSON files, via GUI, containing metadata required by BioSamples and ENA metadata objects. Such exported files ([available here](https://github.com/elixir-europe/biohackathon-projects-2022/tree/main/27/seek_isajson)) have been used as a starting point during the project. The ability to export ISA-JSON files from DataHub through API calls has since been added.

## ISA-JSON upload tool or brokering tool

While the exported ISA-JSON file should comply in its content with the required metadata attributes and accepted values of the target repositories, the ISA model, represented by the multiple ISA-JSON schemas, remains repository-agnostic.  Furthermore, the ISA-JSON does not contain necessary and repository specific authentication/authorization information (e.g. sensitive login credentials), nor the data files to be deposited. Therefore a tool mediating the exchange of credentials, parameters, data and metadata files is made necessary. Given the flexibility and general applicability of the ISA-JSON model, such a tool does not need to be embedded and restricted to a single (meta)data management platform, but can act as an independent brokering tool. Using DataHub as the (meta)data management platform, this so called brokering tool should:
* have a GUI for biologists and be launched from DataHub (or other ISA-JSON creators)
* have programmatic access and API;
* validate the generated ISA-JSON files against the ISA-JSON schemas (not the content), possibly leveraging the existing [ISA-JSON validator](https://isa-tools.org/isa-api/content/validation.html#);
* ask for the following parameters:
    * User account/login.
    * Data files stored at location described in the ISA-JSON.
    * Target repository for each assay of the ISA-JSON.
    * Assay Name and/or Assay Type and/or Measurement/Technology type could be used to verify if the target repository for the assay is appropriate or not (this mapping should be done and maintained by both data brokering services and repositories).
* call for EBI multi-repository converter tool:
    * Pseudocode for BioSamples XML (study: source, sample).
    * Pseudocode for other repositories (for each assay).
* read repositories’ data submission receipts sent by the converter and send them to DataHub

## EBI multi-repository converter tool - Code logic and pseudocode

A conversion and brokering tool already exists to convert ISA-Tab and ISA-JSON format to SRA xml: [ISA-API] (https://academic.oup.com/gigascience/article/10/9/giab060/6371038). Few tests were performed with the ISA-API to convert the ISA-JSON coming from the DataHub into SRA xml, but there were technical difficulties that remained unclear even after testing the conversion with an ISA-JSON example file provided by the documentation. At the beginning, the developers of this project tried to use the ISA-API code as a base for further development, but the results were not conclusive. 

The EBI multi-repository converter applies the mapping rules between ISA-JSON structure/schemas and the different EBI repositories’ models, such as ENA XML objects (e.g., experiment, run etc) and BioSamples checklists for samples.

EBI will share the changes occurring to their configuration files (or checklists) in a Pub/Sub hub, to which platform or infrastructure providers generating ISA-JSON can subscribe to and be informed of updates to schemas and checklists.

### Pseudocode for BioSamples XML

In Investigation -> Studies -> Materials -> Sources, take @id. For each Source @id -> take @id, name (e.g plant 1), -> characteristics -> take category @id (i.e name of metadata field) and value -> annotationValue = Parent Biosample (submitted)

In Investigation -> Studies -> Materials -> Samples -> take @id (i.e id of the sample), name, characteristics = Child Biosample (submitted)

In Investigation -> Studies -> Protocols -> take Name = ‘sample collection’ and -> protocolType -> annotationValue for this field (i.e ‘sample collection’) (there will always be a protocol named ‘sample collection’ in the ISA)

In Investigation -> Studies -> Materials -> Samples -> derivesFrom -> look for the same source @id as above. = Add Biosample relationship ‘Sample @id derived from Source @id’ (in Biosamples, ‘target’ = Source @id)


After submission to BioSamples, the accession number for each entry will be added to the ISA-JSON file hosted at Datahub, e.g.:
1. ISA-JSON for ENA submission to contain a “characteristic” field equal to “accession” as required by ENA. In this case, users could add the accession if they are reusing an entry that is already registered in EBI repositories.
or
2. ENA add the “accession” field directly to the ISA-JSON

These two options 1 and 2 need to be further evaluated and discussed before decisions can be made.

### Pseudocode for ENA EXPERIMENT XML

In Assay → Materials → OtherMaterials, take Type = “Library Name” (hard coded variable)
List @id of all Type = “Library Name” in the Assay
List all Characteristics (key:value) for each Type = “Library Name”

In the same Assay → ProcessSequences, look for “output = @id of the Library Name”
Within the same schema of the output, look for “executesProtocol = xxx” and “its @id”
List the parameterValues (key=category : value=annotation value) of the executesProtocol

Look for “@id of each parameterValues” (key=category) in “Studies” (containing the Assay) → “Protocols” and take the "parameterName": {"annotationValue": xxx}. In other words:
* In the “Studies→Protocols” schema of the “Assay” in question, find “@id of each parameterValues” (key=category), and for each @id, list "parameterName": {"annotationValue": xxx}.
* Build XML, send to ENA/SRA, validate attributes and values

This first pseudocode does not include a way to keep track of the link between Sample entries and libraries, run and data files. To solve this issue we propose two strategies: a *backward and onward linking strategy*.

*Backward linking strategy*

Rationale: in assay, start by listing all “materials: type = Library name”, then look for input/output of those materials, going backwards through the process_schema.json, until the input is equal to a Sample@id. Same for “materials: type =Raw data file”, until the input is equal to a Library@id.

* From the Pseudocode for ENA EXPERIMENT XML (see above):
* In the same Assay → ProcessSequences, look for “output = @id of the Library Name”
* List the related inputs @id, for each input@id  → look for outputs = each input@id etc
* Repeat recursively until the  input@id = Sample@id of the samples of interest.
* Keep the link between Sample@id (ENA accession) and Library@id.
* Add ENA accession for library

Rules:
* All library entries must have one or more parent samples

* From the Pseudocode for RUN EXPERIMENT XML:
In the same Assay → dataFiles “type = Raw Data File”, “name=xxx” and “@id=xxx”
* In the same Assay → ProcessSequences, look for “outputs = @id of the data file”
* List the related inputs @id, for each input@id  → look for outputs = each input@id etc
* Repeat recursively until the  input@id = Libary@id of the libraries of interest.
* Keep the link between Library@id and Datafile@id

Rules:
* All data file entries must have one or more parent libraries

*Onward linking strategy*

Rationale: start from the ISA -JSON process_schema.json to record the links between inputs/outputs in one assay and use the material_schema.json and data_schema.json to find out the “type” of the materials. Based on material type, mapping to EXPERIMENT XML or RUN XML will occur for “type=Library name” and “type=Raw data file”, respectively.

* From Pseudocode for BioSamples XML (see above)
In specified Assay → ProcessSequences, look for “input = @id of the Samples of interest” → List the related output@id
* In the same Assay→Materials→OtherMaterials, look for each output@id = @id of the material  → then, look for the related “type= xxx”.
    * If “type=Library Name” → Pseudocode for ENA EXPERIMENT XML and link Sample accession with Library accession
    * If “type= not library name” → go back in ProcessSequences
        * For each related output@id (that is not a library), look for “input = related output@id” and take the related output@id
        * In the same Assay→Materials→OtherMaterials, look for each output@id = @id of the material  → then, look for the related “type= xxx”.
            * If “type=Library Name” → Pseudocode for ENA EXPERIMENT XML and link Sample accession with Library accession
            * If “type= not library name” → go back in ProcessSequences
Repeat recursively until all Sample@id have a Library@id.
* If related output@id is not found in Assay→Materials→OtherMaterials, look in Assay→dataFile → for each output@id = @id of the dataFile → then, look for the related “type= Raw Data File”.
* Pseudocode for RUN EXPERIMENT XML


The purpose of both strategies is to preserve the link between Sample-Library-Run accession numbers. Currently, attributes associated to other material types that might have been generated by the biologists between Sample and Library (e.g., RNA extract) and/or between Library and Run are not included in the converter. However, a choice to include those or not could be given to the biologists/data submitter in the future.


## EBI PubSub hub

The EBI multi-repository converter uses the ISA-JSON schemas to extract specific metadata from the JSON file and maps it to the requirements of the repository, as defined by the biologists/submitter in the submission tool (see above). Therefore, EBI configuration files specific for each repository could be useful to check compliance to requirements at this stage. The configuration files are stored and maintained by each repository, which will use [PubSub](https://cloud.google.com/pubsub/docs/overview) hub to publish any changes applied to those configuration files over time. Infrastructure providers will subscribe to EBI PubSub hub to receive notification of the changes, so that they can be applied to the metadata management platforms as well.

If all repositories will not use PubSub hub at first, a similar notification system could be implemented using github functions. An automated and programmatic communication system between metadata management platforms and such notification systems must be developed in the future.

## Programmatic submission to each EBI/ELIXIR repository

In order to fully implement the envisioned multi-repository data submission/brokering strategy, most of the EBI/ELIXIR repositories should provide a programmatic way for data submission. Otherwise, automation will be reduced and conversions into different formats could be needed resulting in a more manual, fragmented and less robust process.

Currently, some EBI/ELIXIR repositories provide a programmatic submission route: BioSamples, ENA, MetaboLights

## Overview of the proposed multi-repository data submission/brokering strategy

The single components of the multi-repository submission/brokering strategy have been described in detail in the previous paragraphs, Figure 1 below provides a summary and an overview of the proposed strategy.

![Schematic of multi-repository submission/brokering strategy:](.TODO)

Figure 1: Components of the multi-repository submission/brokering strategy:
* High level mapping of ISA-JSON structure to EBI repositories’ models
* ISA-JSON containing checklists and attributes required by EBI/ELIXIR repositories
* ISA-JSON upload tool or brokering tool
* EBI multi-repository converter tool 
* EBI PubSub hub
* Programmatic submission to each repository

The designed data brokering strategy expects a ISA-JSON file as starting point, containing multi-omics experiment metadata already compliant with repositories’ requirements, such as Samples checklist of BioSamples and ENA (SRA) attributes to describe the library or the sequencing strategy utilised. That experimental metadata needs to be placed into the ISA-JSON model according to the defined high-level mapping between ISA-JSON and EBI repositories’ metadata objects. Although the ISA-JSON content is compliant with repositories’ requirements, the ISA-JSON model will still be repository-agnostic. Validation of the JSON format should occur at this stage. 

The ISA-JSON should then be sent to a data submission/brokering tool which will require some parameters from the biologists (or users in general), such as:
* For each assay in the JSON, provide the assay name and the target repository
* Submission or brokering account for each repository. (Occasionally, the same credentials can be utilized across several repositories (e.g. ENA and Biosamples), or more frequently, an email address and a password specific to the archive is used. A Single Sign-On authentication may also be an efficient alternative. One example is the ‘Life Science Login’ by the European Life Science Research Infrastructures (LS RI). 

No additional parameters about study source and sample should be needed, since these JSON schemas should always be published in the BioSamples database, as defined in the mapping file. The tool will call the API of the EBI multi-repository converter tool and send the ISA JSON, together with the specified parameters. 

The EBI multi-repository converter will receive the ISA-JSON and for each pair of “assay - target repository” specified will fetch the configuration file of the matching repository to check the presence of the required attributes and validate them. If the check is successful, the assay will be converted in the format required by the repository (for instance ENA XML object); if the check fails, the submission process will be rejected and an informative error message will be sent to the submission/brokering tool for the submitter to read. The validation of the attributes and the conversion from ISA-JSON to repository-specific format will be performed according to the rules defined in the mapping file. A successful submission will generate a receipt that will be sent to the submission tool which will send the relevant information to the platform that generated the ISA-JSON.

For submission to BioSamples/ENA by using this strategy, we propose that
* Source, Sample and their characteristics, and Sample collection protocol and its parameter values are sent to BioSamples. Fixed or hard coded values have to be established (similarly to ISA tools), such as:
    * Material types = Source, Sample
    * Protocol type = Sample collection
* OtherMaterials type: 
    * Library name for EXPERIMENT XML
    * Raw Data File for RUN XML
    * etc

Different fixed or hard coded values have to be established for each repository and in agreement with EBI, based on the algorithm that will be used for the mapping.

## Alternative implementation using the Omnipy Python library

As an alternative implementation of the ISA-JSON to ENA data brokering tool, we also explored the possibilities of extending the ["Omnipy" Python library](https://github.com/fairtracks/omnipy) (at the time of the 2022 Biohackathon named "uniFAIR") with functionality that would enable using the library as a basis for the implementation of the data brokering tool. The library was under heavy development at the time and was not ready to be used for the main implementation. The feature that was developed and showcased under the 2022 Biohackathon was a data flow that transformed a set of nested JSON documents into relational tables, in the form of both CSV files and Pandas DataFrames. This was demonstrated using the example ISA-JSON documents referred to previously (https://github.com/elixir-europe/biohackathon-projects-2022/tree/main/27/seek_isajson). Further functionality to map relational data to other metadata standards is under development.

Omnipy is a Python library for data wrangling that aims to be highly scalable, reusable and interoperable with other tools and services. It allows running a data flow on top of the [Prefect](https://www.prefect.io/) Python library, which is a simple-to-use, industry-standard ETL (Extract, Transform, Load) solution. Prefect allows deployment and orchestration of data workflows on a range of compute and storage infrastructures, such as Kubernetes and AWS S3 buckets. Hence, data flows developed using Omnipy can easily scale up to manage large datasets on cloud computing infrastructures, including the possibilities for asynchronous operation to e.g. wait for external API endpoints or data uploads. The library may thus potentially provide a useful backbone for a data brokering tool.

# Discussion

One of the goals of this project was to design a brokering strategy based on a “FAIR-by-design” approach in which biologists would be able to comply to repositories requirements (such as checklist attributes and ontologies) from the beginning of their research by using (meta)data management platforms, such as DataHub. In this approach, the biologists requirements of different repositories (e.g. complying with checklists, and use of controlled vocabulary and ontologies) will be provided in the shape of templates. These templates will be customizable to ensure best fitting to project specific requirements.  Biologists will fill in the templates while working on their research project. When experiments are completed and the data ready to be published in repositories, biologists will export an Investigation (containing Study and Assays) as an ISA.json file. An external tool will handle the credentials to login into the repositories and to access data files, the uploading of data files, and the assignment of Studies’ sources and samples to BioSamples and of each Assay to the appropriate repository. The upload tool will then send the information to the EBI multi-repository converter, where the metadata attributes of Study and Assays will be compared with checklists and requirements of the appropriate repository and the submission will occur. 

During this BioHackathon, we designed a possible method to submit metadata exported from DataHub (a FAIRDOM-SEEK instance) in ISA-JSON format to BioSamples and ENA. The method should be applicable to any repository that uses BioSamples accession numbers and has programmatic submission. A proof of concept has been developed for submission of metadata exported from DataHub in ISA-JSON format to BioSamples and ENA (based on requirements for submission of raw sequencing data). 

However, many aspects in this process need to be further defined and worked on by EBI repositories and infrastructure providers interested in offering a data brokering tool to biologists. First, data repositories and data brokering services should implement automated systems for syncing of metadata checklists, ontologies and other repositories’ requirements between the repositories and the metadata management platforms. Here, we suggest systems based on PubSub or GitHub. Second, a commitment towards a common mapping of repositories’ specific metadata attributes and checklist into one “agnostic” ISA model is necessary in order to use one model for multi-omics data submission. This concept is not entirely new, since a similar approach has been proposed before by the authors of the ISA model, using ISA software suite based on ISA-Tab first and more recently ISA APIs (https://academic.oup.com/bioinformatics/article/26/18/2354/208338), (https://academic.oup.com/gigascience/article/10/9/giab060/6371038). However, the proposed approach is different from the previous one in the following ways:

1. While ISA configuration files were to be defined by “power users” or “community curators”, we proposed that the necessary files needed for the data deposition (to check compliance to repositories’ requirements) should be provided by repositories themselves via a programmatic access point, so that providers of metadata management platforms can keep the templates for biologists to use up to date and in sync in an automated way. This will reduce the time for making such templates, which was a limitation identified by the ISA team (https://academic.oup.com/bioinformatics/article/26/18/2354/208338)

2. Community curators, data stewards or power users of the metadata management platform, can customize the templates starting from the ones given by the repositories. Although the platform should validate the custom metadata, the customization will not affect the “configuration files” of the repositories. Which repositories accept custom metadata, and where this support can be improved is a topic outside of the scope of this project.

3. We propose to limit the use of hard coded variable names and values, and keep it to the bare minimum, while making more use of the relations between the different ISA objects as encoded in the ISA-JSON schemas. Specifically, we propose that the ISA upload tool only parse and validate the structure of the ISA-JSON, by checking for the compliance of all the objects with the rules described in the ISA-JSON schemas, but without validating variable names or values. For instance, checking for the presence of “protocol” but without validating against a list of hard coded protocol types (e.g., library construction). This content-level validation should be done at the data submission stage by each repository. 

Although a basic design and a partial proof of concept for a new strategy and new tool that uses more flexible configuration files in sync with the repositories’ requirement, and that supports multi-repository submission from one single ISA-JSON file has been developed during this BioHackathon project, additional reasoning and testing must be done, as described in the “Future work” section below.

# Conclusions

In conclusion, through this Biohackathon we devised a comprehensive strategy for an ISA-JSON-to-ELIXIR Repository brokering system, in order to efficiently share heterogenous, multi-omics metadata to public repositories. This included the creation and implementation of a multi-repository ISA-JSON converter tool, as well as considering methods to keep up to date with the latest changes to schemas and metadata requirements of the repositories.

While this strategy was developed with the DataHub research management platform in mind, we hope that in the future, the brokering approach described here as well as it’s components (e.g. the converter tool, mapping file/s, and to-be-developed pub sub system) can be applied to any research platform using ISA-JSON.

Indeed the strategy described here aims to not only benefit research platforms but also the repositories themselves, by improving interoperability across them as well as expanding the accepted file formats for their user base.

# Future work

Multiple aspects of the work done so far must be further thought through, discussed and tested with EBI/ELIXIR Repositories to ensure we can develop a sustainable ISA-JSON brokering system. Here we provide a list of topics to be worked on in the future.

* A centralized service in which changes to schemas are logged and exposed to the public, and are accessible programmatically. In this document, this system has been provisionally named Pub/sub (publish/subscribe system), and will allow for easier compliance to requirements from each repository.
* EBI repositories to maintain a public mapping of:
    * ISA-JSON fields + unique repository fields (it could be placed in Pub/Sub)
* Harmonization of what metadata fields overlap/are similar across repositories
* Submission dates and release date in the ISA-JSON apply for the entire Investigation or Study.
    * What if each assay has a different release date?
    * What if one investigation has multiple studies all submitted at different times?
* Asking the submitter to assign a target repository for each assay name might be a flexible method which gives the power to choose the repositories to the users. While using additional parameters such as measurement and technology types could help define the repository to be used, maintaining such parameters aligned with current research trends might be a burden best avoided. 
* Some repositories do not utilize Biosamples or cross-reference Biosample accessions for storing sample metadata - how to adjust the code if not all repositories make use of BioSamples?
* ArrayExpress makes use of BioStudies, how could we tackle that?
* ArrayExpress and PRIDE: from ISA-JSON to MAGE-Tab. To be discussed starting from isa tools available.

# Acknowledgements

We would like to thank Philippe Rocca-Serra from the ISA-Tools team and Xiaoran Zhou from the NFDI4 Plants group, for their expertise during the project.

# References 


