package eu.europeana.entity.utils.serialize;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

import eu.europeana.entity.definitions.model.ConceptScheme;
import eu.europeana.entity.definitions.model.vocabulary.WebEntityFields;
import ioinformarics.oss.jackson.module.jsonld.JsonldModule;
import ioinformarics.oss.jackson.module.jsonld.JsonldResource;
import ioinformarics.oss.jackson.module.jsonld.JsonldResourceBuilder;


public class ConceptSchemeLdSerializer { 

	ObjectMapper mapper = new ObjectMapper(); 
		
	public ConceptSchemeLdSerializer() {}
	
	/**
	 * This method provides full serialization of a concept scheme
	 * @param conceptScheme
	 * @return full concept scheme view
	 * @throws IOException
	 */
	public String serialize(ConceptScheme conceptScheme) throws IOException {
		
		mapper.registerModule(new JsonldModule()); 
		JsonldResourceBuilder<ConceptScheme> jsonResourceBuilder = JsonldResource.Builder.create();
		jsonResourceBuilder.context(WebEntityFields.ENTITY_CONTEXT);
		String jsonString = mapper.writer().writeValueAsString(jsonResourceBuilder.build(conceptScheme));
		return jsonString;
	}

}
