package eu.europeana.entity.solr.model;

import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.beans.Field;

import eu.europeana.entity.definitions.model.Organization;
import eu.europeana.entity.definitions.model.impl.BaseOrganization;
import eu.europeana.entity.definitions.model.vocabulary.ConceptSolrFields;
import eu.europeana.entity.definitions.model.vocabulary.OrganizationSolrFields;

/**
 * This class implements Solr Organization type.
 * @author GrafR
 *
 */
public class SolrOrganizationImpl extends BaseOrganization implements Organization {

	@Override
	@Field(ConceptSolrFields.RDF_ABOUT)
	public void setAbout(String about) {
		super.setAbout(about);
	}
	
	@Override
	@Field(ConceptSolrFields.PREF_LABEL_ALL)
	public void setDcDescription(Map<String, String> dcDescription) {
		super.setDcDescription(dcDescription);
	}
	
	@Override
	@Field(OrganizationSolrFields.EDM_ACRONYM_ALL)
	public void setAcronym(Map<String, List<String>>  acronym) {
		super.setAcronym(acronym);		
	}

	@Override
	@Field(ConceptSolrFields.PREF_LABEL_ALL)
	public void setPrefLabel(Map<String, String> prefLabel) {
		super.setPrefLabel(prefLabel);
	}

	@Override
	@Field(ConceptSolrFields.ALT_LABEL_ALL)
	public void setAltLabel(Map<String, List<String>> altLabel) {
		super.setAltLabel(altLabel);
	}

	@Override
	@Field(ConceptSolrFields.IDENTIFIER)
	public void setIdentifier(String[] identifier) {
		super.setIdentifier(identifier);
	}
	
	@Override
	@Field(ConceptSolrFields.ID)
	public void setEntityId(String entityId) {
		super.setEntityId(entityId);
	}
	
	@Override
	@Field(ConceptSolrFields.INTERNAL_TYPE)
	public void setInternalType(String internalType) {
		super.setInternalType(internalType);
	}	
	
	
	@Override
	@Field(OrganizationSolrFields.GEOGRAPHIC_LEVEL_ALL)
	public void setGeographicLevel(Map<String, String> geographicLevel) {
		super.setGeographicLevel(geographicLevel);
	}

//	@Override
//	@Field(OrganizationSolrFields.EDM_ORGANIZATION_SCOPE_ALL)
//	public void setOrganizationScope(Map<String, String> organizationScope) {
//		super.setOrganizationScope(organizationScope);
//	}

//	@Override
//	@Field(OrganizationSolrFields.EDM_ORGANIZATION_SECTOR_ALL)
//	public void setOrganizationSector(Map<String, String> organizationSector) {
//		super.setOrganizationSector(organizationSector);
//	}

	@Override
	@Field(OrganizationSolrFields.ORGANIZATION_DOMAIN_ALL)
	public void setOrganizationDomain(Map<String, List<String>> organizationDomain) {
		super.setOrganizationDomain(organizationDomain);
	}

	@Override
	@Field(OrganizationSolrFields.EUROPEANA_ROLE_ALL)
	public void setEuropeanaRole(Map<String, List<String>> europeanaRole) {
		super.setEuropeanaRole(europeanaRole);
	}

	@Override
	@Field(OrganizationSolrFields.FOAF_HOMEPAGE)
	public void setHomepage(String homepage) {
		super.setHomepage(homepage);
	}

	@Override
	@Field(OrganizationSolrFields.FOAF_LOGO)
	public void setLogo(String logo) {
		super.setLogo(logo);
	}
	
	@Override
	@Field(ConceptSolrFields.SAME_AS)
	public void setSameAs(String[] sameAs) {
		super.setSameAs(sameAs);
	}
	
	@Override
	@Field(OrganizationSolrFields.VCARD_HAS_ADDRESS)
	public void setHasAddress(String hasAddress) {
		super.setHasAddress(hasAddress);
	}

	@Override
	@Field(OrganizationSolrFields.VCARD_POSTAL_CODE)
	public void setPostalCode(String postalCode) {
		super.setPostalCode(postalCode);
	}

	@Override
	@Field(OrganizationSolrFields.VCARD_POST_OFFICE_BOX)
	public void setPostBox(String postBox) {
		super.setPostBox(postBox);
	}

	@Override
	@Field(OrganizationSolrFields.COUNTRY)
	public void setCountry(String country) {
		super.setCountry(country);
	}

	@Override
	@Field(OrganizationSolrFields.VCARD_LOCALITY)
	public void setLocality(String locality) {
		super.setLocality(locality);
	}

	@Override
	@Field(OrganizationSolrFields.VCARD_STREET_ADDRESS)
	public void setStreetAddress(String streetAddress) {
		super.setStreetAddress(streetAddress);
	}


	@Override
	@Field(OrganizationSolrFields.VCARD_COUNTRYNAME)
	public void setCountryName(String countryName) {
		super.setCountryName(countryName);
	}

	@Override
	@Field(OrganizationSolrFields.VCARD_REGION)
	public void setRegion(String region) {
		super.setRegion(region);
	}

	@Override
	@Field(OrganizationSolrFields.FOAF_PHONE)
	public void setPhone(List<String> phone) {
		super.setPhone(phone);
	}

	@Override
	@Field(OrganizationSolrFields.FOAF_MBOX)
	public void setMbox(List<String> mbox) {
		super.setMbox(mbox);
	}

}
