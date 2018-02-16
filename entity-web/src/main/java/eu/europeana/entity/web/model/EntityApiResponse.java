package eu.europeana.entity.web.model;

import eu.europeana.api.commons.web.model.ApiResponse;

public class EntityApiResponse extends ApiResponse {


	public static String ERROR_SUGGESTION_NOT_FOUND = "Suggestion not found!";
	public static String ERROR_ENTITY_NOT_FOUND = "Entity not found!";

	public EntityApiResponse(String apiKey, String action) {
		super(apiKey, action);
	}


}
