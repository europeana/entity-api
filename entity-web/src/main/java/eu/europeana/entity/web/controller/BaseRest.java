package eu.europeana.entity.web.controller;

import org.apache.commons.lang.StringUtils;

import eu.europeana.entity.config.i18n.I18nConstants;
import eu.europeana.entity.definitions.model.Concept;
import eu.europeana.entity.web.exception.authentication.EntityAuthenticationException;
import eu.europeana.entity.web.model.EntitySearchResults;

public abstract class BaseRest{

	public BaseRest() {
		super();
	}

	protected void validateApiKey(String wsKey) throws EntityAuthenticationException {
		// throws exception if the wskey is not found
		if (StringUtils.isEmpty(wsKey))
			throw new EntityAuthenticationException(null, I18nConstants.EMPTY_APIKEY, new String[]{wsKey});
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