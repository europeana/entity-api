package eu.europeana.entity.web.jsonld;

import org.apache.stanbol.commons.jsonld.JsonLd;
import org.apache.stanbol.commons.jsonld.JsonLdProperty;
import org.apache.stanbol.commons.jsonld.JsonLdPropertyValue;
import org.apache.stanbol.commons.jsonld.JsonLdResource;

import eu.europeana.entity.definitions.model.search.result.ResultSet;
import eu.europeana.entity.web.controller.WebEntityConstants;
import eu.europeana.entity.web.model.view.ConceptView;


public class ConceptSetSerializer extends JsonLd {

	// private static final Logger logger =
	// LoggerFactory.getLogger(ConceptLd.class);

	ResultSet<? extends ConceptView> conceptSet;

	public ResultSet<? extends ConceptView> getConceptSet() {
		return conceptSet;
	}

	public void setConceptSet(ResultSet<? extends ConceptView> conceptSet) {
		this.conceptSet = conceptSet;
	}


	/**
	 * @param conceptSet
	 */
	public ConceptSetSerializer(ResultSet<? extends ConceptView> conceptSet) {
		setConceptSet(conceptSet);
	}

	/**
	 * Adds the given concept to this JsonLd object using the resource's
	 * subject as key. If the key is NULL and there does not exist a resource
	 * with an empty String as key the resource will be added using an empty
	 * String ("") as key.
	 * 
	 * @param concept
	 */
	public String serialize() {

		setUseTypeCoercion(false);
		setUseCuries(true);
		setUsedNamespaces(namespacePrefixMap);

		JsonLdResource jsonLdResource = new JsonLdResource();
		jsonLdResource.setSubject("");
		jsonLdResource.putProperty(WebEntityConstants.AT_CONTEXT, WebEntityConstants.WA_CONTEXT);
		String[] oaType = new String[] { "BasicContainer", "Collection" };
		jsonLdResource.putProperty(buildArrayProperty(WebEntityConstants.AT_TYPE, oaType));
		jsonLdResource.putProperty(WebEntityConstants.TOTAL_ITEMS, getConceptSet().getResultSize());

		serializeItems(jsonLdResource);
		
		put(jsonLdResource);

		return toString(4);
	}

	
	
	protected void serializeItems(JsonLdResource jsonLdResource) {

		JsonLdProperty containsProperty = new JsonLdProperty(WebEntityConstants.CONTAINS);
		JsonLdPropertyValue propertyValue;
		
		for (ConceptView entityPreview : getConceptSet().getResults()) {
				propertyValue = buildConceptViewPropertyValue(entityPreview);
				containsProperty.addValue(propertyValue);
		}
		
		jsonLdResource.putProperty(containsProperty);
			
	}

	private JsonLdPropertyValue buildConceptViewPropertyValue(ConceptView entityPreview) {
		
		JsonLdPropertyValue entityPreviewPropValue = new JsonLdPropertyValue();
		entityPreviewPropValue.putProperty(new JsonLdProperty(WebEntityConstants.AT_ID, entityPreview.getEntityId()));
		entityPreviewPropValue.putProperty(new JsonLdProperty(WebEntityConstants.AT_TYPE, entityPreview.getEntityType()));
		
		String solrFieldPrefix = WebEntityConstants.PREF_LABEL+".";
		entityPreviewPropValue.putProperty(
				buildMapProperty(WebEntityConstants.PREF_LABEL, entityPreview.getPrefLabel(), solrFieldPrefix));
				
		return entityPreviewPropValue;
	}


}