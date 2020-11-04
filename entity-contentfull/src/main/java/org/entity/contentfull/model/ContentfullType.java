package org.entity.contentfull.model;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "name", "description", "displayField", "fields", "sys"})
@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
public class ContentfullType {

	private String name;
	private String description;
	private String displayField;
	private List<ContentfullTypeField> fields;
	private Map<String, Object> sys;
	
	@JsonProperty("name")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@JsonProperty("description")
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	@JsonProperty("displayField")
	public String getDisplayField() {
		return displayField;
	}
	public void setDisplayField(String displayField) {
		this.displayField = displayField;
	}
	
	@JsonProperty("fields")
	public List<ContentfullTypeField> getFields() {
		return fields;
	}
	public void setFields(List<ContentfullTypeField> fields) {
		this.fields = fields;
	}
	
	@JsonProperty("sys")
	public Map<String, Object> getSys() {
		return sys;
	}
	public void setSys(Map<String, Object> sys) {
		this.sys = sys;
	}

}
