package eu.europeana.entity.web.xml.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import eu.europeana.entity.definitions.model.Agent;

@JacksonXmlRootElement(localName = XmlConstants.XML_EDM_AGENT)
@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder({XmlConstants.XML_FOAF_DEPICTION, XmlConstants.XML_SKOS_PREF_LABEL, XmlConstants.XML_SKOS_ALT_LABEL, XmlConstants.XML_SKOS_HIDDEN_LABEL,
    	XmlConstants.XML_FOAF_NAME, XmlConstants.XML_EDM_BEGIN, XmlConstants.XML_RDAGR2_DATE_OF_BIRTH, XmlConstants.XML_RDAGR2_DATE_OF_ESTABLISHMENT,
    	XmlConstants.XML_EDM_END, XmlConstants.XML_RDAGR2_DATE_OF_DEATH, XmlConstants.XML_RDAGR2_DATE_OF_TERMINATION, XmlConstants.XML_DC_DATE,
    	XmlConstants.XML_RDAGR2_PLACE_OF_BIRTH, XmlConstants.XML_RDAGR2_PLACE_OF_DEATH, XmlConstants.XML_RDAGR2_GENDER, 
    	XmlConstants.XML_RDAGR2_PROFESSION_OR_OCCUPATION, XmlConstants.XML_RDAGR2_BIOGRAPHICAL_INFORMATION, XmlConstants.XML_SKOS_NOTE,
    	XmlConstants.XML_DCTERMS_HAS_PART, XmlConstants.XML_DCTERMS_IS_PART_OF, XmlConstants.XML_EDM_HASMET, XmlConstants.XML_EDM_IS_RELATED_TO,
    	XmlConstants.XML_DC_IDENTIFIER, XmlConstants.XML_OWL_SAME_AS, XmlConstants.XML_ORE_IS_AGGREGATED_BY})
public class XmlAgentImpl extends XmlBaseEntityImpl {
    	@JsonIgnore
    	private Agent agent;
    
    	public XmlAgentImpl(Agent agent) {
    	    	super(agent);
    	    	this.agent = agent;
    	}
	
	@JacksonXmlElementWrapper(useWrapping=false)
	@JacksonXmlProperty(localName = XmlConstants.XML_SKOS_HIDDEN_LABEL)
	public List<XmlMultilingualString> getHiddenLabel() {
		return RdfXmlUtils.convertToXmlMultilingualString(agent.getHiddenLabel());
	}
	
	@JacksonXmlElementWrapper(useWrapping=false)
	@JacksonXmlProperty(localName = XmlConstants.XML_SKOS_NOTE)
	public List<XmlMultilingualString> getNote() {
		return RdfXmlUtils.convertToXmlMultilingualString(agent.getNote());
	}
	
	@JacksonXmlElementWrapper(useWrapping=false)
	@JacksonXmlProperty(localName = XmlConstants.XML_SKOS_PREF_LABEL)
	public List<XmlMultilingualString> getPrefLabel() {		
		return RdfXmlUtils.convertToXmlMultilingualString(agent.getPrefLabel());
	}
    	
//	@JacksonXmlElementWrapper(useWrapping=false)
//	@JacksonXmlProperty(localName = XmlConstants.XML_DC_DATE)
//	public List<Object> getDcDate() {
//	    	// TODO: GetDcDate from Agent currently not implemented
//	    	return null;
//		//return XmlMultilingualString.convertToXmlMultilingualStringOrRdfResource(agent.getDcDate());
//	}
	
	@JacksonXmlElementWrapper(useWrapping=false)
	@JacksonXmlProperty(localName = XmlConstants.XML_DC_IDENTIFIER)
	public String[] getIdentifier() {
	    	return agent.getIdentifier();
	}
	
	@JacksonXmlElementWrapper(useWrapping=false)
	@JacksonXmlProperty(localName = XmlConstants.XML_DCTERMS_HAS_PART)
	public List<RdfResource> getHasPart() {
	    	return RdfXmlUtils.convertToRdfResource(agent.getHasPart());
	}

	@JacksonXmlElementWrapper(useWrapping=false)
	@JacksonXmlProperty(localName = XmlConstants.XML_DCTERMS_IS_PART_OF)
	public List<RdfResource> getIsPartOf() {
	    	return RdfXmlUtils.convertToRdfResource(agent.getIsPartOfArray());
	}
	
	@JacksonXmlElementWrapper(useWrapping=false)
	@JacksonXmlProperty(localName = XmlConstants.XML_EDM_BEGIN)
	public String[] getBegin() {
	    	return agent.getBeginArray();
	}

	@JacksonXmlElementWrapper(useWrapping=false)
	@JacksonXmlProperty(localName = XmlConstants.XML_EDM_END)
	public String[] getEnd() {
	    	return agent.getEndArray();
	}
	
	@JacksonXmlElementWrapper(useWrapping=false)
	@JacksonXmlProperty(localName = XmlConstants.XML_EDM_HASMET)
	public List<RdfResource> getHasMet() {
	    	return RdfXmlUtils.convertToRdfResource(agent.getHasMet());
	}
	
	@JacksonXmlElementWrapper(useWrapping=false)
	@JacksonXmlProperty(localName = XmlConstants.XML_EDM_IS_RELATED_TO)
	public List<RdfResource> getIsRelatedTo() {
	    	return RdfXmlUtils.convertToRdfResource(agent.getIsRelatedTo());
	}
	
	@JacksonXmlElementWrapper(useWrapping=false)
	@JacksonXmlProperty(localName = XmlConstants.XML_FOAF_NAME)
	public List<XmlMultilingualString> getName(){
	    	return RdfXmlUtils.convertMapToXmlMultilingualString(agent.getName());
	}
	
	@JacksonXmlElementWrapper(useWrapping=false)
	@JacksonXmlProperty(localName = XmlConstants.XML_RDAGR2_BIOGRAPHICAL_INFORMATION)
	public List<XmlMultilingualString> getBiographicalInformation(){
	    	return RdfXmlUtils.convertToXmlMultilingualString(agent.getBiographicalInformation());
	}
	
	@JacksonXmlElementWrapper(useWrapping=false)
	@JacksonXmlProperty(localName = XmlConstants.XML_RDAGR2_DATE_OF_BIRTH)
	public String[] getDateOfBirth() {
	    	return agent.getDateOfBirth();
	}
	
	@JacksonXmlElementWrapper(useWrapping=false)
	@JacksonXmlProperty(localName = XmlConstants.XML_RDAGR2_DATE_OF_DEATH)
	public String[] getDateOfDeath() {
	    	return agent.getDateOfDeath();
	}
	
	@JacksonXmlElementWrapper(useWrapping=false)
	@JacksonXmlProperty(localName = XmlConstants.XML_RDAGR2_DATE_OF_ESTABLISHMENT)
	public String getDateOfEstablishment() {
	    	return agent.getDateOfEstablishment();
	}
	
	@JacksonXmlElementWrapper(useWrapping=false)
	@JacksonXmlProperty(localName = XmlConstants.XML_RDAGR2_DATE_OF_TERMINATION)
	public String getDateOfTermination() {
	    	return agent.getDateOfTermination();
	}
	
	@JacksonXmlElementWrapper(useWrapping=false)
	@JacksonXmlProperty(localName = XmlConstants.XML_RDAGR2_GENDER)
	public String getGender() {
	    	return agent.getGender();
	}
	
	@JacksonXmlElementWrapper(useWrapping=false)
	@JacksonXmlProperty(localName = XmlConstants.XML_RDAGR2_PLACE_OF_BIRTH)
	public List<Object> getPlaceOfBirth(){
	    	return RdfXmlUtils.convertToXmlMultilingualStringOrRdfResource(agent.getPlaceOfBirth());
	}
	
	@JacksonXmlElementWrapper(useWrapping=false)
	@JacksonXmlProperty(localName = XmlConstants.XML_RDAGR2_PLACE_OF_DEATH)
	public List<Object> getPlaceOfDeath(){
	    	return RdfXmlUtils.convertToXmlMultilingualStringOrRdfResource(agent.getPlaceOfDeath());
	}
	
	@JacksonXmlElementWrapper(useWrapping=false)
	@JacksonXmlProperty(localName = XmlConstants.XML_RDAGR2_PROFESSION_OR_OCCUPATION)
	public List<Object> getProfessionOrOccupation(){
	    	return RdfXmlUtils.convertToXmlMultilingualStringOrRdfResource(agent.getProfessionOrOccupation());
	}
	
	@JacksonXmlElementWrapper(useWrapping=false)
	@JacksonXmlProperty(localName = XmlConstants.XML_OWL_SAME_AS)
	public List<RdfResource> getSameAs(){
	    	return RdfXmlUtils.convertToRdfResource(agent.getSameAs());
	}
	
}
