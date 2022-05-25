# Project 1: Alignment of food and nutrition study data

## Abstract

Ontologies are widely used to make biological knowledge FAIR. There are many examples of ontologies and data standards used across the different ELIXIR communities, such as MONDO, used to harmonise knowledge about diseases; the Experimental Factor Ontology (EFO) and the Human Cell Atlas ontology (HCAO), application ontologies used to integrate data from biomedical studies; and the SBOL standard used by the microbial biotechnology community.

A major challenge in the application of ontologies is that their formal terminology does not always match the language in common use by practitioners. This is problematic for two reasons: (1) when curating data (e.g. from studies), text strings are often different from the ontology terms to which they must be mapped; and (2) knowledge described using ontology terms does not always match the terminology in common use.

Tools to address (1) include ZOOMA and OntoString developed at EMBL-EBI for the Human Cell Atlas Data Coordination Platform and EOSCLife, which use existing manually curated mappings to automatically suggest mappings for future strings. However, (2) is less explored. Some ontologies, such as the Human Phenotype Ontology (HPO), are beginning to provide “layperson” synonyms for terms. However, this approach is not standardised, and lacks support in ontology tooling including the Ontology Lookup Service (OLS).

In this proposal, we aim to explore this problem from two different perspectives. First, we will implement support in OLS for alternate terminologies, and the ability to choose which subset of synonyms to display in the website and API. Secondly, we will demonstrate the applicability of this new functionality in the Microbial Biotechnology ELIXIR community, which does not yet have an ontology for common end-user terminology. We will therefore disseminate ontology best practices from the biomedical community to add a MB application ontology, with mappings to alternate terminologies, to the OLS.

## Topics

Data Platform
Federated Human Data
Interoperability Platform
Metabolomics
Tools Platform

**Project Number:** 1

### Lead(s)

Duygu Dede Sener
d.dedesener@maastrichtuniversity.nl

## Expected outcomes

The infrastructure for alignment of existing data, repositories, tools and services

## Expected audience

We invite participants in the knowledge of:
FAIR (Findable, Accessible, Interoperable, Reusable) data principles
Ontology
Resource/database specific knowledge

**Number of expected hacking days**: 2

