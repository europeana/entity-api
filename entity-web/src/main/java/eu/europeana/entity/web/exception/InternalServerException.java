package eu.europeana.entity.web.exception;

import org.springframework.http.HttpStatus;

import eu.europeana.entity.config.i18n.I18nConstants;


public class InternalServerException extends HttpException{

	public static final String MESSAGE_UNEXPECTED_EXCEPTION = "An unexpected server exception occured!";
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public InternalServerException(String message, Throwable th){
		super(message, null, null, HttpStatus.INTERNAL_SERVER_ERROR, th);
	}

	//TODO #537 -> this is the error message that gets thrown
	//TODO #492 double check this
	public InternalServerException(Throwable th){
		super(th.getMessage(), I18nConstants.UNEXPECTED, null, HttpStatus.INTERNAL_SERVER_ERROR);
//		this(MESSAGE_UNEXPECTED_EXCEPTION, th);
//		this(i18nService.buildMessage("error.entity_unexpected"), th);
	}

}
