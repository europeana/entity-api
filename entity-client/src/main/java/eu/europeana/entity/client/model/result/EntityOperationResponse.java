package eu.europeana.entity.client.model.result;

import eu.europeana.entity.definitions.model.Concept;

public class EntityOperationResponse extends AbstractEntityApiResponse{

	private Concept entity;
	
	private String json;
	
	public Concept getEntity() {
		return entity;
	}
	public void setEntity(Concept entity) {
		this.entity = entity;
	}
	public String getJson() {
		return json;
	}
	public void setJson(String json) {
		this.json = json;
	}
	
	
}
