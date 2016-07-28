package eu.europeana.entity.solr.service;

import eu.europeana.entity.definitions.model.Concept;
import eu.europeana.entity.definitions.model.Entity;
import eu.europeana.entity.definitions.model.search.Query;
import eu.europeana.entity.definitions.model.search.result.ResultSet;
import eu.europeana.entity.definitions.model.vocabulary.ConceptSolrFields;
import eu.europeana.entity.definitions.model.vocabulary.EntityTypes;
import eu.europeana.entity.solr.exception.EntityRetrievalException;
import eu.europeana.entity.solr.exception.EntitySuggestionException;
import eu.europeana.entity.web.model.view.ConceptView;
import eu.europeana.entity.web.model.view.EntityPreview;

public interface SolrEntityService {

	/**
	 * This method retrieves available Entities by searching the given id.
	 * 
	 * @param id The SOLR entity_id
	 * @return
	 * @throws EntityRetrievalException 
	 */
	public Entity searchById(String entityId) throws EntityRetrievalException;

	/**
	 * This method retrieves available Annotations by searching the given entity URI.
	 * @param entityUri - See {@link ConceptSolrFields#ENTITY_ID}
	 * @return
	 * @throws EntityRetrievalException 
	 */
	public Entity searchByUrl(String type, String entityUri) throws EntityRetrievalException;
	
	/**
	 * This method retrieves available Entities that meet the .
	 * @param searchQuery The search query
	 * @return
	 * @throws EntityRetrievalException 
	 */
	public ResultSet<? extends ConceptView> search(Query searchQuery) throws EntityRetrievalException;
	
	/**
	 * This method retrieves available Entities that meet the .
	 * @param searchQuery The search query
	 * @param language
	 * @param rows
	 * @return
	 * @throws EntityRetrievalException 
	 * @throws EntitySuggestionException 
	 */
	public ResultSet<? extends EntityPreview> suggest(Query searchQuery, String language, EntityTypes entityType,  int rows) throws EntitySuggestionException;

	public ResultSet<? extends EntityPreview> suggest(Query searchQuery, String language, String internalEntityType,  int rows) throws EntitySuggestionException;

}