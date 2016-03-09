package eu.europeana.entity.web.jsonld;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;
import org.apache.stanbol.commons.jsonld.JsonLd;
import org.apache.stanbol.commons.jsonld.JsonLdProperty;
import org.apache.stanbol.commons.jsonld.JsonLdPropertyValue;
import org.apache.stanbol.commons.jsonld.JsonLdResource;

import eu.europeana.entity.definitions.model.Concept;
import eu.europeana.entity.definitions.model.search.result.FacetFieldView;
import eu.europeana.entity.definitions.model.search.result.ResultSet;
import eu.europeana.entity.web.controller.WebEntityFields;
import eu.europeana.entity.web.model.view.ConceptView;


public class ConceptSetSerializer extends JsonLd {

	// private static final Logger logger =
	// LoggerFactory.getLogger(ConceptLd.class);

//	TypeUtils typeHelper = new TypeUtils();
//	ResultSet<? extends ConceptView> conceptSet;
	ResultSet<? extends Concept> conceptSet;

	public ResultSet<? extends Concept> getConceptSet() {
//		public ResultSet<? extends ConceptView> getConceptSet() {
		return conceptSet;
	}

	public void setConceptSet(ResultSet<? extends Concept> conceptSet) {
//		public void setConceptSet(ResultSet<? extends ConceptView> conceptSet) {
		this.conceptSet = conceptSet;
	}

//	public TypeUtils getTypeHelper() {
//		return typeHelper;
//	}

	/**
	 * @param conceptSet
	 */
	public ConceptSetSerializer(ResultSet<? extends Concept> conceptSet) {
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
		jsonLdResource.putProperty(WebEntityFields.AT_CONTEXT, WebEntityFields.WA_CONTEXT);
		String[] oaType = new String[] { "BasicContainer", "Collection" };
		jsonLdResource.putProperty(buildArrayProperty(WebEntityFields.AT_TYPE, oaType));
		jsonLdResource.putProperty(WebEntityFields.TOTAL_ITEMS, getConceptSet().getResultSize());

		serializeItems(jsonLdResource);
		serializeFacets(jsonLdResource);
		
		put(jsonLdResource);

		return toString(4);
	}

	protected void serializeFacets(JsonLdResource jsonLdResource) {
		if(getConceptSet().getFacetFields() == null || getConceptSet().getFacetFields().isEmpty())
			return;
		
		JsonLdProperty facetsProperty = new JsonLdProperty(WebEntityFields.SEARCH_RESP_FACETS);
//		JsonLdPropertyValue facetsPropertyValue = new JsonLdPropertyValue();
		//JsonLdProperty facetViewProperty = new JsonLdProperty(null);
		
		for (FacetFieldView view : getConceptSet().getFacetFields()) 
			facetsProperty.addValue(buildFacetPropertyValue(view));
		
		jsonLdResource.putProperty(facetsProperty);
				
	}

	private JsonLdPropertyValue buildFacetPropertyValue(FacetFieldView view) {
		
		JsonLdPropertyValue facetViewEntry = new JsonLdPropertyValue();
		
		facetViewEntry.putProperty(new JsonLdProperty(WebEntityFields.SEARCH_RESP_FACETS_FIELD, view.getName()));
		
		JsonLdProperty values = new JsonLdProperty(WebEntityFields.SEARCH_RESP_FACETS_VALUES);
		JsonLdPropertyValue labelCountValue;
		Map<String, String> valueMap;
		
		for (Map.Entry<String, Long> valueCount : view.getValueCountMap().entrySet()) {
			labelCountValue = new JsonLdPropertyValue();
			valueMap = new TreeMap<String, String>();
			valueMap.put(WebEntityFields.SEARCH_RESP_FACETS_LABEL, valueCount.getKey());
			valueMap.put(WebEntityFields.SEARCH_RESP_FACETS_COUNT, valueCount.getValue().toString());
			labelCountValue.setValues(valueMap);
			
			values.addValue(labelCountValue);
		}
		
		facetViewEntry.putProperty(values);
		
		return facetViewEntry;
	}

	protected void serializeItems(JsonLdResource jsonLdResource) {

		String[] items = new String[(int) getConceptSet().getResults().size()];
		int i = 0;
		for (Concept anno : getConceptSet().getResults()) {
//			for (ConceptView anno : getConceptSet().getResults()) {
			items[i++] = anno.getEntityId();
		}
		
		if(items.length > 0 )
			jsonLdResource.putProperty(buildArrayProperty(WebEntityFields.CONTAINS, items));
		
		return;//needs until updated to switch construct
	}

	/**
	 * TODO: move this to base class build appropriate property representation
	 * for string arrays
	 * 
	 * @param propertyName
	 * @param valueList
	 * @return
	 */
	protected JsonLdProperty buildArrayProperty(String propertyName, String[] values) {

		if (values == null)
			return null;

		JsonLdProperty arrProperty = new JsonLdProperty(propertyName);
		JsonLdPropertyValue propertyValue;
		for (int i = 0; i < values.length; i++) {
			propertyValue = new JsonLdPropertyValue();
			propertyValue.setValue(values[i]);
			arrProperty.addValue(propertyValue);
		}

		return arrProperty;
	}
	
	
	/**
	 * TODO: move this to base class build appropriate property representation
	 * for string arrays
	 * 
	 * @param propertyName
	 * @param valueList
	 * @return
	 */
	protected JsonLdProperty buildValueArrayProperty(String propertyName, String[] values) {

		if (values == null)
			return null;

		JsonLdProperty arrProperty = new JsonLdProperty(propertyName);
		JsonLdPropertyValue propertyValue;
		for (int i = 0; i < values.length; i++) {
			propertyValue = new JsonLdPropertyValue();
			propertyValue.setValue(values[i]);
			arrProperty.addValue(propertyValue);
		}

		return arrProperty;
	}
	
	/**
	 * @param map
	 * @param propertyValue
	 * @param field
	 */
	private void addMapToProperty(Map<String, String> map, JsonLdPropertyValue propertyValue, String field) {
        JsonLdProperty fieldProperty = new JsonLdProperty(field);
        JsonLdPropertyValue fieldPropertyValue = new JsonLdPropertyValue();
        
	    Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry<String, String> pairs = (Map.Entry<String, String>) it.next();
	        String curValue = pairs.getValue();
        	if (!StringUtils.isBlank(curValue)) 
        		fieldPropertyValue.getValues().put(pairs.getKey(), pairs.getValue());
	        it.remove(); // avoids a ConcurrentModificationException
	    }
        if (fieldPropertyValue.getValues().size() != 0) {
         	fieldProperty.addValue(fieldPropertyValue);        
         	propertyValue.putProperty(fieldProperty);
    	}
	}
	

}
