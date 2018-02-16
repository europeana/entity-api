package eu.europeana.entity.utils.json;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

/**
 * @Deprecated the provided methods must be replaced by proper usage of the json to entity parser 
 *
 */
public class JsonUtils {
	
private static final String SEPARATOR_CLOSE_CURLY = "}";
private static final String SEPARATOR_OPEN_CURLY = "{";
private static final String SEPARATOR_CLOSE_BRACKET = "]";
private static final String SEPARATOR_OPEN_BRACKET = "[";
private static final String SEPARATOR_QUOTE = "\"";
private static final String SEPARATOR_SEMICOLON = ";";
private static final String SEPARATOR_COLON = ":";
	

/**
 * wrong implementation.correct it before use
 * @param mp
 * @return
 */
	@Deprecated  
	public static String mapToString(Map<String,String> mp) {
		//TODO change implementation to use String builder
		
		String res = "";
	    Iterator<Map.Entry<String, String>> it = mp.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry<String, String> pairs = (Map.Entry<String, String>) it.next();
	        if (res.length() > 0) {
	        	res = res + ",";
	        }
	        res = res + pairs.getKey() + SEPARATOR_SEMICOLON + pairs.getValue();
	        it.remove(); // avoids a ConcurrentModificationException
	    }
	    if (res.length() > 0) {
	    	res = SEPARATOR_OPEN_BRACKET + res + SEPARATOR_CLOSE_BRACKET;
	    }
	    return res;
	}	
	
	/**
	 * wrong implementation.correct it before use
	 * @param mp
	 * @return
	 */
	@Deprecated 
	public static String mapToStringExt(Map<String,Integer> mp) {
		String res = "";
	    Iterator<Map.Entry<String, Integer>> it = mp.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry<String, Integer> pairs = (Map.Entry<String, Integer>) it.next();
	        if (res.length() > 0) {
	        	res = res + ",";
	        }
	        res = res + pairs.getKey() + SEPARATOR_SEMICOLON + pairs.getValue();
	        it.remove(); // avoids a ConcurrentModificationException
	    }
	    if (res.length() > 0) {
	    	res = SEPARATOR_OPEN_BRACKET + res + SEPARATOR_CLOSE_BRACKET;
	    }
	    return res;
	}

	
	public static String mapOfListsToString(Map<String, List<String>> map, String keyPrefix) {
		
		StringBuilder builder = new StringBuilder(SEPARATOR_OPEN_CURLY);
// do it outside		
//		if(map == null || map.isEmpty())
//			return null;
		String key;
		
		//remove the key prefix e.g. "prefLabel" + "."   
		int prefixLength = keyPrefix.length() +1 ;
		
		for (Map.Entry<String, List<String>> entry : map.entrySet()) {
			key = entry.getKey();
			if(keyPrefix != null) {
				key = key.substring(prefixLength);
			}
			builder.append(SEPARATOR_QUOTE).append(key).append(SEPARATOR_QUOTE).append(SEPARATOR_COLON);
			builder.append(listToString(entry.getValue()));			
		}
		
		builder.append(SEPARATOR_CLOSE_CURLY);
		
		return builder.toString();
	}

	private static String listToString(List<String> values) {
		if(values.size() == 1)
			return  SEPARATOR_QUOTE + values.get(0) + SEPARATOR_QUOTE;
		
		StringBuilder builder = new StringBuilder(SEPARATOR_OPEN_BRACKET);
		for (String value : values) {
			builder.append(SEPARATOR_QUOTE).append(value).append(SEPARATOR_QUOTE);
			builder.append(SEPARATOR_SEMICOLON);
		}
		//remove last semicolon
		builder.deleteCharAt(builder.lastIndexOf(SEPARATOR_SEMICOLON));
		builder.append(SEPARATOR_CLOSE_BRACKET);
		return builder.toString();
	}	
			
	
	public static String extractEntityListStringFromJsonString(String jsonString) {
		return extractEntityListStringFromJsonString(jsonString, "\":(.*?)}}]");
	}

	
	public static String extractEntityListStringFromJsonString(String jsonString, String regex) {
		String res = "";
		String ITEMS = "contains";
		if (StringUtils.isNotEmpty(jsonString)) {
			Pattern pattern = Pattern.compile(ITEMS + regex);
			Matcher matcher = pattern.matcher(jsonString);
			if (matcher.find())
			{
			    res = matcher.group(1) + "}}]";
			}
		}
		return res;
	}
    

    /**
     * This method converts Entity JSON list string to array.
     * @param value The input string
     * @return resulting map
     */
    public static String[] splitEntityListStringToArray(String value) {
        return value.split("\\}\\},\\{");
    }
    
	
	
}
