# Project 19: Nightingale 4.0 - Reusable web components for accelerating end-users access to tools platform metadata


<div style="text-align: center;">
  <img src="https://raw.githubusercontent.com/ebi-webcomponents/nightingale/nightingale-linegraph-track-ts/resources/nightingale_logo_text.svg" alt="Nightingale" />
</div>

## Abstract

Nightingale is an open source library of visual components for sequence information. Most of the components are centred around the idea of a protein feature viewer. They have been adopted by recognised projects such as UniProt, InterPro, PDB, and OpenTargets. 
The library includes other biological related components such as the structure viewer that wraps the molstar viewer, the textarea used in HmmerWeb for DNA sequence search, and the heatmap used in InterPro to display confidence levels of RoseTTAFold models.

During biohackathon 2021 we made considerable progress on refactoring the core of the library, rewriting it in typescript and using LitElements as a framework. This work has continued since then, and a release-candidate of the core will be ready by this year’s biohackathon. 

OpenEBench (https://openebench.bsc.es) is the ELIXIR gateway to benchmarking evaluations and technical monitoring for bioinformatics tools, web servers, and workflows. It offers reusable UI components (widgets) for data visualization and representation, to be placed in other web infrastructures, distributed as simple HTML snippets/npm packages along with a JavaScript file.
An objective of this year's biohackathon is to migrate all Nightingalethe core components into using the new core, taking advantage of its typed system and new architecture, which should simplify the components' code and make it more stable and extensible. Furthermore this objective includes updating our showcase website using the storybook library, for which a first prototype was created in external collaboration during last year’s hackathon.

Another objective is to create a prototypic implementation of the OpenEBench widget library incorporating the state-of-the-art technology core Nightingale is using, sharing their past learnings, and resolving a range of down-sides, enabling re-usability outside of the OpenEBench ecosystem.
Finally we aim to set up guidelines and processes of how to develop, and review, new re-usable web widgets/components in the community.


## Topics

* Tools platform
*  Data Platform
* Proteomics
* Human Copy Number Variation
* Intrinsically Disordered Community
* Machine learning


**Project Number:** 19

### Lead(s)

dominik.bruchner@bsc.es
gsalazar@ebi.ac.uk

## Expected outcomes

Re-factor the core nightingale components:
* Track
* InterPro Track
* Sequence
* Coloured Sequence
* Variation
* Structure
* Navigation
* Manager
* Interaction viewer
* Filter
* Variation Graph
* Line Graph
* Data table
* Tooltip
* Alignments
* Heatmap
* NightingaleSunburst
* Textarea Sequence
* Playground Area

Increase the test coverage of the new Nightingale core.

Make Nightingale core reusable outside of the scope of its core components. This will allow third parties to take advantage of the core functionality, and will make the process of including external components into the library simpler. 

We expect the following outcomes in the shared core functionality collaborating with the OpenEBench visualization library project:
* Shared technology foundation between Nightingale and OpenEBench Widgets (separating the Nightingale core)
* Prototypic implementation of at least one widget (Reusable; API independent; Directly usable as WebComponents)
* Prototypic implementation of widget library storybook, showcasing usage and re-usage
* Setting up a work-flow, and guidelines for further collaboration and developing new components

#### Common Output:
Guidelines and processes of how to develop, and review, new re-usable web widgets/components in the community.


## Expected audience

Ideally web developers with experience in typescript and web components to contribute to the re-factoring. 

Other developers are welcome to contribute by using and/or testing nightingale components.

Users of biological visualisations who want to share their feedback. 

Data visualization users (defining requirements)

Software architects


**Number of expected hacking days**: 4

