package eu.europeana.entity.client.integration.web;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import eu.europeana.entity.definitions.model.Entity;
import org.junit.Test;

import java.util.List;


/**
 * This class is testing suggesting text syntax. Since the suggester is expected to match 
 * against word characters, we need to pre-process the search query to remove all non-word characters
 * @author GrafR
 */
public class SuggestionSyntaxTest extends BaseEntityTest { 
	
	private final String SUGGESTION_TEXT_APOSTROPHE = "vermeer\'";
	private final String SUGGESTION_TEXT_BRACE = "(painter";
	private final String SUGGESTION_TEXT_HYPHEN = "paris-b";
	private final String SUGGESTION_TEXT_REGULAR = "test";
	private final String SUGGESTION_LANGUAGE = "en";
	private final String SUGGESTION_ROWS_COUNT = "10";
	private final String SUGGESTION_SCOPE = "europeana";
	private final String SUGGESTION_ALGORITHM = "monolingual";
	private final String SUGGESTION_TYPES = "agent,concept";
					
	@Test
	public void getSuggestionWithApostropheInText() {
		List<Entity> entityList= getApiClient().getSuggestionsExt(
				getApiKey(),
				SUGGESTION_TEXT_APOSTROPHE,
				SUGGESTION_LANGUAGE,
				SUGGESTION_ROWS_COUNT,
				SUGGESTION_SCOPE,
				SUGGESTION_ALGORITHM,
				SUGGESTION_TYPES);
		checkResults(entityList);
	}

	@Test
	public void getSuggestionWithBraceInText() {
		List<Entity> entityList = getApiClient().getSuggestionsExt(
				getApiKey(),
				SUGGESTION_TEXT_BRACE,
				SUGGESTION_LANGUAGE,
				SUGGESTION_ROWS_COUNT,
				SUGGESTION_SCOPE,
				SUGGESTION_ALGORITHM,
				SUGGESTION_TYPES);

		checkResults(entityList);
	}

	@Test
	public void getSuggestionWithHyphenInText() {
		List<Entity> entityList = getApiClient().getSuggestionsExt(
				getApiKey(),
				SUGGESTION_TEXT_HYPHEN,
				SUGGESTION_LANGUAGE,
				SUGGESTION_ROWS_COUNT,
				SUGGESTION_SCOPE,
				SUGGESTION_ALGORITHM,
				SUGGESTION_TYPES);

		checkResults(entityList);
	}		

	@Test
	public void getSuggestionWithoutSpecialCharactersInText() {
		List<Entity> entityList = getApiClient().getSuggestionsExt(
				getApiKey(),
				SUGGESTION_TEXT_REGULAR,
				SUGGESTION_LANGUAGE,
				SUGGESTION_ROWS_COUNT,
				SUGGESTION_SCOPE,
				SUGGESTION_ALGORITHM,
				SUGGESTION_TYPES);

		checkResults(entityList);
	}

	private void checkResults(List<Entity> entityList){
		assertNotNull(entityList);
		assertTrue(entityList.size() > 0);
		assertTrue(entityList.stream().anyMatch(entity ->
				entity.getInternalType().equalsIgnoreCase("agent") || entity.getInternalType().equalsIgnoreCase("concept")));
	}
}
