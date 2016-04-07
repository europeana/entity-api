package eu.europeana.entity.solr.view;

import java.util.Date;

import eu.europeana.entity.web.model.view.EntityPreview;

public class EntityPreviewImpl implements EntityPreview{

	String entityId;
	String language;
	String preferredLabel;
	
	@Override
	public Date getTimestampUpdated() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getEntityId() {
		return entityId;
	}

	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getPreferredLabel() {
		return preferredLabel;
	}

	public void setPreferredLabel(String preferredLabel) {
		this.preferredLabel = preferredLabel;
	}

	@Override
	public String toString() {
		return "entityId: " + getEntityId() + "\nprefferedLabel: " + getPreferredLabel();
	}
	
	@Override
	public int hashCode() {
		return getEntityId().hashCode();
	}
}
