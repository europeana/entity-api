package eu.europeana.entity.web.model.view;

import java.util.List;

import eu.europeana.entity.definitions.model.ResourcePreview;

public interface PlacePreview extends EntityPreview{

// Not supported anymore
//	void setCountry(String country);
//
//	String getCountry();

	void setIsPartOf(List<ResourcePreview> isPartOf);

	List<ResourcePreview> getIsPartOf();
}
