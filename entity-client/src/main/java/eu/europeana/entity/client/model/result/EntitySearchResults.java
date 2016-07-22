package eu.europeana.entity.client.model.result;

import java.util.List;

import eu.europeana.entity.definitions.model.Concept;
import eu.europeana.entity.definitions.model.Entity;


public class EntitySearchResults extends AbstractEntityApiResponse{

	private String itemsCount;
	private String totalResults;
	private List<Entity> items;
	private String json;
	
	public String getItemsCount() {
		return itemsCount;
	}
	public void setItemsCount(String itemsCount) {
		this.itemsCount = itemsCount;
	}
	public String getTotalResults() {
		return totalResults;
	}
	public void setTotalResults(String totalResults) {
		this.totalResults = totalResults;
	}
	public List<Entity> getItems() {
		return items;
	}
	public void setItems(List<Entity> entities) {
		this.items = entities;
	}
	public String getJson() {
		return json;
	}
	public void setJson(String json) {
		this.json = json;
	}
	
	
}
