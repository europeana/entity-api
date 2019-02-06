package eu.europeana.entity.web.test;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import eu.europeana.api.common.config.I18nConstants;
import eu.europeana.api.commons.web.exception.HttpException;
import eu.europeana.corelib.edm.model.schemaorg.Thing;
import eu.europeana.entity.definitions.exceptions.UnsupportedEntityTypeException;
import eu.europeana.entity.definitions.model.Concept;
import eu.europeana.entity.definitions.model.Entity;
import eu.europeana.entity.definitions.model.impl.BaseConcept;
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
	
	// test uri for mathematics
	public final String TEST_ENTITY_URI = "http://data.europeana.eu/concept/base/153";
	public final String TEST_ENTITY_TYPE = "concept";
	
	@Resource
	SolrEntityService solrEntityService;
	
	/**
	 * This test investigates EDM entity Concept mapping to Schema.org
	 * @throws HttpException 
	 */
	@Test
	public void testConceptMappingToSchemaOrg() throws HttpException {
		
		String entityUri = TEST_ENTITY_URI;
		Entity result;
		Concept concept;
		
		try {
			result = solrEntityService.searchByUrl(TEST_ENTITY_TYPE, entityUri);
		} catch (EntityRetrievalException e) {
			throw new HttpException(e.getMessage(), I18nConstants.SERVER_ERROR_CANT_RETRIEVE_URI,
					new String[] { entityUri }, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (UnsupportedEntityTypeException e) {
			throw new HttpException(null, I18nConstants.UNSUPPORTED_ENTITY_TYPE, new String[] { TEST_ENTITY_TYPE },
					HttpStatus.NOT_FOUND, null);
		}	
		
		Map<String, List<String>> prefLabel = ((BaseConcept) result).getPrefLabel();
        Assert.assertNotNull(prefLabel);        
		
		concept = (BaseConcept) result;
        Thing conceptObject = new Thing();
        SchemaOrgUtils.processConcept(concept, conceptObject);
        String output = SchemaOrgUtils.thingToSchemaOrg(conceptObject);
        Assert.assertNotNull(output);        
	}
	
}
