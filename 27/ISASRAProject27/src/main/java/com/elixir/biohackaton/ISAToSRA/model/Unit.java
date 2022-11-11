/** Elixir BioHackathon 2022 */
package com.elixir.biohackaton.ISAToSRA.model;

import java.util.ArrayList;
import lombok.Data;

@Data
public class Unit {
  public String termSource;
  public String termAccession;
  public ArrayList<Object> comments;
}
