package eu.europeana.entity.solr.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName= "edm:Place")
@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder({"wgs84_pos:lat", "wgs84_pos:long", "wgs84_pos:alt", "skos:prefLabel",
	"skos:altLabel", "skos:note", "dcterms:hasPart", "dcterms:isPartOf",
	"edm:isNextInSequence", "owl:sameAs"})
public class XmlPlaceImpl {
    
    	@JsonIgnore
    	private eu.europeana.entity.definitions.model.impl.BasePlace place;
    
    	public XmlPlaceImpl(eu.europeana.entity.definitions.model.impl.BasePlace place) {
    	    	this.place = place;
    	}
    	
    	@JsonIgnore
	public String addingAdditionalXmlString(String xml) {
	    StringBuilder strBuilder = new StringBuilder();
	    strBuilder.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n" + 
	    	" <rdf:RDF xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\"\r\n" + 
	    	"         xmlns:dc=\"http://purl.org/dc/elements/1.1/\"\r\n" + 
	    	"         xmlns:edm=\"http://www.europeana.eu/schemas/edm/\"\r\n" + 
	    	"         xmlns:wgs84_pos=\"http://www.w3.org/2003/01/geo/wgs84_pos#\"\r\n" + 
	    	"         xmlns:owl=\"http://www.w3.org/2002/07/owl#\"\r\n" + 
	    	"         xmlns:skos=\"http://www.w3.org/2004/02/skos/core#\"\r\n" + 
	    	"         xmlns:dcterms=\"http://purl.org/dc/terms/\" >");
	    strBuilder.append(xml);
	    strBuilder.append("</rdf:RDF>");
	    return strBuilder.toString();
	}

	@JacksonXmlProperty(isAttribute= true, localName = "rdf:about")
	public String getAbout() {
		return place.getAbout();
	}
	
	@JacksonXmlProperty(localName = "wgs84_pos:lat")
	public Float getLatitude() {
		return place.getLatitude();
	}

	@JacksonXmlProperty(localName = "wgs84_pos:long")
	public Float getLongitude() {
		return place.getLongitude();
	}

	@JacksonXmlProperty(localName = "wgs84_pos:alt")
	public Float getAltitude() {
		return place.getAltitude();
	}
	
	@JacksonXmlElementWrapper(useWrapping=false)
	@JacksonXmlProperty(localName = "skos:prefLabel")
	public List<XmlMultilingualString> getPrefLabel() {		
		return XmlMultilingualString.convertToXmlMultilingualString(place.getPrefLabel());
	}
	
	@JacksonXmlElementWrapper(useWrapping=false)
	@JacksonXmlProperty(localName = "skos:altLabel")
	public List<XmlMultilingualString> getAltLabel() {
		return XmlMultilingualString.convertToXmlMultilingualString(place.getAltLabel());
	}
	
	@JacksonXmlElementWrapper(useWrapping=false)
	@JacksonXmlProperty(localName = "skos:note")
	public List<XmlMultilingualString> getNote() {
		return XmlMultilingualString.convertToXmlMultilingualString(place.getNote());
	}
	
	@JacksonXmlElementWrapper(useWrapping=false)
	@JacksonXmlProperty(localName = "dcterms:hasPart")
	public List<RdfResource> getHasPart() {
	    	return RdfResource.convertToRdfResource(place.getHasPart());
	}

	@JacksonXmlElementWrapper(useWrapping=false)
	@JacksonXmlProperty(localName = "dcterms:isPartOf")
	public List<RdfResource> getIsPartOf() {
	    	return RdfResource.convertToRdfResource(place.getIsPartOfArray());
	}
	
	@JacksonXmlElementWrapper(useWrapping=false)
	@JacksonXmlProperty(localName = "edm:isNextInSequence")
	public List<RdfResource> getIsNextInSequence() {
		return RdfResource.convertToRdfResource(place.getIsNextInSequence());
	}
	
	@JacksonXmlElementWrapper(useWrapping=false)
	@JacksonXmlProperty(localName = "owl:sameAs")
	public List<RdfResource> getSameAs(){
	    	return RdfResource.convertToRdfResource(place.getSameAs());
	}
	
    	
}
