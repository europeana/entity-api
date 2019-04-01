package eu.europeana.entity.web.exception.response;

import org.springframework.http.HttpStatus;

import eu.europeana.api.commons.web.exception.HttpException;

public class ConceptSchemeNotFoundException extends HttpException{

			
	/**
	 * 
	 */
	private static final long serialVersionUID = -7378590596508811132L;

	public ConceptSchemeNotFoundException(String message, String i18nKey, String[] i18nParams){
		this(message, i18nKey, i18nParams, HttpStatus.NOT_FOUND, null);
	}
	
	public ConceptSchemeNotFoundException(String message, String i18nKey, String[] i18nParams, HttpStatus status){
		this(message, i18nKey, i18nParams, status, null);
	}
	
	public ConceptSchemeNotFoundException(String message, String i18nKey, String[] i18nParams, Throwable th){
		this(message, i18nKey, i18nParams, HttpStatus.NOT_FOUND, th);
	}
	
	public ConceptSchemeNotFoundException(String message, String i18nKey, String[] i18nParams, HttpStatus status, Throwable th){
		super(message, i18nKey, i18nParams, status, th);
	}
}
