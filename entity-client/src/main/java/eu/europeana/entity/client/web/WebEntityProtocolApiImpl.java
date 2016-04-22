package eu.europeana.entity.client.web;

import java.io.IOException;
import java.util.List;

import org.springframework.http.ResponseEntity;

import eu.europeana.entity.client.BaseEntityApi;
import eu.europeana.entity.client.exception.TechnicalRuntimeException;
import eu.europeana.entity.client.model.result.EntitySearchResults;
import eu.europeana.entity.definitions.model.Concept;


public class WebEntityProtocolApiImpl extends BaseEntityApi implements WebEntityProtocolApi {


	@Override
	public ResponseEntity<String> retrieveEntity(
			String apiKey
			, String type
			, String namespace
			, String identifier) {

		ResponseEntity<String> res;
		try {
			res = apiConnection.retrieveEntity(apiKey, type, namespace, identifier);
		} catch (IOException e) {
			throw new TechnicalRuntimeException(
					"Exception occured when invoking the EntityJsonApi retrieveEntity method", e);
		}

		return res;
	}

	@Override
	public List<Concept> getSuggestions(
			String apiKey
			, String text
			, String language
			, String rows
			) {

		EntitySearchResults res;
		try {
			res = apiConnection.getSuggestions(apiKey, text, language, rows);
		} catch (IOException e) {
			throw new TechnicalRuntimeException(
					"Exception occured when invoking the EntityJsonApi getSuggestion method", e);
		}

		return res.getItems();
	}

	
}
