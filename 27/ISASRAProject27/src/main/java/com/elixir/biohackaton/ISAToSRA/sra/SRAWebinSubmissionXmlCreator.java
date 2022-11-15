/** Elixir BioHackathon 2022 */
package com.elixir.biohackaton.ISAToSRA.sra;

import com.elixir.biohackaton.ISAToSRA.biosamples.BioSamplesSubmitter;
import com.elixir.biohackaton.ISAToSRA.model.Investigation;
import com.elixir.biohackaton.ISAToSRA.model.IsaJson;
import com.elixir.biohackaton.ISAToSRA.model.Study;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SRAWebinSubmissionXmlCreator {
  @Autowired private BioSamplesSubmitter bioSamplesSubmitter;

  @Autowired private SRAStudyXmlCreator sraStudyXmlCreator;

  @Autowired private SRAExperimentXmlCreator sraExperimentXmlCreator;

  @Autowired private SRAProjectXmlCreator sraProjectXmlCreator;

  @Autowired private ObjectMapper objectMapper;

  public void performSubmissionToBioSamplesAndEna(ApplicationArguments args) throws Exception {
    String webinToken;
    String webinUserName;
    String webinPassword;

    if (args.getOptionNames().contains("webinJwt")) {
      webinToken = args.getOptionValues("webinJwt").iterator().next();
    } else {
      throw new RuntimeException("Webin Authentication Token is not provided");
    }

    if (args.getOptionNames().contains("webinUserName")) {
      webinUserName = args.getOptionValues("webinUserName").iterator().next();
    } else {
      throw new RuntimeException("Webin user name is not provided");
    }

    if (args.getOptionNames().contains("webinPassword")) {
      webinPassword = args.getOptionValues("webinPassword").iterator().next();
    } else {
      throw new RuntimeException("Webin password is not provided");
    }

    final Path path = Paths.get("C:\\Users\\dgupta\\Test_BioHackathon_Investigation.json");
    final List<String> lines = Files.readAllLines(path);
    final String isaJsonString = String.join("\n", lines);
    final IsaJson isaJson = this.objectMapper.readValue(isaJsonString, IsaJson.class);
    final List<Study> studies = getStudies(isaJson);
    final List<String> bioSampleAccessions =
        this.bioSamplesSubmitter.createBioSamples(studies, webinToken);

    final Document document = DocumentHelper.createDocument();
    final Element webinElement = startPreparingWebinV2SubmissionXml(document);

    this.sraStudyXmlCreator.createENAStudySetElement(webinElement, studies);
    this.sraExperimentXmlCreator.createENAExperimentSetElement(
        bioSampleAccessions, webinElement, studies);
    this.sraProjectXmlCreator.createENAProjectSetElement(webinElement, getInvestigation(isaJson));

    final OutputFormat format = OutputFormat.createPrettyPrint();
    final XMLWriter writer = new XMLWriter(System.out, format);

    writer.write(document);
  }

  public List<Study> getStudies(final IsaJson isaJson) {
    try {
      return isaJson.getInvestigation().getStudies();
    } catch (final Exception e) {
      log.info("Failed to parse ISA JSON and get studies", e);
    }

    return null;
  }

  public Investigation getInvestigation(final IsaJson isaJson) {
    try {
      return isaJson.getInvestigation();
    } catch (final Exception e) {
      log.info("Failed to parse ISA JSON and get studies", e);
    }

    return null;
  }

  private static Element startPreparingWebinV2SubmissionXml(Document document) {
    final Element webinElement = document.addElement("WEBIN");
    final Element submissionElement = webinElement.addElement("SUBMISSION");
    final Element actionsElement = submissionElement.addElement("ACTIONS");
    final Element actionElement = actionsElement.addElement("ACTION");

    actionElement.addElement("ADD");

    return webinElement;
  }
}
