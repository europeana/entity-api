package eu.europeana.entity.solr.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.beans.DocumentObjectBinder;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;

import eu.europeana.api.commons.definitions.search.ResultSet;
import eu.europeana.entity.definitions.model.Entity;
import eu.europeana.entity.definitions.model.vocabulary.ConceptSolrFields;
import eu.europeana.entity.solr.model.factory.EntityObjectFactory;

public abstract class BaseEntityService{
	
	private final Logger log = Logger.getLogger(getClass());

	@SuppressWarnings("unchecked")
	protected <T extends Entity> ResultSet<T> buildResultSet(QueryResponse rsp) {
		
		ResultSet<T> resultSet = new ResultSet<>();
		
		DocumentObjectBinder binder = new DocumentObjectBinder();
		SolrDocumentList docList = rsp. getResults();
		String type;
		T entity;
		Class<T> entityClass;
		List<T> beans = new ArrayList<T>();
		
		for (SolrDocument doc : docList) {
			type = (String) doc.get(ConceptSolrFields.INTERNAL_TYPE);
			entityClass = (Class<T>) EntityObjectFactory.getInstance().getClassForType(type);
		    
			entity = (T) binder.getBean(entityClass, doc);
			beans.add(entity);
		}
		
		resultSet.setResults(beans);
		resultSet.setResultSize(rsp.getResults().getNumFound());

		return resultSet;
	}

	
	public Logger getLog() {
		return log;
	}

}
