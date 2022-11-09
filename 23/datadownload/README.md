# Implementing [`DataDownload`](https://developers.google.com/search/docs/appearance/structured-data/dataset#publication) for structured data dumps
The second approach to get site-level dumps of structured data is using `DataDownload` type from [`Dataset`](https://developers.google.com/search/docs/appearance/structured-data/dataset#publication).

## Discovery specifications
The ['Dataset'](https://schema.org/Dataset) type contains a `distribution` property which describes a ['DataDownload'](https://schema.org/DataDownload) type. Three 'DataDownload' properties are needed to properly describe an data dump endpoint: `contentUrl`, `encodingFormat` and a date property like `dateModified`.

An example `Dataset` profile with `DataDownload` type: [`DataDownload.jsonld`](DataDownload.jsonld)

## Example responses
The endpoint pointed by `contentUrl` should return a data dump as shown [here](dump.jsonld).  
