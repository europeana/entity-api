package eu.europeana.entity.solr.model;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.beans.Field;

import eu.europeana.entity.definitions.model.Place;
import eu.europeana.entity.definitions.model.impl.BasePlace;
import eu.europeana.entity.definitions.model.vocabulary.ConceptSolrFields;
import eu.europeana.entity.definitions.model.vocabulary.EntitySolrFields;
import eu.europeana.entity.definitions.model.vocabulary.PlaceSolrFields;

public class SolrPlaceImpl extends BasePlace implements Place {

	//COMMON FIELDS
	// Need to annotate concept fields as well as this class doesn't extend the
	// SolrConceptImpl
	@Override
	@Field(PlaceSolrFields.SAME_AS)
	public void setSameAs(String[] sameAs) {
		super.setSameAs(sameAs);
	}

	@Override
	@Field(PlaceSolrFields.EXACT_MATCH)
	public void setExactMatch(String[] exactMatch) {
		super.setExactMatch(exactMatch);
	}
	
	// @Override
	// @Field(AgentSolrFields.IDENTIFIER)
	// public void setIdentifier(String identifier) {
	// super.setIdentifier(identifier);
	// }

	@Override
	@Field(PlaceSolrFields.TYPE)
	public void setInternalType(String internalType) {
		super.setInternalType(internalType);
	}

	@Override
	@Field(PlaceSolrFields.ID)
	public void setEntityId(String entityId) {
		super.setEntityId(entityId);
	}

	@Override
	@Field(PlaceSolrFields.HAS_PART)
	public void setHasPart(String[] hasPart) {
		super.setHasPart(hasPart);
	}

	@Override
	@Field(PlaceSolrFields.IS_PART_OF)
	public void setIsPartOfArray(String[] isPartOf) {
		super.setIsPartOfArray(isPartOf);
	}
	
	/**
	 * Concept fields
	 */	
	@Override
	@Field(PlaceSolrFields.NOTE_ALL)
	public void setNote(Map<String, List<String>> note) {
		Map<String, List<String>>  normalizedNote = SolrUtils.normalizeStringListMap(
				ConceptSolrFields.NOTE, note);
		super.setNote(normalizedNote);
	}
	
	@Override
	@Field(PlaceSolrFields.PREF_LABEL_ALL)
	public void setPrefLabelStringMap(Map<String, String> prefLabel) {
		Map<String, String> normalizedPrefLabel = SolrUtils.normalizeStringMap(
				ConceptSolrFields.PREF_LABEL, prefLabel);
		super.setPrefLabelStringMap(normalizedPrefLabel);
	}

	@Override
	@Field(PlaceSolrFields.ALT_LABEL_ALL)
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
	@Field(PlaceSolrFields.DEPICTION)
	public void setDepiction(String depiction) {
		super.setDepiction(depiction);
	}
	
	@Override
	@Field(PlaceSolrFields.CREATED)
	public void setCreated(Date created) {
	    	super.setCreated(created);
	}
	
	@Override
	@Field(PlaceSolrFields.MODIFIED)
	public void setModified(Date modified) {
	    	super.setModified(modified);
	}
	
	@Override
	@Field(EntitySolrFields.IS_SHOWN_BY_ID)
	public void setIsShownById(String isShownById) {
		super.setIsShownById(isShownById);
	}
	
	@Override
	@Field(EntitySolrFields.IS_SHOWN_BY_SOURCE)
	public void setIsShownBySource(String isShownBySource) {
		super.setIsShownBySource(isShownBySource);
	}
	
	@Override
	@Field(EntitySolrFields.IS_SHOWN_BY_THUMBNAIL)
	public void setIsShownByThumbnail(String isShownByThumbnail) {
		super.setIsShownByThumbnail(isShownByThumbnail);
	}
}
