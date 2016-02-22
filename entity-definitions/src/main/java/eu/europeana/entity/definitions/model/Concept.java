package eu.europeana.entity.definitions.model;

import java.util.Date;

public interface Concept extends eu.europeana.corelib.definitions.edm.entity.Concept {

	public void setDefinition(String definition);

	public String getDefinition();
	
	public String getEntityId();
	
	public void setEntityId(String enitityId);

	public String getInternalType();
	
	public String[] getSameAs();

	void setTimestamp(Date timestamp);

	Date getTimestamp();
	

}
