package eu.europeana.entity.web.xml.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import eu.europeana.entity.definitions.model.Timespan;

@JacksonXmlRootElement(localName = XmlConstants.XML_EDM_TIMESPAN)
@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder({XmlConstants.XML_FOAF_DEPICTION, XmlConstants.XML_SKOS_PREF_LABEL, XmlConstants.XML_SKOS_ALT_LABEL, XmlConstants.XML_SKOS_HIDDEN_LABEL,
    	XmlConstants.XML_EDM_BEGIN,XmlConstants.XML_EDM_END,XmlConstants.XML_DCTERMS_IS_PART_OF,XmlConstants.XML_OWL_SAME_AS,
    	XmlConstants.XML_EDM_WEB_RESOURCE, XmlConstants.XML_EDM_IS_NEXT_IN_SEQUENCE})
public class XmlTimespanImpl extends XmlBaseEntityImpl {
    	
    	public XmlTimespanImpl(Timespan timespan) {
    	    	super(timespan);
    	}

	@JacksonXmlElementWrapper(useWrapping=false)
	@JacksonXmlProperty(localName = XmlConstants.XML_DCTERMS_IS_PART_OF)
	public List<RdfResource> getIsPartOf() {
	    	return RdfXmlUtils.convertToRdfResource(((Timespan)entity).getIsPartOfArray());
	}
	
	@JacksonXmlElementWrapper(useWrapping=false)
	@JacksonXmlProperty(localName = XmlConstants.XML_EDM_BEGIN)
	public String getBegin() {
	    	return ((Timespan)entity).getBeginString();
	}

	@JacksonXmlElementWrapper(useWrapping=false)
	@JacksonXmlProperty(localName = XmlConstants.XML_EDM_END)
	public String getEnd() {
	    	return ((Timespan)entity).getEndString();
	}
	
	@JacksonXmlElementWrapper(useWrapping=false)
	@JacksonXmlProperty(localName = XmlConstants.XML_SKOS_HIDDEN_LABEL)
	public List<XmlMultilingualString> getHiddenLabel() {
		return RdfXmlUtils.convertToXmlMultilingualString(entity.getHiddenLabel());
	}

	@JacksonXmlElementWrapper(useWrapping=false)
	@JacksonXmlProperty(localName = XmlConstants.XML_EDM_IS_NEXT_IN_SEQUENCE)
	public List<RdfResource> getIsNextInSequence() {
	    	return RdfXmlUtils.convertToRdfResource(((Timespan)entity).getIsNextInSequence());
	}

	
}
