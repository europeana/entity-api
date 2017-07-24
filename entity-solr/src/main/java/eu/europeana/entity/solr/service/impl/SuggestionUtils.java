package eu.europeana.entity.solr.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.annotate.JsonAutoDetect.Visibility;
import org.codehaus.jackson.annotate.JsonMethod;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.JsonNodeFactory;
import org.codehaus.jackson.node.ObjectNode;

import eu.europeana.entity.definitions.exceptions.UnsupportedEntityTypeException;
import eu.europeana.entity.definitions.model.ResourcePreview;
import eu.europeana.entity.definitions.model.ResourcePreviewImpl;
import eu.europeana.entity.definitions.model.vocabulary.EntityTypes;
import eu.europeana.entity.definitions.model.vocabulary.WebEntityFields;
import eu.europeana.entity.solr.exception.EntitySuggestionException;
import eu.europeana.entity.solr.model.factory.EntityPreviewObjectFactory;
import eu.europeana.entity.solr.model.vocabulary.SuggestionFields;
import eu.europeana.entity.web.model.view.AgentPreview;
import eu.europeana.entity.web.model.view.ConceptPreview;
import eu.europeana.entity.web.model.view.EntityPreview;
import eu.europeana.entity.web.model.view.PlacePreview;
import eu.europeana.entity.web.model.view.TimeSpanPreview;

public class SuggestionUtils {

	private final Logger log = Logger.getLogger(getClass());

	protected static ObjectMapper objectMapper = new ObjectMapper().setVisibility(JsonMethod.FIELD, Visibility.ANY)
			.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	protected static final JsonFactory jsonFactory = new JsonFactory();

	public Logger getLog() {
		return log;
	}

	public EntityPreview parsePayload(String payload, String preferredLanguage, String highlightTerm) throws EntitySuggestionException {	
		EntityPreview preview = null;
		try {
			JsonParser parser = jsonFactory.createJsonParser(payload);
			parser.setCodec(objectMapper);
			
			JsonNode payloadNode = objectMapper.readTree(payload);
			
//			JsonNode entityNode = getPayload(languageMapNode, preferredLanguage, highlightTerm);

//			JsonNode entityNode = getPayload(languageMapNode, preferredLanguage, highlightTerm);
			
//			preview = parseEntity(entityNode);
			
			String[] languageArray = StringUtils.splitByWholeSeparator(preferredLanguage, ",");
			languageArray = StringUtils.stripAll(languageArray);
			List<String> languageList = Arrays.asList(languageArray);
			
			preview = parseEntity(payloadNode, languageList, highlightTerm);

		} catch (Exception e) {
			throw new EntitySuggestionException("Cannot parse suggestion payload: " + payload, e);
		}
		return preview;
	}

	private JsonNode getPayload(JsonNode languageMapNode, String preferredLanguage, String highlightTerm) {

		String[] languageArray = StringUtils.splitByWholeSeparator(preferredLanguage, ",");
		List<String> languageList = Arrays.asList(languageArray);
		languageList.replaceAll(String::trim);
		
		final JsonNodeFactory nodeFactory = JsonNodeFactory.instance;
		ObjectNode node = nodeFactory.objectNode();
		ObjectNode child = nodeFactory.objectNode();
		ObjectNode languageNodeLabel = nodeFactory.objectNode();
		ObjectNode languageNodeProfession = nodeFactory.objectNode();
		ObjectNode languageNodeHiddenLabel = nodeFactory.objectNode();
		ObjectNode languageNodePartOf = nodeFactory.objectNode();
		
		Iterator<Entry<String, JsonNode>> itr = languageMapNode.getFields();
		
		
		while (itr.hasNext()) {
			Entry<String, JsonNode> next = itr.next();
			String highlightLanguage = null;
			if (next.getKey() == WebEntityFields.PREF_LABEL) {
				Iterator<Entry<String, JsonNode>> nodeItr = next.getValue().getFields();
				while (nodeItr.hasNext()) {
					Entry<String, JsonNode> current = nodeItr.next();
//					if (current.getValue().asText().contains(highlightTerm) && languageList.contains(current.getKey())) {// == preferredLanguage) {
//						// keyword match with selected language
//						languageNodeLabel.put(current.getKey(), current.getValue());
//					} else if (current.getValue().asText().contains(highlightTerm)) {
					 if (languageList.contains(current.getKey())) {
							// only language label matches
							languageNodeLabel.put(current.getKey(), current.getValue());
							
							if(highlightLanguage==null && current.getValue().asText().contains(highlightTerm))
								highlightLanguage = current.getKey();
					} 
					 
					if (current.getValue().asText().contains(highlightTerm)) {
						// only keyword match
						//TODO #661: only take first one
						languageNodeLabel.put(current.getKey(), current.getValue());
					}
				}
				if (languageNodeLabel.size() == 0) {
					// if nothing matched, take first entry
					languageNodeLabel.put(next.getValue().getFields().next().getKey(), next.getValue().getFields().next().getValue());
				}
				child.put(WebEntityFields.PREF_LABEL, languageNodeLabel);
			} else if (next.getKey() == WebEntityFields.PROFESSION_OR_OCCUPATION) {
				Iterator<Entry<String, JsonNode>> nodeItr = next.getValue().getFields();
				while (nodeItr.hasNext()) {
					Entry<String, JsonNode> current = nodeItr.next();
//					if (current.getValue().asText().contains(highlightTerm) && languageList.contains(current.getKey())) {
//						// keyword match with selected language
//						languageNodeProfession.put(current.getKey(), current.getValue());
//					} else if (current.getValue().asText().contains(highlightTerm)) {
					if (current.getValue().asText().contains(highlightTerm)) {
						// only keyword match
						languageNodeProfession.put(current.getKey(), current.getValue());
					} else if (languageList.contains(current.getKey())) {
						// only language label matches
						languageNodeProfession.put(current.getKey(), current.getValue());
					}
				}
				if (languageNodeProfession.size() == 0) {
					// if nothing matched, take first entry
					languageNodeProfession.put(next.getValue().getFields().next().getKey(), next.getValue().getFields().next().getValue());
				}
				child.put(WebEntityFields.PROFESSION_OR_OCCUPATION, languageNodeProfession);
			} else if (next.getKey() == WebEntityFields.HIDDEN_LABEL) {
				Iterator<Entry<String, JsonNode>> nodeItr = next.getValue().getFields();
				while (nodeItr.hasNext()) {
					Entry<String, JsonNode> current = nodeItr.next();
					if (current.getKey() == "") {
						// default
						languageNodeHiddenLabel.put(current.getKey(), current.getValue());						
					} else if (languageList.contains(current.getKey())) {
						// language label matches
						languageNodeHiddenLabel.put(current.getKey(), current.getValue());
					}					
				}
				if (languageNodeHiddenLabel.size() == 0) {
					// if nothing matched, take first entry
					languageNodeHiddenLabel.put(next.getValue().getFields().next().getKey(), next.getValue().getFields().next().getValue());
				}
				child.put(WebEntityFields.HIDDEN_LABEL, languageNodeHiddenLabel);
			} else if (next.getKey() == WebEntityFields.IS_PART_OF) {
				Iterator<Entry<String, JsonNode>> nodeItr = next.getValue().getFields();
				
				while (nodeItr.hasNext()) {
					Entry<String, JsonNode> current = nodeItr.next();
					ObjectNode partOfLabels = nodeFactory.objectNode();
					Iterator<String> labelItr = current.getValue().getFieldNames();
					while (labelItr.hasNext()) {
						String languageCode = labelItr.next();
						if (languageList.contains(languageCode)) {
							partOfLabels.put(languageCode, current.getValue().findValue(languageCode));
						} else if (languageCode == "" && labelItr.hasNext() == false) {
							// edge case when there are multiple isPartOf locations and one only has the "" languageCode
							partOfLabels.put(languageCode, current.getValue().findValue(languageCode));							
						}
						//TODO #661: edge case when any isPartOf does not match a label but has more than the "" label?
					}
					languageNodePartOf.put(current.getKey(), partOfLabels);
				}
				if (languageNodePartOf.size() == 0) {
					// if nothing matched, take first entry
					languageNodePartOf.put(next.getValue().getFields().next().getKey(), next.getValue().getFields().next().getValue());
				}
				child.put(WebEntityFields.IS_PART_OF, languageNodePartOf);
			} else {
				// if no language options (e.g. place, type, ...)
				child.put(next.getKey(), next.getValue());
			}
		}
		child.put("requestedLanguages", languageList.toString());
		child.put("solrHighlightedTerm", highlightTerm);
		node.putAll(child);
		return node;
	}

	private EntityPreview parseEntity(JsonNode entityNode,  List<String> preferredLanguages, String highlightTerm) throws UnsupportedEntityTypeException {
		EntityPreview preview;
		JsonNode propertyNode = entityNode.get(SuggestionFields.TYPE);
		String entityType = propertyNode.getTextValue();
		preview = createPreviewObjectInstance(entityType);
		preview.setType(propertyNode.getTextValue());
		
		propertyNode = entityNode.get(SuggestionFields.ID);
		preview.setEntityId(propertyNode.getTextValue());

		//TODO: remove term as no longer used
//		propertyNode = entityNode.get(SuggestionFields.TERM);
//		if (propertyNode != null)
//			preview.setMatchedTerm(propertyNode.getTextValue());

		propertyNode = entityNode.get(WebEntityFields.DEPICTION);
		if (propertyNode != null)
			preview.setDepiction(propertyNode.getTextValue());
		
		Map<String, String> prefLabel = getValuesAsLanguageMap(entityNode, SuggestionFields.PREF_LABEL, preferredLanguages);
		if(!containsHighlightTerm(prefLabel, highlightTerm)){
			String[] highlightLabel = getHighlightLabel(entityNode, SuggestionFields.PREF_LABEL, highlightTerm);
			prefLabel.put(highlightLabel[0], highlightLabel[1]);
		}
		
		//		prefLabel = applyLanguageFilter(prefLabel, preferredLanguages, highlightTerm);
		preview.setPreferredLabel(prefLabel);

		Map<String, List<String>> hiddenLabel = getValuesAsLanguageMapList(entityNode, SuggestionFields.HIDDEN_LABEL, preferredLanguages);
		preview.setHiddenLabel(hiddenLabel);
		
//TODO: #661 remove		
//		List<String> values = getValuesAsList(entityNode, SuggestionFields.HIDDEN_LABEL);
//		preview.setHiddenLabel(values);

		setEntitySpecificProperties(preview, entityNode, preferredLanguages);
		return preview;
	}
	
	private boolean containsHighlightTerm(Map<String, String> prefLabels, String highlightTerm) {
		if(prefLabels == null || prefLabels.isEmpty())
			return false;
		
		Collection<String> entrySet = prefLabels.values();
		for (Iterator<String> iterator = entrySet.iterator(); iterator.hasNext();) {
			if(iterator.next().contains(highlightTerm))
				return true;
		}
		return false;
	}

	private String[] getHighlightLabel(JsonNode entityNode, String key, String highlightTerm) {
		JsonNode jsonNode = entityNode.get(key);
		if (jsonNode != null) {
			Iterator<Entry<String, JsonNode>> itr = jsonNode.getFields();
			Entry<String, JsonNode> currentEntry;
			String value;
			
			while (itr.hasNext()) {
				currentEntry = itr.next();
				value = currentEntry.getValue().asText();
				if(value.contains(highlightTerm))
					return new String[]{currentEntry.getKey(), value};
			}
		}
		return null;
	}
	
	
	private Map<String, List<String>> getValuesAsLanguageMapList(JsonNode payloadNode, String key, List<String> preferredLanguages) {

		JsonNode jsonNode = payloadNode.get(key);
		Map<String, List<String>> languageMap = new HashMap<>();

		if (jsonNode != null) {
			Iterator<Entry<String, JsonNode>> itr = jsonNode.getFields();
			while (itr.hasNext()) {
				Entry<String, JsonNode> currentEntry = itr.next();
				if(preferredLanguages.contains(currentEntry.getKey())){
					ArrayList<String> valueList = new ArrayList<String>();
					for (JsonNode value : currentEntry.getValue()) {
							//need to extract text value, otherwise 
							valueList.add( value.getTextValue());
					}
					languageMap.put(currentEntry.getKey(), valueList);
				}
			}
		}
		return languageMap;
	}

	private Map<String, String> getValuesAsLanguageMap(JsonNode payloadNode, String key, List<String> preferredLanguages) {

		JsonNode jsonNode = payloadNode.get(key);
		return extractLanguageMap(jsonNode, preferredLanguages);
	}

	private Map<String, String> extractLanguageMap(JsonNode jsonNode, List<String> preferredLanguages) {
		Map<String, String> languageMap = new HashMap<>();

		//TODO hack for places
		String defaultLabel = null;
		final String defaultKey = "";
		
		if (jsonNode != null) {
			Iterator<Entry<String, JsonNode>> itr = jsonNode.getFields();
			while (itr.hasNext()) {
				Entry<String, JsonNode> currentEntry = itr.next();
				//include only prefferedLanguages
				if(preferredLanguages.contains(currentEntry.getKey())){
//					if (currentEntry.getValue().getClass().getName() == "org.codehaus.jackson.node.ArrayNode") {
//						languageMap.put(currentEntry.getKey(), currentEntry.getValue().toString());			
//					} else 
					if (currentEntry.getValue().asText() != null) {
						languageMap.put(currentEntry.getKey(), currentEntry.getValue().asText());				
					}
				}
				
				if(defaultKey.equals(currentEntry.getKey())){
					defaultLabel = currentEntry.getValue().asText();
				}
			}
		}
		
		//TODO hack for places
		//add default label if needed
		if(languageMap.isEmpty() && defaultLabel != null)
			languageMap.put(defaultKey, defaultLabel);
		return languageMap;
	}
	
	private void setEntitySpecificProperties(EntityPreview preview, JsonNode payloadNode, List<String> preferredLanguages) {
		switch (preview.getEntityType()) {
		case Agent:
			putAgentSpecificProperties((AgentPreview) preview, payloadNode, preferredLanguages);
			break;
		case Concept:
			putConceptSpecificProperties((ConceptPreview) preview, payloadNode);
			break;
		case Place:
			putPlaceSpecificProperties((PlacePreview) preview, payloadNode, preferredLanguages);
			break;
		case Timespan:
			putTimespanSpecificProperties((TimeSpanPreview) preview, payloadNode);
			break;
		default:
			break;
		}
	}

	private void putConceptSpecificProperties(ConceptPreview preview, JsonNode payloadNode) {
		JsonNode propertyNode = payloadNode.get(SuggestionFields.IN_SCHEME);
		if (propertyNode != null)
			preview.setInscheme(propertyNode.getTextValue());

	}

	private void putAgentSpecificProperties(AgentPreview preview, JsonNode payloadNode, List<String> preferredLanguages) {

		JsonNode propertyNode = payloadNode.get(SuggestionFields.DATE_OF_BIRTH);
		if (propertyNode != null)
			preview.setDateOfBirth(propertyNode.getTextValue());

		propertyNode = payloadNode.get(SuggestionFields.DATE_OF_DEATH);
		if (propertyNode != null)
			preview.setDateOfDeath(propertyNode.getTextValue());

		propertyNode = payloadNode.get(SuggestionFields.PROFESSION_OR_OCCUPATION);
		if (propertyNode != null){
			Map<String, List<String>> values = getValuesAsLanguageMapList(payloadNode, SuggestionFields.PROFESSION_OR_OCCUPATION, preferredLanguages);
			preview.setProfessionOrOccuation(values);
		}

	}

	private List<String> getValuesAsList(JsonNode payloadNode, String key) {
		
		JsonNode arrayNode = payloadNode.get(key);
		
		List<String> values = null;
		if (arrayNode != null) {
			values = new ArrayList<String>(arrayNode.size());
			for (JsonNode profession : arrayNode) {
				values.add(profession.toString());
			}
		}
		return values;
	}

	private void putPlaceSpecificProperties(PlacePreview preview, JsonNode payloadNode, List<String> preferredLanguages) {
		JsonNode propertyNode = payloadNode.get(SuggestionFields.IS_PART_OF);
		if (propertyNode != null) {
			List<ResourcePreview> isPartOf = new ArrayList<ResourcePreview>();
			
			Entry<String, JsonNode> entry;
			ResourcePreview resourcePreview;
			Map<String, String> languageMap;
			for (Iterator<Entry<String, JsonNode>> iterator = propertyNode.getFields(); iterator.hasNext();) {
				entry = (Entry<String, JsonNode>) iterator.next();
				resourcePreview = new ResourcePreviewImpl(); 
				
				resourcePreview.setHttpUri(entry.getKey());
				languageMap = extractLanguageMap(entry.getValue(), preferredLanguages);
				resourcePreview.setPrefLabel(languageMap);
				isPartOf.add(resourcePreview);
			}
			
//TODO: #661 remove					
//			for(JsonNode resourcePreviewNode: propertyNode) {
//				ResourcePreview resourcePreview = new ResourcePreviewImpl();
////				String prefLabel = resourcePreviewNode.get(SuggestionFields.PREF_LABEL).getTextValue();
////				resourcePreview.setPrefLabel(prefLabel);
//				String httpUri = resourcePreviewNode.get(SuggestionFields.ID).getTextValue();
//				resourcePreview.setHttpUri(httpUri);
//				
//				Map<String, String> prefLabel = getValuesAsLanguageMap(resourcePreviewNode, SuggestionFields.PREF_LABEL);
//				resourcePreview.setPrefLabel(prefLabel);
//				System.out.println();
//				isPartOf.add(resourcePreview);	
//			}
			preview.setIsPartOf(isPartOf);
		}
	}

	private void putTimespanSpecificProperties(TimeSpanPreview preview, JsonNode payloadNode) {
		JsonNode propertyNode = payloadNode.get(SuggestionFields.TIME_SPAN_START);

		if (propertyNode != null)
			preview.setBegin(propertyNode.getTextValue());

		propertyNode = payloadNode.get(SuggestionFields.TIME_SPAN_END);
		if (propertyNode != null)
			preview.setEnd(propertyNode.getTextValue());

	}

	private EntityPreview createPreviewObjectInstance(String entityTypeStr) throws UnsupportedEntityTypeException {
		// EntityTypes entityType = EntityTypes.getByHttpUri(entityTypeUri);

		EntityTypes entityType = EntityTypes.getByInternalType(entityTypeStr);
		return EntityPreviewObjectFactory.getInstance().createObjectInstance(entityType);
	}

}
