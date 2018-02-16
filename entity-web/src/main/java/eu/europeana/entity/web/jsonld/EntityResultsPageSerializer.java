package eu.europeana.entity.web.jsonld;

import java.util.Map;

import org.apache.stanbol.commons.jsonld.JsonLdProperty;
import org.apache.stanbol.commons.jsonld.JsonLdPropertyValue;
import org.apache.stanbol.commons.jsonld.JsonLdResource;

import eu.europeana.api.commons.definitions.search.result.ResultsPage;
import eu.europeana.api.commons.definitions.vocabulary.CommonLdConstants;
import eu.europeana.api.commons.utils.ResultsPageSerializer;
import eu.europeana.entity.definitions.exceptions.UnsupportedEntityTypeException;
import eu.europeana.entity.definitions.model.Entity;
import eu.europeana.entity.utils.jsonld.EuropeanaEntityLd;
import eu.europeana.entity.web.exception.FunctionalRuntimeException;

public class EntityResultsPageSerializer<T extends Entity> extends ResultsPageSerializer<T> {

	public EntityResultsPageSerializer(ResultsPage<T> resPage, String context, String type) {
		super(resPage, context, type);
	}

	@Override
	protected void serializeItems(JsonLdResource jsonLdResource, String profile){
//		switch (profile)
	
		registerContainerProperty(CommonLdConstants.ITEMS);
		
		if(getResultsPage().getItems() == null || getResultsPage().getItems().isEmpty())
			return;
		
		JsonLdProperty itemsProp = new JsonLdProperty(CommonLdConstants.ITEMS);
		
		for(Entity entity: getResultsPage().getItems()) {
			serializeItem(itemsProp, entity);
		}
		jsonLdResource.putProperty(itemsProp);
	}

	private void serializeItem(JsonLdProperty itemsProp, Entity entity){
		EuropeanaEntityLd entityLd;
		//transform annotation object to json-ld
		try {
			entityLd = new EuropeanaEntityLd(entity);
		} catch (UnsupportedEntityTypeException e) {
			throw new FunctionalRuntimeException("Cannot serialize entity of type: "+entity.getInternalType(), e);
		}
		
		//build property value for the given annotation
		JsonLdPropertyValue propertyValue = new JsonLdPropertyValue();
		Map<String, JsonLdProperty> propertyMap = propertyValue.getPropertyMap();
		propertyMap.putAll(entityLd.getLdResource().getPropertyMap());
		itemsProp.addValue(propertyValue);
	}
}
