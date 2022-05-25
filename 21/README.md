# Project 21: Operator dashboard for controlling the NeIC Sensitive Data Archive

## Abstract

The countries Finland, Sweden, Norway, Denmark and Estonia are collaborating in the NeIC Heilsa project to develop software and operate federated EGA nodes. We want to bring developers from all partnering countries together to work on an operator dashboard for our software stack.


As we move into a mature operational ecosystem there is a need for both System Administrators and Helpdesk staff to be able to control and inspect the system. We need to answer questions related to operations, identify errors in order to better manage the services and infrastructure. To standardize the workflow with the operator dashboard we aim to build an MVP for such an “Operator Dashboard”. There will be a view on the sensitive data archive that will provide Helpdesk with means to identify issues such as number of submissions per user, failed submission and the reason, or how many times a dataset has been accessed, accession identifiers for datasets and their associated files etc.
For system admins the main objective is to have means to trace errors and investigate failed submissions or to spot issues related to downloading/accessing datasets/files. We also want to have the ability to modify and retry failed jobs and make safe manual updates to specific database fields.

We have not implemented any dashboard or control interfaces before and we hope that by bringing this project to the hackathon we can get input from people in different organizations on best practices for design and what we might not have thought about for the dashboard.

## Topics

Compute Platfrom
Data Platform
Federated Human Data

**Project Number:** 21

### Lead(s)

johan.viklund@nbis.se

## Expected outcomes

During hackathon
* MVP of the dashboard
* Display number of messages in each queue
* Show where files are in the ingestion pipeline
* How many files are in each inbox
* Retry one submission job

After:
* Production use of dashboard for helpdesk staff to monitor submission of files - 6 months
* Modification of job messages and retries of errors - 12 months

## Expected audience

Backend developers with a little familiarity with go
Experience with docker and containers
RabbitMQ, PostgrerSQL, S3
Frontend developers

**Number of expected hacking days**: 4

