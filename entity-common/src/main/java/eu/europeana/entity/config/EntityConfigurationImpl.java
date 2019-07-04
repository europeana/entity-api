package eu.europeana.entity.config;

import java.util.Properties;

public class EntityConfigurationImpl implements EntityConfiguration {

	private Properties entityProperties;
	
	@Override
	public String getComponentName() {
		return "entity";
	}


	public Properties getEntityProperties() {
		return entityProperties;
	}

	public void setAnnotationProperties(Properties entityProperties) {
		this.entityProperties = entityProperties;
	}

	public void setEntityPropertiesConfig(Properties entityProperties) {
		this.entityProperties = entityProperties;
	}

	@Override
	public boolean isProductionEnvironment() {
		return VALUE_ENVIRONMENT_PRODUCTION.equals(getEnvironment());
	}

	@Override
	public String getEnvironment() {
		return getEntityProperties().getProperty(ENTITY_ENVIRONMENT);
	}


	@Override
	public int getSuggesterSnippets() {
		return Integer.parseInt(getEntityProperties().getProperty(KEY_ENTITY_SUGGESTER_SNIPPETS));
	}

	@Override
	public String getUserToken() {
		return getEntityProperties().getProperty(DEFAULT_USER_TOKEN);
	}


	@Override
	public String getJwtTokenSignatureKey() {
	    return getEntityProperties().getProperty(KEY_APIKEY_JWTTOKEN_SIGNATUREKEY);
	}
}
