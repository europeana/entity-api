package eu.europeana.entity.solr.view;

import java.util.Date;

import eu.europeana.entity.definitions.model.vocabulary.EntityTypes;
import eu.europeana.entity.web.model.view.EntityPreview;

public class EntityPreviewImpl implements EntityPreview{

	String entityId;
	String type; 
	String searchedTerm;
	String matchedTerm;
	String language;
	String preferredLabel;
	String timeSpanStart;
	String timeSpanEnd;
	EntityTypes entityType;
	
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
	public EntityTypes getEntityType() {
		return entityType;
	}

	@Override
	public void setEntityType(EntityTypes entityType) {
		this.entityType = entityType;
	}

	
}
