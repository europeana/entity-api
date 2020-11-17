package eu.europeana.entity.web.xml.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import eu.europeana.entity.definitions.model.Timespan;

@JacksonXmlRootElement(localName = XmlConstants.XML_EDM_TIMESPAN)
@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder({XmlConstants.XML_FOAF_DEPICTION, XmlConstants.XML_SKOS_PREF_LABEL, XmlConstants.XML_SKOS_ALT_LABEL, 
    	XmlConstants.XML_EDM_BEGIN,XmlConstants.XML_EDM_END,XmlConstants.XML_DCTERMS_IS_PART_OF,XmlConstants.XML_OWL_SAME_AS})
public class XmlTimespanImpl extends XmlBaseEntityImpl {
    	@JsonIgnore
    	private Timespan timespan;
    
    	public XmlTimespanImpl(Timespan timespan) {
    	    	super(timespan);
    	    	this.timespan = timespan;
    	}

	@JacksonXmlElementWrapper(useWrapping=false)
	@JacksonXmlProperty(localName = XmlConstants.XML_DCTERMS_IS_PART_OF)
	public List<RdfResource> getIsPartOf() {
	    	return RdfXmlUtils.convertToRdfResource(timespan.getIsPartOfArray());
	}
	
	@JacksonXmlElementWrapper(useWrapping=false)
	@JacksonXmlProperty(localName = XmlConstants.XML_EDM_BEGIN)
	public String getBegin() {
	    	return timespan.getBeginString();
	}

	@JacksonXmlElementWrapper(useWrapping=false)
	@JacksonXmlProperty(localName = XmlConstants.XML_EDM_END)
	public String getEnd() {
	    	return timespan.getEndString();
	}
	
	@JacksonXmlElementWrapper(useWrapping=false)
	@JacksonXmlProperty(localName = XmlConstants.XML_OWL_SAME_AS)
	public List<RdfResource> getSameAs(){
	    	return RdfXmlUtils.convertToRdfResource(timespan.getSameAs());
	}
	
}
