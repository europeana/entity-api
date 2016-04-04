package eu.europeana.entity.web.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.http.HttpException;

import eu.europeana.entity.definitions.model.Concept;
import eu.europeana.entity.definitions.model.search.Query;
import eu.europeana.entity.definitions.model.search.QueryImpl;
import eu.europeana.entity.definitions.model.search.result.ResultSet;
import eu.europeana.entity.definitions.model.vocabulary.SkosConceptSolrFields;
import eu.europeana.entity.solr.exception.EntityServiceException;
import eu.europeana.entity.solr.service.SolrEntityService;
import eu.europeana.entity.web.model.view.ConceptView;
import eu.europeana.entity.web.service.EntityService;

public class EntityServiceImpl implements EntityService {

	public final String BASE_URL_DATA = "http://data.europeana.eu/";  
	
	@Resource 
	SolrEntityService solrEntityService;
	
	@Override
	public Concept retrieveByUrl(String type, String namespace, String identifier) throws HttpException{
		
		String entityUri = BASE_URL_DATA + type + "/" + namespace + "/" + identifier;
		try {
			return solrEntityService.searchByUrl(entityUri);
		} catch (EntityServiceException e) {
			throw new HttpException("Cannot retrieve entity by URI", e);
		}
	}

	
	protected Query buildSearchQuery(String queryString, String[] filters, int rows) {
		
		Query searchQuery = new QueryImpl();
		searchQuery.setQuery(queryString);
		searchQuery.setRows(Math.min(rows, Query.MAX_PAGE_SIZE));	
		searchQuery.setFilters(filters);
		
		return searchQuery;
	}

	
	
	/* (non-Javadoc)
	 * @see eu.europeana.entity.web.service.EntityService#suggest(java.lang.String, java.lang.String, java.lang.String, java.lang.String, int)
	 */
	@Override	
	public ResultSet<? extends ConceptView> suggest(String text, String language, String type, String namespace, int rows) throws HttpException {
		
		List<String> filterList = new ArrayList<String>();
//		filterList.add(WebEntityFields.QUERY_PARAM_LANGUAGE + ":" + language);
//		filterList.add(buildQueryFilter(WebEntityConstants.QUERY_PARAM_NAMESPACE, namespace);
		
		filterList.add(buildQueryFilter(SkosConceptSolrFields.INTERNAL_TYPE, type));
		String[] filters = filterList.toArray(new String[filterList.size()]);
				
		try {
			Query query = buildSearchQuery(text, filters, rows);
			return solrEntityService.suggest(query, language);
		} catch (EntityServiceException e) {
			throw new HttpException("Cannot retrieve entity by URI", e);
		}
	}


	private String buildQueryFilter(String solrField, String value) {
		return  solrField + ":" + value;
	}
	
}
