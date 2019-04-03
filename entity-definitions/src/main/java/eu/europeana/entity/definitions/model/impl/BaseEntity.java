package eu.europeana.entity.definitions.model.impl;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;

import eu.europeana.entity.definitions.model.Entity;
import eu.europeana.entity.definitions.model.RankedEntity;

public class BaseEntity implements Entity, RankedEntity {

	protected String TMP_KEY = "def";
	
	// common functional fields
//	private String about;
	private String internalType;
	private String entityId;
	private Map<String, List<String>> note;
	private Map<String, String> prefLabel;
	private Map<String, List<String>> altLabel;
	private Map<String, List<String>> hiddenLabel;
	private Map<String, List<String>> tmpPrefLabel;
	
	private String definition;
	private String identifier[];
	private String[] sameAs;
	private String[] isRelatedTo;

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
	
	// depiction
	private String depiction;

	public Map<String, String> getPrefLabelStringMap() {
		return prefLabel;
	}

	public void setPrefLabelStringMap(Map<String, String> prefLabel) {
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
		return getEntityId();
	}

	public void setAbout(String about) {
		setEntityId(about);
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

//	@Override
//	public ObjectId getId() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public void setId(ObjectId id) {
//		// TODO Auto-generated method stub
//
//	}

	public String[] getHasPart() {
		return hasPart;
	}

	public void setHasPart(String[] hasPart) {
		this.hasPart = hasPart;
	}

	public String[] getIsPartOfArray() {
		return isPartOf;
	}

	public void setIsPartOfArray(String[] isPartOf) {
		this.isPartOf = isPartOf;
	}

	public String getDepiction() {
		return depiction;
	}
	
	public void setDepiction(String depiction) {
		this.depiction = depiction;
	}

	@Override
	public String[] getSameAs() {
		return sameAs;
	}

	public void setSameAs(String[] sameAs) {
		this.sameAs = sameAs;
	}
	
	@Override
	@Deprecated
	public void setFoafDepiction(String foafDepiction) {
		setDepiction(foafDepiction);
	}

	@Override
	public String getFoafDepiction() {
		return getDepiction();
	}
	
	@Override
	@Deprecated
	public ObjectId getId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Deprecated
	public void setId(ObjectId id) {
		// TODO Auto-generated method stub
	}

	@Override
	public Map<String, List<String>> getPrefLabel() {		
		//if not available
		if (getPrefLabelStringMap() == null)
			return null;
		//if not transformed		
		if (tmpPrefLabel == null)
			tmpPrefLabel = fillTmpMapToMap(getPrefLabelStringMap());

		return tmpPrefLabel;
	}

	/**
	 * This method converts List<String> to Map<String, List<String>> 
	 * @param list of strings
	 */
	protected Map<String, List<String>> fillTmpMap(List<String> list) {
		
		Map<String, List<String>> tmpMap = new HashMap<String, List<String>>();
		tmpMap.put(TMP_KEY, list);
		return tmpMap;
	}

	/**
	 * This method converts  Map<String, String> to Map<String, List<String>> 
	 * @param map of strings
	 */
	protected Map<String, List<String>> fillTmpMapToMap(Map<String, String> mapOfStrings) {
		
		Map<String, List<String>> tmpMap = null;	
		tmpMap = mapOfStrings.entrySet().stream().collect(Collectors.toMap(
				entry -> entry.getKey(), 
				entry -> Collections.singletonList(entry.getValue()))
		);	
		
		return tmpMap;
	}
	
	@Override
	@Deprecated
	public void setPrefLabel(Map<String, List<String>> prefLabel) {
		// TODO Auto-generated method stub
	}
	
	@Deprecated
	public void setOwlSameAs(String[] owlSameAs) {
		setSameAs(sameAs);
		
	}

	public String[] getOwlSameAs() {
		return getSameAs();
	}

	@Override
	public String getEntityIdentifier() {
		String[] splitArray = this.getAbout().split("/");
		return splitArray[splitArray.length-1];
	}

}
