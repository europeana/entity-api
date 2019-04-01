package eu.europeana.entity.web.xml.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName = XmlConstants.XML_EDM_PLACE)
@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder({XmlConstants.XML_WGS84_POS_LAT, XmlConstants.XML_WGS84_POS_LONG, XmlConstants.XML_WGS84_POS_ALT, XmlConstants.XML_SKOS_PREF_LABEL,
    	XmlConstants.XML_SKOS_ALT_LABEL, XmlConstants.XML_SKOS_NOTE, XmlConstants.XML_DCTERMS_HAS_PART, XmlConstants.XML_DCTERMS_IS_PART_OF,
    	XmlConstants.XML_EDM_IS_NEXT_IN_SEQUENCE, XmlConstants.XML_OWL_SAME_AS})
public class XmlPlaceImpl implements XmlBase {
    
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

	@JacksonXmlProperty(isAttribute= true, localName = XmlConstants.XML_RDF_ABOUT)
	public String getAbout() {
		return place.getAbout();
	}
	
	@JacksonXmlProperty(localName = XmlConstants.XML_WGS84_POS_LAT)
	public Float getLatitude() {
		return place.getLatitude();
	}

	@JacksonXmlProperty(localName = XmlConstants.XML_WGS84_POS_LONG)
	public Float getLongitude() {
		return place.getLongitude();
	}

	@JacksonXmlProperty(localName = XmlConstants.XML_WGS84_POS_ALT)
	public Float getAltitude() {
		return place.getAltitude();
	}
	
	@JacksonXmlElementWrapper(useWrapping=false)
	@JacksonXmlProperty(localName = XmlConstants.XML_SKOS_PREF_LABEL)
	public List<XmlMultilingualString> getPrefLabel() {		
		return XmlMultilingualString.convertToXmlMultilingualString(place.getPrefLabel());
	}
	
	@JacksonXmlElementWrapper(useWrapping=false)
	@JacksonXmlProperty(localName = XmlConstants.XML_SKOS_ALT_LABEL)
	public List<XmlMultilingualString> getAltLabel() {
		return XmlMultilingualString.convertToXmlMultilingualString(place.getAltLabel());
	}
	
	@JacksonXmlElementWrapper(useWrapping=false)
	@JacksonXmlProperty(localName = XmlConstants.XML_SKOS_NOTE)
	public List<XmlMultilingualString> getNote() {
		return XmlMultilingualString.convertToXmlMultilingualString(place.getNote());
	}
	
	@JacksonXmlElementWrapper(useWrapping=false)
	@JacksonXmlProperty(localName = XmlConstants.XML_DCTERMS_HAS_PART)
	public List<RdfResource> getHasPart() {
	    	return RdfResource.convertToRdfResource(place.getHasPart());
	}

	@JacksonXmlElementWrapper(useWrapping=false)
	@JacksonXmlProperty(localName  = XmlConstants.XML_DCTERMS_IS_PART_OF)
	public List<RdfResource> getIsPartOf() {
	    	return RdfResource.convertToRdfResource(place.getIsPartOfArray());
	}
	
	@JacksonXmlElementWrapper(useWrapping=false)
	@JacksonXmlProperty(localName = XmlConstants.XML_EDM_IS_NEXT_IN_SEQUENCE)
	public List<RdfResource> getIsNextInSequence() {
		return RdfResource.convertToRdfResource(place.getIsNextInSequence());
	}
	
	@JacksonXmlElementWrapper(useWrapping=false)
	@JacksonXmlProperty(localName = XmlConstants.XML_OWL_SAME_AS)
	public List<RdfResource> getSameAs(){
	    	return RdfResource.convertToRdfResource(place.getSameAs());
	}
	
    	
}
