package eu.europeana.entity.web.controller.exception;

/**
 * @author GrafR
 *
 */
public class EntityIndexingException extends Exception {

    /**
    * 
    */
    private static final long serialVersionUID = -7199791655850276038L;

    public EntityIndexingException(String message, Throwable th) {
	super(message, th);
    }

}
