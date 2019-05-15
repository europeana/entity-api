package eu.europeana.entity.utils;

public class EntityUtils {

	public static String createWikimediaResourceString(String wikimediaCommonsId) {
		assert wikimediaCommonsId.contains("Special:FilePath/");
		return wikimediaCommonsId.replace("Special:FilePath/", "File:");
	}
    
}
