package eu.europeana.entity.solr.model;

import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.beans.Field;

import eu.europeana.entity.definitions.model.Place;
import eu.europeana.entity.definitions.model.impl.BasePlace;
import eu.europeana.entity.definitions.model.vocabulary.AgentSolrFields;
import eu.europeana.entity.definitions.model.vocabulary.ConceptSolrFields;
import eu.europeana.entity.definitions.model.vocabulary.PlaceSolrFields;

public class SolrPlaceImpl extends BasePlace implements Place {

	//COMMON FIELDS
	// Need to annotate concept fields as well as this class doesn't extend the
	// SolrConceptImpl
	@Override
	@Field(AgentSolrFields.SAME_AS)
	public void setSameAs(String[] sameAs) {
		super.setSameAs(sameAs);
	}

	@Override
	@Field(AgentSolrFields.EXACT_MATCH)
	public void setExactMatch(String[] exactMatch) {
		super.setExactMatch(exactMatch);
	}
	
	// @Override
	// @Field(AgentSolrFields.IDENTIFIER)
	// public void setIdentifier(String identifier) {
	// super.setIdentifier(identifier);
	// }

	@Override
	@Field(AgentSolrFields.INTERNAL_TYPE)
	public void setInternalType(String internalType) {
		super.setInternalType(internalType);
	}

	@Override
	@Field(ConceptSolrFields.ID)
	public void setEntityId(String entityId) {
		super.setEntityId(entityId);
	}

	@Override
	@Field(AgentSolrFields.HAS_PART)
	public void setHasPart(String[] hasPart) {
		super.setHasPart(hasPart);
	}

	@Override
	@Field(AgentSolrFields.IS_PART_OF)
	public void setIsPartOfArray(String[] isPartOf) {
		super.setIsPartOfArray(isPartOf);
	}
	
	/**
	 * Concept fields
	 */	
	@Override
	@Field(ConceptSolrFields.NOTE_ALL)
	public void setNote(Map<String, List<String>> note) {
		Map<String, List<String>>  normalizedNote = SolrUtils.normalizeStringListMap(
				ConceptSolrFields.NOTE, note);
		super.setNote(normalizedNote);
	}
	
	@Override
	@Field(ConceptSolrFields.PREF_LABEL_ALL)
	public void setPrefLabelStringMap(Map<String, String> prefLabel) {
		Map<String, String> normalizedPrefLabel = SolrUtils.normalizeStringMap(
				ConceptSolrFields.PREF_LABEL, prefLabel);
		super.setPrefLabelStringMap(normalizedPrefLabel);
	}

	@Override
	@Field(ConceptSolrFields.ALT_LABEL_ALL)
	public void setAltLabel(Map<String, List<String>> altLabel) {
		Map<String, List<String>> normalizedAltLabel = SolrUtils.normalizeStringListMap(
				ConceptSolrFields.ALT_LABEL, altLabel);
		super.setAltLabel(normalizedAltLabel);
	}

//	@Override
//	@Field(ConceptSolrFields.HIDDEN_LABEL)
//	public void setHiddenLabel(Map<String, List<String>> hiddenLabel) {
//		super.setHiddenLabel(hiddenLabel);
//	}
	
	//SPECIFIC FIELDS
	@Override
//	@Field(PlaceSolrFields.IS_NEXT_IN_SEQUENCE)
	public void setIsNextInSequence(String[] isNextInSequence) {
		super.setIsNextInSequence(isNextInSequence);
	}
	
	@Override
	@Field(PlaceSolrFields.LATITUDE)
	public void setLatitude(Float latitude) {
		super.setLatitude(latitude);
	}
	
	@Override
	@Field(PlaceSolrFields.LONGITUDE)
	public void setLongitude(Float longitude) {
		super.setLongitude(longitude);
	}
	
	@Override
	@Field(PlaceSolrFields.ALTITUDE)
	public void setAltitude(Float altitude) {
		super.setAltitude(altitude);
	}

	@Override
	@Field(ConceptSolrFields.DEPICTION)
	public void setDepiction(String depiction) {
		super.setDepiction(depiction);
	}
}
