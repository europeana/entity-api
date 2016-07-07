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
	public Date getBegin();
	public void setBegin(Date begin);
	public Date getEnd();
	public void setEnd(Date end);
	public String getHasMet();
	public void setHasMet(String hasMet);
	public String getIsRelatedTo();
	public void setIsRelatedTo(String isRelatedTo);
	public Map<String, String> getName();
	public void setName(Map<String, String> name);
	public Map<String, String> getBiographicalInformation();
	public void setBiographicalInformation(Map<String, String> biographicalInformation);
	public Date getDateOfBirth();
	public void setDateOfBirth(Date dateOfBirth);
	public List<Date> getDateOfDeath();
	public void setDateOfDeath(List<Date> dateOfDeath);
//	public String getDateOfDeath();
//	public void setDateOfDeath(String dateOfDeath);
//	public Date getDateOfDeath();
//	public void setDateOfDeath(Date dateOfDeath);
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
	

}
