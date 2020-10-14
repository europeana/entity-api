package eu.europeana.entity.web.controller;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import eu.europeana.api.common.config.swagger.SwaggerSelect;
import eu.europeana.api.commons.definitions.vocabulary.CommonApiConstants;
import eu.europeana.api.commons.web.exception.HttpException;
import eu.europeana.api.commons.web.http.HttpHeaders;
import eu.europeana.entity.definitions.formats.FormatTypes;
import eu.europeana.entity.definitions.model.Entity;
import eu.europeana.entity.definitions.model.RankedEntity;
import eu.europeana.entity.definitions.model.vocabulary.WebEntityConstants;
import eu.europeana.entity.web.exception.InternalServerException;
import eu.europeana.entity.web.service.EntityService;
import eu.europeana.entity.web.xml.EntityXmlSerializer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Controller
@SwaggerSelect
@Api(tags = "Entity retrieval", description=" ")
public class ResolveController extends BaseRest {
	
    	private static final String ACCEPT = "Accept=";
    	private static final String ACCEPT_HEADER_JSONLD = ACCEPT + HttpHeaders.CONTENT_TYPE_JSONLD;
    	private static final String ACCEPT_HEADER_JSON = ACCEPT + MediaType.APPLICATION_JSON_VALUE;
    	private static final String ACCEPT_HEADER_APPLICATION_RDF_XML = ACCEPT + HttpHeaders.CONTENT_TYPE_APPLICATION_RDF_XML;
    	private static final String ACCEPT_HEADER_RDF_XML = ACCEPT + HttpHeaders.CONTENT_TYPE_RDF_XML;
    	private static final String ACCEPT_HEADER_APPLICATION_XML = ACCEPT + MediaType.APPLICATION_XML_VALUE;
    
	@Resource 
	EntityService entityService;
	@Resource
	EntityXmlSerializer entityXmlSerializer;

	@ApiOperation(value = "Retrieve a known entity", nickname = "getEntity", response = java.lang.Void.class)
	@RequestMapping(value = {"/entity/{type}/{namespace}/{identifier}.jsonld"}, method = RequestMethod.GET,
			produces = {HttpHeaders.CONTENT_TYPE_JSONLD, MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<String> getJsonLdEntity(
			@RequestParam(value = CommonApiConstants.PARAM_WSKEY, required=false) String wskey,
			@PathVariable(value = WebEntityConstants.PATH_PARAM_TYPE) String type,
			@PathVariable(value = WebEntityConstants.PATH_PARAM_NAMESPACE) String namespace,
			@PathVariable(value = WebEntityConstants.PATH_PARAM_IDENTIFIER) String identifier,
			HttpServletRequest request
			) throws HttpException  {	
	    return createResponse(type, namespace, identifier, FormatTypes.jsonld, null, request);			
	}
	
	@ApiOperation(value = "Retrieve a known entity", nickname = "getEntity", response = java.lang.Void.class)
	@RequestMapping(value = {"/entity/{type}/{namespace}/{identifier}.schema.jsonld"}, method = RequestMethod.GET,
			produces = {HttpHeaders.CONTENT_TYPE_JSONLD, MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<String> getSchemaJsonLdEntity(
			@RequestParam(value = CommonApiConstants.PARAM_WSKEY, required=false) String wskey,
			@PathVariable(value = WebEntityConstants.PATH_PARAM_TYPE) String type,
			@PathVariable(value = WebEntityConstants.PATH_PARAM_NAMESPACE) String namespace,
			@PathVariable(value = WebEntityConstants.PATH_PARAM_IDENTIFIER) String identifier,
			HttpServletRequest request
			) throws HttpException  {
	    return createResponse(type, namespace, identifier, FormatTypes.schema, null, request);			
	}
	
	@ApiOperation(value = "Retrieve a known entity", nickname = "getEntity", response = java.lang.Void.class)
	@RequestMapping(value = {"/entity/{type}/{namespace}/{identifier}.xml"}, method = RequestMethod.GET, 
		produces = {HttpHeaders.CONTENT_TYPE_APPLICATION_RDF_XML, HttpHeaders.CONTENT_TYPE_RDF_XML, MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<String> getXmlEntity(
			@RequestParam(value = CommonApiConstants.PARAM_WSKEY, required=false) String wskey,
			@PathVariable(value = WebEntityConstants.PATH_PARAM_TYPE) String type,
			@PathVariable(value = WebEntityConstants.PATH_PARAM_NAMESPACE) String namespace,
			@PathVariable(value = WebEntityConstants.PATH_PARAM_IDENTIFIER) String identifier,
			HttpServletRequest request
			) throws HttpException  {
	    return createResponse(type, namespace, identifier, FormatTypes.xml, HttpHeaders.CONTENT_TYPE_APPLICATION_RDF_XML, request);
	}
	
	@ApiOperation(value = "Retrieve a known entity", nickname = "getEntity", response = java.lang.Void.class)
	@RequestMapping(value = {"/entity/{type}/{namespace}/{identifier}"}, method = RequestMethod.GET, 
			headers = { ACCEPT_HEADER_JSONLD, ACCEPT_HEADER_JSON},
			produces = { HttpHeaders.CONTENT_TYPE_JSONLD_UTF8, HttpHeaders.CONTENT_TYPE_JSON_UTF8})
	public ResponseEntity<String> getEntity(
			@RequestParam(value = CommonApiConstants.PARAM_WSKEY, required=false) String wskey,
			@PathVariable(value = WebEntityConstants.PATH_PARAM_TYPE) String type,
			@PathVariable(value = WebEntityConstants.PATH_PARAM_NAMESPACE) String namespace,
			@PathVariable(value = WebEntityConstants.PATH_PARAM_IDENTIFIER) String identifier,
			HttpServletRequest request
			) throws HttpException  {
	    return createResponse(type, namespace, identifier, FormatTypes.jsonld, null, request);
	    		
	}
	
	@ApiOperation(value = "Retrieve a known entity", nickname = "getEntity", response = java.lang.Void.class)
	@RequestMapping(value = {"/entity/{type}/{namespace}/{identifier}"}, method = RequestMethod.GET, 
			headers = { ACCEPT_HEADER_APPLICATION_RDF_XML, ACCEPT_HEADER_RDF_XML, ACCEPT_HEADER_APPLICATION_XML},
			produces = {HttpHeaders.CONTENT_TYPE_APPLICATION_RDF_XML, HttpHeaders.CONTENT_TYPE_RDF_XML, MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<String> getXmlHeaderEntity(
			@RequestParam(value = CommonApiConstants.PARAM_WSKEY, required=false) String wskey,
			@PathVariable(value = WebEntityConstants.PATH_PARAM_TYPE) String type,
			@PathVariable(value = WebEntityConstants.PATH_PARAM_NAMESPACE) String namespace,
			@PathVariable(value = WebEntityConstants.PATH_PARAM_IDENTIFIER) String identifier,
			HttpServletRequest request
			) throws HttpException  {
	    return createResponse(type, namespace, identifier, FormatTypes.xml, null, request);
	    		
	}
	
	private ResponseEntity<String> createResponse(String type, String namespace, String identifier, FormatTypes outFormat,  String contentType, HttpServletRequest request) throws HttpException{
	    try {
		verifyReadAccess(request);
        	Entity entity = entityService.retrieveByUrl(type, namespace, identifier);
        	String jsonLd = serialize(entity, outFormat);
        
    	    	Date timestamp = ((RankedEntity)entity).getTimestamp();
    	    	Date etagDate = (timestamp != null)? timestamp : new Date();
    	    	String etag = generateETag(etagDate
    	    		, outFormat.name()
		        , getApiVersion()
		        );
    	    	
    	    	MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>(5);
    	    	headers.add(HttpHeaders.ETAG, "" + etag);
    	    	headers.add(HttpHeaders.ALLOW, HttpHeaders.ALLOW_GET);
    	    	if(!outFormat.equals(FormatTypes.schema)) {
    	    		headers.add(HttpHeaders.VARY, HttpHeaders.ACCEPT);
    	    		headers.add(HttpHeaders.LINK, HttpHeaders.VALUE_LDP_RESOURCE);
    	    	}
    	    	if(contentType != null && !contentType.isEmpty())
    	    	    headers.add(HttpHeaders.CONTENT_TYPE, contentType);
    
//        	System.out.println(jsonLd);
    	    	ResponseEntity<String> response = new ResponseEntity<String>(jsonLd, headers, HttpStatus.OK);
        	    	return response;
	    } catch (RuntimeException e) {
	    	//not found .. 
	    	throw new InternalServerException(e);
	    } catch (HttpException e) {
	    	//avoid wrapping http exception
	    	throw e;
	    } catch (Exception e) {
	    	throw new InternalServerException(e);
	    }	
	}
	
	
	@ApiOperation(value = "Performs a lookup for the entity in all 4 datasets", nickname = "resolveEntity", response = java.lang.Void.class)
	@RequestMapping(value = {"/entity/resolve"}, method = RequestMethod.GET,
			produces = { HttpHeaders.CONTENT_TYPE_JSON_UTF8})
	public ResponseEntity<String> resolveEntity(
			@RequestParam(value = CommonApiConstants.PARAM_WSKEY, required=false) String wskey,
			@RequestParam(value = WebEntityConstants.QUERY_PARAM_URI) String uri,
			HttpServletRequest request
			) throws HttpException  {

		try {
			verifyReadAccess(request);
			String entityUri = entityService.resolveByUri(uri.trim());
					
			MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>(5);
			headers.add(HttpHeaders.LOCATION, entityUri);
			headers.add(HttpHeaders.ALLOW, HttpHeaders.ALLOW_GET);

			ResponseEntity<String> response = new ResponseEntity<String>(headers, HttpStatus.MOVED_PERMANENTLY);
			
			return response;
	
		} catch (RuntimeException e) {
			//not found .. 
			throw new InternalServerException(e);
		} catch (HttpException e) {
			//avoid wrapping http exception
			throw e;
		} catch (Exception e) {
			throw new InternalServerException(e);
		}
					
	}
	
}