package eu.europeana.entity.web.xml.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;

import eu.europeana.api.commons.definitions.utils.DateUtils;

public class RdfTypedElement {

    private final String DATA_TYPE_DATETIME = "http://www.w3.org/2001/XMLSchema#dateTime";
    
    @JsonIgnore
    private String value;
    @JsonIgnore
    private String type;
    
    public RdfTypedElement(Date date) {
	this.value = DateUtils.convertDateToStr(date);
	this.type = DATA_TYPE_DATETIME;
    }
    
    @JacksonXmlProperty(isAttribute= true, localName= XmlConstants.XML_RDF_DATATYPE)
    public String getType() {
	return type;
    }
    
    @JacksonXmlText
    public String getValue() {
	return value;
    }
}
