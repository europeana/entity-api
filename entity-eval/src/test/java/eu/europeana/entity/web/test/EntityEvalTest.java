package eu.europeana.entity.web.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.junit.Test;

public class EntityEvalTest {
	private final Logger LOGGER = Logger.getLogger(getClass());
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
	File outFile;
	
	void initEval() throws FileNotFoundException, IOException, URISyntaxException {
		String inputFile = "/AutoCompleteDataset_Revised_1.csv";
		outFile = new File("e:/work/Europeana/entity_eval/search_"+field+".txt");
		LineIterator iterator = FileUtils.lineIterator(getClasspathFile(inputFile), "utf-8");
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

	protected File getClasspathFile(String fileName) throws URISyntaxException, IOException, FileNotFoundException {
		URL resource = getClass().getResource(fileName);
		if (resource == null) {
			LOGGER.info("Cannot classpath file: " + fileName);
			return null;
		}
		URI fileLocation = resource.toURI();
		return (new File(fileLocation));
	}
	
	List<String> getResults(String query) throws SolrServerException, IOException{
//		String request = "http://entity-api.eanadev.org:9292/solr/test/select?fl=id&indent=on&wt=csv&q=text:(query)";
		@SuppressWarnings("deprecation")
		SolrClient solrClient = new HttpSolrClient("http://entity-api.eanadev.org:9292/solr/test/");
		/**
		 * Construct a SolrQuery
		 */
		SolrQuery solrQuery = new SolrQuery();
		solrQuery.setQuery(field + ":(" + query + ")");
		solrQuery.setFields("id");
//		System.out.println(solrQuery.toQueryString());
		
		QueryResponse rsp = solrClient.query(solrQuery);
		SolrDocumentList docList = rsp.getResults();
		List<String> res = new ArrayList<String>();
		for (SolrDocument solrDocument : docList) {
			res.add((String)solrDocument.getFieldValue("id"));
		}
		
		solrClient.close();
		return res;
	}
	
	@Test
	public void testSearch() throws Exception{
		initEval();
		String headline = "Language	Query	Character	Query@n	Rank found	Entity	Type \t\n";
		FileUtils.writeStringToFile(outFile, headline, "utf-8", false);
		for (String id : ids) {
			System.out.println("searching: " + inputLinesMap.get(id)[posEntity]);
			generateResults(id, inputMap.get(id));	
		}
		System.out.println("done");
	}

	private void generateResults(String id, String label) throws SolrServerException, IOException {
		List<String> resIds;
		String queryAtN;
		int rank;
//		String resultLine;
		String inputLine[];
		List<String> lines = new ArrayList<String>();
		for (int i = 1; i <= label.length(); i++) {
			queryAtN = label.substring(0, i);
			if(!queryAtN.equals(label))
				queryAtN+="*";
			resIds = getResults(queryAtN);
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
		FileUtils.writeLines(outFile, "utf-8", lines, true);
		
	}

	private int computeRank(String id, List<String> ids) {
		if(ids.contains(id))
			return (ids.indexOf(id) + 1);
		else
			return 0;
	}
	
}
