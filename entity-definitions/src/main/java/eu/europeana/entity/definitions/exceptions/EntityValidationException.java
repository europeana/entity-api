package eu.europeana.entity.definitions.exceptions;

/**
 * This class is used represent validation errors for the grouping class hierarchy
 *  
 * @author GrafR 
 *
 */
public class EntityValidationException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6895963160368650224L;
	public static final String ERROR_NULL_EUROPEANA_ID = "europeanaId must not be null";
	public static final String ERROR_NOT_NULL_OBJECT_ID = "Object ID must be null";
	public static final String ERROR_NULL_USER_SET_ID = "Grouping ID must not be null";
	public static final String ERROR_NULL_CREATOR = "Creator must not be null";
	public static final String ERROR_INVALID_BODY = "Invalid values in grouping body!";

	public EntityValidationException(String message){
		super(message);
	}
	
	public EntityValidationException(String message, Throwable th){
		super(message, th);
	}
	
	
}
