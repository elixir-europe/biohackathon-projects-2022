/** Elixir BioHackathon 2022 */
package com.elixir.biohackaton.ISAToSRA.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class DerivesFrom {
  @JsonProperty("@id")
  public String id;
}
