/** Elixir BioHackathon 2022 */
package com.elixir.biohackaton.ISAToSRA.biosamples.service;

import com.elixir.biohackaton.ISAToSRA.biosamples.model.Attribute;
import com.elixir.biohackaton.ISAToSRA.biosamples.model.Relationship;
import com.elixir.biohackaton.ISAToSRA.biosamples.model.Sample;
import com.elixir.biohackaton.ISAToSRA.model.Study;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class BioSamplesSubmitter {
  public Map<String, String> createBioSamples(final List<Study> studies, final String webinToken) {
    final Map<String, String> typeToBioSamplesAccessionMap = new HashMap<>();

    try {
      final Sample sourceBioSample = this.createSourceBioSample(studies, webinToken);
      Attribute sourceBioSampleOrganismAttribute = null;

      for (final Attribute attribute : sourceBioSample.getAttributes()) {
          if (attribute.getType().equalsIgnoreCase("organism")) {
              sourceBioSampleOrganismAttribute = attribute;
          }
      }
      typeToBioSamplesAccessionMap.put("SOURCE", sourceBioSample.getAccession());

      if (sourceBioSampleOrganismAttribute != null) {
        final AtomicInteger counter = new AtomicInteger(0);
          final Attribute finalSourceBioSampleOrganismAttribute = sourceBioSampleOrganismAttribute;

          studies.forEach(
            study -> {
              study
                  .getAssays()
                  .forEach(
                      assay -> {
                        assay
                            .getMaterials()
                            .getSamples()
                            .forEach(
                                sample -> {
                                  final Sample persistedChildSample =
                                      this.createAndUpdateChildSampleWithRelationship(
                                          sample,
                                          sourceBioSample.getAccession(),
                                          finalSourceBioSampleOrganismAttribute.getValue(),
                                          webinToken);

                                  if (persistedChildSample != null) {
                                    typeToBioSamplesAccessionMap.put(
                                        "CHILD_" + counter.getAndIncrement(),
                                        persistedChildSample.getAccession());
                                  }
                                });
                      });
            });
      }
    } catch (final Exception e) {
      throw new RuntimeException("Failed to parse ISA Json and create samples in BioSamples", e);
    }

    return typeToBioSamplesAccessionMap;
  }

  private Sample createAndUpdateChildSampleWithRelationship(
      final com.elixir.biohackaton.ISAToSRA.model.Sample sample,
      final String sourceBioSampleAccession,
      final String parentSampleOrganism,
      final String webinToken) {
    final Sample bioSample =
        new Sample.Builder(sample.getName() != null ? sample.getName() : "testSample")
            .withRelease(Instant.now())
            .withAttributes(
                Collections.singletonList(Attribute.build("organism", parentSampleOrganism)))
            .build();
    try {
      final EntityModel<Sample> persistedSampleEntity =
          this.createSampleInBioSamples(bioSample, webinToken);

      if (persistedSampleEntity != null) {
        final Sample persistedBioSample = persistedSampleEntity.getContent();

        if (persistedBioSample != null) {
          final Sample sampleWithRelationship =
              Sample.Builder.fromSample(persistedBioSample)
                  .withRelationships(
                      Collections.singletonList(
                          Relationship.build(
                              persistedBioSample.getAccession(),
                              "derived from",
                              sourceBioSampleAccession)))
                  .build();

          return this.updateSampleWithRelationshipsToBioSamples(sampleWithRelationship, webinToken);
        } else {
          return null;
        }
      } else {
        return null;
      }
    } catch (final Exception e) {
      throw new RuntimeException("Failed to handle child samples", e);
    }
  }

  private Sample createSourceBioSample(final List<Study> studies, final String webinToken) {
    final AtomicReference<Attribute> organismAttribute =
        new AtomicReference<>(Attribute.build("", ""));
    final AtomicReference<Sample> sourceBioSample = new AtomicReference<>(null);

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
                                if (characteristic.getCategory().getId().contains("organism")) {
                                  organismAttribute.set(
                                      Attribute.build(
                                          "organism",
                                          characteristic.getValue().getAnnotationValue()));
                                }
                              });

                      final Sample sourceSample =
                          new Sample.Builder(source.getName())
                              .withRelease(Instant.now())
                              .withAttributes(Collections.singleton(organismAttribute.get()))
                              .build();
                      final EntityModel<Sample> persistedParentSampleEntity =
                          this.createSampleInBioSamples(sourceSample, webinToken);

                      if (persistedParentSampleEntity != null) {
                        sourceBioSample.set(persistedParentSampleEntity.getContent());
                      } else {
                        throw new RuntimeException("Failed to store source sample to BioSamples");
                      }
                    }));

    return sourceBioSample.get();
  }

  private Sample updateSampleWithRelationshipsToBioSamples(
      final Sample sampleWithRelationship, final String webinToken) {
    final RestTemplate restTemplate = new RestTemplate();
    final ResponseEntity<EntityModel<Sample>> biosamplesResponse;

    try {
      final HttpHeaders headers = getHttpHeaders(webinToken);
      final HttpEntity<?> entity = new HttpEntity<>(sampleWithRelationship, headers);

      biosamplesResponse =
          restTemplate.exchange(
              "https://wwwdev.ebi.ac.uk/biosamples/samples/" + sampleWithRelationship.getAccession(),
              HttpMethod.PUT,
              entity,
              new ParameterizedTypeReference<>() {});
      return biosamplesResponse.getBody().getContent();
    } catch (final Exception ex) {
      throw new RuntimeException("Failed to add relationships to child samples", ex);
    }
  }

  private EntityModel<Sample> createSampleInBioSamples(
      final Sample sample, final String webinToken) {
    final RestTemplate restTemplate = new RestTemplate();
    final ResponseEntity<EntityModel<Sample>> biosamplesResponse;

    try {
      final HttpHeaders headers = getHttpHeaders(webinToken);
      final HttpEntity<?> entity = new HttpEntity<>(sample, headers);

      biosamplesResponse =
          restTemplate.exchange(
              "https://wwwdev.ebi.ac.uk/biosamples/samples/",
              HttpMethod.POST,
              entity,
              new ParameterizedTypeReference<>() {});

      return biosamplesResponse.getBody();
    } catch (final Exception ex) {
      throw new RuntimeException("Failed to create samples in BioSamples", ex);
    }
  }

  private static HttpHeaders getHttpHeaders(String webinToken) {
    final HttpHeaders headers =
        new HttpHeaders() {
          {
            final String authHeader = "Bearer " + webinToken;
            this.set("Authorization", authHeader);
          }
        };
    headers.add("Content-Type", "application/json;charset=UTF-8");
    headers.add("Accept", "application/json");
    return headers;
  }
}
