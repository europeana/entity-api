package eu.europeana.entity.definitions.model;

import java.util.Map;

public interface ResourcePreview {
	
	public String getHttpUri();
	
	public void setHttpUri(String httpUri);
	
	public Map<String, String> getPrefLabel();
	
	public void setPrefLabel(Map<String, String> prefLabel);

}
