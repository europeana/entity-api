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
    	XmlConstants.XML_SKOS_EXACT_MATCH, XmlConstants.XML_SKOS_IN_SCHEMA})
public class XmlConceptImpl {

    	@JsonIgnore
    	private Concept concept;
    
    	public XmlConceptImpl(Concept concept) {
    	    this.concept = concept;
    	}
	
	@JacksonXmlProperty(isAttribute= true, localName = XmlConstants.XML_RDF_ABOUT)
	public String getAbout() {
		return concept.getAbout();
	}
    	
	@JacksonXmlProperty(localName = XmlConstants.XML_FOAF_DEPICTION)
	public RdfResource getDepiction() {
	    	if(concept.getDepiction() == null)
	    	    return null;
		return new RdfResource(concept.getDepiction());
	}
	
	@JacksonXmlElementWrapper(useWrapping=false)
	@JacksonXmlProperty(localName = XmlConstants.XML_SKOS_BROADER)
	public List<RdfResource> getBroader() {
	    	return RdfXmlUtils.convertToRdfResource(concept.getBroader());
	}
	
	@JacksonXmlElementWrapper(useWrapping=false)
	@JacksonXmlProperty(localName = XmlConstants.XML_SKOS_NARROWER)
	public List<RdfResource> getNarrower() {
		return RdfXmlUtils.convertToRdfResource(concept.getNarrower());
	}
	
	@JacksonXmlElementWrapper(useWrapping=false)
	@JacksonXmlProperty(localName = XmlConstants.XML_SKOS_RELATED)
	public List<RdfResource> getRelated() {
		return RdfXmlUtils.convertToRdfResource(concept.getRelated());
	}
	
	@JacksonXmlElementWrapper(useWrapping=false)
	@JacksonXmlProperty(localName = XmlConstants.XML_SKOS_BROAD_MATCH)
	public List<RdfResource> getBroadMatch() {
		return RdfXmlUtils.convertToRdfResource(concept.getBroadMatch());
	}

	@JacksonXmlElementWrapper(useWrapping=false)
	@JacksonXmlProperty(localName = XmlConstants.XML_SKOS_NARROW_MATCH)
	public List<RdfResource> getNarrowMatch() {
		return RdfXmlUtils.convertToRdfResource(concept.getNarrowMatch());
	}

	@JacksonXmlElementWrapper(useWrapping=false)
	@JacksonXmlProperty(localName = XmlConstants.XML_SKOS_EXACT_MATCH)
	public List<RdfResource> getExactMatch() {
		return RdfXmlUtils.convertToRdfResource(concept.getExactMatch());
	}

	@JacksonXmlElementWrapper(useWrapping=false)
	@JacksonXmlProperty(localName = XmlConstants.XML_SKOS_RELATED_MATCH)
	public List<RdfResource> getRelatedMatch() {
		return RdfXmlUtils.convertToRdfResource(concept.getRelatedMatch());
	}

	@JacksonXmlElementWrapper(useWrapping=false)
	@JacksonXmlProperty(localName = XmlConstants.XML_SKOS_CLOSE_MATCH)
	public List<RdfResource> getCloseMatch() {
		return RdfXmlUtils.convertToRdfResource(concept.getCloseMatch());
	}

	@JacksonXmlElementWrapper(useWrapping=false)
	@JacksonXmlProperty(localName = XmlConstants.XML_SKOS_NOTATION)
	public List<XmlMultilingualString> getNotation() {
		return RdfXmlUtils.convertToXmlMultilingualString(concept.getNotation());
	}
	
	@JacksonXmlElementWrapper(useWrapping=false)
	@JacksonXmlProperty(localName = XmlConstants.XML_SKOS_HIDDEN_LABEL)
	public List<XmlMultilingualString> getHiddenLabel() {
		return RdfXmlUtils.convertToXmlMultilingualString(concept.getHiddenLabel());
	}
	
	@JacksonXmlElementWrapper(useWrapping=false)
	@JacksonXmlProperty(localName = XmlConstants.XML_SKOS_ALT_LABEL)
	public List<XmlMultilingualString> getAltLabel() {
		return RdfXmlUtils.convertToXmlMultilingualString(concept.getAltLabel());
	}
	
	@JacksonXmlElementWrapper(useWrapping=false)
	@JacksonXmlProperty(localName = XmlConstants.XML_SKOS_NOTE)
	public List<XmlMultilingualString> getNote() {
		return RdfXmlUtils.convertToXmlMultilingualString(concept.getNote());
	}
	
	@JacksonXmlElementWrapper(useWrapping=false)
	@JacksonXmlProperty(localName = XmlConstants.XML_SKOS_PREF_LABEL)
	public List<XmlMultilingualString> getPrefLabel() {		
		return RdfXmlUtils.convertToXmlMultilingualString(concept.getPrefLabel());
	}
	
	@JacksonXmlElementWrapper(useWrapping=false)
	@JacksonXmlProperty(localName = XmlConstants.XML_SKOS_IN_SCHEMA)
	public List<RdfResource> getInScheme() {		
		return RdfXmlUtils.convertToRdfResource(concept.getInScheme());
	}
}
