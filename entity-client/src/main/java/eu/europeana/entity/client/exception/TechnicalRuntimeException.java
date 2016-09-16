package eu.europeana.entity.client.exception;

/**
 * This class is meant to be used for marking and handling technical exceptions 
 * that might occur within the system  
 *
 */
public class TechnicalRuntimeException extends RuntimeException{
	
	public TechnicalRuntimeException(String message, Exception e) {
		super(message, e);
	}

	public TechnicalRuntimeException(String message) {
		super(message);
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1672999785376920974L;
}
