package eu.europeana.entity.definitions.model;

import java.util.Map;

//import eu.europeana.corelib.definitions.edm.entity.ConceptScheme;

public interface ConceptScheme extends Entity{
	
	
	/**
	 * @return skos:ConceptScheme
	 */
	String getType();
	
	
	/**
	 * @return A summary of the general topics shared by the contextual entities that are part of the grouping.
	 */
	public Map<String, String> getDefinition();
	
	public void setDefinition(Map<String, String> definition);

	/**
	 * @return Holds a search request to the Entity API (complete URL pointing to production), 
	 * very similar to the idea behind the curated datasets of the User Set API. 
	 * To reduce complexity on the API, the exhaustive listing could also be expressed as a query.
	 */
	String getIsDefinedBy();
	
	void setIsDefinedBy(String query);
	
	
	/**
	 * @return
	 */
//	boolean isDisabled();
//
//	void setDisabled(boolean disabled);
	

	/**
	 * @return
	 */
	int getTotal();

	void setTotal(int total);
	
	public void setInternalType(String internalType);
				
}