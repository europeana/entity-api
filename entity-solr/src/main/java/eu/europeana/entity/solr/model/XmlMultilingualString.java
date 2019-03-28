package eu.europeana.entity.solr.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;

public class XmlMultilingualString {

    @JsonIgnore
    private String value;
    @JsonIgnore
    private String language;
    
    public XmlMultilingualString(String value, String language) {
	this.value = value;
	this.language = language;
    }
    
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JacksonXmlProperty(isAttribute=true, localName="xml:lang")
    public String getLanguage() {
	return language;
    }
    
    @JacksonXmlText
    public String getValue() {
	return value;
    }
    
}
