package eu.europeana.entity.web.exception;

import org.springframework.http.HttpStatus;

import eu.europeana.api.commons.web.exception.HttpException;


/**
 * @author GrafR
 *
 */
public class EntityStateException extends HttpException {
	
    /**
     * 
     */
    private static final long serialVersionUID = 1658411212022663491L;

	public EntityStateException(String message, String i18nKey, String[] i18nParams){
		this(message, i18nKey, i18nParams, HttpStatus.GONE);
	}
	
	public EntityStateException(String message, String i18nKey, String[] i18nParams, HttpStatus status){
		super(message, i18nKey, i18nParams, status);
	}
	
}
