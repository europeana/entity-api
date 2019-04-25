package eu.europeana.entity.mongo.service;

import eu.europeana.api.commons.nosql.service.AbstractNoSqlService;
import eu.europeana.entity.definitions.exceptions.EntityValidationException;
import eu.europeana.entity.definitions.model.ConceptScheme;
import eu.europeana.grouping.mongo.model.internal.PersistentConceptScheme;


public interface PersistentEntityService extends AbstractNoSqlService<PersistentConceptScheme, String>{

	/**
	 * This method stores grouping in a database
	 * @param object
	 * @return
	 * @throws EntityValidationException
	 */
	public abstract ConceptScheme store(ConceptScheme object) throws EntityValidationException;

	/**
	 * This method retrieves entity from database by set identifier string
	 * @param identifier The set identifier e.g. http://localhost:8080/scheme/6
	 * @return entity object
	 */
	public abstract PersistentConceptScheme getByIdentifier(String identifier);

	/**
	 * This method removes concept scheme by identifier. E.g. 6.
	 * @param identifier
	 */
	public void deleteByIdentifier(String identifier);
	
	/**
	 * This method performs update for the passed user set object
	 * @param user set
	 */
	public PersistentConceptScheme update(PersistentConceptScheme conceptScheme) throws EntityValidationException;	
}

