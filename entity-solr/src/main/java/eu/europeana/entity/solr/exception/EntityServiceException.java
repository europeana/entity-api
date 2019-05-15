package eu.europeana.entity.solr.exception;


public class EntityServiceException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 229260749080449197L;

	public EntityServiceException(String message, Throwable th) {
		super(message, th);
	}

	public EntityServiceException(String message) {
		super(message);
	}
	
	
}
