package eu.europeana.entity.web.exception.authentication;

import org.springframework.http.HttpStatus;

import eu.europeana.entity.web.exception.HttpException;


public class EntityAuthenticationException extends HttpException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8994054571719881829L;
	/**
	 * 
	 */
	public static final String MESSAGE_NO_APPLICATION_FOR_APIKEY = "No client application registered for the given apiKey!";
	public static final String MESSAGE_INVALID_APIKEY = "Invalid apiKey! ";
	public static final String MESSAGE_EMPTY_APIKEY = "Empty apiKey! ";
	public static final String MESSAGE_APIKEY_FILE_NOT_FOUND = 
			"No apiKey file found in /config/authentication_templates folder! ";
		
	private String paramValue; 
	
	public EntityAuthenticationException(String message, String paramValue){
		this(message, paramValue, null);
	}
	
	public EntityAuthenticationException(String message, String paramValue, Throwable th){
		this(message, paramValue, HttpStatus.UNAUTHORIZED, th);
	}

	public EntityAuthenticationException(String message, String paramValue, HttpStatus status, Throwable th){
		super(message + " " + paramValue, status, th);
		this.paramValue = paramValue;
	}
	
	public String getParamValue() {
		return paramValue;
	}

}
