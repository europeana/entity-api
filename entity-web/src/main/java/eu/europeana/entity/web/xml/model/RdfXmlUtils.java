package eu.europeana.entity.web.xml.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import eu.europeana.corelib.utils.EuropeanaUriUtils;

public class RdfXmlUtils {

    public static List<RdfResource> convertToRdfResource(String[] elements) {
	if (elements == null)
	    return null;
	List<RdfResource> res = new ArrayList<>();
	for (int index = 0; index < elements.length; index++) {
	    res.add(new RdfResource(elements[index]));
	}
	return res;
    }
    
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
    
    public static List<Object> convertToXmlMultilingualStringOrRdfResource(Map<String, List<String>> values) {
	if (values == null)
	    return null;
	List<Object> res = new ArrayList<>();
	for (String language : values.keySet()) {
	    List<String> entryValues = values.get(language);
	    for (String entryValue : entryValues) {
		if(EuropeanaUriUtils.isUri(entryValue))
		    res.add(new RdfResource(entryValue));
		else
		    res.add(new XmlMultilingualString(entryValue, language));
	    }
	}
	return res;
    }
    
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
