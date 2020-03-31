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
    public void testConceptSchemaOrgSerialization() throws HttpException, IOException, UnsupportedEntityTypeException {

	String entityUri = TEST_CONCEPT_ENTITY_URI;
	Concept concept;
        concept = (Concept) solrEntityService.searchByUrl(TEST_CONCEPT_ENTITY_TYPE, entityUri);

	String output;
	output = (new EntitySchemaOrgSerializer()).serializeEntity(concept);

        validateSerializationOutput(output, TEST_TXT_CONCEPT_FILE);	
    }

    /**
     * This method validates serialization outputs for
     * different entity types
     * @param output The Entity output object
     * @param filename The path to the output file for an entity
     * @throws IOException
     */
    private void validateSerializationOutput(String output, String filename)
	    throws IOException {
	Assert.assertNotNull(output);

	String expectedOutput;
	expectedOutput = FileUtils.readFileToString(new File(filename));
        assertEquals(expectedOutput.length(), output.length());
    }

    /**
     * This method writes serialization output for Entity object to provided file.
     * It is used to create expected output
     * @param output
     * @param filename
     * @throws IOException
     */
    public void writeEntityOutputToFile(String output, String filename)
	    throws IOException {
	FileUtils.writeStringToFile(new File(filename), output);
    }
    
//    @Test
    public void writeEntityOutputToFileTest()
	    throws IOException {
	String output = "";
	FileUtils.writeStringToFile(new File(TEST_TXT_CONCEPT_FILE), output);
	FileUtils.writeStringToFile(new File(TEST_TXT_AGENT_FILE), output);
	FileUtils.writeStringToFile(new File(TEST_TXT_PLACE_FILE), output);
	FileUtils.writeStringToFile(new File(TEST_TXT_ORGANISATION_FILE), output);
    }
  
    /**
     * This test investigates EDM entity Agent mapping to Schema.org
     * 
     * @throws HttpException
     * @throws IOException
     * @throws UnsupportedEntityTypeException 
     * @throws EntityRetrievalException 
     */
    @Test
    public void testAgentSchemaOrgSerialization() throws HttpException, IOException, EntityRetrievalException, UnsupportedEntityTypeException {

	String entityUri = TEST_AGENT_ENTITY_URI;
	Agent agent;
	String output = null;

        agent = (Agent) solrEntityService.searchByUrl(TEST_AGENT_ENTITY_TYPE, entityUri);

	Person agentObject = new Person();
	SchemaOrgUtils.processAgent(agent, agentObject);

	JsonLdSerializer serializer = new JsonLdSerializer();
        output = serializer.serialize(agentObject);

	String expectedOutput;
	expectedOutput = FileUtils.readFileToString(new File(TEST_TXT_AGENT_FILE));
        assertEquals(expectedOutput.length(), output.length());
    }

    /**
     * This test investigates EDM entity Place mapping to Schema.org
     * 
     * @throws HttpException
     * @throws IOException
     * @throws UnsupportedEntityTypeException 
     * @throws EntityRetrievalException 
     */
    @Test
    public void testPlaceSchemaOrgSerialization() throws HttpException, IOException, EntityRetrievalException, UnsupportedEntityTypeException {

	String entityUri = TEST_PLACE_ENTITY_URI;
	eu.europeana.corelib.definitions.edm.entity.Place place;
	String output = null;

        place = (eu.europeana.corelib.definitions.edm.entity.Place) solrEntityService
		    .searchByUrl(TEST_PLACE_ENTITY_TYPE, entityUri);

	Map<String, List<String>> isPartOf = place.getIsPartOf();
	Assert.assertNotNull(isPartOf);

	Place placeObject = new Place();
	SchemaOrgUtils.processPlace(place, placeObject);

	JsonLdSerializer serializer = new JsonLdSerializer();
        output = serializer.serialize(placeObject);

	String expectedOutput;
	expectedOutput = FileUtils.readFileToString(new File(TEST_TXT_PLACE_FILE));
        assertEquals(expectedOutput.length(), output.length());	
    }

    /**
     * This test investigates EDM entity Organization mapping to Schema.org
     * 
     * @throws HttpException
     * @throws IOException
     * @throws UnsupportedEntityTypeException 
     * @throws EntityRetrievalException 
     */
    @Test
    public void testOrganizationSchemaOrgSerialization() throws HttpException, IOException, EntityRetrievalException, UnsupportedEntityTypeException {

	String entityUri = TEST_ORGANIZATION_ENTITY_URI;
	Organization organization;
	String output = null;

        organization = (Organization) solrEntityService
		    .searchByUrl(TEST_ORGANIZATION_ENTITY_TYPE, entityUri);

	ContextualEntity organizationObject = new EdmOrganization();
	SchemaOrgUtils.processEntity(organization, organizationObject);

	JsonLdSerializer serializer = new JsonLdSerializer();
        output = serializer.serialize(organizationObject);

	String expectedOutput;
	expectedOutput = FileUtils.readFileToString(new File(TEST_TXT_ORGANISATION_FILE));
        assertEquals(expectedOutput.length(), output.length());
    }

}
