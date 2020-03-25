package eu.europeana.entity.web.jsonld;

import java.io.IOException;

import eu.europeana.api.commons.web.exception.HttpException;
import eu.europeana.corelib.edm.model.schemaorg.ContextualEntity;
import eu.europeana.corelib.edm.utils.JsonLdSerializer;
import eu.europeana.corelib.edm.utils.SchemaOrgTypeFactory;
import eu.europeana.corelib.edm.utils.SchemaOrgUtils;
import eu.europeana.entity.definitions.exceptions.UnsupportedEntityTypeException;
import eu.europeana.entity.definitions.model.Entity;

/**
 * This class supports serialization of Entity object applying 
 * schema.org serialization.
 * 
 * @author GrafR
 *
 */
public class EntitySchemaOrgSerializer extends JsonLdSerializer {

	public EntitySchemaOrgSerializer(){
		super();
	}

	/**
	 * This method serializes Entity object applying schema.org serialization.
	 * The thingObject is the object in corelib format.
	 * 
	 * @param entity      The Entity object
	 * @return The serialized entity in json-ld string format
	 * @throws UnsupportedEntityTypeException
	 */
	public String serializeEntity(Entity entity) 
		throws HttpException, UnsupportedEntityTypeException {
	    
	        String jsonLd;
	        ContextualEntity thingObject = SchemaOrgTypeFactory.createContextualEntity(entity);

		SchemaOrgUtils.processEntity(entity, thingObject);
		try {
		    jsonLd = serialize(thingObject);
		} catch (IOException e) {
		    throw new UnsupportedEntityTypeException(
			    "Serialization to schema.org failed for " + thingObject.getId() + e.getMessage());
		}
                return jsonLd;
	}
	
}