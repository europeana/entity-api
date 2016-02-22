package eu.europeana.entity.web.service.impl;

import javax.annotation.Resource;

import org.apache.http.HttpException;

import eu.europeana.entity.definitions.model.Concept;
import eu.europeana.entity.solr.exception.EntityServiceException;
import eu.europeana.entity.solr.service.SolrEntityService;
import eu.europeana.entity.web.service.EntityService;

public class EntityServiceImpl implements EntityService{

	public final String BASE_URL_DATA = "http://data.europeana.eu/";  
	
	@Resource 
	SolrEntityService solrEntityService;
	
	@Override
	public Concept retrieveByUrl(String type, String namespace, String identifier) throws HttpException{
		
		String entityUri = BASE_URL_DATA + type + "/" + namespace + "/" + identifier;
		try {
			return solrEntityService.searchByUrl(entityUri);
		} catch (EntityServiceException e) {
			throw new HttpException("Cannot retrieve entity by URI", e);
		}
	}
}
