package eu.europeana.entity.web.controller;

import javax.annotation.Resource;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import eu.europeana.api.common.config.swagger.SwaggerSelect;
import eu.europeana.entity.definitions.model.search.result.ResultSet;
import eu.europeana.entity.solr.model.vocabulary.EntityTypes;
import eu.europeana.entity.web.exception.ApplicationAuthenticationException;
import eu.europeana.entity.web.exception.HttpException;
import eu.europeana.entity.web.exception.InternalServerException;
import eu.europeana.entity.web.exception.ParamValidationException;
import eu.europeana.entity.web.http.HttpHeaders;
import eu.europeana.entity.web.jsonld.ConceptSetSerializer;
import eu.europeana.entity.web.model.view.ConceptView;
import eu.europeana.entity.web.service.EntityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Controller
@Api(tags = "Discovery API")
@SwaggerSelect
public class SearchController {

	@Resource 
	EntityService entityService;
	
	@ApiOperation(value = "Request for auto-completion for given text query", nickname = "getSuggestion", response = java.lang.Void.class)
	@RequestMapping(value = {"/entity/suggest", "/entity/suggest.jsonld"}, method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE, "application/ld+json"})
	public ResponseEntity<String> getSuggestion(
			@RequestParam(value = WebEntityConstants.PARAM_WSKEY) String wskey,
			@RequestParam(value = WebEntityConstants.QUERY_PARAM_TEXT) String text,
			@RequestParam(value = WebEntityConstants.QUERY_PARAM_LANGUAGE, defaultValue = WebEntityConstants.PARAM_LANGUAGE_EN) String language,
			@RequestParam(value = WebEntityConstants.QUERY_PARAM_TYPE, defaultValue = WebEntityConstants.PARAM_ALL) EntityTypes type,
			@RequestParam(value = WebEntityConstants.QUERY_PARAM_NAMESPACE, required = false) String namespace,
			@RequestParam(value = WebEntityConstants.QUERY_PARAM_ROWS, defaultValue = WebEntityConstants.PARAM_DEFAULT_ROWS) int rows
			) throws HttpException  {

		try {
			String action = "get:/entity/suggest";
			
			// Check client access (a valid “wskey” must be provided)
			validateApiKey(wskey);
			
	        //String typeStr = EntityTypes.all.getSolrType();
//			if (type != null)
//				typeStr = type.getSolrType();				
			
			ResultSet<? extends ConceptView> results = entityService.suggest(text, language, type.getInternalType(), namespace, rows);
			
	        if (results == null || results.getResultSize() == 0)
	        	throw new ParamValidationException(ParamValidationException.MESSAGE_BLANK_PARAMETER_VALUE,
	        			WebEntityConstants.PARAM_QUERY, action + ":" + text);

	        ConceptSetSerializer serializer = new ConceptSetSerializer(results);
	        String jsonLd = serializer.serialize();

			MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>(5);
			headers.add(HttpHeaders.VARY, HttpHeaders.ACCEPT);
			headers.add(HttpHeaders.LINK, HttpHeaders.VALUE_LDP_CONTAINER);
			headers.add(HttpHeaders.ALLOW, HttpHeaders.ALLOW_GET+"," +  HttpHeaders.ALLOW_POST);

			ResponseEntity<String> response = new ResponseEntity<String>(jsonLd, headers, HttpStatus.OK);

			return response;

		}catch (RuntimeException e) {
			//not found .. 
			throw new InternalServerException(e);
//		} catch (HttpException e) {
//			//avoid wrapping http exception
//			throw e;
		} catch (Exception e) {
			throw new InternalServerException(e);
		}
			
	}
	
	
	protected void validateApiKey(String wsKey) throws ApplicationAuthenticationException {
		// throws exception if the wskey is not found
	}
	
	
}
