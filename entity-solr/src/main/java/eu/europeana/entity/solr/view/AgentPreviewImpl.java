package eu.europeana.entity.solr.view;

import eu.europeana.entity.web.model.view.AgentPreview;

public class AgentPreviewImpl extends EntityPreviewImpl implements AgentPreview {

	String dateOfBirth;
	String dateOfDeath;
	String professionOrOccupation;

	@Override
	public void setDateOfDeath(String deathDate) {
		this.dateOfDeath = deathDate;

	}

	@Override
	public String getDateOfDeath() {
		return dateOfDeath;
	}

	@Override
	public void setDateOfBirth(String birthDate) {
		this.dateOfBirth = birthDate;

	}

	@Override
	public String getDateOfBirth() {
		return dateOfBirth;
	}

	@Override
	public void setProfessionOrOccuation(String role) {
		this.professionOrOccupation = role;
	}

	@Override
	public String getProfessionOrOccuation() {
		return professionOrOccupation;
	}

}
