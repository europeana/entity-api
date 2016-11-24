package eu.europeana.annotation.client.integration.web;

import static org.junit.Assert.fail;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.apache.commons.lang3.StringUtils;
import org.apache.stanbol.commons.exception.JsonParseException;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import eu.europeana.entity.client.exception.ResolveException;
import eu.europeana.entity.definitions.model.Entity;
import eu.europeana.entity.definitions.model.vocabulary.EntityTypes;


/**
 * This class aims at testing of the entity methods.
 * @author GrafR
 */
public class EntityRetrievalTest extends BaseEntityTest { 


	private final String TEST_ENTITY_TYPE = EntityTypes.Agent.getInternalType();
	private final String TEST_ENTITY_NAMESPACE = "base";
	private final String TEST_ENTITY_ID = "33041";

	private final String TEST_PLACE_ENTITY_TYPE = EntityTypes.Place.getInternalType();
	private final String TEST_PLACE_ENTITY_NAMESPACE = "base";
	private final String TEST_PLACE_ENTITY_ID = "124437";
	
	private final String TEST_DBPEDIA_RESOLVE_URI = "http://dbpedia.org/resource/Charles_Dickens"; 
	private final String TEST_EUROPEANA_RESOLVE_RESPONSE_URI = "http://data.europeana.eu/agent/base/59904";

	String searchAnalysisFodler = "./src/test/resources/";

	String REPORT_SOUND_AND_VISION = "report-sound-and-vision.csv"; 
	String RESOLVED_REPORT_SOUND_AND_VISION = "resolved-report-sound-and-vision.csv"; 
	
	
//	@Test
	public void getEntity() throws JsonParseException, IllegalAccessException, 
									IllegalArgumentException, InvocationTargetException {
		
		/**
		 * get entity by type, namespace and identifier
		 */
		ResponseEntity<String> response = retrieveEntity(
				getApiKey()
				, TEST_ENTITY_TYPE
				, TEST_ENTITY_NAMESPACE
				, TEST_ENTITY_ID
				);
		
		validateResponse(response, HttpStatus.OK);
	}
	
	
//	@Test
	public void getPlaceEntity() throws JsonParseException, IllegalAccessException, 
									IllegalArgumentException, InvocationTargetException {
		
		/**
		 * get entity by type, namespace and identifier
		 */
		ResponseEntity<String> response = retrieveEntity(
				getApiKey()
				, TEST_PLACE_ENTITY_TYPE
				, TEST_PLACE_ENTITY_NAMESPACE
				, TEST_PLACE_ENTITY_ID
				);
		
		validateResponse(response, HttpStatus.OK);
	}
	
	
//	@Test
	public void resolveEntity() throws JsonParseException, IllegalAccessException, 
									IllegalArgumentException, InvocationTargetException {
		
		/**
		 * resolve entity by URI
		 */
		List<Entity> response = resolveEntity(
				getApiKey()
				, TEST_DBPEDIA_RESOLVE_URI
				);
		String europeanaId = response.get(0).getEntityId();
		
		assertTrue(europeanaId.equals(TEST_EUROPEANA_RESOLVE_RESPONSE_URI));
	}
	
	
	@Test
	public void resolveEntitiesForAuthorList() throws IOException {
		
		String DBPEDIA_ID_STR = "DBPedia ID";
		String EUROPEANA_ID_STR = "Europeana ID";
		
		Map<String, String> authorMap = new HashMap<String, String>();
		
		File datasetFile = FileUtils.getFile(searchAnalysisFodler + REPORT_SOUND_AND_VISION);
		if(!datasetFile.exists())
			fail("required dataset file doesn't exist" + datasetFile);
		
		LineIterator iterator = FileUtils.lineIterator(datasetFile);
		String line;
		int cnt = 0;
		final String cellSeparator = ";"; 
		final String lineBreak = "\n"; 
		
		File recordFile = FileUtils.getFile(searchAnalysisFodler + RESOLVED_REPORT_SOUND_AND_VISION);
		
		while (iterator.hasNext()) {
			
			String dbpediaId = ""; 
			String europeanaId = ""; 
			
			int DBPEDIA_ID_COL_POS = 1;

			line = (String) iterator.next();
			String[] items = line.split(cellSeparator);
			if (items != null && items.length >= DBPEDIA_ID_COL_POS && items[DBPEDIA_ID_COL_POS] != null) {
				if (cnt > 0) {
					dbpediaId = items[DBPEDIA_ID_COL_POS];
					dbpediaId = dbpediaId.replace("\"", "").replace("\t", "");
					if (StringUtils.isNotEmpty(dbpediaId) && !authorMap.containsKey(dbpediaId)) {
						log.debug("dbpediaId: " + dbpediaId);
						try {
		 					List<Entity> response = resolveEntity(
									getApiKey()
									, dbpediaId
									);
		 					if (response.size() > 0) {
								europeanaId = response.get(0).getEntityId();
								log.debug("europeanaId: " + europeanaId + ", count: " + cnt);
								authorMap.put(dbpediaId, europeanaId);
		 					}
						} catch (Exception re) {
							log.error("Can not resolve entity for DBPedia URI: " + dbpediaId, re);
						}
					}
				}
				cnt++;
			}
		}

		String row = new StringBuilder()
				.append(DBPEDIA_ID_STR).append(cellSeparator)
				.append(EUROPEANA_ID_STR).append(lineBreak).toString();
		FileUtils.writeStringToFile(recordFile, row, "UTF-8", true);

		for (String key: authorMap.keySet()) {
			row = new StringBuilder()
				.append(key).append(cellSeparator)
				.append(authorMap.get(key)).append(lineBreak)
				.toString();
			FileUtils.writeStringToFile(recordFile, row, "UTF-8", true);
		}		
	}
	

	
	
}
