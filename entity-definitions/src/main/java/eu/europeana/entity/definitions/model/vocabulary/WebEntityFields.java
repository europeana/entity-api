package eu.europeana.entity.definitions.model.vocabulary;


public interface WebEntityFields {

	// LD fields
	public static final String CONTEXT = "@context";
	public static final String ENTITY_CONTEXT = "http://www.europeana.eu/schemas/context/entity.jsonld";

	public static final String LANGUAGE_EN="en";
	
	// common fields
	public static final String ID = "id";
	public static final String TYPE = "type";
	public static final String SAME_AS = "sameAs";
	public static final String IDENTIFIER = "identifier";
	public static final String HAS_PART = "hasPart";
	public static final String IS_PART_OF = "isPartOf";
	public static final String PREF_LABEL = "prefLabel";
	public static final String HIDDEN_LABEL = "hiddenLabel";
	public static final String ALT_LABEL = "altLabel";
	public static final String NOTE = "note";
	public static final String DEPICTION = "depiction";
	public static final String SOURCE = "source";

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
	public static final String IN_SCHEME = "inScheme";

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
	public static final String DATE_OF_TERMINATION = "dateOfTermination";
	public static final String GENDER = "gender";
	public static final String PROFESSION_OR_OCCUPATION = "professionOrOccupation";
	public static final String WAS_PRESENT_AT = "wasPresentAt";
	
	
	//Place fields
	public static final String LATITUDE = "lat";
	public static final String LONGITUDE = "long";
	public static final String ALTITUDE = "alt";
	public static final String LATITUDE_LONGITUDE = "lat_long";
	
	public static final String IS_NEXT_IN_SEQUENCE = "isNextInSequence";

	// Organization fields
	public static final String DESCRIPTION = "description";
	public static final String ACRONYM = "acronym";
	public static final String COUNTRY = "country";
	public static final String ORGANIZATION_DOMAIN = "organizationDomain";
	public static final String EUROPEANA_ROLE = "europeanaRole";
	public static final String GEOGRAPHIC_LEVEL = "geographicLevel";
	public static final String FOAF_LOGO = "logo";
	public static final String FOAF_HOMEPAGE = "homepage";
	public static final String FOAF_PHONE = "phone";
	public static final String FOAF_MBOX = "mbox";
	
	//Address Fields
	public static final String STREET_ADDRESS = "streetAddress";
	public static final String LOCALITY = "locality";
	public static final String REGION = "region";
	public static final String POSTAL_CODE = "postalCode";
	public static final String COUNTRY_NAME = "countryName";
	public static final String POST_OFFICE_BOX = "postOfficeBox";
	public static final String HAS_ADDRESS = "hasAddress";
	public static final String ADDRESS_TYPE = "Address";

}
