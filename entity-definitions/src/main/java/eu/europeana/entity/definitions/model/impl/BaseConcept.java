package eu.europeana.entity.definitions.model.impl;

import java.util.List;
import java.util.Map;

import eu.europeana.entity.definitions.model.Concept;
import eu.europeana.entity.definitions.model.RankedEntity;

public class BaseConcept extends BaseEntity implements Concept, RankedEntity {

	private String[] broader;
	private String[] narrower;
	private String[] related;
	private String[] broadMatch;
	private String[] narrowMatch;
	private String[] exactMatch;
	private String[] relatedMatch;
	private String[] closeMatch;
	private String[] inScheme;
	private Map<String, List<String>> notation;

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

	public String[] getInScheme() {
		return inScheme;
	}

	public void setInScheme(String[] inScheme) {
		this.inScheme = inScheme;
	}

	public Map<String, List<String>> getNotation() {
		return notation;
	}

	public void setNotation(Map<String, List<String>> notation) {
		this.notation = notation;
	}

}
