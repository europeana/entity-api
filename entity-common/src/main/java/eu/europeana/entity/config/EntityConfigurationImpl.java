package eu.europeana.entity.config;

import java.util.Properties;

public class EntityConfigurationImpl implements EntityConfiguration{

	private Properties entityProperties;
	
	@Override
	public String getComponentName() {
		return "entity";
	}

//	@Override
//	public boolean isIndexingEnabled() {
//		String value = getAnnotationProperties().getProperty(ENTITY_INDEXING_ENABLED);
//		return Boolean.valueOf(value);
//	}

	public Properties getAnnotationProperties() {
		return entityProperties;
	}

	public void setAnnotationProperties(Properties annotationProperties) {
		this.entityProperties = annotationProperties;
	}

	@Override
	public boolean isProductionEnvironment() {
		return VALUE_ENVIRONMENT_PRODUCTION.equals(getEnvironment());
	}

	@Override
	public String getEnvironment() {
		return getAnnotationProperties().getProperty(ENTITY_ENVIRONMENT);
	}

}
