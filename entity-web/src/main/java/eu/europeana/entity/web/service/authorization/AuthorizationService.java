package eu.europeana.entity.web.service.authorization;

import eu.europeana.api.commons.web.exception.ApplicationAuthenticationException;
import eu.europeana.entity.config.EntityConfiguration;
import eu.europeana.entity.definitions.model.Agent;
import eu.europeana.entity.web.exception.authorization.OperationAuthorizationException;
import eu.europeana.entity.web.exception.authorization.UserAuthorizationException;

public interface AuthorizationService extends eu.europeana.api.commons.service.authorization.AuthorizationService{

	/**
	 * use authorizeUser(String userToken, String apiKey, String operationName) 
	 * @param userToken
	 * @param apiKey
	 * @param entityId The entity id string
	 * @param operationName
	 * @return
	 * @throws UserAuthorizationException
	 */
	Agent authorizeUser(String userToken, String apiKey, String entityId, String operationName)
			throws UserAuthorizationException, ApplicationAuthenticationException, OperationAuthorizationException;

	EntityConfiguration getConfiguration();
	
}
