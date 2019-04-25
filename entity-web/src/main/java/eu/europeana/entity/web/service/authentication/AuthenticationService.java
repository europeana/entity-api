package eu.europeana.entity.web.service.authentication;

import eu.europeana.api.commons.web.exception.ApplicationAuthenticationException;
import eu.europeana.entity.definitions.model.Agent;
import eu.europeana.entity.definitions.model.authentication.Application;
import eu.europeana.entity.web.exception.authorization.UserAuthorizationException;

public interface AuthenticationService {

	public Application createMockClientApplication(String apiKey, String applicationName) throws ApplicationAuthenticationException;
	
	public Agent getUserByToken(String apiKey, String userToken) throws UserAuthorizationException;

	public Application getByApiKey(String apiKey) throws ApplicationAuthenticationException;
	
}
