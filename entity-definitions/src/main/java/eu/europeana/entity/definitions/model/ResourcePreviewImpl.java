package eu.europeana.entity.definitions.model;

import java.util.Map;

public class ResourcePreviewImpl implements ResourcePreview {

	private String httpUri;
	private Map<String, String> prefLabel;
	
	public String getHttpUri() {
		return httpUri;
	}
	public void setHttpUri(String httpUri) {
		this.httpUri = httpUri;
	}
	public Map<String, String> getPrefLabel() {
		return prefLabel;
	}
	public void setPrefLabel(Map<String, String> prefLabel) {
		this.prefLabel = prefLabel;
	}
	
}
