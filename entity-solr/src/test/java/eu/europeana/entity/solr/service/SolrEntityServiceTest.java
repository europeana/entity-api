package eu.europeana.entity.solr.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import eu.europeana.entity.definitions.model.Concept;
import eu.europeana.entity.definitions.model.search.Query;
import eu.europeana.entity.definitions.model.search.QueryImpl;
import eu.europeana.entity.definitions.model.search.result.ResultSet;
import eu.europeana.entity.solr.exception.EntityServiceException;
import eu.europeana.entity.solr.model.SolrConceptImpl;
import eu.europeana.entity.solr.service.impl.SolrEntityServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/entity-solr-test.xml" })
public class SolrEntityServiceTest {

	@Resource
	SolrEntityService solrEntityService;

	private final Logger log = Logger.getLogger(getClass());
	
	@Before
	public void cleanAllSolr() throws EntityServiceException {
		/**
		 * clean up all SOLR Annotations
		 */
		((SolrEntityServiceImpl) solrEntityService).cleanUpAll();
	}

	@Test
	public void testSearchByUrl() throws EntityServiceException {

		Concept entity = solrEntityService.searchByUrl("\"http://d-nb.info/gnd/4019862-5\"");
		assertNotNull(entity);
		assertTrue("http://d-nb.info/gnd/4019862-5".equals(entity.getAbout()));
	}
	
	@Test
	public void testSearch() throws EntityServiceException{
		
		Query searchQuery = new QueryImpl("\"Geistliches Drama\"", 10);
		ResultSet<? extends Concept> rs = solrEntityService.search(searchQuery);
		
		assertNotNull(rs.getResults());
		assertTrue(rs.getResultSize() >= 1 );
		getLog().info("found results:" + rs.getResultSize());
		getLog().debug(rs.getResults().get(0));
	}

	public Logger getLog() {
		return log;
	}
	
}
