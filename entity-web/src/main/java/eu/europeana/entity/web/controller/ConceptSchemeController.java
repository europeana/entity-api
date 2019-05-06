package eu.europeana.entity.web.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

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

import eu.europeana.api.common.config.I18nConstants;
import eu.europeana.api.common.config.swagger.SwaggerSelect;
import eu.europeana.api.commons.definitions.vocabulary.CommonApiConstants;
import eu.europeana.api.commons.web.exception.HttpException;
import eu.europeana.api.commons.web.http.HttpHeaders;
import eu.europeana.entity.definitions.exceptions.EntityInstantiationException;
import eu.europeana.entity.definitions.exceptions.EntityValidationException;
import eu.europeana.entity.definitions.model.ConceptScheme;
import eu.europeana.entity.definitions.model.vocabulary.LdProfiles;
import eu.europeana.entity.definitions.model.vocabulary.WebEntityConstants;
import eu.europeana.entity.web.exception.EntityStateException;
import eu.europeana.entity.web.exception.InternalServerException;
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

			userToken = getUserToken(userToken, request);
			LdProfiles ldProfile = getProfile(profile, request);

			// authorize user
//			getAuthorizationService().authorizeUser(userToken, wskey, null, Operations.CREATE);			
			
			// parse concept scheme
			ConceptScheme webConceptScheme = getEntityService().parseConceptSchemeLd(conceptScheme);

			// validate and process the Set description for format and mandatory fields
			// if false respond with HTTP 400
			
			// store the new ConceptScheme with its respective id, together with all the containing items 
			// following the order given by the list
			// generate an identifier (in sequence) for the Set
			ConceptScheme storedConceptScheme = getEntityService().storeConceptScheme(webConceptScheme);

//			String serializedConceptSchemeJsonLdStr = serializeConceptScheme(profile, storedConceptScheme); 
			String serializedConceptSchemeJsonLdStr = serializeConceptScheme(ldProfile, storedConceptScheme); 
						
			// build response
			MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>(6);
			headers.add(HttpHeaders.AUTHORIZATION, HttpHeaders.PREFER);
			headers.add(HttpHeaders.LINK, HttpHeaders.VALUE_LDP_CONTAINER);
			headers.add(HttpHeaders.LINK, HttpHeaders.VALUE_LDP_RESOURCE);
			headers.add(HttpHeaders.ETAG, generateETag(storedConceptScheme.getModified().hashCode()));
			headers.add(HttpHeaders.ALLOW, HttpHeaders.ALLOW_POST);
			headers.add(EntityHttpHeaders.PREFERENCE_APPLIED, ldProfile.getPreferHeaderValue());
//			headers.add(EntityHttpHeaders.PREFERENCE_APPLIED, profile);

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
	 * @param userToken The user identifier
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
			@RequestParam(value = WebEntityConstants.USER_TOKEN, required = false, defaultValue = WebEntityConstants.USER_ANONYMOUNS) String userToken,			
			@RequestParam(value = CommonApiConstants.QUERY_PARAM_PROFILE, required = false) String profile,
			HttpServletRequest request)
					throws HttpException {
					
		try {
			// validate user - check user credentials (all registered users can create) 
			// if invalid respond with HTTP 401 or if unauthorized respond with HTTP 403;
			// Check client access (a valid "wskey" must be provided)
			validateApiKey(wskey);

			userToken = getUserToken(userToken, request);
			LdProfiles ldProfile = getProfile(profile, request);

			// authorize user
//			getAuthorizationService().authorizeUser(userToken, wskey, null, Operations.CREATE);			
			
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
			    serializedConceptSchemeJsonLdStr = serializeConceptScheme(ldProfile, storedConceptScheme); 
			}
			
			// build response
			MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>(6);
			headers.add(HttpHeaders.AUTHORIZATION, HttpHeaders.PREFER);
			headers.add(HttpHeaders.LINK, HttpHeaders.VALUE_LDP_CONTAINER);
			headers.add(HttpHeaders.LINK, HttpHeaders.VALUE_LDP_RESOURCE);
			headers.add(HttpHeaders.ETAG, generateETag(0));
			headers.add(HttpHeaders.ALLOW, HttpHeaders.ALLOW_POST);
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
	 * @param profile
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
			@RequestParam(value = CommonApiConstants.QUERY_PARAM_PROFILE, required = false) String profile,
			HttpServletRequest request)
					throws HttpException {
					
		try {
			// validate user - check user credentials (all registered users can create) 
			// if invalid respond with HTTP 401 or if unauthorized respond with HTTP 403;
			// Check client access (a valid "wskey" must be provided)
			validateApiKey(wskey);

			userToken = getUserToken(userToken, request);
			LdProfiles ldProfile = getProfile(profile, request);

			// authorize user
//			getAuthorizationService().authorizeUser(userToken, wskey, null, Operations.CREATE);			
			
			// retrieve a concept scheme based on its identifier - process query
			// if the Set doesn’t exist, respond with HTTP 404
			// if the Set is disabled respond with HTTP 410
			ConceptScheme existingConceptScheme = getEntityService().getConceptSchemeById(identifier);
						
			// check that only the admins and the owners of the user sets are allowed to delete the user set. 
			// in the case of regular users (not admins), the autorization method must check if the users 
			// that calls the deletion (i.e. identified by provided user token) is the same user as the creator 
			// of the concept scheme
			hasModifyRights(existingConceptScheme, wskey, userToken);

			// check timestamp if provided within the "If-Match" HTTP header, if false respond with HTTP 412
//			checkHeaderTimestamp(request, existingConceptScheme);
						
			// if the user set is disabled and the user is not an admin, respond with HTTP 410
			HttpStatus httpStatus = null;
			
			if (((WebConceptSchemeImpl) existingConceptScheme).isDisabled()) {
//				if (!isAdmin(wsKey, userToken)) { 
//					// if the user is the owner, the response should be 410
//					if (isOwner(existingUserSet, userToken)) {
////						httpStatus = HttpStatus.GONE;	
//						throw new OperationAuthorizationException(I18nConstants.USERSET_ALREADY_DISABLED, 
//								I18nConstants.USERSET_ALREADY_DISABLED, 
//								new String[]{existingUserSet.getIdentifier()},
//								HttpStatus.GONE);
//						
//					} else {
//						// if the user is a registered user but not the owner, the response should be 401 (unathorized)
//						httpStatus = HttpStatus.UNAUTHORIZED;					
//					}
//				} else {
					// if the user is admin, the set should be permanently deleted and 204 should be returned
			    getEntityService().deleteConceptScheme(existingConceptScheme.getEntityIdentifier());
			    httpStatus = HttpStatus.NO_CONTENT;
//				}
			} else {			
   			    // if the user is an Administrator then permanently remove item 
//			    // (and all items that are members of the user set)
			    httpStatus = HttpStatus.NO_CONTENT;
//				 if (isAdmin(wsKey, userToken)) {
//					 getUserSetService().deleteUserSet(existingUserSet.getIdentifier());
//				 } else { // otherwise flag it as disabled
//  					 if (isOwner(existingUserSet, userToken)) {
  			    getEntityService().disableConceptScheme(existingConceptScheme);
//  					 } else {
// 						// if the user is a registered user but not the owner, the response should be 401 (unathorized)
// 						httpStatus = HttpStatus.UNAUTHORIZED;					
//  					 }
//				 }
			}			
			
			// build response
			MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>(6);
			headers.add(HttpHeaders.AUTHORIZATION, HttpHeaders.PREFER);
			headers.add(HttpHeaders.LINK, HttpHeaders.VALUE_LDP_CONTAINER);
			headers.add(HttpHeaders.LINK, HttpHeaders.VALUE_LDP_RESOURCE);
			headers.add(HttpHeaders.ETAG, generateETag(0));
			headers.add(HttpHeaders.ALLOW, EntityHttpHeaders.ALLOW_GPPD);
			headers.add(EntityHttpHeaders.PREFERENCE_APPLIED, ldProfile.getPreferHeaderValue());

			ResponseEntity<String> response = new ResponseEntity<String>(
					identifier, headers, httpStatus);

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
	
	@RequestMapping(value = {"/scheme/{identifier}"}, method = RequestMethod.PUT, 
			produces = {HttpHeaders.CONTENT_TYPE_JSONLD_UTF8, HttpHeaders.CONTENT_TYPE_JSON_UTF8})
	@ApiOperation(notes = SwaggerConstants.UPDATE_SAMPLES_JSONLD, value = "Update an existing concept scheme", nickname = "update", response = java.lang.Void.class)
	public ResponseEntity<String> updateUserSet(@RequestParam(value = CommonApiConstants.PARAM_WSKEY, required = false) String wskey,
			@PathVariable(value = WebEntityConstants.PATH_PARAM_IDENTIFIER) String identifier,
			@RequestBody String conceptScheme,
			@RequestParam(value = WebEntityConstants.USER_TOKEN, required = false, defaultValue = WebEntityConstants.USER_ANONYMOUNS) String userToken,
			@RequestParam(value = CommonApiConstants.QUERY_PARAM_PROFILE, required = false, defaultValue = WebEntityConstants.PROFILE_MINIMAL) String profile,			
			HttpServletRequest request
			) throws HttpException {
		
		try {
			// check user credentials, if invalid respond with HTTP 401,
			// check client access (a valid "wskey" must be provided)
			// Check client access (a valid "wskey" must be provided)
			validateApiKey(wskey);

			userToken = getUserToken(userToken, request);
			LdProfiles ldProfile = getProfile(profile, request);
			
			// authorize user
//			getAuthorizationService().authorizeUser(userToken, wskey, identifier, Operations.UPDATE);

			// check if the Set exists, if not respond with HTTP 404
			// retrieve an existing concept scheme based on its identifier
			ConceptScheme existingConceptScheme = getEntityService().getConceptSchemeById(identifier);

			// check if the user is the owner of the set or admin, otherwise respond with 403
			hasModifyRights(existingConceptScheme, wskey, userToken);
			
			// check timestamp if provided within the “If-Match” HTTP header, if false respond with HTTP 412
//			checkHeaderTimestamp(request, existingConceptScheme);

			// check if the Set is disabled, respond with HTTP 410
			HttpStatus httpStatus = null;
			int modifiedDate = 0;
			String serializedConceptSchemeJsonLdStr = "";
			
			if (((WebConceptSchemeImpl) existingConceptScheme).isDisabled()) { 
				httpStatus = HttpStatus.GONE;
			} else {			
				// parse fields of the new user set to an object
				ConceptScheme newConceptScheme = getEntityService().parseConceptSchemeLd(conceptScheme);
				
				// validate and process the Set description for format and mandatory fields
				// if false respond with HTTP 400
				getEntityService().validateWebConceptScheme(newConceptScheme);
				//validate items 
//				validateUpdateItemsByProfile(existingConceptScheme, newConceptScheme, profile);
				//remove duplicated items
//				getEntityService().removeItemDuplicates(newConceptScheme);
				
				// Respond with HTTP 200
				// update an existing concept scheme. merge concept schemas 
				// insert new fields in existing object
				// generate and add a created and modified timestamp to the concept sheme
//				existingConceptScheme.setModified(newConceptScheme.getModified());
				ConceptScheme updatedConceptScheme = getEntityService().updateConceptScheme(
						(PersistentConceptScheme) existingConceptScheme, newConceptScheme);
				
				modifiedDate = updatedConceptScheme.getModified().hashCode();			
				httpStatus = HttpStatus.OK;

				serializedConceptSchemeJsonLdStr = serializeConceptScheme(ldProfile, updatedConceptScheme); 
			}
			
			// build response entity with headers
			MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>(5);
			headers.add(HttpHeaders.LINK, EntityHttpHeaders.VALUE_LDP_CONTAINER);
			headers.add(HttpHeaders.LINK, EntityHttpHeaders.VALUE_LDP_RESOURCE);
			headers.add(HttpHeaders.ALLOW, EntityHttpHeaders.ALLOW_GPD);
			headers.add(HttpHeaders.VARY, EntityHttpHeaders.PREFER);
			headers.add(EntityHttpHeaders.PREFERENCE_APPLIED, ldProfile.getPreferHeaderValue());
			headers.add(HttpHeaders.ETAG, generateETag(modifiedDate));

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
			
			// retrieve URL from field isDefinedBy
			// perform search for entity IDs with provided URL
			// for found IDs perform atomic update for Entity field inScheme
			// use modifier "set"
			
			// build response
			MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>(6);
			headers.add(HttpHeaders.AUTHORIZATION, HttpHeaders.PREFER);
			headers.add(HttpHeaders.LINK, HttpHeaders.VALUE_LDP_CONTAINER);
			headers.add(HttpHeaders.LINK, HttpHeaders.VALUE_LDP_RESOURCE);
			headers.add(HttpHeaders.ETAG, generateETag(0));
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
