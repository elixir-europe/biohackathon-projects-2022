// Copyritght (c) 2022 Egon Willighagen
//
// MIT

@Grab(group='io.github.egonw.bacting', module='managers-rdf', version='0.1.2')
@Grab(group='io.github.egonw.bacting', module='managers-ui', version='0.1.2')
@Grab(group='org.apache.commons', module='commons-csv', version='1.9.0')

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.csv.*;

workspaceRoot = "."
bioclipse = new net.bioclipse.managers.BioclipseManager(workspaceRoot);
rdf = new net.bioclipse.managers.RDFManager(workspaceRoot);
ui = new net.bioclipse.managers.UIManager(workspaceRoot);

String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

Reader in = new FileReader("surfactants2_TSCA_PEG.csv");
Iterable<CSVRecord> records = CSVFormat.RFC4180.parse(in);
for (CSVRecord record : records) {
  String columnOne = record.get(1);
  if (columnOne != "CAS") {
    String columnTwo = record.get(2);
    println "\n\tCREATE"
    println "\tLAST\tP31\tQ191154\tS248\tQ115139184"
    println "\tLAST\tLen\t\"${columnTwo}\""
    println "\tLAST\tP231\t\"${columnOne}\"\tS248\tQ115139184"
  }
}
