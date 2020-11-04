package org.entity.contentfull.model;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "title", "entityID", "description", "featuredImage", "sections", "theme", "slug",
		"entityType", "relatedCollectionCards"})
@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
public class ContentfullPage {

	private String name;
	private String identifier;
	private String description;
	private Map<String, Object> featuredImage;
	private Map<String, Object> sections;
	private Map<String, Object> theme;
	private Map<String, Object> slug;
	private Map<String, Object> entityType;
	private Map<String, Object> relatedCollectionCards;

	@JsonProperty("name")
	public String getName() {
		return name;
	}
	public void setName(String nameParam) {
		this.name = nameParam;
	}
	
	@JsonProperty("identifier")
	public String getIdentifier() {
		return identifier;
	}
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
	
	@JsonProperty("description")
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	@JsonProperty("Featured image")
	public Map<String, Object> getFeaturedImage() {
		return featuredImage;
	}
	public void setFeaturedImage(Map<String, Object> featuredImage) {
		this.featuredImage = featuredImage;
	}
	
	@JsonProperty("Sections")
	public Map<String, Object> getSections() {
		return sections;
	}
	public void setSections(Map<String, Object> sections) {
		this.sections = sections;
	}
	
	@JsonProperty("Theme")
	public Map<String, Object> getTheme() {
		return theme;
	}
	public void setTheme(Map<String, Object> theme) {
		this.theme = theme;
	}
	
	@JsonProperty("Slug")
	public Map<String, Object> getSlug() {
		return slug;
	}
	public void setSlug(Map<String, Object> slug) {
		this.slug = slug;
	}
	
	@JsonProperty("Entity type")
	public Map<String, Object> getEntityType() {
		return entityType;
	}
	public void setEntityType(Map<String, Object> entityType) {
		this.entityType = entityType;
	}
	
	@JsonProperty("Related collection cards")
	public Map<String, Object> getRelatedCollectionCards() {
		return relatedCollectionCards;
	}
	public void setRelatedCollectionCards(Map<String, Object> relatedCollectionCards) {
		this.relatedCollectionCards = relatedCollectionCards;
	}

}
