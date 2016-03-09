package eu.europeana.entity.web.exception;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import eu.europeana.entity.web.controller.ApiResponseBuilder;
import eu.europeana.entity.web.controller.WebEntityConstants;
import eu.europeana.entity.web.http.HttpHeaders;
import eu.europeana.entity.web.model.EntityApiResponse;

/**
 * TODO move to api-common
 * 
 * @author GordeaS
 *
 */
public class GlobalExceptionHandling extends ApiResponseBuilder {

	Logger logger = Logger.getLogger(getClass());

	public Logger getLogger() {
		return logger;
	}

	@ExceptionHandler(HttpException.class)
	public ResponseEntity<String> handleHttpException(HttpException ex, HttpServletRequest req,
			HttpServletResponse response) throws IOException {

		getLogger().error("An error occured during the invocation of :" + req.getServletPath(), ex);

		return buildResponseEntity(ex, req, response, ex.getStatus());
	}
	
	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<String> handleException(Exception ex, HttpServletRequest req, HttpServletResponse response)
			throws IOException {

		getLogger().error("An unexpected runtime error occured during the invocation of :" + req.getServletPath(), ex);

		return buildResponseEntity(ex, req, response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	private ResponseEntity<String> buildResponseEntity(Exception ex, HttpServletRequest req,
			HttpServletResponse response, HttpStatus status) {
		EntityApiResponse res = getValidationReport(req.getParameter(WebEntityConstants.PARAM_WSKEY), req.getServletPath(),
				null, ex);
		
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>(5);
		headers.add(HttpHeaders.VARY, HttpHeaders.ACCEPT);
		headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
		String body = serializeResponse(res);
		
		ResponseEntity<String> responseEntity = new ResponseEntity<String>(body, headers,
				status);
		return responseEntity;
	}
	

	
}
