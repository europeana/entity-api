package eu.europeana.entity.definitions.model;

import java.io.Serializable;

public interface ConceptSchemeId extends Serializable {

	String NOT_INITIALIZED_LONG_ID = "-1";
	
	/**
	 * unanbiguous identifier of the resource for a given provider
	 * @return
	 */
	public void setSequenceNumber(String sequenceNr);

	public String getSequenceNumber();

	/**
	 * collection  e.g. 'conceptScheme'
	 * @return
	 */
	public String getCollection();

	public void setCollection(String collection);
	
	
}
