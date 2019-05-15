package eu.europeana.entity.web.xml.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import eu.europeana.entity.utils.EntityUtils;

public class RdfDepiction {
    
    @JsonIgnore
    private String about;
    private String resource;
    
    public RdfDepiction(String about) {
	this.about = about;
	resource = EntityUtils.createWikimediaResourceString(about);
    }
    
    @JacksonXmlProperty(isAttribute=true, localName=XmlConstants.XML_RDF_ABOUT)
    public String getAbout() {
	return about;
    }
    
    @JacksonXmlProperty(localName=XmlConstants.XML_DC_SOURCE)
    public RdfResource getResource() {
	return new RdfResource(resource);
    }
}
