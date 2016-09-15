package eu.europeana.entity.web.model.view;

import java.util.List;

public interface AgentPreview extends EntityPreview{

	void setDateOfDeath(String dateOfDeath);

	String getDateOfDeath();

	void setDateOfBirth(String dateOfBirth);

	String getDateOfBirth();

	void setProfessionOrOccuation(List<String> professionOrOccupation);

	List<String> getProfessionOrOccuation();

}
