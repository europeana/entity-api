package eu.europeana.entity.definitions.model;

import java.util.Date;

import eu.europeana.entity.definitions.core.CoreConcept;

public interface Concept extends CoreConcept, Entity {

	public void setDefinition(String definition);

	public String getDefinition();

	void setTimestamp(Date timestamp);

	Date getTimestamp();
	
}
