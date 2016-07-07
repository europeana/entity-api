package eu.europeana.annotation.client.integration.web;

import java.lang.reflect.InvocationTargetException;

import org.apache.stanbol.commons.exception.JsonParseException;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import eu.europeana.entity.definitions.model.vocabulary.EntityTypes;


/**
 * This class aims at testing of the entity methods.
 * @author GrafR
 */
public class EntityRetrievalTest extends BaseEntityTest { 


	private final String TEST_ENTITY_TYPE = EntityTypes.Agent.getInternalType();
	private final String TEST_ENTITY_NAMESPACE = "base";
	private final String TEST_ENTITY_ID = "33041";

	
	@Test
	public void getEntity() throws JsonParseException, IllegalAccessException, 
									IllegalArgumentException, InvocationTargetException {
		
		/**
		 * get entity by type, namespace and identifier
		 */
		ResponseEntity<String> response = retrieveEntity(
				getApiKey()
				, TEST_ENTITY_TYPE
				, TEST_ENTITY_NAMESPACE
				, TEST_ENTITY_ID
				);
		
		validateResponse(response, HttpStatus.OK);
	}
	
	
}
