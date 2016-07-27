package eu.europeana.entity.web.model.view;

public interface AgentPreview extends EntityPreview{

	void setDateOfDeath(String deathDate);

	String getDateOfDeath();

	void setDateOfBirth(String birthDate);

	String getDateOfBirth();

	void setProfessionOrOccuation(String role);

	String getProfessionOrOccuation();

}
