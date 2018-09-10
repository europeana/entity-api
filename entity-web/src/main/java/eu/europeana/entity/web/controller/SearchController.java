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
import eu.europeana.api.commons.definitions.search.result.impl.ResultsPageImpl;
import eu.europeana.api.commons.definitions.vocabulary.CommonApiConstants;
import eu.europeana.api.commons.definitions.vocabulary.CommonLdConstants;
import eu.europeana.api.commons.definitions.vocabulary.ContextTypes;
import eu.europeana.api.commons.search.util.QueryBuilder;
import eu.europeana.api.commons.utils.ResultsPageSerializer;
import eu.europeana.api.commons.web.exception.HttpException;
import eu.europeana.api.commons.web.http.HttpHeaders;
import eu.europeana.entity.definitions.exceptions.UnsupportedEntityTypeException;
import eu.europeana.entity.definitions.model.Entity;
import eu.europeana.entity.definitions.model.search.SearchProfiles;
import eu.europeana.entity.definitions.model.vocabulary.EntityTypes;
import eu.europeana.entity.definitions.model.vocabulary.WebEntityConstants;
import eu.europeana.entity.web.exception.InternalServerException;
import eu.europeana.entity.web.exception.ParamValidationException;
import eu.europeana.entity.web.jsonld.EntityResultsPageSerializer;
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
			String[] requestedLanguages = getLanguageList(language);
			
			//TODO:use buildSearchQuery method to build the query object at this stage already
			
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
			@RequestParam(value = WebEntityConstants.QUERY_PARAM_FL, required = false) String[] retFields,			
			@RequestParam(value = CommonApiConstants.QUERY_PARAM_FACET, required = false) String[] facets,
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

			// validate scope parameter
			scope = validateScopeParam(scope);
			
			// validate and convert type
			EntityTypes[] entityTypes = getEntityTypesFromString(type);

			//get language list
			String[] preferredLanguages = null;
			if(outLanguage != null && !outLanguage.contains(WebEntityConstants.PARAM_LANGUAGE_ALL))
				preferredLanguages = getLanguageList(outLanguage);
			
			SearchProfiles searchProfile = null;
			if(profile != null){
				if(!SearchProfiles.contains(profile))
					throw new ParamValidationException(WebEntityConstants.QUERY_PARAM_TYPE, profile);
				else
					searchProfile = SearchProfiles.valueOf(profile.toLowerCase());
			} 
			
			// perform search
			Query searchQuery = buildSearchQuery(queryString, qf, facets, sort, page, pageSize, searchProfile, retFields);

			ResultSet<? extends Entity> results = entityService.search(searchQuery, preferredLanguages, entityTypes, scope);
			ResultsPage<? extends Entity> resPage = buildResultsPage(searchQuery, results, request.getRequestURL(),
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
		} catch (RuntimeException e) {
			// not found ..
			// System.out.println(e);
			throw new InternalServerException(e);
		} catch (JsonProcessingException e) {
			// not found ..
			// System.out.println(e);
			throw new InternalServerException(e);
		} catch (Exception e) {
			throw new InternalServerException(e);
		}
	}

	private String validateScopeParam(String scope) throws ParamValidationException {
		if (StringUtils.isBlank(scope))
			return null;
				
		if (!WebEntityConstants.PARAM_SCOPE_EUROPEANA.equalsIgnoreCase(scope))
			throw new ParamValidationException(I18nConstants.INVALID_PARAM_VALUE,
					WebEntityConstants.QUERY_PARAM_SCOPE, scope);
		
		return WebEntityConstants.PARAM_SCOPE_EUROPEANA;
	}

	private String searializeResultsPage(ResultsPage<? extends Entity> resPage, SearchProfiles profile)
			throws JsonProcessingException {
		ResultsPageSerializer<? extends Entity> serializer = new EntityResultsPageSerializer<>(resPage,
				ContextTypes.ENTITY.getJsonValue(), CommonLdConstants.COLLECTION);
		String profileVal = (profile == null)? null : profile.name(); 
		return serializer.serialize(profileVal);
	}

	// TODO move to helper class
	private <T extends Entity> ResultsPage<T> buildResultsPage(Query searchQuery, ResultSet<T> results,
			StringBuffer requestUrl, String reqParams) {
		@SuppressWarnings({ "rawtypes", "unchecked" })
		ResultsPage<T> resPage = new ResultsPageImpl();

		resPage.setItems(results.getResults());
		resPage.setFacetFields(results.getFacetFields());

		resPage.setTotalInPage(results.getResults().size());
		resPage.setTotalInCollection(results.getResultSize());

		String collectionUrl = buildCollectionUrl(searchQuery, requestUrl, reqParams);
		resPage.setCollectionUri(collectionUrl);

		int currentPage = searchQuery.getPageNr();
		String currentPageUrl = buildPageUrl(collectionUrl, currentPage, searchQuery.getPageSize());
		resPage.setCurrentPageUri(currentPageUrl);

		if (currentPage > 0) {
			String prevPage = buildPageUrl(collectionUrl, currentPage - 1, searchQuery.getPageSize());
			resPage.setPrevPageUri(prevPage);
		}

		// if current page is not the last one
		boolean isLastPage = resPage.getTotalInCollection() <= (currentPage + 1) * searchQuery.getPageSize();
		if (!isLastPage) {
			String nextPage = buildPageUrl(collectionUrl, currentPage + 1, searchQuery.getPageSize());
			resPage.setNextPageUri(nextPage);
		}

		return resPage;
	}

	private String buildPageUrl(String collectionUrl, int page, int pageSize) {
		StringBuilder builder = new StringBuilder(collectionUrl);
		builder.append("&").append(CommonApiConstants.QUERY_PARAM_PAGE).append("=").append(page);

		builder.append("&").append(CommonApiConstants.QUERY_PARAM_PAGE_SIZE).append("=").append(pageSize);

		return builder.toString();
	}

	private String buildCollectionUrl(Query searchQuery, StringBuffer requestUrl, String queryString) {

		// queryString = removeParam(WebAnnotationFields.PARAM_WSKEY,
		// queryString);

		// remove out of scope parameters
		queryString = removeParam(CommonApiConstants.QUERY_PARAM_PAGE, queryString);
		queryString = removeParam(CommonApiConstants.QUERY_PARAM_PAGE_SIZE, queryString);

		// avoid duplication of query parameters
		queryString = removeParam(CommonApiConstants.QUERY_PARAM_PROFILE, queryString);

		// add mandatory parameters
		if (StringUtils.isNotBlank(searchQuery.getSearchProfile())) {
			queryString += ("&" + CommonApiConstants.QUERY_PARAM_PROFILE + "=" + searchQuery.getSearchProfile());
		}

		return requestUrl.append("?").append(queryString).toString();
	}

	protected String removeParam(final String queryParam, String queryParams) {
		String tmp;
		// avoid name conflicts search "queryParam="
		int startPos = queryParams.indexOf(queryParam + "=");
		int startEndPos = queryParams.indexOf("&", startPos + 1);

		if (startPos >= 0) {
			// make sure to remove the "&" if not the first param
			if (startPos > 0)
				startPos--;
			tmp = queryParams.substring(0, startPos);

			if (startEndPos > 0)
				tmp += queryParams.substring(startEndPos);
		} else {
			tmp = queryParams;
		}
		return tmp;
	}

	/**
	 * @param queryString
	 * @param qf
	 * @param facets
	 * @param sort
	 * @param page
	 * @param pageSize
	 * @param profile
	 * @param retFields
	 * @return
	 */
	private Query buildSearchQuery(String queryString, String[] qf, String[] facets, String sort, int page,
			int pageSize, SearchProfiles profile, String[] retFields) {
		String sortField = null;
		String sortOrder = null;
		if (StringUtils.isNotBlank(sort)) {
			String[] sorting = StringUtils.split(sort, '+');
			sortField = sorting[0];
			if (sorting.length > 1)
				sortOrder = sorting[1];
		}

		QueryBuilder builder = new QueryBuilder();
		int maxPageSize = Query.DEFAULT_MAX_PAGE_SIZE;
		String profileName = null;
		if (profile != null)
			profileName = profile.name();

		Query query = builder.buildSearchQuery(queryString, qf, facets, retFields, sortField, sortOrder, page, pageSize,
				maxPageSize, profileName);
		return query;
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
			throw new ParamValidationException(I18nConstants.INVALID_PARAM_VALUE, WebEntityConstants.QUERY_PARAM_TYPE,
					typeAsString);
		}

		return entityTypes;
	}

	String[] getLanguageList(String requestedLanguages){
		String[] languageArray = StringUtils.splitByWholeSeparator(requestedLanguages, ",");
		return StringUtils.stripAll(languageArray);	
	}
	
}
