/** Elixir BioHackathon 2022 */
package com.elixir.biohackaton.ISAToSRA.model;

import lombok.Data;

@Data
public class Value {
  public String annotationValue;
  public String termSource;
  public String termAccession;
}
