package eu.europeana.entity.definitions.model.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import eu.europeana.corelib.definitions.edm.entity.Address;
import eu.europeana.entity.definitions.model.Organization;

/**
 * This class defines base organization type of an entity.
 * @author GrafR
 *
 */
public class BaseOrganization extends BaseEntity 
		implements Organization, eu.europeana.corelib.definitions.edm.entity.Organization {

	private Map<String, String> description;
	private Map<String, List<String>> acronym;	
	private String logo;
	private String homepage;
	private List<String> phone;
	private List<String> mbox;
	private Map<String, List<String>> europeanaRole;	
	private Map<String, List<String>> organizationDomain;
	private Map<String, String> geographicLevel;
	private String country;
	private Map<String, List<String>> tmpIdentifier;
	
	//address fields
	private String hasAddress;
	private String streetAddress;
	private String locality;
	private String region;
	private String postalCode;
	private String countryName;
	private String postBox;
	private String hasGeo;
	private Address address;
	
	@Override
	public Map<String, String> getDescription() {
		return description;
	}

	@Override
	public void setDescription(Map<String, String> dcDescription) {
	    	this.description = dcDescription;
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
	@Deprecated
	public void setGeographicLevel(Map<String, String> geographicLevel) {
		this.geographicLevel = geographicLevel;
	}

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

	@Override
	public String getHasGeo() {
		return hasGeo;
	}

	@Override
	public void setHasGeo(String hasGeo) {
		this.hasGeo = hasGeo;
	}

	public Map<String, String> getGeographicLevelStringMap() {
		return geographicLevel;
	}

	public void setGeographicLevelStringMap(Map<String, String> geographicLevel) {
		this.geographicLevel = geographicLevel;
	}
	
	@Override
	public Map<String, List<String>> getEdmAcronym() {
		return getAcronym();
	}

	@Override
	@Deprecated
	public void setEdmAcronym(Map<String, List<String>> edmAcronym) {
		setAcronym(edmAcronym);
	}

	@Override
	public Map<String, String> getEdmOrganizationScope() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Deprecated
	public void setEdmOrganizationScope(Map<String, String> edmOrganizationScope) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Map<String, String> getEdmOrganizationDomain() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Deprecated
	public void setEdmOrganizationDomain(Map<String, String> edmOrganizationDomain) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Map<String, String> getEdmOrganizationSector() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Deprecated
	public void setEdmOrganizationSector(Map<String, String> edmOrganizationSector) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Map<String, String> getEdmGeographicLevel() {
		return getGeographicLevel();
	}

	@Override
	@Deprecated
	public void setEdmGeorgraphicLevel(Map<String, String> edmGeographicLevel) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Map<String, String> getEdmCountry() {
		Map<String, String> newMap = new HashMap<>();
		newMap.put(TMP_KEY, country);
		return newMap;
	}

	@Override
	public void setEdmCountry(Map<String, String> edmCountry) {
		if(edmCountry != null && edmCountry.containsKey(TMP_KEY)) {
		    country = edmCountry.get(TMP_KEY);
		}
	}

	@Override
	@Deprecated
	public void setFoafMbox(List<String> foafMbox) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<String> getFoafMbox() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Deprecated
	public void setFoafPhone(List<String> foafPhone) {
		setPhone(foafPhone);
	}

	@Override
	public List<String> getFoafPhone() {
		return getPhone();
	}

	@Override
	@Deprecated
	public void setDcDescription(Map<String, String> dcDescription) {
		setDescription(dcDescription);
	}

	@Override
	public Map<String, String> getDcDescription() {
		return getDescription();
	}

	@Override
	@Deprecated
	public void setRdfType(String rdfType) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getRdfType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Deprecated
	public void setFoafLogo(String foafLogo) {
		setLogo(foafLogo);
	}

	@Override
	public String getFoafLogo() {
		return getLogo();
	}

	@Override
	@Deprecated
	public void setFoafHomepage(String foafHomePage) {
		setHomepage(foafHomePage);
	}

	@Override
	public String getFoafHomepage() {
		return getHomepage();
	}

	@Override
	@Deprecated
	public void setEdmEuropeanaRole(Map<String, List<String>> edmEuropeanaRole) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Map<String, List<String>> getEdmEuropeanaRole() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setAddress(Address address) {
	    	if(address != null) {
	    	    this.address = address;
	    	    hasAddress = this.address.getAbout();
	    	    streetAddress = this.address.getVcardStreetAddress();
	    	    postalCode = this.address.getVcardPostalCode();
	    	    postBox = this.address.getVcardPostOfficeBox();
	    	    locality = this.address.getVcardLocality();
	    	    //region
	    	    countryName = this.address.getVcardCountryName();
	    	    
	    	}
	}

	@Override
	public Address getAddress() {
	    	if(address == null) {
	    	    address = new BaseAddress();
	    	    address.setAbout(hasAddress);
	    	    address.setVcardStreetAddress(streetAddress);
	    	    address.setVcardPostalCode(postalCode);
	    	    address.setVcardPostOfficeBox(postBox);
	    	    address.setVcardLocality(locality);
	    	    //region
	    	    address.setVcardCountryName(countryName);
	    	}
		return this.address;
	}

	@Override
	@Deprecated
	public void setDcIdentifier(Map<String, List<String>> dcIdentifier) {
	    // TODO Auto-generated method stub
	    
	}

	@Override
	public Map<String, List<String>> getDcIdentifier() {
		//if not available
		if (getIdentifier() == null)
			return null;
		//if not transformed
		if (tmpIdentifier == null) 
			tmpIdentifier = fillTmpMap(Arrays.asList(getIdentifier()));

		return tmpIdentifier;
	}
}
