package eu.europeana.entity.definitions.model.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import eu.europeana.entity.definitions.model.Concept;

public abstract class BaseConcept implements Concept {

	private String about;
	private String internalType;
	private String entityId;
	private String definition;
	private String[] broader;
	private String[] narrower;
	private String[] related;
	private String[] broadMatch;
	private String[] narrowMatch;
	private String[] exactMatch;
	private String[] relatedMatch;
	private String[] closeMatch;
	private String[] sameAs;
	private Map<String,List<String>> notation;
	private Map<String,List<String>> prefLabel;
	private Map<String,List<String>> altLabel;
	private Map<String,List<String>> hiddenLabel;
	private Map<String,List<String>> note;
	private String[] inScheme;
	private Date timestamp; 
	
	
	public String[] getBroader() {
		return broader;
	}
	public void setBroader(String[] broader) {
		this.broader = broader;
	}
	public String[] getNarrower() {
		return narrower;
	}
	public void setNarrower(String[] narrower) {
		this.narrower = narrower;
	}
	public String[] getRelated() {
		return related;
	}
	public void setRelated(String[] related) {
		this.related = related;
	}
	public String[] getBroadMatch() {
		return broadMatch;
	}
	public void setBroadMatch(String[] broadMatch) {
		this.broadMatch = broadMatch;
	}
	public String[] getNarrowMatch() {
		return narrowMatch;
	}
	public void setNarrowMatch(String[] narrowMatch) {
		this.narrowMatch = narrowMatch;
	}
	public String[] getExactMatch() {
		return exactMatch;
	}
	public void setExactMatch(String[] exactMatch) {
		this.exactMatch = exactMatch;
	}
	public String[] getRelatedMatch() {
		return relatedMatch;
	}
	public void setRelatedMatch(String[] relatedMatch) {
		this.relatedMatch = relatedMatch;
	}
	public String[] getCloseMatch() {
		return closeMatch;
	}
	public void setCloseMatch(String[] closeMatch) {
		this.closeMatch = closeMatch;
	}
	public Map<String, List<String>> getNotation() {
		return notation;
	}
	public void setNotation(Map<String, List<String>> notation) {
		this.notation = notation;
	}
	public String[] getInScheme() {
		return inScheme;
	}
	public void setInScheme(String[] inScheme) {
		this.inScheme = inScheme;
	}
	public String getAbout() {
		return about;
	}
	public void setAbout(String about) {
		this.about = about;
	}
	@Override
	public String getDefinition() {
		return definition;
	}
	@Override
	public void setDefinition(String definition) {
		this.definition = definition;
	}
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
	public String getInternalType() {
		return internalType;
	}
	public void setInternalType(String internalType) {
		this.internalType = internalType;
	}
	public String[] getSameAs() {
		return sameAs;
	}
	public void setSameAs(String[] sameAs) {
		this.sameAs = sameAs;
	}
	@Override
	public String getEntityId() {
		return entityId;
	}
	@Override
	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}
	@Override
	public Date getTimestamp() {
		return timestamp;
	}
	@Override
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
		

}
