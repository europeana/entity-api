package eu.europeana.entity.web.model.view;

import java.util.List;
import java.util.Map;

import eu.europeana.entity.definitions.model.search.result.IdBean;
import eu.europeana.entity.definitions.model.vocabulary.EntityTypes;

public interface EntityPreview extends IdBean {

	//functional fields
	public void setEntityId(String entityId);
	public void setPreferredLabel(Map<String, String> prefLabel);
	public Map<String, String> getPreferredLabel();
	
	void setLanguage(String language);

	public String getLanguage();
	
	void setType(String type);

	String getType();

	void setMatchedTerm(String matchedTerm);

	String getMatchedTerm();

	void setSearchedTerm(String searchedTerm);

	String getSearchedTerm();

	void setEntityType(EntityTypes entityType);

	EntityTypes getEntityType();

	void setHiddenLabel(Map<String, List<String>> hiddenLabel);

	Map<String, List<String>> getHiddenLabel();

	public String getDepiction();
	
	public void setDepiction(String depiction);
	
}
