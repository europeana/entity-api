package eu.europeana.entity.web.controller;

import org.apache.commons.lang.StringUtils;

import eu.europeana.entity.definitions.model.Concept;
import eu.europeana.entity.web.controller.exception.GlobalExceptionHandling;
import eu.europeana.entity.web.exception.authentication.EntityAuthenticationException;
import eu.europeana.entity.web.model.EntitySearchResults;

public class BaseRest{

	public BaseRest() {
		super();
	}

	protected void validateApiKey(String wsKey) throws EntityAuthenticationException {
		// throws exception if the wskey is not found
		if (StringUtils.isEmpty(wsKey))
			throw new EntityAuthenticationException(EntityAuthenticationException.MESSAGE_EMPTY_APIKEY,
					wsKey);
		if (!wsKey.equals("apidemo"))
			throw new EntityAuthenticationException(EntityAuthenticationException.MESSAGE_INVALID_APIKEY,
					wsKey);
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