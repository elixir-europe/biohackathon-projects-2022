/** Elixir BioHackathon 2022 */
package com.elixir.biohackaton.ISAToSRA.biosamples.model;

public enum RelationshipType {
  DERIVED_FROM,
  SAME_AS,
  CHILD_OF,
  HAS_MEMBER,
  EXTERNAL_REFERENCE,
  OTHER,
  ANY;

  public static RelationshipType getType(String relationshipTypeString) {
    RelationshipType type;
    try {
      type = RelationshipType.valueOf(relationshipTypeString.replace(" ", "_").toUpperCase());
    } catch (IllegalArgumentException e) {
      type = RelationshipType.OTHER;
    }

    return type;
  }
}
