package eu.europeana.entity.solr.model.factory;

import eu.europeana.entity.definitions.model.vocabulary.EntityTypes;
import eu.europeana.entity.solr.view.AgentPreviewImpl;
import eu.europeana.entity.solr.view.ConceptPreviewImpl;
import eu.europeana.entity.solr.view.ConceptSchemePreviewImpl;
import eu.europeana.entity.solr.view.OrganizationPreviewImpl;
import eu.europeana.entity.solr.view.PlacePreviewImpl;
import eu.europeana.entity.solr.view.TimeSpanPreviewImpl;
import eu.europeana.entity.web.model.view.EntityPreview;


public class EntityPreviewObjectFactory extends AbstractModelObjectFactory<EntityPreview, EntityTypes>{

	private static EntityPreviewObjectFactory singleton;

	// force singleton usage
	private EntityPreviewObjectFactory() {
	};

	public static EntityPreviewObjectFactory getInstance() {

		if (singleton == null) {
			synchronized (EntityPreviewObjectFactory.class) {
				singleton = new EntityPreviewObjectFactory();
			}
		}

		return singleton;

	}
	
	
	@Override
	public EntityPreview createObjectInstance(Enum<EntityTypes> modelObjectType) {
		EntityPreview res = super.createObjectInstance(modelObjectType);
		res.setEntityType(EntityTypes.valueOf(modelObjectType.name()));
		return res;
	}

	
	@Override
	public Class<? extends EntityPreview> getClassForType(
			Enum<EntityTypes> modelType) {
		
		Class<? extends EntityPreview> ret = null;
		EntityTypes entityType = EntityTypes.valueOf(modelType.name());
		
		switch (entityType) {
		case Organization:
			ret = OrganizationPreviewImpl.class;
			break;
		case Concept:
			ret = ConceptPreviewImpl.class;
			break;
		case Agent:
			ret = AgentPreviewImpl.class;
			break;
		case Place:
			ret = PlacePreviewImpl.class;
			break;
		case Timespan:
			ret = TimeSpanPreviewImpl.class;
		case ConceptScheme:
			ret = ConceptSchemePreviewImpl.class;
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
