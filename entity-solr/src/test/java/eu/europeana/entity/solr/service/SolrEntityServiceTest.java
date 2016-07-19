package eu.europeana.entity.solr.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import eu.europeana.entity.definitions.model.Concept;
import eu.europeana.entity.definitions.model.search.Query;
import eu.europeana.entity.definitions.model.search.QueryImpl;
import eu.europeana.entity.definitions.model.search.result.ResultSet;
import eu.europeana.entity.solr.exception.EntityRetrievalException;
import eu.europeana.entity.solr.service.impl.SolrEntityServiceImpl;
import eu.europeana.entity.web.model.view.ConceptView;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/entity-solr-test.xml" })
public class SolrEntityServiceTest {

	@Resource
	SolrEntityService solrEntityService;

	private final Logger log = Logger.getLogger(getClass());
	
	@Before
	public void cleanAllSolr() throws EntityRetrievalException {
		/**
		 * clean up all SOLR Annotations
		 */
		((SolrEntityServiceImpl) solrEntityService).cleanUpAll();
	}

	@Test
	public void testSearchByUrl() throws EntityRetrievalException {

		//Concept entity = solrEntityService.searchByUrl("\"http://d-nb.info/gnd/4019862-5\"");
		Concept entity = solrEntityService.searchByUrl("agent", "\"http://data.europeana.eu/agent/base/33286\"");
		assertNotNull(entity);
		assertTrue("http://data.europeana.eu/agent/base/33286".equals(entity.getAbout()));
	}
	
	@Test
	public void testSearch() throws EntityRetrievalException{
		
		Query searchQuery = new QueryImpl("\"Geistliches Drama\"", 10);
		ResultSet<? extends ConceptView> rs = solrEntityService.search(searchQuery);
		
		assertNotNull(rs.getResults());
		assertTrue(rs.getResultSize() >= 1 );
		getLog().info("found results:" + rs.getResultSize());
		getLog().debug(rs.getResults().get(0));
	}

	public Logger getLog() {
		return log;
	}
	
}
