package eu.europeana.entity.solr.service;

import eu.europeana.api.commons.definitions.search.Query;
import eu.europeana.api.commons.definitions.search.ResultSet;
import eu.europeana.entity.definitions.exceptions.UnsupportedEntityTypeException;
import eu.europeana.entity.definitions.model.Entity;
import eu.europeana.entity.definitions.model.vocabulary.ConceptSolrFields;
import eu.europeana.entity.definitions.model.vocabulary.EntityTypes;
import eu.europeana.entity.solr.exception.EntityRetrievalException;
import eu.europeana.entity.solr.exception.EntitySuggestionException;
import eu.europeana.entity.web.model.view.EntityPreview;

public interface SolrEntityService {

	public static final String HANDLER_SELECT = "/select";
	public static final String HANDLER_SUGGEST = "/suggestEntity";
	
	/**
	 * This method retrieves available Entities by searching the given id.
	 * 
	 * @param id The SOLR entity_id
	 * @return
	 * @throws EntityRetrievalException 
	 */
	public Entity searchById(String entityId) throws EntityRetrievalException;

	/**
	 * This method retrieves available Entities by searching the given entity URI.
	 * @param entityUri - See {@link ConceptSolrFields#ID}
	 * @return
	 * @throws EntityRetrievalException 
	 * @throws UnsupportedEntityTypeException 
	 */
	public Entity searchByUrl(String type, String entityUri) throws EntityRetrievalException, UnsupportedEntityTypeException;
	
	/**
	 * This method retrieves available Entities that meet the .
	 * @param searchQuery The search query
	 * @return
	 * @throws EntityRetrievalException 
	 */
	public ResultSet<? extends Entity> search(Query searchQuery, String[] outLanguage,
			EntityTypes[] internalEntityTypes, String scope) throws EntityRetrievalException;
	
	/**
	 * This method retrieves available Entities that meet the .
	 * @param searchQuery The search query
	 * @param requestedLanguages
	 * @param rows
	 * @return
	 * @throws EntityRetrievalException 
	 * @throws EntitySuggestionException 
	 */
	public ResultSet<? extends EntityPreview> suggest(Query searchQuery, String[] requestedLanguages, EntityTypes[] entityTypes, String scope,  int rows) throws EntitySuggestionException;

	/**
	 * This method retrieves available Entities that meet the query criteria using search by label algorithm
	 * @param searchQuery The query text
	 * @param requestedLanguages
	 * @param rows
	 * @return
	 * @throws EntityRetrievalException 
	 * @throws EntitySuggestionException 
	 */
	public ResultSet<? extends EntityPreview> suggestByLabel(String text, String[] requestedLanguages, EntityTypes[] entityTypes, String scope,  int rows) throws EntitySuggestionException;

	
//	/**
//	 * Performs a lookup for the entity in all 4 datasets:
//	 * 
//	 *    agents, places, concepts and time spans 
//	 * 
//	 * using an alternative uri for an entity (lookup will happen within the owl:sameAs properties).
//	 * 
//	 * @param uri
//	 * @return
//	 * @throws EntityRetrievalException
//	 */
//	public String searchBySameAsUri(String uri) throws EntityRetrievalException;
	
	/**
	 * Performs a lookup for the entity in all 4 datasets:
	 * 
	 *    agents, places, concepts and time spans 
	 * 
	 * using an alternative uri for an entity (lookup will happen within the coref property).
	 * 
	 * @param uri
	 * @return
	 * @throws EntityRetrievalException
	 */
	public String searchByCoref(String uri) throws EntityRetrievalException;
	

}
