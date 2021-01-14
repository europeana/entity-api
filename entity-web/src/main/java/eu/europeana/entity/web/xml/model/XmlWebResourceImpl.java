package eu.europeana.entity.web.xml.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName = XmlConstants.XML_EDM_WEB_RESOURCE)
@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder({XmlConstants.XML_RDF_ABOUT, XmlConstants.XML_DC_SOURCE, XmlConstants.XML_FOAF_THUMBNAIL})
public class XmlWebResourceImpl {

    	@JsonIgnore
    	String source;
    	@JsonIgnore
    	String about;
    	@JsonIgnore
    	String thumbnail;
    	
    	public XmlWebResourceImpl(String about, String source, String thumbnail) {
    	    this.about = about;
    	    this.source = source;
    	    this.thumbnail = thumbnail;
    	}
    	
	@JacksonXmlProperty(isAttribute= true, localName = XmlConstants.XML_RDF_ABOUT)
	public String getAbout() {
		return about;
	}

	@JacksonXmlProperty(localName = XmlConstants.XML_DC_SOURCE)
	public RdfResource getSource() {
		return new RdfResource(source);
	}

	@JacksonXmlProperty(localName = XmlConstants.XML_FOAF_THUMBNAIL)
	public RdfResource getThumbnail() {
		return new RdfResource(thumbnail);
	}

}
