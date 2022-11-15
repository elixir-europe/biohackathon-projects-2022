# Converting from ISA to ENA

## dataFiles to RUN_SET

Run the following code when you are in "isa_ena" directory and don't forget to set the arguments:
```shell
python -B .
```

|Argument||Description|
|---|---|---|
|--importpath|IMPORTPATH|isa json file|
|--exportpath|EXPORTPATH|ena xml file|
|--ref|REF|experiment reference|

### Example
```shell
python -B . --importpath="../seek_isajson/isa_seek-investigation_p19.json" --exportpath="outputs/datafiles-runset.xml" --ref="100" 
```