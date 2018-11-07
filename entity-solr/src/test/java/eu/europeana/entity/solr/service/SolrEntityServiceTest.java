package eu.europeana.entity.solr.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import eu.europeana.api.commons.definitions.search.Query;
import eu.europeana.api.commons.definitions.search.ResultSet;
import eu.europeana.api.commons.definitions.search.impl.QueryImpl;
import eu.europeana.entity.definitions.exceptions.UnsupportedEntityTypeException;
import eu.europeana.entity.definitions.model.Entity;
import eu.europeana.entity.solr.exception.EntityRetrievalException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/entity-solr-context.xml" })
public class SolrEntityServiceTest {

	@Resource
	SolrEntityService solrEntityService;

	private final Logger log = LogManager.getLogger(getClass());

	@Test
	public void testSearchByUrl() throws EntityRetrievalException, UnsupportedEntityTypeException {

		// Concept entity =
		// solrEntityService.searchByUrl("\"http://d-nb.info/gnd/4019862-5\"");
		Entity entity = solrEntityService.searchByUrl("agent", "\"http://data.europeana.eu/agent/base/33286\"");
		assertNotNull(entity);
		assertTrue("http://data.europeana.eu/agent/base/33286".equals(entity.getAbout()));
	}

	@Test
	public void testSearch() throws EntityRetrievalException {

		Query searchQuery = new QueryImpl("\"Dokumentarfilm\"", 10);
		ResultSet<? extends Entity> rs = solrEntityService.search(searchQuery, new String[] { "en", "de", "fr" }, null,
				null);

		assertNotNull(rs.getResults());
		assertTrue(rs.getResultSize() >= 1);
		getLog().info("found results:" + rs.getResultSize());
		getLog().debug(rs.getResults().get(0));
	}

	public Logger getLog() {
		return log;
	}

}
