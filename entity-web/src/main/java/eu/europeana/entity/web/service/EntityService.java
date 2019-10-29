package eu.europeana.entity.web.service;

import java.io.UnsupportedEncodingException;
import java.util.List;

import eu.europeana.api.commons.definitions.search.Query;
import eu.europeana.api.commons.definitions.search.ResultSet;
import eu.europeana.api.commons.definitions.search.result.ResultsPage;
import eu.europeana.api.commons.web.exception.HttpException;
import eu.europeana.entity.definitions.exceptions.UnsupportedEntityTypeException;
import eu.europeana.entity.definitions.model.ConceptScheme;
import eu.europeana.entity.definitions.model.Entity;
import eu.europeana.entity.definitions.model.vocabulary.EntityTypes;
import eu.europeana.entity.definitions.model.vocabulary.SuggestAlgorithmTypes;
import eu.europeana.entity.web.exception.ParamValidationException;
import eu.europeana.entity.web.exception.RequestBodyValidationException;
import eu.europeana.entity.web.exception.response.ConceptSchemeNotFoundException;
import eu.europeana.entity.web.model.view.EntityPreview;
import eu.europeana.grouping.mongo.model.internal.PersistentConceptScheme;

public interface EntityService {

	Entity retrieveByUrl(String type, String namespace, String identifier) throws HttpException;

	/**
	 * This method provides suggestions for auto-completion
	 * 
	 * @param text
	 * @param language
	 * @param entityType
	 * @param namespace
	 * @param rows
	 * @param algorithm The default algorithm is "suggest" but other types are possible
	 * @return
	 * @throws HttpException
	 * 
	 * e.g. GET /entity/suggest?text=leonard&language=en
	 */
	ResultSet<? extends EntityPreview> suggest(
			String text, String[] language, List<EntityTypes> entityTypes, String scope, String namespace, int rows, SuggestAlgorithmTypes algorithm) throws HttpException;


	/**
	 * This method searches the entities using the provided search query and specific filters
	 * @param query
	 * @param preferredLanguages
	 * @param entityTypes
	 * @param scope
	 * @return
	 * @throws HttpException
	 */
	public ResultSet<? extends Entity> search(Query query, String[] preferredLanguages, List<EntityTypes> entityTypes, String scope) throws HttpException;
	
	
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
	 * This method build the results page object for the search results retrieved with the given search query.
	 * @param searchQuery
	 * @param results
	 * @param requestUrl
	 * @param reqParams
	 * @return
	 */
	public <T extends Entity> ResultsPage<T> buildResultsPage(Query searchQuery, ResultSet<T> results,
			StringBuffer requestUrl, String reqParams);
	
	/**
	 * This methods converts ConceptScheme object from JsonLd string format to a ConceptScheme object
	 * @param conceptSchemeJsonLdStr
	 * @return a ConceptScheme object
	 * @throws HttpException
	 */
	public ConceptScheme parseConceptSchemeLd(String conceptSchemeJsonLdStr) throws HttpException;	
	
	/**
	 * This method stores ConceptScheme object in database and in Solr.
	 * @param conceptScheme object
	 * @return stored conceptScheme object
	 */
	public ConceptScheme storeConceptScheme(ConceptScheme conceptScheme);	
	
	/**
	 * This method validates and processes the ConceptScheme description for format and mandatory fields
     * if false responds with HTTP 400
	 * @param webConceptScheme
	 * @throws RequestBodyValidationException 
	 * @throws eu.europeana.entity.web.exception.RequestBodyValidationException 
	 */
	public void validateWebConceptScheme(ConceptScheme webConceptScheme) throws RequestBodyValidationException;	
	
	/**
	 * This method returns ConceptScheme object for given concept scheme identifier.
	 * @param concept scheme identifier
	 * @return ConceptScheme object
	 */
	public ConceptScheme getConceptSchemeById(String conceptSchemeId) throws ConceptSchemeNotFoundException; 
			
	
	/**
	 * This method deletes concept scheme by concept scheme Id value.
	 * @param conceptSchemeId The id of the user set
	 * @throws ConceptSchemeNotFoundException 
	 */
	public void deleteConceptScheme(String conceptSchemeId) throws ConceptSchemeNotFoundException;	
	
	/**
	 * update (stored) <code>persistentConceptScheme</code> with values from <code>webConceptScheme</code>
	 * @param persistentConceptScheme
	 * @param webConceptScheme
	 * @return
	 */
	public ConceptScheme updateConceptScheme(
			PersistentConceptScheme persistentConceptScheme, ConceptScheme webConceptScheme);
		
	/**
	 * This method disables concept scheme in database
	 * @param existingConceptScheme
	 * @return disabled ConceptScheme
	 */
	public ConceptScheme disableConceptScheme(ConceptScheme existingConceptScheme);					 
		
	/**
	 * This method adds the id of the concept scheme to all entities matching the concept scheme ID.
	 * @param concepSchemeId
	 * @param conceptSchemeId
	 * @param addToEntities
	 * @param removeFromEntities
	 */
	public void updateConceptSchemeForEntities(String conceptSchemeId, List<String> addToEntities, List<String> removeFromEntities);
	    	
	
	/**
	 * @param entityTypes
	 * @param suggest
	 * @return 
	 * @throws ParamValidationException
	 */
	public List<EntityTypes> validateEntityTypes(List<EntityTypes> entityTypes, boolean suggest) throws ParamValidationException;

	/**
	 * 
	 * @param searchQuery the query to search for entities
	 * @param scope optional parameter to filter only entities used in europeana, see also general search method
	 * @param entityTypes optional parameter to filter results by entity type
	 * @return
	 * @throws HttpException
	 */
	public List<String> searchEntityIds(Query searchQuery, String scope, List<EntityTypes> entityTypes) throws HttpException;

	ConceptScheme updateEntitiesWithConceptScheme(ConceptScheme storedConceptScheme)
		throws UnsupportedEncodingException, HttpException, UnsupportedEntityTypeException;

	List<EntityTypes> getEntityTypesFromString(String commaSepEntityTypes) throws UnsupportedEntityTypeException;
		
}
