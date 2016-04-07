package eu.europeana.entity.web.model.view;

import eu.europeana.entity.definitions.model.search.result.IdBean;

public interface EntityPreview extends IdBean {

	public String getPreferredLabel();
	
	public String getLanguage();
	//String getEntityType();
	
	public void setEntityId(String entityId);
	
	public void setPreferredLabel(String entityId);
}
