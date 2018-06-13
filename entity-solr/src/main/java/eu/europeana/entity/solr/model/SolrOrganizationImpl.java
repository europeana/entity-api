package eu.europeana.entity.solr.model;

import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.beans.Field;

import eu.europeana.entity.definitions.model.Organization;
import eu.europeana.entity.definitions.model.impl.BaseOrganization;
import eu.europeana.entity.definitions.model.vocabulary.AgentSolrFields;
import eu.europeana.entity.definitions.model.vocabulary.ConceptSolrFields;
import eu.europeana.entity.definitions.model.vocabulary.OrganizationSolrFields;

/**
 * This class implements Solr Organization type.
 * @author GrafR
 *
 */
public class SolrOrganizationImpl extends BaseOrganization implements Organization {

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
	@Field(AgentSolrFields.IDENTIFIER)
	public void setIdentifier(String[] identifier) {
		super.setIdentifier(identifier);
	}
	
	@Override
	@Field(ConceptSolrFields.ID)
	public void setEntityId(String entityId) {
		super.setEntityId(entityId);
	}
	
	@Override
	@Field(AgentSolrFields.INTERNAL_TYPE)
	public void setInternalType(String internalType) {
		super.setInternalType(internalType);
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
	@Field(OrganizationSolrFields.VCARD_COUNTRY)
	public void setCountry(String country) {
		super.setCountry(country);
	}

	@Override
	@Field(OrganizationSolrFields.VCARD_CITY)
	public void setCity(String city) {
		super.setCity(city);
	}

	@Override
	@Field(OrganizationSolrFields.VCARD_STREET)
	public void setStreetAddress(String streetAddress) {
		super.setStreetAddress(streetAddress);
	}

	@Override
	@Field(OrganizationSolrFields.EDM_GEOGRAPHIC_LEVEL_ALL)
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
	@Field(OrganizationSolrFields.EDM_ORGANIZATION_DOMAIN_ALL)
	public void setOrganizationDomain(Map<String, String> organizationDomain) {
		super.setOrganizationDomain(organizationDomain);
	}

	@Override
	@Field(OrganizationSolrFields.EDM_EUROPEANA_ROLE_ALL)
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

}
