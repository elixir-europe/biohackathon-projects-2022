# Project 10: Enhance RDM in Galaxy and DSW by utilising RO-Crates

## Abstract

RO-Crate (https://doi.org/10.3233/DS-210053) is a generic packaging format containing datasets and their description using standards for FAIR Linked Data. Based on rich schema.org metadata, such datasets can be interpreted as workflow definitions, datasets, data associated with workflow invocations, inputs, outputs, etc.

The Galaxy workflow framework is handling all of those objects and supports users in the daily RDM. Integrating RO-Crate deeply into Galaxy and offering import and export options of various Galaxy objects as Research Objects will greatly standardize and improve the RDM in Galaxy and smoothen the UX as well as improving interoperability with other systems.

The low hanging fruit of this proposal is to add support for import/export of RO-Crates following its Workflow profiles. Those Crates should contain as much metadata as the Galaxy framework can provide. This includes workflow metadata such as Licence, Creator, CWL-abstract description, workflow history, contextually also references (DOIs, bio.tool IDs), EDAM terms, and formats of inputs/outputs of data processing of each step of the workflow.

Exports of History and Workflow Invocations need work on the corresponding RO-Crate profile and on the Galaxy codebase. RO-Crates already can be visualized, this would add a human-readable HTML rendering of the Galaxy export and metadata. Close collaboration between RO-Crate and Galaxy developers will speed up this development; the groundwork could be completed during the Biohackathon so that both Crates will be supported by the Galaxy 23.01 release.

Data Stewardship Wizard (DSW) is a tool for data management planning with focus on FAIR metrics, proper guidance and integration with other tools in the data stewardship domain. Thus, similarly to utilising RO-Crates in Galaxy, the import and export functionality would be highly beneficial for DSW in terms of promoting interoperability and FAIRness in general. Existing RO-Crates can be used to pre-fill specific parts of DMPs, and vice versa, RO-Crates can be created or initiated from a DMP. Such support of RO-Crates in DSW can lay a foundation for closer integration with Galaxy and potentially other ELIXIR Tools platform components. 

This project can benefit from collaborations with other Biohackathon projects discussed during the 2022 Tools Platform meeting such as “Scientific and technical enhancement of bioinformatics software metadata using the Tools Ecosystem open infrastructure” as it will also be leveraging workflow and software metadata from the same resources.


## Topics

"RO-Crate,
Research Object,
Metadata,
Workflow invocation,
Galaxy,
Data packaging,
Bioschemas,
Provenance, 
data management plan,
tool integration,
software development kit,
data import"

**Project Number:** 10

### Lead(s)

Ignacio Eguinoa (ignacio.eguinoa@psb.ugent.be)
Marek Suchánek (marek.suchanek@fit.cvut.cz), co-lead


## Expected outcomes

From the hackathon:
- Galaxy export of history, adding workflow invocation metadata following interoperable FAIR standards
- Improvements to specifications of RO-Crate and the Workflow/WorkflowRun provenance profiles
- Prototype of Galaxy import of RO-Crate into history
- Improvements to RO-Crate libraries (Python, Javascript, Ruby) - new languages welcome!
- Prototype embedding of resolved bio.tools/EDAM metadata as part of Galaxy export
- Mapping between RO-Crates and DSW Knowledge Model
- Import and export of RO-Crates functionality in DSW projects
- Analysis of direct DSW-Galaxy integration and implementation plan

Following the hackathon :
- Tighter collaboration between Galaxy, DSW, and RO-Crate developers
- Galaxy release (23.01) with RO-Crate support
- DSW-Galaxy integration based on analysis and plan
- Release new DSW features and updated content
- Documentation of FAIR data management with Galaxy, DSW, and RO-Crate, e.g. for RDMKit
- Further integration between UseGalaxy.eu, ELIXIR Tools platform components (bio.tools, WorkflowHub, EDAM) and EOSC-Life Tools Collaboratory (Life Monitor, WfExS)
- Report on new features, insights, and other outcomes of the hackathon published via BioHackrXiv


## Expected audience

- Data/Workflow Platform developers (e.g. Galaxy)
- Tool maintainers/packagers
- Metadata/ontology experts (e.g. Bioschemas, JSON-LD)
- Python developers
- Ruby developers
- Researchers producing galaxy histories/workflows

This topic involves partners from at least:
- ELIXIR-UK
- ELIXIR-BE
- ELIXIR-ES
- ELIXIR-DE

We will extend the virtual invitation to:
- Galaxy Europe developers
- RO-Crate community https://www.researchobject.org/ro-crate/community.html
- Workflow Run RO-Crate task force https://www.researchobject.org/workflow-run-crate/
- BioCompute object community https://biocomputeobject.org/
- Workflows Community Initiative (FAIR Computational Workflow working group) https://workflows.community/groups/fair/
Participants from related projects (incl. EOSC-Life, SYNTHESYS+, BY-COVID, RELIANCE) developing workflow provenance methods


**Number of expected hacking days**: 4 Days – due to virtual participation, hacking can extend into pre/post Biohackathon hacking days as we did in 2021.

## Related projects

* #5 [Bioschemas - Enabling profile updates through the Data Discovery Engine (DE)](https://github.com/elixir-europe/biohackathon-projects-2022/tree/main/5)
* #8 [Developing a lesson for the ELIXIR Software Management Plan](https://github.com/elixir-europe/biohackathon-projects-2022/tree/main/8)
* #17 [Metadata schemas supporting Linked Open Science (with a focus on reproducibility)](https://github.com/elixir-europe/biohackathon-projects-2022/tree/main/17)
* #22 [Plant data exchange and standard interoperability](https://github.com/elixir-europe/biohackathon-projects-2022/tree/main/22)
* #23 [Publishing and Consuming Schema.org DataFeeds](https://github.com/elixir-europe/biohackathon-projects-2022/tree/main/23)
* #28 [Support for the Common Workflow Language standard version 1.2 in Galaxy](https://github.com/elixir-europe/biohackathon-projects-2022/tree/main/28)
* #29 [Supporting federated secure workflows and analysis using WfExS-backend](https://github.com/elixir-europe/biohackathon-projects-2022/tree/main/29)
* #30 [The ELIXIR::GA4GH Cloud - Admin and User Engagement](https://github.com/elixir-europe/biohackathon-projects-2022/tree/main/30)
* #31 [The What & How in data management: Improving connectivity between RDMkit and FAIR Cookbook](https://github.com/elixir-europe/biohackathon-projects-2022/tree/main/31)
* #35 [Workflow Execution Service to help the quality improvement of published workflows](https://github.com/elixir-europe/biohackathon-projects-2022/tree/main/35)


## Collaborative Notes

See <https://docs.google.com/document/d/1iDT4U7Kg8xcXKw4noW3-2b3-An2VXm0sKm1jBT0cwCI/edit#> for collaborative notes during hackathon. 
