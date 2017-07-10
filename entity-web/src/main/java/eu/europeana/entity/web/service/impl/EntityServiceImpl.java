package eu.europeana.entity.web.service.impl;

import javax.annotation.Resource;

import org.springframework.http.HttpStatus;

import eu.europeana.api.common.config.I18nConstants;
import eu.europeana.api.commons.web.exception.HttpException;
import eu.europeana.entity.definitions.exceptions.UnsupportedEntityTypeException;
import eu.europeana.entity.definitions.model.Entity;
import eu.europeana.entity.definitions.model.search.Query;
import eu.europeana.entity.definitions.model.search.QueryImpl;
import eu.europeana.entity.definitions.model.search.result.ResultSet;
import eu.europeana.entity.definitions.model.vocabulary.EntityTypes;
import eu.europeana.entity.solr.exception.EntityRetrievalException;
import eu.europeana.entity.solr.exception.EntitySuggestionException;
import eu.europeana.entity.solr.service.SolrEntityService;
import eu.europeana.entity.web.model.view.EntityPreview;
import eu.europeana.entity.web.service.EntityService;

public class EntityServiceImpl implements EntityService {

	public final String BASE_URL_DATA = "http://data.europeana.eu/";  
	
	@Resource 
	SolrEntityService solrEntityService;
	
	@Override
	public Entity retrieveByUrl(String type, String namespace, String identifier) throws HttpException{
		
		String entityUri = BASE_URL_DATA + type.toLowerCase() + "/" + namespace + "/" + identifier;
		Entity result;
		try {
			result = solrEntityService.searchByUrl(type, entityUri);
		} catch (EntityRetrievalException e) {
			throw new HttpException(e.getMessage(), I18nConstants.SERVER_ERROR_CANT_RETRIEVE_URI, new String[]{entityUri} , HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (UnsupportedEntityTypeException e) {
			throw new HttpException(null, I18nConstants.UNSUPPORTED_ENTITY_TYPE, new String[]{type}, HttpStatus.NOT_FOUND, null);
		}
		//if not found send appropriate error message
		if(result == null)
			throw new HttpException(null, I18nConstants.URI_NOT_FOUND, new String[]{entityUri}, HttpStatus.NOT_FOUND, null);
		
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
	 * @see eu.europeana.entity.web.service.EntityService#suggest(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, int)
	 */
	@Override	
	public ResultSet<? extends EntityPreview> suggest(String text, String language, EntityTypes[] internalEntityTypes, String scope, String namespace, int rows) throws HttpException {

		try {
			Query query = buildSearchQuery(text, null, rows);
			return solrEntityService.suggest(query, language, internalEntityTypes, scope, rows);
		} catch (EntitySuggestionException e) {
			//TODO #585
			throw new HttpException(e.getMessage(), I18nConstants.SERVER_ERROR_CANT_RETRIEVE_URI, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}


//	private String buildQueryFilter(String solrField, String value) {
//		return  solrField + ":" + value;
//	}
	
	
	@Override
	public String resolveByUri(String uri) throws HttpException{
		String result;
		try {
			result = solrEntityService.searchByCoref(uri);
		} catch (EntityRetrievalException e) {
			throw new HttpException(e.getMessage(), I18nConstants.SERVER_ERROR_CANT_RESOLVE_SAME_AS_URI, new String[]{uri}, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		//if not found send appropriate error message
		if(result == null)
			throw new HttpException(null, I18nConstants.CANT_FIND_BY_SAME_AS_URI, new String[]{uri}, HttpStatus.NOT_FOUND);
		
 		return result;
	}

	
}
