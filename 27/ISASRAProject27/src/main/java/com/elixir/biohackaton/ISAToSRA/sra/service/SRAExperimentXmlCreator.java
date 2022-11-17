/** Elixir BioHackathon 2022 */
package com.elixir.biohackaton.ISAToSRA.sra.service;

import com.elixir.biohackaton.ISAToSRA.model.OtherMaterial;
import com.elixir.biohackaton.ISAToSRA.model.Parameter;
import com.elixir.biohackaton.ISAToSRA.model.ParameterValue;
import com.elixir.biohackaton.ISAToSRA.model.Study;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.Element;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SRAExperimentXmlCreator {
  public static final String OTHER_MATERIAL_LIBRARY_NAME_DETERMINES_EXPERIMENT = "Library Name";

  public Map<Integer, String> createENAExperimentSetElement(
      final Map<String, String> typeToBioSamplesAccessionMap,
      final Element webinElement,
      final List<Study> studies) {
    try {
      final Element root = webinElement.addElement("EXPERIMENT_SET");
      final Map<String, List<Parameter>> protocolToParameterMap =
          populateProtocolToParameterMap(studies).get();

      return mapExperiments(studies, root, protocolToParameterMap, typeToBioSamplesAccessionMap);
    } catch (final Exception e) {
      log.info("Failed to parse experiments from ISA Json file and create ENA Experiments");
    }

    return null;
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
                          if (otherMaterial
                              .getType()
                              .equalsIgnoreCase(
                                  OTHER_MATERIAL_LIBRARY_NAME_DETERMINES_EXPERIMENT)) {
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

  private Map<Integer, String> mapExperiments(
      final List<Study> studies,
      final Element root,
      final Map<String, List<Parameter>> protocolToParameterMap,
      final Map<String, String> bioSampleAccessions) {
    final Map<String, List<ParameterValue>> protocolToParameterValuesMap = new HashMap<>();
    final Map<Integer, String> experimentSequence = new HashMap<>();
    final AtomicInteger sequenceCounter = new AtomicInteger(0);

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
                                  final String otherMaterialId = otherMaterial.getId();

                                  experimentSequence.put(
                                      sequenceCounter.incrementAndGet(), otherMaterialId);
                                  experimentElement.addAttribute("alias", otherMaterialId);
                                  experimentElement
                                      .addElement("TITLE")
                                      .addText(otherMaterial.getName());
                                  experimentElement
                                      .addElement("STUDY_REF")
                                      .addAttribute("refname", study.getTitle());

                                  final Element designElement =
                                      experimentElement.addElement("DESIGN");

                                  designElement
                                      .addElement("DESIGN_DESCRIPTION")
                                      .addText("ISA-Test");

                                  final String sourceBioSampleAccession =
                                      bioSampleAccessions.get("SOURCE");

                                  designElement
                                      .addElement("SAMPLE_DESCRIPTOR")
                                      .addAttribute("accession", sourceBioSampleAccession);

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

                                        if (isALibraryStrategyParameterName(parameterName)) {
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
                                      });

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

                                  final Element platformElement =
                                      experimentElement.addElement("PLATFORM");
                                  final Element experimentTypeElement =
                                      platformElement.addElement("OXFORD_NANOPORE");

                                  experimentTypeElement
                                      .addElement("INSTRUMENT_MODEL")
                                      .addText("MinION");
                                })));

    return experimentSequence;
  }

  private boolean isALibraryBaseParameterName(final String parameterName) {
    return parameterName.equals("library source") || parameterName.equals("library selection");
  }

  private boolean isALibraryStrategyParameterName(final String parameterName) {
    return parameterName.equalsIgnoreCase("library strategy");
  }

  private boolean isALibraryLayoutParameterName(final String parameterName) {
    return parameterName.equalsIgnoreCase("library layout");
  }
}
