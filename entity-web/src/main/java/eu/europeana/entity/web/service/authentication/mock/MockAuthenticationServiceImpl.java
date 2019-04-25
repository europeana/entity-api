package eu.europeana.entity.web.service.authentication.mock;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;

import eu.europeana.api.common.config.I18nConstants;
import eu.europeana.api.commons.web.exception.ApplicationAuthenticationException;
import eu.europeana.entity.config.EntityConfiguration;
import eu.europeana.entity.definitions.model.Agent;
import eu.europeana.entity.definitions.model.authentication.Application;
import eu.europeana.entity.definitions.model.vocabulary.WebEntityConstants;
import eu.europeana.entity.definitions.model.vocabulary.WebEntityFields;
import eu.europeana.entity.web.exception.authorization.UserAuthorizationException;
import eu.europeana.entity.web.service.authentication.AuthenticationService;
import eu.europeana.entity.web.service.authentication.model.ClientApplicationImpl;


public class MockAuthenticationServiceImpl implements AuthenticationService, ResourceServerTokenServices {

	private static final String COLLECTIONS_API_KEY = "phVKTQ8g9F";
	private static final String COLLECTIONS_USER_TOKEN = "pyU4HCDWfS";

	EntityConfiguration configuration;

	Logger logger = LogManager.getLogger(getClass());

	public MockAuthenticationServiceImpl(EntityConfiguration configuration) {
		this.configuration = configuration;
	}

	public Logger getLogger() {
		return logger;
	}

	private Map<String, Application> cachedClients = new HashMap<String, Application>();

	public Map<String, Application> getCachedClients() {
		return cachedClients;
	}

	@Override
	public Application createMockClientApplication(String apiKey, String applicationName) throws ApplicationAuthenticationException {
		Application app = new ClientApplicationImpl();
		app.setApiKey(apiKey);
		app.setName(applicationName);

//		Agent annonymous = AgentObjectFactory.getInstance().createObjectInstance(AgentTypes.PERSON);
//		annonymous.setName(applicationName + "-" + WebEntityFields.USER_ANONYMOUNS);
//		annonymous.setUserGroup(Roles.ANONYMOUS.name());
//		app.setAnonymousUser(annonymous);
//
//		Agent admin = AgentObjectFactory.getInstance().createObjectInstance(AgentTypes.PERSON);
//		admin.setName(applicationName + "-" + WebEntityFields.USER_ADMIN);
////		if (WebUserSetFields.PROVIDER_EUROPEANA_DEV.equals(applicationName))
//		admin.setUserGroup(Roles.ADMIN.name());
////		else
////			admin.setUserGroup(Roles.USER.name());

//		app.setAdminUser(admin);

		// authenticated users
		createTesterUsers(applicationName, app);

		createRegularUser(apiKey, applicationName, app);

		// put app in the cache
		getCachedClients().put(app.getApiKey(), app);

		return app;
	}

	protected void createRegularUser(String apiKey, String applicationName, Application app) {
		if (!COLLECTIONS_API_KEY.equals(apiKey))
			return;

//		Agent collectionsUser = AgentObjectFactory.getInstance().createObjectInstance(AgentTypes.PERSON);
//		String username = "Europeana Collections Curator";
//		collectionsUser.setName(applicationName + "-" + username);
//		collectionsUser.setHttpUrl(username + "@" + applicationName);
//		collectionsUser.setUserGroup(Roles.USER.name());

//		app.addAuthenticatedUser(COLLECTIONS_USER_TOKEN, collectionsUser);
		app.addAuthenticatedUser(COLLECTIONS_USER_TOKEN, null);
	}

	protected void createTesterUsers(String applicationName, Application app) {

		// testers not allowed in production
		if (getConfiguration().isProductionEnvironment())
			return;

		String username = "tester1";
		Agent tester1 = createTesterUser(username, applicationName);
		app.addAuthenticatedUser(username, tester1);

		username = "tester2";
		Agent tester2 = createTesterUser(username, applicationName);
		app.addAuthenticatedUser(username, tester2);

		username = "tester3";
		Agent tester3 = createTesterUser(username, applicationName);
		app.addAuthenticatedUser(username, tester3);
	}

	protected Agent createTesterUser(String username, String applicationName) {
//		Agent tester1 = AgentObjectFactory.getInstance().createObjectInstance(AgentTypes.PERSON);
//		tester1.setName(applicationName + "-" + username);
//		tester1.setHttpUrl(username + "@" + applicationName);
//		tester1.setUserGroup(Roles.TESTER.name());
		return null; //tester1;
	}

	@Override
	public Agent getUserByToken(String apiKey, String userToken) throws UserAuthorizationException {
		Agent user = null;

		// read user from cache
		try {
			Application clientApp = getByApiKey(apiKey);
			user = getUserByToken(userToken, clientApp);

		} catch (ApplicationAuthenticationException e) {
			throw new UserAuthorizationException(null, I18nConstants.INVALID_TOKEN, new String[]{userToken}, e);
		}

		// unknown user
		if (user == null)
			throw new UserAuthorizationException(null, I18nConstants.INVALID_TOKEN, new String[]{userToken}, HttpStatus.UNAUTHORIZED);

		return user;

	}

	private Agent getUserByToken(String userToken, Application application) {
		Agent user;
		if (WebEntityConstants.USER_ANONYMOUNS.equals(userToken))
			user = application.getAnonymousUser();
		else if (WebEntityFields.USER_ADMIN.equals(userToken))
			user = application.getAdminUser();
		else
			user = application.getAuthenticatedUsers().get(userToken);
		return user;
	}

	@Override
	public Application getByApiKey(String apiKey) throws ApplicationAuthenticationException {

		Application app = null;

		if (getCachedClients().isEmpty()) {
		}

		app = getCachedClients().get(apiKey);

		if (app == null) {
			throw new ApplicationAuthenticationException(null, I18nConstants.INVALID_APIKEY, new String[]{apiKey});
		}

		return app;
	}

	public EntityConfiguration getConfiguration() {
		return configuration;
	}

	public void setConfiguration(EntityConfiguration configuration) {
		this.configuration = configuration;
	}

	@Override
	public OAuth2Authentication loadAuthentication(String accessToken)
			throws AuthenticationException, InvalidTokenException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OAuth2AccessToken readAccessToken(String accessToken) {
		// TODO Auto-generated method stub
		return null;
	}
}
