package eu.europeana.entity.definitions.exceptions;

/**
 * This class is used to represent header validation errors for the user set class hierarchy 
 * @author GrafR 
 *
 */
public class ConceptSchemeProfileValidationException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2502808968438416248L;
	public static final String ERROR_INVALID_PROFILE = "Invalid value for requested profile!";
	private String requestedProfile;
	
	public String getRequestedProfile() {
		return requestedProfile;
	}

	public void setRequestedProfile(String requestedProfile) {
		this.requestedProfile = requestedProfile;
	}

	public ConceptSchemeProfileValidationException(String requestedProfile){
		this(ERROR_INVALID_PROFILE, requestedProfile, null);
	}
	
	public ConceptSchemeProfileValidationException(String message, String requestedProfile, Throwable th){
		super(message, th);
		this.requestedProfile = requestedProfile;
	}
	
	
}
