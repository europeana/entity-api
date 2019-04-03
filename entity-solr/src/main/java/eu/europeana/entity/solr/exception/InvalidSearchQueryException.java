package eu.europeana.entity.solr.exception;


public class InvalidSearchQueryException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -167560566275881316L;

	public InvalidSearchQueryException(String message, Throwable th) {
		super(message, th);
	}

	public InvalidSearchQueryException(String message) {
		super(message);
	}
	
	
}
