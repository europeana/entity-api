package eu.europeana.entity.definitions.model.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import eu.europeana.entity.definitions.model.Agent;

public class BaseAgent extends BaseEntity implements Agent {

	private String context;
	private Date date; // format "YYYY"
	private String identifier;
	private String hasPart;
	private String isPartOf;
	private Date begin; // format "YYYY-MM-DD" 
	private Date end; // format "YYYY-MM-DD"
	private String hasMet;
	private String isRelatedTo;
	private Map<String,String> name;
	private Map<String,String> biographicalInformation;
	private Date dateOfBirth; // format "YYYY-MM-DD" 
	private List<Date> dateOfDeath; // format "YYYY"
//	private String dateOfDeath; // format "YYYY"
//	private Date dateOfDeath; // format "YYYY-MM-DD"
	private Map<String,String> placeOfBirth;
	private Map<String,String> placeOfDeath;
	private Date dateOfEstablishment; // format "YYYY"
	private Date dateOfTermination; // format "YYYY"
	private String gender;
	private Map<String,String> professionOrOccupation;
		

	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getIdentifier() {
		return identifier;
	}
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
	public String getHasPart() {
		return hasPart;
	}
	public void setHasPart(String hasPart) {
		this.hasPart = hasPart;
	}
	public String getIsPartOf() {
		return isPartOf;
	}
	public void setIsPartOf(String isPartOf) {
		this.isPartOf = isPartOf;
	}
	public Date getBegin() {
		return begin;
	}
	public void setBegin(Date begin) {
		this.begin = begin;
	}
	public Date getEnd() {
		return end;
	}
	public void setEnd(Date end) {
		this.end = end;
	}
	public String getHasMet() {
		return hasMet;
	}
	public void setHasMet(String hasMet) {
		this.hasMet = hasMet;
	}
	public String getIsRelatedTo() {
		return isRelatedTo;
	}
	public void setIsRelatedTo(String isRelatedTo) {
		this.isRelatedTo = isRelatedTo;
	}
	public Map<String, String> getName() {
		return name;
	}
	public void setName(Map<String, String> name) {
		this.name = name;
	}
	public Map<String, String> getBiographicalInformation() {
		return biographicalInformation;
	}
	public void setBiographicalInformation(Map<String, String> biographicalInformation) {
		this.biographicalInformation = biographicalInformation;
	}
	public Date getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public List<Date> getDateOfDeath() {
		return dateOfDeath;
	}
//	public String getDateOfDeath() {
//		return dateOfDeath;
//	}
//	public Date getDateOfDeath() {
//		return dateOfDeath;
//	}
	public void setDateOfDeath(List<Date> dateOfDeath) {
		this.dateOfDeath = dateOfDeath;
	}
//	public void setDateOfDeath(String dateOfDeath) {
//		this.dateOfDeath = dateOfDeath;
//	}
//	public void setDateOfDeath(Date dateOfDeath) {
//		this.dateOfDeath = dateOfDeath;
//	}
	public Map<String, String> getPlaceOfBirth() {
		return placeOfBirth;
	}
	public void setPlaceOfBirth(Map<String, String> placeOfBirth) {
		this.placeOfBirth = placeOfBirth;
	}
	public Map<String, String> getPlaceOfDeath() {
		return placeOfDeath;
	}
	public void setPlaceOfDeath(Map<String, String> placeOfDeath) {
		this.placeOfDeath = placeOfDeath;
	}
	public Date getDateOfEstablishment() {
		return dateOfEstablishment;
	}
	public void setDateOfEstablishment(Date dateOfEstablishment) {
		this.dateOfEstablishment = dateOfEstablishment;
	}
	public Date getDateOfTermination() {
		return dateOfTermination;
	}
	public void setDateOfTermination(Date dateOfTermination) {
		this.dateOfTermination = dateOfTermination;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public Map<String, String> getProfessionOrOccupation() {
		return professionOrOccupation;
	}
	public void setProfessionOrOccupation(Map<String, String> professionOrOccupation) {
		this.professionOrOccupation = professionOrOccupation;
	}

}
