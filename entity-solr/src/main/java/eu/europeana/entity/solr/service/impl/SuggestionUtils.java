package eu.europeana.entity.solr.service.impl;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.annotate.JsonAutoDetect.Visibility;
import org.codehaus.jackson.annotate.JsonMethod;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;

import eu.europeana.entity.definitions.model.vocabulary.EntityTypes;
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

	public EntityPreview parsePayload(String payload) throws EntitySuggestionException {
		
		EntityPreview preview = null;
		try {
			JsonParser parser = jsonFactory.createJsonParser(payload);
			parser.setCodec(objectMapper);
			JsonNode payloadNode = objectMapper.readTree(payload);
			
			JsonNode propertyNode = payloadNode.get(SuggestionFields.TYPE);
			String entityType = propertyNode.getTextValue();
			preview = createPreviewObjectInstance(entityType); 
			preview.setType(propertyNode.getTextValue());
			
			propertyNode = payloadNode.get(SuggestionFields.ID);
			preview.setEntityId(propertyNode.getTextValue());
			
			propertyNode = payloadNode.get(SuggestionFields.TERM);
			if(propertyNode != null)
				preview.setMatchedTerm(propertyNode.getTextValue());
			
			propertyNode = payloadNode.get(SuggestionFields.PREF_LABEL);
			preview.setPreferredLabel(propertyNode.getTextValue());
			
			setEntitySpecificProperties(preview, payloadNode);
			
			
		} catch (Exception e) {
			throw new EntitySuggestionException("Cannot parse suggestion payload: " + payload, e);
		}		
   		return preview;
	}

	private void setEntitySpecificProperties(EntityPreview preview, JsonNode payloadNode) {
		switch (preview.getEntityType()){
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
		if(propertyNode != null)
			preview.setInscheme(propertyNode.getTextValue());
		
		
	}

	private void putAgentSpecificProperties(AgentPreview preview, JsonNode payloadNode) {
		
		JsonNode propertyNode = payloadNode.get(SuggestionFields.BIRTH_DATE);
		if(propertyNode != null)
			preview.setDateOfBirth(propertyNode.getTextValue());
		
		propertyNode = payloadNode.get(SuggestionFields.DEATH_DATE);
		if(propertyNode != null)
			preview.setDateOfDeath(propertyNode.getTextValue());
		
		propertyNode = payloadNode.get(SuggestionFields.ROLE);
		if(propertyNode != null)
			preview.setProfessionOrOccuation(propertyNode.getTextValue());
		
	}

	private void putPlaceSpecificProperties(PlacePreview preview, JsonNode payloadNode) {
		JsonNode propertyNode = payloadNode.get(SuggestionFields.COUNTRY);
		if(propertyNode != null)
			preview.setCountry(propertyNode.getTextValue());
		
		propertyNode = payloadNode.get(SuggestionFields.IS_PART_OF);
		if(propertyNode != null)
			preview.setIsPartOf(new String[]{payloadNode.getTextValue()});
		
	}
	
	private void putTimespanSpecificProperties(TimeSpanPreview preview, JsonNode payloadNode) {
		JsonNode propertyNode = payloadNode.get(SuggestionFields.TIME_SPAN_START);
		
		if(propertyNode != null)
			preview.setBegin(propertyNode.getTextValue());
		
		propertyNode = payloadNode.get(SuggestionFields.TIME_SPAN_END);
		if(propertyNode != null)
			preview.setEnd(propertyNode.getTextValue());
		
	}

	private EntityPreview createPreviewObjectInstance(String entityTypeStr) {
		//EntityTypes entityType = EntityTypes.getByHttpUri(entityTypeUri);
		
		EntityTypes entityType = EntityTypes.getByInternalType(entityTypeStr);
		return EntityPreviewObjectFactory.getInstance().createObjectInstance(
				entityType);		
	}
	

}
