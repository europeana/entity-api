package eu.europeana.entity.web.service.impl;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import eu.europeana.entity.mongo.service.PersistentEntityService;
import eu.europeana.entity.solr.service.SolrEntityService;

public abstract class BaseEntityServiceImpl {

	@Resource
	PersistentEntityService mongoPersistance;

	@Resource
	SolrEntityService solrService;
	
	Logger logger = LogManager.getLogger(getClass());


	protected PersistentEntityService getMongoPersistence() {
		return mongoPersistance;
	}

	public void setMongoPersistance(PersistentEntityService mongoPersistance) {
		this.mongoPersistance = mongoPersistance;
	}
	
	public Logger getLogger() {
		return logger;
	}

	public void setLogger(Logger logger) {
		this.logger = logger;
	}

	public PersistentEntityService getMongoPersistance() {
		return mongoPersistance;
	}

	public SolrEntityService getSolrService() {
		return solrService;
	}

	public void setSolrService(SolrEntityService solrService) {
		this.solrService = solrService;
	}

}
