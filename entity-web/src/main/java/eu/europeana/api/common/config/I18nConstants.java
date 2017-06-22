package eu.europeana.api.common.config;

public interface I18nConstants {
	
	// shared error messages
	static final String INVALID_APIKEY = "error.entity_invalid_apikey";

	//401
	static final String NO_APPLICATION_FOR_APIKEY = "error.entity_no_application_for_apikey";
	static final String EMPTY_APIKEY = "error.entity_empty_apikey";
	static final String APIKEY_FILE_NOT_FOUND = "error.entity_apikey_file_not_found";
	
	//404
	static final String URI_NOT_FOUND = "error.entity_uri_not_found";
	static final String CANT_FIND_BY_SAME_AS_URI = "error.entity_same_as_not_found";
	static final String UNSUPPORTED_ENTITY_TYPE = "erorr.entity_unsupported_type";
	
	//500
	static final String SERVER_ERROR_CANT_RETRIEVE_URI = "error.entity_server_cannot_retrieve_uri";
	static final String SERVER_ERROR_CANT_RESOLVE_SAME_AS_URI = "error.entity_server_cannot_resolve_uri";
	static final String SERVER_ERROR_UNEXPECTED = "error.entity_server_unexpected_error";
	
}
