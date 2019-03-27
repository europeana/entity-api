package eu.europeana.entity.definitions.exceptions;

/**
 * This exception is triggered when provided format is not known.
 * 
 * @author GrafR
 *
 */
public class UnsupportedFormatTypeException extends Exception {

	private static final long serialVersionUID = -8413674242449152116L;
	
	public UnsupportedFormatTypeException(String message) {
		super(message);
	}
}
