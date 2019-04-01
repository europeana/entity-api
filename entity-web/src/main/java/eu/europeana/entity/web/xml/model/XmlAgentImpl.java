package eu.europeana.entity.web.xml.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName = XmlConstants.XML_EDM_AGENT)
@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder({XmlConstants.XML_SKOS_PREF_LABEL, XmlConstants.XML_SKOS_ALT_LABEL, XmlConstants.XML_SKOS_NOTE, XmlConstants.XML_DC_DATE,
    	XmlConstants.XML_DC_IDENTIFIER, XmlConstants.XML_DCTERMS_HAS_PART, XmlConstants.XML_DCTERMS_IS_PART_OF, XmlConstants.XML_EDM_BEGIN,
    	XmlConstants.XML_EDM_END, XmlConstants.XML_EDM_HASMET, XmlConstants.XML_EDM_IS_RELATED_TO, XmlConstants.XML_FOAF_NAME, 
    	XmlConstants.XML_RDAGR2_BIOGRAPHICAL_INFORMATION, XmlConstants.XML_RDAGR2_DATE_OF_BIRTH, XmlConstants.XML_RDAGR2_DATE_OF_DEATH,
    	XmlConstants.XML_RDAGR2_DATE_OF_ESTABLISHMENT, XmlConstants.XML_RDAGR2_DATE_OF_TERMINATION, XmlConstants.XML_RDAGR2_GENDER,
    	XmlConstants.XML_RDAGR2_PLACE_OF_BIRTH, XmlConstants.XML_RDAGR2_PLACE_OF_DEATH, XmlConstants.XML_RDAGR2_PROFESSION_OR_OCCUPATION,
    	XmlConstants.XML_OWL_SAME_AS})
public class XmlAgentImpl implements XmlBase {
    	@JsonIgnore
    	private eu.europeana.entity.definitions.model.impl.BaseAgent agent;
    
    	public XmlAgentImpl(eu.europeana.entity.definitions.model.impl.BaseAgent agent) {
    	    	this.agent = agent;
    	}
    
    	@JsonIgnore
	public String addingAdditionalXmlString(String xml) {
	    StringBuilder strBuilder = new StringBuilder();
	    strBuilder.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n" + 
	    	" <rdf:RDF xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\r\n" + 
	    	"         xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\"\r\n" + 
	    	"         xmlns:dc=\"http://purl.org/dc/elements/1.1/\"\r\n" + 
	    	"         xmlns:edm=\"http://www.europeana.eu/schemas/edm/\"\r\n" + 
	    	"         xmlns:foaf=\"http://xmlns.com/foaf/0.1/\"\r\n" + 
	    	"         xmlns:rdaGr2=\"http://rdvocab.info/ElementsGr2/\"\r\n" + 
	    	"         xmlns:owl=\"http://www.w3.org/2002/07/owl#\"\r\n" + 
	    	"         xmlns:skos=\"http://www.w3.org/2004/02/skos/core#\"\r\n" + 
	    	"         xmlns:dcterms=\"http://purl.org/dc/terms/\" >");
	    strBuilder.append(xml);
	    strBuilder.append("</rdf:RDF>");
	    return strBuilder.toString();
	}
    	
	@JacksonXmlProperty(isAttribute= true, localName = XmlConstants.XML_RDF_ABOUT)
	public String getAbout() {
		return agent.getAbout();
	}
	
	@JacksonXmlElementWrapper(useWrapping=false)
	@JacksonXmlProperty(localName = XmlConstants.XML_SKOS_ALT_LABEL)
	public List<XmlMultilingualString> getAltLabel() {
		return XmlMultilingualString.convertToXmlMultilingualString(agent.getAltLabel());
	}
	
	@JacksonXmlElementWrapper(useWrapping=false)
	@JacksonXmlProperty(localName = XmlConstants.XML_SKOS_NOTE)
	public List<XmlMultilingualString> getNote() {
		return XmlMultilingualString.convertToXmlMultilingualString(agent.getNote());
	}
	
	@JacksonXmlElementWrapper(useWrapping=false)
	@JacksonXmlProperty(localName = XmlConstants.XML_SKOS_PREF_LABEL)
	public List<XmlMultilingualString> getPrefLabel() {		
		return XmlMultilingualString.convertToXmlMultilingualString(agent.getPrefLabel());
	}
    	
	@JacksonXmlElementWrapper(useWrapping=false)
	@JacksonXmlProperty(localName = XmlConstants.XML_DC_DATE)
	public List<Object> getDcDate() {
	    	// TODO: GetDcDate from Agent currently not implemented
	    	return null;
		//return XmlMultilingualString.convertToXmlMultilingualStringOrRdfResource(agent.getDcDate());
	}
	
	@JacksonXmlElementWrapper(useWrapping=false)
	@JacksonXmlProperty(localName = XmlConstants.XML_DC_IDENTIFIER)
	public String[] getIdentifier() {
	    	return agent.getIdentifier();
	}
	
	@JacksonXmlElementWrapper(useWrapping=false)
	@JacksonXmlProperty(localName = XmlConstants.XML_DCTERMS_HAS_PART)
	public List<RdfResource> getHasPart() {
	    	return RdfResource.convertToRdfResource(agent.getHasPart());
	}

	@JacksonXmlElementWrapper(useWrapping=false)
	@JacksonXmlProperty(localName = XmlConstants.XML_DCTERMS_IS_PART_OF)
	public List<RdfResource> getIsPartOf() {
	    	return RdfResource.convertToRdfResource(agent.getIsPartOfArray());
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
	public RdfResource getHasMet() {
	    	if(agent.getHasMet() == null)
	    	    return null;
	    	return new RdfResource(agent.getHasMet());
	}
	
	@JacksonXmlElementWrapper(useWrapping=false)
	@JacksonXmlProperty(localName = XmlConstants.XML_EDM_IS_RELATED_TO)
	public List<RdfResource> getIsRelatedTo() {
	    	return RdfResource.convertToRdfResource(agent.getIsRelatedTo());
	}
	
	@JacksonXmlElementWrapper(useWrapping=false)
	@JacksonXmlProperty(localName = XmlConstants.XML_FOAF_NAME)
	public List<XmlMultilingualString> getName(){
	    	return XmlMultilingualString.convertMapToXmlMultilingualString(agent.getName());
	}
	
	@JacksonXmlElementWrapper(useWrapping=false)
	@JacksonXmlProperty(localName = XmlConstants.XML_RDAGR2_BIOGRAPHICAL_INFORMATION)
	public List<XmlMultilingualString> getBiographicalInformation(){
	    	return XmlMultilingualString.convertToXmlMultilingualString(agent.getBiographicalInformation());
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
	    	return XmlMultilingualString.convertToXmlMultilingualStringOrRdfResource(agent.getPlaceOfBirth());
	}
	
	@JacksonXmlElementWrapper(useWrapping=false)
	@JacksonXmlProperty(localName = XmlConstants.XML_RDAGR2_PLACE_OF_DEATH)
	public List<Object> getPlaceOfDeath(){
	    	return XmlMultilingualString.convertToXmlMultilingualStringOrRdfResource(agent.getPlaceOfDeath());
	}
	
	@JacksonXmlElementWrapper(useWrapping=false)
	@JacksonXmlProperty(localName = XmlConstants.XML_RDAGR2_PROFESSION_OR_OCCUPATION)
	public List<Object> getProfessionOrOccupation(){
	    	return XmlMultilingualString.convertToXmlMultilingualStringOrRdfResource(agent.getProfessionOrOccupation());
	}
	
	@JacksonXmlElementWrapper(useWrapping=false)
	@JacksonXmlProperty(localName = XmlConstants.XML_OWL_SAME_AS)
	public List<RdfResource> getSameAs(){
	    	return RdfResource.convertToRdfResource(agent.getSameAs());
	}
	
}
