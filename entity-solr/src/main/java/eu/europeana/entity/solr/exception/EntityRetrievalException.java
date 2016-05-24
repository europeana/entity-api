package eu.europeana.entity.solr.exception;


public class EntityRetrievalException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -167560566275881316L;

	public EntityRetrievalException(String message, Throwable th) {
		super(message, th);
	}

	public EntityRetrievalException(String message) {
		super(message);
	}
	
	
}
