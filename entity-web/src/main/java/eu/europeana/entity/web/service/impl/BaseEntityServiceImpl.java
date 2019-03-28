package eu.europeana.entity.web.service.impl;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import eu.europeana.entity.mongo.service.PersistentEntityService;

public abstract class BaseEntityServiceImpl {

	@Resource
	PersistentEntityService mongoPersistance;

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

}
