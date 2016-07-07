package eu.europeana.entity.utils.jsonld;

public interface WebEntityFields {

	//common fields
	public static final String CONTEXT = "@context";
	public static final String ENTITY_CONTEXT = "http://data.europeana.eu/entity/context.json";
	public static final String AT_ID = "@id";
	public static final String AT_TYPE = "@type";
//	public static final String IN_SCHEME = "inScheme";
	public static final String SAME_AS = "sameAs";
	
	//concept fields
	public static final String PREF_LABEL = "prefLabel";
	public static final String ALT_LABEL = "altLabel";
	public static final String NOTE = "note";
	public static final String NOTATION = "notation";
	public static final String RELATED = "related";
	public static final String BROADER = "broader";
	public static final String NARROWER = "narrower";
	
	//match fields
	public static final String EXACT_MATCH = "exactMatch";
	public static final String CLOSE_MATCH = "closeMatch";
	public static final String BROAD_MATCH = "broadMatch";
	public static final String NARROW_MATCH = "narrowMatch";
	public static final String RELATED_MATCH = "relatedMatch";
	
    // REST API query
	public static final String SLASH = "/";
	public static final String PAR_CHAR = "?";
	public static final String AND = "&";
	public static final String EQUALS = "=";
	public static final String PARAM_WSKEY = "wskey";

	// Agent fields
	public static final String DATE                     = "skos_date";
	public static final String IDENTIFIER               = "skos_identifier";
	public static final String HAS_PART                 = "skos_hasPart";
	public static final String IS_PART_OF               = "skos_isPartOf";
	public static final String BEGIN                    = "edm_begin";
	public static final String END                      = "edm_end";
	public static final String HAS_MET                  = "skos_hasMet";
	public static final String IS_RELATED_TO            = "skos_isRelatedTo";
	public static final String NAME                     = "skos_name";
	public static final String BIOGRAPHICAL_INFORMATION = "rdagr2_biographicalInformation"; 
	public static final String DATE_OF_BIRTH            = "rdagr2_dateOfBirth";
	public static final String DATE_OF_DEATH            = "rdagr2_dateOfDeath";
	public static final String PLACE_OF_BIRTH           = "skos_placeOfBirth";
	public static final String PLACE_OF_DEATH           = "skos_placeOfDeath";
	public static final String DATE_OF_ESTABLISHMENT    = "skos_dateOfEstablishment";
	public static final String GENDER                   = "skos_gender";
	public static final String PROFESSION_OR_OCCUPATION = "skos_professionOrOccupation";
	public static final String RDF_ABOUT                = "rdf_about";
	public static final String WIKIPEDIA_CLICKS         = "wikipedia_clicks";
	public static final String EUROPEANA_DOC_COUNT      = "europeana_doc_count";
	public static final String DERIVED_SCORE            = "derived_score";
	public static final String TEXT                     = "text";	
	
}
