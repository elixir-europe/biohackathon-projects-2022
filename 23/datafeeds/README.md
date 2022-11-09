# Implementing [Schema.org feed](https://schema.org/docs/feeds.html) proposal
One of the topics of this project is to test and implement the Schema.org feed mechanism to get site-level dumps of structured data (https://schema.org/docs/feeds.html).

## Discovery specifications
Two Well Known URLs can be used for site-wide discovery of feed URLs, at the domain name level:
* `/.well-known/feeddata-general`:

  this gives a site's default representation in structured data formats, typically but not necessarily in JSON-LD format using Schema.org as the base vocabulary. It usually consists of all the structured data that is embedded in public pages on the site.

  For large data files, this URL could redirect to `feeddata-toc`, which would describe the set of component files making up the feed.
* `/.well-known/feeddata-toc`

  This gives a table of contents for a site, typically but not necessarily expressed in Schema.org, with pointers to structured data feeds of various kinds. It serves as a machine-readable entry point.

## Example responses

Example responses for [`feeddata-general`](feeddata-general.jsonld) and [`feeddata-toc`](feeddata-toc.jsonld).
