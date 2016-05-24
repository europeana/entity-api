package eu.europeana.entity.web.model.view;

import java.util.Date;

import eu.europeana.entity.definitions.model.search.result.IdBean;

public interface EntityPreview extends IdBean {

	public String getPreferredLabel();
	
	public String getLanguage();
	//String getEntityType();
	
	public void setEntityId(String entityId);
	
	public void setPreferredLabel(String entityId);

	void setDeathDate(Date deathDate);

	Date getDeathDate();

	void setBirthDate(Date birthDate);

	Date getBirthDate();

	void setTimeRange(String timeRange);

	String getTimeRange();

	void setCountry(String country);

	String getCountry();

	void setType(String type);

	String getType();

	void setLanguage(String language);

	Date getTimestampUpdated();
}
