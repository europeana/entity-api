package eu.europeana.entity.solr.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class RdfResource {
    
    @JsonIgnore
    private String value;
    
    public RdfResource(String value) {
	this.value = value;
    }
    
    @JacksonXmlProperty(isAttribute=true, localName="rdf:resource")
    public String toString() {
	return value;
    }
    
}
