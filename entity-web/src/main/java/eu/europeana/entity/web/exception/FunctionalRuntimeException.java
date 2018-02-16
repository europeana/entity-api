package eu.europeana.entity.web.exception;

public class FunctionalRuntimeException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3364526076494279093L;
	
	public FunctionalRuntimeException(String message){
		super(message);
	}
	
	public FunctionalRuntimeException(String message, Throwable th){
		super(message, th);
	}
}
