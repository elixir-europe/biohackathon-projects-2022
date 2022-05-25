# Project 34: Using Federated Public Data for Disease Subtyping and Prediction of Effective Treatments

## Abstract

In previous hackathons (Biohackathon 2021, Bringing Genomic Data to the Clinic 2022), we have created a proof of concept tool that indicates confidence in colorectal cancer subtyping given various single and multi-omic methods, and is further able to indicate specific compounds for precise pharmacological intervention given an individual’s transcriptome once subtype is established, heavily leveraging the Drugmonizome tool (Ma’ayan lab, MSSM). We will build a validation engine that uses federated public datasets, as well as data from a number of health systems, to calculate the potential efficacy of compounds given longitudinal effects of treatment within a particular disease subtype. PRIDE, CPTAC, Massive, and other datasets will be used in conjunction with genomic data to validate protein and pathway targets, looking specifically at subtype-relevant pathways pre- and post- treatment. To calculate clinical effectiveness in populations, meta-analysis of retrospective studies (example links below) of EMA, FDA and other national agency (e.g. Japan) approved drugs will be harmonized to the MCBS scale. We will then attempt to define likelihood of drug subtype efficacy combining proteome and global efficacy data, presenting summarized data in a web app (likely shiny). Our stretch goal will be to present a pathway for working “backward” along these lines (e.g. defining clinical subtypes given pharmacological effectiveness). This is of critical importance due to the lack of subtyping for many prevalent diseases, e.g. most forms of dementia.

Example publications for meta-analysis
https://www.bmj.com/content/359/bmj.j4530
https://jamanetwork.com/journals/jamainternalmedicine/fullarticle/2733563
https://www.ncbi.nlm.nih.gov/pmc/articles/PMC3679802/

## Topics

Cancer
Federated Human Data
industry
Proteomics

**Project Number:** 34

### Lead(s)

Ben Busby bbusby@dnanexus.com

## Expected outcomes

Dockerized container for with tools that 
+indicate potential treatments
+present an aggregate validation score for multiomic and clinical data-based approaches
+present individual scores and other information

## Expected audience

The five folks above have agreed to work on this, and at least 2-3 of us expect to be on site.  Experience in cancer pharmacology, metadata harmonization, information theory, or proteomics especially welcome, if other folks are interested in this problem!

**Number of expected hacking days**: 4

