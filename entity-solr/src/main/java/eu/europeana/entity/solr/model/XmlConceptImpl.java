package eu.europeana.entity.solr.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName= "skos:Concept")
@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
public class XmlConceptImpl{

    	@JsonIgnore
    	private eu.europeana.entity.definitions.model.Concept concept;
    
    	public XmlConceptImpl(eu.europeana.entity.definitions.model.Concept concept) {
    	    this.concept = concept;
    	}
    
    	@JsonIgnore
    	public String addingAdditionalXmlString(String xml) {
    	    StringBuilder strBuilder = new StringBuilder();
    	    strBuilder.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n" + 
    	    	" <rdf:RDF xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\"\r\n" + 
    	    	"          xmlns:wgs84_pos=\"http://www.w3.org/2003/01/geo/wgs84_pos#\"\r\n" + 
    	    	"          xmlns:skos=\"http://www.w3.org/2004/02/skos/core#\"\r\n >");
    	    strBuilder.append(xml);
    	    strBuilder.append("</rdf:RDF>");
    	    return strBuilder.toString();
    	}
    	
    	@JsonIgnore
	private List<RdfResource> getRdfResource(String[] elements){
	    	if(elements == null)
	    	    return null;
	    	List<RdfResource> res = new ArrayList<>();
	    	for(int index = 0; index < elements.length; index++) {
	    	    res.add(new RdfResource(elements[index]));
	    	}
		return res;
	}
    	
    	private List<XmlMultilingualString> getXmlMultilingualString(Map<String, List<String>> values){
    	    if(values == null)
    		return null;
    	    List<XmlMultilingualString> res = new ArrayList<>();
    	    for(String language : values.keySet()) {
    		List<String> entryValues = values.get(language);
    		for(String entryValue : entryValues) {
    		    res.add(new XmlMultilingualString(entryValue, language));
    		}
    	    }
    	    return res;
    	}
	
	@JacksonXmlProperty(isAttribute= true, localName = "rdf:about")
	public String getAbout() {
		return concept.getAbout();
	}
    	
	@JacksonXmlElementWrapper(useWrapping=false)
	@JacksonXmlProperty(localName = "skos:broader")
	public List<RdfResource> getBroader() {
	    	return getRdfResource(concept.getBroader());
	}
	
	@JacksonXmlElementWrapper(useWrapping=false)
	@JacksonXmlProperty(localName = "skos:narrower")
	public List<RdfResource> getNarrower() {
		return getRdfResource(concept.getNarrower());
	}
	
	@JacksonXmlElementWrapper(useWrapping=false)
	@JacksonXmlProperty(localName = "skos:related")
	public List<RdfResource> getRelated() {
		return getRdfResource(concept.getRelated());
	}
	
	@JacksonXmlElementWrapper(useWrapping=false)
	@JacksonXmlProperty(localName = "skos:broadMatch")
	public List<RdfResource> getBroadMatch() {
		return getRdfResource(concept.getBroadMatch());
	}

	@JacksonXmlElementWrapper(useWrapping=false)
	@JacksonXmlProperty(localName = "skos:narrowMatch")
	public List<RdfResource> getNarrowMatch() {
		return getRdfResource(concept.getNarrowMatch());
	}

	@JacksonXmlElementWrapper(useWrapping=false)
	@JacksonXmlProperty(localName = "skos:exactMatch")
	public List<RdfResource> getExactMatch() {
		return getRdfResource(concept.getExactMatch());
	}

	@JacksonXmlElementWrapper(useWrapping=false)
	@JacksonXmlProperty(localName = "skos:relatedMatch")
	public List<RdfResource> getRelatedMatch() {
		return getRdfResource(concept.getRelatedMatch());
	}

	@JacksonXmlElementWrapper(useWrapping=false)
	@JacksonXmlProperty(localName = "skos:closeMatch")
	public List<RdfResource> getCloseMatch() {
		return getRdfResource(concept.getCloseMatch());
	}

	@JacksonXmlElementWrapper(useWrapping=false)
	@JacksonXmlProperty(localName = "skos:notation")
	public List<XmlMultilingualString> getNotation() {
		return getXmlMultilingualString(concept.getNotation());
	}
	
	@JacksonXmlElementWrapper(useWrapping=false)
	@JacksonXmlProperty(localName = "skos:altLabel")
	public List<XmlMultilingualString> getAltLabel() {
		return getXmlMultilingualString(concept.getAltLabel());
	}
	
	@JacksonXmlElementWrapper(useWrapping=false)
	@JacksonXmlProperty(localName = "skos:note")
	public List<XmlMultilingualString> getNote() {
		return getXmlMultilingualString(concept.getNote());
	}
	
	@JacksonXmlElementWrapper(useWrapping=false)
	@JacksonXmlProperty(localName = "skos:prefLabel")
	public List<XmlMultilingualString> getPrefLabel() {		
		return getXmlMultilingualString(concept.getPrefLabel());
	}
}
