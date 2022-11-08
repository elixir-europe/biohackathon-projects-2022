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
sparql = ui.readFile("/rhea.rq") // SELECT DISTINCT ?chebi ?name ?formula ?smiles ?inchikey
results = rdf.sparqlRemote("https://sparql.rhea-db.org/sparql/", sparql  )

// convert to Bioschemas
println "PREFIX schema: <https://schema.org/>"
println "PREFIX rh: <http://rdf.rhea-db.org/>"
println()
for (i=1;i<=results.rowCount;i++) {
  compound = results.get(i, "compound")
  chebi = results.get(i, "chebi")
  name = results.get(i, "name");
  println "$compound a schema:MolecularEntity ; schema:name \"$name\" ;"
  formula = results.get(i, "formula"); if (formula != null) println "  schema:molecularFormula \"$formula\" ;"
  smiles = results.get(i, "smiles"); if (smiles != null) println "  schema:smiles \"$smiles\" ;"
  inchikey = results.get(i, "inchikey"); if (inchikey != null) println "  schema:inChIKey \"$inchikey\" ;"
  println "  schema:sameAs <$chebi> ."
}
