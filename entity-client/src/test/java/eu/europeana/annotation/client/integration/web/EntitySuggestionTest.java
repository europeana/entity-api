package eu.europeana.annotation.client.integration.web;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.apache.stanbol.commons.exception.JsonParseException;
import org.junit.Test;

import eu.europeana.api.commons.definitions.model.Entity;


/**
 * This class aims at testing of the entity methods.
 * @author GrafR
 */
public class EntitySuggestionTest extends BaseEntityTest { 


	
	private final String SUGGESTION_TEXT = "marcus";
	private final String SUGGESTION_LANGUAGE = "";
	private final String SUGGESTION_ROWS_COUNT = "10";

					
	@Test
	public void getSuggestion() throws JsonParseException {
				
		/**
		 * get suggestion by text and language
		 */
		List<Entity> response = getApiClient().getSuggestions(
				getApiKey()
				, SUGGESTION_TEXT
				, SUGGESTION_LANGUAGE
				, SUGGESTION_ROWS_COUNT
				);
		
		assertNotNull(response);
		assertTrue(response.size() > 0);
	}
	
	
}
