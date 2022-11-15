/** Elixir BioHackathon 2022 */
package com.elixir.biohackaton.ISAToSRA.sra;

import com.elixir.biohackaton.ISAToSRA.model.OtherMaterial;
import com.elixir.biohackaton.ISAToSRA.model.Parameter;
import com.elixir.biohackaton.ISAToSRA.model.ParameterValue;
import com.elixir.biohackaton.ISAToSRA.model.Study;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.Element;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SRAExperimentXmlCreator {
  public void createENAExperimentSetElement(
      final List<String> bioSampleAccessions,
      final Element webinElement,
      final List<Study> studies) {
    try {
      final Element root = webinElement.addElement("EXPERIMENT_SET");
      final Map<String, List<Parameter>> protocolToParameterMap =
          populateProtocolToParameterMap(studies).get();

      mapExperiments(studies, root, protocolToParameterMap, bioSampleAccessions);
    } catch (final Exception e) {
      log.info("Failed to parse experiments from ISA Json file and create ENA Experiments");
    }
  }

  private String populateProcessSequenceToParameterValuesMapAndGetExecutesProtocolId(
      final Map<String, List<ParameterValue>> protocolToParameterValuesMap, final Study study) {
    final AtomicReference<OtherMaterial> libraryNameMaterialType = new AtomicReference<>();
    final AtomicReference<String> executesProtocolId = new AtomicReference<>();

    study
        .getAssays()
        .forEach(
            assay ->
                assay
                    .getMaterials()
                    .getOtherMaterials()
                    .forEach(
                        otherMaterial -> {
                          if (otherMaterial.getType().equalsIgnoreCase("Library Name")) {
                            libraryNameMaterialType.set(otherMaterial);
                          }

                          if (libraryNameMaterialType.get() != null) {
                            assay
                                .getProcessSequence()
                                .forEach(
                                    processSequence ->
                                        processSequence
                                            .getOutputs()
                                            .forEach(
                                                output -> {
                                                  if (output.getId() != null
                                                      && output
                                                          .getId()
                                                          .equals(
                                                              libraryNameMaterialType
                                                                  .get()
                                                                  .getId())) {
                                                    executesProtocolId.set(
                                                        processSequence
                                                            .getExecutesProtocol()
                                                            .getId());
                                                    protocolToParameterValuesMap.put(
                                                        processSequence
                                                            .getExecutesProtocol()
                                                            .getId(),
                                                        processSequence.getParameterValues());
                                                  }
                                                }));
                          }
                        }));

    return executesProtocolId.get();
  }

  private AtomicReference<Map<String, List<Parameter>>> populateProtocolToParameterMap(
      final List<Study> studies) {
    final AtomicReference<Map<String, List<Parameter>>> protocolToParameterMap =
        new AtomicReference<>(new HashMap<>());

    studies.forEach(
        study ->
            study
                .getProtocols()
                .forEach(
                    protocol -> {
                      protocolToParameterMap.get().put(protocol.id, protocol.getParameters());
                    }));

    return protocolToParameterMap;
  }

  private void mapExperiments(
      final List<Study> studies,
      final Element root,
      final Map<String, List<Parameter>> protocolToParameterMap,
      final List<String> bioSampleAccessions) {
    final Map<String, List<ParameterValue>> protocolToParameterValuesMap = new HashMap<>();

    studies.forEach(
        study ->
            study
                .getAssays()
                .forEach(
                    assay ->
                        assay
                            .getMaterials()
                            .getOtherMaterials()
                            .forEach(
                                otherMaterial -> {
                                  final Element experimentElement = root.addElement("EXPERIMENT");

                                  experimentElement.addAttribute("alias", otherMaterial.getId());
                                  experimentElement
                                            .addElement("TITLE")
                                            .addText(otherMaterial.getName());
                                  experimentElement
                                      .addElement("STUDY_REF")
                                      .addAttribute("refname", study.getTitle());

                                  final Element designElement =
                                      experimentElement.addElement("DESIGN");

                                  designElement.addElement("DESIGN_DESCRIPTION").addText("ISA-Test");

                                    bioSampleAccessions.forEach(
                                            bioSampleAccession -> {
                                                designElement
                                                        .addElement("SAMPLE_DESCRIPTOR")
                                                        .addAttribute("accession", bioSampleAccession);
                                            });

                                  final Element libraryDescriptorElement =
                                      designElement.addElement("LIBRARY_DESCRIPTOR");
                                  final String executesProtocolId =
                                      populateProcessSequenceToParameterValuesMapAndGetExecutesProtocolId(
                                          protocolToParameterValuesMap, study);
                                  final List<Parameter>
                                      filteredParametersMatchingExecutesProtocolId =
                                          protocolToParameterMap.get(executesProtocolId);

                                  filteredParametersMatchingExecutesProtocolId.forEach(
                                      filteredParametersMatchingProtocol -> {
                                        final String parameterId =
                                            filteredParametersMatchingProtocol.getId();
                                        final String parameterName =
                                            filteredParametersMatchingProtocol
                                                .getParameterName()
                                                .getAnnotationValue();

                                        if (isALibraryBaseParameterName(parameterName)) {
                                          final List<ParameterValue> parameterValues =
                                              protocolToParameterValuesMap.get(executesProtocolId);

                                          parameterValues.forEach(
                                              parameterValue -> {
                                                if (parameterValue
                                                    .getCategory()
                                                    .getId()
                                                    .equalsIgnoreCase(parameterId)) {
                                                  libraryDescriptorElement
                                                      .addElement(
                                                          parameterName
                                                              .replace(" ", "_")
                                                              .toUpperCase())
                                                      .addText(
                                                          parameterValue
                                                              .getValue()
                                                              .getAnnotationValue());
                                                }
                                              });
                                        }

                                        if (isALibraryLayoutParameterName(parameterName)) {
                                          final List<ParameterValue> parameterValues =
                                              protocolToParameterValuesMap.get(executesProtocolId);

                                          parameterValues.forEach(
                                              parameterValue -> {
                                                if (parameterValue
                                                    .getCategory()
                                                    .getId()
                                                    .equalsIgnoreCase(parameterId)) {
                                                  final Element libraryLayoutElement =
                                                      libraryDescriptorElement.addElement(
                                                          parameterName
                                                              .replace(" ", "_")
                                                              .toUpperCase());

                                                  libraryLayoutElement.addElement(
                                                      parameterValue
                                                          .getValue()
                                                          .getAnnotationValue()
                                                          .toUpperCase());
                                                }
                                              });
                                        }
                                      });
                                })));
  }

  private boolean isALibraryBaseParameterName(final String parameterName) {
    return parameterName.equalsIgnoreCase("library strategy")
        || parameterName.equals("library source")
        || parameterName.equals("library selection");
  }

  private boolean isALibraryLayoutParameterName(final String parameterName) {
    return parameterName.equalsIgnoreCase("library layout");
  }
}
