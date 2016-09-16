package eu.europeana.entity.client.connection;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.log4j.Logger;
import org.springframework.http.ResponseEntity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import eu.europeana.corelib.definitions.edm.entity.Concept;
import eu.europeana.entity.client.http.HttpConnection;
import eu.europeana.entity.client.model.json.EntityDeserializer;


public class BaseApiConnection {

	private String apiKey;
	private String entityServiceUri = "";
	private HttpConnection httpConnection = new HttpConnection();
	Logger logger = Logger.getLogger(getClass().getName());

	private Gson gson;

	public String getApiKey() {
		return apiKey;
	}

	public String getAdminApiKey() {
		return "apiadmin";
	}
	
	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	public String getEntityServiceUri() {
		return entityServiceUri;
	}

	public void setEntityServiceUri(String entityServiceUri) {
		this.entityServiceUri = entityServiceUri;
	}

	public HttpConnection getHttpConnection() {
		return httpConnection;
	}

	public void setHttpConnection(HttpConnection httpConnection) {
		this.httpConnection = httpConnection;
	}
	
	public Gson getEntityGson() {
		if (gson == null) {
			// Load results object from JSON
			GsonBuilder builder = new GsonBuilder();
			EntityDeserializer annoDeserializer = new EntityDeserializer();
			
			builder.registerTypeHierarchyAdapter(Concept.class,
					annoDeserializer);
			
//			gson = builder.setDateFormat(ModelConst.GSON_DATE_FORMAT).create();
			gson = builder.create();
		}
		return gson;
	}

	/**
	 * Create a new connection to the Entity Service (REST API).
	 * 
	 * @param apiKey
	 *            API Key required to access the API
	 */
	public BaseApiConnection(String entityServiceUri, String apiKey) {
		this.apiKey = apiKey;
		this.entityServiceUri = entityServiceUri;
	}

	
	String getJSONResult(String url) throws IOException {
		logger.trace("Call to Entity API (GET): " + url);
		return getHttpConnection().getURLContent(url);
	}
	
	String getJSONResult(String url, String paramName, String jsonPost) throws IOException {
		logger.trace("Call to Entity API (POST): " + url);
		return getHttpConnection().getURLContent(url, paramName, jsonPost);
	}
	
	String getJSONResultWithBody(String url, String jsonPost) throws IOException {
		logger.trace("Call to Entity API (POST) with body: " + url);
		return getHttpConnection().getURLContentWithBody(url, jsonPost);
	}
	
	
	/**
	 * This method makes POST request for given URL and JSON body parameter that returns
	 * response body, response headers and status code.
	 * @param url
	 * @param jsonPost
	 * @return The response body, response headers and status code.
	 * @throws IOException
	 */
	ResponseEntity<String> postURL(String url, String jsonPost) throws IOException {
		logger.trace("Call to Entity API (POST) with body: " + url + 
				". Returns body, headers and status code.");
		//System.out.println("post: " + url);
		return getHttpConnection().postURL(url, jsonPost);
	}
	
	
	/**
	 * This method makes PUT request for given URL and JSON body parameter that returns
	 * response body, response headers and status code.
	 * @param url
	 * @param jsonPost
	 * @return The response body, response headers and status code.
	 * @throws IOException
	 */
	ResponseEntity<String> putURL(String url, String jsonPut) throws IOException {
		logger.trace("Call to Entity API (PUT) with body: " + url + 
				". Returns body, headers and status code.");
		
		ResponseEntity<String> response = getHttpConnection().putURL(url, jsonPut);
		
		response.getStatusCode();
		
		return response;
	}
	

	/**
	 * This method makes GET request for given URL and returns
	 * response body, response headers and status code.
	 * @param url
	 * @return The response body, response headers and status code.
	 * @throws IOException
	 */
	public ResponseEntity<String> getURL(String url) throws IOException {
		logger.trace("Call to Entity API (GET): " + url + 
				". Returns body, headers and status code.");
		return getHttpConnection().getURL(url);
	}
	
	
	/**
	 * This method makes DELETE request for given URL that returns
	 * response headers and status code.
	 * @param url
	 * @return The response headers and status code.
	 * @throws IOException
	 */
	ResponseEntity<String> deleteURL(String url) throws IOException {
		logger.trace("Call to Entity API (DELETE): " + url + 
				". Returns headers and status code.");
		return getHttpConnection().deleteURL(url);
	}
	

	/**
	 * This method encodes URLs for HTTP connection
	 * @param url The input URL
	 * @return encoded URL
	 * @throws UnsupportedEncodingException
	 */
	String encodeUrl(String url) throws UnsupportedEncodingException {
		return URLEncoder.encode(url,"UTF-8");
	}
}
