/** Elixir BioHackathon 2022 */
package com.elixir.biohackaton.ISAToSRA.sra;

import com.elixir.biohackaton.ISAToSRA.biosamples.BioSamplesSubmitter;
import com.elixir.biohackaton.ISAToSRA.model.IsaJson;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SRAWebinSubmissionXmlCreator {
  @Autowired private BioSamplesSubmitter bioSamplesSubmitter;

  @Autowired private SRAStudyXmlCreator sraStudyXmlCreator;

  @Autowired private SRAExperimentXmlCreator sraExperimentXmlCreator;

  @Autowired private SRAProjectXmlCreator sraProjectXmlCreator;

  @Autowired private ObjectMapper objectMapper;

  public void create() throws Exception {
    final Path path = Paths.get("C:\\Users\\dgupta\\Test_BioHackathon_Investigation.json");
    final List<String> lines = Files.readAllLines(path);
    final String isaJsonString = String.join("\n", lines);
    final IsaJson isaJson = this.objectMapper.readValue(isaJsonString, IsaJson.class);
    final List<String> bioSampleAccessions =
        this.bioSamplesSubmitter.parseIsaJsonAndCreateBioSamples(isaJson);
    final Document document = DocumentHelper.createDocument();

    final Element webinElement = document.addElement("WEBIN");
    final Element subsElement = webinElement.addElement("SUBMISSION");
    final Element actionsElement = subsElement.addElement("ACTIONS");
    final Element actionElement = actionsElement.addElement("ACTION");

    actionElement.addElement("ADD");

    final String studyAlias = this.sraStudyXmlCreator.create(webinElement, isaJson);
    final String experimentAlias =
        this.sraExperimentXmlCreator.create(bioSampleAccessions, studyAlias, webinElement, isaJson);

    this.sraProjectXmlCreator.create(isaJson, webinElement);

    final OutputFormat format = OutputFormat.createPrettyPrint();
    final XMLWriter writer = new XMLWriter(System.out, format);

    writer.write(document);
  }
}
