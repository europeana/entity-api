package eu.europeana.entity.web.xml.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class XmlIsAggregatedByImpl {

    	@JsonIgnore
	String id;
	
	public XmlIsAggregatedByImpl(String aggregation_id) {
	    id = aggregation_id;
	}
	
	@JacksonXmlProperty(isAttribute= true, localName = XmlConstants.XML_RDF_RESOURCE)
	public String getAbout() {
		return id;
	}
    
}
