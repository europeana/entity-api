package eu.europeana.annotation.client.integration.web;

import org.junit.Test;

import eu.europeana.entity.client.exception.TechnicalRuntimeException;


/**
 * This class aims at testing of the entity methods.
 * @author GrafR
 */
public class AuthenticationErrorTest extends BaseEntityTest { 


	
	private final String SUGGESTION_TEXT = "marcus";
	private final String SUGGESTION_LANGUAGE = "";
	private final String SUGGESTION_ROWS_COUNT = "10";
	
	private final String WRONG_WSKEY = "api";

					
	@Test(expected = TechnicalRuntimeException.class)
	public void getSuggestionWithWrongWskey() throws TechnicalRuntimeException {
				
		/**
		 * get suggestion with wrong wskey
		 */
		getApiClient().getSuggestions(
				WRONG_WSKEY
				, SUGGESTION_TEXT
				, SUGGESTION_LANGUAGE
				, SUGGESTION_ROWS_COUNT
				);
	}
	
	
	@Test(expected = TechnicalRuntimeException.class)
	public void getSuggestionWithEmptyWskey() throws TechnicalRuntimeException {
				
		/**
		 * get suggestion with empty wskey
		 */
		getApiClient().getSuggestions(
				""
				, SUGGESTION_TEXT
				, SUGGESTION_LANGUAGE
				, SUGGESTION_ROWS_COUNT
				);
	}
	
	
	@Test(expected = TechnicalRuntimeException.class)
	public void getSuggestionWithoutWskey() throws TechnicalRuntimeException {
				
		/**
		 * get suggestion without wskey
		 */
		getApiClient().getSuggestions(
				null
				, SUGGESTION_TEXT
				, SUGGESTION_LANGUAGE
				, SUGGESTION_ROWS_COUNT
				);
	}
	
	
}
