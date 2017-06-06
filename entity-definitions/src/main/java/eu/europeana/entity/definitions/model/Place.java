package eu.europeana.entity.definitions.model;

import eu.europeana.api.commons.definitions.model.Entity;

public interface Place extends Entity {

	void setAltitude(Float altitude);

	Float getAltitude();

	void setLongitude(Float longitude);

	Float getLongitude();

	void setLatitude(Float latitude);

	Float getLatitude();

	void setIsNextInSequence(String[] isNextInSequence);

	String[] getIsNextInSequence();

	// methods common for some entities
	public String[] getHasPart();

	public void setHasPart(String[] hasPart);

	public String[] getIsPartOf();

	public void setIsPartOf(String[] isPartOf);
}
