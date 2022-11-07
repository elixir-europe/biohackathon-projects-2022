# Project 30: The ELIXIR::GA4GH Cloud

## Abstract

The Global Alliance for Genomics and Health (GA4GH), an international
standard-setting organization bringing together opinion leaders in academia and
industry, has proposed a set of community standards for data storage, transfer
and processing in the cloud. The ELIXIR Cloud & AAI community, a GA4GH Driver
Project, is developing the ELIXIR::GA4GH Cloud (EGC), a federated cloud
environment for large-scale data analysis in the life sciences.
Interoperability across services and clouds is achieved through adopting GA4GH
standards and authentication and authorization guidelines.

To find out more about the organization and the project, check out a recent
[slide deck][slides], or the corresponding [webinar][webinar] (~1 hour).

## Possible themes

Work will be organized in five different themes. There are issues associated
with each of the themes - just have a look at the [project
board][project-board] (check frequently - more issues will be added).

1. Community engagement: Improve website & liaise with interested drivers,
   developers & sys admins (issue label: `outreach`)
2. Integrate the proWES gateway for workflow-level federation (issue label:
   `workflow federation`)
3. Integrate the proTES gateway for task-level federation (issue label: `task
   federation`)
4. Secure and populate the ELIXIR Cloud Registry (issue label: `registry`)
5. Generate documentation for end users, developers & administrators (issue
   label: `documentation`)

## Topics

* Compute Platfrom
* Containers
* GA4GH partnership
* Galaxy
* Tools Platform

## Lead(s)

* Alex Kanitz <alexander.kanitz@unibas.ch>
* Philip Kensche <p.kensche@dkfz-heidelberg.de> (WESkit subproject)

## Possible outcomes

* New services for workflow and task federation integrated into the EGC
* End-to-end file system support (FTP and/or s3)
* New driver projects found and/or use cases for the ELIXIR::GA4GH Cloud defined
* Additional computing centers and data portals represented by the BioHackathon
  community integrated into the EGC
* Improved deployment documentation and automation (e.g., CI/CD)

## Expected audience

* **Bioinformaticians / data scientists**, particularly those needing to run
  different workflows on large amounts of data
* **Systems/service administrators / DevOps**, particularly those interested in
  having their nodes join the ELIXIR::GA4GH Cloud
* **(Web) developers**, particularly those interested in adopting standards and
  guidelines to make their services compatible with the EGC
* **Testers / teachers / trainers** who are interested in interoperable cloud
  solutions

**Number of expected hacking days**: 4

[project-board]: <https://github.com/orgs/elixir-cloud-aai/projects/7>
[slides]: <https://docs.google.com/presentation/d/1DyrwN6HhJ-Tz6mh_QBObFQruWr7zCOL-BIn17Zjlqk4/edit?usp=sharing>
[webinar]: <https://www.pistoiaalliance.org/pistoia-webinars/the-elixirga4gh-cloud-towards-a-federated-fair-life-science-analytics-infrastructure/>
