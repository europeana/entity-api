package eu.europeana.entity.client.web;

import java.util.List;

import org.springframework.http.ResponseEntity;

import eu.europeana.entity.definitions.model.Concept;

public interface WebEntityProtocolApi {

	/**
	 * This method retrieves entity describing it in body JSON string and
	 * providing it with associated wskey, type, namespace and identifier.
	 * 
	 * @param apiKey
	 * @param type
	 * @param namespace
	 * @param identifier
	 * @return response entity containing body, headers and status code.
	 */
	public ResponseEntity<String> retrieveEntity(String apiKey, String type, String namespace, String identifier);

	/**
	 * This method returns entity suggestions depending on given text and
	 * language.
	 * 
	 * @param apiKey
	 * @param text
	 * @param language
	 * @param rows
	 * @return response entity containing body, headers and status code.
	 */
	public List<Concept> getSuggestions(String apiKey, String text, String language, String rows);

}
