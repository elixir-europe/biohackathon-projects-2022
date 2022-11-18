/** Elixir BioHackathon 2022 */
package com.elixir.biohackaton.ISAToSRA.biosamples.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Iterator;
import java.util.Objects;
import java.util.SortedSet;
import java.util.TreeSet;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

public class ExternalReference implements Comparable<ExternalReference> {
  private final String url;
  private final String hash;
  private final SortedSet<String> duo;

  private ExternalReference(String url, String hash, SortedSet<String> duo) {
    this.url = url;
    this.hash = hash;
    this.duo = duo;
  }

  public String getUrl() {
    return this.url;
  }

  @JsonIgnore
  public String getHash() {
    return this.hash;
  }

  public SortedSet<String> getDuo() {
    return duo;
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) return true;
    if (!(o instanceof ExternalReference)) {
      return false;
    }
    ExternalReference other = (ExternalReference) o;
    return Objects.equals(this.url, other.url) && Objects.equals(this.duo, other.duo);
  }

  @Override
  public int hashCode() {
    return Objects.hash(hash);
  }

  @Override
  public int compareTo(ExternalReference other) {
    if (other == null) {
      return 1;
    }

    if (!this.url.equals(other.url)) {
      return this.url.compareTo(other.url);
    }

    if (this.duo == other.duo) {
      return 0;
    } else if (other.duo == null) {
      return 1;
    } else if (this.duo == null) {
      return -1;
    }

    if (!this.duo.equals(other.duo)) {
      if (this.duo.size() < other.duo.size()) {
        return -1;
      } else if (this.duo.size() > other.duo.size()) {
        return 1;
      } else {
        Iterator<String> thisIt = this.duo.iterator();
        Iterator<String> otherIt = other.duo.iterator();
        while (thisIt.hasNext() && otherIt.hasNext()) {
          int val = thisIt.next().compareTo(otherIt.next());
          if (val != 0) return val;
        }
      }
    }
    return 0;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("ExternalReference(");
    sb.append(this.url);
    sb.append(",");
    sb.append(duo);
    sb.append(")");
    return sb.toString();
  }

  public static ExternalReference build(String url, SortedSet<String> duo) {
    UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromUriString(url);
    UriComponents uriComponents = uriComponentsBuilder.build().normalize();

    url = uriComponents.toUriString();

    return new ExternalReference(url, null, duo);
  }

  @JsonCreator
  public static ExternalReference build(@JsonProperty("url") String url) {
    return build(url, new TreeSet<>());
  }
}
