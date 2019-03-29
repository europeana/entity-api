package eu.europeana.entity.solr.model;

import java.util.ArrayList;
import java.util.List;

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
    
    @JsonIgnore
    public static List<RdfResource> convertToRdfResource(String[] elements) {
	if (elements == null)
	    return null;
	List<RdfResource> res = new ArrayList<>();
	for (int index = 0; index < elements.length; index++) {
	    res.add(new RdfResource(elements[index]));
	}
	return res;
    }

}
