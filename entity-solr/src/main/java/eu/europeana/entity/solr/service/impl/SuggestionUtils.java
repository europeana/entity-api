package eu.europeana.entity.solr.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.annotate.JsonAutoDetect.Visibility;
import org.codehaus.jackson.annotate.JsonMethod;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ArrayNode;

import eu.europeana.entity.definitions.exceptions.UnsupportedEntityTypeException;
import eu.europeana.entity.definitions.model.ResourcePreview;
import eu.europeana.entity.definitions.model.ResourcePreviewImpl;
import eu.europeana.entity.definitions.model.vocabulary.EntityTypes;
//<<<<<<< HEAD
//=======
import eu.europeana.entity.definitions.vocabulary.WebEntityConstants;
//>>>>>>> refs/heads/develop
import eu.europeana.entity.definitions.vocabulary.WebEntityFields;
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

	public EntityPreview parsePayload(String payload, String preferredLanguage) throws EntitySuggestionException {

		EntityPreview preview = null;
		try {
			JsonParser parser = jsonFactory.createJsonParser(payload);
			parser.setCodec(objectMapper);
			
			JsonNode languageMapNode = objectMapper.readTree(payload);
			
			JsonNode entityNode = getPayload(languageMapNode, preferredLanguage);

			preview = parseEntity(entityNode);

		} catch (Exception e) {
			throw new EntitySuggestionException("Cannot parse suggestion payload: " + payload, e);
		}
		return preview;
	}

	private JsonNode getPayload(JsonNode languageMapNode, String preferredLanguage) {
		if (languageMapNode.get(preferredLanguage) != null) {
			// first priority: preferredLanguage
			return languageMapNode.get(preferredLanguage);
		} else if (languageMapNode.get(WebEntityConstants.PARAM_LANGUAGE_EN) != null) {
			// default: english
			return languageMapNode.get(WebEntityConstants.PARAM_LANGUAGE_EN);
		} else {
			// fallback: first entry
			return languageMapNode.get(0);
		}
	}

	private EntityPreview parseEntity(JsonNode entityNode) throws UnsupportedEntityTypeException {
		EntityPreview preview;
		JsonNode propertyNode = entityNode.get(SuggestionFields.TYPE);
		String entityType = propertyNode.getTextValue();
		preview = createPreviewObjectInstance(entityType);
		preview.setType(propertyNode.getTextValue());
		
		propertyNode = entityNode.get(SuggestionFields.ID);
		preview.setEntityId(propertyNode.getTextValue());

		propertyNode = entityNode.get(SuggestionFields.TERM);
		if (propertyNode != null)
			preview.setMatchedTerm(propertyNode.getTextValue());

		propertyNode = entityNode.get(WebEntityFields.DEPICTION);
		if (propertyNode != null)
			preview.setDepiction(propertyNode.getTextValue());

		propertyNode = entityNode.get(SuggestionFields.PREF_LABEL);
		preview.setPreferredLabel(propertyNode.getTextValue());

		List<String> values = getValuesAsList(entityNode, SuggestionFields.HIDDEN_LABEL);
		preview.setHiddenLabel(values);

		setEntitySpecificProperties(preview, entityNode);
		return preview;
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

		List<String> values = getValuesAsList(payloadNode, SuggestionFields.PROFESSION_OR_OCCUPATION);
		preview.setProfessionOrOccuation(values);

	}

	private List<String> getValuesAsList(JsonNode payloadNode, String key) {
		ArrayNode arrayNode = (ArrayNode) payloadNode.get(key);
		List<String> values = null;
		if (arrayNode != null) {
			values = new ArrayList<String>(arrayNode.size());
			for (JsonNode profession : arrayNode) {
				values.add(profession.asText());
			}
		}
		return values;
	}

	private void putPlaceSpecificProperties(PlacePreview preview, JsonNode payloadNode) {
		JsonNode propertyNode = payloadNode.get(SuggestionFields.IS_PART_OF);
		if (propertyNode != null) {
			List<ResourcePreview> isPartOf = new ArrayList<ResourcePreview>();
			for(JsonNode resourcePreviewNode: propertyNode) {
				ResourcePreview resourcePreview = new ResourcePreviewImpl();
				String prefLabel = resourcePreviewNode.get(SuggestionFields.PREF_LABEL).getTextValue();
				resourcePreview.setPrefLabel(prefLabel);
				String httpUri = resourcePreviewNode.get(SuggestionFields.ID).getTextValue();
				resourcePreview.setHttpUri(httpUri);
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
		// EntityTypes entityType = EntityTypes.getByHttpUri(entityTypeUri);

		EntityTypes entityType = EntityTypes.getByInternalType(entityTypeStr);
		return EntityPreviewObjectFactory.getInstance().createObjectInstance(entityType);
	}

}
