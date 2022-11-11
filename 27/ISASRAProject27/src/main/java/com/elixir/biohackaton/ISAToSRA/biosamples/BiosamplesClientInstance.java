/** Elixir BioHackathon 2022 */
package com.elixir.biohackaton.ISAToSRA.biosamples;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.apache.http.HeaderElement;
import org.apache.http.HeaderElementIterator;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.protocol.HTTP;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.mediatype.hal.Jackson2HalModule;
import org.springframework.hateoas.server.mvc.TypeConstrainedMappingJackson2HttpMessageConverter;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import uk.ac.ebi.biosamples.client.BioSamplesClient;
import uk.ac.ebi.biosamples.client.model.auth.AuthRealm;
import uk.ac.ebi.biosamples.client.service.ClientService;
import uk.ac.ebi.biosamples.client.service.WebinAuthClientService;
import uk.ac.ebi.biosamples.service.AttributeValidator;
import uk.ac.ebi.biosamples.service.SampleValidator;

public class BiosamplesClientInstance {

  public static BioSamplesClient buildBioSamplesClient(
      final BioSampleClientEnvironment bioSampleClientEnvironment) {
    final BioSamplesProperties bioSamplesProperties = new BioSamplesProperties();
    final RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();

    restTemplateBuilder.additionalCustomizers(
        new BioSampleClientRestTemplateCustomizer(bioSamplesProperties));

    return new BioSamplesClient(
        getBioSampleUri(bioSampleClientEnvironment),
        null,
        restTemplateBuilder,
        sampleValidator(),
        getWebinAuthClientService(restTemplateBuilder, bioSamplesProperties),
        bioSamplesProperties);
  }

  private static URI getBioSampleUri(final BioSampleClientEnvironment bioSampleClientEnvironment) {
    return getURI(bioSampleClientEnvironment);
  }

  private static class BioSampleClientRestTemplateCustomizer implements RestTemplateCustomizer {

    private final uk.ac.ebi.biosamples.BioSamplesProperties bioSamplesProperties;

    BioSampleClientRestTemplateCustomizer(
        final uk.ac.ebi.biosamples.BioSamplesProperties bioSamplesProperties) {
      this.bioSamplesProperties = bioSamplesProperties;
    }

    public void customize(final RestTemplate restTemplate) {

      // use a keep alive strategy to try to make it easier to maintain connections for reuse
      final ConnectionKeepAliveStrategy keepAliveStrategy =
          (response, context) -> {

            // check if there is a non-standard keep alive header present
            final HeaderElementIterator it =
                new BasicHeaderElementIterator(response.headerIterator(HTTP.CONN_KEEP_ALIVE));
            while (it.hasNext()) {
              final HeaderElement he = it.nextElement();
              final String param = he.getName();
              final String value = he.getValue();
              if (value != null && param.equalsIgnoreCase("timeout")) {
                return Long.parseLong(value) * 1000;
              }
            }
            // default to 15s if no header
            return 15 * 1000;
          };

      // set a number of connections to use at once for multiple threads
      final PoolingHttpClientConnectionManager poolingHttpClientConnectionManager =
          new PoolingHttpClientConnectionManager();
      poolingHttpClientConnectionManager.setMaxTotal(
          this.bioSamplesProperties.getBiosamplesClientConnectionCountMax());
      poolingHttpClientConnectionManager.setDefaultMaxPerRoute(
          this.bioSamplesProperties.getBiosamplesClientConnectionCountDefault());

      // set a timeout limit
      final int timeout = this.bioSamplesProperties.getBiosamplesClientTimeout();
      final RequestConfig config =
          RequestConfig.custom()
              .setConnectTimeout(timeout) // time to establish the connection with the remote host
              .setConnectionRequestTimeout(
                  timeout) // maximum time of inactivity between two data packets
              .setSocketTimeout(timeout)
              .build(); // time to wait for a connection from the connection manager/pool

      // make the actual client
      final HttpClient httpClient =
          HttpClientBuilder.create()
              .useSystemProperties()
              .setConnectionManager(poolingHttpClientConnectionManager)
              .setKeepAliveStrategy(keepAliveStrategy)
              .setDefaultRequestConfig(config)
              .build();

      // and wire it into the resttemplate
      restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(httpClient));

      // make sure there is a application/hal+json converter
      // traverson will make its own but not if we want to customize the resttemplate in any way
      // (e.g. caching)
      final List<HttpMessageConverter<?>> converters = restTemplate.getMessageConverters();
      final ObjectMapper mapper = new ObjectMapper();
      mapper.registerModule(new Jackson2HalModule());
      mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
      final MappingJackson2HttpMessageConverter halConverter =
          new TypeConstrainedMappingJackson2HttpMessageConverter(RepresentationModel.class);
      halConverter.setObjectMapper(mapper);
      halConverter.setSupportedMediaTypes(Collections.singletonList(MediaTypes.HAL_JSON));
      // make sure this is inserted first
      converters.add(0, halConverter);
      restTemplate.setMessageConverters(converters);
    }
  }

  private static SampleValidator sampleValidator() {
    return new SampleValidator(attributeValidator());
  }

  private static AttributeValidator attributeValidator() {
    return new AttributeValidator();
  }

  private static ClientService getWebinAuthClientService(
      final RestTemplateBuilder restTemplateBuilder,
      final uk.ac.ebi.biosamples.BioSamplesProperties bioSamplesProperties) {
    return new WebinAuthClientService(
        restTemplateBuilder,
        bioSamplesProperties.getBiosamplesWebinAuthTokenUri(),
        bioSamplesProperties.getBiosamplesClientWebinUsername(),
        bioSamplesProperties.getBiosamplesClientWebinPassword(),
        Arrays.asList(AuthRealm.ENA, AuthRealm.EGA)); // pass the realm
  }

  private static URI getURI(final BioSampleClientEnvironment bioSampleClientEnvironment) {
    try {
      return new URI(bioSampleClientEnvironment.uri());
    } catch (final URISyntaxException e) {
      throw new IllegalArgumentException(e);
    }
  }
}
