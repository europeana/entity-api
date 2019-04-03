package eu.europeana.entity.definitions.model.vocabulary;

import eu.europeana.entity.definitions.exceptions.ConceptSchemeProfileValidationException;

/**
 * This enumeration is intended for Linked Data profiles
 * 
 * @author GrafR
 *
 */
public enum LdProfiles implements ProfileKeyword {

	MINIMAL(VALUE_LD_MINIMAL, VALUE_PREFER_MINIMAL), STANDARD(VALUE_LD_CONTAINEDIRIS, VALUE_PREFER_CONTAINEDIRIS), FULL(VALUE_LD_FULL, VALUE_PREFER_FULL);
	
	private String headerValue;
	private String preferHeaderValue;
	

	LdProfiles(String headerValue, String preferHeaderValue){
		this.headerValue = headerValue; 
		this.preferHeaderValue = preferHeaderValue;
	}
	
	/**
	 * Identifying requested profile by Linked Data value.
	 * For user friendliness the the comparison is case insensitive  
	 * @param ldValue
	 * @return
	 * @throws ConceptSchemeProfileValidationException 
	 */
	public static LdProfiles getByHeaderValue(String headerValue) throws ConceptSchemeProfileValidationException{
		
		for(LdProfiles ldType : LdProfiles.values()) {
			if(headerValue.equals(ldType.getHeaderValue())) {
				return ldType;
			}
		}
		throw new ConceptSchemeProfileValidationException(headerValue);		
	}
	
	/**
	 * 
	 * @param name
	 * @return
	 * @throws ConceptSchemeProfileValidationException
	 */
	public static LdProfiles getByName(String name) throws ConceptSchemeProfileValidationException{
		
		for(LdProfiles ldType : LdProfiles.values()){
			if(name.equals(ldType.name().toLowerCase())) {
				return ldType;
			}
		}
		throw new ConceptSchemeProfileValidationException(name);		
	}
	
	@Override
	public String getHeaderValue() {
		return headerValue;
	}
	
	@Override
	public String toString() {
		return getHeaderValue();
	}

	public String getPreferHeaderValue() {
		return preferHeaderValue;
	}
	
}
