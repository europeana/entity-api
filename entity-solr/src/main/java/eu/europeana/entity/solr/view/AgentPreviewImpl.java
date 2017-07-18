package eu.europeana.entity.solr.view;

import java.util.List;
import java.util.Map;

import eu.europeana.entity.web.model.view.AgentPreview;

public class AgentPreviewImpl extends EntityPreviewImpl implements AgentPreview {

	String dateOfBirth;
	String dateOfDeath;
	Map<String, String> professionOrOccupation;

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
	public void setProfessionOrOccuation(Map<String, String> professionOrOccupation) {
		this.professionOrOccupation = professionOrOccupation;
	}

	@Override
	public Map<String, String> getProfessionOrOccuation() {
		return professionOrOccupation;
	}

}
