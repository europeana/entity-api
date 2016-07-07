package eu.europeana.entity.definitions.model;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface Agent extends Concept {

	public String getContext();
	public void setContext(String context);
	public Date getDate();
	public void setDate(Date date);
	public String getIdentifier();
	public void setIdentifier(String identifier);
	public String getHasPart();
	public void setHasPart(String hasPart);
	public String getIsPartOf();
	public void setIsPartOf(String isPartOf);
	public List<Date> getBegin();
	public void setBegin(List<Date> begin);
	public List<Date> getEnd();
	public void setEnd(List<Date> end);
	public String getHasMet();
	public void setHasMet(String hasMet);
	public String getIsRelatedTo();
	public void setIsRelatedTo(String isRelatedTo);
	public Map<String, String> getName();
	public void setName(Map<String, String> name);
	public List<String> getBiographicalInformation();
	public void setBiographicalInformation(List<String> biographicalInformation);
	public List<Date> getDateOfBirth();
	public void setDateOfBirth(List<Date> dateOfBirth);
	public List<Date> getDateOfDeath();
	public void setDateOfDeath(List<Date> dateOfDeath);
	public Map<String, String> getPlaceOfBirth();
	public void setPlaceOfBirth(Map<String, String> placeOfBirth);
	public Map<String, String> getPlaceOfDeath();
	public void setPlaceOfDeath(Map<String, String> placeOfDeath);
	public Date getDateOfEstablishment();
	public void setDateOfEstablishment(Date dateOfEstablishment);
	public Date getDateOfTermination();
	public void setDateOfTermination(Date dateOfTermination);
	public String getGender();
	public void setGender(String gender);
	public Map<String, String> getProfessionOrOccupation();
	public void setProfessionOrOccupation(Map<String, String> professionOrOccupation);	
	
	public void setRdfAbout(String rdfAbout);
	public void setWikipediaClicks(int wikipediaClicks);
	public void setEuropeanaDocCount(int europeanaDocCount);
	public void setDerivedScore(float derivedScore);
	public void setText(List<String> text);
	public String getRdfAbout();
	public int getWikipediaClicks();
	public int getEuropeanaDocCount();
	public float getDerivedScore();
	public List<String> getText();
	
}
