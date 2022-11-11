/** Elixir BioHackathon 2022 */
package com.elixir.biohackaton.ISAToSRA.biosamples;

import com.elixir.biohackaton.ISAToSRA.model.IsaJson;
import com.elixir.biohackaton.ISAToSRA.model.Study;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import uk.ac.ebi.biosamples.client.BioSamplesClient;
import uk.ac.ebi.biosamples.model.Attribute;
import uk.ac.ebi.biosamples.model.Relationship;
import uk.ac.ebi.biosamples.model.Sample;

@Service
public class BioSamplesSubmitter {
  public static final String WEBIN_AUTH_TOKEN_PLACEHOLDER = "_JWT_HERE";

  public List<String> parseIsaJsonAndCreateBioSamples(final IsaJson isaJson) {
    final AtomicReference<String> parentBioSampleAccession = new AtomicReference<>();
    final AtomicReference<String> parentSampleOrganism = new AtomicReference<>();
    final List<String> bioSampleAccessions = new ArrayList<>();

    try (final BioSamplesClient bioSamplesClient =
        BiosamplesClientInstance.buildBioSamplesClient(BioSampleClientEnvironment.dev)) {
      final ArrayList<Study> studies = isaJson.getInvestigation().getStudies();

      this.createParentSample(
          parentBioSampleAccession,
          parentSampleOrganism,
          bioSampleAccessions,
          bioSamplesClient,
          studies);

      this.createAndUpdateChildSamplesWithRelationships(
          parentBioSampleAccession,
          parentSampleOrganism,
          bioSampleAccessions,
          bioSamplesClient,
          studies);

      return bioSampleAccessions;
    } catch (final Exception e) {
      e.printStackTrace();
    }

    return null;
  }

  private void createAndUpdateChildSamplesWithRelationships(
      final AtomicReference<String> parentBioSampleAccession,
      final AtomicReference<String> parentSampleOrganism,
      final List<String> bioSampleAccessions,
      final BioSamplesClient bioSamplesClient,
      final ArrayList<Study> studies) {
    studies.forEach(
        study ->
            study
                .getAssays()
                .forEach(
                    assay ->
                        assay
                            .getMaterials()
                            .getSamples()
                            .forEach(
                                sample -> {
                                  final Sample bioSample =
                                      new Sample.Builder("leaf 1")
                                          .withRelease(Instant.now())
                                          .withAttributes(
                                              Collections.singletonList(
                                                  Attribute.build(
                                                      "organism", parentSampleOrganism.get())))
                                          .build();
                                  final EntityModel<Sample> persistedSampleEntity =
                                      bioSamplesClient.persistSampleResource(
                                          bioSample, WEBIN_AUTH_TOKEN_PLACEHOLDER);
                                  final Sample persistedBioSample =
                                      persistedSampleEntity.getContent();

                                  System.out.println(
                                      "New sample accession is "
                                          + persistedBioSample.getAccession());

                                  bioSampleAccessions.add(persistedBioSample.getAccession());

                                  final Sample sampleWithRelationship =
                                      Sample.Builder.fromSample(persistedBioSample)
                                          .withRelationships(
                                              Collections.singletonList(
                                                  Relationship.build(
                                                      persistedBioSample.getAccession(),
                                                      "derived from",
                                                      parentBioSampleAccession.get())))
                                          .build();

                                  this.updateSampleWithRelsToBioSamples(sampleWithRelationship);
                                })));
  }

  private void createParentSample(
      final AtomicReference<String> parentBioSampleAccession,
      final AtomicReference<String> parentSampleOrganism,
      final List<String> bioSampleAccessions,
      final BioSamplesClient bioSamplesClient,
      final ArrayList<Study> studies) {
    studies.forEach(
        study ->
            study
                .getMaterials()
                .getSources()
                .forEach(
                    source -> {
                      source
                          .getCharacteristics()
                          .forEach(
                              characteristic -> {
                                if (characteristic.getCategory().getId().contains("organism_320")) {
                                  parentSampleOrganism.set(
                                      characteristic.getValue().getAnnotationValue());
                                }
                              });

                      final Sample parentBioSample =
                          new Sample.Builder(source.getName())
                              .withRelease(Instant.now())
                              .withAttributes(
                                  Collections.singletonList(
                                      Attribute.build("organism", parentSampleOrganism.get())))
                              .build();
                      final EntityModel<Sample> persistedParentSampleEntity =
                          bioSamplesClient.persistSampleResource(
                              parentBioSample, WEBIN_AUTH_TOKEN_PLACEHOLDER);
                      final Sample persistedParentBioSample =
                          persistedParentSampleEntity.getContent();

                      parentBioSampleAccession.set(
                          persistedParentSampleEntity.getContent().getAccession());

                      System.out.println(
                          "Parent sample accession is " + persistedParentBioSample.getAccession());

                      bioSampleAccessions.add(persistedParentBioSample.getAccession());
                    }));
  }

  private void updateSampleWithRelsToBioSamples(final Sample sampleWithRelationship) {
    final RestTemplate restTemplate = new RestTemplate();
    final ResponseEntity<Sample> biosamplesResponse;

    try {
      final HttpHeaders headers =
          new HttpHeaders() {
            {
              final String authHeader = "Bearer " + WEBIN_AUTH_TOKEN_PLACEHOLDER;
              this.set("Authorization", authHeader);
            }
          };
      headers.add("Content-Type", "application/json;charset=UTF-8");
      headers.add("Accept", "application/json");

      final HttpEntity<?> entity = new HttpEntity<>(sampleWithRelationship, headers);

      biosamplesResponse =
          restTemplate.exchange(
              "http://wwwdev.ebi.ac.uk/biosamples/samples/" + sampleWithRelationship.getAccession(),
              HttpMethod.PUT,
              entity,
              new ParameterizedTypeReference<>() {});
      System.out.println(biosamplesResponse.getBody());
    } catch (final Exception ex) {
      ex.printStackTrace();
    }
  }
}
