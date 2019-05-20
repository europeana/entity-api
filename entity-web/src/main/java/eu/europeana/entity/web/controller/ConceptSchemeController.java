package eu.europeana.entity.web.controller;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.ListUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriComponentsBuilder;

import eu.europeana.api.common.config.I18nConstants;
import eu.europeana.api.common.config.swagger.SwaggerSelect;
import eu.europeana.api.commons.definitions.search.Query;
import eu.europeana.api.commons.definitions.search.ResultSet;
import eu.europeana.api.commons.definitions.vocabulary.CommonApiConstants;
import eu.europeana.api.commons.web.exception.HttpException;
import eu.europeana.api.commons.web.http.HttpHeaders;
import eu.europeana.entity.definitions.exceptions.EntityInstantiationException;
import eu.europeana.entity.definitions.exceptions.EntityValidationException;
import eu.europeana.entity.definitions.formats.FormatTypes;
import eu.europeana.entity.definitions.model.ConceptScheme;
import eu.europeana.entity.definitions.model.Entity;
import eu.europeana.entity.definitions.model.vocabulary.EntitySolrFields;
import eu.europeana.entity.definitions.model.vocabulary.EntityTypes;
import eu.europeana.entity.definitions.model.vocabulary.LdProfiles;
import eu.europeana.entity.definitions.model.vocabulary.WebEntityConstants;
import eu.europeana.entity.web.exception.EntityStateException;
import eu.europeana.entity.web.exception.InternalServerException;
import eu.europeana.entity.web.exception.ParamValidationException;
import eu.europeana.entity.web.exception.RequestBodyValidationException;
import eu.europeana.entity.web.http.EntityHttpHeaders;
import eu.europeana.entity.web.http.SwaggerConstants;
import eu.europeana.entity.web.model.WebConceptSchemeImpl;
import eu.europeana.grouping.mongo.model.internal.PersistentConceptScheme;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Controller
@Api(tags = "Concept Scheme API")
@SwaggerSelect
public class ConceptSchemeController extends BaseRest {

    /**
     * This method requests parsing of a ConceptScheme in JsonLd format to a
     * ConceptScheme object
     * 
     * @param wskey
     *            The API key
     * @param userToken
     *            The user identifier
     * @param profile
     *            The profile definition
     * @param request
     *            HTTP request
     * @return response entity that comprises response body, headers and status code
     * @throws HttpException
     */
    @RequestMapping(value = "/scheme/", method = RequestMethod.POST, produces = { HttpHeaders.CONTENT_TYPE_JSONLD_UTF8,
	    HttpHeaders.CONTENT_TYPE_JSON_UTF8 })
    @ApiOperation(notes = SwaggerConstants.SAMPLES_JSONLD, value = "Create concept scheme", nickname = "create", response = java.lang.Void.class)
    public ResponseEntity<String> createConceptScheme(
	    @RequestParam(value = CommonApiConstants.PARAM_WSKEY, required = false) String wskey,
	    @RequestBody String conceptScheme,
	    @RequestParam(value = WebEntityConstants.USER_TOKEN, required = false, defaultValue = WebEntityConstants.USER_ANONYMOUNS) String userToken,
	    @RequestParam(value = CommonApiConstants.QUERY_PARAM_PROFILE, required = false) String profile,
	    HttpServletRequest request) throws HttpException {

	try {
	    // validate user - check user credentials (all registered users can create)
	    // if invalid respond with HTTP 401 or if unauthorized respond with HTTP 403;
	    // Check client access (a valid "wskey" must be provided)
	    validateApiKey(wskey);

	    checkUserToken(userToken);
	    LdProfiles ldProfile = getProfile(profile, request);

	    // parse concept scheme
	    ConceptScheme webConceptScheme = getEntityService().parseConceptSchemeLd(conceptScheme);
	    getEntityService().validateWebConceptScheme(webConceptScheme);

	    // store the new ConceptScheme with its respective id, together with all the
	    // containing items
	    // following the order given by the list
	    // generate an identifier (in sequence) for the Set
	    ConceptScheme storedConceptScheme = getEntityService().storeConceptScheme(webConceptScheme);

	    // apply linked data profile from header
	    ConceptScheme resConceptScheme = applyProfile(storedConceptScheme, ldProfile);
	    String serializedConceptSchemeJsonLdStr = serialize(resConceptScheme, FormatTypes.jsonld);

	    // build response
	    MultiValueMap<String, String> headers = buildResponseHeaders(ldProfile, storedConceptScheme);

	    ResponseEntity<String> response = new ResponseEntity<String>(serializedConceptSchemeJsonLdStr, headers,
		    HttpStatus.OK);

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

    private MultiValueMap<String, String> buildResponseHeaders(LdProfiles ldProfile,
	    ConceptScheme storedConceptScheme) {
	MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>(6);
	headers.add(HttpHeaders.AUTHORIZATION, HttpHeaders.PREFER);
	headers.add(HttpHeaders.LINK, EntityHttpHeaders.VALUE_LDP_BASIC_CONTAINER);
	headers.add(HttpHeaders.LINK, HttpHeaders.VALUE_LDP_RESOURCE);
	headers.add(HttpHeaders.ETAG, generateETag(storedConceptScheme.getModified(), null));
	headers.add(HttpHeaders.ALLOW, HttpHeaders.ALLOW_POST);
	headers.add(EntityHttpHeaders.PREFERENCE_APPLIED, ldProfile.getPreferHeaderValue());
	headers.add(EntityHttpHeaders.CACHE_CONTROL, EntityHttpHeaders.VALUE_CACHE_CONTROL);
	return headers;
    }

    /**
     * This method retrieves an existing concept scheme identified by given
     * identifier, which is a number in string format.
     * 
     * @param wskey
     *            The API key
     * @param profile
     *            The profile definition
     * @param request
     *            HTTP request
     * @return response entity that comprises response body, headers and status code
     * @throws HttpException
     */
    @RequestMapping(value = { "/scheme/{identifier}", "/scheme/{identifier}.jsonld" }, method = {
	    RequestMethod.GET }, produces = { HttpHeaders.CONTENT_TYPE_JSONLD_UTF8,
		    HttpHeaders.CONTENT_TYPE_JSON_UTF8 })
    @ApiOperation(notes = SwaggerConstants.SAMPLES_JSONLD, value = "Retrieve concept scheme", nickname = "retrieve", response = java.lang.Void.class)
    public ResponseEntity<String> getConceptScheme(
	    @RequestParam(value = CommonApiConstants.PARAM_WSKEY, required = false) String wskey,
	    @PathVariable(value = WebEntityConstants.PATH_PARAM_IDENTIFIER) String identifier,
	    @RequestParam(value = CommonApiConstants.QUERY_PARAM_PROFILE, required = false) String profile,
	    HttpServletRequest request) throws HttpException {

	try {
	    // validate user - check user credentials (all registered users can create)
	    // if invalid respond with HTTP 401 or if unauthorized respond with HTTP 403;
	    // Check client access (a valid "wskey" must be provided)
	    validateApiKey(wskey);

	    LdProfiles ldProfile = getProfile(profile, request);

	    // retrieve a concept scheme based on its identifier - process query
	    // if the concept scheme doesn’t exist, respond with HTTP 404
	    // if the entity is disabled respond with HTTP 410
	    String serializedConceptSchemeJsonLdStr = "";
	    ConceptScheme storedConceptScheme = getEntityService().getConceptSchemeById(identifier);
	    String eTag = generateETag(storedConceptScheme.getModified(), null);
	    if (((WebConceptSchemeImpl) storedConceptScheme).isDisabled()) {
		throw new EntityStateException(I18nConstants.MESSAGE_NOT_ACCESSIBLE,
			I18nConstants.MESSAGE_NOT_ACCESSIBLE, new String[] { "disabled" });

	    } else {
		ConceptScheme resConceptScheme = applyProfile(storedConceptScheme, ldProfile);
		serializedConceptSchemeJsonLdStr = serialize(resConceptScheme, FormatTypes.jsonld);
	    }

	    // build response
	    MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>(6);
	    headers.add(HttpHeaders.VARY, HttpHeaders.PREFER);
	    headers.add(HttpHeaders.LINK, EntityHttpHeaders.VALUE_LDP_BASIC_CONTAINER);
	    headers.add(HttpHeaders.LINK, HttpHeaders.VALUE_LDP_RESOURCE);
	    headers.add(HttpHeaders.ETAG, eTag);
	    headers.add(HttpHeaders.ALLOW, HttpHeaders.ALLOW_GPuD);
	    headers.add(EntityHttpHeaders.PREFERENCE_APPLIED, ldProfile.getPreferHeaderValue());

	    ResponseEntity<String> response = new ResponseEntity<String>(serializedConceptSchemeJsonLdStr, headers,
		    HttpStatus.OK);

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

    /**
     * This method implements removal of a concept scheme
     * 
     * @param wskey
     * @param identifier
     * @param userToken
     * @param request
     * @return
     * @throws HttpException
     */
    @RequestMapping(value = { "/scheme/{identifier}" }, method = { RequestMethod.DELETE })
    @ApiOperation(value = "Delete an existing concept scheme", nickname = "delete", response = java.lang.Void.class)
    public ResponseEntity<String> deleteConceptScheme(
	    @RequestParam(value = CommonApiConstants.PARAM_WSKEY, required = false) String wskey,
	    @PathVariable(value = WebEntityConstants.PATH_PARAM_IDENTIFIER) String identifier,
	    @RequestParam(value = WebEntityConstants.USER_TOKEN, required = false, defaultValue = WebEntityConstants.USER_ANONYMOUNS) String userToken,
	    HttpServletRequest request) throws HttpException {

	try {
	    // validate user - check user credentials (all registered users can create)
	    // if invalid respond with HTTP 401 or if unauthorized respond with HTTP 403;
	    // Check client access (a valid "wskey" must be provided)
	    validateApiKey(wskey);

	    checkUserToken(userToken);

	    // retrieve a concept scheme based on its identifier - process query
	    // if the Set doesn’t exist, respond with HTTP 404
	    // if the Set is disabled respond with HTTP 410
	    ConceptScheme existingConceptScheme = getEntityService().getConceptSchemeById(identifier);
	    checkIfMatchHeader(existingConceptScheme.getModified().hashCode(), request);

	    hasModifyRights(existingConceptScheme, wskey, userToken);

	    // if the user set is disabled and the user is not an admin, respond with HTTP
	    // 410
	    HttpStatus httpStatus = null;
	    String eTag = null;

	    if (((WebConceptSchemeImpl) existingConceptScheme).isDisabled()) {
		getEntityService().deleteConceptScheme(existingConceptScheme.getEntityIdentifier());
		httpStatus = HttpStatus.NO_CONTENT;
		eTag = generateETag(new Date(), null);

	    } else {
		httpStatus = HttpStatus.NO_CONTENT;
		ConceptScheme updated = getEntityService().disableConceptScheme(existingConceptScheme);
		eTag = generateETag(updated.getModified(), null);
	    }

	    // build response
	    MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>(6);
	    headers.add(HttpHeaders.AUTHORIZATION, HttpHeaders.PREFER);
	    headers.add(HttpHeaders.LINK, HttpHeaders.VALUE_LDP_CONTAINER);
	    headers.add(HttpHeaders.LINK, HttpHeaders.VALUE_LDP_RESOURCE);
	    headers.add(HttpHeaders.ETAG, eTag);
	    headers.add(HttpHeaders.ALLOW, EntityHttpHeaders.ALLOW_GPPD);

	    ResponseEntity<String> response = new ResponseEntity<String>(identifier, headers, httpStatus);

	    return response;

	} catch (HttpException e) {
	    // avoid wrapping http exception
	    throw e;
	} catch (RuntimeException e) {
	    // not found ..
	    throw new InternalServerException(e);
	} catch (Exception e) {
	    throw new InternalServerException(e);
	}
    }

    @RequestMapping(value = { "/scheme/{identifier}" }, method = RequestMethod.PUT, produces = {
	    HttpHeaders.CONTENT_TYPE_JSONLD_UTF8, HttpHeaders.CONTENT_TYPE_JSON_UTF8 })
    @ApiOperation(notes = SwaggerConstants.UPDATE_SAMPLES_JSONLD, value = "Update an existing concept scheme", nickname = "update", response = java.lang.Void.class)
    public ResponseEntity<String> updateConceptScheme(
	    @RequestParam(value = CommonApiConstants.PARAM_WSKEY, required = false) String wskey,
	    @PathVariable(value = WebEntityConstants.PATH_PARAM_IDENTIFIER) String identifier,
	    @RequestBody String conceptScheme,
	    @RequestParam(value = WebEntityConstants.USER_TOKEN, required = false, defaultValue = WebEntityConstants.USER_ANONYMOUNS) String userToken,
	    @RequestParam(value = CommonApiConstants.QUERY_PARAM_PROFILE, required = false, defaultValue = WebEntityConstants.PROFILE_MINIMAL) String profile,
	    HttpServletRequest request) throws HttpException {

	try {
	    // check user credentials, if invalid respond with HTTP 401,
	    // check client access (a valid "wskey" must be provided)
	    validateApiKey(wskey);

	    checkUserToken(userToken);
	    LdProfiles ldProfile = getProfile(profile, request);
	    // check if the concept scheme exists, if not respond with HTTP 404

	    // retrieve an existing concept scheme based on its identifier
	    ConceptScheme existingConceptScheme = getEntityService().getConceptSchemeById(identifier);

	    checkIfMatchHeader(existingConceptScheme.getModified().hashCode(), request);

	    // check if the user is the owner of the set or admin, otherwise respond with
	    // 403
	    hasModifyRights(existingConceptScheme, wskey, userToken);

	    // check if the Set is disabled, respond with HTTP 410
	    HttpStatus httpStatus = null;
	    String serializedConceptSchemeJsonLdStr = "";
	    String eTag = null;

	    if (((WebConceptSchemeImpl) existingConceptScheme).isDisabled()) {
		httpStatus = HttpStatus.GONE;
		eTag = generateETag(existingConceptScheme.getModified(), null);
	    } else {
		// parse fields of the new user set to an object
		ConceptScheme newConceptScheme = getEntityService().parseConceptSchemeLd(conceptScheme);

		// validate and process the concept scheme description for format and mandatory
		// fields
		// if false respond with HTTP 400
		getEntityService().validateWebConceptScheme(newConceptScheme);

		// Respond with HTTP 200
		// update an existing concept scheme. merge concept schemas
		// insert new fields in existing object
		// generate and add a created and modified timestamp to the concept sheme
		ConceptScheme updatedConceptScheme = getEntityService()
			.updateConceptScheme((PersistentConceptScheme) existingConceptScheme, newConceptScheme);

		httpStatus = HttpStatus.OK;
		eTag = generateETag(updatedConceptScheme.getModified(), null);
		applyProfile(updatedConceptScheme, ldProfile);
		serializedConceptSchemeJsonLdStr = serialize(updatedConceptScheme, FormatTypes.jsonld);

	    }

	    // build response entity with headers
	    MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>(5);
	    headers.add(HttpHeaders.LINK, EntityHttpHeaders.VALUE_LDP_BASIC_CONTAINER);
	    headers.add(HttpHeaders.LINK, EntityHttpHeaders.VALUE_LDP_RESOURCE);
	    headers.add(HttpHeaders.ALLOW, EntityHttpHeaders.ALLOW_GPD);
	    headers.add(HttpHeaders.VARY, EntityHttpHeaders.PREFER);
	    headers.add(EntityHttpHeaders.PREFERENCE_APPLIED, ldProfile.getPreferHeaderValue());
	    headers.add(HttpHeaders.ETAG, eTag);

	    ResponseEntity<String> response = new ResponseEntity<String>(serializedConceptSchemeJsonLdStr, headers,
		    httpStatus);

	    return response;

	} catch (EntityValidationException e) {
	    throw new RequestBodyValidationException(I18nConstants.CONCEPT_SCHEME_CANT_PARSE_BODY,
		    new String[] { e.getMessage() }, e);
	} catch (EntityInstantiationException e) {
	    throw new RequestBodyValidationException(I18nConstants.CONCEPT_SCHEME_CANT_PARSE_BODY,
		    new String[] { e.getMessage() }, e);
	} catch (HttpException e) {
	    // TODO: change this when OAUTH is implemented and the user information is
	    // available in service
	    throw e;
	} catch (Exception e) {
	    throw new InternalServerException(e);
	}
    }

    /**
     * This method updates an existing concept scheme identified by given identifier
     * with groupings.
     * 
     * @param wskey
     *            The API key
     * @param request
     *            HTTP request
     * @return response entity that comprises response body, headers and status code
     * @throws HttpException
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = { "/scheme/{identifier}/concepts", "/scheme/{identifier}/concepts.jsonld" }, method = {
	    RequestMethod.PUT }, produces = { HttpHeaders.CONTENT_TYPE_JSONLD_UTF8,
		    HttpHeaders.CONTENT_TYPE_JSON_UTF8 })
    @ApiOperation(notes = SwaggerConstants.SAMPLES_JSONLD, value = "Update entities with groupings", nickname = "update with groupings", response = java.lang.Void.class)
    public ResponseEntity<String> updateEntitiesWithGroupings(
	    @RequestParam(value = CommonApiConstants.PARAM_WSKEY, required = false) String wskey,
	    @PathVariable(value = WebEntityConstants.PATH_PARAM_IDENTIFIER) String identifier,
	    @RequestParam(value = CommonApiConstants.QUERY_PARAM_PROFILE, required = false, defaultValue = WebEntityConstants.PROFILE_MINIMAL) String profile,
	    HttpServletRequest request) throws HttpException {

	try {
	    // validate user - check user credentials (all registered users can create)
	    // if invalid respond with HTTP 401 or if unauthorized respond with HTTP 403;
	    // Check client access (a valid "wskey" must be provided)
	    validateApiKey(wskey);

	    LdProfiles ldProfile = getProfile(profile, request);

	    // retrieve a concept scheme based on its identifier - process query
	    // if the concept scheme doesn’t exist, respond with HTTP 404
	    // if the entity is disabled respond with HTTP 410
	    ConceptScheme storedConceptScheme = getEntityService().getConceptSchemeById(identifier);
	    if (((WebConceptSchemeImpl) storedConceptScheme).isDisabled()) {
		throw new EntityStateException(I18nConstants.MESSAGE_NOT_ACCESSIBLE,
			I18nConstants.MESSAGE_NOT_ACCESSIBLE, new String[] { "disabled" });

	    }

	    // search based on isDefinedBy content
	    String isDefinedBy = storedConceptScheme.getIsDefinedBy();
	    if (StringUtils.isBlank(isDefinedBy))
		throw new ParamValidationException(I18nConstants.EMPTY_PARAM_MANDATORY,
			CommonApiConstants.QUERY_PARAM_QUERY, isDefinedBy);

	    //TODO: switch parsing of parameters to CommonsApi.Query
	    //parse parameters from is defined by URL
	    MultiValueMap<String, String> parameters = UriComponentsBuilder.fromUriString(isDefinedBy).build()
		    .getQueryParams();
	    String queryString = parameters.getFirst(CommonApiConstants.QUERY_PARAM_QUERY);
	    List<String> qfList = parameters.get(CommonApiConstants.QUERY_PARAM_QF);
	    String pageSize = parameters.getFirst(CommonApiConstants.QUERY_PARAM_PAGE_SIZE);
	    String page = parameters.getFirst(CommonApiConstants.QUERY_PARAM_PAGE);
	    String type = parameters.getFirst(WebEntityConstants.QUERY_PARAM_TYPE);
	    String scope = parameters.getFirst(WebEntityConstants.QUERY_PARAM_SCOPE);

	    // process query
	    if(StringUtils.isNotBlank(queryString))
	    queryString = URLDecoder.decode(queryString, StandardCharsets.UTF_8.name());
	    
	    // process qf
	    String[] qf = null;
	    if (qfList != null && qfList.size() > 0) {
		qf = convertStringListToArray(qfList);
	    }
	    	    
	    //process pageSize
	    if(StringUtils.isEmpty(page) )
		page = "" + Query.DEFAULT_PAGE;
	    
	    //process pageSize
	    if(StringUtils.isEmpty(pageSize) )
		pageSize = "" + Query.DEFAULT_PAGE_SIZE;
	    
	    // process type
	    if(StringUtils.isBlank(type))
		type = EntityTypes.All.name();
	    else
		type = URLDecoder.decode(type, StandardCharsets.UTF_8.name());
	    		
	    List<EntityTypes> entityTypes = getEntityTypesFromString(type);
	    validateEntityTypes(entityTypes, false);

	    // process fl
	    String[] retFields = toArray(EntitySolrFields.ID);

	    // process sort param
	    // String[] sortCriteria = null; //toArray(sort);
	   
	    //Search concepts that match the ConceptScheme
	    Query searchQuery = getEntityService().buildSearchQuery(queryString, qf, null, null, Integer.valueOf(page),
		    Integer.valueOf(pageSize), null, retFields);
	    
	    List<String> matchingEntityIds = searchEntityIds(searchQuery, scope, entityTypes);
	    getLogger().debug("Matching concepts for scheme : " + identifier + ", #results: " + matchingEntityIds.size());

	    //search entities that are already in the current ConceptScheme 
	    //first page with max size
	    String conceptSchemeId = storedConceptScheme.getEntityId();
	    String inSchemeQuery = EntitySolrFields.IN_SCHEME + ":\"" + conceptSchemeId + "\"";
	    Query existingQuery = getEntityService().buildSearchQuery(inSchemeQuery, qf, null, null, Query.DEFAULT_PAGE, Query.DEFAULT_MAX_PAGE_SIZE, null, retFields);
	    
	    List<String> existingEntityIds = searchEntityIds(existingQuery, null, null);
	    getLogger().debug("existing befoe update: " + existingEntityIds.size());
	    
	    
	    //Compute ADD and REMOVE lists
	    List<String> removeList = ListUtils.subtract(existingEntityIds, matchingEntityIds);
	    List<String> addList = ListUtils.subtract(matchingEntityIds, existingEntityIds);
	    
	    // perform atomic updates for entities
	    getEntityService().performAtomicUpdate(conceptSchemeId, addList, removeList);
	    
	    // update concept scheme
	    // add total (update modified)
	    // fire a new search query to check the actual number of entities in scheme
	    existingQuery.setPageSize(0);
	    ResultSet<? extends Entity> storedResults = getEntityService().search(existingQuery, null, entityTypes, scope);
	    //number of (existing) entities in scheme after atomic update	
	    long inSchemeCount = storedResults.getResultSize();
	    getLogger().debug("matching after update: " + inSchemeCount);
	    
	    storedConceptScheme.setTotal((int)inSchemeCount);
	    ConceptScheme updatedConceptScheme = getEntityService()
			.updateConceptScheme((PersistentConceptScheme) storedConceptScheme, null);
	    
	    // serialize
	    ConceptScheme resConceptScheme = applyProfile(updatedConceptScheme, ldProfile);
	    String serializedConceptSchemeJsonLdStr = serialize(resConceptScheme, FormatTypes.jsonld);

	    // build response
	    MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>(6);
	    headers.add(HttpHeaders.AUTHORIZATION, HttpHeaders.PREFER);
	    headers.add(HttpHeaders.LINK, HttpHeaders.VALUE_LDP_CONTAINER);
	    headers.add(HttpHeaders.LINK, HttpHeaders.VALUE_LDP_RESOURCE);
	    headers.add(HttpHeaders.ALLOW, HttpHeaders.ALLOW_POST);
	    headers.add(EntityHttpHeaders.PREFERENCE_APPLIED, LdProfiles.STANDARD.getPreferHeaderValue());

	    ResponseEntity<String> response = new ResponseEntity<String>(serializedConceptSchemeJsonLdStr, headers,
		    HttpStatus.OK);

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

    private List<String> searchEntityIds(Query searchQuery, String scope, List<EntityTypes> entityTypes) throws HttpException {
	
	List<String> matchingEntityIds = new ArrayList<String>();
	
	ResultSet<? extends Entity> results = getEntityService().search(searchQuery, null, entityTypes, scope);
	for (Entity searchRes : results.getResults()) {
	    matchingEntityIds.add(searchRes.getEntityId());
	}
	return matchingEntityIds;
    }

}
