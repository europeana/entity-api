package eu.europeana.entity.web.service;

import eu.europeana.api.commons.definitions.search.Query;
import eu.europeana.api.commons.definitions.search.ResultSet;
import eu.europeana.api.commons.definitions.search.result.ResultsPage;
import eu.europeana.api.commons.web.exception.HttpException;
import eu.europeana.entity.definitions.model.Entity;
import eu.europeana.entity.definitions.model.search.SearchProfiles;
import eu.europeana.entity.definitions.model.vocabulary.EntityTypes;
import eu.europeana.entity.web.model.view.EntityPreview;

public interface EntityService {

	Entity retrieveByUrl(String type, String namespace, String identifier) throws HttpException;

	/**
	 * This method provides suggestions for auto-completion
	 * 
	 * @param text
	 * @param language
	 * @param internalEntityType
	 * @param namespace
	 * @param rows
	 * @param algorithm The default algorithm is "suggest" but other types are possible
	 * @return
	 * @throws HttpException
	 * 
	 * e.g. GET /entity/suggest?text=leonard&language=en
	 */
	ResultSet<? extends EntityPreview> suggest(
			String text, String[] language, EntityTypes[] internalEntityTypes, String scope, String namespace, int rows, String algorithm) throws HttpException;


	/**
	 * This method searches the entities using the provided search query and specific filters
	 * @param query
	 * @param preferredLanguages
	 * @param internalEntityTypes
	 * @param scope
	 * @return
	 * @throws HttpException
	 */
	public ResultSet<? extends Entity> search(Query query, String[] preferredLanguages, EntityTypes[] internalEntityTypes, String scope) throws HttpException;
	
	
	/**
	 * Performs a lookup for the entity in all 4 datasets:
	 * 
	 *    agents, places, concepts and time spans 
	 * 
	 * using an alternative uri for an entity (lookup will happen within the owl:sameAs properties).
	 * 
	 * @param uri
	 * @return
	 * @throws HttpException
	 */
	String resolveByUri(String uri) throws HttpException;
	
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
	public Query buildSearchQuery(String queryString, String[] qf, String[] facets, String[] sort, int page,
			int pageSize, SearchProfiles profile, String[] retFields);

	/**
	 * This method build the results page object for the search results retrieved with the given search query.
	 * @param searchQuery
	 * @param results
	 * @param requestUrl
	 * @param reqParams
	 * @return
	 */
	public <T extends Entity> ResultsPage<T> buildResultsPage(Query searchQuery, ResultSet<T> results,
			StringBuffer requestUrl, String reqParams);
}
