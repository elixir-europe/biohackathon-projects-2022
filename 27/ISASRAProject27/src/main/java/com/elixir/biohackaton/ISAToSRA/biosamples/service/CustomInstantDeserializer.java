/** Elixir BioHackathon 2022 */
package com.elixir.biohackaton.ISAToSRA.biosamples.service;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import java.io.IOException;
import java.time.Instant;
import java.time.format.DateTimeParseException;

public class CustomInstantDeserializer extends StdDeserializer<Instant> {

  public CustomInstantDeserializer() {
    this(null);
  }

  public CustomInstantDeserializer(Class<Instant> t) {
    super(t);
  }

  @Override
  public Instant deserialize(JsonParser jsonparser, DeserializationContext ctxt)
      throws IOException, JsonProcessingException {
    String date = jsonparser.getText();
    // TODO remove this hack
    if (!date.endsWith("Z")) {
      date = date + "Z";
    }
    try {
      return Instant.parse(date);
    } catch (DateTimeParseException e) {
      throw new RuntimeException(e);
    }
  }
}
