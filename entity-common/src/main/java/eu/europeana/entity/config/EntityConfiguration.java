package eu.europeana.entity.config;

public interface EntityConfiguration {

	public static final String ENTITY_ENVIRONMENT = "entity.environment";
	
	//public static final String SUFFIX_BASEURL = "baseUrl";
	public static final String VALUE_ENVIRONMENT_PRODUCTION = "production";
	public static final String VALUE_ENVIRONMENT_TEST = "test";
	public static final String VALUE_ENVIRONMENT_DEVELOPMENT = "development";

	public static final String KEY_ENTITY_SUGGESTER_SNIPPETS = "entity.suggester.snippets";
	public static final String KEY_APIKEY_JWTTOKEN_SIGNATUREKEY = "europeana.apikey.jwttoken.siganturekey";
	
	public static final String DEFAULT_USER_TOKEN = "default.user.token";

	public static final String AUTHORIZATION_API_NAME = "authorization.api.name";

	public String getComponentName();
	
	
	/**
	 * checks entity.environment=production property
	 */
	public boolean isProductionEnvironment();
	
	/**
	 * uses entity.environment property
	 */
	public String getEnvironment();
	
	public int getSuggesterSnippets();
	
	public String getUserToken();

	public String getJwtTokenSignatureKey();
	
	public String getAuthorizationApiName();
	
}
