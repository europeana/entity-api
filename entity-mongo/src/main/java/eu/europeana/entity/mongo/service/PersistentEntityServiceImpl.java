package eu.europeana.entity.mongo.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mongodb.morphia.query.Query;
import org.springframework.stereotype.Component;

import eu.europeana.api.commons.nosql.service.impl.AbstractNoSqlServiceImpl;
import eu.europeana.entity.definitions.exceptions.EntityValidationException;
import eu.europeana.entity.definitions.model.ConceptScheme;
import eu.europeana.entity.definitions.model.vocabulary.WebEntityConstants;
import eu.europeana.entity.mongo.dao.ConceptSchemeDao;
import eu.europeana.entity.mongo.model.PersistentConceptSchemeImpl;
import eu.europeana.grouping.mongo.model.internal.PersistentConceptScheme;

/**
 * A service for persistence operation of grouping.
 * 
 * @author GrafR
 *
 */
@Component
public class PersistentEntityServiceImpl extends AbstractNoSqlServiceImpl<PersistentConceptScheme, String>
	implements PersistentEntityService {

    final String NOT_PERSISTENT_OBJECT = "Concept scheme object in not an instance of persistent concept scheme.";

    protected final Logger logger = LogManager.getLogger(this.getClass());

    /**
     * This method validates persistent user set and generates ID if
     * genarateId=true.
     * 
     * @param object
     */
    private void setEntityId(PersistentConceptScheme object) {
	Long sequenceId = generateEntityId(WebEntityConstants.CONCEPT_SCHEME);
	object.setGeneratedIdentifier(sequenceId.toString());
	object.setEntityId(WebEntityConstants.BASE_CONCEPT_SCHEME_URL + sequenceId);
    }

    /**
     * Generate next sequence number for grouping identifier in database
     * 
     * @param collection
     *            The collection e.g. "Entity"
     * @return next sequence number
     */
    public long generateEntityId(String collection) {
	return getEntityDao().generateNextSequenceNumber(collection);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * eu.europeana.entity.mongo.service.PersistentEntityService#store(eu.europeana.
     * entity.definitions.model.ConceptScheme)
     */
    @Override
    public ConceptScheme store(ConceptScheme conceptScheme) {

	PersistentConceptScheme persistentObject = null;

	if (conceptScheme instanceof PersistentConceptSchemeImpl) {
	    persistentObject = (PersistentConceptSchemeImpl) conceptScheme;
	} else {
	    throw new IllegalArgumentException(NOT_PERSISTENT_OBJECT);
	}

	setEntityId(persistentObject);
	return this.store(persistentObject);
    }

    /**
     * @return
     */
    protected ConceptSchemeDao<PersistentConceptScheme, String> getEntityDao() {
	return (ConceptSchemeDao<PersistentConceptScheme, String>) getDao();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * eu.europeana.entity.mongo.service.PersistentEntityService#getByIdentifier(
     * java.lang.String)
     */
    public PersistentConceptScheme getByIdentifier(String identifier) {
	Query<PersistentConceptScheme> query = getEntityDao().createQuery();
	query.filter(PersistentConceptScheme.GENERATED_IDENTIFIER, identifier);

	return getEntityDao().findOne(query);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * eu.europeana.entity.mongo.service.PersistentEntityService#deleteByIdentifier(
     * java.lang.String)
     */
    public void deleteByIdentifier(String identifier) {
	PersistentConceptScheme storedConceptScheme = getByIdentifier(identifier);
	getEntityDao().delete(storedConceptScheme);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * eu.europeana.entity.mongo.service.PersistentEntityService#update(eu.europeana
     * .grouping.mongo.model.internal.PersistentConceptScheme)
     */
    @Override
    public PersistentConceptScheme update(PersistentConceptScheme conceptScheme) throws EntityValidationException {
	return store(conceptScheme);
    }
}
