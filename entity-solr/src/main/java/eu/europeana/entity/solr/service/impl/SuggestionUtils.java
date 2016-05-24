package eu.europeana.entity.solr.service.impl;

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
			JsonNode node = objectMapper.readTree(payload);
			JsonNode entityIdNode = node.get(SuggestionFields.ENTITY_ID);
			
			preview.setEntityId(entityIdNode.getTextValue());
			
		} catch (Exception e) {
			throw new EntitySuggestionException("Cannot parse suggestion payload: " + payload, e);
		}		
   
		
	}
	
	
}
