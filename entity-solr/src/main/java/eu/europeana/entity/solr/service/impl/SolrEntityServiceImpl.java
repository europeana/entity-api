package eu.europeana.entity.solr.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;

import eu.europeana.entity.definitions.model.Concept;
import eu.europeana.entity.definitions.model.search.Query;
import eu.europeana.entity.definitions.model.search.result.ResultSet;
import eu.europeana.entity.definitions.model.vocabulary.SkosConceptSolrFields;
import eu.europeana.entity.solr.exception.EntityServiceException;
import eu.europeana.entity.solr.model.SolrConceptImpl;
import eu.europeana.entity.solr.service.SolrEntityService;
import eu.europeana.entity.web.model.view.ConceptView;

public class SolrEntityServiceImpl extends SolrEntityUtils implements SolrEntityService {

	@Resource
	SolrServer solrServer;
	private final Logger log = Logger.getLogger(getClass());
	

	public void setSolrServer(SolrServer solrServer) {
		this.solrServer = solrServer;
	}

	public Concept searchById(String entityId) throws EntityServiceException{
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Concept searchByUrl(String entityId) throws EntityServiceException {
		
		getLogger().debug("search entity by id: " + entityId);

		/**
		 * Construct a SolrQuery
		 */
		SolrQuery query = new SolrQuery();
		query.setQuery(SkosConceptSolrFields.ENTITY_ID + ":\"" + entityId + "\"");
		
		getLogger().trace("query: " + query);

		List<? extends Concept> beans = null;
		
		/**
		 * Query the server
		 */
		try {
			QueryResponse rsp = solrServer.query(query);
			
			beans = rsp.getBeans(SolrConceptImpl.class);
			//rs = buildResultSet(rsp);
		} catch (SolrServerException e) {
			throw new EntityServiceException(
					"Unexpected exception occured when searching entities for id: " + entityId, e);
		}

		if (beans == null || beans.size() == 0)
			return null;
		if (beans.size() != 1)
			throw new EntityServiceException("Expected one result but found: " + beans.size());

		return beans.get(0);
	}

	@Override
	public ResultSet<? extends ConceptView> search(
			Query searchQuery) throws EntityServiceException {
		ResultSet<? extends ConceptView> res = null;
		SolrQuery query = toSolrQuery(searchQuery);

		try {
			getLogger().info("search obj: " + searchQuery);
			QueryResponse rsp = solrServer.query(query);
			res = buildResultSet(rsp);
			getLogger().debug("search obj res size: " + res.getResultSize());
		} catch (SolrServerException e) {
			throw new EntityServiceException(
					"Unexpected exception occured when searching annotations for solrAnnotation: "
							+ searchQuery.toString(),
					e);
		}

		return res;	
	}

	public void cleanUpAll() {
		// TODO Auto-generated method stub
		//pay attention, this method should be run only on development environment 		
	}

	public Logger getLogger() {
		return log;
	}

	@Override
	public ResultSet<? extends ConceptView> suggest(Query searchQuery, String language) throws EntityServiceException {
		
		ResultSet<? extends ConceptView> res = null;
		SolrQuery query = toSolrQuery(searchQuery);
		String handler = "/suggestConcept/";
		if(language != null)
			handler += language;
		
		query.setRequestHandler(handler);
		
		try {
			getLogger().info("suggest entity: " + searchQuery);
			QueryResponse rsp = solrServer.query(query);
			res = buildResultSet(rsp);
			getLogger().debug("search obj res size: " + res.getResultSize());
		} catch (SolrServerException e) {
			throw new EntityServiceException(
					"Unexpected exception occured when searching annotations for solrAnnotation: "
							+ searchQuery.toString(),
					e);
		}

		return res;	
	}



}
