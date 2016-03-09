package eu.europeana.entity.solr.service.impl;

import java.util.List;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;

import eu.europeana.entity.definitions.model.search.Query;
import eu.europeana.entity.definitions.model.search.result.ResultSet;
import eu.europeana.entity.solr.view.ConceptViewAdapter;
import eu.europeana.entity.web.model.view.ConceptView;

public class SolrEntityUtils {

	private static final int DEFAULT_FACET_LIMIT = 50;

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

	protected <T extends ConceptView> ResultSet<T> buildResultSet(QueryResponse rsp) {

		ResultSet<T> resultSet = new ResultSet<>();
		@SuppressWarnings("unchecked")
		List<T> beans = (List<T>) rsp.getBeans(ConceptViewAdapter.class);
		resultSet.setResults(beans);
		
		resultSet.setResultSize(rsp.getResults().getNumFound());

		return resultSet;
	}
}
