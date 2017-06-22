package eu.europeana.entity.definitions.model;

import java.util.Date;

public interface Concept extends eu.europeana.corelib.definitions.edm.entity.Concept, Entity {

	public void setDefinition(String definition);

	public String getDefinition();

	void setTimestamp(Date timestamp);

	Date getTimestamp();
	
}
