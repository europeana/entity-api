package eu.europeana.entity.solr.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;

import eu.europeana.entity.definitions.model.search.Query;
import eu.europeana.entity.definitions.model.search.result.ResultSet;
import eu.europeana.entity.solr.view.ConceptViewAdapter;
import eu.europeana.entity.web.model.view.ConceptView;

public abstract class BaseEntityService{
	
	private static final int DEFAULT_FACET_LIMIT = 50;
	
	private final Logger log = Logger.getLogger(getClass());

	protected SolrQuery toSolrQuery(Query searchQuery) {

		SolrQuery solrQuery = new SolrQuery();

		solrQuery.setQuery(searchQuery.getQuery());

		solrQuery.setRows(searchQuery.getRows());
		solrQuery.setStart(searchQuery.getStart());

		if (searchQuery.getFilters() != null)
			solrQuery.addFilterQuery(searchQuery.getFilters());

		if (searchQuery.getFacetFields() != null) {
			solrQuery.setFacet(true);
			solrQuery.addFacetField(searchQuery.getFacetFields());
			solrQuery.setFacetLimit(DEFAULT_FACET_LIMIT);
		}

		if (searchQuery.getSort() != null) {
			solrQuery.setSort(searchQuery.getSort(), SolrQuery.ORDER.valueOf(searchQuery.getSortOrder()));
		}

		solrQuery.setFields(searchQuery.getViewFields());

		return solrQuery;
	}

	@SuppressWarnings("unchecked")
	protected <T extends ConceptView> ResultSet<T> buildResultSet(QueryResponse rsp) {
		return (ResultSet<T>) buildResultSet(rsp, ConceptViewAdapter.class);
	}

	protected <T extends ConceptView> ResultSet<T> buildResultSet(QueryResponse rsp, Class<T> conceptViewClass) {

		ResultSet<T> resultSet = new ResultSet<>();
		List<T> beans = (List<T>) rsp.getBeans(conceptViewClass);
		resultSet.setResults(beans);

		resultSet.setResultSize(rsp.getResults().getNumFound());

		return resultSet;
	}

	
	public Logger getLog() {
		return log;
	}

}
