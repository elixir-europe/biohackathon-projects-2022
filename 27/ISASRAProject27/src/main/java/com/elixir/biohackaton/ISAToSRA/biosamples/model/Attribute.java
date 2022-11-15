/** Elixir BioHackathon 2022 */
package com.elixir.biohackaton.ISAToSRA.biosamples.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.*;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Attribute implements Comparable<Attribute> {
  private String type;
  private String value;
  private SortedSet<String> iri;
  private String unit;
  private String tag;

  private Attribute() {}

  @JsonProperty("type")
  public String getType() {
    return type;
  }

  @JsonProperty("value")
  public String getValue() {
    return value;
  }

  @JsonProperty("tag")
  public String getTag() {
    return tag;
  }

  @JsonProperty("iri")
  public SortedSet<String> getIri() {
    return iri;
  }

  @JsonProperty("unit")
  public String getUnit() {
    return unit;
  }

  @Override
  public boolean equals(Object o) {

    if (o == this) return true;
    if (!(o instanceof Attribute)) {
      return false;
    }
    Attribute other = (Attribute) o;
    return Objects.equals(this.type, other.type)
        && Objects.equals(this.value, other.value)
        && Objects.equals(this.tag, other.tag)
        && Objects.equals(this.iri, other.iri)
        && Objects.equals(this.unit, other.unit);
  }

  @Override
  public int hashCode() {
    return Objects.hash(type, value, tag, iri, unit);
  }

  @Override
  public int compareTo(Attribute other) {
    if (other == null) {
      return 1;
    }

    //		if (!this.type.equals(other.type)) {
    //			return this.type.compareTo(other.type);
    //		}
    int comparison = nullSafeStringComparison(this.type, other.type);
    if (comparison != 0) {
      return comparison;
    }

    //		if (!this.value.equals(other.value)) {
    //			return this.value.compareTo(other.value);
    //		}
    comparison = nullSafeStringComparison(this.value, other.value);
    if (comparison != 0) {
      return comparison;
    }

    comparison = nullSafeStringComparison(this.tag, other.tag);
    if (comparison != 0) {
      return comparison;
    }

    if (this.iri == null && other.iri != null) {
      return -1;
    }
    if (this.iri != null && other.iri == null) {
      return 1;
    }
    if (!this.iri.equals(other.iri)) {
      if (this.iri.size() < other.iri.size()) {
        return -1;
      } else if (this.iri.size() > other.iri.size()) {
        return 1;
      } else {
        Iterator<String> thisIt = this.iri.iterator();
        Iterator<String> otherIt = other.iri.iterator();
        while (thisIt.hasNext() && otherIt.hasNext()) {
          int val = thisIt.next().compareTo(otherIt.next());
          if (val != 0) return val;
        }
      }
    }

    //		if (this.unit == null && other.unit != null) {
    //			return -1;
    //		}
    //		if (this.unit != null && other.unit == null) {
    //			return 1;
    //		}
    //		if (this.unit != null && other.unit != null
    //				&& !this.unit.equals(other.unit)) {
    //			return this.unit.compareTo(other.unit);
    //		}
    //
    //		return 0;
    return nullSafeStringComparison(this.unit, other.unit);
  }

  public int nullSafeStringComparison(String one, String two) {

    if (one == null && two != null) {
      return -1;
    }
    if (one != null && two == null) {
      return 1;
    }
    if (one != null && !one.equals(two)) {
      return one.compareTo(two);
    }

    return 0;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("Attribute(");
    sb.append(type);
    sb.append(",");
    sb.append(value);
    sb.append(",");
    sb.append(tag);
    sb.append(",");
    sb.append(iri);
    sb.append(",");
    sb.append(unit);
    sb.append(")");
    return sb.toString();
  }

  public static Attribute build(String type, String value) {
    return build(type, value, null, Collections.emptyList(), null);
  }

  public static Attribute build(String type, String value, String iri, String unit) {
    if (iri == null) iri = "";
    return build(type, value, null, Collections.singleton(iri), unit);
  }

  public static Attribute build(String type, String value, String tag, String iri, String unit) {
    if (iri == null) iri = "";
    return build(type, value, tag, Collections.singleton(iri), unit);
  }

  @JsonCreator
  public static Attribute build(
      @JsonProperty("type") String type,
      @JsonProperty("value") String value,
      @JsonProperty("tag") String tag,
      @JsonProperty("iri") Collection<String> iri,
      @JsonProperty("unit") String unit) {
    // check for nulls
    if (type == null) {
      throw new IllegalArgumentException("type must not be null");
    }
    if (value == null) {
      value = "";
    }
    if (iri == null) {
      iri = Collections.emptyList();
    }
    // cleanup inputs
    if (type != null) type = type.trim();
    if (value != null) value = value.trim();
    if (tag != null) tag = tag.trim();
    if (unit != null) unit = unit.trim();
    // create output
    Attribute attr = new Attribute();
    attr.type = type;
    attr.value = value;
    attr.tag = tag;
    attr.iri = new TreeSet<>();
    if (iri != null) {
      for (String iriOne : iri) {
        if (iriOne != null) {
          attr.iri.add(iriOne);
        }
      }
    }
    attr.unit = unit;
    return attr;
  }

  public static class Builder {
    private final String type;
    private final String value;
    private List<String> iris = new ArrayList<>();
    private String unit;
    private String tag;

    public Builder(String type, String value, String tag) {
      this.type = type;
      this.value = value;
      this.tag = tag;
    }

    public Builder(String type, String value) {
      this.type = type;
      this.value = value;
    }

    public Builder withIri(String iri) {
      iris.add(iri);
      return this;
    }

    public Builder withUnit(String unit) {
      this.unit = unit;
      return this;
    }

    public Builder withTag(String tag) {
      this.tag = tag;
      return this;
    }

    public Attribute build() {
      return Attribute.build(this.type, this.value, this.tag, this.iris, this.unit);
    }
  }
}
