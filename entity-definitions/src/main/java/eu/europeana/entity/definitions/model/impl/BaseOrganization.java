package eu.europeana.entity.definitions.model.impl;

import java.util.List;
import java.util.Map;

import eu.europeana.entity.definitions.model.Organization;

/**
 * This class defines base organization type of an entity.
 * @author GrafR
 *
 */
public class BaseOrganization extends BaseAgent implements Organization {

	private Map<String, List<String>> acronym;	
	private Map<String, List<String>> europeanaRole;	
	private String homepage;
	private String logo;
	private String postalCode;
	private String postBox;
	private String country;
	private String city;
	private String streetAddress;
	private Map<String, String> geographicLevel;
	private Map<String, String> organizationScope;
	private Map<String, String> organizationSector;
	private Map<String, String> organizationDomain;

	public Map<String, List<String>> getAcronym() {
		return acronym;
	}

	public void setAcronym(Map<String, List<String>> acronym) {
		this.acronym = acronym;
	}
	
	public Map<String, List<String>> getEuropeanaRole() {
		return europeanaRole;
	}

	public void setEuropeanaRole(Map<String, List<String>> europeanaRole) {
		this.europeanaRole = europeanaRole;
	}
	
	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getPostBox() {
		return postBox;
	}

	public void setPostBox(String postBox) {
		this.postBox = postBox;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getStreetAddress() {
		return streetAddress;
	}

	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}

	public Map<String, String> getGeographicLevel() {
		return geographicLevel;
	}

	public void setGeographicLevel(Map<String, String> geographicLevel) {
		this.geographicLevel = geographicLevel;
	}

	public Map<String, String> getOrganizationScope() {
		return organizationScope;
	}

	public void setOrganizationScope(Map<String, String> organizationScope) {
		this.organizationScope = organizationScope;
	}

	public Map<String, String> getOrganizationSector() {
		return organizationSector;
	}

	public void setOrganizationSector(Map<String, String> organizationSector) {
		this.organizationSector = organizationSector;
	}

	public Map<String, String> getOrganizationDomain() {
		return organizationDomain;
	}

	public void setOrganizationDomain(Map<String, String> organizationDomain) {
		this.organizationDomain = organizationDomain;
	}

	public String getHomepage() {
		return homepage;
	}

	public void setHomepage(String homepage) {
		this.homepage = homepage;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

}
