package eu.europeana.entity.solr.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.util.SimpleOrderedMap;

import eu.europeana.entity.definitions.model.search.Query;
import eu.europeana.entity.definitions.model.search.result.ResultSet;
import eu.europeana.entity.solr.exception.EntityServiceException;
import eu.europeana.entity.solr.view.ConceptViewAdapter;
import eu.europeana.entity.web.model.view.ConceptView;
import eu.europeana.entity.web.model.view.EntityPreview;

public class SolrEntityUtils {

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

	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected <T extends EntityPreview> ResultSet<T> buildSuggestionSet(QueryResponse rsp, String language, int rows,
			Class<T> entityPreviewClass) throws EntityServiceException {

		ResultSet<T> resultSet = new ResultSet<>();

		Map<String, Object> suggest = (Map<String, Object>) rsp.getResponse().get("suggest");

		SimpleOrderedMap exactMatchGroup = (SimpleOrderedMap) suggest.get("suggestEntity_" + language + "_exact");
		SimpleOrderedMap fuzzyMatchGroup = (SimpleOrderedMap) suggest.get("suggestEntity_" + language + "_fuzzy");

		List<SimpleOrderedMap> exactSuggestions = (List<SimpleOrderedMap>) ((SimpleOrderedMap) exactMatchGroup
				.getVal(0)).get("suggestions");
		List<SimpleOrderedMap> fuzzySuggestions = (List<SimpleOrderedMap>) ((SimpleOrderedMap) fuzzyMatchGroup
				.getVal(0)).get("suggestions");

		List<T> beans = extractBeans(exactSuggestions, fuzzySuggestions, rows, entityPreviewClass);

		resultSet.setResults(beans);
		resultSet.setResultSize(beans.size());

		return resultSet;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private <T extends EntityPreview> List<T> extractBeans(List<SimpleOrderedMap> exactSuggestions,
			List<SimpleOrderedMap> fuzzySuggestions, int rows, Class<T> entityPreviewClass)
					throws EntityServiceException {
		Set<String> ids = new HashSet<String>();
		String term;
		String id;
		T preview;
		List<T> beans = new ArrayList<T>();

		// add exact matches to list
		if (exactSuggestions != null)
			for (SimpleOrderedMap entry : exactSuggestions) {
				// cut to rows
				if (ids.size() == rows)
					return beans;

				term = (String) entry.get("term");
				id = (String) entry.get("payload");

				preview = (T) buildEntityPreview(entityPreviewClass, term, id);

				beans.add(preview);
				ids.add(id);

			}

		// add fuzzy matches to list but avoid dupplications with exact match
		if (fuzzySuggestions != null)
			for (SimpleOrderedMap entry : fuzzySuggestions) {
				// cut to rows
				if (ids.size() == rows)
					return beans;

				term = (String) entry.get("term");
				id = (String) entry.get("payload");
				if (!ids.contains(id)) {
					preview = (T) buildEntityPreview(entityPreviewClass, term, id);
					// cut to rows
					if (ids.size() < rows) {
						beans.add(preview);
						ids.add(id);
					}
				} else {
					getLog().debug("Ignored dupplicated entry: " + id + "\nterm: " + term);
				}
			}
		return beans;
	}

	private <T extends EntityPreview> EntityPreview buildEntityPreview(Class<T> entityPreviewClass, String term,
			String id) throws EntityServiceException {
		try {
			T preview = entityPreviewClass.newInstance();
			preview.setEntityId(id);
			preview.setPreferredLabel(term);
			return preview;
		} catch (Exception e) {
			throw new EntityServiceException(
					"Cannot instantiate Entity Preview class" + entityPreviewClass.getCanonicalName(), e);
		}
	}

	public Logger getLog() {
		return log;
	}

}
