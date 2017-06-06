package eu.europeana.entity.definitions.model;

import eu.europeana.api.commons.definitions.model.Entity;

public interface Timespan extends Entity{

	public String[] getBegin();

	public void setBegin(String[] begin);

	public String[] getEnd();

	public void setEnd(String[] end);

}
