package eu.europeana.entity.solr.model.factory;

import eu.europeana.entity.definitions.model.Entity;
import eu.europeana.entity.definitions.model.vocabulary.EntityTypes;
import eu.europeana.entity.solr.model.SolrAgentImpl;
import eu.europeana.entity.solr.model.SolrConceptImpl;
import eu.europeana.entity.solr.model.SolrOrganizationImpl;
import eu.europeana.entity.solr.model.SolrPlaceImpl;


public class EntityObjectFactory extends AbstractModelObjectFactory<Entity, EntityTypes>{

	private static EntityObjectFactory singleton;

	// force singleton usage
	private EntityObjectFactory() {
	};

	public static EntityObjectFactory getInstance() {

		if (singleton == null) {
			synchronized (EntityObjectFactory.class) {
				singleton = new EntityObjectFactory();
			}
		}

		return singleton;

	}
	
	
	@Override
	public Entity createObjectInstance(Enum<EntityTypes> modelObjectType) {
		Entity res = super.createObjectInstance(modelObjectType);
//		res.setInternalType(modelObjectType.name());
		return res;
	}

	
	@Override
	public Class<? extends Entity> getClassForType(
			Enum<EntityTypes> modelType) {
		
		Class<? extends Entity> ret = null;
		EntityTypes entityType = EntityTypes.valueOf(modelType.name());
		
		switch (entityType) {
		case Organization:
			ret = SolrOrganizationImpl.class;
			break;
		case Concept:
			ret = SolrConceptImpl.class;
			break;
		case Agent:
			ret = SolrAgentImpl.class;
			break;
		case Place:
			ret = SolrPlaceImpl.class;
			break;
		case Timespan:
			ret = SolrPlaceImpl.class;
			break;
		default:
			throw new RuntimeException(
					"The given type is not supported by the web model");
		}

		return ret;
	}


	@Override
	public Class<EntityTypes> getEnumClass() {
		return EntityTypes.class;
	}
}
