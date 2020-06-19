package org.entity.contentfull;

import com.contentful.java.cda.CDAArray;
import com.contentful.java.cda.CDAClient;
import com.contentful.java.cda.CDAContentType;
import com.contentful.java.cda.CDAEntry;
import com.contentful.java.cda.CDASpace;
import com.contentful.tea.java.models.exceptions.TeaException;
import com.contentful.tea.java.services.contentful.Contentful;

/**
 * Hello world!
 *
 */

public class App 
{

    public static void main( String[] args )
    {
        //setupRoute(request);

        try {
          Contentful contentful = new Contentful();
          contentful.reset().loadFromPreferences();

          final CDAClient client = contentful.getCurrentClient();
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
        		        .fetch(CDAContentType.class)
        		        .all();

          
      	int i=0;
      	i++;

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
    
    
}
