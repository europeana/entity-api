package eu.europeana.entity.web.exception.authorization;

import org.springframework.http.HttpStatus;

import eu.europeana.api.commons.web.exception.HttpException;

public class UserAuthorizationException extends HttpException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -214259531722175838L;

	public UserAuthorizationException(String message, String i18nKey, String[] i18nParams){
		this(message, i18nKey, i18nParams, HttpStatus.FORBIDDEN, null);
	}
	
	public UserAuthorizationException(String message, String i18nKey, String[] i18nParams, Throwable th){
		this(message, i18nKey, i18nParams, HttpStatus.FORBIDDEN, th);
	}
	
	public UserAuthorizationException(String message, String i18nKey, String[] i18nParams, HttpStatus status){
		this(message, i18nKey, i18nParams, status, null);
	}

	public UserAuthorizationException(String message, String i18nKey, String[] i18nParams, HttpStatus status, Throwable th){
		super(message, i18nKey, i18nParams, status, th);
	}

}
