# Project 27: Streamlining data brokering from RDM platforms to ELIXIR Repositories

## Working Document

Please ensure you read the working document [here](https://github.com/elixir-europe/biohackathon-projects-2022/blob/main/27/working_doc.md) where you can find helpful links to additional material, information on test data files and the Biohackathon project agenda. 

## Abstract

Mobilizing data from data infrastructures to data deposition databases is an integral service that research data management (RDM) platforms could offer. However, brokering the heterogeneous mixture of scientific data requires systems that are compatible with the diverse (meta)data models of the different RDM platforms, and diverse submission routes of different target repositories.

The metadata management platform DataHub, an instance of the FAIRDOM-SEEK software, uses the well-established (Investigation Study Assay) framework to describe metadata.

This BioHackathon project will specifically focus on designing and implementing data brokering systems from DataHub, to ELIXIR Deposition Databases, starting with the European Nucleotide Archive (ENA). During this project we will establish which existing tools can be reused, which need to be adapted or whether new tools need to be developed for data brokering from DataHub.

We aim to make brokering tools more flexible, to support researchers and data stewards with metadata collection. We will also focus on increasing the sustainability and reducing the burden of maintenance of the tools, trying to limit dependencies on static reference files and hard coded variables.

The design and the implementation of tools during this project aim to provide easy to maintain and flexible solutions for data brokering that can be further developed to be applied to other data repositories, and other national or institutional data management platforms.

Finally, describing and implementing models for brokering data to ELIXIR Deposition Databases in this way, is one aim of ELIXIR-Converge (2020-2023), in Task 1.2.

## Topics

Data Platform
Interoperability Platform
Tools Platform

**Project Number:** 27

### Lead(s)

Flora D'Anna = flora.danna90@gmail.com, flora.danna@psb.vib-ugent.be
Zahra Waheed = zahra@ebi.ac.uk

## Expected outcomes

Expected outcomes for the week:

Unblock content agnostic conversion of ISA-JSON to ISA-Tab. 
Design and/or expand a tool to convert ISA-JSON to SRA xml schema with limited dependency on static configuration files and hard coded variables.
Design and/or implement a system for automatic update of configuration files needed to convert ISA-JSON to SRA xml schema, based on changes of the SRA schema.
Design and/or implement a method to submit metadata exported from DataHub (FAIRDOM-SEEK instance) in ISA-JSON format to ENA, including proof of concept for conversion of metadata exported from DataHub in ISA-JSON format to SRA xml schema.
Unblock ISA-Tab to SRA xml schema conversion using ISA tools or new solutions.


Long-term expected outcomes and timeframe in months:

M3: Adoption into other tools exporting ISA-JSON/Tab.
M6: Adoption of the ISA/ENA converter into the toolchain used by different FAIRDOM SEEK instances.
M12: Extension to various other relevant target formats e.g. PRIDE-XML and PAGE-JSON
M18: Successful conversion of ISA-JSON/Tab to the appropriate ISA formatted files required by each repository (BioSamples, BioStudies, Metabolights, ArrayExpress and PRIDE). 
M24: Successful end-to-end brokering from national and institutional data management platforms.

## Expected audience

Knowledge of: ISA model, ENA submission process, FAIRDOM-SEEK, coding skills- particularly Python, JSON and XML

**Number of expected hacking days**: 4

