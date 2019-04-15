package eu.europeana.entity.web.xml.model;

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
    
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JacksonXmlProperty(isAttribute=true, localName=XmlConstants.XML_LANG)
    public String getLanguage() {
	return language;
    }
    
    @JacksonXmlText
    public String getValue() {
	return value;
    }
}
