# Project 35: Workflow Execution Service to help the quality improvement of published workflows

## Abstract

The explosion of the amount of biological data and the rise of cloud computing have been increasing the demand for distributed data analysis. GA4GH Cloud Workstream has developed the Workflow Execution Service (WES) Standard, which helps realize "Bring workflow to data."

To support researchers to utilize the published workflow resources, we developed Sapporo, a production-ready implementation of the WES. Sapporo is a unique WES implementation that supports multiple workflow languages. The DDBJ Center, a counterpart of NCBI and EBI in Japan, provides Sapporo-based WES to researchers in Japan. In the past BioHackathon Europe, we could solve the interoperability issues of Sapporo with Elixir WES. Improvements in various WES implementations will make it easier to share and perform data analysis across organizations and countries.

However, there are unsolved issues in the quality control of shared workflows, which causes problems to the operation of WES. The workflow quality includes; the correctness of workflow language syntax, test details, and sufficiency of workflow metadata, such as license, version, and maintainers. In many public workflow registries, quality control of registered workflows depends on the registry maintainers, which undermines the scalability of the registry. Therefore, we developed Yevis, a TRS-compatible and GitHub-based system that supports building a registry with automated quality control. Yevis uses Sapporo as an on-demand instance to run workflow testing, which assures the portability of the testing environment.

In BioHackathon Europe 2022, we aim to extend the functionalities of Yevis and Sapporo to help the quality control of the existing workflow registries, such as WorkflowHub. We also would like to welcome new contributors and get feedback on the products from the BioHackathon participants. By collaborating with the other BioHackathon participants, we would like to contribute to promoting the publication of well-maintained workflows.

## Topics

Compute Platfrom
GA4GH partnership
Tools Platform

**Project Number:** 35

### Lead(s)

Tazro Ohta t.ohta@dbcls.rois.ac.jp
Hirotaka Suetake suehiro619@gmail.com

## Expected outcomes

The community consensus on quality validation criteria of published workflows
The systematic method to automatically validate published workflows by WES
Public workflows verified by the system
More participants familiar with WES and other cloud workflow solutions
Novel collaboration network between the workflow users and the developers

## Expected audience

Workflow composers (who write workflow language)
Workflow platform developers (who develop workflow language and runtime)
Workflow registry maintainers
On-premise/Cloud infrastructure administrators

**Number of expected hacking days**: 4

