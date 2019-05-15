package eu.europeana.entity.web.xml.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class RdfResource {
    
    @JsonIgnore
    private String value;
    
    public RdfResource(String value) {
	this.value = value;
    }
    
    @JacksonXmlProperty(isAttribute=true, localName=XmlConstants.XML_RDF_RESOURCE)
    public String toString() {
	return value;
    }
}
