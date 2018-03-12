package eu.europeana.entity.definitions.model.vocabulary;


public interface WebEntityFields {

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
	public static final String DEPICTION_SOURCE = "source";
//	public static final String CONCEPT="Concept";

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
	
	//Place fields
	public static final String LATITUDE = "lat";
	public static final String LONGITUDE = "long";
	public static final String ALTITUDE = "alt";
	public static final String IS_NEXT_IN_SEQUENCE = "isNextInSequence";
//	public static final String COUNTRY = "country";

	// Organization fields
	public static final String ACRONYM = "acronym";
	public static final String ACRONYM_EN = "acronym.en";
	public static final String EDM_ACRONYM = "edm_acronym.en";
	public static final String LABEL = "label";
	public static final String DC_IDENTIFIER = "dc_identifier";
	public static final String FOAF_LOGO = "foaf_logo";
	public static final String FOAF_HOMEPAGE = "foaf_homepage";
	public static final String EDM_EUROPEANA_ROLE = "edm_europeanaRole.en";
	public static final String EDM_ORGANIZATION_DOMAIN = "edm_organizationDomain.en";
	public static final String EDM_ORGANIZATION_SECTOR = "edm_organizationSector.en";
	public static final String EDM_ORGANIZATION_SCOPE = "edm_organizationScope.en";
	public static final String EDM_GEOGRAPHIC_LEVEL = "edm_geographicLevel.en";
	public static final String VCARD_STREET = "vcard_streetAddress.1";
	public static final String VCARD_CITY = "vcard_locality.1";
	public static final String VCARD_POSTAL_CODE = "vcard_postalCode.1";
	public static final String VCARD_COUNTRY = "vcard_countryName.1";
	public static final String VCARD_POST_OFFICE_BOX = "vcard_postOfficeBox.1";
	public static final String VCARD_HAS_ADDRESS = "vcard_hasAddress.1";
	public static final String TIMESTAMP = "timestamp";
	public static final String PAYLOAD = "payload";

	//Technical fields
	public static final String DERIVED_SCORE = "derived_score";
	public static final String TEXT = "text";

}
