package eu.europeana.entity.definitions.model;

public interface Place extends Entity {

	void setAltitude(String altitude);

	String getAltitude();

	void setLongitude(String longitude);

	String getLongitude();

	void setLatitude(String latitude);

	String getLatitude();

	void setIsNextInSequence(String[] isNextInSequence);

	String[] getIsNextInSequence();

	// methods common for some entities
	public String[] getHasPart();

	public void setHasPart(String[] hasPart);

	public String[] getIsPartOf();

	public void setIsPartOf(String[] isPartOf);
}
