package eu.europeana.entity.web.test;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

public class EntityEvalTest extends BaseEvaluation{
	
	
	@Test
	public void testSearch() throws Exception{
		initEval("AutoCompleteDataset_Revised_1.csv", "results");
		String headline = "Language	Query	Character	Query@n	Rank found	Entity	Type \t\n";
		FileUtils.writeStringToFile(testResultsFile, headline, "utf-8", false);
		for (String id : ids) {
			System.out.println("searching: " + inputLinesMap.get(id)[posEntity]);
			generateResults(id, inputMap.get(id), FIELD_SKOS_PREF_LABEL);	
		}
		System.out.println("done");
	}
	
}
