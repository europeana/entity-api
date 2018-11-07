package eu.europeana.entity.web.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.core.JsonProcessingException;

import eu.europeana.api.common.config.swagger.SwaggerSelect;
import eu.europeana.api.commons.config.i18n.I18nConstants;
import eu.europeana.api.commons.definitions.search.Query;
import eu.europeana.api.commons.definitions.search.ResultSet;
import eu.europeana.api.commons.definitions.search.result.ResultsPage;
import eu.europeana.api.commons.definitions.vocabulary.CommonApiConstants;
import eu.europeana.api.commons.web.exception.HttpException;
import eu.europeana.api.commons.web.http.HttpHeaders;
import eu.europeana.entity.definitions.model.Entity;
import eu.europeana.entity.definitions.model.search.SearchProfiles;
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

	@ApiOperation(value = "Suggest entities for the given text query. Suported values for type: Agent, Place, Concept, Timespan, All. Supported values for scope: europeana", nickname = "getSuggestion", response = java.lang.Void.class)
	@RequestMapping(value = { "/entity/suggest", "/entity/suggest.jsonld" }, method = RequestMethod.GET, produces = {
			HttpHeaders.CONTENT_TYPE_JSON_UTF8, HttpHeaders.CONTENT_TYPE_JSONLD_UTF8 })
	public ResponseEntity<String> getSuggestion(
			@RequestParam(value = CommonApiConstants.PARAM_WSKEY, required = false) String wskey,
			@RequestParam(value = CommonApiConstants.QUERY_PARAM_TEXT) String text,
			@RequestParam(value = CommonApiConstants.QUERY_PARAM_LANGUAGE, defaultValue = WebEntityConstants.PARAM_LANGUAGE_EN) String language,
			@RequestParam(value = WebEntityConstants.QUERY_PARAM_SCOPE, required = false) String scope,
			@RequestParam(value = WebEntityConstants.QUERY_PARAM_TYPE, defaultValue = WebEntityConstants.PARAM_TYPE_ALL) String type,
			@RequestParam(value = CommonApiConstants.QUERY_PARAM_ROWS, defaultValue = WebEntityConstants.PARAM_DEFAULT_ROWS) int rows)
			throws HttpException {

		try {
			// Check client access (a valid “wskey” must be provided)
			validateApiKey(wskey);

			// validate and convert type
			EntityTypes[] entityTypes = getEntityTypesFromString(type);

			// validate scope parameter
			validateScopeParam(scope);
			
			//past parguage list
			String[] requestedLanguages = toArray(language);
			
			// perform search
			ResultSet<? extends EntityPreview> results = entityService.suggest(text, requestedLanguages, entityTypes, scope, null,
					rows);

			// serialize results
			SuggestionSetSerializer serializer = new SuggestionSetSerializer(results);
			String jsonLd = serializer.serialize();

			// build response
			MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>(5);
			// removed in #EA-763 and specifications
			// //headers.add(HttpHeaders.VARY, HttpHeaders.ACCEPT);
			headers.add(HttpHeaders.ALLOW, HttpHeaders.ALLOW_GET);

			ResponseEntity<String> response = new ResponseEntity<String>(jsonLd, headers, HttpStatus.OK);

			return response;

		} catch (HttpException e) {
			// avoid wrapping http exception
			throw e;
		} catch (RuntimeException e) {
			// not found ..
			// System.out.println(e);
			throw new InternalServerException(e);
		} catch (Exception e) {
			throw new InternalServerException(e);
		}
	}

	@ApiOperation(value = "Search entitties for the given text query.", nickname = "search", response = java.lang.Void.class)
	@RequestMapping(value = { "/entity/search", "/entity/search.jsonld" }, method = RequestMethod.GET, produces = {
			HttpHeaders.CONTENT_TYPE_JSON_UTF8, HttpHeaders.CONTENT_TYPE_JSONLD_UTF8 })
	public ResponseEntity<String> search(
			@RequestParam(value = CommonApiConstants.PARAM_WSKEY, required = false) String wskey,
			@RequestParam(value = CommonApiConstants.QUERY_PARAM_QUERY) String queryString,
			@RequestParam(value = CommonApiConstants.QUERY_PARAM_QF, required = false) String[] qf,
			@RequestParam(value = WebEntityConstants.QUERY_PARAM_FL, required = false) String fl,			
			@RequestParam(value = CommonApiConstants.QUERY_PARAM_FACET, required = false) String facet,
			@RequestParam(value = CommonApiConstants.QUERY_PARAM_LANG, required = false) String outLanguage,
			@RequestParam(value = WebEntityConstants.QUERY_PARAM_TYPE, required = false, defaultValue = WebEntityConstants.PARAM_TYPE_ALL) String type,
			@RequestParam(value = WebEntityConstants.QUERY_PARAM_SCOPE, required = false) String scope,
			@RequestParam(value = CommonApiConstants.QUERY_PARAM_SORT, required = false) String sort,
			@RequestParam(value = CommonApiConstants.QUERY_PARAM_PAGE, required = false, defaultValue = "0") int page,
			@RequestParam(value = CommonApiConstants.QUERY_PARAM_PAGE_SIZE, required = false, defaultValue = ""
					+ Query.DEFAULT_PAGE_SIZE) int pageSize,
			@RequestParam(value = CommonApiConstants.QUERY_PARAM_PROFILE, required = false) String profile,
			HttpServletRequest request) throws HttpException {

		try {
			// Check client access (a valid “wskey” must be provided)
			validateApiKey(wskey);

			// ** Process input params
			if (StringUtils.isBlank(queryString))
				throw new ParamValidationException(I18nConstants.EMPTY_PARAM_MANDATORY,
						CommonApiConstants.QUERY_PARAM_QUERY, queryString);

			//process scope
			scope = validateScopeParam(scope);
			
			//process type
			EntityTypes[] entityTypes = getEntityTypesFromString(type);

			//process lang 
			String[] preferredLanguages = null;
			if(outLanguage != null && !outLanguage.contains(WebEntityConstants.PARAM_LANGUAGE_ALL))
				preferredLanguages = toArray(outLanguage);
			
			//process profile
			SearchProfiles searchProfile = null;
			if(profile != null){
				if(!SearchProfiles.contains(profile))
					throw new ParamValidationException(WebEntityConstants.QUERY_PARAM_TYPE, profile);
				else
					searchProfile = SearchProfiles.valueOf(profile.toLowerCase());
			} 
			
			//process fl
			String[] retFields = toArray(fl);
			
			//process facet
			String[] facets = toArray(facet);
			
			//process sort param
			String[] sortCriteria = toArray(sort);
			
			// perform search
			Query searchQuery = entityService.buildSearchQuery(queryString, qf, facets, sortCriteria, page, pageSize, searchProfile, retFields);
			ResultSet<? extends Entity> results = entityService.search(searchQuery, preferredLanguages, entityTypes, scope);

			ResultsPage<? extends Entity> resPage = entityService.buildResultsPage(searchQuery, results, request.getRequestURL(),
					request.getQueryString());
			String jsonLd = searializeResultsPage(resPage, searchProfile);

			// build response
			MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>(5);
			// removed in #EA-763 and specifications
			// //headers.add(HttpHeaders.VARY, HttpHeaders.ACCEPT);
			headers.add(HttpHeaders.ALLOW, HttpHeaders.ALLOW_GET);

			ResponseEntity<String> response = new ResponseEntity<String>(jsonLd, headers, HttpStatus.OK);

			return response;

		} catch (HttpException e) {
			// avoid wrapping http exception
			throw e;
		} catch (JsonProcessingException e) {
			// not found ..
			// System.out.println(e);
			throw new InternalServerException(e);
		} catch (RuntimeException e) {
			// not found ..
			// System.out.println(e);
			throw new InternalServerException(e);
		} 
	}
	
	
}
