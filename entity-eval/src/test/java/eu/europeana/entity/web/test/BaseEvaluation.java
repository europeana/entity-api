package eu.europeana.entity.web.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.params.CommonParams;
import org.apache.solr.common.util.SimpleOrderedMap;

import eu.europeana.entity.solr.exception.EntitySuggestionException;
import eu.europeana.entity.solr.model.vocabulary.SuggestionFields;
import eu.europeana.entity.solr.service.SolrEntityService;
import eu.europeana.entity.solr.service.impl.SuggestionUtils;
import eu.europeana.entity.web.model.view.EntityPreview;

public abstract class BaseEvaluation {

	final Logger LOGGER = LogManager.getLogger(getClass());
	int posLanguage = 0;
	int posQuery = 1;
	int posEntity = 2;
	int posType = 3;
	int posId = 4;
	Map<String, String> inputMap = new LinkedHashMap<String, String>();
	Map<String, String[]> inputLinesMap = new LinkedHashMap<String, String[]>();
	List<String> ids = new ArrayList<>();
	String field = "label";
//	String field = "text";
	File datasetFile;
	File testResultsFile;
	private SuggestionUtils suggestionHelper;
	static final String FIELD_SKOS_PREF_LABEL = "skos_prefLabel";
	static final String FIELD_LABEL = "label";
	static final String SUGGESTER = "suggester";
	static final String FIELD_ID = "id";
	static final String HEAD_LINE = "Language	Query	Character	Query@n	Rank found	Entity	Type \t\n";
	
	
	protected File getClasspathFile(String fileName) throws URISyntaxException, IOException, FileNotFoundException {
		URL resource = getClass().getResource(fileName);
		if (resource == null) {
			LOGGER.info("Cannot classpath file: " + fileName);
			return null;
		}
		URI fileLocation = resource.toURI();
		return (new File(fileLocation));
	}
	
	
	protected File getClasspathFile(String resourceFolder, String fileName) throws URISyntaxException, IOException, FileNotFoundException {
		//URL resource = getClass().getResource( + resourceFolder);
		File resFolder = new File("./src/test/resources/"+resourceFolder+"/");
		if (!resFolder.exists()) {
			LOGGER.info("Cannot find resource folder:" + resourceFolder);
			return null;
		}
		return new File(resFolder, fileName);
	}
	
	void initEval(String datasetFilename, String resultsFile) throws FileNotFoundException, IOException, URISyntaxException {
		datasetFile = getClasspathFile("datasets", datasetFilename);
		testResultsFile = getClasspathFile("results", resultsFile);
		if(datasetFile == null || !datasetFile.exists()){
			LOGGER.info("Cannot find dataset file:" + datasetFile);
			return;
		}
		
			
		LineIterator iterator = FileUtils.lineIterator(datasetFile, "utf-8");
		String line;
		String[] parts;
		int count = 0;
		while (iterator.hasNext()) {
			line = (String) iterator.next();
			System.out.println(line);
			parts = line.split(";");
//			for (int i = 0; i < parts.length; i++) {
				inputMap.put(parts[posId], parts[posEntity]);
				inputLinesMap.put(parts[posId], parts);
				ids.add(parts[posId]);
//			}
			count++;
//			if(count ==3)
//				break;
			
		}
	}
	
	void generateResults(String id, String label, String testCase) throws SolrServerException, IOException {
		List<String> resIds;
		String queryAtN;
		int rank;
//		String resultLine;
		String inputLine[];
		List<String> lines = new ArrayList<String>();
		SolrQuery query; 
		for (int i = 1; i <= label.length(); i++) {
			queryAtN = label.substring(0, i);
//			if(!queryAtN.equals(label))
			query = buildSolrQuery(queryAtN, testCase);
			resIds = getResults(query, testCase);
			rank = computeRank(id, resIds);
			
			inputLine = inputLinesMap.get(id);
			String resultLine = inputLine[posLanguage] + "\t" 
					+ label + "\t" 
					+ i + "\t"
					+ queryAtN + "\t"
					+ rank + "\t"
					+ id + "\t"
					+ "Entity"+"\t";
//			System.out.println(resultLine);
			lines.add(resultLine);			
		}
		FileUtils.writeLines(testResultsFile, "utf-8", lines, true);
		
	}

	List<String> getResults(SolrQuery query, String testCase){
//		String request = "http://entity-api.eanadev.org:9292/solr/test/select?fl=id&indent=on&wt=csv&q=text:(query)";
		@SuppressWarnings("deprecation")
		SolrClient solrClient = new HttpSolrClient("http://entity-api.eanadev.org:9292/solr/test/");
		
		QueryResponse rsp;
		List<String> res = null;
		
		try {
			rsp = solrClient.query(query);
			res = new ArrayList<String>();
			switch(testCase){
				case SUGGESTER:
					extractSuggestEntityIds(rsp, res);
					break;
				default:
					extractEntityIds(rsp, res);
			}
				
			solrClient.close();
		} catch (SolrServerException | IOException | EntitySuggestionException e) {
			throw new RuntimeException("cannot get results for solr query: " + query.toQueryString(), e);
		}
		
		return res;
	}


	@SuppressWarnings({"unchecked", "rawtypes"})
	private void extractSuggestEntityIds(QueryResponse rsp, List<String> res) throws EntitySuggestionException {
		
		Map<String, Object> suggest = (Map<String, Object>) rsp.getResponse().get(SuggestionFields.SUGGEST);
		SimpleOrderedMap<?> suggestionsMap = (SimpleOrderedMap) suggest.get(SuggestionFields.PREFIX_SUGGEST_ENTITY);
		List<SimpleOrderedMap<?>> suggestions = null;
		String searchedTerm = ""; 
		Set<String> highlightTerms;
		
		if ((SimpleOrderedMap) suggestionsMap.getVal(0) != null) {
			suggestions = (List<SimpleOrderedMap<?>>) ((SimpleOrderedMap) suggestionsMap.getVal(0))
					.get(SuggestionFields.SUGGESTIONS);
			searchedTerm = suggestionsMap.getName(0); 
		}

		// add exact matches to list
		if (suggestions != null){
			EntityPreview preview;
			String payload;
			for (SimpleOrderedMap<?> entry : suggestions) {
				// cut to rows
				payload = (String) entry.get(SuggestionFields.PAYLOAD);
				highlightTerms = new HashSet<>(Arrays.asList(searchedTerm));
				preview = getSuggestionHelper().parsePayload(payload, new String[]{"All"}, highlightTerms);
				res.add(preview.getEntityId());
			}
		}
	}

	private void extractEntityIds(QueryResponse rsp, List<String> res)
			throws EntitySuggestionException {
		
		SolrDocumentList docList = rsp.getResults();
		if(docList != null){
			for (SolrDocument solrDocument : docList) {
				res.add((String)solrDocument.getFieldValue(FIELD_ID));
			}
		}
	}

	int computeRank(String id, List<String> ids) {
		if(ids.contains(id))
			return (ids.indexOf(id) + 1);
		else
			return 0;
	}
	
	SolrQuery buildSolrQuery(String query, String testCase){
		
		SolrQuery solrQuery = null;
		String[] searchParams; 
		String[] fields;
		switch(testCase){
			case FIELD_SKOS_PREF_LABEL:
				//?q=skos_prefLabel%3AMozart*&sort=derived_score+desc&rows=100&fl=skos_prefLabel%2C+payload%2C+id%2C+pagerank%2C+derived_score%2C+europeana_term_hits%2C+europeana_doc_count&wt=json&indent=true
				searchParams = new String[]{CommonParams.SORT, "derived_score desc", "q.op", "AND"};
				fields = new String[]{"id", "skos_prefLabel", "payload", "derived_score"};
				solrQuery = buildSolrQuery(FIELD_SKOS_PREF_LABEL + ":(" +query + "*)", searchParams, fields);
				break;
			case FIELD_LABEL:
				//?q=label%3AMozart*&sort=derived_score+desc&rows=100&fl=skos_prefLabel%2C+payload%2C+id%2C+pagerank%2C+derived_score%2C+europeana_term_hits%2C+europeana_doc_count&wt=json&indent=true
				searchParams = new String[]{CommonParams.SORT, "derived_score desc", "q.op", "AND"};
				fields = new String[]{"id", "skos_prefLabel", "payload", "derived_score"};
				solrQuery = buildSolrQuery(FIELD_LABEL + ":(" +query + "*)", searchParams, fields);
				break;	
			case SUGGESTER:	
				//?q=skos_prefLabel%3AMozart*&sort=derived_score+desc&rows=100&fl=skos_prefLabel%2C+payload%2C+id%2C+pagerank%2C+derived_score%2C+europeana_term_hits%2C+europeana_doc_count&wt=json&indent=true
				solrQuery = buildSuggesterQuery(query);
				break;
		}
		return solrQuery;
	}
	
	SolrQuery buildSolrQuery(String query, String[] searchParams, String[] fields) {
		// String request =
		// "http://entity-api.eanadev.org:9292/solr/test/select?fl=id&indent=on&wt=csv&q=text:(query)";
		/**
		 * Construct a SolrQuery
		 */
		SolrQuery solrQuery = new SolrQuery(CommonParams.Q, query, searchParams);		
//		solrQuery.setQuery(field + ":(" + query + ")");
		solrQuery.setFields(fields);
		// System.out.println(solrQuery.toQueryString());
		return solrQuery;
	}
	
	SolrQuery buildSuggesterQuery(String query) {
		// String request =
		// http://entity-api.eanadev.org:9292/solr/test/suggestEntity?indent=on&q=leon*&wt=json
		/**
		 * Construct a SolrQuery
		 */
		SolrQuery solrQuery = new SolrQuery(query);		
		solrQuery.setRequestHandler(SolrEntityService.HANDLER_SUGGEST);
		return solrQuery;
	}
	
	public SuggestionUtils getSuggestionHelper() {
		if (suggestionHelper == null)
			suggestionHelper = new SuggestionUtils();
		return suggestionHelper;
	}
}
