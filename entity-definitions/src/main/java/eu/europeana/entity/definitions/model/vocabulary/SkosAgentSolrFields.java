package eu.europeana.entity.definitions.model.vocabulary;

public interface SkosAgentSolrFields extends SkosConceptSolrFields{
		
	public static final String DATE                     = "skos_date";
	public static final String IDENTIFIER               = "dc_identifier";
	public static final String HAS_PART                 = "skos_hasPart";
	public static final String IS_PART_OF               = "skos_isPartOf";
	public static final String BEGIN                    = "edm_begin";
	public static final String END                      = "edm_end";
	public static final String HAS_MET                  = "skos_hasMet";
	public static final String IS_RELATED_TO            = "skos_isRelatedTo";
	public static final String NAME                     = "skos_name";
	public static final String BIOGRAPHICAL_INFORMATION = "rdagr2_biographicalInformation"; 
	public static final String BIOGRAPHICAL_INFORMATION_ALL = "rdagr2_biographicalInformation"+".*"; 
	public static final String DATE_OF_BIRTH            = "rdagr2_dateOfBirth";
	public static final String DATE_OF_DEATH            = "rdagr2_dateOfDeath";
	public static final String PLACE_OF_BIRTH           = "rdagr2_placeOfBirth";
	public static final String PLACE_OF_DEATH           = "rdagr2_placeOfDeath";
	public static final String PLACE_OF_BIRTH_ALL       = "rdagr2_placeOfBirth"+ ".*";
	public static final String PLACE_OF_DEATH_ALL       = "rdagr2_placeOfDeath"+ ".*";
	public static final String DATE_OF_ESTABLISHMENT    = "rdagr2_dateOfEstablishment";
	public static final String GENDER                   = "rdagr2_gender";
	public static final String PROFESSION_OR_OCCUPATION = "rdagr2_professionOrOccupation";
	public static final String PROFESSION_OR_OCCUPATION_ALL = "rdagr2_professionOrOccupation"+".*";
	public static final String RDF_ABOUT                = "rdf_about";
	public static final String WIKIPEDIA_CLICKS         = "wikipedia_clicks";
	public static final String EUROPEANA_DOC_COUNT      = "europeana_doc_count";
	public static final String DERIVED_SCORE            = "derived_score";
//	public static final String TEXT                     = "text";
	
	public static final String INTERNAL_TYPE            = "internal_type";
		
	
}
