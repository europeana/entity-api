package eu.europeana.entity.solr.view;

import java.util.Date;

import eu.europeana.entity.web.model.view.EntityPreview;

public class EntityPreviewImpl implements EntityPreview{

	String entityId;
	String type; 
	String searchedTerm;
	String matchedTerm;
	String language;
	String preferredLabel;
	String country;
	String timeSpanStart;
	String timeSpanEnd;
	Date birthDate;
	Date deathDate;
	String role;
	String latitude;
	String longitude;
	
	
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

	public String getTimeSpanStart() {
		return timeSpanStart;
	}

	public void setTimeSpanStart(String timeSpanStart) {
		this.timeSpanStart = timeSpanStart;
	}

	public String getTimeSpanEnd() {
		return timeSpanEnd;
	}

	public void setTimeSpanEnd(String timeSpanEnd) {
		this.timeSpanEnd = timeSpanEnd;
	}

	@Override
	public String getMatchedTerm() {
		return matchedTerm;
	}

	@Override
	public void setMatchedTerm(String matchedTerm) {
		this.matchedTerm = matchedTerm;
	}

	@Override
	public String getSearchedTerm() {
		return searchedTerm;
	}

	@Override
	public void setSearchedTerm(String searchedTerm) {
		this.searchedTerm = searchedTerm;
	}

	@Override
	public String getRole() {
		return role;
	}

	@Override
	public void setRole(String role) {
		this.role = role;
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
}
