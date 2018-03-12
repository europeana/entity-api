package eu.europeana.entity.solr.model;

import java.util.Date;
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
	@Field(OrganizationSolrFields.EDM_ACRONYM)
	public void setAcronym(String[] acronym) {
		super.setAcronym(acronym);		
	}

	@Override
	@Field(OrganizationSolrFields.ACRONYM)
	public void setSimpleAcronym(String simpleAcronym) {
		super.setSimpleAcronym(simpleAcronym);		
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
	@Field(OrganizationSolrFields.LABEL)
	public void setLabel(String[] label) {
		super.setLabel(label);
	}

	
	@Override
	@Field(OrganizationSolrFields.TIMESTAMP)
	public void setTimestamp(Date timestamp) {
		super.setTimestamp(timestamp);
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
	public void setStreet(String street) {
		super.setStreet(street);
	}

	@Override
	@Field(OrganizationSolrFields.EDM_GEOGRAPHIC_LEVEL)
	public void setLevel(String level) {
		super.setLevel(level);
	}

	@Override
	@Field(OrganizationSolrFields.EDM_ORGANIZATION_SCOPE)
	public void setScope(String scope) {
		super.setScope(scope);
	}

	@Override
	@Field(OrganizationSolrFields.EDM_ORGANIZATION_SECTOR)
	public void setSector(String sector) {
		super.setSector(sector);
	}

	@Override
	@Field(OrganizationSolrFields.EDM_ORGANIZATION_DOMAIN)
	public void setDomain(String domain) {
		super.setDomain(domain);
	}

	@Override
	@Field(OrganizationSolrFields.EDM_EUROPEANA_ROLE)
	public void setRole(String[] role) {
		super.setRole(role);
	}

	@Override
	@Field(OrganizationSolrFields.FOAF_HOMEPAGE)
	public void setHomepage(String[] homepage) {
		super.setHomepage(homepage);
	}

	@Override
	@Field(OrganizationSolrFields.FOAF_LOGO)
	public void setLogo(String[] logo) {
		super.setLogo(logo);
	}

	@Override
	@Field(OrganizationSolrFields.DC_IDENTIFIER)
	public void setDcIdentifier(String[] dcIdentifier) {
		super.setDcIdentifier(dcIdentifier);
	}
		
	@Override
	@Field(OrganizationSolrFields.PAYLOAD)
	public void setPayload(String[] payload) {
		super.setPayload(payload);
	}

}
