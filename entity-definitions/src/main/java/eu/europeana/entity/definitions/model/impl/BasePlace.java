package eu.europeana.entity.definitions.model.impl;

import eu.europeana.entity.definitions.model.Place;

public class BasePlace extends BaseEntity implements Place {

	private String[] isNextInSequence;
	private Float latitude, longitude, altitude;
	private String[] exactMatch;

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
}
