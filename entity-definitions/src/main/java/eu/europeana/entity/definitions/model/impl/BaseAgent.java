package eu.europeana.entity.definitions.model.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import eu.europeana.entity.definitions.model.Agent;

public class BaseAgent extends BaseEntity implements Agent {

	private String context;
	private Date date; // format "YYYY"
	private String[] begin; // format "YYYY-MM-DD" 
	private String[] end; // format "YYYY-MM-DD"
	private String[] dateOfBirth; // format "YYYY-MM-DD" 
	private String[] dateOfDeath; // format "YYYY"
	private String hasMet;
	private Map<String,String> name;
	private Map<String,List<String>> biographicalInformation;
	private Map<String,List<String>> professionOrOccupation;
	private Map<String,List<String>> placeOfBirth;
	private Map<String,List<String>> placeOfDeath;
	
	private String dateOfEstablishment; // format "YYYY"
	private String dateOfTermination; // format "YYYY"
	private String gender;
	
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
	public String[] getBegin() {
		return begin;
	}
	public void setBegin(String[] begin) {
		this.begin = begin;
	}
	public String[] getEnd() {
		return end;
	}
	public void setEnd(String[] end) {
		this.end = end;
	}
	public String getHasMet() {
		return hasMet;
	}
	public void setHasMet(String hasMet) {
		this.hasMet = hasMet;
	}
	
	public Map<String, String> getName() {
		return name;
	}
	public void setName(Map<String, String> name) {
		this.name = name;
	}
	public Map<String, List<String>> getBiographicalInformation() {
		return biographicalInformation;
	}
	public void setBiographicalInformation(Map<String, List<String>> biographicalInformation) {
		this.biographicalInformation = biographicalInformation;
	}
	public String[] getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(String[] dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public String[] getDateOfDeath() {
		return dateOfDeath;
	}
	public void setDateOfDeath(String[] dateOfDeath) {
		this.dateOfDeath = dateOfDeath;
	}
	public Map<String, List<String>> getPlaceOfBirth() {
		return placeOfBirth;
	}
	public void setPlaceOfBirth(Map<String, List<String>> placeOfBirth) {
		this.placeOfBirth = placeOfBirth;
	}
	public Map<String, List<String>> getPlaceOfDeath() {
		return placeOfDeath;
	}
	public void setPlaceOfDeath(Map<String, List<String>> placeOfDeath) {
		this.placeOfDeath = placeOfDeath;
	}
	public String getDateOfEstablishment() {
		return dateOfEstablishment;
	}
	public void setDateOfEstablishment(String dateOfEstablishment) {
		this.dateOfEstablishment = dateOfEstablishment;
	}
	public String getDateOfTermination() {
		return dateOfTermination;
	}
	public void setDateOfTermination(String dateOfTermination) {
		this.dateOfTermination = dateOfTermination;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public Map<String, List<String>> getProfessionOrOccupation() {
		return professionOrOccupation;
	}
	public void setProfessionOrOccupation(Map<String, List<String>> professionOrOccupation) {
		this.professionOrOccupation = professionOrOccupation;
	}

}
