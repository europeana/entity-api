package eu.europeana.entity.solr.view;

import java.util.Date;

import eu.europeana.entity.web.model.view.EntityPreview;

public class EntityPreviewImpl implements EntityPreview{

	String entityId;
	String type; 
	String language;
	String preferredLabel;
	String country;
	String timeRange;
	Date birthDate;
	Date deathDate;
	
	
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

	@Override
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

	@Override
	public String getType() {
		return type;
	}

	@Override
	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String getCountry() {
		return country;
	}

	@Override
	public void setCountry(String country) {
		this.country = country;
	}

	@Override
	public String getTimeRange() {
		return timeRange;
	}

	@Override
	public void setTimeRange(String timeRange) {
		this.timeRange = timeRange;
	}

	@Override
	public Date getBirthDate() {
		return birthDate;
	}

	@Override
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	@Override
	public Date getDeathDate() {
		return deathDate;
	}

	@Override
	public void setDeathDate(Date deathDate) {
		this.deathDate = deathDate;
	}
}
