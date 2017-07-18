package eu.europeana.entity.web.model.view;

import java.util.Map;

public interface AgentPreview extends EntityPreview{

	void setDateOfDeath(String dateOfDeath);

	String getDateOfDeath();

	void setDateOfBirth(String dateOfBirth);

	String getDateOfBirth();

	void setProfessionOrOccuation(Map<String, String> values);

	Map<String, String> getProfessionOrOccuation();

}
