package eu.europeana.entity.web.xml.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    
    @JsonIgnore
    public static List<XmlMultilingualString> convertToXmlMultilingualString(Map<String, List<String>> values) {
	if (values == null)
	    return null;
	List<XmlMultilingualString> res = new ArrayList<>();
	for (String language : values.keySet()) {
	    List<String> entryValues = values.get(language);
	    for (String entryValue : entryValues) {
		res.add(new XmlMultilingualString(entryValue, language));
	    }
	}
	return res;
    }
    
    @JsonIgnore
    public static List<Object> convertToXmlMultilingualStringOrRdfResource(Map<String, List<String>> values) {
	if (values == null)
	    return null;
	List<Object> res = new ArrayList<>();
	for (String language : values.keySet()) {
	    List<String> entryValues = values.get(language);
	    for (String entryValue : entryValues) {
		if(entryValue.startsWith("http"))
		    res.add(new RdfResource(entryValue));
		else
		    res.add(new XmlMultilingualString(entryValue, language));
	    }
	}
	return res;
    }
    
    @JsonIgnore
    public static List<XmlMultilingualString> convertMapToXmlMultilingualString(Map<String, String> values) {
	if (values == null)
	    return null;
	List<XmlMultilingualString> res = new ArrayList<>();
	for (String language : values.keySet()) {
	    res.add(new XmlMultilingualString(values.get(language), language));
	}
	return res;
    }

}
