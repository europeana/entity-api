package eu.europeana.entity.definitions.model.vocabulary;

public interface AgentSolrFields extends ConceptSolrFields{
		
	public static final String DATE                     = "dc_date";
	public static final String HAS_MET                  = "edm_hasMet";
	public static final String IS_RELATED_TO            = "edm_isRelatedTo";
	public static final String NAME                     = "foaf_name";
	public static final String BIOGRAPHICAL_INFORMATION = "rdagr2_biographicalInformation"; 
	public static final String BIOGRAPHICAL_INFORMATION_ALL = "rdagr2_biographicalInformation"+".*"; 
	public static final String DATE_OF_BIRTH_ALL            = "rdagr2_dateOfBirth"+".*";
	public static final String DATE_OF_DEATH_ALL            = "rdagr2_dateOfDeath"+".*";
	public static final String PLACE_OF_BIRTH           = "rdagr2_placeOfBirth";
	public static final String PLACE_OF_DEATH           = "rdagr2_placeOfDeath";
	public static final String PLACE_OF_BIRTH_ALL       = "rdagr2_placeOfBirth"+ ".*";
	public static final String PLACE_OF_DEATH_ALL       = "rdagr2_placeOfDeath"+ ".*";
	public static final String DATE_OF_ESTABLISHMENT    = "rdagr2_dateOfEstablishment";
	public static final String GENDER                   = "rdagr2_gender";
	public static final String PROFESSION_OR_OCCUPATION = "rdagr2_professionOrOccupation";
	public static final String PROFESSION_OR_OCCUPATION_ALL = "rdagr2_professionOrOccupation"+".*";
	
		
	
}
