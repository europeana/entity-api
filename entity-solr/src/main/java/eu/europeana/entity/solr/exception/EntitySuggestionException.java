package eu.europeana.entity.solr.exception;


public class EntitySuggestionException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -167560566275881316L;

	public EntitySuggestionException(String message, Throwable th) {
		super(message, th);
	}

	public EntitySuggestionException(String message) {
		super(message);
	}
	
	
}
