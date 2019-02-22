package eu.europeana.entity.web.test;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import eu.europeana.api.common.config.I18nConstants;
import eu.europeana.api.commons.web.exception.HttpException;
import eu.europeana.corelib.definitions.edm.entity.Agent;
import eu.europeana.corelib.definitions.edm.entity.Organization;
import eu.europeana.corelib.edm.model.schemaorg.Person;
import eu.europeana.corelib.edm.model.schemaorg.Place;
import eu.europeana.corelib.edm.model.schemaorg.Thing;
import eu.europeana.corelib.edm.utils.JsonLdSerializer;
import eu.europeana.corelib.edm.utils.SchemaOrgUtils;
import eu.europeana.entity.definitions.exceptions.UnsupportedEntityTypeException;
import eu.europeana.entity.definitions.model.Concept;
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
	// bnf
	public final String TEST_ORGANIZATION_ENTITY_URI = "http://data.europeana.eu/organization/1482250000002112001";
	public final String TEST_ORGANIZATION_ENTITY_TYPE = "organization";
	
	@Resource
	SolrEntityService solrEntityService;
	
    private static final Logger LOG = LogManager.getLogger(ConceptEntitySchemaMapTest.class);
	
	/**
	 * This test investigates EDM entity Concept mapping to Schema.org
	 * @throws HttpException 
	 * @throws IOException 
	 */
//	@Test
	public void testConceptMappingToSchemaOrg() throws HttpException, IOException {
		
		String entityUri = TEST_CONCEPT_ENTITY_URI;
		Concept concept;
		String output = null;
		
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
        
        JsonLdSerializer serializer = new JsonLdSerializer();
        try {
            output = serializer.serialize(conceptObject);
        } catch (IOException e) {
            LOG.error("Serialization to schema.org failed for " + conceptObject.getId(), e);
        }
        
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
		String output = null;
		
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
        
        JsonLdSerializer serializer = new JsonLdSerializer();
        try {
            output = serializer.serialize(agentObject);
        } catch (IOException e) {
            LOG.error("Serialization to schema.org failed for " + agentObject.getId(), e);
        }
        
        Assert.assertNotNull(output);  
        FileUtils.writeStringToFile(new File("agent-output.txt"), output);
	}
	
	/**
	 * This test investigates EDM entity Place mapping to Schema.org
	 * @throws HttpException 
	 * @throws IOException 
	 */
	@Test
	public void testPlaceMappingToSchemaOrg() throws HttpException, IOException {
		
		String entityUri = TEST_PLACE_ENTITY_URI;
		eu.europeana.corelib.definitions.edm.entity.Place place;
		String output = null;
		
		try {
			place = (eu.europeana.corelib.definitions.edm.entity.Place) solrEntityService.searchByUrl(
					TEST_PLACE_ENTITY_TYPE, entityUri);
		} catch (EntityRetrievalException e) {
			throw new HttpException(e.getMessage(), I18nConstants.SERVER_ERROR_CANT_RETRIEVE_URI,
					new String[] { entityUri }, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (UnsupportedEntityTypeException e) {
			throw new HttpException(null, I18nConstants.UNSUPPORTED_ENTITY_TYPE, new String[] { TEST_PLACE_ENTITY_TYPE },
					HttpStatus.NOT_FOUND, null);
		}	
		
		Map<String, List<String>> isPartOf = place.getIsPartOf();
        Assert.assertNotNull(isPartOf);        
		
        Place placeObject = new Place();
        SchemaOrgUtils.processPlace(place, placeObject);
        
        JsonLdSerializer serializer = new JsonLdSerializer();
        try {
            output = serializer.serialize(placeObject);
        } catch (IOException e) {
            LOG.error("Serialization to schema.org failed for " + placeObject.getId(), e);
        }        
        
        Assert.assertNotNull(output);        
        FileUtils.writeStringToFile(new File("place-output.txt"), output);
	}
	
	/**
	 * This test investigates EDM entity Organization mapping to Schema.org
	 * @throws HttpException 
	 * @throws IOException 
	 */
	@Test
	public void testOrganizationMappingToSchemaOrg() throws HttpException, IOException {
		
		String entityUri = TEST_ORGANIZATION_ENTITY_URI;
		eu.europeana.corelib.definitions.edm.entity.Organization organization;
		String output = null;
		
		try {
			organization = (eu.europeana.corelib.definitions.edm.entity.Organization) 
					solrEntityService.searchByUrl(TEST_ORGANIZATION_ENTITY_TYPE, entityUri);
		} catch (EntityRetrievalException e) {
			throw new HttpException(e.getMessage(), I18nConstants.SERVER_ERROR_CANT_RETRIEVE_URI,
					new String[] { entityUri }, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (UnsupportedEntityTypeException e) {
			throw new HttpException(null, I18nConstants.UNSUPPORTED_ENTITY_TYPE, new String[] { TEST_ORGANIZATION_ENTITY_TYPE },
					HttpStatus.NOT_FOUND, null);
		}	
		
		eu.europeana.corelib.edm.model.schemaorg.Organization organizationObject = 
				new eu.europeana.corelib.edm.model.schemaorg.Organization();
        SchemaOrgUtils.processEntity(organization, organizationObject);
        
        JsonLdSerializer serializer = new JsonLdSerializer();
        try {
            output = serializer.serialize(organizationObject);
        } catch (IOException e) {
            LOG.error("Serialization to schema.org failed for " + organizationObject.getId(), e);
        }
        
        Assert.assertNotNull(output);  
        FileUtils.writeStringToFile(new File("organization-output.txt"), output);
	}
	
}
