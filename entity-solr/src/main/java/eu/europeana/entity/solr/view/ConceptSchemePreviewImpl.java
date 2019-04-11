package eu.europeana.entity.solr.view;

import java.util.Date;
import java.util.List;
import java.util.Map;

import eu.europeana.entity.web.model.view.AgentPreview;
import eu.europeana.entity.web.model.view.ConceptSchemePreview;

public class ConceptSchemePreviewImpl extends EntityPreviewImpl implements ConceptSchemePreview {

	private String conceptSchemeId;
	private String type;
	private Map<String, String> prefLabel;	
	private Map<String, String> definition;	
	private String isDefinedBy;
	private String sameAs;
	private List<String> inScheme;

	// web context
	private String context;

    // Indicates whether the set is disabled in database
    private boolean disabled;

	// A non-negative integer specifying the total number of items that are contained within a grouping
	private int total = 0;    

	// The time at which the Set was created by the user. The value must be a literal expressed 
	// as xsd:dateTime with the UTC timezone expressed as "Z".
	private Date created;

	// The time at which the Set was modified, after creation. The value must be a literal expressed 
	// as xsd:dateTime with the UTC timezone expressed as "Z".
	private Date modified;

	public String getConceptSchemeId() {
		return this.conceptSchemeId;
	}
	
	public void setConceptSchemeId(String id) {
		this.conceptSchemeId = id;
	}
	
	public String getType() {
		return this.type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public Map<String, String> getPrefLabel() {
		return this.prefLabel;
	}
	
	public void setPrefLabel(Map<String, String> prefLabel) {
		this.prefLabel = prefLabel;
	}

	public Map<String, String> getDefinition() {
		return this.definition;
	}
	
	public void setDefinition(Map<String, String> definition) {
		this.definition = definition;
	}

	public String getIsDefinedBy() {
		return this.isDefinedBy;
	}
	
	public void setIsDefinedBy(String isDefinedBy) {
		this.isDefinedBy = isDefinedBy;
	}

	public String getSameAs() {
		return this.sameAs;
	}

	public void setSameAs(String sameAs) {
		this.sameAs = sameAs;
	}
	
	public List<String> getInScheme() {
		return this.inScheme;
	}

	public void setInScheme(List<String> inScheme) {
		this.inScheme = inScheme;
	}

	public String getContext() {
		return context;
	}
	
	public void setContext(String context) {
		this.context = context;
	}	
		
	@Override
	public boolean isDisabled() {
		return disabled;
	}

	@Override
	public void setDisabled(boolean disabled) {
		this.disabled = disabled;		
	}
	
	@Override
	public Date getCreated() {
		return created;
	}

	@Override
	public void setCreated(Date created) {
		this.created = created;
	}

	@Override
	public Date getModified() {
		return modified;
	}

	@Override
	public void setModified(Date modified) {
		this.modified = modified;
	}

	@Override
	public int getTotal() {
		return total;
	}
	
	@Override
	public void setTotal(int total) {
		this.total = total;
	}

}
