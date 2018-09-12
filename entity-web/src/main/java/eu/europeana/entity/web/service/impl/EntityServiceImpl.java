package eu.europeana.entity.web.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;

import eu.europeana.api.common.config.I18nConstants;
import eu.europeana.api.commons.definitions.search.Query;
import eu.europeana.api.commons.definitions.search.ResultSet;
import eu.europeana.api.commons.definitions.search.impl.QueryImpl;
import eu.europeana.api.commons.definitions.search.result.ResultsPage;
import eu.europeana.api.commons.definitions.search.result.impl.ResultsPageImpl;
import eu.europeana.api.commons.definitions.vocabulary.CommonApiConstants;
import eu.europeana.api.commons.search.util.QueryBuilder;
import eu.europeana.api.commons.web.exception.HttpException;
import eu.europeana.entity.definitions.exceptions.UnsupportedEntityTypeException;
import eu.europeana.entity.definitions.model.Entity;
import eu.europeana.entity.definitions.model.search.SearchProfiles;
import eu.europeana.entity.definitions.model.vocabulary.ConceptSolrFields;
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
	public Entity retrieveByUrl(String type, String namespace, String identifier) throws HttpException {

		StringBuilder stringBuilder = new StringBuilder();

		stringBuilder.append(BASE_URL_DATA);
		if (StringUtils.isNotEmpty(type))
			stringBuilder.append(type.toLowerCase() + "/");
		if (StringUtils.isNotEmpty(namespace) && !EntityTypes.Organization.getInternalType().equalsIgnoreCase(type))
			stringBuilder.append(namespace + "/");
		if (StringUtils.isNotEmpty(identifier))
			stringBuilder.append(identifier);

		String entityUri = stringBuilder.toString();
		Entity result;
		try {
			result = solrEntityService.searchByUrl(type, entityUri);
		} catch (EntityRetrievalException e) {
			throw new HttpException(e.getMessage(), I18nConstants.SERVER_ERROR_CANT_RETRIEVE_URI,
					new String[] { entityUri }, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (UnsupportedEntityTypeException e) {
			throw new HttpException(null, I18nConstants.UNSUPPORTED_ENTITY_TYPE, new String[] { type },
					HttpStatus.NOT_FOUND, null);
		}
		// if not found send appropriate error message
		if (result == null)
			throw new HttpException(null, I18nConstants.RESOURCE_NOT_FOUND, new String[] { entityUri },
					HttpStatus.NOT_FOUND, null);

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

	/*
	 * (non-Javadoc)
	 * 
	 * @see eu.europeana.entity.web.service.EntityService#
	 */
	@Override
	public ResultSet<? extends EntityPreview> suggest(String text, String[] language, EntityTypes[] internalEntityTypes,
			String scope, String namespace, int rows) throws HttpException {

		try {
			Query query = buildSearchQuery(text, null, rows);
			return solrEntityService.suggest(query, language, internalEntityTypes, scope, rows);
		} catch (EntitySuggestionException e) {
			throw new HttpException(e.getMessage(), I18nConstants.SERVER_ERROR_CANT_RETRIEVE_URI, null,
					HttpStatus.INTERNAL_SERVER_ERROR, e);
		}
	}

	@Override
	public String resolveByUri(String uri) throws HttpException {
		String result;
		try {
			result = solrEntityService.searchByCoref(uri);
		} catch (EntityRetrievalException e) {
			throw new HttpException(e.getMessage(), I18nConstants.SERVER_ERROR_CANT_RESOLVE_SAME_AS_URI,
					new String[] { uri }, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		// if not found send appropriate error message
		if (result == null)
			throw new HttpException(null, I18nConstants.CANT_FIND_BY_SAME_AS_URI, new String[] { uri },
					HttpStatus.NOT_FOUND);

		return result;
	}

	@Override
	public ResultSet<? extends Entity> search(Query query, String[] outLanguage, EntityTypes[] internalEntityTypes,
			String scope) throws HttpException {

		return solrEntityService.search(query, outLanguage, internalEntityTypes, scope);
	}

	/**
	 * @param queryString
	 * @param qf
	 * @param facets
	 * @param sort
	 * @param page
	 * @param pageSize
	 * @param profile
	 * @param retFields
	 * @return
	 */
	public Query buildSearchQuery(String queryString, String[] qf, String[] facets, String sort, int page, int pageSize,
			SearchProfiles profile, String[] retFields) {
		String sortField = null;
		String sortOrder = null;
		if (StringUtils.isNotBlank(sort)) {
			String[] sorting = StringUtils.split(sort, '+');
			sortField = sorting[0];
			if (sorting.length > 1)
				sortOrder = sorting[1];
		}

		QueryBuilder builder = new QueryBuilder();
		int maxPageSize = Query.DEFAULT_MAX_PAGE_SIZE;
		String profileName = null;
		if (profile != null) {
			profileName = profile.name();
		}

		if (retFields != null) {
			retFields = buildCustomSelectionFields(retFields);
		}

		Query query = builder.buildSearchQuery(queryString, qf, facets, retFields, sortField, sortOrder, page, pageSize,
				maxPageSize, profileName);
		return query;
	}

	/**
	 * This method enriches provided custom selection fields by required fields
	 * if they are not already provided in input array.
	 * 
	 * @param inputArray
	 * @return enriched array
	 */
	protected String[] buildCustomSelectionFields(String[] inputFields) {
		List<String> fieldList = new ArrayList<String>();
		Collections.addAll(fieldList, inputFields);
		//add mandatory fields
		if(!fieldList.contains(ConceptSolrFields.ID))
			fieldList.add(ConceptSolrFields.ID);
		if(!fieldList.contains(ConceptSolrFields.INTERNAL_TYPE))
			fieldList.add(ConceptSolrFields.INTERNAL_TYPE);
		
		return fieldList.toArray(new String[fieldList.size()]);
	}

	// TODO: consider usage of a helper class for helper methods
	public <T extends Entity> ResultsPage<T> buildResultsPage(Query searchQuery, ResultSet<T> results,
			StringBuffer requestUrl, String reqParams) {
		@SuppressWarnings({ "rawtypes", "unchecked" })
		ResultsPage<T> resPage = new ResultsPageImpl();

		resPage.setItems(results.getResults());
		resPage.setFacetFields(results.getFacetFields());

		resPage.setTotalInPage(results.getResults().size());
		resPage.setTotalInCollection(results.getResultSize());

		String collectionUrl = buildCollectionUrl(searchQuery, requestUrl, reqParams);
		resPage.setCollectionUri(collectionUrl);

		int currentPage = searchQuery.getPageNr();
		String currentPageUrl = buildPageUrl(collectionUrl, currentPage, searchQuery.getPageSize());
		resPage.setCurrentPageUri(currentPageUrl);

		if (currentPage > 0) {
			String prevPage = buildPageUrl(collectionUrl, currentPage - 1, searchQuery.getPageSize());
			resPage.setPrevPageUri(prevPage);
		}

		// if current page is not the last one
		boolean isLastPage = resPage.getTotalInCollection() <= (currentPage + 1) * searchQuery.getPageSize();
		if (!isLastPage) {
			String nextPage = buildPageUrl(collectionUrl, currentPage + 1, searchQuery.getPageSize());
			resPage.setNextPageUri(nextPage);
		}

		return resPage;
	}

	private String buildPageUrl(String collectionUrl, int page, int pageSize) {
		StringBuilder builder = new StringBuilder(collectionUrl);
		builder.append("&").append(CommonApiConstants.QUERY_PARAM_PAGE).append("=").append(page);

		builder.append("&").append(CommonApiConstants.QUERY_PARAM_PAGE_SIZE).append("=").append(pageSize);

		return builder.toString();
	}

	private String buildCollectionUrl(Query searchQuery, StringBuffer requestUrl, String queryString) {

		// queryString = removeParam(WebAnnotationFields.PARAM_WSKEY,
		// queryString);

		// remove out of scope parameters
		queryString = removeParam(CommonApiConstants.QUERY_PARAM_PAGE, queryString);
		queryString = removeParam(CommonApiConstants.QUERY_PARAM_PAGE_SIZE, queryString);

		// avoid duplication of query parameters
		queryString = removeParam(CommonApiConstants.QUERY_PARAM_PROFILE, queryString);

		// add mandatory parameters
		if (StringUtils.isNotBlank(searchQuery.getSearchProfile())) {
			queryString += ("&" + CommonApiConstants.QUERY_PARAM_PROFILE + "=" + searchQuery.getSearchProfile());
		}

		return requestUrl.append("?").append(queryString).toString();
	}

	protected String removeParam(final String queryParam, String queryParams) {
		String tmp;
		// avoid name conflicts search "queryParam="
		int startPos = queryParams.indexOf(queryParam + "=");
		int startEndPos = queryParams.indexOf("&", startPos + 1);

		if (startPos >= 0) {
			// make sure to remove the "&" if not the first param
			if (startPos > 0)
				startPos--;
			tmp = queryParams.substring(0, startPos);

			if (startEndPos > 0)
				tmp += queryParams.substring(startEndPos);
		} else {
			tmp = queryParams;
		}
		return tmp;
	}

}
