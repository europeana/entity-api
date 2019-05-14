package eu.europeana.entity.solr.model;

import java.util.Date;
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

	
//	@Override
//	public void setDescription(Map<String, String> dcDescription) {
//	    super.setDescription(dcDescription);
//	}
	
//	@Override
	@Field(OrganizationSolrFields.DC_DESCRIPTION_ALL)
	public void setDescriptionsMap(Map<String, List<String>> dcDescription) {
	    Map<String, String> normalizedDescription = SolrUtils.normalizeToStringMap(
		    OrganizationSolrFields.DC_DESCRIPTION, dcDescription);
	    super.setDescription(normalizedDescription);
	}
	
	
	@Override
	@Field(OrganizationSolrFields.EDM_ACRONYM_ALL)
	public void setAcronym(Map<String, List<String>>  acronym) {
		Map<String, List<String>> normalizedAcronym = SolrUtils.normalizeStringListMap(
				OrganizationSolrFields.EDM_ACRONYM, acronym);
		super.setAcronym(normalizedAcronym);
	}

	@Override
	@Field(OrganizationSolrFields.PREF_LABEL_ALL)
	public void setPrefLabelStringMap(Map<String, String> prefLabel) {
		Map<String, String> normalizedPrefLabel = SolrUtils.normalizeStringMap(
				ConceptSolrFields.PREF_LABEL, prefLabel);
		super.setPrefLabelStringMap(normalizedPrefLabel);
	}

	@Override
	@Field(OrganizationSolrFields.ALT_LABEL_ALL)
	public void setAltLabel(Map<String, List<String>> altLabel) {
		Map<String, List<String>> normalizedAltLabel = SolrUtils.normalizeStringListMap(
				ConceptSolrFields.ALT_LABEL, altLabel);
		super.setAltLabel(normalizedAltLabel);
	}

	@Override
	@Field(OrganizationSolrFields.IDENTIFIER)
	public void setIdentifier(String[] identifier) {
		super.setIdentifier(identifier);
	}
	
	@Override
	@Field(OrganizationSolrFields.ID)
	public void setEntityId(String entityId) {
		super.setEntityId(entityId);
	}
	
	@Override
	@Field(OrganizationSolrFields.INTERNAL_TYPE)
	public void setInternalType(String internalType) {
		super.setInternalType(internalType);
	}	
	
	
	@Override
	@Field(OrganizationSolrFields.GEOGRAPHIC_LEVEL_ALL)
	public void setGeographicLevel(Map<String, String> geographicLevel) {
		Map<String, String> normalizedGeographicLevel = SolrUtils.normalizeStringMap(
				OrganizationSolrFields.GEOGRAPHIC_LEVEL, geographicLevel);
		super.setGeographicLevelStringMap(normalizedGeographicLevel);		
	}

	@Override
	@Field(OrganizationSolrFields.ORGANIZATION_DOMAIN_ALL)
	public void setOrganizationDomain(Map<String, List<String>> organizationDomain) {
		Map<String, List<String>> normalizedOrganizationDomain = SolrUtils.normalizeStringListMap(
				OrganizationSolrFields.ORGANIZATION_DOMAIN, organizationDomain);
		super.setOrganizationDomain(normalizedOrganizationDomain);		
	}

	@Override
	@Field(OrganizationSolrFields.EUROPEANA_ROLE_ALL)
	public void setEuropeanaRole(Map<String, List<String>> europeanaRole) {
		Map<String, List<String>> normalizedEuropeanaRole = SolrUtils.normalizeStringListMap(
				OrganizationSolrFields.EUROPEANA_ROLE, europeanaRole);
		super.setEuropeanaRole(normalizedEuropeanaRole);
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
	@Field(OrganizationSolrFields.DEPICTION)
	public void setDepiction(String depiction) {
		super.setDepiction(depiction);
	}
	
	@Override
	@Field(OrganizationSolrFields.SAME_AS)
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

	@Override
	@Field(OrganizationSolrFields.VCARD_HAS_GEO)
	public void setHasGeo(String hasGeo) {
		super.setHasGeo(hasGeo);
	}

	@Override
	@Field(OrganizationSolrFields.CREATED)
	public void setCreated(Date created) {
	    	super.setCreated(created);
	}
	
	@Override
	@Field(OrganizationSolrFields.MODIFIED)
	public void setModified(Date modified) {
	    	super.setModified(modified);
	}

}
