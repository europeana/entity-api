package eu.europeana.entity.web.service.impl;

import javax.annotation.Resource;

import org.apache.http.HttpException;

import eu.europeana.entity.definitions.model.Concept;
import eu.europeana.entity.definitions.model.search.Query;
import eu.europeana.entity.definitions.model.search.QueryImpl;
import eu.europeana.entity.definitions.model.search.result.ResultSet;
import eu.europeana.entity.solr.exception.EntityServiceException;
import eu.europeana.entity.solr.service.SolrEntityService;
import eu.europeana.entity.web.model.view.EntityPreview;
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
	public ResultSet<? extends EntityPreview> suggest(String text, String language, String type, String namespace, int rows) throws HttpException {
		
		try {
			Query query = buildSearchQuery(text, null, rows);
			return solrEntityService.suggest(query, language, rows);
		} catch (EntityServiceException e) {
			throw new HttpException("Cannot retrieve entity by URI", e);
		}
	}


//	private String buildQueryFilter(String solrField, String value) {
//		return  solrField + ":" + value;
//	}
	
}
