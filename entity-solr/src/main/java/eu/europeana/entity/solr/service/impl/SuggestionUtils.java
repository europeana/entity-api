package eu.europeana.entity.solr.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

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

	protected static ObjectMapper objectMapper = new ObjectMapper();
//	.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
//			.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//	protected static final JsonFactory jsonFactory = new JsonFactory();

	public Logger getLog() {
		return log;
	}

	/**
	 * This method parses a payload employing preferred languages and highlighted terms.
	 * @param payload
	 * @param preferredLanguages
	 * @param highlightTerms
	 * @return parsed payload
	 * @throws EntitySuggestionException
	 */
	public EntityPreview parsePayload(String payload, String[] preferredLanguages, Set<String> highlightTerms) 
			throws EntitySuggestionException {	
		EntityPreview preview = null;
		try {
			JsonNode payloadNode = objectMapper.readTree(payload);
			//convert to mutable list 
			//increase the size with 2 as additional languages may be added by language logic  
			List<String> prefLanguagesList = new ArrayList<String>(preferredLanguages.length + 2);
			prefLanguagesList.addAll(Arrays.asList(preferredLanguages));
			preview = parseEntity(payloadNode, prefLanguagesList, highlightTerms);

		} catch (Exception e) {
			throw new EntitySuggestionException("Cannot parse suggestion payload: " + payload, e);
		}
		return preview;
	}

	/**
	 * This method parses a payload employing preferred languages and highlighted terms.
	 * @param payload
	 * @param preferredLanguages
	 * @param highlightTerms
	 * @return parsed payload
	 * @throws EntitySuggestionException
	 */
//	public EntityPreview parsePayloadByLanguage(String payload, String[] preferredLanguages, Set<String> highlightTerms) 
//			throws EntitySuggestionException {	
//		EntityPreview preview = null;
//		try {
////			JsonParser parser = jsonFactory.createJsonParser(payload);
////			parser.setCodec(objectMapper);
//			
//			JsonNode payloadNode = objectMapper.readTree(payload);
//			//convert to mutable list 
//			//increase the size with 2 as additional languages may be added by language logic  
//			List<String> prefLanguagesList = new ArrayList<String>(preferredLanguages.length + 2);
//			prefLanguagesList.addAll(Arrays.asList(preferredLanguages));
//			preview = parseEntity(payloadNode, prefLanguagesList, highlightTerms);
//
//		} catch (Exception e) {
//			throw new EntitySuggestionException("Cannot parse suggestion payload: " + payload, e);
//		}
//		return preview;
//	}

	/**
	 * This method parses entity employing preferred languages and highlighted terms.
	 * @param entityNode
	 * @param preferredLanguages
	 * @param highlightTerms
	 * @return parsed entity
	 * @throws UnsupportedEntityTypeException
	 */
	private EntityPreview parseEntity(JsonNode entityNode,  List<String> preferredLanguages, Set<String> highlightTerms) 
			throws UnsupportedEntityTypeException {
		EntityPreview preview;
		JsonNode propertyNode = entityNode.get(SuggestionFields.TYPE);
		String entityType = propertyNode.asText();
		preview = createPreviewObjectInstance(entityType);
		preview.setType(propertyNode.asText());
		
		propertyNode = entityNode.get(SuggestionFields.ID);
		preview.setEntityId(propertyNode.asText());

		propertyNode = entityNode.get(WebEntityFields.DEPICTION);
		if (propertyNode != null)
			preview.setDepiction(propertyNode.asText());
		
		//filter prefLabels to keep only prefered languages 	
		Map<String, String> prefLabel = getValuesAsLanguageMap(entityNode, SuggestionFields.PREF_LABEL, preferredLanguages);
		if(!containsHighlightTerm(prefLabel, highlightTerms)){
			String[] highlightLabel = getHighlightLabel(entityNode, highlightTerms);
			//currently missmatch between the pref label and alt label, edmAcronym
			if(highlightLabel != null){
				//#56 add the matched label to language list
				String matchedLanguage = highlightLabel[0];
				prefLabel.put(matchedLanguage, highlightLabel[1]);
				preferredLanguages.add(matchedLanguage);
			}
		}
		//Fallback no pref label matched
		if (prefLabel.isEmpty()) {
			//no prefLabel was matched, the suggestion was based on the acronym
			log.error("Fallback, return all languages, no preferredLabel matched for entity: " + preview.getEntityId() 
				+ ", using searched terms: " + StringUtils.join(highlightTerms, ", ") 
				+ ", and languages: " + StringUtils.join(preferredLanguages, ','));
			
			//#EA-1368 include all languages
			preferredLanguages.add(WebEntityConstants.PARAM_LANGUAGE_ALL);
			
			prefLabel = getValuesAsLanguageMap(entityNode, SuggestionFields.PREF_LABEL, preferredLanguages);		
		} 
		
		preview.setPreferredLabel(prefLabel);

		Map<String, List<String>> altLabel = getValuesAsLanguageMapList(entityNode, SuggestionFields.ALT_LABEL, preferredLanguages);
		preview.setAltLabel(altLabel);
		
		
		Map<String, List<String>> hiddenLabel = getValuesAsLanguageMapList(entityNode, SuggestionFields.HIDDEN_LABEL, preferredLanguages);
		preview.setHiddenLabel(hiddenLabel);
		
		putIsShownByProperties(entityNode, preview);
				
		setEntitySpecificProperties(preview, entityNode, preferredLanguages);
		return preview;
	}

	/**
	 * This method checks if highlight term from provided set is included in prefLabel.
	 * @param prefLabels
	 * @param highlightTerms
	 * @return true if highlight term found in prefLabel
	 */
	private boolean containsHighlightTerm(Map<String, String> prefLabels, Set<String> highlightTerms) {
		if(prefLabels == null || prefLabels.isEmpty())
			return false;
		
		String highlightTerm;
		String label;
		Collection<String> labels = prefLabels.values();
		
		for (Iterator<String> iterator = highlightTerms.iterator(); iterator.hasNext();) {
			highlightTerm = (String) iterator.next();
			
			for (Iterator<String> labelIterator = labels.iterator(); labelIterator.hasNext();) {
				label = labelIterator.next(); 
				
				if(label.contains(highlightTerm) || label.toLowerCase().contains(highlightTerm.toLowerCase()))
					return true;
			}
		}
		
		return false;
	}

	/**
	 * If highlighted terms are not included in prefLabel we look in json node value.
	 * @param entityNode
	 * @param highlightTerms
	 * @return highlighted term if it is included in json node value
	 */
	private String[] getHighlightLabel(JsonNode entityNode, Set<String> highlightTerms) {
		JsonNode jsonNode = entityNode.get(SuggestionFields.PREF_LABEL);
		if (jsonNode != null) {
			Iterator<Entry<String, JsonNode>> itr = jsonNode.fields();
			Entry<String, JsonNode> currentEntry;
			String label;
			
			while (itr.hasNext()) {
				currentEntry = itr.next();
				label = currentEntry.getValue().asText();
				//if the highlighter doesn't work, use searched term ignoring case
				for (String highlightTerm : highlightTerms) {
					if(label.toLowerCase().contains(highlightTerm.toLowerCase()))
						return new String[]{currentEntry.getKey(), label};
				}
			}
		}
		return null;
	}
	
	
	private Map<String, List<String>> getValuesAsLanguageMapList(JsonNode payloadNode, String key, List<String> preferredLanguages) {

		JsonNode jsonNode = payloadNode.get(key);
		Map<String, List<String>> languageMap = new HashMap<>();

		boolean includeAllLanguages = preferredLanguages.contains(WebEntityConstants.PARAM_LANGUAGE_ALL);

		if (jsonNode != null) {
			Iterator<Entry<String, JsonNode>> itr = jsonNode.fields();
			while (itr.hasNext()) {
				Entry<String, JsonNode> currentEntry = itr.next();
				// include only preferredLanguages, allow also All
				if(includeAllLanguages || preferredLanguages.contains(currentEntry.getKey())){
					ArrayList<String> valueList = new ArrayList<String>();
					for (JsonNode value : currentEntry.getValue()) {
							//need to extract text value, otherwise 
							valueList.add( value.textValue());
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
		boolean includeAllLanguages = preferredLanguages.contains(WebEntityConstants.PARAM_LANGUAGE_ALL);
		
		if (jsonNode != null) {
			Iterator<Entry<String, JsonNode>> itr = jsonNode.fields();
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
			preview.setInscheme(propertyNode.textValue());

	}

	/**
	 * This method reads isShownBy properties from the payload and saves them in entity
	 * preview object
	 * @param payloadNode
	 * @param preview
	 */
	private void putIsShownByProperties(JsonNode payloadNode, EntityPreview preview) {
		JsonNode propertyNode = payloadNode.get(SuggestionFields.IS_SHOWN_BY);
		if (propertyNode != null) {
		    JsonNode idNode = propertyNode.get(WebEntityFields.ID);
		    if (idNode != null)
			preview.setIsShownById(idNode.textValue());
		    JsonNode sourceNode = propertyNode.get(WebEntityFields.SOURCE);
		    if (sourceNode != null)
			preview.setIsShownBySource(sourceNode.textValue());
		    JsonNode thumbnailNode = propertyNode.get(WebEntityFields.THUMBNAIL);
		    if (thumbnailNode != null)
			preview.setIsShownByThumbnail(idNode.textValue());
		}
	}

	private void putAgentSpecificProperties(AgentPreview preview, JsonNode payloadNode, List<String> preferredLanguages) {

		JsonNode propertyNode = payloadNode.get(SuggestionFields.DATE_OF_BIRTH);
		if (propertyNode != null)
			preview.setDateOfBirth(propertyNode.textValue());

		propertyNode = payloadNode.get(SuggestionFields.DATE_OF_DEATH);
		if (propertyNode != null)
			preview.setDateOfDeath(propertyNode.textValue());

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
			preview.setCountry(propertyNode.textValue());
		
		//only english versions are available for now, and the structure is not a language map
		propertyNode = payloadNode.get(WebEntityFields.ORGANIZATION_DOMAIN);
		if (propertyNode != null)
			preview.setOrganizationDomain(propertyNode.textValue());
	}

	private void putPlaceSpecificProperties(PlacePreview preview, JsonNode payloadNode, List<String> preferredLanguages) {
		JsonNode propertyNode = payloadNode.get(SuggestionFields.IS_PART_OF);
		if (propertyNode != null) {
			List<ResourcePreview> isPartOf = new ArrayList<ResourcePreview>();
			
			Entry<String, JsonNode> entry;
			ResourcePreview resourcePreview;
			Map<String, String> languageMap;
			for (Iterator<Entry<String, JsonNode>> iterator = propertyNode.fields(); iterator.hasNext();) {
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
			preview.setBegin(propertyNode.textValue());

		propertyNode = payloadNode.get(SuggestionFields.TIME_SPAN_END);
		if (propertyNode != null)
			preview.setEnd(propertyNode.textValue());

	}

	private EntityPreview createPreviewObjectInstance(String entityTypeStr) throws UnsupportedEntityTypeException {
		EntityTypes entityType = EntityTypes.getByInternalType(entityTypeStr);
		return EntityPreviewObjectFactory.getInstance().createObjectInstance(entityType);
	}

}
