package eu.europeana.entity.web.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

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
import eu.europeana.entity.definitions.model.ConceptScheme;
import eu.europeana.entity.definitions.model.Entity;
import eu.europeana.entity.definitions.model.search.SearchProfiles;
import eu.europeana.entity.definitions.model.vocabulary.EntityTypes;
import eu.europeana.entity.definitions.model.vocabulary.LdProfiles;
import eu.europeana.entity.definitions.model.vocabulary.WebEntityConstants;
import eu.europeana.entity.solr.model.SolrConceptImpl;
import eu.europeana.entity.web.exception.EntityStateException;
import eu.europeana.entity.web.exception.InternalServerException;
import eu.europeana.entity.web.exception.ParamValidationException;
import eu.europeana.entity.web.exception.RequestBodyValidationException;
import eu.europeana.entity.web.http.EntityHttpHeaders;
import eu.europeana.entity.web.http.SwaggerConstants;
import eu.europeana.entity.web.model.WebConceptSchemeImpl;
import eu.europeana.entity.web.service.EntityService;
import eu.europeana.grouping.mongo.model.internal.PersistentConceptScheme;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Controller
@Api(tags = "Concept Scheme API")
@SwaggerSelect
public class ConceptSchemeController extends BaseRest {

	@Resource
	EntityService entityService;
	
	protected EntityService getEntityService() {
		return entityService;
	}
	
	public void setEntityService(EntityService entityService) {
		this.entityService = entityService;
	}	

	/**
	 * This method requests parsing of a ConceptScheme in JsonLd format to a ConceptScheme object
	 * @param wskey The API key
	 * @param userToken The user identifier
	 * @param profile The profile definition
	 * @param request HTTP request
	 * @return response entity that comprises response body, headers and status code
	 * @throws HttpException
	 */
	@RequestMapping(value = "/scheme/", method = RequestMethod.POST, 
			produces = {HttpHeaders.CONTENT_TYPE_JSONLD_UTF8, HttpHeaders.CONTENT_TYPE_JSON_UTF8})
	@ApiOperation(notes = SwaggerConstants.SAMPLES_JSONLD, value = "Create concept scheme", nickname = "create", response = java.lang.Void.class)
	public ResponseEntity<String> createConceptScheme(
			@RequestParam(value = CommonApiConstants.PARAM_WSKEY, required = false) String wskey,
			@RequestBody String conceptScheme,
			@RequestParam(value = WebEntityConstants.USER_TOKEN, required = false, defaultValue = WebEntityConstants.USER_ANONYMOUNS) String userToken,			
			@RequestParam(value = CommonApiConstants.QUERY_PARAM_PROFILE, required = false) String profile,
			HttpServletRequest request)
					throws HttpException {
					
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

			// store the new ConceptScheme with its respective id, together with all the containing items 
			// following the order given by the list
			// generate an identifier (in sequence) for the Set
			ConceptScheme storedConceptScheme = getEntityService().storeConceptScheme(webConceptScheme);

			String serializedConceptSchemeJsonLdStr = serializeConceptScheme(ldProfile, storedConceptScheme); 
						
			// build response
			MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>(6);
			headers.add(HttpHeaders.AUTHORIZATION, HttpHeaders.PREFER);
			headers.add(HttpHeaders.LINK, EntityHttpHeaders.VALUE_LDP_BASIC_CONTAINER);
			headers.add(HttpHeaders.LINK, HttpHeaders.VALUE_LDP_RESOURCE);
			headers.add(HttpHeaders.ETAG, generateETag(storedConceptScheme.getModified(), null));
			headers.add(HttpHeaders.ALLOW, HttpHeaders.ALLOW_POST);
			headers.add(EntityHttpHeaders.PREFERENCE_APPLIED, ldProfile.getPreferHeaderValue());
			headers.add(EntityHttpHeaders.CACHE_CONTROL, EntityHttpHeaders.VALUE_CACHE_CONTROL);

			ResponseEntity<String> response = new ResponseEntity<String>(
					serializedConceptSchemeJsonLdStr, headers, HttpStatus.OK);

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
	 * This method retrieves an existing concept scheme identified by given identifier, which is
	 * a number in string format.	 
	 * @param wskey The API key
	 * @param profile The profile definition
	 * @param request HTTP request
	 * @return response entity that comprises response body, headers and status code
	 * @throws HttpException
	 */
	@RequestMapping(value = { "/scheme/{identifier}", "/scheme/{identifier}.jsonld" }, 
			method = {RequestMethod.GET},
			produces = { HttpHeaders.CONTENT_TYPE_JSONLD_UTF8, HttpHeaders.CONTENT_TYPE_JSON_UTF8 }
			)
	@ApiOperation(notes = SwaggerConstants.SAMPLES_JSONLD, value = "Retrieve concept scheme", nickname = "retrieve", response = java.lang.Void.class)
	public ResponseEntity<String> getConceptScheme(
			@RequestParam(value = CommonApiConstants.PARAM_WSKEY, required = false) String wskey,
			@PathVariable(value = WebEntityConstants.PATH_PARAM_IDENTIFIER) String identifier,
			@RequestParam(value = CommonApiConstants.QUERY_PARAM_PROFILE, required = false) String profile,
			HttpServletRequest request)
					throws HttpException {
					
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
				throw new EntityStateException(
					I18nConstants.MESSAGE_NOT_ACCESSIBLE, I18nConstants.MESSAGE_NOT_ACCESSIBLE
					, new String[] { "disabled" });
				
			} else {			
			    serializedConceptSchemeJsonLdStr = serializeConceptScheme(ldProfile, storedConceptScheme); 
			}
			
			// build response
			MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>(6);
			headers.add(HttpHeaders.VARY, HttpHeaders.PREFER);
			headers.add(HttpHeaders.LINK, EntityHttpHeaders.VALUE_LDP_BASIC_CONTAINER);
			headers.add(HttpHeaders.LINK, HttpHeaders.VALUE_LDP_RESOURCE);
			headers.add(HttpHeaders.ETAG, eTag);
			headers.add(HttpHeaders.ALLOW, HttpHeaders.ALLOW_GPuD);
			headers.add(EntityHttpHeaders.PREFERENCE_APPLIED, ldProfile.getPreferHeaderValue());

			ResponseEntity<String> response = new ResponseEntity<String>(
					serializedConceptSchemeJsonLdStr, headers, HttpStatus.OK);

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
	 * @param wskey
	 * @param identifier
	 * @param userToken
	 * @param request
	 * @return
	 * @throws HttpException
	 */
	@RequestMapping(value = { "/scheme/{identifier}" }, method = {RequestMethod.DELETE})
	@ApiOperation(value = "Delete an existing concept scheme", nickname = "delete", response = java.lang.Void.class)
	public ResponseEntity<String> deleteConceptScheme(
			@RequestParam(value = CommonApiConstants.PARAM_WSKEY, required = false) String wskey,
			@PathVariable(value = WebEntityConstants.PATH_PARAM_IDENTIFIER) String identifier,
			@RequestParam(value = WebEntityConstants.USER_TOKEN, required = false, defaultValue = WebEntityConstants.USER_ANONYMOUNS) String userToken,
			HttpServletRequest request)
					throws HttpException {
					
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

			// if the user set is disabled and the user is not an admin, respond with HTTP 410
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

			ResponseEntity<String> response = new ResponseEntity<String>(
					identifier, headers, httpStatus);

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
	
	@RequestMapping(value = {"/scheme/{identifier}"}, method = RequestMethod.PUT, 
			produces = {HttpHeaders.CONTENT_TYPE_JSONLD_UTF8, HttpHeaders.CONTENT_TYPE_JSON_UTF8})
	@ApiOperation(notes = SwaggerConstants.UPDATE_SAMPLES_JSONLD, value = "Update an existing concept scheme", nickname = "update", response = java.lang.Void.class)
	public ResponseEntity<String> updateConceptScheme(@RequestParam(value = CommonApiConstants.PARAM_WSKEY, required = false) String wskey,
			@PathVariable(value = WebEntityConstants.PATH_PARAM_IDENTIFIER) String identifier,
			@RequestBody String conceptScheme,
			@RequestParam(value = WebEntityConstants.USER_TOKEN, required = false, defaultValue = WebEntityConstants.USER_ANONYMOUNS) String userToken,
			@RequestParam(value = CommonApiConstants.QUERY_PARAM_PROFILE, required = false, defaultValue = WebEntityConstants.PROFILE_MINIMAL) String profile,			
			HttpServletRequest request
			) throws HttpException {
		
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
			
			// check if the user is the owner of the set or admin, otherwise respond with 403
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
				
				// validate and process the concept scheme description for format and mandatory fields
				// if false respond with HTTP 400
				getEntityService().validateWebConceptScheme(newConceptScheme);

				// Respond with HTTP 200
				// update an existing concept scheme. merge concept schemas 
				// insert new fields in existing object
				// generate and add a created and modified timestamp to the concept sheme
				ConceptScheme updatedConceptScheme = getEntityService().updateConceptScheme(
						(PersistentConceptScheme) existingConceptScheme, newConceptScheme);
				
				httpStatus = HttpStatus.OK;
				eTag = generateETag(updatedConceptScheme.getModified(), null);
				serializedConceptSchemeJsonLdStr = serializeConceptScheme(ldProfile, updatedConceptScheme); 
			}
			
			// build response entity with headers
			MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>(5);
			headers.add(HttpHeaders.LINK, EntityHttpHeaders.VALUE_LDP_BASIC_CONTAINER);
			headers.add(HttpHeaders.LINK, EntityHttpHeaders.VALUE_LDP_RESOURCE);
			headers.add(HttpHeaders.ALLOW, EntityHttpHeaders.ALLOW_GPD);
			headers.add(HttpHeaders.VARY, EntityHttpHeaders.PREFER);
			headers.add(EntityHttpHeaders.PREFERENCE_APPLIED, ldProfile.getPreferHeaderValue());
			headers.add(HttpHeaders.ETAG, eTag);

			ResponseEntity<String> response = new ResponseEntity<String>(
					serializedConceptSchemeJsonLdStr, headers, httpStatus);

			return response;
			
		} catch (EntityValidationException e) { 
			throw new RequestBodyValidationException(I18nConstants.CONCEPT_SCHEME_CANT_PARSE_BODY, new String[]{e.getMessage()}, e);
		} catch (EntityInstantiationException e) {
			throw new RequestBodyValidationException(I18nConstants.CONCEPT_SCHEME_CANT_PARSE_BODY, new String[]{e.getMessage()}, e);
		} catch (HttpException e) {
				//TODO: change this when OAUTH is implemented and the user information is available in service
				throw e;
		} catch (Exception e) {
			throw new InternalServerException(e);
		}
	}	
	
	/**
	 * This method updates an existing concept scheme identified by given identifier with groupings.	 
	 * @param wskey The API key
	 * @param request HTTP request
	 * @return response entity that comprises response body, headers and status code
	 * @throws HttpException
	 */
	@RequestMapping(value = { "/scheme/{identifier}/concepts", "/scheme/{identifier}/concepts.jsonld" }, 
			method = {RequestMethod.PUT},
			produces = { HttpHeaders.CONTENT_TYPE_JSONLD_UTF8, HttpHeaders.CONTENT_TYPE_JSON_UTF8 }
			)
	@ApiOperation(notes = SwaggerConstants.SAMPLES_JSONLD, value = "Update entities with groupings", nickname = "update with groupings", response = java.lang.Void.class)
	public ResponseEntity<String> updateEntitiesWithGroupings(
			@RequestParam(value = CommonApiConstants.PARAM_WSKEY, required = false) String wskey,
			@PathVariable(value = WebEntityConstants.PATH_PARAM_IDENTIFIER) String identifier,
			HttpServletRequest request)
					throws HttpException {
					
		try {
			// validate user - check user credentials (all registered users can create) 
			// if invalid respond with HTTP 401 or if unauthorized respond with HTTP 403;
			// Check client access (a valid "wskey" must be provided)
			validateApiKey(wskey);
			
			// retrieve a concept scheme based on its identifier - process query
			// if the concept scheme doesn’t exist, respond with HTTP 404
			// if the entity is disabled respond with HTTP 410
			String serializedConceptSchemeJsonLdStr = "";
			ConceptScheme storedConceptScheme = getEntityService().getConceptSchemeById(identifier);
			if (((WebConceptSchemeImpl) storedConceptScheme).isDisabled()) {
				throw new EntityStateException(
					I18nConstants.MESSAGE_NOT_ACCESSIBLE, I18nConstants.MESSAGE_NOT_ACCESSIBLE
					, new String[] { "disabled" });
				
			} else {			
			    serializedConceptSchemeJsonLdStr = serializeConceptScheme(
				    LdProfiles.STANDARD, storedConceptScheme); 
			}
			
			// search based on isDefinedBy content
			String isDefinedBy = storedConceptScheme.getIsDefinedBy();
			if (StringUtils.isBlank(isDefinedBy))
				throw new ParamValidationException(I18nConstants.EMPTY_PARAM_MANDATORY,
						CommonApiConstants.QUERY_PARAM_QUERY, isDefinedBy);

			MultiValueMap<String, String> parameters =
			        UriComponentsBuilder.fromUriString(isDefinedBy).build().getQueryParams();
			List<String> queryStringList = parameters.get("query");
			List<String> qfList = parameters.get("qf");
			List<String> pageSizeList = parameters.get("pageSize");
			List<String> pageList = parameters.get("page");
			List<String> typeList = parameters.get("type");
			    
			// process scope
			String scope = validateScopeParam("");
			
			// process type
			String typeStr = "All";
			if (typeList != null && typeList.size() > 0 && !StringUtils.isBlank(typeList.get(0))) {
			    typeStr = typeList.get(0);
			}
			List<EntityTypes> entityTypes = getEntityTypesFromString(typeStr);

			// process lang 
			String[] preferredLanguages = null;
			
			// process profile
			SearchProfiles searchProfile = null;
			
			// process fl
			String[] retFields = toArray("id");
			
			// process facet
			String[] facets = null; //toArray(facet);
			
			// process sort param
			String[] sortCriteria = null; //toArray(sort);
			
			// process query string
			String queryString = null;
			queryString = queryStringList.get(0).replace("+"," ");
			
			// process qf
			String[] qf = null;
			if (qfList != null && qfList.size() > 0) {
			    qf = convertStringListToArray(qfList);
			}
			
			// process page
			int page = 0;
			if (pageList != null && pageList.size() > 0) {
			    page = Integer.valueOf(pageList.get(0));
			}
			
			// process pageSize
			int pageSize = 0;
			if (pageSizeList != null && pageSizeList.size() > 0) {
			    pageSize = Integer.valueOf(pageSizeList.get(0));
			}
			
			// perform search
			List<String> entityIdList = new ArrayList<String>();
			Query searchQuery = entityService.buildSearchQuery(queryString, qf, facets, sortCriteria, page, pageSize, searchProfile, retFields);
			ResultSet<? extends Entity> results = entityService.search(searchQuery, preferredLanguages, entityTypes, scope);
                        for (Entity searchRes : results.getResults()) {
                            entityIdList.add(searchRes.getEntityId());
                        }
                  
                        // The id of the concept scheme needs to be added to all entities matching the search query. 
                        // NOTE: in order to be more developer friendly, only the identifier part in the URI of 
                        // the ConceptScheme should be stored in the inScheme field.
                        for (Entity searchRes : results.getResults()) {
                            String[] inScheme = ((SolrConceptImpl) searchRes).getInScheme();
                            String entityIdentifier = storedConceptScheme.getEntityIdentifier();
                            if (inScheme == null || !Arrays.asList(inScheme).contains(entityIdentifier)) {
                        	String[] extendedInScheme = addStringToArray(inScheme, entityIdentifier);
                        	((SolrConceptImpl) searchRes).setInScheme(extendedInScheme);
                            }
                        }
                        
                        // perform atomic updates for entities
                        String test = serializeConcept(results.getResults().get(0));
//                        entityService.atomicUpdate(results);

			// build response
			MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>(6);
			headers.add(HttpHeaders.AUTHORIZATION, HttpHeaders.PREFER);
			headers.add(HttpHeaders.LINK, HttpHeaders.VALUE_LDP_CONTAINER);
			headers.add(HttpHeaders.LINK, HttpHeaders.VALUE_LDP_RESOURCE);
//			headers.add(HttpHeaders.ETAG, generateETag(0));
			headers.add(HttpHeaders.ALLOW, HttpHeaders.ALLOW_POST);
			headers.add(EntityHttpHeaders.PREFERENCE_APPLIED, LdProfiles.STANDARD.getPreferHeaderValue());

			ResponseEntity<String> response = new ResponseEntity<String>(
					serializedConceptSchemeJsonLdStr, headers, HttpStatus.OK);

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

}
