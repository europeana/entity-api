package eu.europeana.entity.mongo.service;

import eu.europeana.api.commons.nosql.service.AbstractNoSqlService;
import eu.europeana.entity.definitions.exceptions.GroupingValidationException;
import eu.europeana.entity.definitions.model.ConceptScheme;
import eu.europeana.grouping.mongo.model.internal.PersistentConceptScheme;


public interface PersistentEntityService extends AbstractNoSqlService<PersistentConceptScheme, String>{

	/**
	 * This method stores grouping in a database
	 * @param object
	 * @return
	 * @throws EntityValidationException
	 */
	public abstract ConceptScheme store(ConceptScheme object) throws GroupingValidationException;
		
}

