package eu.europeana.entity.web.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import eu.europeana.api.common.config.I18nConstants;
import eu.europeana.api.commons.web.exception.HttpException;
import eu.europeana.corelib.definitions.edm.entity.Agent;
import eu.europeana.corelib.edm.model.schemaorg.Person;
import eu.europeana.corelib.edm.model.schemaorg.Place;
import eu.europeana.corelib.edm.model.schemaorg.Thing;
import eu.europeana.entity.definitions.exceptions.UnsupportedEntityTypeException;
import eu.europeana.entity.definitions.model.Concept;
import eu.europeana.entity.definitions.model.Entity;
import eu.europeana.entity.definitions.model.impl.BaseConcept;
import eu.europeana.entity.definitions.model.impl.BasePlace;
import eu.europeana.entity.solr.exception.EntityRetrievalException;
import eu.europeana.entity.solr.service.SolrEntityService;

/**
 * This class investigates best way to implement Schema.org mapping
 * @author GrafR
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/entity-solr-context.xml" })
public class ConceptEntitySchemaMapTest {
	
	// test URI's and types
	// mathematics
	public final String TEST_CONCEPT_ENTITY_URI = "http://data.europeana.eu/concept/base/153";
	public final String TEST_CONCEPT_ENTITY_TYPE = "concept";	
	// da vinci
	public final String TEST_AGENT_ENTITY_URI = "http://data.europeana.eu/agent/base/146741";
	public final String TEST_AGENT_ENTITY_TYPE = "agent";	
	// paris
	public final String TEST_PLACE_ENTITY_URI = "http://data.europeana.eu/place/base/41948";
	public final String TEST_PLACE_ENTITY_TYPE = "place";
	
	@Resource
	SolrEntityService solrEntityService;
	
	/**
	 * This test investigates EDM entity Concept mapping to Schema.org
	 * @throws HttpException 
	 * @throws IOException 
	 */
	@Test
	public void testConceptMappingToSchemaOrg() throws HttpException, IOException {
		
		String entityUri = TEST_CONCEPT_ENTITY_URI;
		Concept concept;
		
		try {
			concept = (Concept) solrEntityService.searchByUrl(TEST_CONCEPT_ENTITY_TYPE, entityUri);
		} catch (EntityRetrievalException e) {
			throw new HttpException(e.getMessage(), I18nConstants.SERVER_ERROR_CANT_RETRIEVE_URI,
					new String[] { entityUri }, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (UnsupportedEntityTypeException e) {
			throw new HttpException(null, I18nConstants.UNSUPPORTED_ENTITY_TYPE, new String[] { TEST_CONCEPT_ENTITY_TYPE },
					HttpStatus.NOT_FOUND, null);
		}	
		
		Map<String, List<String>> prefLabel = concept.getPrefLabel();
        Assert.assertNotNull(prefLabel);        
		
        Thing conceptObject = new Thing();
        SchemaOrgUtils.processConcept(concept, conceptObject);
        String output = SchemaOrgUtils.thingToSchemaOrg(conceptObject);
        Assert.assertNotNull(output);        
        FileUtils.writeStringToFile(new File("concept-output.txt"), output);        
	}
	
	/**
	 * This test investigates EDM entity Agent mapping to Schema.org
	 * @throws HttpException 
	 * @throws IOException 
	 */
//	@Test
	public void testAgentMappingToSchemaOrg() throws HttpException, IOException {
		
		String entityUri = TEST_AGENT_ENTITY_URI;
		Agent agent;
		
		try {
			agent = (Agent) solrEntityService.searchByUrl(TEST_AGENT_ENTITY_TYPE, entityUri);
		} catch (EntityRetrievalException e) {
			throw new HttpException(e.getMessage(), I18nConstants.SERVER_ERROR_CANT_RETRIEVE_URI,
					new String[] { entityUri }, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (UnsupportedEntityTypeException e) {
			throw new HttpException(null, I18nConstants.UNSUPPORTED_ENTITY_TYPE, new String[] { TEST_AGENT_ENTITY_TYPE },
					HttpStatus.NOT_FOUND, null);
		}	
		
		Person agentObject = new Person();
        SchemaOrgUtils.processAgent(agent, agentObject);
        String output = SchemaOrgUtils.thingToSchemaOrg(agentObject);
        Assert.assertNotNull(output);  
        FileUtils.writeStringToFile(new File("agent-output.txt"), output);
	}
	
	/**
	 * This test investigates EDM entity Place mapping to Schema.org
	 * @throws HttpException 
	 */
//	@Test
	public void testPlaceMappingToSchemaOrg() throws HttpException {
		
		String entityUri = TEST_PLACE_ENTITY_URI;
		Entity result;
		eu.europeana.corelib.definitions.edm.entity.Place place;
		
		try {
			result = solrEntityService.searchByUrl(TEST_PLACE_ENTITY_TYPE, entityUri);
		} catch (EntityRetrievalException e) {
			throw new HttpException(e.getMessage(), I18nConstants.SERVER_ERROR_CANT_RETRIEVE_URI,
					new String[] { entityUri }, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (UnsupportedEntityTypeException e) {
			throw new HttpException(null, I18nConstants.UNSUPPORTED_ENTITY_TYPE, new String[] { TEST_PLACE_ENTITY_TYPE },
					HttpStatus.NOT_FOUND, null);
		}	
		
		Map<String, List<String>> isPartOf = ((BasePlace) result).getIsPartOf();
        Assert.assertNotNull(isPartOf);        
		
		place = (BasePlace) result;
        Place placeObject = new Place();
        SchemaOrgUtils.processPlace(place, placeObject);
        String output = SchemaOrgUtils.thingToSchemaOrg(placeObject);
        Assert.assertNotNull(output);        
	}
	
}
