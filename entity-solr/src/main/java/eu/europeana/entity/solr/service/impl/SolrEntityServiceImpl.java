package eu.europeana.entity.solr.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.util.SimpleOrderedMap;

import eu.europeana.entity.definitions.model.Concept;
import eu.europeana.entity.definitions.model.Entity;
import eu.europeana.entity.definitions.model.search.Query;
import eu.europeana.entity.definitions.model.search.result.ResultSet;
import eu.europeana.entity.definitions.model.vocabulary.EntityTypes;
import eu.europeana.entity.definitions.model.vocabulary.ConceptSolrFields;
import eu.europeana.entity.solr.exception.EntityRetrievalException;
import eu.europeana.entity.solr.exception.EntitySuggestionException;
import eu.europeana.entity.solr.model.factory.EntityObjectFactory;
import eu.europeana.entity.solr.model.vocabulary.SuggestionFields;
import eu.europeana.entity.solr.service.SolrEntityService;
import eu.europeana.entity.solr.view.EntityPreviewImpl;
import eu.europeana.entity.web.model.view.ConceptView;
import eu.europeana.entity.web.model.view.EntityPreview;

public class SolrEntityServiceImpl extends BaseEntityService implements SolrEntityService {

	@Resource
	SolrServer solrServer;
	SuggestionUtils suggestionHelper = null;

	private final Logger log = Logger.getLogger(getClass());

	public void setSolrServer(SolrServer solrServer) {
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
	public Entity searchByUrl(String type, String entityId) throws EntityRetrievalException {

		getLogger().debug("search entity (type:" +type+" ) by id: " + entityId);

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
					EntityTypes.getByInternalType(type));

			beans = rsp.getBeans(concreteClass);
//			beans = rsp.getBeans(AgentViewAdapter.class);
//			beans = rsp.getBeans(SolrAgentImpl.class);
//			beans = rsp.getBeans(SolrConceptImpl.class);
			// rs = buildResultSet(rsp);
		} catch (SolrServerException e) {
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
	public ResultSet<? extends ConceptView> search(Query searchQuery) throws EntityRetrievalException {
		ResultSet<? extends ConceptView> res = null;
		SolrQuery query = toSolrQuery(searchQuery);

		try {
			getLogger().info("search obj: " + searchQuery);
			QueryResponse rsp = solrServer.query(query);
			res = buildResultSet(rsp);
			getLogger().debug("search obj res size: " + res.getResultSize());
		} catch (SolrServerException e) {
			throw new EntityRetrievalException(
					"Unexpected exception occured when searching annotations for solrAnnotation: "
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
		SolrQuery query = toSolrQuery(searchQuery);
		String handler = "/suggestEntity/";
		if (language != null)
			handler += language;

		query.setRequestHandler(handler);
		
		addQueryFilterParam(query, entityTypes, scope);
		
		

		try {
			getLogger().debug("invoke suggest handler: " + handler);
			getLogger().debug("suggest query: " + query);
			QueryResponse rsp = solrServer.query(query);

			res = buildSuggestionSet(rsp, language, rows, EntityPreviewImpl.class);
			getLogger().debug("search obj res size: " + res.getResultSize());
		} catch (SolrServerException e) {
			throw new EntitySuggestionException(
					"Unexpected exception occured when searching annotations for solrAnnotation: "
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
		String entityTypeCondition = entityTypes.length == 0 ? entityTypes[0].getInternalType() : buildEntityTypeCondition(entityTypes);
		String filter = null;
		
		String scopeFilter = null;
		if(SuggestionFields.PARAM_EUROPEANA.equals(scope)) { 
			scopeFilter = SuggestionFields.FILTER_IN_EUROPEANA;
		}
		
		if( !isSpecificEntityType && !isScope)
			return;
		if(isSpecificEntityType && !isScope)
			filter = entityTypeCondition;
		else if(!isSpecificEntityType && scopeFilter != null) 
			filter = scopeFilter;
		else if(isSpecificEntityType && isScope)
			filter = entityTypeCondition + " AND " + scopeFilter;
		
		if(filter != null)
			query.add("suggest.cfq", filter);
		
	}

	

	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected <T extends EntityPreview> ResultSet<T> buildSuggestionSet(QueryResponse rsp, String language, int rows,
			Class<T> entityPreviewClass) throws EntitySuggestionException {

		ResultSet<T> resultSet = new ResultSet<>();
		resultSet.setLanguage(language);
		
		Map<String, Object> suggest = (Map<String, Object>) rsp.getResponse().get(SuggestionFields.SUGGEST);
		
		SimpleOrderedMap<?> exactMatchGroup = (SimpleOrderedMap) suggest
				.get(SuggestionFields.PREFIX_SUGGEST_ENTITY + language);

		List<SimpleOrderedMap<?>> exactSuggestions = null;
		
		if((SimpleOrderedMap) exactMatchGroup.getVal(0) != null){
			exactSuggestions = (List<SimpleOrderedMap<?>>) ((SimpleOrderedMap) exactMatchGroup
				.getVal(0)).get(SuggestionFields.SUGGESTIONS);
		}
				
		List<T> beans = extractBeans(exactSuggestions, rows, language);

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
		
		payload = (String) entry.get(SuggestionFields.PAYLOAD);
		preview = (T) getSuggestionHelper().parsePayload(payload);
		term = (String) entry.get(SuggestionFields.TERM);
		preview.setSearchedTerm(term);

		return preview;
	}

	
	public SuggestionUtils getSuggestionHelper() {
		if (suggestionHelper == null)
			suggestionHelper = new SuggestionUtils();
		return suggestionHelper;
	}

	@Override
	public ResultSet<? extends EntityPreview> suggest(Query searchQuery, String language, String commaSepEntityTypes, String scope,
			int rows) throws EntitySuggestionException {
		return suggest(searchQuery, language, EntityTypes.getEntityTypesFromString(commaSepEntityTypes), scope, rows);
	}

}
