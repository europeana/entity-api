package eu.europeana.entity.web.service.impl;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;

import eu.europeana.api.common.config.I18nConstants;
import eu.europeana.api.commons.definitions.search.Query;
import eu.europeana.api.commons.definitions.search.ResultSet;
import eu.europeana.api.commons.definitions.search.impl.QueryImpl;
import eu.europeana.api.commons.web.exception.HttpException;
import eu.europeana.entity.definitions.exceptions.UnsupportedEntityTypeException;
import eu.europeana.entity.definitions.model.Entity;
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
		
		StringBuilder stringBuilder = new StringBuilder();

		stringBuilder.append(BASE_URL_DATA);
		if (StringUtils.isNotEmpty(type))
			stringBuilder.append(type.toLowerCase() + "/");
		if (StringUtils.isNotEmpty(namespace))
			stringBuilder.append(namespace + "/");
		if (StringUtils.isNotEmpty(identifier))
			stringBuilder.append(identifier);

		String entityUri = stringBuilder.toString();
//		String entityUri = BASE_URL_DATA + type.toLowerCase() + "/" + namespace + "/" + identifier;
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
			throw new HttpException(null, I18nConstants.RESOURCE_NOT_FOUND, new String[]{entityUri}, HttpStatus.NOT_FOUND, null);
		
		return result;
	}
	
	/**
	 * @deprecated use QueryBuilder
	 * @param queryString
	 * @param filters
	 * @param rows
	 * @return
	 */
	protected Query buildSearchQuery(String queryString, String[] filters, int pageSize) {
		
		Query searchQuery = new QueryImpl();
		searchQuery.setQuery(queryString);
		searchQuery.setPageSize(Math.min(pageSize, Query.DEFAULT_MAX_PAGE_SIZE));	
		searchQuery.setFilters(filters);
		
		return searchQuery;
	}

	
	
	/* (non-Javadoc)
	 * @see eu.europeana.entity.web.service.EntityService#
	 */
	@Override	
	public ResultSet<? extends EntityPreview> suggest(String text, String[] language, EntityTypes[] internalEntityTypes, String scope, String namespace, int rows) throws HttpException {

		try {
			Query query = buildSearchQuery(text, null, rows);
			return solrEntityService.suggest(query, language, internalEntityTypes, scope, rows);
		} catch (EntitySuggestionException e) {
			throw new HttpException(e.getMessage(), I18nConstants.SERVER_ERROR_CANT_RETRIEVE_URI, null, HttpStatus.INTERNAL_SERVER_ERROR, e);
		}
	}
	
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

	@Override
	public ResultSet<? extends Entity> search(Query query, String[] outLanguage,
			EntityTypes[] internalEntityTypes, String scope) throws HttpException {
		
		return solrEntityService.search(query, outLanguage,
				internalEntityTypes, scope);
	}

	
}
