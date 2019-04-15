package eu.europeana.entity.solr.model;

import java.util.Date;
import java.util.Map;

import org.apache.solr.client.solrj.beans.Field;

import eu.europeana.entity.definitions.model.ConceptScheme;
import eu.europeana.entity.definitions.model.impl.BaseConceptScheme;
import eu.europeana.entity.definitions.model.vocabulary.SolrConceptSchemeConstants;

/**
 * This class manages Solr object for concept scheme
 * 
 * @author GrafR
 *
 */
public class SolrConceptSchemeImpl extends BaseConceptScheme {

    /**
     * Constructor
     */
    public SolrConceptSchemeImpl(ConceptScheme conceptScheme) {
	this.setCreated(conceptScheme.getCreated());
	this.setModified(conceptScheme.getModified());
	this.setSameAs(conceptScheme.getSameAs());
	this.setInternalType(conceptScheme.getType());
	this.setPrefLabelStringMap(conceptScheme.getPrefLabelStringMap());
	this.setDefinition(conceptScheme.getDefinition());
	this.setEntityId(conceptScheme.getEntityId());
    }

    @Override
    @Field(SolrConceptSchemeConstants.PREF_LABEL_ALL)
    public void setPrefLabelStringMap(Map<String, String> prefLabel) {
	Map<String, String> normalizedPrefLabel = SolrUtils.normalizeStringMapByAddingPrefix(
		SolrConceptSchemeConstants.PREF_LABEL+".",
		prefLabel);
	super.setPrefLabelStringMap(normalizedPrefLabel);
    }

    @Override
    @Field(SolrConceptSchemeConstants.DEFINITION_ALL)
    public void setDefinition(Map<String, String> definition) {
	Map<String, String> normalizedDefinition = SolrUtils.normalizeStringMapByAddingPrefix(
		SolrConceptSchemeConstants.DEFINITION+".",
		definition);
	super.setDefinition(normalizedDefinition);
    }

    @Override
    @Field(SolrConceptSchemeConstants.INTERNAL_TYPE)
    public void setInternalType(String internalType) {
	super.setInternalType(internalType);
    }

    @Override
    @Field(SolrConceptSchemeConstants.ID)
    public void setEntityId(String entityId) {
	super.setEntityId(entityId);
    }

    @Override
    @Field(SolrConceptSchemeConstants.SAME_AS)
    public void setSameAs(String[] sameAs) {
	super.setSameAs(sameAs);
    }

    @Override
    @Field(SolrConceptSchemeConstants.CREATED)
    public void setCreated(Date created) {
	super.setCreated(created);
    }

    @Override
    @Field(SolrConceptSchemeConstants.MODIFIED)
    public void setModified(Date modified) {
	super.setModified(modified);
    }

}
