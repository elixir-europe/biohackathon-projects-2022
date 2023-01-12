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
  - name:  
    orcid:
    affiliation: 
 affiliations:
  - name: Luxembourg Center for Systems Biomedicine (LCSB), University of Luxembourg, Esch-sur-Alzette, Luxembourg
    index: 1
  - name: Centre for Bioinformatics, University of Oslo, ELIXIR Norway
    index: 2
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

...

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
