package eu.europeana.entity.mongo.dao;

import java.io.Serializable;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;

import eu.europeana.api.commons.nosql.dao.impl.NosqlDaoImpl;
import eu.europeana.grouping.mongo.model.internal.GeneratedGroupingIdImpl;
import eu.europeana.grouping.mongo.model.internal.PersistentConceptScheme;

public class PersistentEntityDaoImpl <E extends PersistentConceptScheme, T extends Serializable>
		extends NosqlDaoImpl<E, T> implements PersistentEntityDao<E, T>{

//	@Resource
//	private EntityConfiguration configuration;
//	
//	protected final Logger logger = LogManager.getLogger(this.getClass());
//	
//	public EntityConfiguration getConfiguration() {
//		return configuration;
//	}
//
//	public void setConfiguration(EntityConfiguration configuration) {
//		this.configuration = configuration;
//	}
	
	public PersistentEntityDaoImpl(Class<E> clazz, Datastore datastore) {
		super(datastore, clazz);
	}

	@SuppressWarnings("deprecation")
	public long generateNextGroupingId(String provider) {

		GeneratedGroupingIdImpl nextGroupingId = null;

		synchronized ((Object) provider) {

			Query<GeneratedGroupingIdImpl> q = getDatastore().createQuery(GeneratedGroupingIdImpl.class);
			q.filter("_id", provider);
			
			UpdateOperations<GeneratedGroupingIdImpl> uOps = getDatastore()
					.createUpdateOperations(GeneratedGroupingIdImpl.class)
					.inc(GeneratedGroupingIdImpl.SEQUENCE_COLUMN_NAME);
			// search GroupingId and get incremented Grouping number 
			nextGroupingId = getDatastore().findAndModify(q, uOps);
			
			if (nextGroupingId == null) {
				nextGroupingId = new GeneratedGroupingIdImpl( 
						provider, ""+1L);
				ds.save(nextGroupingId);
			}
		}

		return nextGroupingId.getGroupingId();
	}
	
	
}
