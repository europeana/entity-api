package eu.europeana.entity.web.controller;

import eu.europeana.entity.utils.jsonld.WebEntityFields;

public interface WebEntityConstants extends WebEntityFields{

	String PARAM_WSKEY = "wskey";
	String PATH_PARAM_TYPE = "type";
	String PATH_PARAM_NAMESPACE = "namespace";
	String PATH_PARAM_IDENTIFIER = "identifier";

	String QUERY_PARAM_TEXT = "text";
	String QUERY_PARAM_LANGUAGE = "language";
	String QUERY_PARAM_TYPE = "type";
	String QUERY_PARAM_NAMESPACE = "namespace";
	String QUERY_PARAM_ROWS = "rows";
	
	String PARAM_ALL = "All";
	String PARAM_LANGUAGE_EN = "en";
	String PARAM_AGENT = "agent";
	String PARAM_PLACE = "place";
	String PARAM_CONCEPT = "concept";
	String PARAM_TIMESPAN = "timespan";
	String PARAM_QUERY = "query";
	String PARAM_DEFAULT_ROWS = "10";

	String TYPE_BASIC_CONTAINER = "BasicContainer";
	
	/**
	 * Solr fields
	 */
	String SOLR_INTERNAL_TYPE = "internal_type";
	
	/**
	 * Model attribute names
	 */
	public static final String AT_CONTEXT = "@context";
//	public static final String CREATOR = "creator";
//	public static final String CREATED = "created";
	public static final String NAME = "name";
//	public static final String GENERATED = "generated";
//	public static final String GENERATOR = "generator";
//	public static final String HOMEPAGE = "homepage";
//	public static final String MOTIVATION = "motivation";
//	public static final String STYLED_BY = "styledBy";
//	public static final String SAME_AS = "sameAs";
//	public static final String EQUIVALENT_TO = "equivalentTo";
//	public static final String STATUS = "status";
//	public static final String CONTENT_TYPE = "contentType";
//	public static final String HTTP_URI = "httpUri";
//	public static final String SOURCE = "source";
//	public static final String SELECTOR = "selector";
//	public static final String STYLE_CLASS = "styleClass";
	public static final String TYPE = "type";
//	public static final String BODY = "body";
	public static final String DC_LANGUAGE = "language";
	public static final String AT_LANGUAGE = "@language";
	
//	public static final String FORMAT = "format";
//	public static final String MEDIA_TYPE = "mediaType";
//	public static final String PAGE = "page";
//	public static final String TARGET = "target";
	public static final String ID = "id";
//	public static final String TYPE = "type";
	public static final String CONTAINS = "contains";
//	public static final String PURPOSE = "purpose";

	public static final String PREF_LABEL = "prefLabel";
	
	public static final String TOP_CONCEPT = "topConcept";
	public static final String BEGIN = "begin";
	public static final String END = "end";
	public static final String GEO_LAT = "lat";
	public static final String GEO_LONG = "long";
	
	public static final String COUNTRY = "country";
	public static final String IS_PART_OF = "isPartOf";
	
	
	/**
	 * Search API response
	 */
	public static final String SEARCH_RESP_FACETS = "facets";
	public static final String SEARCH_RESP_FACETS_FIELD = "field";
	public static final String SEARCH_RESP_FACETS_VALUES = "values";
	public static final String SEARCH_RESP_FACETS_LABEL = "label";
	public static final String SEARCH_RESP_FACETS_COUNT = "count";

//	public static final String WA_CONTEXT = "https://www.w3.org/ns/anno.jsonld";
	public static final String LDP_CONTEXT = "https://www.w3.org/ns/ldp.jsonld";
//	public static final String LDP_CONTEXT = "https://www.w3.org/ns/ldp.jsonld";
	public static final String ENTITY_CONTEXT = "http://www.europeana.eu/schemas/context/entity.jsonld";
	public static final String TOTAL_ITEMS = "totalItems";
	
	/**
	 * PARAMS
	 */
	public static final String PARAM_INCLUDE_ERROR_STACK = "includeErrorStack";
	
}
