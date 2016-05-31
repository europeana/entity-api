package eu.europeana.entity.solr.service.impl;

import java.util.Date;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.annotate.JsonAutoDetect.Visibility;
import org.codehaus.jackson.annotate.JsonMethod;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;

import eu.europeana.entity.solr.exception.EntitySuggestionException;
import eu.europeana.entity.solr.model.vocabulary.SuggestionFields;
import eu.europeana.entity.web.model.view.EntityPreview;

public class SuggestionUtils {

	private final Logger log = Logger.getLogger(getClass());
	
	protected static ObjectMapper objectMapper = new ObjectMapper().setVisibility(JsonMethod.FIELD, Visibility.ANY)
			.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    protected static final JsonFactory jsonFactory = new JsonFactory();


	public Logger getLog() {
		return log;
	}

	public void parsePayload(EntityPreview preview, String payload) throws EntitySuggestionException {
		
		try {
			JsonParser parser = jsonFactory.createJsonParser(payload);
			parser.setCodec(objectMapper);
			JsonNode payloadNode = objectMapper.readTree(payload);
			
			JsonNode propertyNode = payloadNode.get(SuggestionFields.ENTITY_ID);
			preview.setEntityId(propertyNode.getTextValue());
			
			propertyNode = payloadNode.get(SuggestionFields.TERM);
			if(propertyNode != null)
				preview.setMatchedTerm(propertyNode.getTextValue());
			
			propertyNode = payloadNode.get(SuggestionFields.PREF_LABEL);
			preview.setPreferredLabel(propertyNode.getTextValue());
			
			propertyNode = payloadNode.get(SuggestionFields.TYPE);
			preview.setType(propertyNode.getTextValue());
			
			propertyNode = payloadNode.get(SuggestionFields.TIME_SPAN_START);
			if(propertyNode != null)
				preview.setTimeSpanStart(propertyNode.getTextValue());
			
			propertyNode = payloadNode.get(SuggestionFields.TIME_SPAN_END);
			if(propertyNode != null)
				preview.setTimeSpanEnd(propertyNode.getTextValue());
			
			propertyNode = payloadNode.get(SuggestionFields.BIRTH_DATE);
			if(propertyNode != null)
				preview.setBirthDate(new Date(propertyNode.getLongValue()));
			
			propertyNode = payloadNode.get(SuggestionFields.DEATH_DATE);
			if(propertyNode != null)
				preview.setDeathDate(new Date(propertyNode.getLongValue()));
			
			propertyNode = payloadNode.get(SuggestionFields.ROLE);
			if(propertyNode != null)
				preview.setRole(propertyNode.getTextValue());
			
			propertyNode = payloadNode.get(SuggestionFields.LATITUDE);
			if(propertyNode != null)
				preview.setLatitude(propertyNode.getTextValue());
			
			propertyNode = payloadNode.get(SuggestionFields.LONGITUDE);
			if(propertyNode != null)
				preview.setLongitude(propertyNode.getTextValue());
			
			
		} catch (Exception e) {
			throw new EntitySuggestionException("Cannot parse suggestion payload: " + payload, e);
		}		
   		
	}
	
	
}
