package eu.europeana.entity.definitions.model.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import eu.europeana.entity.definitions.model.Place;

public class BasePlace extends BaseEntity implements Place, eu.europeana.corelib.definitions.edm.entity.Place {

	private String[] isNextInSequence;
	private Float latitude, longitude, altitude;
	private String[] exactMatch;

	private Map<String, List<String>> tmpIsPartOf;	
	private Map<String, List<String>> tmpHasPart;	
	
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
	@Deprecated
	public void setIsPartOf(Map<String, List<String>> isPartOf) {
		// TODO Auto-generated method stub
		
	}

	@Override
	@Deprecated
	public void setPosition(Map<String, Float> position) {
		// TODO Auto-generated method stub
		
	}

	@Override
	@Deprecated
	public Map<String, Float> getPosition() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Deprecated
	public void setDcTermsHasPart(Map<String, List<String>> dcTermsHasPart) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Map<String, List<String>> getDcTermsHasPart() {
		//if not available
		if (getHasPart() == null)
			return null;
		//if not transformed
		if (tmpHasPart == null) 
			tmpHasPart = fillTmpMap(Arrays.asList(getHasPart()));
			
		return tmpHasPart;
	}

	@Override
	public Map<String, List<String>> getIsPartOf() {
		//if not available
		if (getIsPartOfArray() == null)
			return null;
		//if not transformed
		if (tmpIsPartOf == null) 
			tmpIsPartOf = fillTmpMap(Arrays.asList(getIsPartOfArray()));

		return tmpIsPartOf;
	}
	
}
