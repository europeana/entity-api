package org.entity.contentfull.parser;

import java.io.IOException;

import org.entity.contentfull.model.ContentfullPage;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;

import ioinformarics.oss.jackson.module.jsonld.JsonldModule;

import com.fasterxml.jackson.core.JsonParser.Feature;

public class ContentfullPageParser {

	public ContentfullPage parseContentfullPage(String contentfullPageJsonStr) throws JsonParseException, IOException {

		JsonParser parser;
	    ObjectMapper mapper = new ObjectMapper();
	    mapper.configure(Feature.AUTO_CLOSE_SOURCE, true);
	    mapper.configure(Feature.ALLOW_MISSING_VALUES, true);	    
	    
	    JsonFactory jsonFactory = mapper.getFactory();
		
		/**
		 * parse JsonLd string using JsonLdParser
		 */
		parser = jsonFactory.createParser(contentfullPageJsonStr);
		ContentfullPage contentfullPage = mapper.readValue(parser, ContentfullPage.class); 
        
		
		return contentfullPage;
		
	}
}
