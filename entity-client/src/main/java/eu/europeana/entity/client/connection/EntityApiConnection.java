package eu.europeana.entity.client.connection;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.http.ResponseEntity;

import eu.europeana.entity.client.config.ClientConfiguration;
import eu.europeana.entity.client.model.result.EntitySearchResults;
import eu.europeana.entity.definitions.model.Concept;
import eu.europeana.entity.definitions.model.Entity;
import eu.europeana.entity.definitions.model.impl.BaseEntity;
import eu.europeana.entity.definitions.vocabulary.WebEntityFields;


/**
 * @author GrafR
 *
 */
public class EntityApiConnection extends BaseApiConnection {

	/**
	 * Create a new connection to the Entity Service (REST API).
	 * 
	 * @param apiKey
	 *            API Key required to access the API
	 */
	public EntityApiConnection(String annotationServiceUri, String apiKey) {
		super(annotationServiceUri, apiKey);
	}

	public EntityApiConnection() {
		this(ClientConfiguration.getInstance().getServiceUri(),
				ClientConfiguration.getInstance().getApiKey());
	}
	

	/**
	 * This method retrieves Entity object from REST interface.
	 * Example HTTP request for tag object: 
	 *     http://localhost:8080/entity/concept/base/10?wskey=apidemo
	 * @param apiKey
	 * @param type
	 * @param namespace
	 * @param identifier
	 * @return response entity that comprises response body, headers and status code.
	 * @throws IOException
	 */
	public ResponseEntity<String> retrieveEntity (
				String apiKey
				, String type
				, String namespace
				, String identifier
				)  throws IOException {
				
		String url = getEntityServiceUri() + WebEntityFields.SLASH;
		url += type + WebEntityFields.SLASH;
		url += namespace + WebEntityFields.SLASH;
    	url += identifier;
		url += WebEntityFields.PAR_CHAR;
		url += WebEntityFields.PARAM_WSKEY + WebEntityFields.EQUALS + apiKey;
		
		/**
		 * Execute Europeana API request
		 */
		return getURL(url);		
	}

	
	/**
	 * This method returns a list of Entity objects for the passed query.
     * E.g. http://localhost:8080/entity/suggest?wskey=apidemo&text=ro&language=en&rows=10
	 * @param apiKey
     * @param query The query string
	 * @param language
	 * @param rows
	 * @return entity operation response
	 * @throws IOException
	 */
	public EntitySearchResults getSuggestions (
			String apiKey
			, String query
			, String language
			, String rows
			) throws IOException {
		
		String url = buildUrl(apiKey, query, language, rows);
		
		/**
		 * Execute Europeana API request
		 */
		String json = getJSONResult(url);
		
		return getEntitySearchResults(json);
	}

	/**
	 * This method converts json response in EntitySearchResults.
	 * @param json
	 * @return EntitySearchResults
	 */
	public EntitySearchResults getEntitySearchResults(String json) {
		EntitySearchResults asr = new EntitySearchResults();
		asr.setSuccess("true");
		asr.setAction("create:/entity/suggest");
		try {
        	JSONObject jsonListObj = new JSONObject(json);
        	if (json.contains("Unauthorized")) {
        		asr.setError(json);
        	} else {
	        	JSONArray jsonArray = jsonListObj.getJSONArray(("contains"));
	        	if(jsonArray!=null && jsonArray.length()>0){
			        List<Entity> entityList = new ArrayList<Entity>();
	                for (int i = 0; i < jsonArray.length(); i++) {
	                	JSONObject jsonObj = jsonArray.getJSONObject(i);
	                	BaseEntity entityObject = new BaseEntity();
			        	entityObject.setEntityId(jsonObj.getString("@id"));
			        	entityObject.setDefinition(jsonObj.getString("prefLabel"));
						entityList.add(entityObject);
				    }
				    asr.setItems(entityList);
				}
        	}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return asr;
	}

	/**
	 * This method constructs url dependent on search parameter.
	 * @param apiKey
	 * @param query
	 * @param language
	 * @param rows
	 * @return query
	 */
	private String buildUrl(String apiKey, String query, String language, String rows) {		
		
		StringBuilder builder = new StringBuilder();
		builder.append(getEntityServiceUri());
//		String wskey = apiKey;
//		if (StringUtils.isEmpty(apiKey))
//			wskey = getApiKey();
		builder.append("/suggest?wskey=").append(apiKey);
		if (StringUtils.isNotEmpty(query)) {
			builder.append("&text=").append(query);
		}
		if (StringUtils.isNotEmpty(language))
			builder.append("&language=").append(language);
		else
			builder.append("&language=en");
		if (StringUtils.isNotEmpty(rows))
			builder.append("&rows=").append(rows);
		else
			builder.append("&rows=10");
				
		return builder.toString();		
	}


}
