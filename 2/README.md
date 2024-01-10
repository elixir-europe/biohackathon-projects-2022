# Project 2: APICURON integration with curation databases

## Abstract

The contribution and effort of biocurators is extremely difficult to attribute and quantify. APICURON (https://apicuron.org) is a web server that provides biological databases and organizations with a real-time automatic tracking system of biocuration activities. Registered resources submit biocuration events and the APICURON web server calculates achievements (medals, badges) and leaderboards on the fly. Results are stored and served through a public API and the APICURON website. APICURON aims at promoting engagement and certifying biocuration CVs, to this end it is already connected with ORCID to automatically propagate badges and achievements to ORCID user profiles.
APICURON database schema is extremely simple and lightweight, however, member databases that want to push data, need to generate metadata representing curation activities periodically and in a well defined JSON document. This entails member databases being able to: i) compare different versions of a curated entry; ii) retrieve the curator ORCID; iii) reliably assign a timestamp; and iv) schedule automatic execution of the submission task, including authentication and interaction with the APICURON API. Each of these points can be problematic for different reasons, in particular for those databases not properly implementing versioning. Also, when comparing different versions of the same entry it is necessary to identify changes associated with curation activity and map those changes to a standard vocabulary.
APICURON is already supported by 2022-APICURON implementation study of the Data Platform and well connected with the International Society for Biocuration. A core of early adopters’ curation databases (DisProt, PED, Pfam, Rfam, IntAct, SABIO-RK, Reactome, PomBase, SILVA, BioModels) are already connecting to APICURON. This biohackathon will focus on the implementation and testing of software pipelines for the extraction and submission of curation metadata to APICURON. The effort will be used to revise the relevant documentation in order to simplify integration of other databases.

## Topics

Data Platform
Interoperability Platform
Tools Platform

**Project Number:** 2

### Lead(s)

Adel Bouhraoua, kameleddineadel.bouhraoua@studenti.unipd.it

## Expected outcomes

Biohackathon outcomes
- Report about versioning strategies in curation databases 
- Workflows for the extraction of curation activities. One for each participating database
- Server client (software) to test metadata submission to APICURON 
- Guidelines about how to connect a curation resource to APICURON

Long term outcomes
- “Sandbox” APICURON server for testing purposes (3 months)
- Integration of database versioning strategies into APICURON guidelines (6 months)
- New curation databases automatically pushing data into APICURON (12 months)
- Definition of an ontology for curation activities, and/or extention of the Contributor Role Ontology (12 months)

## Expected audience

Database maintainers with knowledge about versioning, interaction with API, authentication technology

**Number of expected hacking days**: 4

