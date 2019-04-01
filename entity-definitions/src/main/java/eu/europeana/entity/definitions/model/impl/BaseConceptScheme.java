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
public class BaseConceptScheme implements ConceptScheme {
	
//	private String id;
	private String conceptSchemeId;
	private String type;
	private Map<String, String> prefLabel;	
	private Map<String, String> definition;	
	private String isDefinedBy;
	private String sameAs;
//	private String[] inScheme;
	private List<String> inScheme;

	// web context
	private String context;
	
	
	public String getConceptSchemeId() {
//		return this.id;
		return this.conceptSchemeId;
	}
	
	public void setConceptSchemeId(String id) {
//		this.id = id;
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
	
//	public String[] getInScheme() {
	public List<String> getInScheme() {
		return this.inScheme;
	}

//	public void setInScheme(String[] inScheme) {
	public void setInScheme(List<String> inScheme) {
		this.inScheme = inScheme;
	}

	public String getContext() {
		return context;
	}
	
	public void setContext(String context) {
		this.context = context;
	}	
		
//	@Override
//	public String[] getDcTitle() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public void setDcTitle(String[] dcTitle) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public String[] getDcCreator() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public void setDcCreator(String[] dcCreator) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public String[] getHasTopConceptOf() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public void setHasTopConceptOf(String[] hasTopConceptOf) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public String[] getNote() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public void setNote(String[] note) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public ObjectId getId() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public void setId(ObjectId id) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public String getAbout() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public void setAbout(String about) {
//		// TODO Auto-generated method stub
//		
//	}
}