package eu.europeana.entity.web.exception.authentication;

import org.springframework.http.HttpStatus;

import eu.europeana.api.commons.web.exception.HttpException;


public class EntityAuthenticationException extends HttpException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8994054571719881829L;
	/**
	 * 
	 */		
	
	public EntityAuthenticationException(String message, String i18nKey, String[] i18nParams){
		this(message, i18nKey, i18nParams, HttpStatus.UNAUTHORIZED, null);
	}
	
	public EntityAuthenticationException(String message, String i18nKey, String[] i18nParams, HttpStatus status, Throwable th){
		super(message, i18nKey, i18nParams, status, th);
	}
	
}
