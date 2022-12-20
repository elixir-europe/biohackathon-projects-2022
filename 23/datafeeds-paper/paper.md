---
title: 'Publishing and Consuming Schema.org Data Dumps: BioHackathon Europe 2022 Project Report'
tags:
  - FAIR
  - RDF
  - Schema.org
  - Bioschemas
  - Data dump
authors:
  - name: Alasdair Gray
    orcid: 0000-0002-5711-4872
    affiliation: 1
  - name: Alban Gaignard
    orcid: 0000-0002-3597-8557
    affiliation: 2
  - name: Ivan Mičetić
    orcid: 0000-0003-1691-8425
    affiliation: 3

affiliations:
 - name: Heriot-Watt University, Edinburgh, UK
   index: 1
 - name: Nantes Université, CNRS, INSERM, l’institut du thorax, F-44000 Nantes, France
   index: 2
 - name: University of Padua, Padova, Italy
   index: 3

date: 20 December 2022
bibliography: paper.bib
authors_short: Bioschemas team
group: Bioschemas team
event: BioHackathon Europe 2022
git_url: https://github.com/elixir-europe/biohackathon-projects-2022/tree/main/projects/23/
---

# Introduction

Bioschemas provides a lightweight vocabulary for making the content of Web pages machine processable. However, as shown in Project 29 at BioHackathon 2021 [@gray_papadopoulos_gaignard_rosnet_mičetić_moretti_2022], harvesting markup by visiting each page of a site is not feasible for large sites due to the time required to process each page. This approach imposes processing demands on the publisher and consumer. In February 2022, the Schema.org community proposed a mechanism for sharing markup from multiple pages as a DataFeed published at an established location [@sdo_datafeed]. The feed could be a single file with the whole content or split into multiple files based on some aspect of the dataset, e.g. ChEMBL could have a file for proteins and another one for molecular entities. This would reduce processing demands for publishers and consumers and speed up data harvesting.

The aim of this hackathon project is to explore the implementation of the Schema.org proposal from both a producer and consumer perspective, for a variety of resources implementing different Bioschemas profiles. On the consumer side, we will prototype a consumption pipeline that enables these feeds to be ingested into knowledge graphs including IDP Knowledge Graph [@gray_papadopoulos_mičetić_hatos_2021,@GrayEtal:bioschemas-idpkg:swat4hcls2022] and the Open AIRE Research Graph [@doucet_openaire_2019]. To enable the latter, we will also develop additional mappings between Bioschemas profiles and OpenAIRE’s data model. We will need to understand how to mix schema feeds from different sources, possibly exploiting background knowledge from Wikidata to reconcile concepts.

# Publishing Data Dumps

During the BioHackathon, we reviewed the Schema.org proposal for publishing the markup found within a site as a single, or small number of files, at a well known location [@sdo_datafeed]. We also attempted to publish data dumps for several sites.

We found the term DataFeed did not match our understanding of what was being published[^1]. The idea is to publish a dump of the data that would be found by harvesting every page of the site. We provided this as feedback to the Schema.org community in their GitHub Issue Tracker ([#2891](https://github.com/schemaorg/schemaorg/issues/2891#issuecomment-1308587831)) and in a future revision they are likely to change to referring to these as Datasets.

[^1]: Prototypes modelling the MobiDB data dump and associated metadata as a DataFeed can be found on [GitHub](https://github.com/elixir-europe/biohackathon-projects-2022/tree/datafeedv1/23/datafeeds).

## Data Dump Files

To allow for the creation of data dumps, we needed to provide guidelines on the structure of the data file. The expectation is that the `jsonld` files will contain all markup that would be extracted by visiting each page of the site without duplication of markup. There can be multiple `jsonld` files for the site, e.g. you could decide to have one file per major data type of the site.

Each `jsonld` file should contain a single array of `json` objects, see example below which is a snippet of the [prototype MobiDB file](https://github.com/elixir-europe/biohackathon-projects-2022/blob/datafeedv1/23/datadownload/dump.jsonld) (also available on JSON-LD Playground [full](https://json-ld.org/playground/#startTab=tab-expanded&json-ld=%5B%7B%22%40context%22%3A%22https%3A%2F%2Fschema.org%22%2C%22includedInDataset%22%3A%22https%3A%2F%2Fmobidb.org%2F%232021-11%22%2C%22%40type%22%3A%22Protein%22%2C%22%40id%22%3A%22https%3A%2F%2Fmobidb.org%2FP0DTC9%22%2C%22http%3A%2F%2Fpurl.org%2Fdc%2Fterms%2FconformsTo%22%3A%7B%22%40id%22%3A%22https%3A%2F%2Fbioschemas.org%2Fprofiles%2FProtein%2F0.11-RELEASE%22%2C%22%40type%22%3A%22CreativeWork%22%7D%2C%22identifier%22%3A%22https%3A%2F%2Fidentifiers.org%2Fmobidb%3AP0DTC9%22%2C%22sameAs%22%3A%22http%3A%2F%2Fpurl.uniprot.org%2Funiprot%2FP0DTC9%22%2C%22name%22%3A%22Nucleoprotein%22%2C%22hasBioPolymerSequence%22%3A%22MSDNGPQNQRNAPRITFGGPSDSTGSNQNGERSGARSKQRR...%22%2C%22hasSequenceAnnotation%22%3A%5B%7B%22%40type%22%3A%22SequenceAnnotation%22%2C%22%40id%22%3A%22https%3A%2F%2Fmobidb.org%2FP0DTC9%23prediction-disorder-mobidb_lite.1_51%22%2C%22sequenceLocation%22%3A%7B%22%40type%22%3A%22SequenceRange%22%2C%22%40id%22%3A%22https%3A%2F%2Fmobidb.org%2FP0DTC9%23sequence-location.1_51%22%2C%22http%3A%2F%2Fpurl.org%2Fdc%2Fterms%2FconformsTo%22%3A%7B%22%40id%22%3A%22https%3A%2F%2Fbioschemas.org%2Fprofiles%2FSequenceRange%2F0.1-DRAFT%22%2C%22%40type%22%3A%22CreativeWork%22%7D%2C%22rangeStart%22%3A1%2C%22rangeEnd%22%3A51%7D%2C%22additionalProperty%22%3A%7B%22%40type%22%3A%22PropertyValue%22%2C%22name%22%3A%22Term%22%2C%22value%22%3A%7B%22%40type%22%3A%22DefinedTerm%22%2C%22%40id%22%3A%22https%3A%2F%2Fdisprot.org%2Fassets%2Fdata%2FIDPO_v0.2.owl%23IDPO%3A00076%22%2C%22inDefinedTermSet%22%3A%7B%22%40type%22%3A%22DefinedTermSet%22%2C%22%40id%22%3A%22https%3A%2F%2Fdisprot.org%2Fassets%2Fdata%2FIDPO_v0.2.owl%22%2C%22name%22%3A%22IDP%20ontology%22%7D%2C%22termCode%22%3A%22IDPO%3A00076%22%2C%22name%22%3A%22Disorder%22%7D%7D%2C%22description%22%3A%22Protein%20disordered%20region%20predicted%20by%20MobiDB-lite%22%7D%5D%7D%2C%7B%22%40context%22%3A%22https%3A%2F%2Fschema.org%22%2C%22includedInDataset%22%3A%22https%3A%2F%2Fmobidb.org%2F%232021-11%22%2C%22%40type%22%3A%22Protein%22%2C%22%40id%22%3A%22https%3A%2F%2Fmobidb.org%2FP04637%22%2C%22http%3A%2F%2Fpurl.org%2Fdc%2Fterms%2FconformsTo%22%3A%7B%22%40id%22%3A%22https%3A%2F%2Fbioschemas.org%2Fprofiles%2FProtein%2F0.11-RELEASE%22%2C%22%40type%22%3A%22CreativeWork%22%7D%2C%22identifier%22%3A%22https%3A%2F%2Fidentifiers.org%2Fmobidb%3AP04637%22%2C%22sameAs%22%3A%22http%3A%2F%2Fpurl.uniprot.org%2Funiprot%2FP04637%22%2C%22name%22%3A%22Cellular%20tumor%20antigen%20p53%22%2C%22hasBioPolymerSequence%22%3A%22MEEPQSDPSVEPPLSQETFSDLWKLLPENNVLSPLPSQ...%22%2C%22hasSequenceAnnotation%22%3A%5B%7B%22%40type%22%3A%22SequenceAnnotation%22%2C%22%40id%22%3A%22https%3A%2F%2Fmobidb.org%2FP04637%23prediction-disorder-mobidb_lite.50_96%22%2C%22sequenceLocation%22%3A%7B%22%40type%22%3A%22SequenceRange%22%2C%22%40id%22%3A%22https%3A%2F%2Fmobidb.org%2FP04637%23sequence-location.50_96%22%2C%22http%3A%2F%2Fpurl.org%2Fdc%2Fterms%2FconformsTo%22%3A%7B%22%40id%22%3A%22https%3A%2F%2Fbioschemas.org%2Fprofiles%2FSequenceRange%2F0.1-DRAFT%22%2C%22%40type%22%3A%22CreativeWork%22%7D%2C%22rangeStart%22%3A50%2C%22rangeEnd%22%3A96%7D%2C%22additionalProperty%22%3A%7B%22%40type%22%3A%22PropertyValue%22%2C%22name%22%3A%22Term%22%2C%22value%22%3A%7B%22%40type%22%3A%22DefinedTerm%22%2C%22%40id%22%3A%22https%3A%2F%2Fdisprot.org%2Fassets%2Fdata%2FIDPO_v0.2.owl%23IDPO%3A00076%22%2C%22inDefinedTermSet%22%3A%7B%22%40type%22%3A%22DefinedTermSet%22%2C%22%40id%22%3A%22https%3A%2F%2Fdisprot.org%2Fassets%2Fdata%2FIDPO_v0.2.owl%22%2C%22name%22%3A%22IDP%20ontology%22%7D%2C%22termCode%22%3A%22IDPO%3A00076%22%2C%22name%22%3A%22Disorder%22%7D%7D%2C%22description%22%3A%22Protein%20disordered%20region%20predicted%20by%20MobiDB-lite%22%7D%5D%7D%5D)/[short](https://json-ld.org/playground/#startTab=tab-expanded&json-ld=%5B%7B%22%40context%22%3A%22https%3A%2F%2Fschema.org%22%2C%22includedInDataset%22%3A%22https%3A%2F%2Fmobidb.org%2F%232021-11%22%2C%22%40type%22%3A%22Protein%22%2C%22%40id%22%3A%22https%3A%2F%2Fmobidb.org%2FP0DTC9%22%2C%22http%3A%2F%2Fpurl.org%2Fdc%2Fterms%2FconformsTo%22%3A%7B%22%40id%22%3A%22https%3A%2F%2Fbioschemas.org%2Fprofiles%2FProtein%2F0.11-RELEASE%22%2C%22%40type%22%3A%22CreativeWork%22%7D%2C%22identifier%22%3A%22https%3A%2F%2Fidentifiers.org%2Fmobidb%3AP0DTC9%22%2C%22sameAs%22%3A%22http%3A%2F%2Fpurl.uniprot.org%2Funiprot%2FP0DTC9%22%2C%22name%22%3A%22Nucleoprotein%22%7D%2C%7B%22%40context%22%3A%22https%3A%2F%2Fschema.org%22%2C%22includedInDataset%22%3A%22https%3A%2F%2Fmobidb.org%2F%232021-11%22%2C%22%40type%22%3A%22Protein%22%2C%22%40id%22%3A%22https%3A%2F%2Fmobidb.org%2FP04637%22%2C%22http%3A%2F%2Fpurl.org%2Fdc%2Fterms%2FconformsTo%22%3A%7B%22%40id%22%3A%22https%3A%2F%2Fbioschemas.org%2Fprofiles%2FProtein%2F0.11-RELEASE%22%2C%22%40type%22%3A%22CreativeWork%22%7D%2C%22identifier%22%3A%22https%3A%2F%2Fidentifiers.org%2Fmobidb%3AP04637%22%2C%22sameAs%22%3A%22http%3A%2F%2Fpurl.uniprot.org%2Funiprot%2FP04637%22%2C%22name%22%3A%22Cellular%20tumor%20antigen%20p53%22%7D%5D) where it can also be visualised as shown in the screenshot). 

```json
[
  {
    "@context": "https://schema.org",
    "includedInDataset": "https://mobidb.org/#2021-11",
    "@type": "Protein",
    "@id": "https://mobidb.org/P0DTC9",
    "http://purl.org/dc/terms/conformsTo": {
      "@id": "https://bioschemas.org/profiles/Protein/0.11-RELEASE",
      "@type": "CreativeWork"
    },
    "identifier": "https://identifiers.org/mobidb:P0DTC9",
    "sameAs": "http://purl.uniprot.org/uniprot/P0DTC9",
    "name": "Nucleoprotein"
  },
  {
    "@context": "https://schema.org",
    "includedInDataset": "https://mobidb.org/#2021-11",
    "@type": "Protein",
    "@id": "https://mobidb.org/P04637",
    "http://purl.org/dc/terms/conformsTo": {
      "@id": "https://bioschemas.org/profiles/Protein/0.11-RELEASE",
      "@type": "CreativeWork"
    },
    "identifier": "https://identifiers.org/mobidb:P04637",
    "sameAs": "http://purl.uniprot.org/uniprot/P04637",
    "name": "Cellular tumor antigen p53"
  }
]
```

![mobidb-datadump](mobidb-datadump.png)

The data dumps should follow the associated profiles and include details of their `@type`, a link to the Schema.org context using the `https` protocol, and a statement of conformance with the `dcterms:conformsTo` property. We have also used a newly proposed property `includedInDataset` to provide a link from the protein description to the dataset in which it can be found.

## Data Dump Metadata

To ensure that the data dumps are FAIR, they should contain a dataset description describing them conforming to the [Bioschemas Dataset Profile](https://bioschemas.org/profiles/Dataset). Note that each dump file is a `schema:DatasetDownload`. Below we include the [MobiDB prototype metadata](https://github.com/elixir-europe/biohackathon-projects-2022/blob/datafeedv1/23/datadownload/DataDownload.jsonld).

```json
{
  "@context": "https://schema.org/",
  "@type": "Dataset",
  "@id": "https://mobidb.org/#2021-11",
  "http://purl.org/dc/terms/conformsTo": {
    "@id": "https://bioschemas.org/profiles/Dataset/0.3-RELEASE-2019_06_14",
    "@type": "CreativeWork"
  },
  "includedInDataCatalog": {
    "@id": "https://mobidb.org/#DataCatalog"
  },
  "distribution": [
    {
      "@type": "DataDownload",
      "encodingFormat": "application/ld+json",
      "contentUrl": "https://mobidb.org/.well-known/protein",
      "dateModified": "2021-11"
    }
  ],
  "url": "https://mobidb.org/",
  "dateModified": "2021-11",
  "version": "4.1",
  "name": "MobiDB (November 2021)",
  "description": "MobiDB is a database of protein disorder and mobility annotations",
  "identifier": "https://mobidb.org/#2021-11",
  "keywords": [
    "IDP"
  ],
  "creator": {
    "@id": "https://biocomputingup.it/#Organization"
  },
  "license": {
    "@type": "CreativeWork",
    "@id": "https://creativecommons.org/licenses/by/4.0/",
    "name": "Creative Commons CC4 Attribution",
    "url": "https://creativecommons.org/licenses/by/4.0/"
  }
}
```

## Live Deploys

Bioschemas maintains a list of [live deployments](https://bioschemas.org/developer/liveDeploys), i.e. sites that are known to contain markup conforming to a Bioschemas profile. We extended the functionality of the live deploys page to include a section on available data dumps. Data dumps can either be associated with an existing known deployment or a completely new deployment. The screenshot below shows the seven sites that are known to have a data dump, with the MetaNetX entry expanded to show its full details.

![live-deploys](live-deploys.png)

# Data Harvesting

During the BioHackathon, we started harvesting the data dumps listed on the [live deploy page](https://bioschemas.org/developer/liveDeploys#nav-datadump) and loading them into a [GraphDB](https://graphdb.ontotext.com/) triplestore using a [script](https://github.com/BioSchemas/bioschemas-data-harvesting/blob/8c36d72082c23df52d60ef9d45bc2b2f3f7718df/datadump-loading/datadump-load-script.sh) to fetch, load, and capture provenance about the process. This process was completed after the BioHackathon once the sources had published complete and correct dump files.

Each dataset is loaded into its own named graph. Provance triples are added to the default graph to describe the named graph. The provenance captures where the dump file was acquired from (`pav:retrievedFrom`) and when (`pav:retrievedOn`).

In total, six data dumps were loaded into the triplestore resulting in 47,956,784 triples being loaded. Details of the data loading can be found in the following table.

| Resource                                                     | Format    | Load time                                                    | Notes                                                        |
| ------------------------------------------------------------ | --------- | ------------------------------------------------------------ | ------------------------------------------------------------ |
| [bio.tools](https://raw.githubusercontent.com/bio-tools/content/master/datasets/bioschemas-dump.ttl) | turtle    | 8s                                                           |                                                              |
| [ChEMBL (Mirror)](https://github.com/ammar257ammar/SWAT4HCLS2022-ChEMBL-bioschemas-mapping/releases/download/v1.0.0/json-ld-output.zip) | zip       | 5h2m10s  <br />32s<br />54s<br />40s<br />38s<br />38s<br />46s<br />46s<br />44s<br />43s<br />47s<br />9s<br />1m10s<br />1m7s<br />1m1s<br />50s<br />49s<br />49s<br />46s<br />50s | Each file downloaded and loaded separately                   |
| [MassBank Europe](https://msbi.ipb-halle.de/~sneumann/MassBank-2006.06.jsonld) | jsonld    | 19m42s                                                       |                                                              |
| [MetaNetX](https://www.metanetx.org/ftp/latest/metanetx.schema_org.jsonld.gz) | jsonld.gz | 5h26m33s                                                     | Only 1 million molecular entities due to hard limit set in generation script |
| [MobiDB](https://mobidb.org/.well-known/protein)             | jsonld    | <1s                                                          | Only 1 thousand proteins due to a hard limit in the API      |
| [Rhea (Unofficial)](https://swel.macs.hw.ac.uk/bioschemas-data/BH2022/rhea.jsonld) | jsonld    | 1m24s                                                        |                                                              |

### Post Processing

After loading, it was found that one resource had used the `https` namespace for Schema.org rather than `http`, and one source had used undefined prefixes. A series of update queries of the form shown below were issued to fix these issues.

```SPARQL
WITH <http://bio.tools/>
DELETE {
    ?s ?p <schema:Organization>
}
INSERT {
    ?s ?p <http://schema.org/Organization>
}
WHERE {
    ?s ?p <schema:Organization>
}
```

# Data Analysis

We reused the notebook originally developed at BioHackathon 2020 [@gray_bioschemas-idpcntral_2021] and since evolved for the Intrinsically Disordered Protein Knowledge Graph (IDP-KG) [@gray_idp-kg_2022]. We include the HCLS Dataset Description profile statistics queries[^footnote] [@Dumontier_HCLS-datadesc_PeerJ2016], read in from an existing [repository](https://github.com/AlasdairGray/HCLS-Stats-Queries). We also include [queries](https://github.com/BioSchemas/bioschemas-data-harvesting/tree/main/queries) developed specifically for the analysis of the Bioschemas harvested data.

To use the [notebook (MyBinder launcher)](https://mybinder.org/v2/gh/BioSchemas/bioschemas-data-harvesting/HEAD?labpath=AnalysisQueries.ipynb), you simply need to run all cells and then select the query you would like to execute from the resulting dropdown menu.

We now present the results of the queries obtained during the hackathon, i.e. the data values are as they were on 11 November 2021. Running the notebook in March 2022 obtains different results due to more harvested data having been added.

[^footnote]: [Dataset Descriptions: HCLS Community Profile §6](https://www.w3.org/TR/hcls-dataset/#s6_6) accessed March 2022

## HCLS Dataset Statistics

We include here a selection of results from some of the HCLS statistics queries. We focus on those providing the most interesting statistics for the available data. For the full set of queries and results, please run the notebook.

### Number of triples

This is the raw count of the number of triples contained in the triplestore repository.

| triples |
| ---: |
| 10,610,743 |

### Number of named graphs 

The result presented here is equivalent to number of pages harvested since BMUSE generates a named graph for each page harvested.

| graphs |
| ---: |
| 413,748 |

### Number of instance per class

There are many different types included in the markup. BMUSE extracts all markup, not just Bioschemas profiles.

The results are ordered by the Class IRI; in the notebook you can edit the query and change the ordering of results.

(57 results)

| Class | distinctInstances |
| :--- | ---: |
| http://rdfs.org/sioc/ns#Item | 57 |
| http://xmlns.com/foaf/0.1/Document | 89 |
| http://xmlns.com/foaf/0.1/Image | 219 |
| https://bioschemas.org/Gene | 238,079 |
| https://bioschemas.org/Protein | 1,262 |
| https://bioschemas.org/Taxon | 55,884 |
| https://schema.org/AboutPage | 1 |
| https://schema.org/Action | 3 |
| https://schema.org/Answer | 8 |
| https://schema.org/BioChemEntity | 49,823 |
| https://schema.org/BreadcrumbList | 14,037 |
| https://schema.org/ChemicalSubstance | 29 |
| https://schema.org/CollectionPage | 187 |
| https://schema.org/CollegeOrUniversity | 2 |
| https://schema.org/ContactPoint | 148 |
| https://schema.org/CreativeWork | 14,299 |
| https://schema.org/DataCatalog | 7,439 |
| https://schema.org/DataDownload | 1,497 |
| https://schema.org/Dataset | 201,302 |
| https://schema.org/DefinedTerm | 4,261 |
| https://schema.org/DefinedTermSet | 4,112 |
| https://schema.org/DigitalDocument | 1 |
| https://schema.org/EducationalOrganization | 3 |
| https://schema.org/Event | 12,818 |
| https://schema.org/FAQPage | 1 |
| https://schema.org/Gene | 39 |
| https://schema.org/GeoShape | 19,398 |
| https://schema.org/GovernmentOrganization | 1 |
| https://schema.org/ItemList | 187 |
| https://schema.org/ListItem | 28,137 |
| https://schema.org/MolecularEntity | 199,350 |
| https://schema.org/NGO | 11,717 |
| https://schema.org/Offer | 5 |
| https://schema.org/Organization | 206,715 |
| https://schema.org/PeopleAudience | 2,475 |
| https://schema.org/Person | 326,935 |
| https://schema.org/Place | 19,438 |
| https://schema.org/PostalAddress | 307,406 |
| https://schema.org/PropertyValue | 144,002 |
| https://schema.org/Protein | 4,462 |
| https://schema.org/QAPage | 1 |
| https://schema.org/Question | 8 |
| https://schema.org/ScholarlyArticle | 9,350 |
| https://schema.org/SearchAction | 5 |
| https://schema.org/SequenceAnnotation | 15,786 |
| https://schema.org/SequenceRange | 15,786 |
| https://schema.org/SoftwareApplication | 4 |
| https://schema.org/SoftwareSourceCode | 4 |
| https://schema.org/Study | 4,328 |
| https://schema.org/Thing | 27,872 |
| https://schema.org/URL | 1 |
| https://schema.org/WebApplication | 3 |
| https://schema.org/WebPage | 55,114 |
| https://schema.org/WebSite | 5 |
| https://schema.org/contact | 40 |
| https://schema.org/hostInstitution | 40 |
| https://schema.org/url | 10,360 |

## Bioschemas Queries

The following queries focus on features of interest to the Bioschemas community.

### Instances per Bioschemas Class

Note that due to the data content we need to include some properties with both a Bioschemas namespace and a Schema.org namespace.

The results are ordered by the count of the number of instances; in the notebook you can edit the query and change the ordering of results.

(18 results)

| Class | instances |
| :--- | ---: |
| https://schema.org/Person | 326,935 |
| https://bioschemas.org/Gene | 238,079 |
| https://schema.org/Organization | 206,715 |
| https://schema.org/Dataset | 201,302 |
| https://schema.org/MolecularEntity | 199,350 |
| https://bioschemas.org/Taxon | 55,884 |
| https://schema.org/BioChemEntity | 49,823 |
| https://schema.org/SequenceAnnotation | 15,786 |
| https://schema.org/SequenceRange | 15,786 |
| https://schema.org/Event | 12,818 |
| https://schema.org/ScholarlyArticle | 9,350 |
| https://schema.org/DataCatalog | 7,439 |
| https://schema.org/Protein | 4,462 |
| https://schema.org/Study | 4,328 |
| https://bioschemas.org/Protein | 1,262 |
| https://schema.org/Gene | 39 |
| https://schema.org/ChemicalSubstance | 29 |
| https://schema.org/SoftwareApplication | 4 |

### Number of Domains

This result informs us how many web domains were harvested. This is approximately equal to the number of datasets, but some sites may host more than one dataset so not necessarily an exact correspondence.

| count |
| ---: |
| 25 |

### Number of Pages per Domain

We now report the number of pages that have been harvested from each domain. Note that we do not understand the empty domain as all markup was extracted from a web domain.

(25 results)

| domain | count |
| :--- | ---: |
| massbank.eu | 76,253 |
| scholia.toolforge.org | 74,319 |
| www.gbif.org | 68,167 |
| test.intermine.org | 49,959 |
| bgee.org | 49,022 |
| www.metanetx.org | 49,012 |
| tess.elixir-europe.org | 13,939 |
| ega-archive.org | 11,833 |
| fairsharing.org | 6,351 |
| prosite.expasy.org | 5,858 |
| ippidb.pasteur.fr | 2,433 |
| mobidb.org | 2,082 |
| disprot.org | 2,043 |
| pcddb.cryst.bbk.ac.uk | 1,402 |
| www.ebi.ac.uk | 672 |
| proteinensemble.org | 187 |
| www.france-bioinformatique.fr | 86 |
| pairedomicsdata.bioinformatics.nl | 78 |
| www.covid19dataportal.org | 19 |
|  | 12 |
| www.alliancegenome.org | 11 |
| biopragmatics.github.io | 3 |
| nanocommons.github.io | 3 |
| bridgedb.github.io | 2 |
| www.uniprot.org | 2 |

### Count of Types per Domain

We now report the number of instances of each type on each domain. What is intersting here is the fact that Bgee has many proteins listed on their pages.

The results are ordered by the count of the number of instances; in the notebook you can edit the query and change the ordering of results.

(146 results)

| domain | type | count |
| :--- | :--- | ---: |
| www.gbif.org | https://schema.org/PostalAddress | 297,090 |
| www.gbif.org | https://schema.org/Person | 291,260 |
| bgee.org | https://bioschemas.org/Gene | 263,793 |
| www.gbif.org | https://schema.org/Organization | 186,688 |
| www.gbif.org | https://schema.org/PropertyValue | 126,268 |
| massbank.eu | https://schema.org/Dataset | 76,249 |
| massbank.eu | https://schema.org/MolecularEntity | 76,249 |
| scholia.toolforge.org | https://schema.org/CreativeWork | 74,310 |
| scholia.toolforge.org | https://schema.org/MolecularEntity | 74,310 |
| www.gbif.org | https://schema.org/Dataset | 63,134 |
| test.intermine.org | https://schema.org/Dataset | 49,959 |
| test.intermine.org | https://schema.org/BioChemEntity | 49,823 |
| bgee.org | https://bioschemas.org/Taxon | 49,059 |
| bgee.org | https://schema.org/WebPage | 49,009 |
| www.metanetx.org | https://schema.org/CreativeWork | 49,002 |
| www.metanetx.org | https://schema.org/MolecularEntity | 49,001 |
| prosite.expasy.org | https://schema.org/Person | 31,364 |
| tess.elixir-europe.org | https://schema.org/ListItem | 27,872 |
| tess.elixir-europe.org | https://schema.org/Thing | 27,872 |
| www.gbif.org | https://schema.org/GeoShape | 19,398 |
| www.gbif.org | https://schema.org/Place | 19,398 |
| tess.elixir-europe.org | https://schema.org/BreadcrumbList | 13,938 |
| tess.elixir-europe.org | https://schema.org/Event | 12,778 |
| prosite.expasy.org | https://schema.org/Organization | 11,715 |
| prosite.expasy.org | https://schema.org/NGO | 11,714 |
| disprot.org | https://schema.org/PropertyValue | 11046 |
| disprot.org | https://schema.org/SequenceAnnotation | 11,046 |
| disprot.org | https://schema.org/SequenceRange | 11,046 |
| prosite.expasy.org | https://schema.org/url | 10,360 |
| tess.elixir-europe.org | https://schema.org/PostalAddress | 10,316 |
| ega-archive.org | https://schema.org/DataCatalog | 7,431 |
| ega-archive.org | https://schema.org/Dataset | 7,431 |
| tess.elixir-europe.org | https://schema.org/Organization | 7,110 |
| prosite.expasy.org | https://bioschemas.org/Taxon | 6,796 |
| prosite.expasy.org | https://schema.org/ScholarlyArticle | 6,681 |
| disprot.org | https://schema.org/DefinedTerm | 6,599 |
| fairsharing.org | https://schema.org/Dataset | 6,328 |
| prosite.expasy.org | https://schema.org/WebPage | 6,093 |
| prosite.expasy.org | https://schema.org/CreativeWork | 5,857 |
| fairsharing.org | https://schema.org/CreativeWork | 5,542 |
| mobidb.org | https://schema.org/PropertyValue | 4,486 |
| mobidb.org | https://schema.org/SequenceAnnotation | 4,486 |
| mobidb.org | https://schema.org/SequenceRange | 4,486 |
| ega-archive.org | https://schema.org/Study | 4,328 |
| disprot.org | https://schema.org/DefinedTermSet | 4,076 |
| mobidb.org | https://schema.org/DefinedTerm | 3,400 |
| mobidb.org | https://schema.org/DefinedTermSet | 3,400 |
| tess.elixir-europe.org | https://schema.org/Person | 3,298 |
| tess.elixir-europe.org | https://schema.org/CreativeWork | 2,876 |
| disprot.org | https://schema.org/ScholarlyArticle | 2,857 |
| tess.elixir-europe.org | https://schema.org/PeopleAudience | 2,475 |
| proteinensemble.org | https://schema.org/PropertyValue | 2,202 |
| mobidb.org | https://schema.org/CreativeWork | 2,073 |
| mobidb.org | https://schema.org/Protein | 2,073 |
| disprot.org | https://schema.org/CreativeWork | 2,038 |
| disprot.org | https://schema.org/Protein | 2,038 |
| proteinensemble.org | https://schema.org/DefinedTerm | 1,626 |
| pcddb.cryst.bbk.ac.uk | https://schema.org/Organization | 1,402 |
| pcddb.cryst.bbk.ac.uk | https://schema.org/DataDownload | 1,394 |
| prosite.expasy.org | https://bioschemas.org/Protein | 1,376 |
| pcddb.cryst.bbk.ac.uk | https://schema.org/Dataset | 697 |
| pcddb.cryst.bbk.ac.uk | https://schema.org/Person | 697 |
| biopragmatics.github.io | https://schema.org/Dataset | 287 |
| www.france-bioinformatique.fr | https://schema.org/ListItem | 265 |
| proteinensemble.org | https://schema.org/Protein | 254 |
| proteinensemble.org | https://schema.org/SequenceAnnotation | 254 |
| proteinensemble.org | https://schema.org/SequenceRange | 254 |
| pairedomicsdata.bioinformatics.nl | https://schema.org/Person | 222 |
| www.ebi.ac.uk | http://xmlns.com/foaf/0.1/Image | 222 |
| proteinensemble.org | https://schema.org/CollectionPage | 187 |
| proteinensemble.org | https://schema.org/CreativeWork | 187 |
| proteinensemble.org | https://schema.org/DefinedTermSet | 187 |
| proteinensemble.org | https://schema.org/ItemList | 187 |
| proteinensemble.org | https://schema.org/ScholarlyArticle | 181 |
| pairedomicsdata.bioinformatics.nl | https://schema.org/ContactPoint | 148 |
| www.covid19dataportal.org | https://schema.org/Organization | 148 |
| www.covid19dataportal.org | https://schema.org/Dataset | 110 |
| www.france-bioinformatique.fr | https://schema.org/BreadcrumbList | 99 |
|  | https://schema.org/Dataset | 97 |
| test.intermine.org | https://schema.org/Protein | 97 |
| www.ebi.ac.uk | http://xmlns.com/foaf/0.1/Document | 91 |
|  | https://schema.org/Person | 90 |
| bgee.org | https://schema.org/Dataset | 87 |
| pairedomicsdata.bioinformatics.nl | https://schema.org/CreativeWork | 74 |
| pairedomicsdata.bioinformatics.nl | https://schema.org/DataCatalog | 74 |
| pairedomicsdata.bioinformatics.nl | https://schema.org/DataDownload | 74 |
| pairedomicsdata.bioinformatics.nl | https://schema.org/Dataset | 74 |
| pairedomicsdata.bioinformatics.nl | https://schema.org/Organization | 74 |
| www.ebi.ac.uk | http://rdfs.org/sioc/ns#Item | 59 |
| www.france-bioinformatique.fr | https://schema.org/Event | 40 |
| www.france-bioinformatique.fr | https://schema.org/Place | 40 |
| www.france-bioinformatique.fr | https://schema.org/contact | 40 |
| www.france-bioinformatique.fr | https://schema.org/hostInstitution | 40 |
| test.intermine.org | https://schema.org/Gene | 39 |
|  | https://bioschemas.org/Taxon | 29 |
| nanocommons.github.io | https://schema.org/ChemicalSubstance | 29 |
|  | https://schema.org/Organization | 27 |
| bridgedb.github.io | https://schema.org/DataDownload | 23 |
| bridgedb.github.io | https://schema.org/Dataset | 23 |
| www.covid19dataportal.org | https://schema.org/DataCatalog | 19 |
| www.uniprot.org | https://schema.org/Organization | 14 |
|  | https://schema.org/CreativeWork | 9 |
| nanocommons.github.io | https://schema.org/Organization | 9 |
| bgee.org | https://schema.org/Answer | 8 |
| bgee.org | https://schema.org/CreativeWork | 8 |
| bgee.org | https://schema.org/Question | 8 |
|  | https://schema.org/WebPage | 7 |
| nanocommons.github.io | https://schema.org/Dataset | 7 |
| nanocommons.github.io | https://schema.org/DataDownload | 6 |
|  | https://schema.org/DataCatalog | 5 |
| bgee.org | https://schema.org/Offer | 5 |
|  | https://schema.org/SearchAction | 4 |
| bgee.org | https://schema.org/SoftwareSourceCode | 4 |
| nanocommons.github.io | https://schema.org/CreativeWork | 4 |
|  | https://schema.org/EducationalOrganization | 3 |
|  | https://schema.org/NGO | 3 |
|  | https://schema.org/ScholarlyArticle | 3 |
| bgee.org | https://schema.org/WebApplication | 3 |
| biopragmatics.github.io | https://schema.org/Person | 3 |
| prosite.expasy.org | https://schema.org/Action | 3 |
|  | https://schema.org/CollegeOrUniversity | 2 |
|  | https://schema.org/WebSite | 2 |
| bgee.org | https://schema.org/SoftwareApplication | 2 |
| biopragmatics.github.io | https://schema.org/WebPage | 2 |
| bridgedb.github.io | https://schema.org/CreativeWork | 2 |
| www.uniprot.org | https://schema.org/GovernmentOrganization | 2 |
| www.uniprot.org | https://schema.org/NGO | 2 |
| www.uniprot.org | https://schema.org/WebPage | 2 |
|  | https://schema.org/GovernmentOrganization | 1 |
| bgee.org | https://schema.org/AboutPage | 1 |
| bgee.org | https://schema.org/FAQPage | 1 |
| biopragmatics.github.io | https://schema.org/WebSite | 1 |
| bridgedb.github.io | https://schema.org/SoftwareApplication | 1 |
| bridgedb.github.io | https://schema.org/WebPage | 1 |
| bridgedb.github.io | https://schema.org/WebSite | 1 |
| massbank.eu | https://schema.org/DataCatalog | 1 |
| massbank.eu | https://schema.org/ScholarlyArticle | 1 |
| nanocommons.github.io | https://schema.org/DataCatalog | 1 |
| nanocommons.github.io | https://schema.org/Person | 1 |
| nanocommons.github.io | https://schema.org/URL | 1 |
| nanocommons.github.io | https://schema.org/WebSite | 1 |
| prosite.expasy.org | https://schema.org/DigitalDocument | 1 |
| prosite.expasy.org | https://schema.org/SearchAction | 1 |
| www.metanetx.org | https://schema.org/SoftwareApplication | 1 |
| www.uniprot.org | https://schema.org/Dataset | 1 |
| www.uniprot.org | https://schema.org/QAPage | 1 |

## Connectivity of the Data

We were interested to gain some insight as to how connected the data was both internally, and how many points where it would link up with other knowledge graphs. The queries in this section focus on the connectedness of the data.

We first investigated the number of nodes that only contained incoming edges. We report the total number of object nodes there are (excluding literals), and the number of edge IRIs, i.e. those that only have incoming properties. Only 4.65% of the nodes only contain incoming edges.

| Object IRIs | Edge IRIs |
| ---: | ---: |
| 2,057,094 | 95,610 |

We then investigated the number of outgoing links per class. We report here the top 20 results.



\begin{longtable}{p{.5\textwidth}p{.25\textwidth}r}
    \hline
        s & class & nb\_out\_edges \\ \hline
        \url{https://bioschemas.org/crawl/v1/bgee/?page=gene\&amp;gene\_id=ENSBTAG00000027937/20211110/90020/bgee.org/?page=gene\&amp;gene\_id=ENSBTAG00000027937/1779564251} & \url{https://bioschemas.org/Gene} & 856 \\
        \url{https://www.metanetx.org/chem\_info/MNXM1944} & \url{https://schema.org/MolecularEntity} & 654 \\
        \url{https://doi.org/10.15468/hb9rjv} & \url{https://schema.org/Dataset} & 594 \\
        \url{https://bioschemas.org/crawl/v1/bgee/?page=gene\&amp;gene\_id=ENSBTAG00000043564/20211110/94715/bgee.org/?page=gene\&amp;gene\_id=ENSBTAG00000043564/1772156424} & \url{https://bioschemas.org/Gene} & 519 \\
        \url{https://bioschemas.org/crawl/v1/bgee/?page=gene\&amp;gene\_id=ENSBTAG00000043584/20211110/94734/bgee.org/?page=gene\&amp;gene\_id=ENSBTAG00000043584/2022662406} & \url{https://bioschemas.org/Gene} & 474 \\
        \url{https://doi.org/10.15468/m5vrza} & \url{https://schema.org/Dataset} & 406 \\
        \url{http://www.ebi.ac.uk/pdbe/about/past-events} & \url{http://rdfs.org/sioc/ns\#Item} & 346 \\
        \url{http://www.ebi.ac.uk/pdbe/about/past-events} & \url{http://xmlns.com/foaf/0.1/Document} & 346 \\
        \url{https://doi.org/10.15468/vmf5ye} & \url{https://schema.org/Dataset} & 296 \\
        \url{https://bioschemas.org/crawl/v1/bgee/?page=gene\&amp;gene\_id=ENSBTAG00000043559/20211110/94710/bgee.org/?page=gene\&amp;gene\_id=ENSBTAG00000043559/1377128066} & \url{https://bioschemas.org/Gene} & 292 \\
        \url{https://doi.org/10.5281/zenodo.291971} & \url{https://schema.org/Dataset} & 289 \\
        \url{https://bioschemas.org/crawl/v1/bgee/?page=gene\&amp;gene\_id=ENSBTAG00000043546/20211110/94697/bgee.org/?page=gene\&amp;gene\_id=ENSBTAG00000043546/1804549344} & \url{https://bioschemas.org/Gene} & 284 \\
        \url{https://doi.org/10.15472/hy9nif} & \url{https://schema.org/Dataset} & 282 \\
        \url{https://bioschemas.org/crawl/v1/bgee/?page=gene\&amp;gene\_id=ENSBTAG00000043550/20211110/94701/bgee.org/?page=gene\&amp;gene\_id=ENSBTAG00000043550/1476242225} & \url{https://bioschemas.org/Gene} & 269 \\
        \url{https://www.metanetx.org/chem\_info/MNXM383} & \url{https://schema.org/MolecularEntity} & 264 \\
        \url{https://bioschemas.org/crawl/v1/bgee/?page=gene\&amp;gene\_id=ENSBTAG00000043577/20211110/94727/bgee.org/?page=gene\&amp;gene\_id=ENSBTAG00000043577/1610681495} & \url{https://bioschemas.org/Gene} & 261 \\
        \url{https://bioschemas.org/crawl/v1/bgee/?page=gene\&amp;gene\_id=ENSBTAG00000043556/20211110/94707/bgee.org/?page=gene\&amp;gene\_id=ENSBTAG00000043556/1277162978} & \url{https://bioschemas.org/Gene} & 240 \\
        \url{https://bioschemas.org/crawl/v1/bgee/?page=gene\&amp;gene\_id=ENSBTAG00000043568/20211110/94719/bgee.org/?page=gene\&amp;gene\_id=ENSBTAG00000043568/2065005542} & \url{https://bioschemas.org/Gene} & 235 \\
        \url{https://bioschemas.org/crawl/v1/bgee/?page=gene\&amp;gene\_id=ENSBTAG00000043560/20211110/94711/bgee.org/?page=gene\&amp;gene\_id=ENSBTAG00000043560/1818154049} & \url{https://bioschemas.org/Gene} & 229 \\ \hline

\end{longtable}

# Discussion

Through this Hackathon project we have demonstrated that it is possible to harvest Bioschemas markup from a number of sites and load them into a triplestore. However, this has revealed a number of challenges that need to be resolved.

Harvesting content from a whole site is very time consuming, particularly for dynamic sites. Harvesting requires visiting each page with markup in turn and extracting the markup. In the case of dynamic sites the content needs to be rendered before it can be extracted. Most of the sites that could be completed harvested did not contain data content beyond Dataset and DataCatalog.

The quality of deployed markup is very problematic. As reported above, a number of sites could not be harvested due to issues with their sitemap. Of those that could be harvested, a number of pages were not harvested due to issues with the markup contained within them, e.g. inclusion of non-escaped characters within strings was a common error. Even the markup that could be extracted contained errors, e.g. the use of different namespaces for the declaration of the Gene type as identified in the Instances per Bioschemas Class query. This highlights the need for a Bioschemas validator capable of both syntactic and semantic checking (see the data validation outputs of [Project 29](https://github.com/elixir-europe/biohackathon-projects-2021/tree/main/projects/29/data-validation)).

# Future work

The next steps for this work would be to improve the robustness of the data harvesting pipeline, including automating the manual steps of iterating over an index of sitemap files; merging individual files for each harvested page into a single file per subsitemap; and loading the harvested data into the triplestore.

The use of data dumps for sites should be considered to eliminate the need for data harvesting, which is a costly process for both data producers -- who have to have sufficient bandwidth and compute resources to serve the content -- and data consumers -- who have to have sufficient compute resources to retrieve, render, and extract the content from each page.

# Jupyter notebooks, GitHub repositories and data repositories

* GitHub repository: https://github.com/BioSchemas/bioschemas-data-harvesting
* Jupyter Notebook: https://github.com/BioSchemas/bioschemas-data-harvesting/blob/main/AnalysisQueries.ipynb
    * MyBinder launch: https://mybinder.org/v2/gh/BioSchemas/bioschemas-data-harvesting/HEAD?labpath=AnalysisQueries.ipynb
* SPARQL Endpoint: https://swel.macs.hw.ac.uk/data/repositories/bioschemas
  * Snorql Extended Interface: https://swel.macs.hw.ac.uk/bioschemas/
* Data download director: https://swel.macs.hw.ac.uk/bioschemas-data/

# Acknowledgements

This work was done during the BioHackathon Europe 2021 organised by ELIXIR and run in November 2021. We wish to thank the organizers and supporters of the Biohackathon Europe 2021 for offering the venue for improving Bioschemas community efforts.

# References
