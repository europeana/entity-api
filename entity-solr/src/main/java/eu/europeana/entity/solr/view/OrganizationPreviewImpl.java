package eu.europeana.entity.solr.view;

import eu.europeana.entity.web.model.view.OrganizationPreview;

public class OrganizationPreviewImpl extends AgentPreviewImpl implements OrganizationPreview {

	/** this field is used for acronym as a simple string in payload by suggest queries */
	private String simpleAcronym;

	private String[] acronym;
	private String[] label;
	private String[] role;
	private String[] homepage;
	private String[] logo;
	private String[] dcIdentifier;
	private String payload;
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

	@Override
	public void setSimpleAcronym(String simpleAcronym) {
		this.simpleAcronym = simpleAcronym;

	}

	@Override
	public String getSimpleAcronym() {
		return simpleAcronym;
	}
	
	@Override
	public String[] getAcronym() {
		return acronym;
	}

	@Override
	public void setAcronym(String[] acronym) {
		this.acronym = acronym;
	}

	@Override
	public String[] getLabel() {
		return label;
	}

	@Override
	public void setLabel(String[] label) {
		this.label = label;
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
	public String getPayload() {
		return payload;
	}

	@Override
	public void setPayload(String payload) {
		this.payload = payload;
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
	public String getCity() {
		return city;
	}

	@Override
	public void setCity(String city) {
		this.city = city;
	}

	@Override
	public String getStreet() {
		return street;
	}

	@Override
	public void setStreet(String street) {
		this.street = street;
	}

	@Override
	public String getLevel() {
		return level;
	}

	@Override
	public void setLevel(String level) {
		this.level = level;
	}

	@Override
	public String getScope() {
		return scope;
	}

	@Override
	public void setScope(String scope) {
		this.scope = scope;
	}

	@Override
	public String getSector() {
		return sector;
	}

	@Override
	public void setSector(String sector) {
		this.sector = sector;
	}

	@Override
	public String getDomain() {
		return domain;
	}

	@Override
	public void setDomain(String domain) {
		this.domain = domain;
	}

	@Override
	public String[] getRole() {
		return role;
	}

	@Override
	public void setRole(String[] role) {
		this.role = role;
	}

	@Override
	public String[] getHomepage() {
		return homepage;
	}

	@Override
	public void setHomepage(String[] homepage) {
		this.homepage = homepage;
	}

	@Override
	public String[] getLogo() {
		return logo;
	}

	@Override
	public void setLogo(String[] logo) {
		this.logo = logo;
	}

	@Override
	public String[] getDcIdentifier() {
		return dcIdentifier;
	}

	@Override
	public void setDcIdentifier(String[] dcIdentifier) {
		this.dcIdentifier = dcIdentifier;
	}
	
}
