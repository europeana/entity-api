package eu.europeana.entity.definitions.model.impl;

import java.util.List;
import java.util.Map;

import eu.europeana.entity.definitions.model.ConceptScheme;


/**
 * Europeana ConceptScheme API Specification
 * 
 * @author GrafR
 *
 */
public class BaseConceptScheme extends BaseEntity implements ConceptScheme {
	
	private String isDefinedBy;
	// A non-negative integer specifying the total number of items that are contained within a grouping
	private int total = 0;
	private Map<String, String> definition;

	
	@Override
	public String getIsDefinedBy() {
		return this.isDefinedBy;
	}
	
	@Override
	public void setIsDefinedBy(String isDefinedBy) {
		this.isDefinedBy = isDefinedBy;
	}

	public Map<String, String> getDefinition() {
		return definition;
	}

	public void setDefinition(Map<String, String> definition) {
		this.definition = definition;
	}

		
//	@Override
//	public boolean isDisabled() {
//		return disabled;
//	}

//	@Override
//	public void setDisabled(boolean disabled) {
//		this.disabled = disabled;		
//	}
	
	@Override
	public int getTotal() {
		return total;
	}
	
	@Override
	public void setTotal(int total) {
		this.total = total;
	}

	@Override
	public String getType() {
	    return getInternalType();
	}

	
}