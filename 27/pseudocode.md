## Pseudocode for BioSamples XML

* In Investigation -> Studies -> Materials -> Sources, take @id 
For each Source @id -> take @id, name (e.g plant 1), -> characteristics -> take category @id (i.e name of metadata field) and value -> annotationValue 
= Parent Biosample (submitted)

* In Investigation -> Studies -> Materials -> Samples -> take @id (i.e id of the sample), name, characteristics
= Child Biosample (submitted)

* In Investigation -> Studies -> Protocols -> take Name =  ‘sample collection’  and -> protocolType  -> annotationValue for this field (i.e ‘sample collection’) 
(there will always be a protocol named ‘sample collection’ in the ISA)

* In Investigation -> Studies -> Materials -> Samples -> derivesFrom -> look for the same source @id as above. 
= Add Biosample relationship ‘Sample @id derived from Source @id’ (in Biosamples, ‘target’ = Source @id)

### ISA-JSON conversion to samples
* ISAJSON file -> string 
* Source = parent sample, sample = child sample
* Isaroot is a java representation of the file, 
* For every source you are getting you are constructing a biosample
* Samples being obtained from assays, source from materials from study, investigation


## Pseudocode for ENA EXPERIMENT XML
1) In Assay → Materials → OtherMaterials, take Type = “Library Name”
* List @id of all Type = “Library Name” in the Assay
* List all Characteristics (key:value) for each Type = “Library Name”

2) In the same Assay → ProcessSequences, look for “output = @id of the Library Name”
* Within the same schema of the output, look for “executesProtocol = xxx” and “its @id”
* List the parameterValues (key=category : value=annotation value) of the executesProtocol
* Look for “@id of each parameterValues” (key=category) in “Studies” (containing the Assay) → “Protocols” and take the "parameterName": {"annotationValue": xxx}. In other words:
  * In the “Studies→Protocols” schema of the “Assay” in question, find “@id of each parameterValues” (key=category), and for each @id, list "parameterName": {"annotationValue": xxx}.
  * Build XML, send to ENA/SRA, validate attributes and values

