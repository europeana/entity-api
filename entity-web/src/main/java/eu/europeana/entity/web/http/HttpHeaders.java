package eu.europeana.entity.web.http;

import org.springframework.http.MediaType;

/**
 * TODO move to api-common
 * @author GordeaS
 *
 */
@Deprecated
public interface HttpHeaders extends javax.ws.rs.core.HttpHeaders{

	/**
	 * see {@link <a href="http://www.w3.org/wiki/LinkHeader">W3C Link Header documentation</a>}.
	 * 
	 */
	public static final String LINK = "Link";
	public static final String ALLOW = "Allow";
	
	public static final String ALLOW_POST = "POST";
	public static final String ALLOW_GET = "GET";
	public static final String ALLOW_GPuD = "GET,PUT,DELETE";
	
	public static final String CONTENT_TYPE_JSON_UTF8 = MediaType.APPLICATION_JSON_VALUE+";charset=utf-8";
	public static final String CONTENT_TYPE_JSONLD_UTF8 = "application/ld+json;charset=utf-8";
	
	public static final String VALUE_LDP_RESOURCE = "<http://www.w3.org/ns/ldp#Resource>; rel=\"type\"";
	public static final String VALUE_LDP_CONTAINER = "<http://www.w3.org/ns/ldp#Resource>; rel=\"type\"\n"+
			"<http://www.w3.org/TR/annotation-protocol/constraints>;\n" +
			"rel=\"http://www.w3.org/ns/ldp#constrainedBy\"";
	
}
