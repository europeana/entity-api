package eu.europeana.entity.config;

public interface EntityConfiguration {

	public static final String ENTITY_ENVIRONMENT = "entity.environment";
	
	//public static final String SUFFIX_BASEURL = "baseUrl";
	public static final String VALUE_ENVIRONMENT_PRODUCTION = "production";
	public static final String VALUE_ENVIRONMENT_TEST = "test";
	public static final String VALUE_ENVIRONMENT_DEVELOPMENT = "development";
	public static final String SOCKS_PROXY_URL = "socks.proxy.url";
	
	public String getComponentName();
	
	
	/**
	 * checks annotation.environment=production property
	 */
	public boolean isProductionEnvironment();
	
	/**
	 * uses annotation.environment property
	 */
	public String getEnvironment();
	
	
//	/**
//	 * uses annotation.environment.{$environment}.baseUrl property
//	 */
//	public String getAnnotationBaseUrl();
	
	
}
