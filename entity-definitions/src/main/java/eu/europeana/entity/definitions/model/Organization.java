package eu.europeana.entity.definitions.model;

import java.util.Date;

public interface Organization extends Agent {
    
	public String[] getAcronym();

	public String getSimpleAcronym();
	
	public void setAcronym(String[] acronym);

	public void setSimpleAcronym(String simpleAcronym);
	
	public String[] getLabel();

	public void setLabel(String[] label);

	public Date getTimestamp();

	public void setTimestamp(Date timestamp);

	public String getHasAddress();

	public void setHasAddress(String hasAddress);

	public String[] getPayload();

	public void setPayload(String[] payload);

	public String getPostalCode();

	public void setPostalCode(String postalCode);

	public String getPostBox();

	public void setPostBox(String postBox);

	public String getCountry();

	public void setCountry(String country);

	public String getCity();

	public void setCity(String city);

	public String getStreet();

	public void setStreet(String street);

	public String getLevel();

	public void setLevel(String level);

	public String getScope();

	public void setScope(String scope);

	public String getSector();

	public void setSector(String sector);

	public String getDomain();

	public void setDomain(String domain);

	public String[] getRole();

	public void setRole(String[] role);

	public String[] getHomepage();

	public void setHomepage(String[] homepage);

	public String[] getLogo();

	public void setLogo(String[] logo);

	public String[] getDcIdentifier();

	public void setDcIdentifier(String[] dcIdentifier);

}
