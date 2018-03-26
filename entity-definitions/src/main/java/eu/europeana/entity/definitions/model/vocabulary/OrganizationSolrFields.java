package eu.europeana.entity.definitions.model.vocabulary;

public interface OrganizationSolrFields extends AgentSolrFields{
		
	public static final String ACRONYM = "acronym";
	public static final String EDM_ACRONYM_ALL = "edm_acronym.*";
	public static final String LABEL = "label";
	public static final String DC_IDENTIFIER = "dc_identifier";
	public static final String FOAF_LOGO = "foaf_logo";
	public static final String FOAF_HOMEPAGE = "foaf_homepage";
	public static final String EDM_EUROPEANA_ROLE = "edm_europeanaRole.*";
	public static final String EDM_ORGANIZATION_DOMAIN = "edm_organizationDomain.*";
	public static final String EDM_ORGANIZATION_SECTOR = "edm_organizationSector.*";
	public static final String EDM_ORGANIZATION_SCOPE = "edm_organizationScope.*";
	public static final String EDM_GEOGRAPHIC_LEVEL = "edm_geographicLevel.*";
	public static final String VCARD_STREET = "vcard_streetAddress.1";
	public static final String VCARD_CITY = "vcard_locality.1";
	public static final String VCARD_POSTAL_CODE = "vcard_postalCode.1";
	public static final String VCARD_COUNTRY = "vcard_countryName.1";
	public static final String VCARD_POST_OFFICE_BOX = "vcard_postOfficeBox.1";
	public static final String VCARD_HAS_ADDRESS = "vcard_hasAddress.1";
	public static final String TIMESTAMP = "timestamp";
	public static final String PAYLOAD = "payload";
	
}
