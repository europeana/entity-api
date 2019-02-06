package eu.europeana.entity.definitions.model.vocabulary;

public interface OrganizationSolrFields extends AgentSolrFields{
		
	public static final String EDM_ACRONYM = "edm_acronym";
	public static final String EDM_ACRONYM_ALL = EDM_ACRONYM + ".*";
	public static final String LABEL = "label";
	public static final String DC_DESCRIPTION = "dc_description";
	public static final String FOAF_LOGO = "foaf_logo";
	public static final String FOAF_HOMEPAGE = "foaf_homepage";
	public static final String FOAF_PHONE = "foaf_phone";
	public static final String FOAF_MBOX = "foaf_mbox";
	public static final String EUROPEANA_ROLE = "europeanaRole";
	public static final String EUROPEANA_ROLE_ALL = EUROPEANA_ROLE + ".*";
	public static final String EUROPEANA_ROLE_EN = EUROPEANA_ROLE + ".en";
	public static final String ORGANIZATION_DOMAIN = "organizationDomain";
	public static final String ORGANIZATION_DOMAIN_ALL = ORGANIZATION_DOMAIN +".*";
	public static final String ORGANIZATION_DOMAIN_EN = ORGANIZATION_DOMAIN +".en";
	public static final String GEOGRAPHIC_LEVEL = "geographicLevel";
	public static final String GEOGRAPHIC_LEVEL_ALL = GEOGRAPHIC_LEVEL + ".*";
	public static final String GEOGRAPHIC_LEVEL_EN = GEOGRAPHIC_LEVEL + ".en";
	public static final String COUNTRY = "country";
	
	public static final String VCARD_STREET_ADDRESS = "vcard_streetAddress.1";
	public static final String VCARD_LOCALITY = "vcard_locality.1";
	public static final String VCARD_POSTAL_CODE = "vcard_postalCode.1";
	public static final String VCARD_COUNTRYNAME = "vcard_countryName.1";
	public static final String VCARD_REGION = "vcard_region.1";
	public static final String VCARD_POST_OFFICE_BOX = "vcard_postOfficeBox.1";
	public static final String VCARD_HAS_ADDRESS = "vcard_hasAddress.1";
	public static final String VCARD_HAS_GEO = "vcard_hasGeo.1";
	public static final String TIMESTAMP = "timestamp";
	public static final String PAYLOAD = "payload";
	
}
