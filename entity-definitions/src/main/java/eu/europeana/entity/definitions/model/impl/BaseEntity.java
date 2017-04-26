package eu.europeana.entity.definitions.model.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;

import eu.europeana.entity.definitions.model.Entity;
import eu.europeana.entity.definitions.model.RankedEntity;

public class BaseEntity implements Entity, RankedEntity {

	// common functional fields
	private String about;
	private String internalType;
	private String entityId;
	private Map<String, List<String>> note;
	private Map<String, List<String>> prefLabel;
	private Map<String, List<String>> altLabel;
	private Map<String, List<String>> hiddenLabel;
	private String definition;
	private String identifier[];
//	private String[] sameAs;
	private String[] isRelatedTo;
	private String[] coref;

	// hierarchical structure available only for a part of entities. Add set/get
	// methods to the appropriate interfaces
	private String[] hasPart;
	private String[] isPartOf;

	// technical fields
	private Date timestamp;
	private int wikipediaClicks;
	private int europeanaDocCount;
	private float derivedScore;

	// other derived technical fields?
	private String context;

	public Map<String, List<String>> getPrefLabel() {
		return prefLabel;
	}

	public void setPrefLabel(Map<String, List<String>> prefLabel) {
		this.prefLabel = prefLabel;
	}

	public Map<String, List<String>> getAltLabel() {
		return altLabel;
	}

	public void setAltLabel(Map<String, List<String>> altLabel) {
		this.altLabel = altLabel;
	}

	public Map<String, List<String>> getHiddenLabel() {
		return hiddenLabel;
	}

	public void setHiddenLabel(Map<String, List<String>> hiddenLabel) {
		this.hiddenLabel = hiddenLabel;
	}

	public Map<String, List<String>> getNote() {
		return note;
	}

	public void setNote(Map<String, List<String>> note) {
		this.note = note;
	}

	public String getDefinition() {
		return definition;
	}

	public void setDefinition(String definition) {
		this.definition = definition;
	}

	public String getInternalType() {
		return internalType;
	}

	public void setInternalType(String internalType) {
		this.internalType = internalType;
	}

//	public String[] getSameAs() {
//		return sameAs;
//	}
//
//	public void setSameAs(String[] sameAs) {
//		this.sameAs = sameAs;
//	}

	public String getEntityId() {
		return entityId;
	}

	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

	public String[] getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String[] identifier) {
		this.identifier = identifier;
	}

	public String getAbout() {
		return about;
	}

	public void setAbout(String about) {
		this.about = about;
	}

	@Override
	public Date getTimestamp() {
		return timestamp;
	}

	@Override
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public int getWikipediaClicks() {
		return wikipediaClicks;
	}

	@Override
	public void setWikipediaClicks(int wikipediaClicks) {
		this.wikipediaClicks = wikipediaClicks;
	}

	@Override
	public int getEuropeanaDocCount() {
		return europeanaDocCount;
	}

	@Override
	public void setEuropeanaDocCount(int europeanaDocCount) {
		this.europeanaDocCount = europeanaDocCount;
	}

	@Override
	public float getDerivedScore() {
		return derivedScore;
	}

	@Override
	public void setDerivedScore(float derivedScore) {
		this.derivedScore = derivedScore;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public String[] getIsRelatedTo() {
		return isRelatedTo;
	}

	public void setIsRelatedTo(String[] isRelatedTo) {
		this.isRelatedTo = isRelatedTo;
	}

	@Override
	public ObjectId getId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setId(ObjectId id) {
		// TODO Auto-generated method stub

	}

	public String[] getHasPart() {
		return hasPart;
	}

	public void setHasPart(String[] hasPart) {
		this.hasPart = hasPart;
	}

	public String[] getIsPartOf() {
		return isPartOf;
	}

	public void setIsPartOf(String[] isPartOf) {
		this.isPartOf = isPartOf;
	}

	@Override
	public String[] getCoref() {
		return coref;
	}

	public void setCoref(String[] coref) {
		this.coref = coref;
	}

}
