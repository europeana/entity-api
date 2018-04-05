package eu.europeana.entity.definitions.model;

import java.util.List;
import java.util.Map;

public interface Organization extends Agent {
    
	/**
	 * Retrieves the acronym for an Organization Class (language,value)
	 * format
	 * 
	 * @return A Map<String,List<List<String>>> for the acronyms of an organization
	 *         class (one per language)
	 */
	Map<String, List<String>> getAcronym();

	/**
	 * Set the acronym for an Organization Class
	 * 
	 * @param acronym
	 *            A Map<String,List<List<String>>> for the acronym of an
	 *            organization class (one per language)
	 */
	void setAcronym(Map<String, List<String>> acronym);
	
	/**
	 * Retrieves the europeanaRole for an Organization Class (language,value)
	 * format
	 * 
	 * @return A Map<String,List<List<String>>> for the europeanaRoles of an organization
	 *         class (one per language)
	 */
	Map<String, List<String>> getEuropeanaRole();

	/**
	 * Set the europeanaRole for an Organization Class
	 * 
	 * @param acronym
	 *            A Map<String,List<List<String>>> for the europeanaRole of an
	 *            organization class (one per language)
	 */
	void setEuropeanaRole(Map<String, List<String>> europeanaRole);
	
	public String getPostalCode();

	public void setPostalCode(String postalCode);

	public String getPostBox();

	public void setPostBox(String postBox);

	public String getCountry();

	public void setCountry(String country);

	public String getCity();

	public void setCity(String city);

	public String getStreetAddress();

	public void setStreetAddress(String streetAddress);

	public Map<String, String> getGeographicLevel();

	public void setGeographicLevel(Map<String, String> geographicLevel);

	public Map<String, String> getOrganizationScope();

	public void setOrganizationScope(Map<String, String> organizationScope);

	public Map<String, String> getOrganizationSector();

	public void setOrganizationSector(Map<String, String> organizationSector);

	public Map<String, String> getOrganizationDomain();

	public void setOrganizationDomain(Map<String, String> organizationDomain);

	public String getHomepage();

	public void setHomepage(String homepage);

	public String getLogo();

	public void setLogo(String logo);

}