package eu.europeana.entity.web.controller;

public interface WebEntityFields {

	String PARAM_WSKEY = "wskey";
	String PATH_PARAM_TYPE = "type";
	String PATH_PARAM_NAMESPACE = "namespace";
	String PATH_PARAM_IDENTIFIER = "identifier";

	String QUERY_PARAM_TEXT = "text";
	String QUERY_PARAM_LANGUAGE = "language";
	String QUERY_PARAM_TYPE = "type";
	String QUERY_PARAM_NAMESPACE = "namespace";
	String QUERY_PARAM_ROWS = "rows";
	
	String PARAM_ALL = "all";
	String PARAM_LANGUAGE_EN = "en";
	String PARAM_AGENTS = "agents";
	String PARAM_PLACES = "places";
	String PARAM_CONCEPTS = "concepts";
	String PARAM_TIMESPANS = "timespans";
	String PARAM_QUERY = "query";
	String PARAM_DEFAULT_ROWS = "10";

	/**
	 * Solr fields
	 */
	String SOLR_INTERNAL_TYPE = "internal_type";
	
}
