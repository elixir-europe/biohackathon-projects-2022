/** Elixir BioHackathon 2022 */
package com.elixir.biohackaton.ISAToSRA.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import lombok.Data;

@Data
public class Sample {
  @JsonProperty("@id")
  public String id;

  public String name;
  public ArrayList<DerivesFrom> derivesFrom;
  public ArrayList<Object> characteristics;
  public ArrayList<FactorValue> factorValues;
}
