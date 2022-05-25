# Project 20: Onboarding suite for Federated EGA nodes

## Abstract

The European Genome-phenome Archive (EGA) is a service for permanent archiving and sharing personally identifiable genetic and phenotypic data resulting from biomedical research projects. The Federated EGA, consisting of the Central and Federated EGA nodes, will be a distributed network of repositories for sharing human -omics data and phenotypes. Each node of the federation is responsible for its own infrastructure and the connection to the Central EGA. Currently, the adoption and deployment of a new federated node is challenging due to the complexity of the project and the diversity of technological solutions used, in order to ensure the secure archiving of the data and the transfer of the information between the nodes.

The goal of this project is to develop a suite consisting of simple scripts that would help newcomers to the federation to deeply understand the main concepts, while enabling them to get involved in the development of the technology as quickly as possible.

In order to achieve that, we are planning to focus on the main pipeline, handling the archiving of the data submitted by users. Specifically, the goal is to create a number of scripts that would lead the user/developer through the process followed from submitting a file to archiving it and making it available for downloading. The scripts will shed light on the processes under the hood, including the messaging between the services, the records stored in the database as well as the tools used for encrypting and decrypting the data. By the end of the biohackathon, we aim to have a suite that will ease the onboarding of new members of the Federation.

## Topics

Compute Platfrom
Data Platform
Federated Human Data
Training Platform

**Project Number:** 20

### Lead(s)

stefan.negru@csc.fi

## Expected outcomes

During the hackathon:
 * Recognize the aspects that make the adoption of the existing infrastructure difficult based on nodes that are interested to join
 * Develop script(s) for encryption and submission of data
 * Develop easy to use scripts for base pipeline for showing:
   * Messaging between services
   * Records stored in the database

Longterm:
 * Easy to setup local environment with synthetic data to evaluate the nordic federated ega pipeline. ~6 months
 * Templating a system to setup the nordic federated ega pipeline using different infrastructure providers, such as with openstack, kubernetes, amazon. ~12 months after hackathon

## Expected audience

Backend developers with a little familiarity with go
Experience with docker, containers, RabbitMQ, PostgrerSQL, S3

**Number of expected hacking days**: 4

