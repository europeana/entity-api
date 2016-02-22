package eu.europeana.entity.web.service;

import org.apache.http.HttpException;

import eu.europeana.entity.definitions.model.Concept;
import eu.europeana.entity.solr.exception.EntityServiceException;

public interface EntityService {

	Concept retrieveByUrl(String type, String namespace, String identifier) throws HttpException;

}
