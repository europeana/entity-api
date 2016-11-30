package eu.europeana.entity.client.exception;

/**
 * This class is meant to be used for marking and handling technical exceptions 
 * that might occur within the system  
 *
 */
public class ResolveException extends RuntimeException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8281933808897246375L;

	public ResolveException(String message, Exception e) {
		super(message, e);
	}

	public ResolveException(String message) {
		super(message);
	}
}
