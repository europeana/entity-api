package eu.europeana.entity.web.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;

import eu.europeana.api.common.config.I18nConstants;
import eu.europeana.entity.definitions.model.Concept;
import eu.europeana.entity.web.exception.authentication.EntityAuthenticationException;
import eu.europeana.entity.web.model.EntitySearchResults;

public abstract class BaseRest{

	public BaseRest() {
		super();
	}

	protected void validateApiKey(String wsKey) throws EntityAuthenticationException {
		// throws exception if the wskey is not found
		if (wsKey == null)
			throw new EntityAuthenticationException(I18nConstants.MISSING_APIKEY, I18nConstants.MISSING_APIKEY, null, HttpStatus.UNAUTHORIZED);
		if (StringUtils.isEmpty(wsKey))
			throw new EntityAuthenticationException(null, I18nConstants.EMPTY_APIKEY, null);
		if (!wsKey.equals("apidemo"))
			throw new EntityAuthenticationException(null, I18nConstants.INVALID_APIKEY,  new String[]{wsKey});
	}

	public EntitySearchResults<Concept> buildSearchErrorResponse(String apiKey, String action,
			Throwable th) {

		EntitySearchResults<Concept> response = new EntitySearchResults<Concept>(apiKey,
				action);
		response.success = false;
		response.error = th.getMessage();
		// response.requestNumber = 0L;

		return response;
	}

	
}