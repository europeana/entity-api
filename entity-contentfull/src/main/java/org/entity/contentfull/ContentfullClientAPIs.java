package org.entity.contentfull;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.entity.contentfull.parser.ContentfullTypeParser;

import com.contentful.java.cda.CDAArray;
import com.contentful.java.cda.CDAClient;
import com.contentful.java.cda.CDAEntry;
import com.contentful.java.cda.CDASpace;
import com.contentful.tea.java.models.exceptions.TeaException;
import com.contentful.tea.java.services.contentful.Contentful;

public class ContentfullClientAPIs 
{
	private static Contentful contentful = null;
	private static CDAClient client = null;
	
	private static void init() {
		contentful = new Contentful();
		contentful.reset().loadFromPreferences();
		client = contentful.getCurrentClient();
	}

    public static CDAClient getClient() {
    	if(client==null) init();
		return client;
	}

	public static void main( String[] args )
    {
        try {
          init();
          
          final CDAEntry cdaLanding = client
              .fetch(CDAEntry.class)
              .include(5)
              .where("locale", "en-US")
              .one("2uNOpLMJioKeoMq8W44uYc");
          
          final CDAArray courses = client
                  .fetch(CDAEntry.class)
                  .include(5)
                  .withContentType("course")
                  .where("locale", "en-US")
                  .orderBy("-sys.createdAt")
                  .all();

          
          final CDAArray all =
        		    client
        		        .fetch(CDAEntry.class)
        		        .withContentType("wikidataEntity")
        		        .where("fields.title", "Novi Bor")
        		        .all();

          String dataChange= "2020-06-20T13:36:47.680Z";
          List<CDAEntry> res = getEntriesChangedAfterDate(client,dataChange);

          
          
          //testing the parsing of the contentfull types
  		  InputStream inputStream = ContentfullClientAPIs.class.getClassLoader().getResourceAsStream("ContentfullType-example.json");
  		  String contentfullTypeJsonStr = IOUtils.toString(inputStream, StandardCharsets.UTF_8);

          ContentfullTypeParser fieldParser = new ContentfullTypeParser();
          fieldParser.parseContentfullType(contentfullTypeJsonStr);

          
          
      		int dummy=0;
      		dummy++;

        } catch (Throwable t) {
          throw new TeaException.HomeLayoutNotFoundException(t);
        } finally {
        }

    }
    
    /**
     * This method fetches a Contentfull space
     * @param client	CDAClient to be used for accessing the Contentfull APIs
     * @return
     */
    public CDASpace getSpace(CDAClient client) {
    	
    	// Creating the Contentful client in case the access token, space id, and environment id are not already set 
//		final CDAClient client = 
//				CDAClient
//					.builder()
//					.setToken("<access_token>") // required
//					.setSpace("<space_id>") // required
//					.setEnvironment("<environment_id>") // optional, defaults to `master`
//					.build();

    	final CDASpace space =
          	    client
          	        .fetchSpace();
    	return space;
    }
    
    /**
     * This method gets the Contentfull model for the given content type (an array of CDAEntry instances)
     * @param client	CDAClient to be used for accessing the Contentfull APIs
     * @return
     */
    public CDAArray getContentModelForSpace(CDAClient client) {

    	/*
    	 * please note that the list of CDAEntry-ies includes also those that are 1.level linked 
    	 * to the main entries (e.g. for the courses entries it returns their lessons as well)
    	 */
        final CDAArray all =
      		    client
      		        .fetch(CDAEntry.class)
      		        .withContentType("wikidataEntity")
      		        .where("fields.title", "Novi Bor")
      		        .all();

    	return all;
    }
    
    /**
     * Getting a List<CDAEntry> containing entries that are changed after some Date.
     * 
     * @param client
     * @param date
     * @return
     * @throws ParseException
     */
    
    public static List<CDAEntry> getEntriesChangedAfterDate (CDAClient client, String date) throws ParseException {
    	
    	List<CDAEntry> results = new ArrayList<CDAEntry>(); 
    	/*
    	 * please note that the list of CDAEntry-ies includes also those that are 1.level linked 
    	 * to the main entries (e.g. for the courses entries it returns their lessons as well)
    	 */
        final CDAArray all =
      		    client
      		        .fetch(CDAEntry.class)
      		        .withContentType("wikidataEntity")
      		        .all();
        
        
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    	Date dateToCompare = formatter.parse(date);
        
        Map<String,CDAEntry> entries = all.entries();
        for (Map.Entry<String,CDAEntry> entry : entries.entrySet())
        {
        	CDAEntry cdaEntry = entry.getValue();
        	Map<String,Object> attributes = cdaEntry.attrs();
        	for (Map.Entry<String,Object> attrEntry : attributes.entrySet())
            {
        		if(attrEntry.getKey().compareTo("updatedAt")==0)
        		{
        			Date dateUpdatedAt = formatter.parse((String)attrEntry.getValue());
        			if(dateUpdatedAt.after(dateToCompare))
        			{
        				results.add(cdaEntry);
        			}
        		}
            }
        	
        }

        return results;
        
    	
    }
    
    
}
