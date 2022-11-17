/** Elixir BioHackathon 2022 */
package com.elixir.biohackaton.ISAToSRA.sra.service;

import java.io.File;
import java.io.FileWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import org.apache.commons.codec.binary.Base64;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class WebinSubmitter {
  final RestTemplate restTemplate = new RestTemplate();
  private static final String webinSubmissionUrl =
      "https://wwwdev.ebi.ac.uk/ena/dev/submit/webin-v2/submit";

  public String performWebinSubmission(
      final String submissionAccountId, final String webinXml, final String webinPassword) {
    File file = null;

    try {
      file = File.createTempFile("webin_submission", ".xml");
      final FileWriter writer = new FileWriter(file);

      writer.write(webinXml);
      writer.close();
    } catch (final Exception e) {
      e.printStackTrace();
    }

    return performWebinV2Submission(file, submissionAccountId, webinPassword).getBody();
  }

  HttpHeaders createHeaders(final String username, final String password) {
    return new HttpHeaders() {
      {
        final String auth = username + ":" + password;
        final byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(StandardCharsets.US_ASCII));
        final String authHeader = "Basic " + new String(encodedAuth);

        set("Authorization", authHeader);
      }
    };
  }

  public ResponseEntity<String> performWebinV2Submission(
      final File file, final String submissionAccountId, final String webinPassword) {
    try {
      final HttpMethod requestMethod = HttpMethod.POST;
      final HttpHeaders headers = createHeaders(submissionAccountId, webinPassword);
      final MultiValueMap<String, String> fileMap = new LinkedMultiValueMap<>();
      final HttpEntity<byte[]> fileEntity =
          new HttpEntity<>(Files.readAllBytes(file.toPath()), fileMap);
      final MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
      final ContentDisposition contentDisposition =
          ContentDisposition.builder("form-data").name("file").filename(file.getName()).build();
      final HttpEntity<MultiValueMap<String, Object>> requestEntity;

      headers.setContentType(MediaType.MULTIPART_FORM_DATA);
      fileMap.add(HttpHeaders.CONTENT_DISPOSITION, contentDisposition.toString());
      body.add("file", fileEntity);
      requestEntity = new HttpEntity<>(body, headers);

      return restTemplate.exchange(webinSubmissionUrl, requestMethod, requestEntity, String.class);
    } catch (final Exception e) {
      throw new RuntimeException(e);
    }
  }
}
