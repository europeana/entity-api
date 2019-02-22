package eu.europeana.entity.client.connection;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.http.ResponseEntity;

import eu.europeana.entity.client.config.ClientConfiguration;
import eu.europeana.entity.client.model.result.EntitySearchResults;
import eu.europeana.entity.definitions.model.Entity;
import eu.europeana.entity.definitions.model.impl.BaseEntity;
import eu.europeana.entity.definitions.model.vocabulary.WebEntityConstants;
import eu.europeana.entity.definitions.model.vocabulary.WebEntityFields;


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
	public EntityApiConnection(String entityServiceUri, String apiKey) {
		super(entityServiceUri, apiKey);
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
				
		String url = getEntityServiceUri() + WebEntityConstants.SLASH;
		url += type + WebEntityConstants.SLASH;
		url += namespace + WebEntityConstants.SLASH;
    	url += identifier;
		url += WebEntityConstants.PAR_CHAR;
		url += "wskey" + WebEntityConstants.EQUALS + apiKey;
		
		/**
		 * Execute Europeana API request
		 */
		return getURL(url);		
	}

	/**
	 * This method retrieves Entity object from REST interface.
	 * Example HTTP request for tag object: 
	 *     http://localhost:8080/entity/concept/base/10?wskey=apidemo
	 * @param url
	 * @return entity operation response
	 * @throws IOException
	 */
	public EntitySearchResults retrieveEntityWithUrl(String url)  throws IOException {
		/**
		 * Execute Europeana API request
		 */
		String json = getJSONResult(url);
		
		return getEntityResolveResults(json);		
	}

	/**
	 * This method returns a list of Entity objects for the passed query.
     * E.g. http://entity-api-test.eanadev.org/entity/search?wskey=apidemo&query=label%3AGermany&lang=all&type=Place&sort=derived_score%2Bdesc&page=0&pageSize=10
	 * @param apiKey
     * @param query The query string
	 * @param language
	 * @param type
	 * @param sort
	 * @param page
	 * @param pageSize
	 * @return entity operation response
	 * @throws IOException
	 */
	public EntitySearchResults getSearch (
			String apiKey
			, String query
			, String language
			, String type
			, String sort
			, String page
			, String pageSize
			) throws IOException {
		
		String url = buildSearchUrl(apiKey, query, language, type, sort, page, pageSize);
		
		/**
		 * Execute Europeana API request
		 */
		String json = getJSONResult(url);
		
		return getEntitySearchResults(language, json);
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
		
		return getEntitySearchResults(language, json);
	}

	/**
	 * This method converts json response in EntitySearchResults.
	 * @param json
	 * @return EntitySearchResults
	 */
	@Deprecated //- please implement proper response parsing using Jackson
	public EntitySearchResults getEntitySearchResults(String language, String json) {
		EntitySearchResults asr = new EntitySearchResults();
		asr.setSuccess("true");
		asr.setAction("create:/entity/suggest");
		try {
        	JSONObject jsonListObj = new JSONObject(json);
        	if (json.contains("Unauthorized")) {
        		asr.setError(json);
        	} else {
        		if(jsonListObj.has(WebEntityConstants.ITEMS)) {
		        	JSONArray jsonArray = jsonListObj.getJSONArray(WebEntityConstants.ITEMS);
		        	if(jsonArray!=null && jsonArray.length()>0){
				        List<Entity> entityList = new ArrayList<Entity>();
		                for (int i = 0; i < jsonArray.length(); i++) {
		                	JSONObject jsonObj = jsonArray.getJSONObject(i);
		                	BaseEntity entityObject = new BaseEntity();
				        	entityObject.setEntityId(jsonObj.getString(WebEntityFields.ID));
	//			        	Map<String, List<String>> prefLabelMap = new HashMap<String, List<String>>();
				        	Map<String, String> prefLabelMap = new HashMap<String, String>();
				        	String label = jsonObj.getString((WebEntityFields.PREF_LABEL));
	//						prefLabelMap.put(language, Arrays.asList(label));
							prefLabelMap.put(language, label);
				        	entityObject.setPrefLabelStringMap(prefLabelMap);
							entityList.add(entityObject);
					    }
					    asr.setItems(entityList);
					}
        		}
        	}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return asr;
	}

	
	/**
	 * This method converts json response in EntitySearchResults.
	 * @param json
	 * @return EntitySearchResults
	 */
	//TODO: implement reliable response parsing using jackson
	public EntitySearchResults getEntityResolveResults(String json) {
		EntitySearchResults asr = new EntitySearchResults();
		asr.setSuccess("true");
		asr.setAction("create:/entity/resolve");
		try {
        	JSONObject jsonObj = new JSONObject(json);
        	if (json.contains("Unauthorized")) {
        		asr.setError(json);
        	} else {
//	        	JSONArray jsonArray = jsonListObj.getJSONArray(("contains"));
//	        	if(jsonArray!=null && jsonArray.length()>0){
			        List<Entity> entityList = new ArrayList<Entity>();
//	                for (int i = 0; i < jsonArray.length(); i++) {
//	                	JSONObject jsonObj = jsonArray.getJSONObject(i);
	                	BaseEntity entityObject = new BaseEntity();
			        	entityObject.setEntityId(jsonObj.getString(WebEntityFields.ID));
//			        	entityObject.setEntityId(jsonObj.getString("@id"));
			        	entityObject.setDefinition(""+jsonObj.getJSONObject(WebEntityFields.PREF_LABEL));
						entityList.add(entityObject);
//				    }
				    asr.setItems(entityList);
//				}
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
	
	/**
	 * This method constructs url dependent on search parameter.
	 * @param apiKey
	 * @param query
	 * @param language
	 * @param type
	 * @param sort
	 * @param page
	 * @param pageSize
	 * @return query
	 */
	private String buildSearchUrl(String apiKey, String query, String language, String type, String sort, String page, String pageSize) {		
		
		StringBuilder builder = new StringBuilder();
		builder.append(getEntityServiceUri());
//		String wskey = apiKey;
//		if (StringUtils.isEmpty(apiKey))
//			wskey = getApiKey();
		builder.append("/search?wskey=").append(apiKey);
		if (StringUtils.isNotEmpty(query)) {
			builder.append("&query=").append(query);
		}
		if (StringUtils.isNotEmpty(language))
			builder.append("&language=").append(language);
		else
			builder.append("&language=en");
		if (StringUtils.isNotEmpty(type))
			builder.append("&type=").append(type);
		else
			builder.append("&type=All");
		if (StringUtils.isNotEmpty(sort))
			builder.append("&sort=").append(sort);
		if (StringUtils.isNotEmpty(page))
			builder.append("&page=").append(page);
		else
			builder.append("&page=0");
		if (StringUtils.isNotEmpty(pageSize))
			builder.append("&pageSize=").append(pageSize);
		else
			builder.append("&pageSize=10");
				
		return builder.toString();		
	}


	/**
	 * This method performs a lookup for the entity in all 4 datasets using REST interface.
	 * Example HTTP request for resolution URI: 
	 *     /entity/resolve?uri=http://dbpedia.org/resource/Charles_Dickens?wskey=apidemo
	 * @param apiKey
	 * @param uri
	 * @return response entity that comprises response body, headers and status code.
	 * @throws IOException
	 */
	public EntitySearchResults resolveEntityByUri (
//			public ResponseEntity<String> resolveEntityByUri (
				String apiKey
				, String uri
				)  throws IOException {
				
		StringBuilder builder = new StringBuilder();
		builder.append(getEntityServiceUri());
		builder.append("/resolve?wskey=").append(apiKey);
		if (StringUtils.isNotEmpty(uri)) {
			builder.append("&uri=").append(uri);
		}				
		String queryUrl = builder.toString();		

		/**
		 * Execute API request
		 */
//		return getURL(queryUrl);		
		String json = getJSONResult(queryUrl);
		
		return getEntityResolveResults(json);
	}

	
}
