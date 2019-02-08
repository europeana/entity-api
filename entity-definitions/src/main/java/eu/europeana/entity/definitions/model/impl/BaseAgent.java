package eu.europeana.entity.definitions.model.impl;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;

import eu.europeana.entity.definitions.model.Agent;

public class BaseAgent extends BaseEntity implements Agent, eu.europeana.corelib.definitions.edm.entity.Agent {

	private Date date; // format "YYYY"
	private String[] begin; // format "YYYY-MM-DD"
	private String[] end; // format "YYYY-MM-DD"
	private String[] dateOfBirth; // format "YYYY-MM-DD"
	private String[] dateOfDeath; // format "YYYY"
	private String hasMet;
	private Map<String, String> name;
	private Map<String, List<String>> biographicalInformation;
	private Map<String, List<String>> professionOrOccupation;
	private Map<String, List<String>> placeOfBirth;
	private Map<String, List<String>> placeOfDeath;

	private String dateOfEstablishment; // format "YYYY"
	private String dateOfTermination; // format "YYYY"
	private String gender;
	
	private String[] exactMatch;

	private Map<String, List<String>> tmpBegin;
	private Map<String, List<String>> tmpEnd;
	
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String[] getBeginArray() {
		return begin;
	}

	public void setBegin(String[] begin) {
		this.begin = begin;
	}

	public String[] getEndArray() {
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

	public String[] getExactMatch() {
		return exactMatch;
	}

	public void setExactMatch(String[] exactMatch) {
		this.exactMatch = exactMatch;
	}

	@Override
	public Map<String, List<String>> getPrefLabel() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setPrefLabel(Map<String, List<String>> prefLabel) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setFoafDepiction(String foafDepiction) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getFoafDepiction() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ObjectId getId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setId(ObjectId id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Map<String, List<String>> getBegin() {
		if (tmpBegin == null) {
			if (getBeginArray() != null) {
				fillTmpPeriod(tmpBegin, Arrays.asList(getBeginArray()));
			}
		}

		return tmpBegin;
	}

	@Override
	public Map<String, List<String>> getEnd() {
		if (tmpEnd == null) {
			if (getEndArray() != null) {
				fillTmpPeriod(tmpEnd, Arrays.asList(getEndArray()));
			}			
		}
			
		return tmpEnd;
	}

	/**
	 * This method converts List<String> to Map<String, List<String>> 
	 * @param period stands for begin or end map
	 * @param tmpPeriodArray contains a list of strings for begin or end period
	 */
	private void fillTmpPeriod(Map<String, List<String>> period, List<String> tmpPeriodArray) {
		period = tmpPeriodArray.stream().collect(Collectors.toMap(
			entry -> TMP_KEY, 
			entry -> Collections.singletonList(entry))
		);	
	}
	
	@Override
	@Deprecated
	public void setBegin(Map<String, List<String>> begin) {
		// TODO Auto-generated method stub
		
	}

	@Override
	@Deprecated
	public void setEnd(Map<String, List<String>> end) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setEdmWasPresentAt(String[] edmWasPresentAt) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String[] getEdmWasPresentAt() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setEdmHasMet(Map<String, List<String>> edmHasMet) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Map<String, List<String>> getEdmHasMet() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setEdmIsRelatedTo(Map<String, List<String>> edmIsRelatedTo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Map<String, List<String>> getEdmIsRelatedTo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOwlSameAs(String[] owlSameAs) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String[] getOwlSameAs() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setFoafName(Map<String, List<String>> foafName) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Map<String, List<String>> getFoafName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setDcDate(Map<String, List<String>> dcDate) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Map<String, List<String>> getDcDate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setDcIdentifier(Map<String, List<String>> dcIdentifier) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Map<String, List<String>> getDcIdentifier() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setRdaGr2DateOfBirth(Map<String, List<String>> rdaGr2DateOfBirth) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Map<String, List<String>> getRdaGr2DateOfBirth() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setRdaGr2PlaceOfDeath(Map<String, List<String>> rdaGr2PlaceOfDeath) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Map<String, List<String>> getRdaGr2PlaceOfDeath() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setRdaGr2PlaceOfBirth(Map<String, List<String>> rdaGr2PlaceOfBirth) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Map<String, List<String>> getRdaGr2PlaceOfBirth() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setRdaGr2DateOfDeath(Map<String, List<String>> rdaGr2DateOfDeath) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Map<String, List<String>> getRdaGr2DateOfDeath() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setRdaGr2DateOfEstablishment(Map<String, List<String>> rdaGr2DateOfEstablishment) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Map<String, List<String>> getRdaGr2DateOfEstablishment() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setRdaGr2DateOfTermination(Map<String, List<String>> rdaGr2DateOfTermination) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Map<String, List<String>> getRdaGr2DateOfTermination() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setRdaGr2Gender(Map<String, List<String>> rdaGr2Gender) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Map<String, List<String>> getRdaGr2Gender() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setRdaGr2ProfessionOrOccupation(Map<String, List<String>> rdaGr2ProfessionOrOccupation) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Map<String, List<String>> getRdaGr2ProfessionOrOccupation() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setRdaGr2BiographicalInformation(Map<String, List<String>> rdaGr2BiographicalInformation) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Map<String, List<String>> getRdaGr2BiographicalInformation() {
		// TODO Auto-generated method stub
		return null;
	}

}
