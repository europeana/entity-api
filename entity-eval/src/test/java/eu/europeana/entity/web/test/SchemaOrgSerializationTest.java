package eu.europeana.entity.web.test;

import static org.junit.Assert.assertEquals;

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
import eu.europeana.corelib.edm.model.schemaorg.ContextualEntity;
import eu.europeana.corelib.edm.model.schemaorg.EdmOrganization;
import eu.europeana.corelib.edm.model.schemaorg.Person;
import eu.europeana.corelib.edm.model.schemaorg.Place;
import eu.europeana.corelib.edm.utils.JsonLdSerializer;
import eu.europeana.corelib.edm.utils.SchemaOrgUtils;
import eu.europeana.entity.definitions.exceptions.UnsupportedEntityTypeException;
import eu.europeana.entity.definitions.model.Concept;
import eu.europeana.entity.definitions.model.Entity;
import eu.europeana.entity.solr.exception.EntityRetrievalException;
import eu.europeana.entity.solr.service.SolrEntityService;
import eu.europeana.entity.web.jsonld.EntitySchemaOrgSerializer;

/**
 * This class investigates best way to implement Schema.org mapping
 * 
 * @author GrafR
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/entity-solr-context.xml" })
public class SchemaOrgSerializationTest {

    // test URI's and types
    // byzantine art
    public final String TEST_CONCEPT_ENTITY_URI = "http://data.europeana.eu/concept/base/207"; 
    public final String TEST_CONCEPT_ENTITY_TYPE = "concept";
    // da vinci
    public final String TEST_AGENT_ENTITY_URI = "http://data.europeana.eu/agent/base/146741";
    public final String TEST_AGENT_ENTITY_TYPE = "agent";
    // paris
    public final String TEST_PLACE_ENTITY_URI = "http://data.europeana.eu/place/base/41948";
    public final String TEST_PLACE_ENTITY_TYPE = "place";
    // internet archive
    public final String TEST_ORGANIZATION_ENTITY_URI = "http://data.europeana.eu/organization/1482250000004505021";
    public final String TEST_ORGANIZATION_ENTITY_TYPE = "organization";
    
    // output files
    public final String TEST_TXT_CONCEPT_FILE = "/datasets/concept-art-output.txt";
    public final String TEST_TXT_AGENT_FILE = "/datasets/agent-output.txt";
    public final String TEST_TXT_PLACE_FILE = "/datasets/place-output.txt";
    public final String TEST_TXT_ORGANISATION_FILE = "/datasets/organisation-archive-output.txt";

    
    @Resource
    SolrEntityService solrEntityService;

    private static final Logger LOG = LogManager.getLogger(SchemaOrgSerializationTest.class);

    @Test
    public void testConceptMappingToSchemaOrg() throws HttpException, IOException, UnsupportedEntityTypeException {

	String entityUri = TEST_CONCEPT_ENTITY_URI;
	Concept concept;

	try {
	    concept = (Concept) solrEntityService.searchByUrl(TEST_CONCEPT_ENTITY_TYPE, entityUri);
	} catch (EntityRetrievalException e) {
	    throw new HttpException(e.getMessage(), I18nConstants.SERVER_ERROR_CANT_RETRIEVE_URI,
		    new String[] { entityUri }, HttpStatus.INTERNAL_SERVER_ERROR);
	} catch (UnsupportedEntityTypeException e) {
	    throw new HttpException(null, I18nConstants.UNSUPPORTED_ENTITY_TYPE,
		    new String[] { TEST_CONCEPT_ENTITY_TYPE }, HttpStatus.NOT_FOUND, null);
	}

        validateSerializationOutput(concept, TEST_TXT_CONCEPT_FILE);	
    }

    /**
     * This method validates serialization outputs for
     * different entity types
     * @param entity The Entity object
     * @param filename The path to the output file for an entity
     * @throws HttpException
     * @throws UnsupportedEntityTypeException
     * @throws IOException
     */
    private void validateSerializationOutput(Entity entity, String filename)
	    throws HttpException, UnsupportedEntityTypeException, IOException {
	String output;
	output = (new EntitySchemaOrgSerializer()).serializeEntity(entity);
	Assert.assertNotNull(output);

	// FileUtils.writeStringToFile(new File(filename), output); // used to create expected output
	String expectedOutput;
	expectedOutput = FileUtils.readFileToString(new File(filename));
        assertEquals(expectedOutput.length(), output.length());
	FileUtils.writeStringToFile(new File(filename), output);
    }

    /**
     * This test investigates EDM entity Agent mapping to Schema.org
     * 
     * @throws HttpException
     * @throws IOException
     */
    @Test
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
	    throw new HttpException(null, I18nConstants.UNSUPPORTED_ENTITY_TYPE,
		    new String[] { TEST_AGENT_ENTITY_TYPE }, HttpStatus.NOT_FOUND, null);
	}

	Person agentObject = new Person();
	SchemaOrgUtils.processAgent(agent, agentObject);

	JsonLdSerializer serializer = new JsonLdSerializer();
	try {
	    output = serializer.serialize(agentObject);
	} catch (IOException e) {
	    LOG.error("Serialization to schema.org failed for " + agentObject.getId(), e);
	}

	// FileUtils.writeStringToFile(new File(TEST_TXT_AGENT_FILE), output); // used to create expected output
	String expectedOutput;
	expectedOutput = FileUtils.readFileToString(new File(TEST_TXT_AGENT_FILE));
        assertEquals(expectedOutput.length(), output.length());
	FileUtils.writeStringToFile(new File(TEST_TXT_AGENT_FILE), output);
    }

    /**
     * This test investigates EDM entity Place mapping to Schema.org
     * 
     * @throws HttpException
     * @throws IOException
     */
    @Test
    public void testPlaceMappingToSchemaOrg() throws HttpException, IOException {

	String entityUri = TEST_PLACE_ENTITY_URI;
	eu.europeana.corelib.definitions.edm.entity.Place place;
	String output = null;

	try {
	    place = (eu.europeana.corelib.definitions.edm.entity.Place) solrEntityService
		    .searchByUrl(TEST_PLACE_ENTITY_TYPE, entityUri);
	} catch (EntityRetrievalException e) {
	    throw new HttpException(e.getMessage(), I18nConstants.SERVER_ERROR_CANT_RETRIEVE_URI,
		    new String[] { entityUri }, HttpStatus.INTERNAL_SERVER_ERROR);
	} catch (UnsupportedEntityTypeException e) {
	    throw new HttpException(null, I18nConstants.UNSUPPORTED_ENTITY_TYPE,
		    new String[] { TEST_PLACE_ENTITY_TYPE }, HttpStatus.NOT_FOUND, null);
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

	// FileUtils.writeStringToFile(new File(TEST_TXT_PLACE_FILE), output); // used to create expected output	
	String expectedOutput;
	expectedOutput = FileUtils.readFileToString(new File(TEST_TXT_PLACE_FILE));
        assertEquals(expectedOutput.length(), output.length());
	FileUtils.writeStringToFile(new File(TEST_TXT_PLACE_FILE), output);	
    }

    /**
     * This test investigates EDM entity Organization mapping to Schema.org
     * 
     * @throws HttpException
     * @throws IOException
     */
    @Test
    public void testOrganizationMappingToSchemaOrg() throws HttpException, IOException {

	String entityUri = TEST_ORGANIZATION_ENTITY_URI;
	Organization organization;
	String output = null;

	try {
	    organization = (Organization) solrEntityService
		    .searchByUrl(TEST_ORGANIZATION_ENTITY_TYPE, entityUri);
	} catch (EntityRetrievalException e) {
	    throw new HttpException(e.getMessage(), I18nConstants.SERVER_ERROR_CANT_RETRIEVE_URI,
		    new String[] { entityUri }, HttpStatus.INTERNAL_SERVER_ERROR);
	} catch (UnsupportedEntityTypeException e) {
	    throw new HttpException(null, I18nConstants.UNSUPPORTED_ENTITY_TYPE,
		    new String[] { TEST_ORGANIZATION_ENTITY_TYPE }, HttpStatus.NOT_FOUND, null);
	}

	ContextualEntity organizationObject = new EdmOrganization();
	SchemaOrgUtils.processEntity(organization, organizationObject);

	JsonLdSerializer serializer = new JsonLdSerializer();
	try {
	    output = serializer.serialize(organizationObject);
	} catch (IOException e) {
	    LOG.error("Serialization to schema.org failed for " + organizationObject.getId(), e);
	}

	// FileUtils.writeStringToFile(new File(TEST_TXT_ORGANISATION_FILE), output); // used to create expected output	
	String expectedOutput;
	expectedOutput = FileUtils.readFileToString(new File(TEST_TXT_ORGANISATION_FILE));
        assertEquals(expectedOutput.length(), output.length());
	FileUtils.writeStringToFile(new File(TEST_TXT_ORGANISATION_FILE), output);	
    }

}
