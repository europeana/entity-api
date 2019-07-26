package eu.europeana.entity.web.service.authorization;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.provider.ClientDetailsService;

import eu.europeana.api.common.config.I18nConstants;
import eu.europeana.api.commons.service.authorization.BaseAuthorizationService;
import eu.europeana.api.commons.web.exception.ApplicationAuthenticationException;
import eu.europeana.entity.config.EntityConfiguration;
import eu.europeana.entity.definitions.model.Agent;
import eu.europeana.entity.definitions.model.authentication.Application;
import eu.europeana.entity.web.exception.authorization.OperationAuthorizationException;
import eu.europeana.entity.web.exception.authorization.UserAuthorizationException;
import eu.europeana.entity.web.model.vocabulary.Operations;
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

    /*
     * (non-Javadoc)
     * 
     * @see
     * eu.europeana.set.web.service.authorization.AuthorizationService#authorizeUser
     * (java.lang.String, java.lang.String,
     * eu.europeana.set.definitions.model.UserSetId, java.lang.String)
     */
    @Override
    public Agent authorizeUser(String userToken, String apiKey, String setId, String operationName)
	    throws UserAuthorizationException, ApplicationAuthenticationException, OperationAuthorizationException {

	Application app = getAuthenticationService().getByApiKey(apiKey);
	Agent user = getAuthenticationService().getUserByToken(apiKey, userToken);

	// NOTE: actually getUserByToken throws exception when user is not found
//		if (user== null || user.getName() == null || user.getUserGroup() == null)
//			throw new UserAuthorizationException(I18nConstants.INVALID_TOKEN, 
//					I18nConstants.INVALID_TOKEN, new String[]{userToken}, HttpStatus.UNAUTHORIZED);

	if (!isAdmin(user) && !hasPermission(app, setId, operationName))
	    throw new OperationAuthorizationException(I18nConstants.USER_NOT_AUTHORIZED,
		    I18nConstants.USER_NOT_AUTHORIZED, new String[] { "Entity id: " + userToken },
		    HttpStatus.UNAUTHORIZED);

	// check permissions
	if (isAdmin(user) && hasPermission(user, operationName))// allow all
	    return user;
//		else if(isTester(user) && configuration.isProductionEnvironment()){
//			// testers not allowed in production environment
//			throw new UserAuthorizationException(I18nConstants.TEST_USER_FORBIDDEN, 
//					I18nConstants.TEST_USER_FORBIDDEN, new String[]{user.getName()}, HttpStatus.FORBIDDEN);
//		} else	if(hasPermission(user, operationName)){
//			//user is authorized
//			return user;
//		}

	// user is not authorized to perform operation
	throw new UserAuthorizationException(I18nConstants.USER_NOT_AUTHORIZED, I18nConstants.USER_NOT_AUTHORIZED,
		new String[] {}, HttpStatus.UNAUTHORIZED);
//				I18nConstants.USER_NOT_AUTHORIZED, new String[]{user.getName()}, HttpStatus.UNAUTHORIZED);	
    }

    // verify client app privileges
    protected boolean hasPermission(Application app, String setId, String operationName) {
	if (Operations.MODERATION_ALL.equals(operationName) || Operations.RETRIEVE.equals(operationName)
		|| Operations.UPDATE.equals(operationName) || Operations.CREATE.equals(operationName))
	    return true;

	return setId != null;
    }

    // verify user privileges
    protected boolean hasPermission(Agent user, String operationName) {
//		Roles role = Roles.valueOf(user.getUserGroup());
//		
//		for (String operation : role.getOperations()) {
//			if(operation.equalsIgnoreCase(operationName))
	return true;// users is authorized, everything ok
//		}

//		return false;
    }

    protected boolean isAdmin(Agent user) {
	return true; // Roles.ADMIN.name().equals(user.getUserGroup());
    }

    protected boolean isTester(Agent user) {
	return false; // Roles.TESTER.name().equals(user.getUserGroup());
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
    protected String getAuthorizationApiName() {
	return getConfiguration().getAuthorizationApiName();
    }
}
