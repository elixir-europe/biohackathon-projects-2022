/** Elixir BioHackathon 2022 */
package com.elixir.biohackaton.ISAToSRA.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import lombok.Data;

@Data
public class OtherMaterial {
  @JsonProperty("@id")
  public String id; // other_material-333

  public String name;
  public String type; // = library name
  public ArrayList<Characteristic> characteristics; // -> get characteristics
  public ArrayList<DerivesFrom> derivesFrom;
}
