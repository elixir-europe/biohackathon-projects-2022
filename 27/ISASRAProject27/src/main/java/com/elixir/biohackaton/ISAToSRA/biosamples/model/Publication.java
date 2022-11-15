/** Elixir BioHackathon 2022 */
package com.elixir.biohackaton.ISAToSRA.biosamples.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonDeserialize(builder = Publication.Builder.class)
public class Publication implements Comparable<Publication> {

  private String doi;
  private String pubmed_id;

  private Publication(String doi, String pubmed_id) {
    this.doi = doi;
    this.pubmed_id = pubmed_id;
  }

  @JsonProperty("doi")
  public String getDoi() {
    return this.doi;
  }

  @JsonProperty("pubmed_id")
  public String getPubMedId() {
    return this.pubmed_id;
  }

  @Override
  public boolean equals(Object o) {

    if (o == this) return true;
    if (!(o instanceof Publication)) {
      return false;
    }
    Publication other = (Publication) o;
    return Objects.equals(this.doi, other.doi) && Objects.equals(this.pubmed_id, other.pubmed_id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(doi, pubmed_id);
  }

  @Override
  public int compareTo(Publication other) {
    if (other == null) {
      return 1;
    }

    int comparisonResult = nullSafeStringComparison(this.doi, other.doi);
    if (comparisonResult != 0) {
      return comparisonResult;
    }

    return nullSafeStringComparison(this.pubmed_id, other.pubmed_id);
  }

  @Override
  public String toString() {
    return "Publication{" + "doi='" + doi + '\'' + ", pubmed_id='" + pubmed_id + '\'' + '}';
  }

  private int nullSafeStringComparison(String first, String other) {
    if (first == null && other == null) return 0;
    if (first == null) return -1;
    if (other == null) return 1;
    return first.compareTo(other);
  }

  //	@JsonCreator
  //	public static Publication build(@JsonProperty("doi") String doi,
  //			@JsonProperty("pubmed_id") String pubmedId) {
  //		return new Publication(doi, pubmedId);
  //	}

  public static class Builder {
    private String doi;
    private String pubmed_id;

    @JsonCreator
    public Builder() {}

    @JsonProperty("doi")
    public Builder doi(String doi) {
      this.doi = doi;
      return this;
    }

    @JsonProperty("pubmed_id")
    public Builder pubmed_id(String pubmed_id) {
      this.pubmed_id = pubmed_id;
      return this;
    }

    public Publication build() {
      return new Publication(doi, pubmed_id);
    }
  }
}
