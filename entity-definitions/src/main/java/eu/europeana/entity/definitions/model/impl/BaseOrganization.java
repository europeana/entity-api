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

	private Map<String, String> dcDescription;
	private Map<String, List<String>> acronym;	
	private String logo;
	private String homepage;
	private List<String> phone;
	private List<String> mbox;
	private Map<String, List<String>> europeanaRole;	
	private Map<String, List<String>> organizationDomain;
	private Map<String, String> geographicLevel;
	private String country;
	
	//address fields
	private String hasAddress;
	private String streetAddress;
	private String locality;
	private String region;
	private String postalCode;
	private String countryName;
	private String postBox;
	
//	private Map<String, String> organizationScope;
//	private Map<String, String> organizationSector;
	
	@Override
	public Map<String, String> getDcDescription() {
		return dcDescription;
	}

	@Override
	public void setDcDescription(Map<String, String> dcDescription) {
		this.dcDescription = dcDescription;
	}
	
	@Override
	public Map<String, List<String>> getAcronym() {
		return acronym;
	}

	@Override
	public void setAcronym(Map<String, List<String>> acronym) {
		this.acronym = acronym;
	}
	
	@Override
	public Map<String, List<String>> getEuropeanaRole() {
		return europeanaRole;
	}

	@Override
	public void setEuropeanaRole(Map<String, List<String>> europeanaRole) {
		this.europeanaRole = europeanaRole;
	}
	
	@Override
	public List<String> getPhone() {
		return phone;
	}

	@Override
	public void setPhone(List<String> phone) {
		this.phone = phone;
	}

	@Override
	public List<String> getMbox() {
		return mbox;
	}

	@Override
	public void setMbox(List<String> mbox) {
		this.mbox = mbox;
	}

	@Override
	public String getHasAddress() {
		return hasAddress;
	}

	@Override
	public void setHasAddress(String hasAddress) {
		this.hasAddress = hasAddress;
	}

	@Override
	public String getLocality() {
		return locality;
	}

	@Override
	public void setLocality(String locality) {
		this.locality = locality;
	}

	@Override
	public String getRegion() {
		return region;
	}

	@Override
	public void setRegion(String region) {
		this.region = region;
	}

	@Override
	public String getCountryName() {
		return countryName;
	}

	@Override
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	@Override
	public String getPostalCode() {
		return postalCode;
	}

	@Override
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	@Override
	public String getPostBox() {
		return postBox;
	}

	@Override
	public void setPostBox(String postBox) {
		this.postBox = postBox;
	}

	@Override
	public String getCountry() {
		return country;
	}

	@Override
	public void setCountry(String country) {
		this.country = country;
	}

	@Override
	public String getStreetAddress() {
		return streetAddress;
	}

	@Override
	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}

	@Override
	public Map<String, String> getGeographicLevel() {
		return geographicLevel;
	}

	@Override
	public void setGeographicLevel(Map<String, String> geographicLevel) {
		this.geographicLevel = geographicLevel;
	}

//	public Map<String, String> getOrganizationScope() {
//		return organizationScope;
//	}
//
//	public void setOrganizationScope(Map<String, String> organizationScope) {
//		this.organizationScope = organizationScope;
//	}

//	public Map<String, String> getOrganizationSector() {
//		return organizationSector;
//	}
//
//	public void setOrganizationSector(Map<String, String> organizationSector) {
//		this.organizationSector = organizationSector;
//	}

	@Override
	public Map<String, List<String>> getOrganizationDomain() {
		return organizationDomain;
	}

	@Override
	public void setOrganizationDomain(Map<String, List<String>> organizationDomain) {
		this.organizationDomain = organizationDomain;
	}

	@Override
	public String getHomepage() {
		return homepage;
	}

	@Override
	public void setHomepage(String homepage) {
		this.homepage = homepage;
	}

	@Override
	public String getLogo() {
		return logo;
	}

	@Override
	public void setLogo(String logo) {
		this.logo = logo;
	}

}
