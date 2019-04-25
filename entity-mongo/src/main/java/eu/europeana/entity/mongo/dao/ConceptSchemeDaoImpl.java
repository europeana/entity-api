package eu.europeana.entity.mongo.dao;

import java.io.Serializable;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;

import eu.europeana.api.commons.nosql.dao.impl.NosqlDaoImpl;
import eu.europeana.grouping.mongo.model.internal.GeneratedGroupingIdImpl;
import eu.europeana.grouping.mongo.model.internal.PersistentConceptScheme;

public class ConceptSchemeDaoImpl <E extends PersistentConceptScheme, T extends Serializable>
		extends NosqlDaoImpl<E, T> implements ConceptSchemeDao<E, T>{

	public ConceptSchemeDaoImpl(Class<E> clazz, Datastore datastore) {
		super(datastore, clazz);
	}

	@SuppressWarnings("deprecation")
	public long generateNextSequenceNumber(String objectType) {

		GeneratedGroupingIdImpl nextGroupingId = null;

		synchronized ((Object) objectType) {

			Query<GeneratedGroupingIdImpl> q = getDatastore().createQuery(GeneratedGroupingIdImpl.class);
			q.filter("_id", objectType);
			
			UpdateOperations<GeneratedGroupingIdImpl> uOps = getDatastore()
					.createUpdateOperations(GeneratedGroupingIdImpl.class)
					.inc(GeneratedGroupingIdImpl.SEQUENCE_COLUMN_NAME);
			// search GroupingId and get incremented Grouping number 
			nextGroupingId = getDatastore().findAndModify(q, uOps);
			
			if (nextGroupingId == null) {
				nextGroupingId = new GeneratedGroupingIdImpl( 
					objectType, ""+1L);
				ds.save(nextGroupingId);
			}
		}

		return nextGroupingId.getGroupingId();
	}
	
	
}
