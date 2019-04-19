package eu.europeana.entity.web.xml.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class EdmWebResource {
    
    @JsonIgnore
    private String about;
    
    public EdmWebResource(String about) {
	this.about = about;
    }
    
    @JacksonXmlProperty(localName=XmlConstants.XML_EDM_WEB_RESOURCE)
    public RdfDepiction getResource() {
	return new RdfDepiction(about);
    }
}
