package eu.europeana.entity.web.exception.authorization;

import org.springframework.http.HttpStatus;

import eu.europeana.api.commons.web.exception.HttpException;

public class OperationAuthorizationException extends HttpException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3952552850686149411L;
	
	public OperationAuthorizationException(String message, String i18nKey, String[] i18nParams){
		this(message, i18nKey, i18nParams, HttpStatus.METHOD_NOT_ALLOWED, null);
	}
	
	public OperationAuthorizationException(String message, String i18nKey, String[] i18nParams, HttpStatus status){
		this(message, i18nKey, i18nParams, status, null);
	}

	public OperationAuthorizationException(String message, String i18nKey, String[] i18nParams, HttpStatus status, Throwable th){
		super(message, i18nKey, i18nParams, status, th);
	}
	
}
