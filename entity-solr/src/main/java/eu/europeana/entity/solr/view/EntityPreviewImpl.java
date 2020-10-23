package eu.europeana.entity.solr.view;

import java.util.Date;
import java.util.List;
import java.util.Map;

import eu.europeana.entity.definitions.model.vocabulary.EntityTypes;
import eu.europeana.entity.web.model.view.EntityPreview;

public class EntityPreviewImpl implements EntityPreview{

	String entityId;
	String type; 
	String searchedTerm;
	String matchedTerm;
	String language;
	Map<String, String> preferredLabel;
	Map<String, List<String>> hiddenLabel;
	Map<String, List<String>> altLabel;
	String timeSpanStart;
	String timeSpanEnd;
	EntityTypes entityType;
	String depiction;
	
        // isShownBy fields
	private String isShownById;
	private String isShownBySource;
	private String isShownByThumbnail;
	
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
	
	public Map<String, String> getPreferredLabel() {
		return preferredLabel;
	}

	public void setPreferredLabel(Map<String, String> preferredLabel) {
		this.preferredLabel = preferredLabel;
	}

	@Override
	public String toString() {
		return "entityId: " + getEntityId() + "\npreferredLabel: " + getPreferredLabel();
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

	@Override
	public Map<String, List<String>> getHiddenLabel() {
		return hiddenLabel;
	}

	@Override
	public void setHiddenLabel(Map<String, List<String>> hiddenLabel) {
		this.hiddenLabel = hiddenLabel;
	}

	@Override
	public Map<String, List<String>> getAltLabel() {
	    return altLabel;
	}

	@Override
	public void setAltLabel(Map<String, List<String>> altlabel) {
	    this.altLabel = altlabel;
	}

	@Override
	public String getDepiction() {
		return depiction;
	}

	public void setDepiction(String depiction) {
		this.depiction = depiction;
	}
	
	public String getIsShownById() {
		return isShownById;
	}
	
	public void setIsShownById(String isShownById) {
		this.isShownById = isShownById;
	}

	public String getIsShownBySource() {
		return isShownBySource;
	}
	
	public void setIsShownBySource(String isShownBySource) {
		this.isShownBySource = isShownBySource;
	}

	public String getIsShownByThumbnail() {
		return isShownByThumbnail;
	}
	
	public void setIsShownByThumbnail(String isShownByThumbnail) {
		this.isShownByThumbnail = isShownByThumbnail;
	}
	
}
