package eu.europeana.entity.definitions.model;

import eu.europeana.corelib.definitions.edm.entity.ContextualClass;

public interface Entity extends ContextualClass {

	public String getContext();

	public void setContext(String context);

	public String[] getIdentifier();

	public void setIdentifier(String[] identifier);

	public String[] getIsRelatedTo();

	public void setIsRelatedTo(String[] isRelatedTo);

	public String getEntityId();

	public void setEntityId(String enitityId);

	public String getInternalType();

	public String[] getSameAs();

}
