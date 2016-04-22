package eu.europeana.entity.client.model.result;

import java.util.List;

import eu.europeana.entity.definitions.model.Concept;


public class EntitySearchResults extends AbstractEntityApiResponse{

	private String itemsCount;
	private String totalResults;
	private List<Concept> items;
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
	public List<Concept> getItems() {
		return items;
	}
	public void setItems(List<Concept> annotations) {
		this.items = annotations;
	}
	public String getJson() {
		return json;
	}
	public void setJson(String json) {
		this.json = json;
	}
	
	
}
