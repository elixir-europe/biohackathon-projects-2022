/** Elixir BioHackathon 2022 */
package com.elixir.biohackaton.ISAToSRA.biosamples.model;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;
import static java.time.format.DateTimeFormatter.ISO_LOCAL_TIME;

import com.elixir.biohackaton.ISAToSRA.biosamples.service.CharacteristicDeserializer;
import com.elixir.biohackaton.ISAToSRA.biosamples.service.CharacteristicSerializer;
import com.elixir.biohackaton.ISAToSRA.biosamples.service.CustomInstantDeserializer;
import com.elixir.biohackaton.ISAToSRA.biosamples.service.CustomInstantSerializer;
import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.TemporalAccessor;
import java.util.*;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder({
  "name",
  "accession",
  "domain",
  "webinSubmissionAccountId",
  "taxId",
  "release",
  "update",
  "submitted",
  "characteristics",
  "relationships",
  "externalReferences",
  "releaseDate",
  "updateDate",
  "submittedDate",
  "submittedVia"
})
public class Sample implements Comparable<Sample> {
  protected String accession;
  protected String name;

  /** This is the unique permanent ID of the AAP domain/team that owns this sample. */
  protected String domain;

  protected String webinSubmissionAccountId;

  protected Long taxId;

  protected Instant release;
  protected Instant update;
  protected Instant create;
  protected Instant submitted;
  protected Instant reviewed;

  protected SortedSet<Attribute> attributes;
  protected SortedSet<Relationship> relationships;
  protected SortedSet<ExternalReference> externalReferences;

  protected SortedSet<Organization> organizations;
  protected SortedSet<Contact> contacts;
  protected SortedSet<Publication> publications;
  protected SubmittedViaType submittedVia;

  protected Sample() {}

  @JsonProperty("accession")
  public String getAccession() {
    return accession;
  }

  @JsonIgnore
  public boolean hasAccession() {
    if (accession != null && accession.trim().length() != 0) {
      return true;
    } else {
      return false;
    }
  }

  @JsonProperty("name")
  public String getName() {
    return name;
  }

  @JsonProperty("domain")
  public String getDomain() {
    return domain;
  }

  @JsonProperty("webinSubmissionAccountId")
  public String getWebinSubmissionAccountId() {
    return webinSubmissionAccountId;
  }

  // DO NOT specify the JSON property value manually, must be autoinferred or errors
  @JsonSerialize(using = CustomInstantSerializer.class)
  public Instant getRelease() {
    return release;
  }

  // DO NOT specify the JSON property value manually, must be autoinferred or errors
  @JsonSerialize(using = CustomInstantSerializer.class)
  public Instant getUpdate() {
    return update;
  }

  @JsonSerialize(using = CustomInstantSerializer.class)
  public Instant getCreate() {
    return create;
  }

  @JsonSerialize(using = CustomInstantSerializer.class)
  public Instant getSubmitted() {
    return submitted;
  }

  @JsonSerialize(using = CustomInstantSerializer.class)
  @JsonIgnore
  public Instant getReviewed() {
    return reviewed;
  }

  @JsonProperty(value = "releaseDate", access = JsonProperty.Access.READ_ONLY)
  @JsonIgnore
  public String getReleaseDate() {
    return release != null
        ? ZonedDateTime.ofInstant(release, ZoneOffset.UTC).format(ISO_LOCAL_DATE)
        : null;
  }

  @JsonProperty(value = "updateDate", access = JsonProperty.Access.READ_ONLY)
  @JsonIgnore
  public String getUpdateDate() {
    return update != null
        ? ZonedDateTime.ofInstant(update, ZoneOffset.UTC).format(ISO_LOCAL_DATE)
        : null;
  }

  @JsonProperty(value = "submittedDate", access = JsonProperty.Access.READ_ONLY)
  @JsonIgnore
  public String getSubmittedDate() {
    return submitted != null
        ? ZonedDateTime.ofInstant(submitted, ZoneOffset.UTC).format(ISO_LOCAL_DATE)
        : null;
  }

  @JsonProperty(value = "reviewedDate", access = JsonProperty.Access.READ_ONLY)
  @JsonIgnore
  public String getReviewedDate() {
    return reviewed != null
        ? ZonedDateTime.ofInstant(reviewed, ZoneOffset.UTC).format(ISO_LOCAL_DATE)
        : null;
  }

  @JsonProperty(value = "taxId")
  public Long getTaxId() {
    if (taxId != null) {
      return taxId;
    }

    Optional<Long> taxon = Optional.empty();
    for (Attribute attribute : attributes) {
      if ("organism".equalsIgnoreCase(attribute.getType()) && !attribute.getIri().isEmpty()) {
        taxon =
            attribute.getIri().stream()
                .map(this::extractTaxIdFromIri)
                .filter(i -> i > 0)
                .findFirst();
        break;
      }
    }

    return taxon.orElse(null);
  }

  private long extractTaxIdFromIri(String iri) {
    if (iri.isEmpty()) return 0;
    String[] segments = iri.split("NCBITaxon_");
    try {
      return Integer.parseInt(segments[segments.length - 1]);
    } catch (NumberFormatException e) {
      return 0;
    }
  }

  @JsonIgnore
  public SortedSet<Attribute> getAttributes() {
    return attributes;
  }

  // DO NOT specify the JSON property value manually, must be autoinferred or errors
  @JsonSerialize(using = CharacteristicSerializer.class)
  public SortedSet<Attribute> getCharacteristics() {
    return attributes;
  }

  @JsonProperty("relationships")
  public SortedSet<Relationship> getRelationships() {
    return relationships;
  }

  @JsonProperty("externalReferences")
  public SortedSet<ExternalReference> getExternalReferences() {
    return externalReferences;
  }

  @JsonProperty("organization")
  public SortedSet<Organization> getOrganizations() {
    return organizations;
  }

  @JsonProperty("contact")
  public SortedSet<Contact> getContacts() {
    return contacts;
  }

  @JsonProperty("publications")
  public SortedSet<Publication> getPublications() {
    return publications;
  }

  @JsonProperty("submittedVia")
  public SubmittedViaType getSubmittedVia() {
    return submittedVia;
  }

  @Override
  public boolean equals(Object o) {

    if (o == this) return true;
    if (!(o instanceof Sample)) {
      return false;
    }
    Sample other = (Sample) o;

    // dont use update date for comparisons, too volatile. SubmittedVia doesnt contain information
    // for comparison

    return Objects.equals(this.name, other.name)
        && Objects.equals(this.accession, other.accession)
        && Objects.equals(this.domain, other.domain)
        && Objects.equals(this.webinSubmissionAccountId, other.webinSubmissionAccountId)
        && Objects.equals(this.taxId, other.taxId)
        && Objects.equals(this.release, other.release)
        && Objects.equals(this.attributes, other.attributes)
        && Objects.equals(this.relationships, other.relationships)
        && Objects.equals(this.externalReferences, other.externalReferences)
        && Objects.equals(this.organizations, other.organizations)
        && Objects.equals(this.contacts, other.contacts)
        && Objects.equals(this.publications, other.publications);
  }

  @Override
  public int compareTo(Sample other) {
    if (other == null) {
      return 1;
    }

    if (!this.accession.equals(other.accession)) {
      return this.accession.compareTo(other.accession);
    }

    if (!this.name.equals(other.name)) {
      return this.name.compareTo(other.name);
    }

    if (!this.taxId.equals(other.taxId)) {
      return this.taxId.compareTo(other.taxId);
    }

    if (!this.release.equals(other.release)) {
      return this.release.compareTo(other.release);
    }

    if (!this.attributes.equals(other.attributes)) {
      if (this.attributes.size() < other.attributes.size()) {
        return -1;
      } else if (this.attributes.size() > other.attributes.size()) {
        return 1;
      } else {
        Iterator<Attribute> thisIt = this.attributes.iterator();
        Iterator<Attribute> otherIt = other.attributes.iterator();
        while (thisIt.hasNext() && otherIt.hasNext()) {
          int val = thisIt.next().compareTo(otherIt.next());
          if (val != 0) return val;
        }
      }
    }
    if (!this.relationships.equals(other.relationships)) {
      if (this.relationships.size() < other.relationships.size()) {
        return -1;
      } else if (this.relationships.size() > other.relationships.size()) {
        return 1;
      } else {
        Iterator<Relationship> thisIt = this.relationships.iterator();
        Iterator<Relationship> otherIt = other.relationships.iterator();
        while (thisIt.hasNext() && otherIt.hasNext()) {
          int val = thisIt.next().compareTo(otherIt.next());
          if (val != 0) return val;
        }
      }
    }
    if (!this.externalReferences.equals(other.externalReferences)) {
      if (this.externalReferences.size() < other.externalReferences.size()) {
        return -1;
      } else if (this.externalReferences.size() > other.externalReferences.size()) {
        return 1;
      } else {
        Iterator<ExternalReference> thisIt = this.externalReferences.iterator();
        Iterator<ExternalReference> otherIt = other.externalReferences.iterator();
        while (thisIt.hasNext() && otherIt.hasNext()) {
          int val = thisIt.next().compareTo(otherIt.next());
          if (val != 0) return val;
        }
      }
    }
    if (!this.organizations.equals(other.organizations)) {
      if (this.organizations.size() < other.organizations.size()) {
        return -1;
      } else if (this.organizations.size() > other.organizations.size()) {
        return 1;
      } else {
        Iterator<Organization> thisIt = this.organizations.iterator();
        Iterator<Organization> otherIt = other.organizations.iterator();
        while (thisIt.hasNext() && otherIt.hasNext()) {
          int val = thisIt.next().compareTo(otherIt.next());
          if (val != 0) return val;
        }
      }
    }
    if (!this.contacts.equals(other.contacts)) {
      if (this.contacts.size() < other.contacts.size()) {
        return -1;
      } else if (this.contacts.size() > other.contacts.size()) {
        return 1;
      } else {
        Iterator<Contact> thisIt = this.contacts.iterator();
        Iterator<Contact> otherIt = other.contacts.iterator();
        while (thisIt.hasNext() && otherIt.hasNext()) {
          int val = thisIt.next().compareTo(otherIt.next());
          if (val != 0) return val;
        }
      }
    }
    if (!this.publications.equals(other.publications)) {
      if (this.publications.size() < other.publications.size()) {
        return -1;
      } else if (this.publications.size() > other.publications.size()) {
        return 1;
      } else {
        Iterator<Publication> thisIt = this.publications.iterator();
        Iterator<Publication> otherIt = other.publications.iterator();
        while (thisIt.hasNext() && otherIt.hasNext()) {
          int val = thisIt.next().compareTo(otherIt.next());
          if (val != 0) return val;
        }
      }
    }
    return 0;
  }

  @Override
  public int hashCode() {
    // dont put update date in the hash because its not in comparison
    return Objects.hash(
        name,
        accession,
        taxId,
        release,
        attributes,
        relationships,
        externalReferences,
        organizations,
        publications);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("Sample(");
    sb.append(name);
    sb.append(",");
    sb.append(accession);
    sb.append(",");
    sb.append(domain);
    sb.append(",");
    sb.append(webinSubmissionAccountId);
    sb.append(",");
    sb.append(taxId);
    sb.append(",");
    sb.append(release);
    sb.append(",");
    sb.append(update);
    sb.append(",");
    sb.append(create);
    sb.append(",");
    sb.append(submitted);
    sb.append(",");
    sb.append(attributes);
    sb.append(",");
    sb.append(relationships);
    sb.append(",");
    sb.append(externalReferences);
    sb.append(",");
    sb.append(organizations);
    sb.append(",");
    sb.append(contacts);
    sb.append(",");
    sb.append(publications);
    sb.append(",");
    sb.append(submittedVia);
    sb.append(")");
    return sb.toString();
  }

  public static Sample build(
      String name,
      String accession,
      String domain,
      String webinSubmissionAccountId,
      Long taxId,
      Instant release,
      Instant update,
      Instant create,
      Instant submitted,
      Instant reviewed,
      Set<Attribute> attributes,
      Set<Relationship> relationships,
      Set<ExternalReference> externalReferences) {
    return build(
        name,
        accession,
        domain,
        webinSubmissionAccountId,
        taxId,
        release,
        update,
        create,
        submitted,
        reviewed,
        attributes,
        relationships,
        externalReferences,
        null,
        null,
        null,
        null);
  }

  public static Sample build(
      String name,
      String accession,
      String domain,
      String webinSubmissionAccountId,
      Long taxId,
      Instant release,
      Instant update,
      Instant create,
      Instant submitted,
      Instant reviewed,
      Set<Attribute> attributes,
      Set<Relationship> relationships,
      Set<ExternalReference> externalReferences,
      SubmittedViaType submittedVia) {
    return build(
        name,
        accession,
        domain,
        webinSubmissionAccountId,
        taxId,
        release,
        update,
        create,
        submitted,
        reviewed,
        attributes,
        relationships,
        externalReferences,
        null,
        null,
        null,
        submittedVia);
  }

  // Used for deserializtion (JSON -> Java)
  @JsonCreator
  public static Sample build(
      @JsonProperty("name") String name,
      @JsonProperty("accession") String accession,
      @JsonProperty("domain") String domain,
      @JsonProperty("webinSubmissionAccountId") String webinSubmissionAccountId,
      @JsonProperty("taxId") Long taxId,
      @JsonProperty("release") @JsonDeserialize(using = CustomInstantDeserializer.class)
          Instant release,
      @JsonProperty("update") @JsonDeserialize(using = CustomInstantDeserializer.class)
          Instant update,
      @JsonProperty("create") @JsonDeserialize(using = CustomInstantDeserializer.class)
          Instant create,
      @JsonProperty("submitted") @JsonDeserialize(using = CustomInstantDeserializer.class)
          Instant submitted,
      @JsonProperty("reviewed") @JsonDeserialize(using = CustomInstantDeserializer.class)
          Instant reviewed,
      @JsonProperty("characteristics") @JsonDeserialize(using = CharacteristicDeserializer.class)
          Collection<Attribute> attributes,
      @JsonProperty("relationships") Collection<Relationship> relationships,
      @JsonProperty("externalReferences") Collection<ExternalReference> externalReferences,
      @JsonProperty("organization") Collection<Organization> organizations,
      @JsonProperty("contact") Collection<Contact> contacts,
      @JsonProperty("publications") Collection<Publication> publications,
      @JsonProperty("submittedVia") SubmittedViaType submittedVia) {

    Sample sample = new Sample();

    if (accession != null) {
      sample.accession = accession.trim();
    }

    if (name == null) throw new IllegalArgumentException("Sample name must be provided");
    sample.name = name.trim();

    if (domain != null) {
      sample.domain = domain.trim();
    }

    if (webinSubmissionAccountId != null) {
      sample.webinSubmissionAccountId = webinSubmissionAccountId.trim();
    }

    if (taxId != null) {
      sample.taxId = taxId;
    }

    // Instead of validation failure, if null, set it to now
    sample.update = update == null ? Instant.now() : update;

    sample.create = create == null ? sample.update : create;

    sample.submitted = submitted;

    sample.reviewed = reviewed;

    // Validation moved to a later stage, to capture the error (SampleService.store())
    sample.release = release;

    sample.attributes = new TreeSet<>();
    if (attributes != null) {
      sample.attributes.addAll(attributes);
    }

    sample.relationships = new TreeSet<>();
    if (relationships != null) {
      sample.relationships.addAll(relationships);
    }

    sample.externalReferences = new TreeSet<>();
    if (externalReferences != null) {
      sample.externalReferences.addAll(externalReferences);
    }

    sample.organizations = new TreeSet<>();
    if (organizations != null) {
      sample.organizations.addAll(organizations);
    }

    sample.contacts = new TreeSet<>();
    if (contacts != null) {
      sample.contacts.addAll(contacts);
    }

    sample.publications = new TreeSet<>();
    if (publications != null) {
      sample.publications.addAll(publications);
    }

    if (submittedVia != null) {
      sample.submittedVia = submittedVia;
    } else {
      sample.submittedVia = SubmittedViaType.JSON_API;
    }

    return sample;
  }

  public static class Builder {

    protected String name;

    protected String accession = null;
    protected String domain = null;
    protected String webinSubmissionAccountId = null;

    protected Long taxId = null;

    protected Instant release = Instant.now();
    protected Instant update = Instant.now();
    protected Instant create = Instant.now();
    protected Instant submitted = Instant.now();
    protected Instant reviewed;

    protected SubmittedViaType submittedVia;

    protected SortedSet<Attribute> attributes = new TreeSet<>();
    protected SortedSet<Relationship> relationships = new TreeSet<>();
    protected SortedSet<ExternalReference> externalReferences = new TreeSet<>();
    protected SortedSet<Organization> organizations = new TreeSet<>();
    protected SortedSet<Contact> contacts = new TreeSet<>();
    protected SortedSet<Publication> publications = new TreeSet<>();

    public Builder(String name, String accession) {
      this.name = name;
      this.accession = accession;
    }

    public Builder(String name) {
      this.name = name;
    }

    public Builder withAccession(String accession) {
      this.accession = accession;
      return this;
    }

    public Builder withDomain(String domain) {
      this.domain = domain;
      return this;
    }

    public Builder withWebinSubmissionAccountId(String webinSubmissionAccountId) {
      this.webinSubmissionAccountId = webinSubmissionAccountId;
      return this;
    }

    public Builder withTaxId(Long taxId) {
      this.taxId = taxId;
      return this;
    }

    public Builder withRelease(String release) {
      this.release = parseDateTime(release).toInstant();
      return this;
    }

    public Builder withRelease(Instant release) {
      this.release = release;
      return this;
    }

    public Builder withUpdate(Instant update) {
      this.update = update;
      return this;
    }

    public Builder withUpdate(String update) {
      this.update = parseDateTime(update).toInstant();
      return this;
    }

    public Builder withCreate(Instant create) {
      this.create = create;
      return this;
    }

    public Builder withCreate(String create) {
      this.create = parseDateTime(create).toInstant();
      return this;
    }

    public Builder withSubmitted(Instant submitted) {
      this.submitted = submitted;
      return this;
    }

    public Builder withSubmitted(String submitted) {
      this.submitted = parseDateTime(submitted).toInstant();
      return this;
    }

    public Builder withNoSubmitted() {
      this.submitted = null;
      return this;
    }

    public Builder withReviewed(Instant reviewed) {
      this.reviewed = reviewed;
      return this;
    }

    public Builder withReviewed(String reviewed) {
      this.reviewed = parseDateTime(reviewed).toInstant();
      return this;
    }

    public Builder withNoReviewed() {
      this.reviewed = null;
      return this;
    }

    public Builder withSubmittedVia(SubmittedViaType submittedVia) {
      this.submittedVia = submittedVia;
      return this;
    }

    /**
     * Replace builder's attributes with the provided attribute collection
     *
     * @param attributes
     * @return
     */
    public Builder withAttributes(Collection<Attribute> attributes) {
      this.attributes = new TreeSet<>(attributes);
      return this;
    }

    public Builder addAttribute(Attribute attribute) {
      this.attributes.add(attribute);
      return this;
    }

    public Builder addAllAttributes(Collection<Attribute> attributes) {
      this.attributes.addAll(attributes);
      return this;
    }

    /**
     * Replace builder's relationships with the provided relationships collection
     *
     * @param relationships
     * @return
     */
    public Builder withRelationships(Collection<Relationship> relationships) {
      if (relationships != null) this.relationships = new TreeSet<>(relationships);
      return this;
    }

    public Builder addRelationship(Relationship relationship) {
      this.relationships.add(relationship);
      return this;
    }

    public Builder addAllRelationships(Collection<Relationship> relationships) {
      this.relationships.addAll(relationships);
      return this;
    }

    /**
     * Replace builder's externalReferences with the provided external references collection
     *
     * @param externalReferences
     * @return
     */
    public Builder withExternalReferences(Collection<ExternalReference> externalReferences) {
      if (externalReferences != null && externalReferences.size() > 0)
        this.externalReferences = new TreeSet<>(externalReferences);
      return this;
    }

    public Builder addExternalReference(ExternalReference externalReference) {
      this.externalReferences.add(externalReference);
      return this;
    }

    public Builder addAllExternalReferences(Collection<ExternalReference> externalReferences) {
      this.externalReferences.addAll(externalReferences);
      return this;
    }

    /**
     * Replace builder's organisations with the provided organisation collection
     *
     * @param organizations
     * @return
     */
    public Builder withOrganizations(Collection<Organization> organizations) {
      if (organizations != null && organizations.size() > 0)
        this.organizations = new TreeSet<>(organizations);
      return this;
    }

    public Builder addOrganization(Organization organization) {
      this.organizations.add(organization);
      return this;
    }

    public Builder allAllOrganizations(Collection<Organization> organizations) {
      this.organizations.addAll(organizations);
      return this;
    }

    /**
     * Replace builder's contacts with the provided contact collection
     *
     * @param contacts
     * @return
     */
    public Builder withContacts(Collection<Contact> contacts) {
      if (contacts != null && contacts.size() > 0) this.contacts = new TreeSet<>(contacts);
      return this;
    }

    public Builder addContact(Contact contact) {
      this.contacts.add(contact);
      return this;
    }

    public Builder addAllContacts(Collection<Contact> contacts) {
      this.contacts.addAll(contacts);
      return this;
    }

    /**
     * Replace the publications with the provided collections
     *
     * @param publications
     * @return
     */
    public Builder withPublications(Collection<Publication> publications) {
      if (publications != null && publications.size() > 0)
        this.publications = new TreeSet<>(publications);
      return this;
    }

    /**
     * Add a publication to the list of builder publications
     *
     * @param publication
     * @return
     */
    public Builder addPublication(Publication publication) {
      this.publications.add(publication);
      return this;
    }

    /**
     * Add all publications in the provided collection to the builder publications
     *
     * @param publications
     * @return
     */
    public Builder addAllPublications(Collection<Publication> publications) {
      this.publications.addAll(publications);
      return this;
    }

    // Clean accession field
    public Builder withNoAccession() {
      this.accession = null;
      return this;
    }

    // Clean domain field
    public Builder withNoDomain() {
      this.domain = null;
      return this;
    }

    // Clean webin account id field
    public Builder withNoWebinSubmissionAccountId() {
      this.webinSubmissionAccountId = null;
      return this;
    }

    // Clean collection fields
    public Builder withNoAttributes() {
      this.attributes = new TreeSet<>();
      return this;
    }

    public Builder withNoRelationships() {
      this.relationships = new TreeSet<>();
      return this;
    }

    public Builder withNoExternalReferences() {
      this.externalReferences = new TreeSet<>();
      return this;
    }

    public Builder withNoContacts() {
      this.contacts = new TreeSet<>();
      return this;
    }

    public Builder withNoOrganisations() {
      this.organizations = new TreeSet<>();
      return this;
    }

    public Builder withNoPublications() {
      this.publications = new TreeSet<>();
      return this;
    }

    public Sample build() {
      return Sample.build(
          name,
          accession,
          domain,
          webinSubmissionAccountId,
          taxId,
          release,
          update,
          create,
          submitted,
          reviewed,
          attributes,
          relationships,
          externalReferences,
          organizations,
          contacts,
          publications,
          submittedVia);
    }

    private ZonedDateTime parseDateTime(String datetimeString) {
      if (datetimeString.isEmpty()) return null;
      TemporalAccessor temporalAccessor =
          getFormatter()
              .parseBest(datetimeString, ZonedDateTime::from, LocalDateTime::from, LocalDate::from);
      if (temporalAccessor instanceof ZonedDateTime) {
        return (ZonedDateTime) temporalAccessor;
      } else if (temporalAccessor instanceof LocalDateTime) {
        return ((LocalDateTime) temporalAccessor).atZone(ZoneId.of("UTC"));
      } else {
        return ((LocalDate) temporalAccessor).atStartOfDay(ZoneId.of("UTC"));
      }
    }

    /**
     * Return a Builder produced extracting informations from the sample
     *
     * @param sample the sample to use as reference
     * @return the Builder
     */
    public static Builder fromSample(Sample sample) {
      return new Builder(sample.getName(), sample.getAccession())
          .withDomain(sample.getDomain())
          .withWebinSubmissionAccountId(sample.getWebinSubmissionAccountId())
          .withTaxId(sample.getTaxId())
          .withRelease(sample.getRelease())
          .withUpdate(sample.getUpdate())
          .withCreate(sample.getCreate())
          .withSubmitted(sample.getSubmitted())
          .withReviewed(sample.getReviewed())
          .withAttributes(sample.getAttributes())
          .withRelationships(sample.getRelationships())
          .withExternalReferences(sample.getExternalReferences())
          .withOrganizations(sample.getOrganizations())
          .withPublications(sample.getPublications())
          .withContacts(sample.getContacts())
          .withSubmittedVia(sample.getSubmittedVia());
    }

    private DateTimeFormatter getFormatter() {
      return new DateTimeFormatterBuilder()
          .parseCaseInsensitive()
          .append(ISO_LOCAL_DATE)
          .optionalStart() // time made optional
          .appendLiteral('T')
          .append(ISO_LOCAL_TIME)
          .optionalStart() // zone and offset made optional
          .appendOffsetId()
          .optionalStart()
          .appendLiteral('[')
          .parseCaseSensitive()
          .appendZoneRegionId()
          .appendLiteral(']')
          .optionalEnd()
          .optionalEnd()
          .optionalEnd()
          .toFormatter();
    }
  }
}
