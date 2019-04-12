package eu.europeana.entity.web.xml.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import eu.europeana.entity.definitions.model.Place;

@JacksonXmlRootElement(localName = XmlConstants.XML_EDM_PLACE)
@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder({XmlConstants.XML_FOAF_DEPICTION, XmlConstants.XML_SKOS_PREF_LABEL, XmlConstants.XML_SKOS_ALT_LABEL, XmlConstants.XML_SKOS_HIDDEN_LABEL,
    	XmlConstants.XML_WGS84_POS_LAT, XmlConstants.XML_WGS84_POS_LONG, XmlConstants.XML_WGS84_POS_ALT, XmlConstants.XML_SKOS_NOTE, 
    	XmlConstants.XML_DCTERMS_HAS_PART, XmlConstants.XML_DCTERMS_IS_PART_OF, XmlConstants.XML_EDM_IS_NEXT_IN_SEQUENCE, XmlConstants.XML_OWL_SAME_AS})
public class XmlPlaceImpl {
    
    	@JsonIgnore
    	private Place place;
    
    	public XmlPlaceImpl(Place place) {
    	    	this.place = place;
    	}

	@JacksonXmlProperty(isAttribute= true, localName = XmlConstants.XML_RDF_ABOUT)
	public String getAbout() {
		return place.getAbout();
	}
	
	@JacksonXmlProperty(localName = XmlConstants.XML_FOAF_DEPICTION)
	public RdfResource getDepiction() {
	    	if(place.getDepiction() == null)
	    	    return null;
		return new RdfResource(place.getDepiction());
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
		return RdfXmlUtils.convertToXmlMultilingualString(place.getPrefLabel());
	}
	
	@JacksonXmlElementWrapper(useWrapping=false)
	@JacksonXmlProperty(localName = XmlConstants.XML_SKOS_ALT_LABEL)
	public List<XmlMultilingualString> getAltLabel() {
		return RdfXmlUtils.convertToXmlMultilingualString(place.getAltLabel());
	}
	
	@JacksonXmlElementWrapper(useWrapping=false)
	@JacksonXmlProperty(localName = XmlConstants.XML_SKOS_HIDDEN_LABEL)
	public List<XmlMultilingualString> getHiddenLabel() {
		return RdfXmlUtils.convertToXmlMultilingualString(place.getHiddenLabel());
	}
	
	@JacksonXmlElementWrapper(useWrapping=false)
	@JacksonXmlProperty(localName = XmlConstants.XML_SKOS_NOTE)
	public List<XmlMultilingualString> getNote() {
		return RdfXmlUtils.convertToXmlMultilingualString(place.getNote());
	}
	
	@JacksonXmlElementWrapper(useWrapping=false)
	@JacksonXmlProperty(localName = XmlConstants.XML_DCTERMS_HAS_PART)
	public List<RdfResource> getHasPart() {
	    	return RdfXmlUtils.convertToRdfResource(place.getHasPart());
	}

	@JacksonXmlElementWrapper(useWrapping=false)
	@JacksonXmlProperty(localName  = XmlConstants.XML_DCTERMS_IS_PART_OF)
	public List<RdfResource> getIsPartOf() {
	    	return RdfXmlUtils.convertToRdfResource(place.getIsPartOfArray());
	}
	
	@JacksonXmlElementWrapper(useWrapping=false)
	@JacksonXmlProperty(localName = XmlConstants.XML_EDM_IS_NEXT_IN_SEQUENCE)
	public List<RdfResource> getIsNextInSequence() {
		return RdfXmlUtils.convertToRdfResource(place.getIsNextInSequence());
	}
	
	@JacksonXmlElementWrapper(useWrapping=false)
	@JacksonXmlProperty(localName = XmlConstants.XML_OWL_SAME_AS)
	public List<RdfResource> getSameAs(){
	    	return RdfXmlUtils.convertToRdfResource(place.getSameAs());
	}
	
    	
}