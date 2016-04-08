package eu.europeana.api.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.springframework.web.servlet.ModelAndView;

/**
 * TODO: move to api commons project
 * @author GordeaS
 *
 */
@Deprecated
public class JsonWebUtils {
	
	private static final Logger log = Logger.getLogger(JsonWebUtils.class);
	private static ObjectMapper objectMapper = new ObjectMapper();
	
	public static ModelAndView toJson(Object object) {
		return toJson(object, null);
	}
	
	public static ModelAndView toJson(String json, String callback) {
		String resultPage = "json";
		Map<String, Object> model = new HashMap<String, Object>();
		model.put(resultPage, json);
		if (StringUtils.isNotBlank(callback)) {
			resultPage = "jsonp";
			model.put("callback", callback);
		}
		return new ModelAndView(resultPage, model);
	}

	public static ModelAndView toJson(Object object, String callback) {
		return toJson(object, callback, false, -1);
	}
		
	public static ModelAndView toJson(Object object, String callback, boolean shortObject, int objectId) {
			
		objectMapper.setSerializationInclusion(Inclusion.NON_NULL);
		String errorMessage = null;
		try {
			String jsonStr = objectMapper.writeValueAsString(object);	
			if (shortObject) {
				String idBeginStr = "id\":{";
				int startIdPos = jsonStr.indexOf(idBeginStr);
				int endIdPos = jsonStr.indexOf("}", startIdPos);
				jsonStr = jsonStr.substring(0, startIdPos) + idBeginStr.substring(0, idBeginStr.length() - 1) 
				    + Integer.valueOf(objectId) + jsonStr.substring(endIdPos + 1);
			}
			return toJson(jsonStr, callback);
		} catch (JsonGenerationException e) {
			log.error("Json Generation Exception: " + e.getMessage(),e);
			errorMessage = "Json Generation Exception: " + e.getMessage() + " See error logs!";
		} catch (JsonMappingException e) {
			log.error("Json Mapping Exception: " + e.getMessage(),e);
			errorMessage = "Json Generation Exception: " + e.getMessage() + " See error logs!";
		} catch (IOException e) {
			log.error("I/O Exception: " + e.getMessage(),e);
			errorMessage = "I/O Exception: " + e.getMessage() + " See error logs!";
		}
		//Report technical errors...
		String resultPage = "json";
		return new ModelAndView(resultPage, "errorMessage", errorMessage);
	}
		
}
