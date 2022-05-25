# Project 25: Scientific and technical enhancement of bioinformatics software metadata using the Tools Ecosystem open infrastructure

## Abstract

The Tools Ecosystem is a centralized repository for the open and transparent exchange of metadata about software tools and services in Bioinformatics and Life Sciences.
It serves as the foundation for the sustainability of the diverse Tools Platform services, and for the interoperability between all these essential services (bio.tools, BioContainers, OpenEBench, Bioconda, WorkflowHub, usegalaxy.eu) and related resources outside of the ELIXIR Tools Platform (e.g. Bioschemas).

The goal of this project will be to cross-compare and analyze the metadata centralized in the Tools Ecosystem to maintain high quality descriptions. In order to achieve these goals we need to design tools and processes that detect curation bottlenecks, perform rigorous data cross-validation and generate detailed reporting about potential issues and actionable items.

Multiple strategies will be explored:
- Comparison of the functional profiles of bio.tools entries with the corresponding semantic constraints defined in EDAM. Develop software to identify and report on inconsistencies between resources.
- Comparison of the metadata defining a software tool with the knowledge extracted from publications that cite it, as well as the workflows that use it.

Beyond the immediate improvement of the metadata, we plan to use the results of these analyses in order to:
- Automate relevant analyses using continuous integration mechanisms (extending previous and current work in EDAM and the Tools Ecosystem)
- Improve curation user interfaces to reduce the risk of annotation errors.
- Provide high quality functional tool profiles to be used in the context of workflow annotation

Another important goal is to provide onboarding of and support for scientific communities joining the Biohackathon.

Given the nature of the data we use in this project, we will be working in close collaboration with the project "Enhance RDM in Galaxy by utilizing RO-Crates", who will also be leveraging workflow and software metadata from the same resources.

## Topics

Bioschemas
Federated Human Data
Galaxy
Interoperability Platform
Tools Platform

**Project Number:** 25

### Lead(s)

Hans Ienasescu (haiiu@dtu.dk)
Lucie Lamothe (lucie.lamothe@france-bioinformatique.fr)

## Expected outcomes

By the end of the BioHackathon week:
- Results of the cross-analysis of bioinformatics tools, highlighting potential inconsistencies or annotation gaps between the different resources, and suggesting annotation improvements (missing or more specific terms) for registry curators.  
- Software code to run the analyses mentioned
- Prototypes for CI tasks that automate the analyses
- Initiate contact with scientific communities and perform actions to ensure future onboarding and support (e.g. identify gaps and EDAM, bio.tools, WorkflowHub) 

Within 3 months of the end of the Biohackathon:
- Production-ready code and CI tasks automating the analyses to improve the monitoring of the Tools Ecosystem
- Improvements to the bio.tools curation UI, if analysis results reveal that such modifications might help or improve the annotation quality.
- New concepts in EDAM, tools in bio.tools , workflows in WorkflowHub created by the scientific communities

## Expected audience

- Ontology specialists
- Workflow specialists
- Python programmers
- Data analysts
- Bioinformatics Software providers/packagers
- Scientific community domain experts

**Number of expected hacking days**: 4

