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
	public String[] getBegin();
	public void setBegin(String[] begin);
	public String[] getEnd();
	public void setEnd(String[] end);
	public String getHasMet();
	public void setHasMet(String hasMet);
	public Map<String, String> getName();
	public void setName(Map<String, String> name);
	public Map<String, List<String>> getBiographicalInformation();
	public void setBiographicalInformation(Map<String, List<String>> biographicalInformation);
	public Map<String, List<String>> getProfessionOrOccupation();
	public void setProfessionOrOccupation(Map<String, List<String>> professionOrOccupation);	
	
	public String[] getDateOfBirth();
	public void setDateOfBirth(String[] dateOfBirth);
	public String[] getDateOfDeath();
	public void setDateOfDeath(String[] dateOfDeath);
	public Map<String, List<String>> getPlaceOfBirth();
	public void setPlaceOfBirth(Map<String, List<String>> placeOfBirth);
	public Map<String, List<String>> getPlaceOfDeath();
	public void setPlaceOfDeath(Map<String, List<String>> placeOfDeath);
	public String getDateOfEstablishment();
	public void setDateOfEstablishment(String dateOfEstablishment);
	public String getDateOfTermination();
	public void setDateOfTermination(String dateOfTermination);
	public String getGender();
	public void setGender(String gender);
	
//	public void setText(List<String> text);

	public int getWikipediaClicks();
	public int getEuropeanaDocCount();
	public float getDerivedScore();
//	public List<String> getText();
	
}
