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
		this(message, i18nKey, i18nParams, HttpStatus.UNAUTHORIZED);
//		this(message, i18nKey, i18nParams, HttpStatus.UNAUTHORIZED, null);
	}
	
	//TODO #492 what to do about th?
//	public EntityAuthenticationException(String message, HttpStatus status, Throwable th){
//	public EntityAuthenticationException(String message, String i18nKey, String[] i18nParams, HttpStatus status, Throwable th){
	public EntityAuthenticationException(String message, String i18nKey, String[] i18nParams, HttpStatus status){
//		super(message, i18nKey, i18nParams, status, th);
		super(message, i18nKey, i18nParams, status);
	}
	
}
