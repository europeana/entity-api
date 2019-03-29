package eu.europeana.entity.solr.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName= "edm:Agent")
@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder({"skos:prefLabel", "skos:altLabel", "skos:note", "dc:date", "dc:identifier", "dcterms:hasPart",
    	"dcterms:isPartOf", "edm:begin", "edm:end", "edm:hasMet", "edm:isRelatedTo", "foaf:name",
    	"rdaGr2:biographicalInformation", "rdaGr2:dateOfBirth", "rdaGr2:dateOfDeath", "rdaGr2:dateOfEstablishment",
    	"rdaGr2:dateOfTermination", "rdaGr2:gender", "rdaGr2:placeOfBirth", "rdaGr2:placeOfDeath",
    	"rdaGr2:professionOrOccupation", "owl:sameAs"})
public class XmlAgentImpl {
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
    	
	@JacksonXmlProperty(isAttribute= true, localName = "rdf:about")
	public String getAbout() {
		return agent.getAbout();
	}
	
	@JacksonXmlElementWrapper(useWrapping=false)
	@JacksonXmlProperty(localName = "skos:altLabel")
	public List<XmlMultilingualString> getAltLabel() {
		return XmlMultilingualString.convertToXmlMultilingualString(agent.getAltLabel());
	}
	
	@JacksonXmlElementWrapper(useWrapping=false)
	@JacksonXmlProperty(localName = "skos:note")
	public List<XmlMultilingualString> getNote() {
		return XmlMultilingualString.convertToXmlMultilingualString(agent.getNote());
	}
	
	@JacksonXmlElementWrapper(useWrapping=false)
	@JacksonXmlProperty(localName = "skos:prefLabel")
	public List<XmlMultilingualString> getPrefLabel() {		
		return XmlMultilingualString.convertToXmlMultilingualString(agent.getPrefLabel());
	}
    	
	@JacksonXmlElementWrapper(useWrapping=false)
	@JacksonXmlProperty(localName = "dc:date")
	public List<Object> getDcDate() {
	    	// TODO: GetDcDate from Agent currently not implemented
	    	return null;
		//return XmlMultilingualString.convertToXmlMultilingualStringOrRdfResource(agent.getDcDate());
	}
	
	@JacksonXmlElementWrapper(useWrapping=false)
	@JacksonXmlProperty(localName = "dc:identifier")
	public String[] getIdentifier() {
	    	return agent.getIdentifier();
	}
	
	@JacksonXmlElementWrapper(useWrapping=false)
	@JacksonXmlProperty(localName = "dcterms:hasPart")
	public List<RdfResource> getHasPart() {
	    	return RdfResource.convertToRdfResource(agent.getHasPart());
	}

	@JacksonXmlElementWrapper(useWrapping=false)
	@JacksonXmlProperty(localName = "dcterms:isPartOf")
	public List<RdfResource> getIsPartOf() {
	    	return RdfResource.convertToRdfResource(agent.getIsPartOfArray());
	}
	
	@JacksonXmlElementWrapper(useWrapping=false)
	@JacksonXmlProperty(localName = "edm:begin")
	public String[] getBegin() {
	    	return agent.getBeginArray();
	}

	@JacksonXmlElementWrapper(useWrapping=false)
	@JacksonXmlProperty(localName = "edm:end")
	public String[] getEnd() {
	    	return agent.getEndArray();
	}
	
	@JacksonXmlElementWrapper(useWrapping=false)
	@JacksonXmlProperty(localName = "edm:hasMet")
	public RdfResource getHasMet() {
	    	if(agent.getHasMet() == null)
	    	    return null;
	    	return new RdfResource(agent.getHasMet());
	}
	
	@JacksonXmlElementWrapper(useWrapping=false)
	@JacksonXmlProperty(localName = "edm:isRelatedTo")
	public List<RdfResource> getIsRelatedTo() {
	    	return RdfResource.convertToRdfResource(agent.getIsRelatedTo());
	}
	
	@JacksonXmlElementWrapper(useWrapping=false)
	@JacksonXmlProperty(localName = "foaf:name")
	public List<XmlMultilingualString> getName(){
	    	return XmlMultilingualString.convertMapToXmlMultilingualString(agent.getName());
	}
	
	@JacksonXmlElementWrapper(useWrapping=false)
	@JacksonXmlProperty(localName = "rdaGr2:biographicalInformation")
	public List<XmlMultilingualString> getBiographicalInformation(){
	    	return XmlMultilingualString.convertToXmlMultilingualString(agent.getBiographicalInformation());
	}
	
	@JacksonXmlElementWrapper(useWrapping=false)
	@JacksonXmlProperty(localName = "rdaGr2:dateOfBirth")
	public String[] getDateOfBirth() {
	    	return agent.getDateOfBirth();
	}
	
	@JacksonXmlElementWrapper(useWrapping=false)
	@JacksonXmlProperty(localName = "rdaGr2:dateOfDeath")
	public String[] getDateOfDeath() {
	    	return agent.getDateOfDeath();
	}
	
	@JacksonXmlElementWrapper(useWrapping=false)
	@JacksonXmlProperty(localName = "rdaGr2:dateOfEstablishment")
	public String getDateOfEstablishment() {
	    	return agent.getDateOfEstablishment();
	}
	
	@JacksonXmlElementWrapper(useWrapping=false)
	@JacksonXmlProperty(localName = "rdaGr2:dateOfTermination")
	public String getDateOfTermination() {
	    	return agent.getDateOfTermination();
	}
	
	@JacksonXmlElementWrapper(useWrapping=false)
	@JacksonXmlProperty(localName = "rdaGr2:gender")
	public String getGender() {
	    	return agent.getGender();
	}
	
	@JacksonXmlElementWrapper(useWrapping=false)
	@JacksonXmlProperty(localName = "rdaGr2:placeOfBirth")
	public List<Object> getPlaceOfBirth(){
	    	return XmlMultilingualString.convertToXmlMultilingualStringOrRdfResource(agent.getPlaceOfBirth());
	}
	
	@JacksonXmlElementWrapper(useWrapping=false)
	@JacksonXmlProperty(localName = "rdaGr2:placeOfDeath")
	public List<Object> getPlaceOfDeath(){
	    	return XmlMultilingualString.convertToXmlMultilingualStringOrRdfResource(agent.getPlaceOfDeath());
	}
	
	@JacksonXmlElementWrapper(useWrapping=false)
	@JacksonXmlProperty(localName = "rdaGr2:professionOrOccupation")
	public List<Object> getProfessionOrOccupation(){
	    	return XmlMultilingualString.convertToXmlMultilingualStringOrRdfResource(agent.getProfessionOrOccupation());
	}
	
	@JacksonXmlElementWrapper(useWrapping=false)
	@JacksonXmlProperty(localName = "owl:sameAs")
	public List<RdfResource> getSameAs(){
	    	return RdfResource.convertToRdfResource(agent.getSameAs());
	}
	
}
