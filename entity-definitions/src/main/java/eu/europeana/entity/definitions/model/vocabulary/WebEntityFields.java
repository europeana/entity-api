package eu.europeana.entity.definitions.model.vocabulary;

import eu.europeana.api.commons.definitions.vocabulary.CommonApiConstants;

public interface WebEntityFields extends CommonApiConstants{

	// LD fields
	public static final String CONTEXT = "@context";
	public static final String ENTITY_CONTEXT = "http://www.europeana.eu/schemas/context/entity.jsonld";

	// common fields
	public static final String ID = "id";
	public static final String TYPE = "type";
	// public static final String IN_SCHEME = "inScheme";
	public static final String SAME_AS = "sameAs";
	public static final String IDENTIFIER = "identifier";
	public static final String HAS_PART = "hasPart";
	public static final String IS_PART_OF = "isPartOf";
	public static final String PREF_LABEL = "prefLabel";
	public static final String HIDDEN_LABEL = "hiddenLabel";
	public static final String ALT_LABEL = "altLabel";
	public static final String NOTE = "note";
	public static final String RDF_ABOUT = "about";
	public static final String DEPICTION = "depiction";
	

	// concept fields
	public static final String NOTATION = "notation";
	public static final String RELATED = "related";
	public static final String BROADER = "broader";
	public static final String NARROWER = "narrower";

	// match fields
	public static final String EXACT_MATCH = "exactMatch";
//	public static final String COREF = "coref";
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
	public static final String DATE = "date";
	public static final String BEGIN = "begin";
	public static final String END = "end";
	public static final String HAS_MET = "hasMet";
	public static final String IS_RELATED_TO = "isRelatedTo";
	public static final String NAME = "name";
	public static final String BIOGRAPHICAL_INFORMATION = "biographicalInformation";
	public static final String DATE_OF_BIRTH = "dateOfBirth";
	public static final String DATE_OF_DEATH = "dateOfDeath";
	public static final String PLACE_OF_BIRTH = "placeOfBirth";
	public static final String PLACE_OF_DEATH = "placeOfDeath";
	public static final String DATE_OF_ESTABLISHMENT = "dateOfEstablishment";
	public static final String GENDER = "gender";
	public static final String PROFESSION_OR_OCCUPATION = "professionOrOccupation";
	
	//Place fields
	public static final String LATITUDE = "lat";
	public static final String LONGITUDE = "long";
	public static final String ALTITUDE = "alt";
	public static final String IS_NEXT_IN_SEQUENCE = "isNextInSequence";
	public static final String COUNTRY = "country";
	
	//Technical fields
	 public static final String DERIVED_SCORE = "derived_score";
	// public static final String WIKIPEDIA_CLICKS = "wikipedia_clicks";
	// public static final String EUROPEANA_DOC_COUNT = "europeana_doc_count";
	//public static final String TEXT = "text";

}
