package eu.europeana.entity.solr.view;

public class PlacePreviewImpl extends EntityPreviewImpl implements eu.europeana.entity.web.model.view.PlacePreview {

	String country;
	String[] isPartOf;
	
	@Override
	public String getCountry() {
		return country;
	}

	@Override
	public void setCountry(String country) {
		this.country = country;
	}

	@Override
	public String[] getIsPartOf() {
		return isPartOf;
	}

	@Override
	public void setIsPartOf(String[] isPartOf) {
		this.isPartOf = isPartOf;
	}
}
