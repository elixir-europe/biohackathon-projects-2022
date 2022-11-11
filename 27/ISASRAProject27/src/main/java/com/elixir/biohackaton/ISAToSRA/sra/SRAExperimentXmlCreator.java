/** Elixir BioHackathon 2022 */
package com.elixir.biohackaton.ISAToSRA.sra;

import com.elixir.biohackaton.ISAToSRA.model.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import org.dom4j.Element;
import org.springframework.stereotype.Service;

@Service
public class SRAExperimentXmlCreator {
  public String create(
      final List<String> bioSampleAccessions,
      final String studyAlias,
      final Element webinElement,
      final IsaJson isaJson) {
    final AtomicReference<String> enaExperimentAlias = new AtomicReference<>();
    final AtomicReference<String> enaExperimentTitle = new AtomicReference<>();
    final AtomicReference<Map<String, List<ParameterValue>>> protocolToParameterValuesMap =
        new AtomicReference<>(new HashMap<>());
    final AtomicReference<String> executesProtocolId = new AtomicReference<>();

    try {
      final ArrayList<Study> studies = isaJson.getInvestigation().getStudies();
      final Element root = webinElement.addElement("EXPERIMENT_SET");
      final Element experimentElement =
          mapExperimentAliasAndTitle(
              enaExperimentAlias, enaExperimentTitle, studies, studyAlias, root);
      final Map<String, List<Parameter>> protocolToParameterMap =
          populateProtocolToParameterMap(studies).get();

      mapLibraryDescriptor(protocolToParameterValuesMap, executesProtocolId, studies);

      final Element designElement = experimentElement.addElement("DESIGN");
      final Element libraryDescriptorElement = designElement.addElement("LIBRARY_DESCRIPTOR");
      final List<Parameter> filteredParametersMatchingProtocols =
          protocolToParameterMap.get(executesProtocolId.get());

      filteredParametersMatchingProtocols.forEach(
          filteredParametersMatchingProtocol -> {
            final String parameterId = filteredParametersMatchingProtocol.getId();
            final String parameterName =
                filteredParametersMatchingProtocol.getParameterName().getAnnotationValue();

            if (isALibraryBaseParameterName(parameterName)) {
              final List<ParameterValue> parameterValues =
                  protocolToParameterValuesMap.get().get(executesProtocolId.get());

              parameterValues.forEach(
                  parameterValue -> {
                    if (parameterValue.getCategory().getId().equalsIgnoreCase(parameterId)) {
                      libraryDescriptorElement
                          .addElement(parameterName.replace(" ", "_").toUpperCase())
                          .addText(parameterValue.getValue().getAnnotationValue());
                    }
                  });
            }

            if (isALibraryLayoutParameterName(parameterName)) {
              final List<ParameterValue> parameterValues =
                  protocolToParameterValuesMap.get().get(executesProtocolId.get());

              parameterValues.forEach(
                  parameterValue -> {
                    if (parameterValue.getCategory().getId().equalsIgnoreCase(parameterId)) {
                      final Element libraryLayoutElement =
                          libraryDescriptorElement.addElement(
                              parameterName.replace(" ", "_").toUpperCase());

                      libraryLayoutElement.addElement(
                          parameterValue.getValue().getAnnotationValue().toUpperCase());
                    }
                  });
            }
          });

      bioSampleAccessions.forEach(
          bioSampleAccession -> {
            designElement
                .addElement("SAMPLE_DESCRIPTOR")
                .addAttribute("accession", bioSampleAccession);
          });

      return enaExperimentAlias.get();
    } catch (final Exception e) {
      e.printStackTrace();
    }

    return null;
  }

  private static void mapLibraryDescriptor(
      final AtomicReference<Map<String, List<ParameterValue>>> protocolToParameterValuesMap,
      final AtomicReference<String> executesProtocolId,
      final ArrayList<Study> studies) {
    final AtomicReference<OtherMaterial> libraryNameMaterialType = new AtomicReference<>();

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
                                                            protocolToParameterValuesMap
                                                                .get()
                                                                .put(
                                                                    processSequence
                                                                        .getExecutesProtocol()
                                                                        .getId(),
                                                                    processSequence
                                                                        .getParameterValues());
                                                          }
                                                        }));
                                  }
                                })));
  }

  private static AtomicReference<Map<String, List<Parameter>>> populateProtocolToParameterMap(
      final ArrayList<Study> studies) {
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

  private static Element mapExperimentAliasAndTitle(
      final AtomicReference<String> enaExperimentAlias,
      final AtomicReference<String> enaExperimentTitle,
      final ArrayList<Study> studies,
      final String studyAlias,
      final Element root) {
    studies.forEach(
        study -> {
          study
              .getAssays()
              .forEach(
                  assay -> {
                    assay
                        .getMaterials()
                        .getOtherMaterials()
                        .forEach(
                            otherMaterial -> {
                              enaExperimentAlias.set(otherMaterial.getId());
                              enaExperimentTitle.set(otherMaterial.getName());
                            });
                  });
        });

    final Element experimentElement =
        root.addElement("EXPERIMENT").addAttribute("alias", enaExperimentAlias.get());

    experimentElement.addElement("STUDY_REF").addAttribute("refname", studyAlias);
    experimentElement.addElement("TITLE").addText(enaExperimentTitle.get());

    return experimentElement;
  }

  private static boolean isALibraryBaseParameterName(final String parameterName) {
    return parameterName.equalsIgnoreCase("library strategy")
        || parameterName.equals("library source")
        || parameterName.equals("library selection");
  }

  private static boolean isALibraryLayoutParameterName(final String parameterName) {
    return parameterName.equalsIgnoreCase("library layout");
  }
}
