package eu.europeana.entity.definitions.model.impl;

import eu.europeana.entity.definitions.model.Place;

public class BasePlace extends BaseEntity implements Place {

	private String[] isNextInSequence;
	private String latitude, longitude, altitude;

	@Override
	public String[] getIsNextInSequence() {
		return isNextInSequence;
	}

	@Override
	public void setIsNextInSequence(String[] isNextInSequence) {
		this.isNextInSequence = isNextInSequence;
	}

	@Override
	public String getLatitude() {
		return latitude;
	}

	@Override
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	@Override
	public String getLongitude() {
		return longitude;
	}

	@Override
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	@Override
	public String getAltitude() {
		return altitude;
	}

	@Override
	public void setAltitude(String altitude) {
		this.altitude = altitude;
	}
}
