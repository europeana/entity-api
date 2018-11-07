package eu.europeana.entity.client.integration.web;

import java.lang.reflect.InvocationTargetException;

import org.apache.stanbol.commons.exception.JsonParseException;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import eu.europeana.entity.definitions.model.vocabulary.EntityTypes;


/**
 * This class is testing the retrieval of organization.
 * @author GrafR
 */
public class OrganizationRetrievalTest extends BaseEntityTest { 


	private final String TEST_ENTITY_TYPE = EntityTypes.Organization.getInternalType();
	private final String TEST_ENTITY_NAMESPACE = "%22%22";
	private final String TEST_ENTITY_ID = "1482250000002112001";

	
	@Test
	public void getOrganizationEntity() throws JsonParseException, IllegalAccessException, 
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
