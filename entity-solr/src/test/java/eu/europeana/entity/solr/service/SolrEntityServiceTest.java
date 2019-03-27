package eu.europeana.entity.solr.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.common.params.CommonParams;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import eu.europeana.api.commons.definitions.search.Query;
import eu.europeana.api.commons.definitions.search.ResultSet;
import eu.europeana.api.commons.definitions.search.impl.QueryImpl;
import eu.europeana.entity.definitions.exceptions.UnsupportedEntityTypeException;
import eu.europeana.entity.definitions.model.Entity;
import eu.europeana.entity.definitions.model.vocabulary.WebEntityConstants;
import eu.europeana.entity.solr.exception.EntityRetrievalException;
import eu.europeana.entity.solr.exception.EntitySuggestionException;
import eu.europeana.entity.web.model.view.EntityPreview;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/entity-solr-context.xml" })
public class SolrEntityServiceTest {

	private String TEST_SUGGESTION_TEXT = "moz";
	
	private String RES_SUGGEST = "ResultSet [query=null, results=[entityId: http://data.europeana.eu/agent/base/146951\n" + 
			"preferredLabel: {de=Wolfgang Amadeus Mozart, en=Wolfgang Amadeus Mozart, fr=Wolfgang Amadeus Mozart}, entityId: http://data.europeana.eu/agent/base/145380\n" + 
			"preferredLabel: {de=Moses Mendelssohn, en=Moses Mendelssohn, lv=Mozess Mendelsons, fr=Moses Mendelssohn}, entityId: http://data.europeana.eu/concept/base/45\n" + 
			"preferredLabel: {de=Mosaik, lt=Mozaika, en=Mosaic, fr=Mosaïque}, entityId: http://data.europeana.eu/place/base/216282\n" + 
			"preferredLabel: {de=Birma (Myanmar), ln=Mozambiki, en=Birma (Myanmar), fr=Birmanie}, entityId: http://data.europeana.eu/agent/base/147150\n" + 
			"preferredLabel: {de=Leopold Mozart, en=Leopold Mozart, fr=Leopold Mozart}, entityId: http://data.europeana.eu/place/base/51\n" + 
			"preferredLabel: {de=Mosambik, en=Mozambique, fr=Mozambique}, entityId: http://data.europeana.eu/place/base/134394\n" + 
			"preferredLabel: {de=Woiwodschaft Ermland-Masuren, lt=Varmijos Mozūrų vaivadija, en=Warmian-Masurian Voivodeship, fr=Voïvodie de Varmie-Mazurie}, entityId: http://data.europeana.eu/agent/base/1325\n" + 
			"preferredLabel: {de=Mozart Camargo Guarnieri, en=Camargo Guarnieri, fr=Camargo Guarnieri}, entityId: http://data.europeana.eu/agent/base/55638\n" + 
			"preferredLabel: {de=Leszek Możdżer, no=Leszek Mozdzer, en=Leszek Możdżer, fr=Leszek Możdżer}, entityId: http://data.europeana.eu/agent/base/145487\n" + 
			"preferredLabel: {de=Mozi, en=Mozi, fr=Mozi}], facetFields=null, resultSize=10, searchTime=0]";

	private String RES_SUGGEST_BY_LABEL = "ResultSet [query=null, results=[entityId: http://data.europeana.eu/agent/base/146951\n" + 
			"preferredLabel: {de=Wolfgang Amadeus Mozart, en=Wolfgang Amadeus Mozart, fr=Wolfgang Amadeus Mozart}, entityId: http://data.europeana.eu/agent/base/145380\n" + 
			"preferredLabel: {de=Moses Mendelssohn, en=Moses Mendelssohn, lv=Mozess Mendelsons, fr=Moses Mendelssohn}, entityId: http://data.europeana.eu/concept/base/45\n" + 
			"preferredLabel: {de=Mosaik, lt=Mozaika, en=Mosaic, fr=Mosaïque}, entityId: http://data.europeana.eu/place/base/216282\n" + 
			"preferredLabel: {de=Birma (Myanmar), ln=Mozambiki, en=Birma (Myanmar), fr=Birmanie}, entityId: http://data.europeana.eu/agent/base/147150\n" + 
			"preferredLabel: {de=Leopold Mozart, en=Leopold Mozart, fr=Leopold Mozart}, entityId: http://data.europeana.eu/place/base/51\n" + 
			"preferredLabel: {de=Mosambik, en=Mozambique, fr=Mozambique}, entityId: http://data.europeana.eu/place/base/134394\n" + 
			"preferredLabel: {de=Woiwodschaft Ermland-Masuren, lt=Varmijos Mozūrų vaivadija, en=Warmian-Masurian Voivodeship, fr=Voïvodie de Varmie-Mazurie}, entityId: http://data.europeana.eu/agent/base/1325\n" + 
			"preferredLabel: {de=Mozart Camargo Guarnieri, en=Camargo Guarnieri, fr=Camargo Guarnieri}, entityId: http://data.europeana.eu/agent/base/55638\n" + 
			"preferredLabel: {de=Leszek Możdżer, en=Leszek Możdżer, fr=Leszek Możdżer}, entityId: http://data.europeana.eu/agent/base/145487\n" + 
			"preferredLabel: {de=Mozi, en=Mozi, fr=Mozi}], facetFields=null, resultSize=10, searchTime=0]";
	
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

	@Test
	public void testCompareSuggestByLableWithRegularSuggest() throws EntityRetrievalException, EntitySuggestionException {
		
		Query searchQuery = new QueryImpl(TEST_SUGGESTION_TEXT, 10);
		ResultSet<? extends EntityPreview> resSuggest = solrEntityService.suggest(searchQuery, new String[] { "en", "de", "fr" }, null,
				null, 10);
		
		SolrQuery solrQuery;
		String[] searchParams;
		String[] fields;
		searchParams = new String[]{CommonParams.SORT, "derived_score desc", "q.op", "AND"};
		fields = new String[]{"id", "payload", "derived_score"};
		solrQuery = new SolrQuery(
				CommonParams.Q, WebEntityConstants.FIELD_LABEL + ":(" + TEST_SUGGESTION_TEXT + "*)", searchParams);		
		solrQuery.setFields(fields);
		ResultSet<? extends EntityPreview> resSuggestByLabel = solrEntityService.suggestByLabel(
				TEST_SUGGESTION_TEXT, new String[] { "en", "de", "fr" }, null,
				null, 10);

		assertNotNull(resSuggest.getResults());
		assertTrue(resSuggest.getResultSize() >= 1);
		getLog().info("found results:" + resSuggest.getResultSize());
		getLog().debug(resSuggest.getResults().get(0));
		
		assertNotNull(resSuggestByLabel.getResults());
		assertTrue(resSuggestByLabel.getResultSize() >= 1);
		getLog().info("found results:" + resSuggestByLabel.getResultSize());
		getLog().debug(resSuggestByLabel.getResults().get(0));
		
		assertTrue(resSuggest.getResultSize() == resSuggestByLabel.getResultSize());
		assertTrue(compareSuggestionResults(resSuggest, resSuggestByLabel));
		
		assertTrue(resSuggest.toString().equals(RES_SUGGEST));
		assertTrue(resSuggestByLabel.toString().equals(RES_SUGGEST_BY_LABEL));
	}

	/**
	 * This method compares responses of suggest and suggestByLabel search
	 * by EntityId
	 * @param resSuggest
	 * @param resSuggestByLabel
	 * @return true if the number and content of EntityIDs in both responses is the same
	 */
	private boolean compareSuggestionResults(
			ResultSet<? extends EntityPreview> resSuggest, ResultSet<? extends EntityPreview> resSuggestByLabel) {
        boolean res = true;
        

        List<String> resSuggestList= new ArrayList<String>();
        List<String> resSuggestByLabelList= new ArrayList<String>();
        for (EntityPreview entity : resSuggest.getResults()) {
        	resSuggestList.add(entity.getEntityId());
        }
        for (EntityPreview entity : resSuggestByLabel.getResults()) {
        	resSuggestByLabelList.add(entity.getEntityId());
        }
        
        // intersection as set
        Set<String> intersect = new HashSet<String>(resSuggestList);
        intersect.retainAll(resSuggestByLabelList);
        
        if (intersect.size() != resSuggest.getResultSize()) {
        	res = false;
        }
        return res;        
	}
	
	public Logger getLog() {
		return log;
	}

}
