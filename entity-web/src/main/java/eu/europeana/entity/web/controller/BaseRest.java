package eu.europeana.entity.web.controller;

import org.apache.commons.lang.StringUtils;

import eu.europeana.entity.web.exception.authentication.EntityAuthenticationException;

public class BaseRest {

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

}