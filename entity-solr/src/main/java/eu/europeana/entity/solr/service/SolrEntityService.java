package eu.europeana.entity.solr.service;

import eu.europeana.entity.definitions.model.Concept;
import eu.europeana.entity.definitions.model.search.Query;
import eu.europeana.entity.definitions.model.search.result.ResultSet;
import eu.europeana.entity.definitions.model.vocabulary.SkosConceptSolrFields;
import eu.europeana.entity.solr.exception.EntityServiceException;
import eu.europeana.entity.web.model.view.ConceptView;

public interface SolrEntityService {

	/**
	 * This method retrieves available Entities by searching the given id.
	 * 
	 * @param id The SOLR entity_id
	 * @return
	 * @throws EntityServiceException 
	 */
	public Concept searchById(String entityId) throws EntityServiceException;

	/**
	 * This method retrieves available Annotations by searching the given entity URI.
	 * @param entityUri - See {@link SkosConceptSolrFields#ENTITY_ID}
	 * @return
	 * @throws EntityServiceException 
	 */
	public Concept searchByUrl(String entityUri) throws EntityServiceException;
	
	/**
	 * This method retrieves available Entities that meet the .
	 * @param searchQuery The search query
	 * @return
	 * @throws EntityServiceException 
	 */
	public ResultSet<? extends ConceptView> search(Query searchQuery) throws EntityServiceException;
	
	
}
