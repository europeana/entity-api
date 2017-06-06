package eu.europeana.entity.client.web;

import java.util.List;

import org.springframework.http.ResponseEntity;

import eu.europeana.api.commons.definitions.model.Entity;


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
	public List<Entity> getSuggestions(String apiKey, String text, String language, String rows);

	
	/**
	 * Performs a lookup for the entity in all 4 datasets:
	 * 
	 *    agents, places, concepts and time spans 
	 * 
	 * using an alternative uri for an entity (lookup will happen within the owl:sameAs properties).
	 * 
	 * @param uri
	 * @return
	 * @throws HttpException
	 */
	public List<Entity> resolveEntityByUri(String apiKey, String uri);
//	public ResponseEntity<String> resolveEntityByUri(String apiKey, String uri);
	
}
