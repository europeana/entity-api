package eu.europeana.entity.web.service.authentication.mock;

import javax.annotation.Resource;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import eu.europeana.api.commons.oauth2.service.impl.BridgeAuthenticationManager;
import eu.europeana.api.commons.web.exception.ApplicationAuthenticationException;
import eu.europeana.entity.config.EntityConfiguration;
import eu.europeana.entity.definitions.model.Agent;
import eu.europeana.entity.definitions.model.authentication.Application;
import eu.europeana.entity.web.exception.authorization.UserAuthorizationException;
import eu.europeana.entity.web.service.authentication.AuthenticationService;


/**
 * This class implements an adapter for the existing authorization service (MockAuthenticationServiceImpl), in order to implement the OAuth2 interfaces.
 * This service reuses the apiKey as the clientId, and the userToken as accessToken (and refreshToken)
 * @author GrafR
 *
 */
public class BridgeAuthenticationManagerAdapter extends BridgeAuthenticationManager 
	implements AuthenticationService { 

	MockAuthenticationServiceImpl mockOauthService;
	
	@Resource
	EntityConfiguration configuration;
	
	public EntityConfiguration getConfiguration() {
		return configuration;
	}

	public void setConfiguration(EntityConfiguration configuration) {
		this.configuration = configuration;
	}
	
	public BridgeAuthenticationManagerAdapter(){
		super();
		initService();
	}
	
	public void initService(){
		setTokenServices((MockAuthenticationServiceImpl)getMockOauthService());
	}

	@Override
	public Agent getUserByToken(String apiKey, String userToken) throws UserAuthorizationException {
		return getMockOauthService().getUserByToken(apiKey, userToken);
	}

	@Override
	public Application getByApiKey(String apiKey) throws ApplicationAuthenticationException {
		return getMockOauthService().getByApiKey(apiKey);
	}

	public AuthenticationService getMockOauthService() {
		//avoid NPE at bean initialization
		if(mockOauthService == null){
			mockOauthService = new MockAuthenticationServiceImpl(getConfiguration());
		}
		
		//ensure proper initialization of mockOauthService
		if(mockOauthService.getConfiguration() == null) {
			mockOauthService.setConfiguration(getConfiguration());
		}
			
		return mockOauthService;
	}

	public void setMockOauthService(MockAuthenticationServiceImpl mockOauthService) {
		this.mockOauthService = mockOauthService;
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		return authentication;
	}

	@Override
	public Application createMockClientApplication(String apiKey, String applicationName)
			throws ApplicationAuthenticationException {
		return mockOauthService.createMockClientApplication(apiKey, applicationName);
	}
	
}
