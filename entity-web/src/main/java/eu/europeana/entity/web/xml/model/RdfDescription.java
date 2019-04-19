package eu.europeana.entity.web.xml.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class RdfDescription {
    
    @JsonIgnore
    private String about;
    
    public RdfDescription(String about) {
	this.about = about;
    }
    
    @JacksonXmlProperty(localName=XmlConstants.XML_RDF_DESCRIPTION)
    public RdfDepiction getResource() {
	return new RdfDepiction(about);
    }
}
