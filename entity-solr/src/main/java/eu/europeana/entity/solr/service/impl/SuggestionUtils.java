package eu.europeana.entity.solr.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.annotate.JsonAutoDetect.Visibility;
import org.codehaus.jackson.annotate.JsonMethod;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.JsonNodeFactory;
import org.codehaus.jackson.node.ObjectNode;
import org.codehaus.jackson.node.TextNode;

import eu.europeana.entity.definitions.exceptions.UnsupportedEntityTypeException;
import eu.europeana.entity.definitions.model.ResourcePreview;
import eu.europeana.entity.definitions.model.ResourcePreviewImpl;
import eu.europeana.entity.definitions.model.vocabulary.EntityTypes;
import eu.europeana.entity.definitions.model.vocabulary.WebEntityConstants;
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
			
			JsonNode languageMapNode = objectMapper.readTree(payload);
			
			JsonNode entityNode = getPayload(languageMapNode, preferredLanguage, highlightTerm);

			preview = parseEntity(entityNode);

		} catch (Exception e) {
			throw new EntitySuggestionException("Cannot parse suggestion payload: " + payload, e);
		}
		return preview;
	}

	private JsonNode getPayload(JsonNode languageMapNode, String preferredLanguage, String highlightTerm) {
		final JsonNodeFactory nodeFactory = JsonNodeFactory.instance;
		ObjectNode node = nodeFactory.objectNode();
		ObjectNode child = nodeFactory.objectNode();
		ObjectNode languageNodeLabel = nodeFactory.objectNode();
		ObjectNode languageNodeProfession = nodeFactory.objectNode();
		
		Iterator<Entry<String, JsonNode>> itr = languageMapNode.getFields();
		
		while (itr.hasNext()) {
			Entry<String, JsonNode> next = itr.next();
			if (next.getKey() == "prefLabel") {
				Iterator<Entry<String, JsonNode>> nodeItr = next.getValue().getFields();
				while (nodeItr.hasNext()) {
					Entry<String, JsonNode> current = nodeItr.next();
					if (current.getValue().asText().contains(highlightTerm) && current.getKey() == preferredLanguage) {
						// keyword match with selected language
						languageNodeLabel.put(current.getKey(), current.getValue());
					} else if (current.getValue().asText().contains(highlightTerm)) {
						// only keyword match
						languageNodeLabel.put(current.getKey(), current.getValue());
					} else if (current.getKey() == preferredLanguage) {
						// only language label matches
						languageNodeLabel.put(current.getKey(), current.getValue());
					}
				}
				child.put("prefLabel", languageNodeLabel);
			} else if (next.getKey() == "professionOrOccupation") {
				Iterator<Entry<String, JsonNode>> nodeItr = next.getValue().getFields();
				while (nodeItr.hasNext()) {
					Entry<String, JsonNode> current = nodeItr.next();
					if (current.getValue().asText().contains(highlightTerm) && current.getKey() == preferredLanguage) {
						// keyword match with selected language
						languageNodeProfession.put(current.getKey(), current.getValue());
					} else if (current.getValue().asText().contains(highlightTerm)) {
						// only keyword match
						languageNodeProfession.put(current.getKey(), current.getValue());
					} else if (current.getKey() == preferredLanguage) {
						// only language label matches
						languageNodeProfession.put(current.getKey(), current.getValue());
					} else {
						// if nothing matches
						languageNodeProfession.put(current.getKey(), current.getValue());
					}
				}
				child.put("professionOrOccupation", languageNodeProfession);
			} else {
				// if no language options (e.g. place, type, ...)
				child.put(next.getKey(), next.getValue());
			}
		}
		node.putAll(child);
		return node;
	}

	private EntityPreview parseEntity(JsonNode entityNode) throws UnsupportedEntityTypeException {
		EntityPreview preview;
		JsonNode propertyNode = entityNode.get(SuggestionFields.TYPE);
		String entityType = propertyNode.getTextValue();
		preview = createPreviewObjectInstance(entityType);
		preview.setType(propertyNode.getTextValue());
		
		propertyNode = entityNode.get(SuggestionFields.ID);
		preview.setEntityId(propertyNode.getTextValue());

		//TODO: remove term as no longer used
		propertyNode = entityNode.get(SuggestionFields.TERM);
		if (propertyNode != null)
			preview.setMatchedTerm(propertyNode.getTextValue());

		propertyNode = entityNode.get(WebEntityFields.DEPICTION);
		if (propertyNode != null)
			preview.setDepiction(propertyNode.getTextValue());
		
		Map<String, String> prefLabel = getValuesAsLanguageMap(entityNode, SuggestionFields.PREF_LABEL);
		preview.setPreferredLabel(prefLabel);

		Map<String, List<String>> hiddenLabel = getValuesAsLanguageMapList(entityNode, SuggestionFields.HIDDEN_LABEL);
		preview.setHiddenLabel(hiddenLabel);
		
//TODO: #661 remove		
//		List<String> values = getValuesAsList(entityNode, SuggestionFields.HIDDEN_LABEL);
//		preview.setHiddenLabel(values);

		setEntitySpecificProperties(preview, entityNode);
		return preview;
	}
	
	private Map<String, List<String>> getValuesAsLanguageMapList(JsonNode payloadNode, String key) {

		JsonNode jsonNode = payloadNode.get(key);
		Map<String, List<String>> languageMap = new HashMap<>();

		if (jsonNode != null) {
			Iterator<Entry<String, JsonNode>> itr = jsonNode.getFields();
			while (itr.hasNext()) {
				Entry<String, JsonNode> currentEntry = itr.next();
				ArrayList<String> valueList = new ArrayList<String>();
				for (JsonNode value : currentEntry.getValue()) {
						//need to extract text value, otherwise 
						valueList.add( value.getTextValue());
				}
				languageMap.put(currentEntry.getKey(), valueList);
			}
		}
		return languageMap;
	}

	private Map<String, String> getValuesAsLanguageMap(JsonNode payloadNode, String key) {

		JsonNode jsonNode = payloadNode.get(key);
		return extractLanguageMap(jsonNode);
	}

	private Map<String, String> extractLanguageMap(JsonNode jsonNode) {
		Map<String, String> languageMap = new HashMap<>();

		if (jsonNode != null) {
			Iterator<Entry<String, JsonNode>> itr = jsonNode.getFields();
			while (itr.hasNext()) {
				Entry<String, JsonNode> currentEntry = itr.next();
				if (currentEntry.getValue().getClass().getName() == "org.codehaus.jackson.node.ArrayNode") {
					languageMap.put(currentEntry.getKey(), currentEntry.getValue().toString());			
				} else if (currentEntry.getValue().asText() != null) {
					languageMap.put(currentEntry.getKey(), currentEntry.getValue().asText());				
				}
			}
		}
		return languageMap;
	}
	
	private void setEntitySpecificProperties(EntityPreview preview, JsonNode payloadNode) {
		switch (preview.getEntityType()) {
		case Agent:
			putAgentSpecificProperties((AgentPreview) preview, payloadNode);
			break;
		case Concept:
			putConceptSpecificProperties((ConceptPreview) preview, payloadNode);
			break;
		case Place:
			putPlaceSpecificProperties((PlacePreview) preview, payloadNode);
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

	private void putAgentSpecificProperties(AgentPreview preview, JsonNode payloadNode) {

		JsonNode propertyNode = payloadNode.get(SuggestionFields.DATE_OF_BIRTH);
		if (propertyNode != null)
			preview.setDateOfBirth(propertyNode.getTextValue());

		propertyNode = payloadNode.get(SuggestionFields.DATE_OF_DEATH);
		if (propertyNode != null)
			preview.setDateOfDeath(propertyNode.getTextValue());

		propertyNode = payloadNode.get(SuggestionFields.PROFESSION_OR_OCCUPATION);
		if (propertyNode != null){
			Map<String, List<String>> values = getValuesAsLanguageMapList(payloadNode, SuggestionFields.PROFESSION_OR_OCCUPATION);
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

	private void putPlaceSpecificProperties(PlacePreview preview, JsonNode payloadNode) {
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
				languageMap = extractLanguageMap(entry.getValue());
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
