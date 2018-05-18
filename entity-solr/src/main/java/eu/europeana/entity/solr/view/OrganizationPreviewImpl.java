package eu.europeana.entity.solr.view;

import java.util.List;
import java.util.Map;

import eu.europeana.entity.web.model.view.OrganizationPreview;

public class OrganizationPreviewImpl extends AgentPreviewImpl implements OrganizationPreview {

	private Map<String, List<String>> acronym;
	private String country;
	private String organizationDomain;

	public String getOrganizationDomain() {
		return organizationDomain;
	}

	public void setOrganizationDomain(String organizationDomain) {
		this.organizationDomain = organizationDomain;
	}

	public Map<String, List<String>> getAcronym() {
		return acronym;
	}

	public void setAcronym(Map<String, List<String>> acronym) {
		this.acronym = acronym;
	}
		
	@Override
	public String getCountry() {
		return country;
	}

	@Override
	public void setCountry(String country) {
		this.country = country;
	}

	
	
}
