package eu.europeana.entity.client.web;

import java.io.IOException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;

import eu.europeana.entity.client.BaseEntityApi;
import eu.europeana.entity.client.exception.TechnicalRuntimeException;
import eu.europeana.entity.client.model.result.EntitySearchResults;
import eu.europeana.entity.definitions.model.Entity;


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
	public Entity retrieveEntityWithUrl(String url) {
		EntitySearchResults res;
		try {
			res = apiConnection.retrieveEntityWithUrl(url);
			if (StringUtils.isNotEmpty(res.getError()))
				throw new TechnicalRuntimeException(
						"Authorisation failed", null);
		} catch (IOException e) {
			throw new TechnicalRuntimeException(
					"Exception occured when invoking the EntityJsonApi retrieveEntityWithUrl method", e);
		}
		List<Entity> entities = res.getItems();
		return (entities == null || entities.size() == 0 ? null : entities.get(0));
	}
	
	@Override
	public List<Entity> getSearch(String apiKey, String text, String language, String type, String sort, String page, String pageSize){
		EntitySearchResults res;
		try {
			res = apiConnection.getSearch(apiKey, text, language, type, sort, page, pageSize);
			if (StringUtils.isNotEmpty(res.getError()))
				throw new TechnicalRuntimeException(
						"Authorisation failed", null);
		} catch (IOException e) {
			throw new TechnicalRuntimeException(
					"Exception occured when invoking the EntityJsonApi getSearch method", e);
		}

		return res.getItems();
	}
	
	@Override
	public List<Entity> getSuggestions(
			String apiKey
			, String text
			, String language
			, String rows
			) {

		EntitySearchResults res;
		try {
			res = apiConnection.getSuggestions(apiKey, text, language, rows);
			if (StringUtils.isNotEmpty(res.getError()))
				throw new TechnicalRuntimeException(
						"Authorisation failed", null);
		} catch (IOException e) {
			throw new TechnicalRuntimeException(
					"Exception occured when invoking the EntityJsonApi getSuggestion method", e);
		}

		return res.getItems();
	}

	@Override
	public List<Entity> resolveEntityByUri(String apiKey, String uri) {
//		public ResponseEntity<String> resolveEntityByUri(String apiKey, String uri) {
		
		EntitySearchResults res;

//		ResponseEntity<String> res;
		try {
			res = apiConnection.resolveEntityByUri(apiKey, uri);
		} catch (IOException e) {
			throw new TechnicalRuntimeException(
					"Exception occured when invoking the EntityJsonApi retrieveEntity method", e);
		}

		return res.getItems();
	}

	
}
