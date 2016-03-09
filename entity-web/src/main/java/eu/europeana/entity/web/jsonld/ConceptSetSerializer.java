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

//	TypeUtils typeHelper = new TypeUtils();
//	ResultSet<? extends ConceptView> conceptSet;
	ResultSet<? extends ConceptView> conceptSet;

	public ResultSet<? extends ConceptView> getConceptSet() {
//		public ResultSet<? extends ConceptView> getConceptSet() {
		return conceptSet;
	}

	public void setConceptSet(ResultSet<? extends ConceptView> conceptSet) {
//		public void setConceptSet(ResultSet<? extends ConceptView> conceptSet) {
		this.conceptSet = conceptSet;
	}

//	public TypeUtils getTypeHelper() {
//		return typeHelper;
//	}

	/**
	 * @param conceptSet
	 */
	public ConceptSetSerializer(ResultSet<? extends ConceptView> conceptSet) {
//		public ConceptSetSerializer(ResultSet<? extends ConceptView> conceptSet) {
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

	/**
//	 * TODO: move this to base class build appropriate property representation
//	 * for string arrays
//	 * 
//	 * @param propertyName
//	 * @param valueList
//	 * @return
//	 */
//	protected JsonLdProperty buildArrayProperty(String propertyName, String[] values) {
//
//		if (values == null)
//			return null;
//
//		JsonLdProperty arrProperty = new JsonLdProperty(propertyName);
//		JsonLdPropertyValue propertyValue;
//		for (int i = 0; i < values.length; i++) {
//			propertyValue = new JsonLdPropertyValue();
//			propertyValue.setValue(values[i]);
//			arrProperty.addValue(propertyValue);
//		}
//
//		return arrProperty;
//	}
//	
	
//	/**
//	 * TODO: move this to base class build appropriate property representation
//	 * for string arrays
//	 * 
//	 * @param propertyName
//	 * @param valueList
//	 * @return
//	 */
//	protected JsonLdProperty buildValueArrayProperty(String propertyName, String[] values) {
//
//		if (values == null)
//			return null;
//
//		JsonLdProperty arrProperty = new JsonLdProperty(propertyName);
//		JsonLdPropertyValue propertyValue;
//		for (int i = 0; i < values.length; i++) {
//			propertyValue = new JsonLdPropertyValue();
//			propertyValue.setValue(values[i]);
//			arrProperty.addValue(propertyValue);
//		}
//
//		return arrProperty;
//	}
	
//	/**
//	 * @param map
//	 * @param propertyValue
//	 * @param field
//	 */
//	private void addMapToProperty(Map<String, String> map, JsonLdPropertyValue propertyValue, String field) {
//        JsonLdProperty fieldProperty = new JsonLdProperty(field);
//        JsonLdPropertyValue fieldPropertyValue = new JsonLdPropertyValue();
//        
//	    Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
//	    while (it.hasNext()) {
//	        Map.Entry<String, String> pairs = (Map.Entry<String, String>) it.next();
//	        String curValue = pairs.getValue();
//        	if (!StringUtils.isBlank(curValue)) 
//        		fieldPropertyValue.getValues().put(pairs.getKey(), pairs.getValue());
//	        it.remove(); // avoids a ConcurrentModificationException
//	    }
//        if (fieldPropertyValue.getValues().size() != 0) {
//         	fieldProperty.addValue(fieldPropertyValue);        
//         	propertyValue.putProperty(fieldProperty);
//    	}
//	}
//	

}
