package eu.europeana.entity.web.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.junit.Test;

public class EvalSuggestOptionsTest extends BaseEvaluation {

	List<String> getResults(SolrQuery solrQuery) {
		
		@SuppressWarnings("deprecation")
		SolrClient solrClient = new HttpSolrClient("http://entity-api.eanadev.org:9292/solr/test/", null, false);

		QueryResponse rsp;
		List<String> res = null;

		try {
			System.out.println(solrQuery.toString());
			rsp = solrClient.query(solrQuery);
			SolrDocumentList docList = rsp.getResults();
			res = new ArrayList<String>();
			for (SolrDocument solrDocument : docList) {
				res.add((String) solrDocument.getFieldValue(FIELD_ID));
			}

			solrClient.close();
		} catch (SolrServerException | IOException e) {
			throw new RuntimeException("cannot get results for solr query: " + solrQuery.toQueryString(), e);
		}

		return res;
	}

	@Test
	public void testSuggestPrefLabel() throws Exception {
		initEval("AutoCompleteDataset_2.csv", FIELD_SKOS_PREF_LABEL + "SuggestionsResults.csv");
		FileUtils.writeStringToFile(testResultsFile, HEAD_LINE, "utf-8", false);
		for (String id : ids) {
			System.out.println("searching: " + inputLinesMap.get(id)[posEntity]);
			generateResults(id, inputMap.get(id), FIELD_SKOS_PREF_LABEL);
		}
		System.out.println("done");
	}

	@Test
	public void testSuggestLabel() throws Exception {
		initEval("AutoCompleteDataset_2.csv", FIELD_LABEL + "SuggestionsResults.csv");
		FileUtils.writeStringToFile(testResultsFile, HEAD_LINE, "utf-8", false);
		for (String id : ids) {
			System.out.println("searching: " + inputLinesMap.get(id)[posEntity]);
			generateResults(id, inputMap.get(id), FIELD_LABEL);
		}
		System.out.println("done");
	}
	
	@Test
	public void testSuggester() throws Exception {
		initEval("AutoCompleteDataset_2.csv", "suggesterResults.csv");
		FileUtils.writeStringToFile(testResultsFile, HEAD_LINE, "utf-8", false);
		for (String id : ids) {
			System.out.println("searching: " + inputLinesMap.get(id)[posEntity]);
			generateResults(id, inputMap.get(id), SUGGESTER);
		}
		System.out.println("done");
	}
}
