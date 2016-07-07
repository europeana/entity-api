package eu.europeana.entity.solr.model.factory;

import eu.europeana.entity.definitions.model.Concept;
import eu.europeana.entity.definitions.model.vocabulary.EntityTypes;
import eu.europeana.entity.solr.model.SolrAgentImpl;
import eu.europeana.entity.solr.model.SolrConceptImpl;


public class ConceptObjectFactory extends AbstractModelObjectFactory<Concept, EntityTypes>{

	private static ConceptObjectFactory singleton;

	// force singleton usage
	private ConceptObjectFactory() {
	};

	public static ConceptObjectFactory getInstance() {

		if (singleton == null) {
			synchronized (ConceptObjectFactory.class) {
				singleton = new ConceptObjectFactory();
			}
		}

		return singleton;

	}
	
	
	@Override
	public Concept createObjectInstance(Enum<EntityTypes> modelObjectType) {
		Concept res = super.createObjectInstance(modelObjectType);
//		res.setInternalType(modelObjectType.name());
		return res;
	}

	
	@Override
	public Class<? extends Concept> getClassForType(
			Enum<EntityTypes> modelType) {
		
		Class<? extends Concept> ret = null;
		EntityTypes entityType = EntityTypes.valueOf(modelType.name());
		
		switch (entityType) {
		case Concept:
			ret = SolrConceptImpl.class;
			break;
		case Agent:
			ret = SolrAgentImpl.class;
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
