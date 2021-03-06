package eu.europeana.entity.client.integration.web;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.apache.stanbol.commons.exception.JsonParseException;
import org.junit.Test;


/**
 * This class is testing suggesting text syntax. Since the suggester is expected to match 
 * against word characters, we need to pre-process the search query to remove all non-word characters
 * @author GrafR
 */
public class SuggestionSyntaxTest extends BaseEntityTest { 
	
	private final String SUGGESTION_TEXT_APOSTROPHE = "wh\"";
	private final String SUGGESTION_TEXT_BRACE = "(painter";
	private final String SUGGESTION_TEXT_HYPHEN = "v1-2";
	private final String SUGGESTION_TEXT_REGULAR = "moz1";
	private final String SUGGESTION_LANGUAGE = "en";
	private final String SUGGESTION_ROWS_COUNT = "10";
	private final String SUGGESTION_SCOPE = "europeana";
	private final String SUGGESTION_ALGORITHM = "monolingual";
	private final String SUGGESTION_TYPES = "agent,concept";
					
	@Test
	public void getSuggestionWithApostropheInText() throws JsonParseException {
				
        	/**
        	 * get suggestion by text and language
        	 */
        	String response = getApiClient().getSuggestionsExt(
        			getApiKey()
        			, SUGGESTION_TEXT_APOSTROPHE
        			, SUGGESTION_LANGUAGE
        			, SUGGESTION_ROWS_COUNT
        			, SUGGESTION_SCOPE
        			, SUGGESTION_ALGORITHM
        			, SUGGESTION_TYPES
        			);		
        	assertNotNull(response);
        	assertTrue(response.equals("true"));
	}		

	@Test
	public void getSuggestionWithBraceInText() throws JsonParseException {
				
        	/**
        	 * get suggestion by text and language
        	 */
        	String response = getApiClient().getSuggestionsExt(
        			getApiKey()
        			, SUGGESTION_TEXT_BRACE
        			, SUGGESTION_LANGUAGE
        			, SUGGESTION_ROWS_COUNT
        			, SUGGESTION_SCOPE
        			, SUGGESTION_ALGORITHM
        			, SUGGESTION_TYPES
        			);		
        	assertNotNull(response);
        	assertTrue(response.equals("true"));
	}		

	@Test
	public void getSuggestionWithHyphenInText() throws JsonParseException {
				
        	/**
        	 * get suggestion by text and language
        	 */
        	String response = getApiClient().getSuggestionsExt(
        			getApiKey()
        			, SUGGESTION_TEXT_HYPHEN
        			, SUGGESTION_LANGUAGE
        			, SUGGESTION_ROWS_COUNT
        			, SUGGESTION_SCOPE
        			, SUGGESTION_ALGORITHM
        			, SUGGESTION_TYPES
        			);		
        	assertNotNull(response);
        	assertTrue(response.equals("true"));
	}		

	@Test
	public void getSuggestionWithoutSpecialCharactersInText() throws JsonParseException {
				
        	/**
        	 * get suggestion by text and language
        	 */
        	String response = getApiClient().getSuggestionsExt(
        			getApiKey()
        			, SUGGESTION_TEXT_REGULAR
        			, SUGGESTION_LANGUAGE
        			, SUGGESTION_ROWS_COUNT
        			, SUGGESTION_SCOPE
        			, SUGGESTION_ALGORITHM
        			, SUGGESTION_TYPES
        			);		
        	assertNotNull(response);
        	assertTrue(response.equals("true"));
	}		

}
