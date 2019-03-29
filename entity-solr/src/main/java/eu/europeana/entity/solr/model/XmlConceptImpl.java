package eu.europeana.entity.solr.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName= "skos:Concept")
@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder({"skos:prefLabel", "skos:altLabel", "skos:broader", "skos:narrower", "skos:related", 
	"skos:broadMatch", "skos:narrowMatch", "skos:relatedMatch", "skos:exactMatch", 
	"skos:closeMatch", "skos:note", "skos:notation"})
public class XmlConceptImpl{

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
	
	@JacksonXmlProperty(isAttribute= true, localName = "rdf:about")
	public String getAbout() {
		return concept.getAbout();
	}
    	
	@JacksonXmlElementWrapper(useWrapping=false)
	@JacksonXmlProperty(localName = "skos:broader")
	public List<RdfResource> getBroader() {
	    	return RdfResource.convertToRdfResource(concept.getBroader());
	}
	
	@JacksonXmlElementWrapper(useWrapping=false)
	@JacksonXmlProperty(localName = "skos:narrower")
	public List<RdfResource> getNarrower() {
		return RdfResource.convertToRdfResource(concept.getNarrower());
	}
	
	@JacksonXmlElementWrapper(useWrapping=false)
	@JacksonXmlProperty(localName = "skos:related")
	public List<RdfResource> getRelated() {
		return RdfResource.convertToRdfResource(concept.getRelated());
	}
	
	@JacksonXmlElementWrapper(useWrapping=false)
	@JacksonXmlProperty(localName = "skos:broadMatch")
	public List<RdfResource> getBroadMatch() {
		return RdfResource.convertToRdfResource(concept.getBroadMatch());
	}

	@JacksonXmlElementWrapper(useWrapping=false)
	@JacksonXmlProperty(localName = "skos:narrowMatch")
	public List<RdfResource> getNarrowMatch() {
		return RdfResource.convertToRdfResource(concept.getNarrowMatch());
	}

	@JacksonXmlElementWrapper(useWrapping=false)
	@JacksonXmlProperty(localName = "skos:exactMatch")
	public List<RdfResource> getExactMatch() {
		return RdfResource.convertToRdfResource(concept.getExactMatch());
	}

	@JacksonXmlElementWrapper(useWrapping=false)
	@JacksonXmlProperty(localName = "skos:relatedMatch")
	public List<RdfResource> getRelatedMatch() {
		return RdfResource.convertToRdfResource(concept.getRelatedMatch());
	}

	@JacksonXmlElementWrapper(useWrapping=false)
	@JacksonXmlProperty(localName = "skos:closeMatch")
	public List<RdfResource> getCloseMatch() {
		return RdfResource.convertToRdfResource(concept.getCloseMatch());
	}

	@JacksonXmlElementWrapper(useWrapping=false)
	@JacksonXmlProperty(localName = "skos:notation")
	public List<XmlMultilingualString> getNotation() {
		return XmlMultilingualString.convertToXmlMultilingualString(concept.getNotation());
	}
	
	@JacksonXmlElementWrapper(useWrapping=false)
	@JacksonXmlProperty(localName = "skos:altLabel")
	public List<XmlMultilingualString> getAltLabel() {
		return XmlMultilingualString.convertToXmlMultilingualString(concept.getAltLabel());
	}
	
	@JacksonXmlElementWrapper(useWrapping=false)
	@JacksonXmlProperty(localName = "skos:note")
	public List<XmlMultilingualString> getNote() {
		return XmlMultilingualString.convertToXmlMultilingualString(concept.getNote());
	}
	
	@JacksonXmlElementWrapper(useWrapping=false)
	@JacksonXmlProperty(localName = "skos:prefLabel")
	public List<XmlMultilingualString> getPrefLabel() {		
		return XmlMultilingualString.convertToXmlMultilingualString(concept.getPrefLabel());
	}
}
