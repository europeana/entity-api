package eu.europeana.entity.web.xml.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName= XmlConstants.XML_SKOS_CONCEPT)
@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder({XmlConstants.XML_SKOS_PREF_LABEL, XmlConstants.XML_SKOS_ALT_LABEL, XmlConstants.XML_SKOS_BROADER, XmlConstants.XML_SKOS_NARROWER,
    	XmlConstants.XML_SKOS_RELATED, XmlConstants.XML_SKOS_BROAD_MATCH, XmlConstants.XML_SKOS_NARROW_MATCH, XmlConstants.XML_SKOS_RELATED_MATCH,
    	XmlConstants.XML_SKOS_EXACT_MATCH, XmlConstants.XML_SKOS_CLOSE_MATCH, XmlConstants.XML_SKOS_NOTE, XmlConstants.XML_SKOS_NOTATION})
public class XmlConceptImpl implements XmlBase {

    	@JsonIgnore
    	private eu.europeana.entity.definitions.model.impl.BaseConcept concept;
    
    	public XmlConceptImpl(eu.europeana.entity.definitions.model.impl.BaseConcept concept) {
    	    this.concept = concept;
    	}
    
    	@JsonIgnore
    	public String addingAdditionalXmlString(String xml) {
    	    StringBuilder strBuilder = new StringBuilder();
    	    strBuilder.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n" + 
    	    	" <rdf:RDF xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\"\r\n" +  
    	    	"          xmlns:skos=\"http://www.w3.org/2004/02/skos/core#\"\r\n >");
    	    strBuilder.append(xml);
    	    strBuilder.append("</rdf:RDF>");
    	    return strBuilder.toString();
    	}
	
	@JacksonXmlProperty(isAttribute= true, localName = XmlConstants.XML_RDF_ABOUT)
	public String getAbout() {
		return concept.getAbout();
	}
    	
	@JacksonXmlElementWrapper(useWrapping=false)
	@JacksonXmlProperty(localName = XmlConstants.XML_SKOS_BROADER)
	public List<RdfResource> getBroader() {
	    	return RdfResource.convertToRdfResource(concept.getBroader());
	}
	
	@JacksonXmlElementWrapper(useWrapping=false)
	@JacksonXmlProperty(localName = XmlConstants.XML_SKOS_NARROWER)
	public List<RdfResource> getNarrower() {
		return RdfResource.convertToRdfResource(concept.getNarrower());
	}
	
	@JacksonXmlElementWrapper(useWrapping=false)
	@JacksonXmlProperty(localName = XmlConstants.XML_SKOS_RELATED)
	public List<RdfResource> getRelated() {
		return RdfResource.convertToRdfResource(concept.getRelated());
	}
	
	@JacksonXmlElementWrapper(useWrapping=false)
	@JacksonXmlProperty(localName = XmlConstants.XML_SKOS_BROAD_MATCH)
	public List<RdfResource> getBroadMatch() {
		return RdfResource.convertToRdfResource(concept.getBroadMatch());
	}

	@JacksonXmlElementWrapper(useWrapping=false)
	@JacksonXmlProperty(localName = XmlConstants.XML_SKOS_NARROW_MATCH)
	public List<RdfResource> getNarrowMatch() {
		return RdfResource.convertToRdfResource(concept.getNarrowMatch());
	}

	@JacksonXmlElementWrapper(useWrapping=false)
	@JacksonXmlProperty(localName = XmlConstants.XML_SKOS_EXACT_MATCH)
	public List<RdfResource> getExactMatch() {
		return RdfResource.convertToRdfResource(concept.getExactMatch());
	}

	@JacksonXmlElementWrapper(useWrapping=false)
	@JacksonXmlProperty(localName = XmlConstants.XML_SKOS_RELATED_MATCH)
	public List<RdfResource> getRelatedMatch() {
		return RdfResource.convertToRdfResource(concept.getRelatedMatch());
	}

	@JacksonXmlElementWrapper(useWrapping=false)
	@JacksonXmlProperty(localName = XmlConstants.XML_SKOS_CLOSE_MATCH)
	public List<RdfResource> getCloseMatch() {
		return RdfResource.convertToRdfResource(concept.getCloseMatch());
	}

	@JacksonXmlElementWrapper(useWrapping=false)
	@JacksonXmlProperty(localName = XmlConstants.XML_SKOS_NOTATION)
	public List<XmlMultilingualString> getNotation() {
		return XmlMultilingualString.convertToXmlMultilingualString(concept.getNotation());
	}
	
	@JacksonXmlElementWrapper(useWrapping=false)
	@JacksonXmlProperty(localName = XmlConstants.XML_SKOS_ALT_LABEL)
	public List<XmlMultilingualString> getAltLabel() {
		return XmlMultilingualString.convertToXmlMultilingualString(concept.getAltLabel());
	}
	
	@JacksonXmlElementWrapper(useWrapping=false)
	@JacksonXmlProperty(localName = XmlConstants.XML_SKOS_NOTE)
	public List<XmlMultilingualString> getNote() {
		return XmlMultilingualString.convertToXmlMultilingualString(concept.getNote());
	}
	
	@JacksonXmlElementWrapper(useWrapping=false)
	@JacksonXmlProperty(localName = XmlConstants.XML_SKOS_PREF_LABEL)
	public List<XmlMultilingualString> getPrefLabel() {		
		return XmlMultilingualString.convertToXmlMultilingualString(concept.getPrefLabel());
	}
}
