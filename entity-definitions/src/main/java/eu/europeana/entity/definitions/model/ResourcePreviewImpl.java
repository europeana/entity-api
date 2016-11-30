package eu.europeana.entity.definitions.model;

public class ResourcePreviewImpl implements ResourcePreview {

	private String httpUri;
	private String prefLabel;
	
	public String getHttpUri() {
		return httpUri;
	}
	public void setHttpUri(String httpUri) {
		this.httpUri = httpUri;
	}
	public String getPrefLabel() {
		return prefLabel;
	}
	public void setPrefLabel(String prefLabel) {
		this.prefLabel = prefLabel;
	}
	
}
