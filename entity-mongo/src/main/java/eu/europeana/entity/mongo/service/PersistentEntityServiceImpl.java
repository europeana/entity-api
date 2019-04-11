package eu.europeana.entity.mongo.service;

import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mongodb.morphia.query.Query;
import org.springframework.stereotype.Component;

import eu.europeana.api.commons.nosql.service.impl.AbstractNoSqlServiceImpl;
import eu.europeana.entity.definitions.exceptions.EntityValidationException;
import eu.europeana.entity.definitions.model.ConceptScheme;
import eu.europeana.entity.definitions.model.vocabulary.WebEntityFields;
import eu.europeana.entity.mongo.dao.PersistentEntityDao;
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

    final String NOT_PERSISTENT_OBJECT = "User set object in not an instance of persistent user set.";

    protected final Logger logger = LogManager.getLogger(this.getClass());

    /**
     * This method validates persistent user set and generates ID if
     * genarateId=true.
     * 
     * @param object
     */
    private void updateTechnicalFields(PersistentConceptScheme object) {
	Long sequenceId = generateEntityId(WebEntityFields.CONCEPT_SCHEME);
	object.setGeneratedIdentifier(sequenceId.toString());
	object.setEntityId(WebEntityFields.BASE_CONCEPT_SCHEME_URL + sequenceId);
    }

    /**
     * Generate next sequence number for grouping identifier in database
     * 
     * @param collection
     *            The collection e.g. "Entity"
     * @return next sequence number
     */
    public long generateEntityId(String collection) {
	return getEntityDao().generateNextGroupingId(collection);
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

	updateTechnicalFields(persistentObject);
	return this.store(persistentObject);
    }

    /**
     * @return
     */
    protected PersistentEntityDao<PersistentConceptScheme, String> getEntityDao() {
	return (PersistentEntityDao<PersistentConceptScheme, String>) getDao();
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
