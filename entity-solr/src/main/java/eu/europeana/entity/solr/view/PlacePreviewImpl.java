package eu.europeana.entity.solr.view;

import java.util.List;

import eu.europeana.entity.definitions.model.ResourcePreview;

public class PlacePreviewImpl extends EntityPreviewImpl implements eu.europeana.entity.web.model.view.PlacePreview {

	List<ResourcePreview> isPartOf;
	
	@Override
	public void setIsPartOf(List<ResourcePreview> isPartOf) {
		this.isPartOf = isPartOf;
		
	}

	@Override
	public List<ResourcePreview> getIsPartOf() {
		return isPartOf;
	}
	
}
