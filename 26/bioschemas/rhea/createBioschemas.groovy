// Copyright (C) 2022  Egon Willighagen
// License: MIT

// Bacting config
@Grab(group='io.github.egonw.bacting', module='managers-ui', version='0.1.2')
@Grab(group='io.github.egonw.bacting', module='managers-rdf', version='0.1.2')

workspaceRoot = "."
ui = new net.bioclipse.managers.UIManager(workspaceRoot);
bioclipse = new net.bioclipse.managers.BioclipseManager(workspaceRoot);
rdf = new net.bioclipse.managers.RDFManager(workspaceRoot);

// query Rhea for chemical compounds
sparql = ui.readFile("/rhea.rq") // SELECT DISTINCT ?chebi ?name ?synonym ?formula ?smiles ?inchikey
results = rdf.sparqlRemote("https://sparql.rhea-db.org/sparql/", sparql  )

// println "PREFIX schema: <https://schema.org/>"
// println "PREFIX rh: <http://rdf.rhea-db.org/>"

// convert to Bioschemas
println "["
for (i=1;i<=results.rowCount;i++) {
  println "  {"
  println "    \"@context\": \"https://schema.org/\","
  println "    \"type\": \"MolecularEntity\","
  println "    \"http://purl.org/dc/terms/conformsTo\": {\n        \"@id\": \"https://bioschemas.org/profiles/MolecularEntity/0.5-RELEASE\",\n        \"@type\": \"CreativeWork\"\n    },"
  compound = results.get(i, "compound").replace("rh:", "http://rdf.rhea-db.org/")
  chebi = results.get(i, "chebi")
  name = results.get(i, "name").replace("\"", "''")
  println "   \"@id\": \"$compound\","
  println "   \"name\": \"$name\","
  synonym = results.get(i, "synonym")
  if (synonym != null) {
    synonym = synonym.replace("\"", "''")
    println "    \"alternateName\": \"${synonym}\","
  }
  formula = results.get(i, "formula"); if (formula != null) println "    \"molecularFormula\": \"$formula\","
  smiles = results.get(i, "smiles"); if (smiles != null) {
    smiles = smiles.replace("\\", "\\\\")
    println "    \"smiles\": \"$smiles\","
  }
  inchikey = results.get(i, "inchikey"); if (inchikey != null) println "    \"inChIKey\": \"$inchikey\","
  println "    \"sameAs\": \"$chebi\""
  if (i=results.rowCount) {
    println "  }"
  } else {
    println "  },"
  }
}
println "]"
