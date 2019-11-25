package eu.europeana.entity.web.service.authorization;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.oauth2.provider.ClientDetailsService;

import eu.europeana.api.commons.definitions.vocabulary.Role;
import eu.europeana.api.commons.service.authorization.BaseAuthorizationService;
import eu.europeana.entity.config.EntityConfiguration;
import eu.europeana.entity.web.model.vocabulary.UserRoles;
import eu.europeana.entity.web.service.authentication.AuthenticationService;

public class AuthorizationServiceImpl extends BaseAuthorizationService implements AuthorizationService {

    protected final Logger logger = LogManager.getLogger(getClass());

    @Resource
    AuthenticationService authenticationService;

    @Resource
    EntityConfiguration configuration;

    @Resource(name = "commons_oauth2_europeanaClientDetailsService")
    ClientDetailsService clientDetailsService;

    public AuthorizationServiceImpl(AuthenticationService authenticationService) {
	this.authenticationService = authenticationService;
    }

    public AuthorizationServiceImpl() {

    }

    public AuthenticationService getAuthenticationService() {
	return authenticationService;
    }

    public void setAuthenticationService(AuthenticationService authenticationService) {
	this.authenticationService = authenticationService;
    }

    public EntityConfiguration getConfiguration() {
	return configuration;
    }

    public void setConfiguration(EntityConfiguration configuration) {
	this.configuration = configuration;
    }

    @Override
    protected ClientDetailsService getClientDetailsService() {
	return clientDetailsService;
    }

    @Override
    protected String getSignatureKey() {
	return getConfiguration().getJwtTokenSignatureKey();
    }

    @Override
    protected Role getRoleByName(String name) {
	return UserRoles.getRoleByName(name);
    }

    @Override
    protected String getApiName() {
	return getConfiguration().getAuthorizationApiName();
    }
    
}
