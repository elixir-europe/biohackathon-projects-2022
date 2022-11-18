/** Elixir BioHackathon 2022 */
package com.elixir.biohackaton.ISAToSRA.model;

import java.util.ArrayList;
import lombok.Data;

@Data
public class Investigation {
  public String identifier;
  public String title;
  public String description;
  public String submissionDate;
  public String publicReleaseDate;
  public ArrayList<Object> ontologySourceReferences;
  public String filename;
  public ArrayList<Comment> comments;
  public ArrayList<Object> publications;
  public ArrayList<Person> people;
  public ArrayList<Study> studies;
}
