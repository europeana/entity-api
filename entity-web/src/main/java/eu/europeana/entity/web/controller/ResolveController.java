package eu.europeana.entity.web.controller;

import java.io.IOException;
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

import eu.europeana.api.common.config.I18nConstants;
import eu.europeana.api.common.config.swagger.SwaggerSelect;
import eu.europeana.api.commons.definitions.vocabulary.CommonApiConstants;
import eu.europeana.api.commons.web.exception.HttpException;
import eu.europeana.api.commons.web.http.HttpHeaders;
import eu.europeana.corelib.edm.model.schemaorg.ContextualEntity;
import eu.europeana.corelib.edm.utils.JsonLdSerializer;
import eu.europeana.corelib.edm.utils.SchemaOrgTypeFactory;
import eu.europeana.corelib.edm.utils.SchemaOrgUtils;
import eu.europeana.entity.definitions.exceptions.UnsupportedEntityTypeException;
import eu.europeana.entity.definitions.formats.FormatTypes;
import eu.europeana.entity.definitions.model.Entity;
import eu.europeana.entity.definitions.model.RankedEntity;
import eu.europeana.entity.definitions.model.vocabulary.WebEntityConstants;
import eu.europeana.entity.utils.jsonld.EuropeanaEntityLd;
import eu.europeana.entity.web.exception.InternalServerException;
import eu.europeana.entity.web.exception.ParamValidationException;
import eu.europeana.entity.web.service.EntityService;
import eu.europeana.entity.web.xml.EntityXmlSerializer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Controller
@SwaggerSelect
@Api(tags = "Entity retrieval", description=" ")
public class ResolveController extends BaseRest {
	
	@Resource 
	EntityService entityService;
	@Resource
	EntityXmlSerializer entityXmlSerializer;

	@ApiOperation(value = "Retrieve a known entity", nickname = "getEntity", response = java.lang.Void.class)
	@RequestMapping(value = {"/entity/{type}/{namespace}/{identifier}", "/entity/{type}/{namespace}/{identifier}.jsonld", "/entity/{type}/{namespace}/{identifier}.schema.jsonld",
			"/entity/{type}/{namespace}/{identifier}.xml"}, method = RequestMethod.GET,
			produces = {HttpHeaders.CONTENT_TYPE_JSONLD_UTF8, HttpHeaders.CONTENT_TYPE_JSON_UTF8, 
				HttpHeaders.CONTENT_TYPE_APPLICATION_RDF_XML, HttpHeaders.CONTENT_TYPE_RDF_XML, MediaType.APPLICATION_XML_VALUE})
  public ResponseEntity<String> getEntity(
			@RequestParam(value = CommonApiConstants.PARAM_WSKEY, required=false) String wskey,
			@PathVariable(value = WebEntityConstants.PATH_PARAM_TYPE) String type,
			@PathVariable(value = WebEntityConstants.PATH_PARAM_NAMESPACE) String namespace,
			@PathVariable(value = WebEntityConstants.PATH_PARAM_IDENTIFIER) String identifier,
			HttpServletRequest request
			) throws HttpException  {

	    try {			
	    	validateApiKey(wskey);
	    	
	    	FormatTypes outFormat = getFormatType(request);		
	    	
	    	Entity entity = entityService.retrieveByUrl(type, namespace, identifier);
	    	
	    	String jsonLd = serialize(entity, outFormat);

	    	Date timestamp = ((RankedEntity)entity).getTimestamp();
	    	Date etagDate = (timestamp != null)? timestamp : new Date();
	    	int etag = etagDate.hashCode() + outFormat.hashCode(); 
	    	
	    	MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>(5);
	    	headers.add(HttpHeaders.ETAG, "" + etag);
	    	headers.add(HttpHeaders.ALLOW, HttpHeaders.ALLOW_GET);
	    	if(!outFormat.equals(FormatTypes.schema)) {
	    		headers.add(HttpHeaders.VARY, HttpHeaders.ACCEPT);
	    		headers.add(HttpHeaders.LINK, HttpHeaders.VALUE_LDP_RESOURCE);
	    	}

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


	private FormatTypes getFormatType(HttpServletRequest request) throws ParamValidationException {
	    String extension = getExtension(request);
	    String accept = getAcceptedHeader(request);
	    if(extension == null && accept == null)
		extension = FormatTypes.jsonld.name(); // default
	    else if(extension == null && accept != null)
		extension = accept;
	    else if(accept != null && (!extension.equals(accept))) {
		throw new ParamValidationException(I18nConstants.BAD_HEADER_REQUEST, extension, accept, 
			HttpStatus.BAD_REQUEST, null);
	    }
	    //identify required format
	    FormatTypes outFormat = getFormatType(extension);
	    return outFormat;
	}

	private String getAcceptedHeader(HttpServletRequest request) throws ParamValidationException {
	    String accept = request.getHeader(HttpHeaders.ACCEPT);
	    if(accept == null || accept.equals("*/*")){
		return null;
	    }
	    if(accept.toLowerCase().contains(HttpHeaders.CONTENT_TYPE_RDF_XML) || accept.toLowerCase().contains(HttpHeaders.CONTENT_TYPE_APPLICATION_RDF_XML)
		    || accept.toLowerCase().contains(MediaType.APPLICATION_XML_VALUE))
		accept = FormatTypes.xml.name();
	    else if(accept.toLowerCase().contains(HttpHeaders.CONTENT_TYPE_JSONLD_UTF8) || accept.toLowerCase().contains(HttpHeaders.CONTENT_TYPE_JSON_UTF8)
		    || accept.toLowerCase().contains(MediaType.APPLICATION_JSON_VALUE)) {
		accept = FormatTypes.jsonld.name();
	    }  else {
		throw new ParamValidationException(I18nConstants.INVLAID_HEADER_REQUEST, HttpHeaders.ACCEPT, accept, 
			HttpStatus.NOT_ACCEPTABLE, null);
	    }
	    
	    return accept;
	}
	
	
	/**
	 * This method evaluates extension 
	 * @param request The HTTP request
	 * @return current extension
	 */
	private String getExtension(HttpServletRequest request) {
		// identify required extension
		String uri = request.getRequestURI();
		int extensionBeginPos = uri.indexOf('.');
		// set default extension
		String extension = null;// = FormatTypes.jsonld.name();
		// use extension if provided
		if (extensionBeginPos != -1)
			extension = uri.substring(extensionBeginPos+1);
		return extension;
	}


	/**
	 * This method selects serialization method according to provided format.
	 * @param entity The entity
	 * @param format The format extension
	 * @return entity in jsonLd format
	 * @throws UnsupportedEntityTypeException
	 */
	private String serialize(Entity entity, FormatTypes format) 
			throws UnsupportedEntityTypeException {
		
		String responseBody = null;
		ContextualEntity thingObject = null;
        
		if(FormatTypes.jsonld.equals(format)) {
		    	EuropeanaEntityLd entityLd = new EuropeanaEntityLd(entity);		
			return entityLd.toString(4);
		} else if (FormatTypes.schema.equals(format)) {			
		    	responseBody = serializeSchema(entity, responseBody, thingObject);	        
		} else if(FormatTypes.xml.equals(format)) {
		    	responseBody = entityXmlSerializer.serializeXml(entity);
		}
		return responseBody;
	}


	/**
	 * This method serializes Entity object applying schema.org serialization.
	 * @param entity The Entity object
	 * @param entityType The type of the entity
	 * @param jsonLd The resulting json-ld string
	 * @param thingObject The object in corelib format
	 * @return The serialized entity in json-ld string format
	 * @throws UnsupportedEntityTypeException
	 */
	private String serializeSchema(Entity entity, String jsonLd, ContextualEntity thingObject)
			throws UnsupportedEntityTypeException {
		thingObject = SchemaOrgTypeFactory.createContextualEntity(entity);
		
		SchemaOrgUtils.processEntity(entity, thingObject);
		JsonLdSerializer serializer = new JsonLdSerializer();
		try {
		    jsonLd = serializer.serialize(thingObject);
		} catch (IOException e) {
			throw new UnsupportedEntityTypeException(
					"Serialization to schema.org failed for " + thingObject.getId() + e.getMessage());
		}
		return jsonLd;
	}

	
	@ApiOperation(value = "Performs a lookup for the entity in all 4 datasets", nickname = "resolveEntity", response = java.lang.Void.class)
	@RequestMapping(value = {"/entity/resolve"}, method = RequestMethod.GET,
			produces = { HttpHeaders.CONTENT_TYPE_JSON_UTF8})
	public ResponseEntity<String> resolveEntity(
			@RequestParam(value = CommonApiConstants.PARAM_WSKEY, required=false) String wskey,
			@RequestParam(value = WebEntityConstants.QUERY_PARAM_URI) String uri
			) throws HttpException  {

		try {
			
			validateApiKey(wskey);

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
