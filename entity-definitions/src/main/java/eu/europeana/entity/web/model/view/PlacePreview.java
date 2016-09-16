package eu.europeana.entity.web.model.view;

public interface PlacePreview extends EntityPreview{

	void setCountry(String country);

	String getCountry();

	void setIsPartOf(String[] isPartOf);

	String[] getIsPartOf();
}
