package eu.europeana.entity.solr.exception;


public class EntityRuntimeException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -167560566275881316L;

	public EntityRuntimeException(String message, Throwable th) {
		super(message, th);
	}

	public EntityRuntimeException(String message) {
		super(message);
	}
	
	
}
