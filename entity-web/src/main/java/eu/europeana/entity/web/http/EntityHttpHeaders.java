package eu.europeana.entity.web.http;

import eu.europeana.api.commons.web.http.HttpHeaders;

public interface EntityHttpHeaders extends HttpHeaders {

	/**
	 * Authorization
	 */
	public static final String BEARER = "Bearer";
	
	/**
	 * Response headers
	 */
	public static final String PREFERENCE_APPLIED = "Preference-Applied";	
	public static final String ALLOW_GPD = "GET,PUT,DELETE";
	public static final String ALLOW_GPPD = "GET,POST,PUT,DELETE";	
}
