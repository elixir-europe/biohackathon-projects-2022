/** Elixir BioHackathon 2022 */
package com.elixir.biohackaton.ISAToSRA.model;

import lombok.Data;

@Data
public class FactorValue {
  public Category category;
  public Value value;
  public Unit unit;
}
