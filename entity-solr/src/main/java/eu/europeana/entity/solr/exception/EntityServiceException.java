package eu.europeana.entity.solr.exception;


public class EntityServiceException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -167560566275881316L;

	public EntityServiceException(String message, Throwable th) {
		super(message, th);
	}

	public EntityServiceException(String message) {
		super(message);
	}
	
	
}
