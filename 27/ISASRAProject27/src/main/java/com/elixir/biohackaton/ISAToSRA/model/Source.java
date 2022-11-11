/** Elixir BioHackathon 2022 */
package com.elixir.biohackaton.ISAToSRA.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import lombok.Data;

@Data
public class Source {
  @JsonProperty("@id")
  public String id;

  public String name;
  public ArrayList<Characteristic> characteristics;
}
