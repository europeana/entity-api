package eu.europeana.entity.client.integration.web;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.apache.stanbol.commons.exception.JsonParseException;
import org.junit.Test;

import eu.europeana.entity.definitions.model.Entity;


/**
 * This class is testing organization suggestEntity request handler.
 * @author GrafR
 */
public class OrganizationSuggestionTest extends BaseEntityTest { 


	
	private final String SUGGESTION_TEXT = "bibliotheque";// nationale de france";
	private final String SUGGESTION_LANGUAGE = "";
	private final String SUGGESTION_ROWS_COUNT = "10";
	private final String TEST_ENTITY_ID = "http://data.europeana.eu/organization/base/1482250000002063029";

					
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

		boolean isEntityFound = false;
		for (Entity entity : response) {
			if (TEST_ENTITY_ID.equals(entity.getEntityId()))
				isEntityFound = true;
		}
		
		assertTrue(isEntityFound);
	}
	
	
}
