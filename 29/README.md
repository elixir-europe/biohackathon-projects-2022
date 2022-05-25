# Project 29: Supporting federated secure workflows and analysis using WfExS-backend

## Abstract

Human data is subject to ethical, legal, and sociological issues (ELSI), e.g. EU General Data
Protection Regulations (GDPR), imposing restrictions on the data access and mobilisation.
These genomic and phenotypic datasets are required to improve the understanding of the
genetic basis of disease, supporting the delivery of personalised medicine to patients.
This project links work in the ELIXIR Compute and Tools platforms with requirements from the
Federated Human Data and Rare Diseases communities to build, validate, and deploy data
analysis workflows across federated secure computational facilities, driven by 1+MG use
cases.
The WfExS-backend (https://github.com/inab/WfExS-backend) is a high-level workflow
execution software, fetching and materialising all elements needed to run a workflow: the
workflow, engine, software containers and inputs. It creates and consumes RO-Crates,
focusing on the interconnection of research infrastructures for handling sensitive human data.
WfExS delegates workflow execution to existing workflow engines (Nextflow and cwltool) and
is designed to facilitate secure and reproducible executions to promote analysis reproducibility
and replicability. Secure executions are achieved using FUSE encrypted directories for
non-disclosable inputs, intermediate results and output files.
RO-Crate representations of workflow executions are an element of knowledge transfer
between repeated executions. WfExS-backend stores all the gathered execution details,
output metadata and execution provenance in the output RO-Crate achieving reproducible
executions. Execution results are encrypted with crypt4gh and safely moved outside the
execution environments.
Documentation and demonstration of these tools, utilising synthetic data, to the human data
communities (HDCs), while bringing HDCs feedback to the compute and tools platform
facilitates uptake of these standards supporting interoperability.
Future developments focus on: secure data export procedures; supporting 1+MG use cases
and genomics infrastructure; supporting other workflow engines, e.g. SnakeMake and Galaxy;
supporting other containerisation technologies; supporting additional secure execution
scenarios; supporting additional workflows and data providers.

## Topics

Compute Platform, Federated Human Data, Interoperability Platform, Rare Disease, Tools
Platform, SW Containers, Benchmarking

**Project Number:** 29

### Lead(s)

Dylan Spalding dylan.spalding@csc.fi
José Mª Fernández (jose.m.fernandez@bsc.es)
Laura Rodríguez-Navas (laura.rodriguez@bsc.es)

## Expected outcomes

1. Giving WfExS-backend a wider end user and developer base, and supporting the 1+MG
use cases, including a draft container / workflow or recipe and associated documentation.
2. Gap analysis identifying tools and services that are required, and adding support to
additional workflow execution engines.
3. Testing WfExS-backend in additional workflow execution scenarios.
4. Improving generated Workflow Execution RO-Crate

## Expected audience

People with snakemake or planemo / Galaxy experience.
People with interesting Nextflow, CWL, snakemake or other workflows, hopefully already
available in a git repository.
People with interesting use cases, such as 1+MG use case experts or representatives from
FHD or RD communities, with existing or developing workflows which can be described in a
workflow language, where the inputs are available through permanent identifiers, and those
inputs can be fetched, either openly or through authentication.
Members or representatives of the compute and tools platforms, and federated human data
community.

**Number of expected hacking days**: 4

