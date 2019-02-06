package eu.europeana.entity.definitions.model.impl;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;
import eu.europeana.entity.definitions.model.Concept;
import eu.europeana.entity.definitions.model.RankedEntity;

public class BaseConcept extends BaseEntity implements Concept, RankedEntity {

	private String[] broader;
	private String[] narrower;
	private String[] related;
	private String[] broadMatch;
	private String[] narrowMatch;
	private String[] exactMatch;
	private String[] coref;
	private String[] relatedMatch;
	private String[] closeMatch;
	private String[] inScheme;
	private Map<String, List<String>> notation;

	private Map<String, List<String>> tmpPrefLabel;

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

	public String[] getCoref() {
		return coref;
	}

	public void setCoref(String[] coref) {
		this.coref = coref;
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

	@Override
	public Map<String, List<String>> getPrefLabel() {
		if (tmpPrefLabel == null)
			fillTmpPrefLabel(getPrefLabelStringMap());

		return tmpPrefLabel;
	}

	/**
	 * This method converts Map<String, String> to Map<String, List<String>> 
	 * @param prefLabelStringMap
	 */
	private void fillTmpPrefLabel(Map<String, String> prefLabelStringMap) {
		tmpPrefLabel = prefLabelStringMap.entrySet().stream().collect(Collectors.toMap(
				entry -> entry.getKey(), 
				entry -> Collections.singletonList(entry.getValue()))
		);	
	}

	@Override
	@Deprecated
	public void setPrefLabel(Map<String, List<String>> prefLabel) {
		// TODO Auto-generated method stub
	}

	@Override
	@Deprecated
	public void setFoafDepiction(String foafDepiction) {
		// setDepiction(foafDepiction);
	}

	@Override
	@Deprecated
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

}
