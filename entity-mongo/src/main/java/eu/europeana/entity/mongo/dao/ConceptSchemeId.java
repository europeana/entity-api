package eu.europeana.entity.mongo.dao;

import java.io.Serializable;

import eu.europeana.api.commons.nosql.dao.NosqlDao;
import eu.europeana.grouping.mongo.model.internal.PersistentConceptScheme;

public interface ConceptSchemeId<E extends PersistentConceptScheme, T extends Serializable > extends NosqlDao<E, T> {
	
	long generateNextSequenceNumber(String objectType);
}
