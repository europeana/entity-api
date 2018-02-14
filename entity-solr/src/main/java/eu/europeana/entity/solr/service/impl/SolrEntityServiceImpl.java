package eu.europeana.entity.solr.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.util.SimpleOrderedMap;

import eu.europeana.api.commons.definitions.search.Query;
import eu.europeana.api.commons.definitions.search.ResultSet;
import eu.europeana.api.commons.search.util.QueryBuilder;
import eu.europeana.entity.definitions.exceptions.UnsupportedEntityTypeException;
import eu.europeana.entity.definitions.model.Entity;
import eu.europeana.entity.definitions.model.vocabulary.ConceptSolrFields;
import eu.europeana.entity.definitions.model.vocabulary.EntityTypes;
import eu.europeana.entity.solr.exception.EntityRetrievalException;
import eu.europeana.entity.solr.exception.EntityRuntimeException;
import eu.europeana.entity.solr.exception.EntitySuggestionException;
import eu.europeana.entity.solr.model.factory.EntityObjectFactory;
import eu.europeana.entity.solr.model.vocabulary.SuggestionFields;
import eu.europeana.entity.solr.service.SolrEntityService;
import eu.europeana.entity.web.model.view.EntityPreview;


public class SolrEntityServiceImpl extends BaseEntityService implements SolrEntityService {

	@Resource
	SolrClient solrServer;
	SuggestionUtils suggestionHelper = null;

	private final Logger log = Logger.getLogger(getClass());

	public void setSolrServer(SolrClient solrServer) {
		this.solrServer = solrServer;
	}

	public Entity searchById(String entityId) throws EntityRetrievalException {
		return null;
	}

	/**
	 * replace it with the usage of internal type
	 * @deprecated
	 * @param entityId
	 * @return
	 */
	public String getTypeFromEntityId(String entityId) {
		
		String res = "";
		
		int ENTITY_TYPE_POS = 3;
		res = entityId.replace("/", "//").split("//")[ENTITY_TYPE_POS];
		res = Character.toUpperCase(res.charAt(0)) + res.substring(1);
		
		return res;
	}
	
	
	@Override
	public Entity searchByUrl(String type, String entityId) throws EntityRetrievalException, UnsupportedEntityTypeException {

		getLogger().debug("search entity (type:" +type+" ) by id: " + entityId);

		EntityTypes entityType = EntityTypes.getByInternalType(type);
		
		/**
		 * Construct a SolrQuery
		 */
		SolrQuery query = new SolrQuery();
		query.setQuery(ConceptSolrFields.ID + ":\"" + entityId + "\"");

		getLogger().trace("query: " + query);

		List<? extends Entity> beans = null;

		/**
		 * Query the server
		 */
		try {
			QueryResponse rsp = solrServer.query(query);

			Class<? extends Entity> concreteClass = null;
			//String entityType = getTypeFromEntityId(entityId);
			concreteClass = EntityObjectFactory.getInstance().getClassForType(
					entityType);

			beans = rsp.getBeans(concreteClass);
//			beans = rsp.getBeans(AgentViewAdapter.class);
//			beans = rsp.getBeans(SolrAgentImpl.class);
//			beans = rsp.getBeans(SolrConceptImpl.class);
			// rs = buildResultSet(rsp);
		} catch (Exception e) {
			throw new EntityRetrievalException(
					"Unexpected exception occured when searching entities for id: " + entityId, e);
		}

		if (beans == null || beans.size() == 0)
			return null;
		if (beans.size() != 1)
			throw new EntityRetrievalException("Expected one result but found: " + beans.size());

		return beans.get(0);
	}

	@Override
	public ResultSet<? extends Entity> search(Query searchQuery, String[] outLanguage,
			EntityTypes[] entityTypes, String scope) throws EntityRetrievalException {
		
		ResultSet<? extends Entity> res = null;
		
		SolrQuery query = (new QueryBuilder()).toSolrQuery(searchQuery);
		addQueryFilterParam(query, entityTypes, scope);	

		String handler = "/select";
		query.setRequestHandler(handler);

		try {
			getLogger().info("search obj: " + searchQuery);
			QueryResponse rsp = solrServer.query(query);
			res = buildResultSet(rsp);
			getLogger().debug("search obj res size: " + res.getResultSize());
		} catch (Exception e) {
			throw new EntityRetrievalException(
					"Unexpected exception occured when searching entities: "
							+ searchQuery.toString(),
					e);
		} 

		return res;
	}

	public void cleanUpAll() {
		// TODO Auto-generated method stub
		// pay attention, this method should be run only on development
		// environment
	}

	public Logger getLogger() {
		return log;
	}

	@Override
	public ResultSet<? extends EntityPreview> suggest(Query searchQuery, String language, EntityTypes[] entityTypes, String scope, int rows)
			throws EntitySuggestionException {

		ResultSet<? extends EntityPreview> res = null;
		SolrQuery query = new QueryBuilder().toSolrQuery(searchQuery);
		String handler = "/suggestEntity";
		query.setRequestHandler(handler);
		
		addQueryFilterParam(query, entityTypes, scope);	

		try {
			getLogger().debug("invoke suggest handler: " + handler);
			getLogger().debug("suggest query: " + query);
			QueryResponse rsp = solrServer.query(query);

			res = buildSuggestionSet(rsp, language, rows);
			getLogger().debug("search obj res size: " + res.getResultSize());
		} catch (Exception e) {
			throw new EntitySuggestionException(
					"Unexpected exception occured when searching entities: "
							+ searchQuery.toString(),
					e);
		}
		return res;
	}
	
	private String buildEntityTypeCondition(EntityTypes[] entityTypes) {
		String disjunction = String.join(" or ", EntityTypes.toStringArray(entityTypes));
		return "(" + disjunction + ")";
	}

	private void addQueryFilterParam(SolrQuery query, EntityTypes[] entityTypes, String scope) {
		
		Boolean isScope = scope != null && !scope.equals("");
		Boolean isSpecificEntityType = entityTypes != null && entityTypes.length > 0 && !EntityTypes.arrayHasValue(entityTypes, EntityTypes.All);
		//build entityType filter
		String entityTypeCondition = null;
		if(isSpecificEntityType)
			entityTypeCondition = buildEntityTypeCondition(entityTypes);
		
		//build scopeFilter
		String scopeFilter = null;
		if(SuggestionFields.PARAM_EUROPEANA.equals(scope)) { 
			scopeFilter = SuggestionFields.FILTER_IN_EUROPEANA;
		}
		
		//add filters to query
		String filter = null;
		if( !isSpecificEntityType && !isScope)
			return;
		if(isSpecificEntityType && !isScope){
			filter = entityTypeCondition;
		}else if(!isSpecificEntityType && scopeFilter != null) 
			filter = scopeFilter;
		else if(isSpecificEntityType && isScope)
			filter = entityTypeCondition + " AND " + scopeFilter;
		
		if(filter != null)
			query.add("suggest.cfq", filter);
		
	}

	

	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected <T extends EntityPreview> ResultSet<T> buildSuggestionSet(QueryResponse rsp, String language, int rows) throws EntitySuggestionException {

		ResultSet<T> resultSet = new ResultSet<>();
//		resultSet.setLanguage(language);
		
		Map<String, Object> suggest = (Map<String, Object>) rsp.getResponse().get(SuggestionFields.SUGGEST);
		
		SimpleOrderedMap<?> suggestionsMap = (SimpleOrderedMap) suggest
				.get(SuggestionFields.PREFIX_SUGGEST_ENTITY);

		List<SimpleOrderedMap<?>> suggestions = null;
		
		if((SimpleOrderedMap) suggestionsMap.getVal(0) != null){
			suggestions = (List<SimpleOrderedMap<?>>) ((SimpleOrderedMap) suggestionsMap
				.getVal(0)).get(SuggestionFields.SUGGESTIONS);
		}
				
		List<T> beans = extractBeans(suggestions, rows, language);

		resultSet.setResults(beans);
		resultSet.setResultSize(beans.size());

		return resultSet;
	}

	private <T extends EntityPreview> List<T> extractBeans(List<SimpleOrderedMap<?>> suggestions, int rows, String language)
					throws EntitySuggestionException {
//		Set<String> ids = new HashSet<String>();
		List<T> beans = new ArrayList<T>();

		// add exact matches to list
		if (suggestions != null) 
			processSuggestionMap(suggestions, beans, rows, language);
		
		// add fuzzy matches to list but avoid dupplications with exact match
//		if (fuzzySuggestions != null)
//			processSuggestionMap(fuzzySuggestions, ids, beans, rows, entityPreviewClass, language);
		
		return beans;
	}

	private <T extends EntityPreview> void processSuggestionMap(List<SimpleOrderedMap<?>> suggestionMap, 
			List<T> beans, int rows, String language) throws EntitySuggestionException {
		
		T preview;

		for (SimpleOrderedMap<?> entry : suggestionMap) {
			// cut to rows
			if (beans.size() == rows)
				return;

			preview = buildEntityPreview(entry, language);
			beans.add(preview);
		}
	}

	@SuppressWarnings("unchecked")
	private <T extends EntityPreview> T buildEntityPreview(SimpleOrderedMap<?> entry, String language)
			throws EntitySuggestionException {
		String term;
		String payload;
		T preview;
		String hightlightStartMarker = "<b>";
		String hightlightEndMarker = "</b>";
		
		term = (String) entry.get(SuggestionFields.TERM);
		if(term == null || !term.contains(hightlightStartMarker))
			throw new EntitySuggestionException("The hightlighting is not present in retrieved term: " + term);
		
		int beginHighlight = term.indexOf(hightlightStartMarker) + 3;
		int endHighlight = term.indexOf(hightlightEndMarker);
		String highlightTerm = term.substring(beginHighlight, endHighlight);
		
		payload = (String) entry.get(SuggestionFields.PAYLOAD);
		preview = (T) getSuggestionHelper().parsePayload(payload, language, highlightTerm);
		
		preview.setSearchedTerm(term);

		return preview;
	}

	
	public SuggestionUtils getSuggestionHelper() {
		if (suggestionHelper == null)
			suggestionHelper = new SuggestionUtils();
		return suggestionHelper;
	}


//	@Override
//	public String searchBySameAsUri(String uri) throws EntityRetrievalException {
//
//		getLogger().debug("search entity by sameAs uri: " + uri);
//
//		/**
//		 * Construct a SolrQuery
//		 */
//		SolrQuery query = new SolrQuery();
//		query.setQuery(ConceptSolrFields.SAME_AS + ":\"" + uri + "\"");
//		query.addField(ConceptSolrFields.ID);
//		
//		try {
//			QueryResponse rsp = solrServer.query(query);
//			SolrDocumentList docs = rsp.getResults();
//			
//			if(docs.getNumFound() == 0)
//				return null;
//			
//			if(docs.getNumFound() == 1)
//				return docs.get(0).getFieldValue(ConceptSolrFields.ID).toString();
//			
//			else if(docs.getNumFound() > 1)
//				//TODO: change to runtime exception
//				throw new EntityRetrievalException("Too many solr entries found for sameAs uri: " + uri 
//				 + ". Expected 0..1, but found " + docs.getNumFound());
//			
//		} catch (SolrServerException e) {
//			//TODO: change to runtime exception
//			throw new EntityRetrievalException(
//					"Unexpected exception occured when searching Solr entities. ", e);
//		}
//		
//		return null;
//	}

	@Override
	public String searchByCoref(String uri) throws EntityRetrievalException {

		getLogger().debug("search entity by coref uri: " + uri);

		/**
		 * Construct a SolrQuery
		 */
		SolrQuery query = new SolrQuery();
		query.setQuery(ConceptSolrFields.COREF + ":\"" + uri + "\"");
		query.addField(ConceptSolrFields.ID);
		
		try {
			QueryResponse rsp = solrServer.query(query);
			SolrDocumentList docs = rsp.getResults();
			
			if(docs.getNumFound() == 0)
				return null;
			
			if(docs.getNumFound() == 1)
				return docs.get(0).getFieldValue(ConceptSolrFields.ID).toString();
			
			//TODO: can this return >1 result? should it?
			else if(docs.getNumFound() > 1)
				throw new EntityRetrievalException("Too many solr entries found for coref uri: " + uri 
				 + ". Expected 0..1, but found " + docs.getNumFound());
			
		} catch (Exception e) {
			throw new EntityRuntimeException(
					"Unexpected exception occured when searching Solr entities. ", e);
		}
		
		return null;
	}

	
//	/**
//	 * This method queries Solr server
//	 * 
//	 * @param query
//	 * @param type The internal type e.g. agent, place, concept or time span
//	 * @return entity object
//	 * @throws EntityRetrievalException
//	 */
//	private Entity queryServer(SolrQuery query, String type) throws EntityRetrievalException {
//		getLogger().trace("query: " + query);
//
//		List<? extends Entity> beans = null;
//		
//		/**
//		 * Query the server
//		 */
//		try {
//			QueryResponse rsp = solrServer.query(query);
//
//			Class<? extends Entity> concreteClass = null;
//			concreteClass = EntityObjectFactory.getInstance().getClassForType(
//					EntityTypes.getByInternalType(type));
//
//			beans = rsp.getBeans(concreteClass);
//		} catch (SolrServerException e) {
//			throw new EntityRetrievalException(
//					"Unexpected exception occured when searching Solr entities. ", e);
//		}
//
//		if (beans == null || beans.size() == 0)
//			return null;
//		if (beans.size() != 1)
//			throw new EntityRetrievalException("Expected one result but found: " + beans.size());
//
//		return beans.get(0);
//	}

	
}
