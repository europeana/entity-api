package eu.europeana.entity.definitions.model;

import java.util.List;
import java.util.Map;

//import eu.europeana.corelib.definitions.edm.entity.ConceptScheme;

public interface ConceptScheme {
	
	/**
	 * @return The unique identifier of the grouping, defined under "http://data.europeana.eu/scheme/".
	 */
	public String getConceptSchemeId();
	
	void setConceptSchemeId(String id);
	
	/**
	 * @return skos:ConceptScheme
	 */
	String getType();
	
	void setType(String type);
	
	/**
	 * @return Name given to the grouping.
	 */
	public Map<String, String> getPrefLabel();
	
	public void setPrefLabel(Map<String, String> prefLabel);

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
	 * @return A reference to a Linked Data resource (e.g. Wikidata) 
	 * that can be used to enrich the metadata for the grouping.
	 */
	public String getSameAs();

	public void setSameAs(String sameAs);
	
	/**
	 * @return The unique identifier of the grouping(s) which gather this entity.
	 */
//	public String[] getInScheme();
	public List<String> getInScheme();

//	public void setInScheme(String[] inScheme);	
	public void setInScheme(List<String> inScheme);	
	
	/**
	 * @return
	 */
	public String getContext();
	
	public void setContext(String context);
		
}