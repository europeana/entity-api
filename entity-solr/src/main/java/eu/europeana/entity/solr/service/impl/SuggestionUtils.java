package eu.europeana.entity.solr.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.annotate.JsonAutoDetect.Visibility;
import org.codehaus.jackson.annotate.JsonMethod;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;

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
import eu.europeana.entity.web.model.view.OrganizationPreview;
import eu.europeana.entity.web.model.view.PlacePreview;
import eu.europeana.entity.web.model.view.TimeSpanPreview;

public class SuggestionUtils {

	private final Logger log = LogManager.getLogger(getClass());

	protected static ObjectMapper objectMapper = new ObjectMapper().setVisibility(JsonMethod.FIELD, Visibility.ANY)
			.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	protected static final JsonFactory jsonFactory = new JsonFactory();

	public Logger getLog() {
		return log;
	}

	public EntityPreview parsePayload(String payload, String[] preferredLanguages, String highlightTerm) throws EntitySuggestionException {	
		EntityPreview preview = null;
		try {
			JsonParser parser = jsonFactory.createJsonParser(payload);
			parser.setCodec(objectMapper);
			
			JsonNode payloadNode = objectMapper.readTree(payload);
			
			preview = parseEntity(payloadNode, Arrays.asList(preferredLanguages), highlightTerm);

		} catch (Exception e) {
			throw new EntitySuggestionException("Cannot parse suggestion payload: " + payload, e);
		}
		return preview;
	}

	private EntityPreview parseEntity(JsonNode entityNode,  List<String> preferredLanguages, String highlightTerm) throws UnsupportedEntityTypeException {
		EntityPreview preview;
		JsonNode propertyNode = entityNode.get(SuggestionFields.TYPE);
		String entityType = propertyNode.getTextValue();
		preview = createPreviewObjectInstance(entityType);
		preview.setType(propertyNode.getTextValue());
		
		propertyNode = entityNode.get(SuggestionFields.ID);
		preview.setEntityId(propertyNode.getTextValue());

		propertyNode = entityNode.get(WebEntityFields.DEPICTION);
		if (propertyNode != null)
			preview.setDepiction(propertyNode.getTextValue());
		
			
		Map<String, String> prefLabel = getValuesAsLanguageMap(entityNode, SuggestionFields.PREF_LABEL, preferredLanguages);
		if(!containsHighlightTerm(prefLabel, highlightTerm)){
			String[] highlightLabel = getHighlightLabel(entityNode, SuggestionFields.PREF_LABEL, highlightTerm);
			//currently missmatch between the pref label and alt label, edmAcronym
			if(highlightLabel != null){
				String matchedLanguage = highlightLabel[0];
				prefLabel.put(matchedLanguage, highlightLabel[1]);
				//#56 add the matched label to language list
				preferredLanguages = updateLanguageList(preferredLanguages, matchedLanguage);
			}
		}
		preview.setPreferredLabel(prefLabel);

		Map<String, List<String>> hiddenLabel = getValuesAsLanguageMapList(entityNode, SuggestionFields.HIDDEN_LABEL, preferredLanguages);
		preview.setHiddenLabel(hiddenLabel);

		setEntitySpecificProperties(preview, entityNode, preferredLanguages);
		return preview;
	}

	private List<String> updateLanguageList(List<String> preferredLanguages, String matchedLanguage) {
		List<String> updatedLanguageList = new ArrayList<String>(preferredLanguages.size() +1);
		updatedLanguageList.addAll(preferredLanguages);
		updatedLanguageList.add(matchedLanguage);
		preferredLanguages = updatedLanguageList;
		return preferredLanguages;
	}
	
	private boolean containsHighlightTerm(Map<String, String> prefLabels, String highlightTerm) {
		if(prefLabels == null || prefLabels.isEmpty())
			return false;
		
		Collection<String> entrySet = prefLabels.values();
		String label;
		for (Iterator<String> iterator = entrySet.iterator(); iterator.hasNext();) {
			label = iterator.next(); 
			//if highlighter doesn't work, the searched term is lowercase
			if(label.contains(highlightTerm) || label.toLowerCase().contains(highlightTerm))
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
				//if the highlighter doesn't work, use searched term ignoring case
				if(value.toLowerCase().contains(highlightTerm))
					return new String[]{currentEntry.getKey(), value};
			}
		}
		return null;
	}
	
	
	private Map<String, List<String>> getValuesAsLanguageMapList(JsonNode payloadNode, String key, List<String> preferredLanguages) {

		JsonNode jsonNode = payloadNode.get(key);
		Map<String, List<String>> languageMap = new HashMap<>();

		boolean includeAllLanguages = preferredLanguages.contains(WebEntityConstants.PARAM_LANGUAGE_ALL);

		if (jsonNode != null) {
			Iterator<Entry<String, JsonNode>> itr = jsonNode.getFields();
			while (itr.hasNext()) {
				Entry<String, JsonNode> currentEntry = itr.next();
				//include only preferredLanguages, allow also All
				if(includeAllLanguages || preferredLanguages.contains(currentEntry.getKey())){
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

//	/**
//	 * This method addresses use case, when content of payload field
//	 * is a JsonArray
//	 * @param payloadNode The payload data
//	 * @param key The name of the field
//	 * @param preferredLanguages The list of possible languages
//	 * @return
//	 */
//	private Map<String, List<String>> getValuesAsLanguageMapListFromJsonArray(
//			JsonNode payloadNode, String key, List<String> preferredLanguages) {
//
//		Map<String, List<String>> languageMap = new HashMap<>();
//
//		for(String language : preferredLanguages) {
//			JsonNode jsonNode = payloadNode.get(key + "." + language);
//	
//			if (jsonNode != null) {
//				Iterator<JsonNode> itr = jsonNode.getElements();
//				while (itr.hasNext()) {
//					JsonNode currentEntry = itr.next();
//					ArrayList<String> valueList = new ArrayList<String>();
//					valueList.add(currentEntry.getTextValue());
//					languageMap.put(language, valueList);
//				}
//			}
//		}
//		return languageMap;
//	}

	private Map<String, String> getValuesAsLanguageMap(JsonNode payloadNode, String key, List<String> preferredLanguages) {

		JsonNode jsonNode = payloadNode.get(key);
		return extractLanguageMap(jsonNode, preferredLanguages);
	}

	/**
	 * This method addresses use case, when content of payload field
	 * is a text node
	 * @param payloadNode The payload data
	 * @param key The name of the field
	 * @param preferredLanguages The list of possible languages
	 * @return
	 */
//	private Map<String, String> getValuesAsLanguageMapFromTextNode(
//			JsonNode payloadNode, String key, List<String> preferredLanguages) {
//
//		Map<String, String> languageMap = new HashMap<>();
//
//		for(String language : preferredLanguages) {
//			JsonNode jsonNode = payloadNode.get(key + "." + language);
//			if (jsonNode != null)
//				languageMap.put(language, jsonNode.getTextValue());
//		}		
//		return languageMap;
//	}

	private Map<String, String> extractLanguageMap(JsonNode jsonNode, List<String> preferredLanguages) {
		Map<String, String> languageMap = new HashMap<>();

		//TODO hack for places
		String defaultLabel = null;
		final String defaultKey = "";
		boolean includeAllLanguages = preferredLanguages.contains(WebEntityConstants.PARAM_LANGUAGE_ALL);
		
		if (jsonNode != null) {
			Iterator<Entry<String, JsonNode>> itr = jsonNode.getFields();
			while (itr.hasNext()) {
				Entry<String, JsonNode> currentEntry = itr.next();
				//include only preferredLanguages, allow also All
				if(includeAllLanguages || preferredLanguages.contains(currentEntry.getKey())){
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
		case Organization:
			putOrganizationSpecificProperties((OrganizationPreview) preview, payloadNode, preferredLanguages);
			break;
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

	private void putOrganizationSpecificProperties(
			OrganizationPreview preview, JsonNode payloadNode, List<String> preferredLanguages) {

		Map<String, List<String>> acronym = getValuesAsLanguageMapList(
				payloadNode, WebEntityConstants.ACRONYM, preferredLanguages);
		preview.setAcronym(acronym);
		
		//only english versions are available for now, and the structure is not a language map
		JsonNode propertyNode = payloadNode.get(WebEntityFields.COUNTRY);
		if (propertyNode != null)
			preview.setCountry(propertyNode.getTextValue());
		
		//only english versions are available for now, and the structure is not a language map
		propertyNode = payloadNode.get(WebEntityFields.ORGANIZATION_DOMAIN);
		if (propertyNode != null)
			preview.setOrganizationDomain(propertyNode.getTextValue());
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
		EntityTypes entityType = EntityTypes.getByInternalType(entityTypeStr);
		return EntityPreviewObjectFactory.getInstance().createObjectInstance(entityType);
	}

}
