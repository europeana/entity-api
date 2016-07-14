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
	private List<String> isPartOf;
	private List<Date> begin; // format "YYYY-MM-DD" 
	private List<Date> end; // format "YYYY-MM-DD"
	private String hasMet;
	private String isRelatedTo;
	private Map<String,String> name;
	private List<String> biographicalInformation;
	private List<Date> dateOfBirth; // format "YYYY-MM-DD" 
	private List<Date> dateOfDeath; // format "YYYY"
	private Map<String,String> placeOfBirth;
	private Map<String,String> placeOfDeath;
	private Date dateOfEstablishment; // format "YYYY"
	private Date dateOfTermination; // format "YYYY"
	private String gender;
	private Map<String,String> professionOrOccupation;
		
	private String rdfAbout;
	private int wikipediaClicks; 
	private int europeanaDocCount;
	private float derivedScore;
	private List<String> text;

	
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
	public List<String>  getIsPartOf() {
		return isPartOf;
	}
	public void setIsPartOf(List<String>  isPartOf) {
		this.isPartOf = isPartOf;
	}
	public List<Date> getBegin() {
		return begin;
	}
	public void setBegin(List<Date> begin) {
		this.begin = begin;
	}
	public List<Date> getEnd() {
		return end;
	}
	public void setEnd(List<Date> end) {
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
	public List<String> getBiographicalInformation() {
		return biographicalInformation;
	}
	public void setBiographicalInformation(List<String> biographicalInformation) {
		this.biographicalInformation = biographicalInformation;
	}
	public List<Date> getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(List<Date> dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public List<Date> getDateOfDeath() {
		return dateOfDeath;
	}
	public void setDateOfDeath(List<Date> dateOfDeath) {
		this.dateOfDeath = dateOfDeath;
	}
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
	public String getRdfAbout() {
		return rdfAbout;
	}
	public void setRdfAbout(String rdfAbout) {
		this.rdfAbout = rdfAbout;
	}
	public int getWikipediaClicks() {
		return wikipediaClicks;
	}
	public void setWikipediaClicks(int wikipediaClicks) {
		this.wikipediaClicks = wikipediaClicks;
	}
	public int getEuropeanaDocCount() {
		return europeanaDocCount;
	}
	public void setEuropeanaDocCount(int europeanaDocCount) {
		this.europeanaDocCount = europeanaDocCount;
	}
	public float getDerivedScore() {
		return derivedScore;
	}
	public void setDerivedScore(float derivedScore) {
		this.derivedScore = derivedScore;
	}
	public List<String> getText() {
		return text;
	}
	public void setText(List<String> text) {
		this.text = text;
	}

}
