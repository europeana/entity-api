package eu.europeana.entity.web.exception;


import org.springframework.http.HttpStatus;

public class ApplicationAuthenticationException extends HttpException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8994054535719881829L;
	/**
	 * 
	 */
	public static final String MESSAGE_NO_APPLICATION_FOR_APIKEY = "No client application registered for the given apiKey!";
	public static final String MESSAGE_INVALID_APIKEY = "Invalid apiKey! ";
		
	private String paramValue; 
	
	public ApplicationAuthenticationException(String message, String paramValue){
		this(message, paramValue, null);
	}
	
	public ApplicationAuthenticationException(String message, String paramValue, Throwable th){
		this(message, paramValue, HttpStatus.UNAUTHORIZED, th);
	}

	public ApplicationAuthenticationException(String message, String paramValue, HttpStatus status, Throwable th){
		super(message + " " + paramValue, status, th);
		this.paramValue = paramValue;
	}
	
	public String getParamValue() {
		return paramValue;
	}

}
