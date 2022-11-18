/** Elixir BioHackathon 2022 */
package com.elixir.biohackaton.ISAToSRA.biosamples.service;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import java.io.IOException;
import java.time.Instant;
import java.time.format.DateTimeFormatter;

public class CustomInstantSerializer extends StdSerializer<Instant> {

  public CustomInstantSerializer() {
    this(null);
  }

  public CustomInstantSerializer(Class<Instant> t) {
    super(t);
  }

  @Override
  public void serialize(Instant value, JsonGenerator gen, SerializerProvider arg2)
      throws IOException {
    gen.writeString(DateTimeFormatter.ISO_INSTANT.format(value));
  }
}
