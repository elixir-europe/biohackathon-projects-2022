# Project 6: Building a robust and reproducible assembly and annotation pipeline for non-model eukaryote genomes

## Abstract

The European Reference Genome Atlas (ERGA) has gathered a wide community to generate reference genome assemblies for diverse eukaryote species. To this end, sequencing platforms have already generated large datasets for several species, which now require extensive bioinformatic analyses. Our project aims to build an assembly and annotation pipeline, in collaboration with the Vertebrate Genomes Project (VGP), to enable newcomers to the field to integrate heterogeneous sequencing datasets (PacBio HiFi, Nanopore, Illumina and Hi-C reads) and generate high-quality chromosome-level assemblies and gene sets. In addition, we will test new tools to identify efficient assembly and annotation strategies. Implementing this pipeline within the Galaxy framework will help streamlining the process, while also facilitating its access to biologists with limited access to High Performance Computing resources, as eukaryote genomes typically require large computational resources. This pipeline will also serve as a tutorial to convey technical skills and good practices in genome assembly and annotation. Working to establish these pipelines will also be vital to this community as they will help serve as standardized and reproducible protocols.

## Topics

Biodiversity
Galaxy
Tools Platform

**Project Number:** 6

### Lead(s)

- Nadège Guiglielmoni (nguiglie@uni-koeln.de)
- Linelle Abueg (labueg@rockefeller.edu)

## Expected outcomes

- An improved pipeline for assembly of diverse eukaryotic genomes
- A Galaxy workflow for genome annotation
- A committed genome assembly community stemming from the interaction between ERGA, Galaxy and the VGP
- Extended tutorials and guidelines for high-quality genome assembly using Galaxy workflows
- Wrapping tools relevant for genome assembly/annotation for the Galaxy platform
  - [BandageNG](https://github.com/asl/BandageNG): no conda package
  - [InstaGRAAL](https://github.com/koszullab/instaGRAAL): no conda package
  - [FASTK](https://github.com/thegenemyers/FASTK/issues/10): not sure if conda package is up-to-date?
  - [MultiQC](https://github.com/ewels/MultiQC) integrations: PRs open for [gfastats](https://github.com/ewels/MultiQC/pull/1699) and other [pipeline QC](https://github.com/ewels/MultiQC/pull/1641)
  - open to ideas for other tools for assembly, assembly QC, and annotation! 

## Expected audience

- Researchers working on genome assembly, annotation, or quality control
- Participants interested in creating Galaxy tool wrappers / bioconda packages

**Number of expected hacking days**: 4

