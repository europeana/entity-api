package eu.europeana.entity.web.xml;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import eu.europeana.entity.definitions.exceptions.UnsupportedEntityTypeException;
import eu.europeana.entity.definitions.model.Entity;
import eu.europeana.entity.solr.model.SolrAgentImpl;
import eu.europeana.entity.solr.model.SolrConceptImpl;
import eu.europeana.entity.solr.model.SolrOrganizationImpl;
import eu.europeana.entity.solr.model.SolrPlaceImpl;
import eu.europeana.entity.web.xml.model.XmlAgentImpl;
import eu.europeana.entity.web.xml.model.XmlConceptImpl;
import eu.europeana.entity.web.xml.model.XmlOrganizationImpl;
import eu.europeana.entity.web.xml.model.XmlPlaceImpl;

public class EntityXmlSerializer {

	private final String XML_HEADER_TAG_CONCEPT = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n" + 
    	    	" <rdf:RDF xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\"\r\n" +  
    	    	"          xmlns:foaf=\"http://xmlns.com/foaf/0.1/\"\r\n" + 
    	    	"         xmlns:dc=\"http://purl.org/dc/elements/1.1/\"\r\n" + 
    	    	"         xmlns:edm=\"http://www.europeana.eu/schemas/edm/\"\r\n" + 
    	    	"          xmlns:skos=\"http://www.w3.org/2004/02/skos/core#\"\r\n >";
    	private final String XML_HEADER_TAG_AGENT = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n" + 
		    	" <rdf:RDF xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\r\n" + 
		    	"         xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\"\r\n" + 
		    	"         xmlns:dc=\"http://purl.org/dc/elements/1.1/\"\r\n" + 
		    	"         xmlns:edm=\"http://www.europeana.eu/schemas/edm/\"\r\n" + 
		    	"         xmlns:foaf=\"http://xmlns.com/foaf/0.1/\"\r\n" + 
		    	"         xmlns:rdaGr2=\"http://rdvocab.info/ElementsGr2/\"\r\n" + 
		    	"         xmlns:owl=\"http://www.w3.org/2002/07/owl#\"\r\n" + 
		    	"         xmlns:skos=\"http://www.w3.org/2004/02/skos/core#\"\r\n" + 
		    	"         xmlns:dcterms=\"http://purl.org/dc/terms/\" >";
    	private final String XML_HEADER_TAG_PLACE = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n" + 
	    	" <rdf:RDF xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\"\r\n" + 
	    	"         xmlns:foaf=\"http://xmlns.com/foaf/0.1/\"\r\n" + 
	    	"         xmlns:dc=\"http://purl.org/dc/elements/1.1/\"\r\n" + 
	    	"         xmlns:edm=\"http://www.europeana.eu/schemas/edm/\"\r\n" + 
	    	"         xmlns:wgs84_pos=\"http://www.w3.org/2003/01/geo/wgs84_pos#\"\r\n" + 
	    	"         xmlns:owl=\"http://www.w3.org/2002/07/owl#\"\r\n" + 
	    	"         xmlns:skos=\"http://www.w3.org/2004/02/skos/core#\"\r\n" + 
	    	"         xmlns:dcterms=\"http://purl.org/dc/terms/\" >";
    	private final String XML_HEADER_TAG_ORGANIZATION = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n" + 
	    	" <rdf:RDF xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\"\r\n" + 
	    	"         xmlns:foaf=\"http://xmlns.com/foaf/0.1/\"\r\n" + 
	    	"         xmlns:dc=\"http://purl.org/dc/elements/1.1/\"\r\n" + 
	    	"         xmlns:edm=\"http://www.europeana.eu/schemas/edm/\"\r\n" + 
	    	"         xmlns:vcard=\"https://www.w3.org/2006/vcard/\"\r\n" + 
	    	"         xmlns:owl=\"http://www.w3.org/2002/07/owl#\"\r\n" + 
	    	"         xmlns:skos=\"http://www.w3.org/2004/02/skos/core#\"\r\n >";
    	private final String XML_END_TAG = "</rdf:RDF>";
    
    	/**
	 * This method serializes Entity object to xml formats.
	 * @param entity The Entity object
	 * @return The serialized entity in xml string format
	 * @throws JsonProcessingException, UnsupportedEntityTypeException
	 */
	public String serializeXml(Entity entity) throws UnsupportedEntityTypeException {
		JacksonXmlModule xmlModule = new JacksonXmlModule();
		xmlModule.setDefaultUseWrapper(true);
		ObjectMapper objectMapper = new XmlMapper(xmlModule);
		objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

		String outputHeader;
		String output = "";
		try {
    		    if(entity instanceof SolrConceptImpl) {
    			XmlConceptImpl xmlElement = new XmlConceptImpl((SolrConceptImpl) entity);
    			output = objectMapper.writeValueAsString(xmlElement);
    			outputHeader = XML_HEADER_TAG_CONCEPT;
    		    }
    		    else if(entity instanceof SolrAgentImpl) {
    			XmlAgentImpl xmlElement = new XmlAgentImpl((SolrAgentImpl) entity);
    			output = objectMapper.writeValueAsString(xmlElement);
    			outputHeader = XML_HEADER_TAG_AGENT;
    		    }
    		    else if(entity instanceof SolrPlaceImpl) {
    			XmlPlaceImpl xmlElement = new XmlPlaceImpl((SolrPlaceImpl) entity);
    			output = objectMapper.writeValueAsString(xmlElement);
    			outputHeader = XML_HEADER_TAG_PLACE;
    		    }
    		    else if(entity instanceof SolrOrganizationImpl) {
    			XmlOrganizationImpl xmlElement = new XmlOrganizationImpl((SolrOrganizationImpl) entity);
    			output = objectMapper.writeValueAsString(xmlElement);
    			outputHeader = XML_HEADER_TAG_ORGANIZATION;
    		    }
    		    else {
    			throw new UnsupportedEntityTypeException("Serialization to xml failed for " + entity.getAbout());
    		    }
		    StringBuilder strBuilder = new StringBuilder();
		    strBuilder.append(outputHeader);
		    strBuilder.append(output);
		    strBuilder.append(XML_END_TAG);
		    output = strBuilder.toString();
		} catch (JsonProcessingException e) {
		    throw new UnsupportedEntityTypeException("Serialization to xml failed for " + entity.getAbout() + e.getMessage());
		}
		    
		return output;
	}
}
