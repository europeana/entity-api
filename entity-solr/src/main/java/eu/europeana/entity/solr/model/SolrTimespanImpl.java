package eu.europeana.entity.solr.model;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.beans.Field;

import eu.europeana.entity.definitions.model.Timespan;
import eu.europeana.entity.definitions.model.impl.BaseTimespan;
import eu.europeana.entity.definitions.model.vocabulary.ConceptSolrFields;

public class SolrTimespanImpl extends BaseTimespan implements Timespan {

    // COMMON FIELDS
    // Need to annotate concept fields as well as this class doesn't extend the
    // SolrConceptImpl
    @Override
    @Field(ConceptSolrFields.ID)
    public void setEntityId(String entityId) {
	super.setEntityId(entityId);
    }

    @Override
    @Field(ConceptSolrFields.TYPE)
    public void setInternalType(String internalType) {
	super.setInternalType(internalType);
    }

    @Override
    @Field(ConceptSolrFields.DEPICTION)
    public void setDepiction(String depiction) {
	super.setDepiction(depiction);
    }

    @Override
    @Field(ConceptSolrFields.IS_SHOWN_BY_ID)
    public void setIsShownById(String isShownById) {
	super.setIsShownById(isShownById);
    }

    @Override
    @Field(ConceptSolrFields.IS_SHOWN_BY_SOURCE)
    public void setIsShownBySource(String isShownBySource) {
	super.setIsShownBySource(isShownBySource);
    }

    @Override
    @Field(ConceptSolrFields.IS_SHOWN_BY_THUMBNAIL)
    public void setIsShownByThumbnail(String isShownByThumbnail) {
	super.setIsShownByThumbnail(isShownByThumbnail);
    }

    @Override
    @Field(ConceptSolrFields.PREF_LABEL_ALL)
    public void setPrefLabelStringMap(Map<String, String> prefLabel) {
	Map<String, String> normalizedPrefLabel = SolrUtils.normalizeStringMap(ConceptSolrFields.PREF_LABEL, prefLabel);
	super.setPrefLabelStringMap(normalizedPrefLabel);
    }

    @Override
    @Field(ConceptSolrFields.ALT_LABEL_ALL)
    public void setAltLabel(Map<String, List<String>> altLabel) {
	Map<String, List<String>> normalizedAltLabel = SolrUtils.normalizeStringListMap(ConceptSolrFields.ALT_LABEL,
		altLabel);
	super.setAltLabel(normalizedAltLabel);
    }

    /**
     * Concept fields
     */
    @Override
    @Field(ConceptSolrFields.NOTE_ALL)
    public void setNote(Map<String, List<String>> note) {
	Map<String, List<String>> normalizedNote = SolrUtils.normalizeStringListMap(ConceptSolrFields.NOTE, note);
	super.setNote(normalizedNote);
    }

    @Override
    @Field(ConceptSolrFields.HAS_PART)
    public void setHasPart(String[] hasPart) {
	super.setHasPart(hasPart);
    }

    @Override
    @Field(ConceptSolrFields.IS_PART_OF)
    public void setIsPartOfArray(String[] isPartOf) {
	super.setIsPartOfArray(isPartOf);
    }

    @Override
    @Field(ConceptSolrFields.SAME_AS)
    public void setSameAs(String[] sameAs) {
	super.setSameAs(sameAs);
    }

    // SPECIFIC FIELDS
    @Override
    @Field(ConceptSolrFields.IS_NEXT_IN_SEQUENCE)
    public void setIsNextInSequence(String[] isNextInSequence) {
	super.setIsNextInSequence(isNextInSequence);
    }

    @Override
    @Field(ConceptSolrFields.BEGIN)
    public void setBeginString(String begin) {
	super.setBeginString(begin);
    }
    
    @Override
    @Field(ConceptSolrFields.END)
    public void setEndString(String end) {
        super.setEndString(end);
    }

    // TECHNICAL FIELDS
    @Override
    @Field(ConceptSolrFields.CREATED)
    public void setCreated(Date created) {
	super.setCreated(created);
    }

    @Override
    @Field(ConceptSolrFields.MODIFIED)
    public void setModified(Date modified) {
	super.setModified(modified);
    }

}
