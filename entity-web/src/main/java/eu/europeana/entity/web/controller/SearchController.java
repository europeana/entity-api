package eu.europeana.entity.web.controller;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import eu.europeana.api.common.config.swagger.SwaggerSelect;
import eu.europeana.api.commons.web.exception.HttpException;
import eu.europeana.api.commons.web.http.HttpHeaders;
import eu.europeana.entity.definitions.exceptions.UnsupportedEntityTypeException;
import eu.europeana.entity.definitions.model.search.result.ResultSet;
import eu.europeana.entity.definitions.model.vocabulary.EntityTypes;
import eu.europeana.entity.definitions.model.vocabulary.WebEntityConstants;
import eu.europeana.entity.web.exception.InternalServerException;
import eu.europeana.entity.web.exception.ParamValidationException;
import eu.europeana.entity.web.jsonld.SuggestionSetSerializer;
import eu.europeana.entity.web.model.view.EntityPreview;
import eu.europeana.entity.web.service.EntityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Controller
@Api(tags = "Discovery API")
@SwaggerSelect
public class SearchController extends BaseRest {

	@Resource
	EntityService entityService;

	@SuppressWarnings("unchecked")
	@ApiOperation(value = "Suggest entitties for the given text query. Suported values for type: Agent, Place, Concept, Timespan, All. Supported values for scope: europeana", nickname = "getSuggestion", response = java.lang.Void.class)
	@RequestMapping(value = { "/entity/suggest", "/entity/suggest.jsonld" }, method = RequestMethod.GET, produces = {
			HttpHeaders.CONTENT_TYPE_JSON_UTF8, HttpHeaders.CONTENT_TYPE_JSONLD_UTF8 })
	public ResponseEntity<String> getSuggestion(@RequestParam(value = WebEntityConstants.PARAM_WSKEY) String wskey,
			@RequestParam(value = WebEntityConstants.QUERY_PARAM_TEXT) String text,
			@RequestParam(value = WebEntityConstants.QUERY_PARAM_LANGUAGE, defaultValue = WebEntityConstants.PARAM_LANGUAGE_EN) String language,
			@RequestParam(value = WebEntityConstants.QUERY_PARAM_SCOPE, required = false) String scope,
			@RequestParam(value = WebEntityConstants.QUERY_PARAM_TYPE, defaultValue = WebEntityConstants.PARAM_ALL) String type,
			// @RequestParam(value = WebEntityConstants.QUERY_PARAM_NAMESPACE,
			// required = false) String namespace,
			@RequestParam(value = WebEntityConstants.QUERY_PARAM_ROWS, defaultValue = WebEntityConstants.PARAM_DEFAULT_ROWS) int rows)
			throws HttpException {

		String action = "get:/entity/suggest";

		try {
			// Check client access (a valid “wskey” must be provided)
			validateApiKey(wskey);

			// validate and convert type
			EntityTypes[] entityTypes = getEntityTypesFromString(type);

			// validate scope parameter
			if (StringUtils.isNotBlank(scope) && !WebEntityConstants.PARAM_EUROPEANA.equalsIgnoreCase(scope))
				throw new ParamValidationException("Invalid request parameter value! ",
						WebEntityConstants.QUERY_PARAM_SCOPE, scope);

			// perform search
			ResultSet<? extends EntityPreview> results = entityService.suggest(text, language, entityTypes, scope, null,
					rows);
			
			// serialize results
			SuggestionSetSerializer serializer = new SuggestionSetSerializer(results);
			String jsonLd = serializer.serialize();

			// build response
			MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>(5);
			headers.add(HttpHeaders.VARY, HttpHeaders.ACCEPT);
			headers.add(HttpHeaders.LINK, HttpHeaders.VALUE_LDP_CONTAINER);
			headers.add(HttpHeaders.ALLOW, HttpHeaders.ALLOW_GET);

			ResponseEntity<String> response = new ResponseEntity<String>(jsonLd, headers, HttpStatus.OK);

			return response;

		} catch (RuntimeException e) {
			// not found ..
			System.out.println(e);
			throw new InternalServerException(e);
		} catch (HttpException e) {
			// avoid wrapping http exception
			throw e;
		} catch (Exception e) {
			throw new InternalServerException(e);
		}

	}

	/**
	 * Get entity type string list from comma separated entities string.
	 * 
	 * @param commaSepEntityTypes
	 *            Comma separated entities string
	 * @return Entity types string list
	 * @throws ParamValidationException
	 */
	public EntityTypes[] getEntityTypesFromString(String commaSepEntityTypes) throws ParamValidationException {

		String[] splittedEntityTypes = commaSepEntityTypes.split(",");
		EntityTypes[] entityTypes = new EntityTypes[splittedEntityTypes.length];

		EntityTypes entityType = null;
		String typeAsString = null;

		try {
			for (int i = 0; i < splittedEntityTypes.length; i++) {
				typeAsString = splittedEntityTypes[i].trim();
				entityType = EntityTypes.getByInternalType(typeAsString);
				entityTypes[i] = entityType;
			}
		} catch (UnsupportedEntityTypeException e) {
			throw new ParamValidationException("Invalid request parameter value! ", WebEntityConstants.QUERY_PARAM_TYPE,
					typeAsString);
		}

		return entityTypes;
	}

}
