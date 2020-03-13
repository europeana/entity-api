package eu.europeana.entity.solr.model;

import java.util.Date;
import java.util.Map;

import org.apache.solr.client.solrj.beans.Field;

import eu.europeana.entity.definitions.model.ConceptScheme;
import eu.europeana.entity.definitions.model.impl.BaseConceptScheme;
import eu.europeana.entity.definitions.model.vocabulary.EntitySolrFields;
import eu.europeana.entity.definitions.model.vocabulary.SolrConceptSchemeConstants;

/**
 * This class manages Solr object for concept scheme
 * 
 * @author GrafR
 *
 */
public class SolrConceptSchemeImpl extends BaseConceptScheme implements SolrConceptScheme{

    private String[] suggestFilters = {"ConceptScheme"};
    private Map<String, String> prefixedDefinition;
    private Map<String, String> prefixedPrefLabel;

    /**
     * Constructor
     */
    public SolrConceptSchemeImpl(ConceptScheme conceptScheme) {
	this.setCreated(conceptScheme.getCreated());
	this.setModified(conceptScheme.getModified());
	this.setSameAs(conceptScheme.getSameAs());
	this.setInternalType(conceptScheme.getType());
	this.setPrefixedPrefLabel(conceptScheme.getPrefLabelStringMap());
	this.setPrefixedDefinition(conceptScheme.getDefinition());
	this.setEntityId(conceptScheme.getEntityId());
    }

    public SolrConceptSchemeImpl() {}

    @Override
    @Field(SolrConceptSchemeConstants.PREF_LABEL_ALL)
    public void setPrefixedPrefLabel(Map<String, String> prefLabel) {
	Map<String, String> normalizedPrefLabel = SolrUtils.normalizeStringMapByAddingPrefix(
		SolrConceptSchemeConstants.PREF_LABEL+".",
		prefLabel);
	prefixedPrefLabel = normalizedPrefLabel;
    }

    @Override
    public Map<String, String> getPrefixedPrefLabel() {
	return prefixedPrefLabel; 
    }    
    

    @Override
    public Map<String, String> getPrefLabelStringMap() {
	if(super.getPrefLabelStringMap() == null) {
	    //extract prefLabelStringMap (web representation) from prefixedPrefLabel (solr representation)
	    Map<String, String> prefLabel = SolrUtils.normalizeStringMap(SolrConceptSchemeConstants.PREF_LABEL, getPrefixedPrefLabel());
	    super.setPrefLabelStringMap(prefLabel);
	}
	    
	return super.getPrefLabelStringMap();
    }    
    
    
    @Override
    @Field(SolrConceptSchemeConstants.DEFINITION_ALL)
    public void setPrefixedDefinition(Map<String, String> definition) {
	//ensure the prefix exists (solr representation)
	Map<String, String> normalizedDefinition = SolrUtils.normalizeStringMapByAddingPrefix(
		SolrConceptSchemeConstants.DEFINITION+".",
		definition);
	prefixedDefinition = normalizedDefinition;
    }

    @Override
    public Map<String, String> getPrefixedDefinition() {
	return prefixedDefinition;
    }   
    
    
    @Override
    public Map<String, String> getDefinition() {
	if(super.getDefinition() == null) {
	    //extract definition (web representation) from prefixedDefinition (solr representation)
	    Map<String, String> definition = SolrUtils.normalizeStringMap(SolrConceptSchemeConstants.DEFINITION, getPrefixedDefinition());
	    super.setDefinition(definition);
	}
	    
	return super.getDefinition();
    }    

    @Override
    @Field(SolrConceptSchemeConstants.TYPE)
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

    public String[] getSuggestFilters() {
        return suggestFilters;
    }

    @Field(SolrConceptSchemeConstants.SUGGEST_FILTERS)
    public void setSuggestFilters(String[] suggestFilters) {
        this.suggestFilters = suggestFilters;
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
