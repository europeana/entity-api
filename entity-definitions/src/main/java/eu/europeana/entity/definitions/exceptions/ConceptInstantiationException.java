package eu.europeana.entity.definitions.exceptions;

/**
 * This class is used represent validation errors for the annotation class hierarchy 
 * @author Sergiu Gordea 
 *
 */
public class ConceptInstantiationException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6895963160368630224L;
	public static final String DEFAULT_MESSAGE = "Cannot instantiate concept attribute: ";
	
	public ConceptInstantiationException(String attributeName){
		super(DEFAULT_MESSAGE + attributeName);
	}
	
	public ConceptInstantiationException(String attributeName , Throwable th){
		super(DEFAULT_MESSAGE + attributeName, th);
	}
	
}
