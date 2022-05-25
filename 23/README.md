# Project 23: Publishing and Consuming Schema.org DataFeeds

## Abstract

Bioschemas provides a lightweight vocabulary for making the content of Web pages machine processable. However, as shown in Project 29 at BioHackathon 2021 (10.37044/osf.io/y6gbq), harvesting markup by visiting each page of a site is not feasible for large sites due to the time required to process each page. This approach imposes processing demands on the publisher and consumer. In February 2022, the Schema.org community proposed a mechanism for sharing markup from multiple pages as a DataFeed published at an established location. The feed could be a single file with the whole content or split into multiple files based on some aspect of the dataset, e.g. ChEMBL could have a file for proteins and another one for molecular entities. This would reduce processing demands for publishers and consumers and speed up data harvesting.

The aim of this hackathon proposal is to explore the implementation of the Schema.org proposal from both a producer and consumer perspective, for a variety of resources implementing different Bioschemas profiles. Additionally, we will investigate whether existing mechanisms such as OAI-PMH (https://www.openarchives.org/pmh/) or GraphQL (https://graphql.org/) can be exploited to generate on-the-fly dumps of Bioschemas markup restricted to a consumer’s particular data need; rather than having to process a single monolithic (possibly huge) file. On the consumer side, we will prototype a consumption pipeline that enables these feeds to be ingested into knowledge graphs including IDP Knowledge Graph (10.37044/osf.io/v3jct) and the Open AIRE Research Graph. To enable the latter, we will also develop additional mappings between Bioschemas profiles and OpenAIRE’s data model. We will need to understand how to mix schema feeds from different sources, possibly exploiting background knowledge from Wikidata to reconcile concepts.

## Topics

Bioschemas
Data Platform
Interoperability Platform
Intrinsically Disordered Community

**Project Number:** 23

### Lead(s)

Alasdair Gray
Alban Gaignard

## Expected outcomes

- Improved understanding of generating and publishing DataFeeds, including exploiting existing mechanisms to generate subsets of a data feed, guidelines for data producers to easily produce Bioschemas DataFeeds.

- Prototype mechanism for producing, consuming and integrating multiple DataFeeds

- Additional mappings between Bioschemas and OpenAIRE/Datacite

- Tutorials detailing step-by-step guides for publishing and consuming DataFeeds

## Expected audience

JSON-LD, Triplestores, Schema.org/Bioschemas, Knowledge Graphs, OAI-PMH, GraphQL, g2g

**Number of expected hacking days**: 4

