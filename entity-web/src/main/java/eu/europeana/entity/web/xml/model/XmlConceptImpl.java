package eu.europeana.entity.web.xml.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import eu.europeana.entity.definitions.model.Concept;

@JacksonXmlRootElement(localName= XmlConstants.XML_SKOS_CONCEPT)
@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder({ XmlConstants.XML_FOAF_DEPICTION, XmlConstants.XML_SKOS_PREF_LABEL, XmlConstants.XML_SKOS_ALT_LABEL, XmlConstants.XML_SKOS_HIDDEN_LABEL,
    	XmlConstants.XML_SKOS_NOTE, XmlConstants.XML_SKOS_NOTATION, XmlConstants.XML_SKOS_BROADER, XmlConstants.XML_SKOS_NARROWER, XmlConstants.XML_SKOS_RELATED,
    	XmlConstants.XML_SKOS_BROAD_MATCH, XmlConstants.XML_SKOS_NARROW_MATCH, XmlConstants.XML_SKOS_RELATED_MATCH, XmlConstants.XML_SKOS_CLOSE_MATCH,
    	XmlConstants.XML_SKOS_EXACT_MATCH, XmlConstants.XML_SKOS_IN_SCHEMA, XmlConstants.XML_OWL_SAME_AS, XmlConstants.XML_ORE_IS_AGGREGATED_BY})
public class XmlConceptImpl extends XmlBaseEntityImpl {

    	public XmlConceptImpl(Concept concept) {
    	    	super(concept);
    	}
	
	@JacksonXmlElementWrapper(useWrapping=false)
	@JacksonXmlProperty(localName = XmlConstants.XML_SKOS_BROADER)
	public List<RdfResource> getBroader() {
	    	return RdfXmlUtils.convertToRdfResource(getConcept().getBroader());
	}
	
	@JacksonXmlElementWrapper(useWrapping=false)
	@JacksonXmlProperty(localName = XmlConstants.XML_SKOS_NARROWER)
	public List<RdfResource> getNarrower() {
		return RdfXmlUtils.convertToRdfResource(getConcept().getNarrower());
	}
	
	@JacksonXmlElementWrapper(useWrapping=false)
	@JacksonXmlProperty(localName = XmlConstants.XML_SKOS_RELATED)
	public List<RdfResource> getRelated() {
		return RdfXmlUtils.convertToRdfResource(getConcept().getRelated());
	}
	
	@JacksonXmlElementWrapper(useWrapping=false)
	@JacksonXmlProperty(localName = XmlConstants.XML_SKOS_BROAD_MATCH)
	public List<RdfResource> getBroadMatch() {
		return RdfXmlUtils.convertToRdfResource(getConcept().getBroadMatch());
	}

	@JacksonXmlElementWrapper(useWrapping=false)
	@JacksonXmlProperty(localName = XmlConstants.XML_SKOS_NARROW_MATCH)
	public List<RdfResource> getNarrowMatch() {
		return RdfXmlUtils.convertToRdfResource(getConcept().getNarrowMatch());
	}

	@JacksonXmlElementWrapper(useWrapping=false)
	@JacksonXmlProperty(localName = XmlConstants.XML_SKOS_EXACT_MATCH)
	public List<RdfResource> getExactMatch() {
		return RdfXmlUtils.convertToRdfResource(getConcept().getExactMatch());
	}

	@JacksonXmlElementWrapper(useWrapping=false)
	@JacksonXmlProperty(localName = XmlConstants.XML_SKOS_RELATED_MATCH)
	public List<RdfResource> getRelatedMatch() {
		return RdfXmlUtils.convertToRdfResource(getConcept().getRelatedMatch());
	}

	@JacksonXmlElementWrapper(useWrapping=false)
	@JacksonXmlProperty(localName = XmlConstants.XML_SKOS_CLOSE_MATCH)
	public List<RdfResource> getCloseMatch() {
		return RdfXmlUtils.convertToRdfResource(getConcept().getCloseMatch());
	}

	@JacksonXmlElementWrapper(useWrapping=false)
	@JacksonXmlProperty(localName = XmlConstants.XML_SKOS_NOTATION)
	public List<XmlMultilingualString> getNotation() {
		return RdfXmlUtils.convertToXmlMultilingualString(getConcept().getNotation());
	}
	
	@JacksonXmlElementWrapper(useWrapping=false)
	@JacksonXmlProperty(localName = XmlConstants.XML_SKOS_HIDDEN_LABEL)
	public List<XmlMultilingualString> getHiddenLabel() {
		return RdfXmlUtils.convertToXmlMultilingualString(getConcept().getHiddenLabel());
	}
	
	@JsonIgnore
	private Concept getConcept() {
	    return (Concept)entity;
	}
	
	@JacksonXmlElementWrapper(useWrapping=false)
	@JacksonXmlProperty(localName = XmlConstants.XML_SKOS_NOTE)
	public List<XmlMultilingualString> getNote() {
		return RdfXmlUtils.convertToXmlMultilingualString(getConcept().getNote());
	}
	
	@JacksonXmlElementWrapper(useWrapping=false)
	@JacksonXmlProperty(localName = XmlConstants.XML_SKOS_IN_SCHEMA)
	public List<RdfResource> getInScheme() {		
		return RdfXmlUtils.convertToRdfResource(getConcept().getInScheme());
	}
}
