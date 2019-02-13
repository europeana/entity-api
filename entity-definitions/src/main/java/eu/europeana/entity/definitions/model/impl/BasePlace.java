package eu.europeana.entity.definitions.model.impl;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;

import eu.europeana.entity.definitions.model.Place;

public class BasePlace extends BaseEntity implements Place, eu.europeana.corelib.definitions.edm.entity.Place {

	private String[] isNextInSequence;
	private Float latitude, longitude, altitude;
	private String[] exactMatch;

	private Map<String, List<String>> tmpIsPartOf;	
	private Map<String, List<String>> tmpPrefLabel;
	
	@Override
	public String[] getIsNextInSequence() {
		return isNextInSequence;
	}

	@Override
	public void setIsNextInSequence(String[] isNextInSequence) {
		this.isNextInSequence = isNextInSequence;
	}

	@Override
	public Float getLatitude() {
		return latitude;
	}

	@Override
	public void setLatitude(Float latitude) {
		this.latitude = latitude;
	}

	@Override
	public Float getLongitude() {
		return longitude;
	}

	@Override
	public void setLongitude(Float longitude) {
		this.longitude = longitude;
	}

	@Override
	public Float getAltitude() {
		return altitude;
	}

	@Override
	public void setAltitude(Float altitude) {
		this.altitude = altitude;
	}

	public String[] getExactMatch() {
		return exactMatch;
	}

	public void setExactMatch(String[] exactMatch) {
		this.exactMatch = exactMatch;
	}

	@Override
	public Map<String, List<String>> getPrefLabel() {
		if (tmpPrefLabel == null)
			fillTmpPrefLabel(getPrefLabelStringMap());

		return tmpPrefLabel;
	}

	/**
	 * This method converts Map<String, String> to Map<String, List<String>> 
	 * @param prefLabelStringMap
	 */
	private void fillTmpPrefLabel(Map<String, String> prefLabelStringMap) {
		tmpPrefLabel = prefLabelStringMap.entrySet().stream().collect(Collectors.toMap(
				entry -> entry.getKey(), 
				entry -> Collections.singletonList(entry.getValue()))
		);	
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
		return getDepiction();
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
	@Deprecated
	public void setIsPartOf(Map<String, List<String>> isPartOf) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setPosition(Map<String, Float> position) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Map<String, Float> getPosition() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setDcTermsHasPart(Map<String, List<String>> dcTermsHasPart) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Map<String, List<String>> getDcTermsHasPart() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOwlSameAs(String[] owlSameAs) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String[] getOwlSameAs() {
		return getSameAs();
	}

	@Override
	public Map<String, List<String>> getIsPartOf() {
		if (tmpIsPartOf == null) {
			if (getIsPartOfArray() != null) {
				fillTmpIsPartOf(Arrays.asList(getIsPartOfArray()));
			}
		}

		return tmpIsPartOf;
	}
	
	/**
	 * This method converts List<String> to Map<String, List<String>> 
	 * @param prefLabelStringMap
	 */
	private void fillTmpIsPartOf(List<String> tmpIsPartOfArray) {
		tmpIsPartOf = tmpIsPartOfArray.stream().collect(Collectors.toMap(
			entry -> TMP_KEY, 
			entry -> Collections.singletonList(entry))
		);	
	}
	
}
