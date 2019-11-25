package eu.europeana.entity.config;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;

public class EntityConfigurationImpl implements EntityConfiguration {

	private Properties entityProperties;
	
	private List<String> solrServerUrls;
	
	@Override
	public String getComponentName() {
		return "entity";
	}


	public Properties getEntityProperties() {
		return entityProperties;
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

	@Override
	public String getAuthorizationApiName() {
	    return getEntityProperties().getProperty(AUTHORIZATION_API_NAME);
	}
	
	@Override
	public List<String> getSolrServeUrls(){
	    if(solrServerUrls == null) {
		String serverList = getEntityProperties().getProperty(SOLR_ENTITY_URL);
		if(StringUtils.isNotBlank(serverList)) {
		    solrServerUrls = Arrays.asList(serverList.split(","));
		}		
	    }
	    return solrServerUrls;
	}
}
