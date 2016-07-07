package eu.europeana.entity.web.service.impl;

import javax.annotation.Resource;

import org.springframework.http.HttpStatus;

import eu.europeana.entity.definitions.model.Concept;
import eu.europeana.entity.definitions.model.search.Query;
import eu.europeana.entity.definitions.model.search.QueryImpl;
import eu.europeana.entity.definitions.model.search.result.ResultSet;
import eu.europeana.entity.solr.exception.EntityRetrievalException;
import eu.europeana.entity.solr.exception.EntitySuggestionException;
import eu.europeana.entity.solr.service.SolrEntityService;
import eu.europeana.entity.web.exception.HttpException;
import eu.europeana.entity.web.model.view.EntityPreview;
import eu.europeana.entity.web.service.EntityService;

public class EntityServiceImpl implements EntityService {

	public final String BASE_URL_DATA = "http://data.europeana.eu/";  
	
	@Resource 
	SolrEntityService solrEntityService;
	
	@Override
	public Concept retrieveByUrl(String type, String namespace, String identifier) throws HttpException{
		
		String entityUri = BASE_URL_DATA + type.toLowerCase() + "/" + namespace + "/" + identifier;
		Concept result;
		try {
			result = solrEntityService.searchByUrl(entityUri);
		} catch (EntityRetrievalException e) {
			throw new HttpException("Cannot retrieve entity by URI", HttpStatus.INTERNAL_SERVER_ERROR, e);
		}
		//if not found send appropriate error message
		if(result == null)
			throw new HttpException("No Entitty found for URI: " + entityUri, HttpStatus.NOT_FOUND);
		
		return result;
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
		} catch (EntitySuggestionException e) {
			throw new HttpException("Cannot retrieve entity by URI", HttpStatus.INTERNAL_SERVER_ERROR, e);
		}
	}


//	private String buildQueryFilter(String solrField, String value) {
//		return  solrField + ":" + value;
//	}
	
}
