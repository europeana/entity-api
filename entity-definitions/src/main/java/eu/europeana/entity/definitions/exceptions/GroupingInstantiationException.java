package eu.europeana.entity.definitions.exceptions;

/**
 * This class is used represent validation errors for the grouping class hierarchy 
 * 
 * @author GrafR 
 *
 */
public class GroupingInstantiationException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5694474327016762122L;
	
	/**
	 * 
	 */
	public static final String DEFAULT_MESSAGE = "Cannot instantiate grouping attribute: ";
	
	public GroupingInstantiationException(String attributeName){
		this(attributeName, null);
	}
	
	public GroupingInstantiationException(String attributeName , Throwable th){
		super(DEFAULT_MESSAGE + attributeName, th);
	}
	
}
