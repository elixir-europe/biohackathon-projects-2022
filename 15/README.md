# Project 15: Infrastructure for Synthetic Health Data

## Abstract

Machine Learning (ML) methods are becoming ever more prevalent across all domains in Life Sciences. However, a key component of effective ML is the availability of large datasets that are diverse and representative. In the context of health systems, with significant heterogeneity of clinical phenotypes and diversity of healthcare systems, there exists a necessity to develop and refine unbiased and fair ML models. Synthetic data are increasingly being used to protect the patient’s right to privacy and overcome the paucity of annotated open-access medical data. Synthetic data and generative models can address these challenges while advancing the use of ML in healthcare and research.

Following up the efforts currently undertaken in the ELIXIR Health Data and the Machine Learning Focus Groups around the synthetic health data landscape, this project will focus on the health data providers' need for a ready-to-use synthetic data platform which is assessed by health data experts, researchers, and ML specialists. Aligned to ELIXIR Health Data Focus Group’s objectives, we aim at building an infrastructure for synthetic health data offering a dockerized synthetic data generator based on the open-source libraries Synthetic Data Vault (SDV) (github.com/sdv-dev) and ydata-synthetic (github.com/ydataai) with state of the art ML methods. This will enable users to generate synthetic data that has the same structure and statistical properties as the original dataset from a variety of data types (clinical, variational or omics). Despite the capacity to generate their own datasets, a set of exemplary datasets will be publicly available in appropriate repositories and will include rich metadata descriptions according to the DOME recommendations (https://dome-ml.org/) and GA4GH (ga4gh.org) standards. OpenEBench (openebench.bsc.es) will host a community of practice for comparing different approaches for synthetic data generation.

## Topics

Data Platform
Federated Human Data
GA4GH partnership
Machine learning
Tools Platform

**Project Number:** 15

### Lead(s)

Núria Queralt Rosinach, n.queralt_rosinach@lumc.nl

Soumyabrata Ghosh, soumyabrata.ghosh@uni.lu 

Venkata Satagopam, venkata.satagopam@uni.lu

Tim Beck, timbeck@leicester.ac.uk 

Davide Cirillo, davide.cirillo@bsc.es	

Wei Gu, wei.gu@uni.lu 

Fotis Psomopoulos, fpsom@certh.gr 

Dylan Spalding, dylan.spalding@csc.fi 

Salvador Capella-Gutierrez, salvador.capella@bsc.es  

## Expected outcomes

Hackathon outcomes:
1. Design and development of a synthetic data generation workflow in Python using SDV, and ydata-synthetic libraries (4 days)
2. Development of a web interface (with Python Stremlit package or similar) to run the workflow with configuration settings (4 days)
3. Packaging in a docker which can shipped to data provider location (1 day)

As long-term outcomes, we are planning to submit a manuscript on the synthetic health data infrastructure developed following ELIXIR requirements. The development of the infrastructure per se is a long-term outcome, where we envision adding other components such as implementing evaluation metrics to assess the quality of the generated synthetic data and a direct deposition of the synthetic datasets to recommended repositories.

## Expected audience

Python developer(s) with experience in data science libraries + UI 
Researchers developing workflows
Synthetic data experts and users
Experts on statistics + ML
Researcher(s) with experience in EHR and clinical data
Researcher(s) with experience in various omics data-types

We would like to invite two specific people from Europe who are critical to the success of the project (name /institute/email):

Prof. Mihaela van der Schaar 
University of Cambridge 
mv472@damtp.cam.ac.uk
(https://www.vanderschaar-lab.com/)

Prof. Patrick Ruch
HEG / HESSO Geneva and Group Leader at SIB (Text Mining group)
patrick.ruch@hesge.ch
https://orcid.org/0000-0002-3374-2962
(http://bitem.hesge.ch/people/patrick-ruch)
CINECA Synthetic Datasets

**Number of expected hacking days**: 4 days

