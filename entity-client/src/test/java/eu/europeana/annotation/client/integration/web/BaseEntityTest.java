package eu.europeana.annotation.client.integration.web;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.apache.log4j.Logger;
import org.apache.stanbol.commons.exception.JsonParseException;
import org.junit.Before;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import eu.europeana.entity.client.config.ClientConfiguration;
import eu.europeana.entity.client.web.WebEntityProtocolApi;
import eu.europeana.entity.client.web.WebEntityProtocolApiImpl;

public class BaseEntityTest {

	protected Logger log = Logger.getLogger(getClass());

	public String TEST_WSKEY = "apidemo";

	private WebEntityProtocolApi apiClient;

	
	@Before
	public void initObjects() {
		apiClient = new WebEntityProtocolApiImpl();
	}

	public WebEntityProtocolApi getApiClient() {
		return apiClient;
	}

	
	/**
	 * This method retrieves test entity object
	 * 
	 * @param apiKey
	 * @param type
	 * @param namespace
	 * @param identifier
	 * @return response entity that contains response body, headers and status code.
	 */
	protected ResponseEntity<String> retrieveEntity(
			String apiKey
			, String type
			, String namespace
			, String identifier) {

		/**
		 * get entity
		 */
		ResponseEntity<String> storedResponse = getApiClient().retrieveEntity(
				apiKey, type, namespace, identifier);
		return storedResponse;
	}

	
	/**
	 * This method performs a lookup for the entity in all 4 datasets
	 * 
	 * @param apiKey
	 * @param uri
	 * @return response entity that contains response body, headers and status code.
	 */
	protected ResponseEntity<String> resolveEntity(
			String apiKey
			, String uri) {

		ResponseEntity<String> storedResponse = getApiClient().resolveEntityByUri(
				apiKey, uri);
		return storedResponse;
	}

	
	public String getApiKey() {

		return ClientConfiguration.getInstance().getApiKey();
		// return TEST_WSKEY;
	}

	protected void validateResponse(ResponseEntity<String> response) throws JsonParseException {
		validateResponse(response, HttpStatus.CREATED);
	}

	protected void validateResponse(ResponseEntity<String> response, HttpStatus status) throws JsonParseException {
		assertEquals(response.getStatusCode(), status);
		assertNotNull(response.getBody());		
	}

}
