package eu.europeana.entity.web.controller;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import eu.europeana.api.common.config.swagger.SwaggerSelect;
import eu.europeana.api.commons.web.exception.HttpException;
import eu.europeana.api.commons.web.http.HttpHeaders;
import eu.europeana.entity.definitions.model.ConceptScheme;
import eu.europeana.entity.definitions.model.vocabulary.LdProfiles;
import eu.europeana.entity.definitions.model.vocabulary.WebEntityFields;
import eu.europeana.entity.definitions.model.vocabulary.fields.WebConceptSchemeModelFields;
import eu.europeana.entity.web.exception.InternalServerException;
import eu.europeana.entity.web.http.EntityHttpHeaders;
import eu.europeana.entity.web.http.SwaggerConstants;
import eu.europeana.entity.web.service.EntityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Controller
@Api(tags = "Grouping API")
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
	@ApiOperation(notes = SwaggerConstants.SAMPLES_JSONLD, value = "Create concept scheme", nickname = "createConceptScheme", response = java.lang.Void.class)
	public ResponseEntity<String> createConceptScheme(
			@RequestParam(value = WebEntityFields.PARAM_WSKEY, required = false) String wskey,
			@RequestBody String conceptScheme,
			@RequestParam(value = WebEntityFields.USER_TOKEN, required = false, defaultValue = WebEntityFields.USER_ANONYMOUNS) String userToken,			
			@RequestParam(value = WebEntityFields.PROFILE, required = false) String profile,
			HttpServletRequest request)
					throws HttpException {
					
		/*
		Check user credentials (from either userToken parameter or Authorization header), if invalid respond back with HTTP 401 or if unauthorized respond with HTTP 403;
		Validate and process the Set description for format and mandatory fields (ie. “title”), if false respond with HTTP 400;
		Generate an identifier (in sequence) for the Set;
		Generate and add a created and modified timestamp to the Set;
		Generate “ETag”;
		Store the new Set with its respective id, together with all the containing items following the order given by the list;
		Serialize Set description in JSON-LD  following the requested profile (indicated in the “Prefer” header, otherwise assume the default, ie. minimal) as defined in Section 4.1 and respond with HTTP 201.
		*/
		
		try {
			// validate user - check user credentials (all registered users can create) 
			// if invalid respond with HTTP 401 or if unauthorized respond with HTTP 403;
			// Check client access (a valid "wskey" must be provided)
			validateApiKey(wskey);

			userToken = getUserToken(userToken, request);
			LdProfiles ldProfile = getProfile(profile, request);

			// authorize user
//			getAuthorizationService().authorizeUser(userToken, wskey, null, Operations.CREATE);			
			
			// parse grouping
			ConceptScheme webConceptScheme = getEntityService().parseConceptSchemeLd(conceptScheme);

			// validate and process the Set description for format and mandatory fields
			// if false respond with HTTP 400
			getEntityService().validateWebConceptScheme(webConceptScheme);
			if(StringUtils.isEmpty(webConceptScheme.getContext()))
				webConceptScheme.setContext(WebConceptSchemeModelFields.VALUE_CONTEXT_EUROPEANA_COLLECTION);
			
			// store the new ConceptScheme with its respective id, together with all the containing items 
			// following the order given by the list
			// generate an identifier (in sequence) for the Set
			ConceptScheme storedConceptScheme = getEntityService().storeConceptScheme(webConceptScheme);

//			String serializedConceptSchemeJsonLdStr = null; 
//			String serializedConceptSchemeJsonLdStr = serializeConceptScheme(profile, storedConceptScheme); 
			String serializedConceptSchemeJsonLdStr = serializeConceptScheme(ldProfile, storedConceptScheme); 
						
			Date etagDate = new Date();
			int etag = etagDate.hashCode(); 
			
			// build response
			MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>(6);
			headers.add(HttpHeaders.AUTHORIZATION, HttpHeaders.PREFER);
			headers.add(HttpHeaders.LINK, HttpHeaders.VALUE_LDP_CONTAINER);
			headers.add(HttpHeaders.LINK, HttpHeaders.VALUE_LDP_RESOURCE);
			headers.add(HttpHeaders.ETAG, "" + etag);
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

}
