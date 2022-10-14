# Project 16: Make your own or favourite software available on your cluster with EasyBuild/EESSI

## Abstract

[EasyBuild](https://easybuild.io/) is a community effort to develop a software build and installation framework that allows you to manage (scientific) software on High Performance Computing (HPC) systems in an efficient way. As its name suggests, EasyBuild makes software installation easy by automating builds, making previous builds reproducible, resolving dependencies, and retaining logs for traceability. It is also one of the components of the **European Environment for Scientific Software Installations** ([EESSI](https://www.eessi-hpc.org/)), a collaboration between different European HPC sites and industry partners, with the common goal to set up a shared repository of scientific software installations that can be used on a variety of operating systems and computer architectures. It can be applied in a full size HPC cluster, a cloud environment, a container or a personal workstation.

With the deluge of data in the genomics field (e.g., clinical data) and the concomitant development of new technologies, the number of data analysis software has exploded in recent years. The fields of bioinformatics and cheminformatics follow this same trend with ever more developments to optimize and parallelize analyses. The bioinformatics field is now the main provider of new software in EasyBuild. Developers of those tools are not always professional developers, and they do therefore not always follow best practices when releasing their software. As a result, many tools are complicated to install, making them ideal candidates for porting their installation to EasyBuild so that they become more easily accessible to end users.

We propose to introduce users to EasyBuild and EESSI, and to port new software to EasyBuild/EESSI (e.g., the participant’s own or favourite software), thereby making it available and discoverable to the entire EasyBuild community. In parallel we would like to build bridges between EESSI and Galaxy to make the scientific software more accessible to researchers in the domain.

## Topics

Compute Platfrom
EOSC-life
Galaxy
Tools Platform

**Project Number:** 16

### Lead(s)

Sébastien Moretti (sebastien.moretti@sib.swiss)
Kenneth Hoste (kenneth.hoste@ugent.be)
Alan O’Cais (alan.ocais@cecam.org)
Jurij Pečar (jurij.pecar@embl.de)
Elisabeth Ortega (elisabeth.ortega@hpcnow.com)

## Expected outcomes

- Extend domain software supported by EasyBuild (days to weeks).
- Introduce EasyBuild/EESSI to software developers and users (days to weeks).
- Create communication paths and technical bridges between EasyBuild/EESSI and Galaxy (mix both communities) (4-6 months).
- Evaluating performance of popular bioinformatics software that’s available through Galaxy, compare with installations provided via EasyBuild and EESSI (4-6 months).
- Software developers can maintain their own software in EasyBuild (weeks to years).

## Expected audience

- Software users.
- Software developers.
- Galaxy developers and users.
- The EasyBuild and the EESSI communities.

**Number of expected hacking days**: 4

## Useful links

- Getting started with EasyBuild: https://docs.easybuild.io/
- Getting started with EESSI: https://eessi.github.io/docs/
- EasyBuild Slack channel: https://easybuild.io/join-slack
- EESSI Slack channel: https://www.eessi-hpc.org/slack-channel/

## Other links

- EasyBuild homepage: https://easybuild.io/
- EasyBuild community: https://github.com/easybuilders
- EasyBuild last presentations: https://www.youtube.com/c/easybuilders
- EESSI homepage: https://www.eessi-hpc.org/
- EESSI at GitHub: https://github.com/EESSI
- Introductory talk on EESSI: https://archive.fosdem.org/2021/schedule/event/eessi
- EESSI paper: https://dx.doi.org/10.1002/spe.3075
