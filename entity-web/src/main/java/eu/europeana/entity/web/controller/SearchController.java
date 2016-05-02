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
import org.springframework.web.servlet.ModelAndView;

import eu.europeana.api.common.config.swagger.SwaggerSelect;
import eu.europeana.entity.definitions.model.search.result.ResultSet;
import eu.europeana.entity.web.exception.HttpException;
import eu.europeana.entity.web.exception.InternalServerException;
import eu.europeana.entity.web.http.HttpHeaders;
import eu.europeana.entity.web.jsonld.SuggestionSetSerializer;
import eu.europeana.entity.web.model.EntityApiResponse;
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
	@ApiOperation(value = "Request for auto-completion for given text query", nickname = "getSuggestion", response = java.lang.Void.class)
	@RequestMapping(value = {"/entity/suggest", "/entity/suggest.jsonld"}, method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE, "application/ld+json"})
	public ResponseEntity<String> getSuggestion(
			@RequestParam(value = WebEntityConstants.PARAM_WSKEY) String wskey,
			@RequestParam(value = WebEntityConstants.QUERY_PARAM_TEXT) String text,
			@RequestParam(value = WebEntityConstants.QUERY_PARAM_LANGUAGE, defaultValue = WebEntityConstants.PARAM_LANGUAGE_EN) String language
			,
//			@RequestParam(value = WebEntityConstants.QUERY_PARAM_TYPE, defaultValue = WebEntityConstants.PARAM_ALL) EntityTypes type,
//			@RequestParam(value = WebEntityConstants.QUERY_PARAM_NAMESPACE, required = false) String namespace,
			@RequestParam(value = WebEntityConstants.QUERY_PARAM_ROWS, defaultValue = WebEntityConstants.PARAM_DEFAULT_ROWS) int rows
			) throws HttpException  {

		String action = "get:/entity/suggest";

		try {			
			// Check client access (a valid “wskey” must be provided)
			validateApiKey(wskey);
			
			ResultSet<? extends EntityPreview> results = entityService.suggest(text, language, null, null, rows);
			
	        SuggestionSetSerializer serializer = new SuggestionSetSerializer(results);
	        String jsonLd = serializer.serialize();

			MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>(5);
			headers.add(HttpHeaders.VARY, HttpHeaders.ACCEPT);
			headers.add(HttpHeaders.LINK, HttpHeaders.VALUE_LDP_CONTAINER);
			headers.add(HttpHeaders.ALLOW, HttpHeaders.ALLOW_GET+"," +  HttpHeaders.ALLOW_POST);

			ResponseEntity<String> response = new ResponseEntity<String>(jsonLd, headers, HttpStatus.OK);

			return response;

		} catch (RuntimeException e) {
			//not found .. 
//			throw new InternalServerException(e);
			return buildErrorResponse(e, 
					getErrorReport(wskey, action, EntityApiResponse.ERROR_SUGGESTION_NOT_FOUND, null, false)
					, HttpStatus.NOT_FOUND);	
		} catch (HttpException e) {
			//avoid wrapping http exception
//			throw e;
			return buildErrorResponse(e, 
					getErrorReport(wskey, action, EntityApiResponse.ERROR_SUGGESTION_NOT_FOUND, null, false)
					, HttpStatus.NOT_FOUND);	
		} catch (Exception e) {
//			throw new InternalServerException(e);
			return buildErrorResponse(e, 
					getErrorReport(wskey, action, EntityApiResponse.ERROR_SUGGESTION_NOT_FOUND, null, false)
					, HttpStatus.NOT_FOUND);	
		}
			
	}
		
}
