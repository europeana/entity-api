package eu.europeana.entity.definitions.model.impl;

import java.util.Date;

import eu.europeana.entity.definitions.model.Organization;

/**
 * This class defines base organization type of an entity.
 * @author GrafR
 *
 */
public class BaseOrganization extends BaseAgent implements Organization {

	private String[] acronym;
	private String simpleAcronym;
	private String[] label;
	private String[] role;
	private String[] homepage;
	private String[] logo;
	private String[] dcIdentifier;
	private String[] payload;
	private String hasAddress;
	private String postalCode;
	private String postBox;
	private String country;
	private String city;
	private String street;
	private String level;
	private String scope;
	private String sector;
	private String domain;
	private Date timestamp;

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public String[] getAcronym() {
		return acronym;
	}

	public void setAcronym(String[] acronym) {
		this.acronym = acronym;
	}

	public void setSimpleAcronym(String simpleAcronym) {
		this.simpleAcronym = simpleAcronym;
	}

	public String getSimpleAcronym() {
		return simpleAcronym;
	}
	
	public String[] getLabel() {
		return label;
	}

	public void setLabel(String[] label) {
		this.label = label;
	}

	public String getHasAddress() {
		return hasAddress;
	}

	public void setHasAddress(String hasAddress) {
		this.hasAddress = hasAddress;
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

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public String getSector() {
		return sector;
	}

	public void setSector(String sector) {
		this.sector = sector;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String[] getRole() {
		return role;
	}

	public void setRole(String[] role) {
		this.role = role;
	}

	public String[] getHomepage() {
		return homepage;
	}

	public void setHomepage(String[] homepage) {
		this.homepage = homepage;
	}

	public String[] getLogo() {
		return logo;
	}

	public void setLogo(String[] logo) {
		this.logo = logo;
	}

	public String[] getDcIdentifier() {
		return dcIdentifier;
	}

	public void setDcIdentifier(String[] dcIdentifier) {
		this.dcIdentifier = dcIdentifier;
	}
	
	public void setPayload(String[] payload) {
		this.payload = payload;
	}

	public String[] getPayload() {
		return payload;
	}

}
