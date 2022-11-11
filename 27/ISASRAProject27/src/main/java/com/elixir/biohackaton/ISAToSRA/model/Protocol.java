/** Elixir BioHackathon 2022 */
package com.elixir.biohackaton.ISAToSRA.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import lombok.Data;

@Data
public class Protocol {
  @JsonProperty("@id")
  public String id;

  public String name;
  public ProtocolType protocolType;
  public String description;
  public String uri;
  public String version;
  public ArrayList<Parameter> parameters;
  public ArrayList<Component> components;
}
