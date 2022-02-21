package eu.europeana.entity.client.integration.web;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import java.util.List;
import org.junit.Test;
import eu.europeana.entity.definitions.model.Entity;

/**
 * This class is testing organization suggestEntity request handler.
 * @author GrafR
 */
public class OrganizationSuggestionTest extends BaseEntityTest { 

	private final String SUGGESTION_TEXT = "bibliotheque";// nationale de france";
	private final String SUGGESTION_LANGUAGE = "en";
	private final String SUGGESTION_ROWS_COUNT = "10";

	@Test
	public void getSuggestion() {
		List<Entity> response = getApiClient().getSuggestions(
				getApiKey(),
				SUGGESTION_TEXT,
				SUGGESTION_LANGUAGE,
				SUGGESTION_ROWS_COUNT);
		
		assertNotNull(response);
		assertTrue(response.size() > 0);
		assertTrue(response.stream().anyMatch(entity ->
				entity.getInternalType().equalsIgnoreCase("organization")));

		// check for english
		assertTrue(response.stream().allMatch(entity ->
				entity.getPrefLabel().get(SUGGESTION_LANGUAGE).stream().anyMatch(value ->
						value.contains("Library"))));
	}
	
}
