package eu.europeana.entity.web.model.view;

import java.util.List;
import java.util.Map;

public interface OrganizationPreview extends AgentPreview{
    
	public Map<String, List<String>> getAcronym();

	public void setAcronym(Map<String, List<String>> acronym);
	
	public String getCountry();

	public void setCountry(String country);

	public String getOrganizationDomain();

	public void setOrganizationDomain(String organizationDomain);
	
}
