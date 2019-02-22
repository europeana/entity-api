package eu.europeana.entity.solr.model;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * This class implements supporting methods for Solr*Impl classes e.g. normalization of the content
 * to match to the required output format.
 * @author GrafR
 *
 */
public class SolrUtils {

	/**
	 * This method removes unnecessary prefixes from the fields in format Map<String, String> languageMap
	 * e.g. "skos_prefLabel"
	 * @param labelId e.g. ConceptSolrFields.PREF_LABEL
	 * @param languageMap e.g. prefLabel
	 * @return normalized content in format Map<String, String>  
	 */
	public static Map<String, String> normalizeStringMap(String labelId, Map<String, String> languageMap) {
		Map<String, String> res;
		int prefixLen = labelId.length() + 1;
		if (languageMap.keySet().iterator().next().startsWith(labelId)) {
			res = languageMap.entrySet().stream().collect(Collectors.toMap(
					entry -> entry.getKey().substring(prefixLen), 
					entry -> entry.getValue())
			);	
		} else {
			res = languageMap;
		}
		return res;
	}
	
	/**
	 * This method removes unnecessary prefixes from the fields in format Map<String, List<String>> 
	 * e.g. "skos_altLabel"
	 * @param labelId e.g. ConceptSolrFields.ALT_LABEL
	 * @param languageMap e.g. altLabel
	 * @return normalized content in format Map<String, List<String>>  
	 */
	public static Map<String, List<String>> normalizeStringListMap(String labelId, Map<String, List<String>> languageMap){
		Map<String, List<String>> res;
		int prefixLen = labelId.length() + 1;
		if (languageMap.keySet().iterator().next().startsWith(labelId)) {
			res = languageMap.entrySet().stream().collect(Collectors.toMap(
					entry -> entry.getKey().substring(prefixLen), 
					entry -> entry.getValue())
			);	
		} else {
			res = languageMap;
		}
		return res;
	}
	
	/**
	 * This method removes unnecessary prefixes from the fields in format List<String> itemList
	 * e.g. "rdagr2_dateOfBirth"
	 * @param labelId e.g. AgentSolrFields.DATE_OF_BIRTH
	 * @param itemList e.g. dateOfBirth
	 * @return normalized content in formatString[]  
	 */
	public static String[] normalizeStringList(String labelId, List<String> itemList) {
		List<String> res;
		int prefixLen = labelId.length() + 1;
		if (itemList.iterator().next().startsWith(labelId)) {
			res = itemList.stream() 
                     .map(entry -> entry.substring(prefixLen)) 
                     .collect(Collectors.toList()			
			);	
		} else {
			res = itemList;
		}
		return convertListToArray(res);
	}
	
	/**
	 * This method converts a string list to an array
	 * @param itemList
	 * @return string array
	 */
	public static String[] convertListToArray(List<String> itemList) {
		String[] itemArr = new String[itemList.size()];
		itemArr = itemList.toArray(itemArr);
		return itemArr;
	}

}
