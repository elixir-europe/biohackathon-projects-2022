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
  - [VGP longform tutorial (step-by-step, i.e., not using workflows, meant to be intro to genome assembly)](https://training.galaxyproject.org/training-material//topics/assembly/tutorials/vgp_genome_assembly/tutorial.html)
  - [VGP short tutorial (workflow-focused)](https://training.galaxyproject.org/training-material/topics/assembly/tutorials/vgp_workflow_training/tutorial.html)
  - in-development workflows housed on [Delphine's github](https://github.com/Delphine-L/iwc/tree/VGP/workflows/VGP-assembly-v2)
- Wrapping tools relevant for genome assembly/annotation for the Galaxy platform
  - ~~[BandageNG](https://github.com/asl/BandageNG): no conda package~~ ✔️ in bioconda!
  - ~~[InstaGRAAL](https://github.com/koszullab/instaGRAAL): no conda package~~ ✔️ in Galaxy!
  - ~~[FASTK](https://github.com/thegenemyers/FASTK/issues/10): not sure if conda package is up-to-date?~~ ✔️ in bioconda!
  - [MultiQC](https://github.com/ewels/MultiQC) integrations: PRs open for [gfastats](https://github.com/ewels/MultiQC/pull/1699) and other [pipeline QC](https://github.com/ewels/MultiQC/pull/1641)
  - ~~[NextDenovo](https://github.com/Nextomics/NextDenovo): no conda package~~ ✔️ in Bioconda!
  - [FCS](https://github.com/ncbi/fcs): Docker/Singularity
  - ~~[Ratatosk](https://github.com/DecodeGenetics/Ratatosk): no conda package~~ ✔️ in Bioconda!
  - open to ideas for other tools for assembly, assembly QC, and annotation! (e.g., https://github.com/nadegeguiglielmoni/genome_assembly_tools)

## Expected audience

- Researchers working on genome assembly, annotation, or quality control
- Participants interested in creating Galaxy tool wrappers / bioconda packages

**Number of expected hacking days**: 4

