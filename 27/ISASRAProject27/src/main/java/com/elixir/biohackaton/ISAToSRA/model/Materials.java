/** Elixir BioHackathon 2022 */
package com.elixir.biohackaton.ISAToSRA.model;

import java.util.ArrayList;
import lombok.Data;

@Data
public class Materials {
  public ArrayList<Source> sources;
  public ArrayList<Sample> samples;
  public ArrayList<OtherMaterial> otherMaterials;
}
