package eu.europeana.entity.definitions.model;

import java.util.Date;
import java.util.Map;

import eu.europeana.corelib.definitions.edm.entity.ContextualClass;

//import eu.europeana.corelib.definitions.edm.entity.ContextualClass;

public interface Entity extends ContextualClass {

	public String[] getIdentifier();

	public void setIdentifier(String[] identifier);

	public String[] getIsRelatedTo();

	public void setIsRelatedTo(String[] isRelatedTo);

	public String getEntityId();

	public void setEntityId(String enitityId);

	public String getInternalType();

	public String[] getSameAs();

	public String getDepiction();
	
	public void setDepiction(String depiction);


	/**
	 * Retrieves the preferable label for a contextual class (language,value)
	 * 
	 * @return A Map<String, String> for the preferable labels of a contextual class 
	 *         (one per language)
	 */
	public Map<String, String> getPrefLabelStringMap();
	
	/**
	 * @return
	 */
	Date getCreated();

	void setCreated(Date created);

	/**
	 * @return
	 */
	Date getModified();

	void setModified(Date modified);
}
