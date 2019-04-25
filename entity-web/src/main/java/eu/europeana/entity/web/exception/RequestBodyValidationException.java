package eu.europeana.entity.web.exception;

import org.springframework.http.HttpStatus;

import eu.europeana.api.commons.web.exception.HttpException;

/**
 * This class handles HTTP body request exception
 * 
 * @author GrafR
 *
 */
public class RequestBodyValidationException extends HttpException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1858372026154478958L;
	
	public RequestBodyValidationException(String i18nKey, String[] params){
		this(i18nKey, params, null);		
	}
	
	public RequestBodyValidationException(String i18nKey, String[] params, Throwable th){
		super(i18nKey, i18nKey, params, HttpStatus.BAD_REQUEST, th);		
	}
		
}
