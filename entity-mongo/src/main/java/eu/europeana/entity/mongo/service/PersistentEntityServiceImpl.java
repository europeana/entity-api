package eu.europeana.entity.mongo.service;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import eu.europeana.api.commons.nosql.service.impl.AbstractNoSqlServiceImpl;
import eu.europeana.entity.definitions.exceptions.GroupingValidationException;
import eu.europeana.entity.definitions.model.ConceptScheme;
import eu.europeana.entity.definitions.model.ConceptSchemeId;
import eu.europeana.entity.definitions.model.vocabulary.WebEntityFields;
import eu.europeana.entity.mongo.dao.PersistentEntityDao;
import eu.europeana.entity.mongo.model.PersistentConceptSchemeImpl;
import eu.europeana.grouping.mongo.model.internal.PersistentConceptScheme;



/**
 * A service for persistence operation of grouping.
 * @author GrafR
 *
 */
@Component
public class PersistentEntityServiceImpl extends AbstractNoSqlServiceImpl<PersistentConceptScheme, String>
		implements PersistentEntityService {

	final String NOT_PERSISTENT_OBJECT = 
			"User set object in not an instance of persistent user set.";

	protected final Logger logger = LogManager.getLogger(this.getClass());
	
//	@Resource
//	private EntityConfiguration configuration;
//
//	public EntityConfiguration getConfiguration() {
//		return configuration;
//	}
//
//	public void setConfiguration(EntityConfiguration configuration) {
//		this.configuration = configuration;
//	}
	
	
	/**
	 * This method validates persistent user set and generates ID if
	 * genarateId=true.
	 * 
	 * @param object
	 */
	private void validatePersistentEntity(PersistentConceptScheme object) {

		long sequenceId = generateEntityId(WebEntityFields.CONCEPT_SCHEME_PROVIDER); 
		object.setConceptSchemeId("" + sequenceId);

		// validate user set ID
		if (StringUtils.isBlank(object.getConceptSchemeId()) 
				|| ConceptSchemeId.NOT_INITIALIZED_LONG_ID.equals(object.getConceptSchemeId()))
				throw new GroupingValidationException(
						"Entity.GroupingId.identifier must be a valid alpha-numeric value or a positive number!");
	}

	/**
	 * Generate next sequence number for grouping identifier in database
	 * @param collection The collection e.g. "Entity"
	 * @return next sequence number
	 */
	public long generateEntityId(String collection) {
		return getEntityDao().generateNextGroupingId(collection);
	}

	@Override
	public ConceptScheme store(ConceptScheme grouping) {
		
		PersistentConceptScheme persistentObject = null;
		
		if (grouping instanceof PersistentConceptScheme) {
			persistentObject = (PersistentConceptSchemeImpl) grouping;
		}else {
    	    throw new IllegalArgumentException(NOT_PERSISTENT_OBJECT);			
		}
		
		validatePersistentEntity(persistentObject);
		return this.store(persistentObject);
	}

	protected PersistentEntityDao<PersistentConceptScheme, String> getEntityDao() {
		return (PersistentEntityDao<PersistentConceptScheme, String>) getDao();
	}

}
