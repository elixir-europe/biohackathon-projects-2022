---
title: 'BioHackEU22 Report for Project 31: The What & How in data management: Improving connectivity between RDMkit and FAIR Cookbook'
title_short: 'BioHackEU22 #31: RDMkit and FAIR Cookbook'
tags:
  - FAIR
  - data management
  - RDMkit
  - FAIR Cookbook
authors:
  - name: Danielle Welter
    orcid: 0000-0003-1058-2668
    affiliation: 1
  - name: Marina Popleteeva 
    orcid:
    affiliation: 1
  - name: Nazeefa Fatima 
    orcid: 0000-0001-7791-4984 
    affiliation: 2
  - name:  Laura Portell Silva
    orcid: 0000-0003-0390-3208
    affiliation: 3
 affiliations:
  - name: Luxembourg Center for Systems Biomedicine (LCSB), University of Luxembourg, Esch-sur-Alzette, Luxembourg
    index: 1
  - name: Centre for Bioinformatics, University of Oslo, ELIXIR Norway
    index: 2
  - name: Barcelona Supercomputing Center (BSC), ELIXIR Spain
    index: 3
date: 11 November 2022
cito-bibliography: paper.bib
event: BH22EU
biohackathon_name: "BioHackathon Europe 2022"
biohackathon_url:   "https://biohackathon-europe.org/index.html"
biohackathon_location: "Paris, France, 2022"
group: Project 31
<!--URL to project git repo --- should contain paper.md-->
git_url: https://github.com/elixir-europe/biohackathon-projects-2022/tree/main/projects/31/bhxiv
<!--This is the short authors description that is used at the bottom of the generated paper.-->
authors_short: Danielle Welter \emph{et al.}
---
<!--

The paper.md, bibtex and figure file can be found in this repo:

  https://github.com/journal-of-research-objects/Example-BioHackrXiv-Paper

To modify, please clone the repo. You can generate PDF of the paper by
pasting above link (or yours) in

  http://biohackrxiv.genenetwork.org/

-->

# Introduction

Biohackathon project 31 "The What & How in data management: Improving connectivity between RDMkit and FAIR Cookbook" was proposed by the joint editorial board of the RDMkit [@elixir_research_2021] and FAIR Cookbook [@philippe_rocca_serra_2022_7156792]. The aims of the project were 3-fold:

- Improve the technical interoperability between RDMkit & FAIR Cookbook
- Clarify and illustrate the ELIXIR research data management ecosystem for users
- Perform a gap analysis of the ecosystem for targeted content improvements.


# Technical integration

<!--Bert or Philippe-->

- YAML file
- potential PIDs for RDMK
- automatic metadata extraction


# ELIXIR Research Data Management Ecosystem

A major focus of this project was to generate an improved illustration of the wider ELIXIR Research Data Management (RDM) Ecosystem, its components and how they fit together. While each of the components of the RDM Ecosystem - the RDMkit, the FAIR Cookbook, FAIRsharing, the Data Stewardship Wizard (DSW), bio.tools and TeSS - are all fairly well-documented in their own right, users from outside of ELIXIR or new to research data management often find it difficult to find all the resources or identify which ones best serve their needs. In order to address this gap, we started the process of creating a single unified presentation page to the ELIXIR RDM Ecosystem, highlighting all its components. In particular, we identified a series of representative journeys that users might take to navigate between the resources in order to address common RDM problems. 

## User journeys
<!--Nazeefa, Laura, Marina-->

The following 6 user journeys represent a non-exhaustive list of solutions to common research data management problems, making optimal use of all the ecosystem's resources and links between them. 

### User journey 1: How do I start with data management?

Find general guidance on RDM topics, including domain specific and task oriented topics at RDMkit. Once the considerations that apply are identified, RDMkit links you to step-by-step instructions in the FAIR Cookbook to help accomplish the tasks needed. Extended information and related resources about the mentioned standards in the FAIR Cookbook can be found in the links to FAIRsharing. In each RDMkit page, there are also links to TeSS with relevant training material about your topic of interest. TeSS also links to the bio.tools entry about bioinformatics tools related to the training material.

### User journey 2: How do I write a Data Management Plan?

Get an overview about what entails a Data Management Plan (DMP) in the Data Management Plan page in the RDMkit. In the table at the bottom, choose a tool to create DMPs like the Data Stewardship Wizard (DSW) to create a DMP. The DSW contains links back to the RDMkit to get specific guidelines that are needed to answer some of the questions. Also, the DSW has link to FAIRsharing to help you choose among repositories, standards and policies to adopt for your project. From RDMkit you can also navigate to TeSS to get DMP related training materials.

### User journey 3: How do I know if my data is sensitive?

Get an overview of what is sensitive data and get general guidance in the Sensitive data page in the RDMkit. Then, navigate to the Data protection page to find out how to protect sensitive data from an ethical and legal standpoint. From this page, follow the links to the FAIRcookbook to learn about how to represent sensitive data use conditions in machine-readable metadata format. It is also possible to learn about national regulations and policies in the National resources pages in the RDMkit.

### User journey 4: How do I handle my metadata?

Understand what to consider when managing the metadata for a project with the Documentation and metadata page in RDMkit. To explore further about the standards used in a specific domain as well as their connections, a link to FAIRsharing is provided. Also, several relevant FAIRcookbook recipes are linked from RDMkit. For example, it is possible to follow instructions on how to create a metadata profile, on how to use ontologies, or on how to create a data dictionary. The FAIRcookbook recipes also link back to RDMkit for broader context.

### User journey 5: How do I get extra guidance on how to deposit high-throughput data into EBI BioImage Archive?

Learn how to deposit data to the BioImage Archive by following the steps in the FAIRcookbook recipe. In the recipe general guidelines and tool assembly pages in the RDMkit are linked. There, guidelines in metadata and standards to use and to know how to prepare the data for deposition are described. The RDMkit links to TeSS to get training videos about metadata annotation for biological images.

### User journey 6: What existing data management resources I can use to create teaching material for making DMPs?

Explore how existing training materials address the topic of Data Management Planning e.g. through hands-on session, in a form of webinar, or a mix of theoretical and practical content. The Training eSupport System (TeSS), a training platform by ELIXIR, provides a collection of past/recent material used for courses, webinars, and so on. RDMkit is also a good starting point to find teaching resources (linked to TeSS) and information on DMP (that can be recommended source for training audience); there, information on current DMP-creating tools such as DSW is mentioned. Equally important aspect of creating a DMP is to be familiar with institutional guidelines, and those can be learned about via national resources available on RDMkit.

# Gap analysis
<!--Tooba, Rafael-->

As well as solving the technical problems around linking the RDMkit and the FAIR Cookbook, we performed a gap analysis of the content of the two resources. For this, we focussed on two aspects:

1. Missing links between existing pages;
2. Content gaps 

## Missing page links

We reviewed all recipes in the FAIR Cookbook and Task pages in the RDMkit to identify any pair of pages that should be cross-linked with each other but currently aren't. We also reviewed the RDMkit Domain pages as part of this exercise, although the decision was made early on that links between Domain pages and Cookbook recipes would only be acceptable if the recipe covers a problem specific to that domain. This was done to avoid linking overload for common tasks. For example, almost all RDMkit Domain pages mention annotation with ontologies and controlled vocabularies but crosslinking all of them with the Cookbook's domain agnostic recipe on best practices for ontology selection would generate a long list of poorly related links to the RDMkit on the Cookbook recipe's page, which would likely be very confusing to users. Links to Domain pages are therefore only included where a Cookbook recipe is entirely specific to the domain in question, such as the [Plant sciences domain page](https://rdmkit.elixir-europe.org/plant_sciences#relevant-tools-and-resources) cross-referencing the Cookbook recipe "[Plant genomic and genetic variation data submission to EMBL-EBI databases](https://w3id.org/faircookbook/FCB061)".

In total, we identified around a dozen additional links, which were recorded for addition to the YAML file from the technical implementation track. 

In addition to the review of the two core resources, RDMkit and FAIR Cookbook, we also paid attention to potential missing links between these resources and TeSS or DSW. This generate a further couple of action item, which were returned to the editorial team of the appropriate resource for action at a later time. 

## Content gaps

In addition to completing the cross-references between existing pages, we also reviewed the resources for content gaps where no Cookbook recipes exist to address problems highlighted in an RDMkit Task page, or a problem addressed in a Cookbook recipe that does not feature in the RDMkit. In total, we identified 4 potential new recipes for the FAIR Cookbook to address problems described in the RDMkit. Other gaps that were initially identified were deemed to be outside the remit of the FAIR Cookbook, such as problems pertaining to data management planning and project coordination, which are not directly relevant to the FAIR principles. 


  
  
# Discussion

<!--Danielle-->

We did what we did and it's good/bad because...


# Future work

<!--Rafael-->

Once we've recovered, we're going to do x, y and z.


## Acknowledgements

We thank the organisers of the BioHackathon Europe 2022 for travel support for some of the authors.

## References
