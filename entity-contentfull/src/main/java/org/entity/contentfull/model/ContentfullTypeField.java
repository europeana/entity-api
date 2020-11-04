package org.entity.contentfull.model;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "id", "name", "type", "localized", "required", "validations", "disabled",
	"omitted", "items", "linkType"})
@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
public class ContentfullTypeField {

	private String id;
	private String name;
	private String type;
	private boolean localized;
	private boolean required;
	private List<Map<String, Object>> validations;
	private boolean disabled;
	private boolean omitted;
	private Map<String, Object> items;
	private String linkType;
		
	@JsonProperty("id")
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	@JsonProperty("name")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@JsonProperty("type")
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	@JsonProperty("localized")
	public boolean isLocalized() {
		return localized;
	}
	public void setLocalized(boolean localized) {
		this.localized = localized;
	}
	
	@JsonProperty("required")
	public boolean isRequired() {
		return required;
	}
	public void setRequired(boolean required) {
		this.required = required;
	}
	
	@JsonProperty("validations")
	public List<Map<String, Object>> getValidations() {
		return validations;
	}
	public void setValidations(List<Map<String, Object>> validations) {
		this.validations = validations;
	}
	
	@JsonProperty("disabled")
	public boolean isDisabled() {
		return disabled;
	}
	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}
	
	@JsonProperty("omitted")
	public boolean isOmitted() {
		return omitted;
	}
	public void setOmitted(boolean omitted) {
		this.omitted = omitted;
	}
	
	@JsonProperty("items")
	public Map<String, Object> getItems() {
		return items;
	}
	public void setItems(Map<String, Object> items) {
		this.items = items;
	}
	
	@JsonProperty("linkType")
	public String getLinkType() {
		return linkType;
	}
	public void setLinkType(String linkType) {
		this.linkType = linkType;
	}

	

}
