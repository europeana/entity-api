package eu.europeana.entity.definitions.exceptions;

/**
 * This class is used represent validation errors for the grouping class hierarchy 
 * 
 * @author GrafR 
 *
 */
public class EntityInstantiationException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5694474327016762122L;
	
	/**
	 * 
	 */
	public static final String DEFAULT_MESSAGE = "Cannot instantiate grouping attribute: ";
	
	public EntityInstantiationException(String attributeName){
		this(attributeName, null);
	}
	
	public EntityInstantiationException(String attributeName , Throwable th){
		super(DEFAULT_MESSAGE + attributeName, th);
	}
	
}
