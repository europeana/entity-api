package eu.europeana.entity.solr.service.impl;

import java.util.ArrayList;
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
import eu.europeana.entity.definitions.model.search.Query;
import eu.europeana.entity.definitions.model.search.result.ResultSet;
import eu.europeana.entity.definitions.model.vocabulary.EntityTypes;
import eu.europeana.entity.definitions.model.vocabulary.SkosConceptSolrFields;
import eu.europeana.entity.solr.exception.EntityRetrievalException;
import eu.europeana.entity.solr.exception.EntitySuggestionException;
import eu.europeana.entity.solr.model.factory.ConceptObjectFactory;
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

	public Concept searchById(String entityId) throws EntityRetrievalException {
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
	public Concept searchByUrl(String type, String entityId) throws EntityRetrievalException {

		getLogger().debug("search entity (type:" +type+" ) by id: " + entityId);

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

			Class<? extends Concept> concreteClass = null;
			//String entityType = getTypeFromEntityId(entityId);
			concreteClass = ConceptObjectFactory.getInstance().getClassForType(
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
	public ResultSet<? extends EntityPreview> suggest(Query searchQuery, String language, int rows)
			throws EntitySuggestionException {

		ResultSet<? extends EntityPreview> res = null;
		SolrQuery query = toSolrQuery(searchQuery);
		String handler = "/suggestEntity/";
		if (language != null)
			handler += language;

		query.setRequestHandler(handler);

		try {
			getLogger().info("suggest entity: " + searchQuery);
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

	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected <T extends EntityPreview> ResultSet<T> buildSuggestionSet(QueryResponse rsp, String language, int rows,
			Class<T> entityPreviewClass) throws EntitySuggestionException {

		ResultSet<T> resultSet = new ResultSet<>();

		Map<String, Object> suggest = (Map<String, Object>) rsp.getResponse().get(SuggestionFields.SUGGEST);

		SimpleOrderedMap<?> exactMatchGroup = (SimpleOrderedMap) suggest
				.get(SuggestionFields.PREFIX_SUGGEST_ENTITY + language);
//		SimpleOrderedMap<?> fuzzyMatchGroup = (SimpleOrderedMap) suggest
//				.get(SuggestionFields.PREFIX_SUGGEST_ENTITY + language + SuggestionFields.SUFFIX_FUZZY);

		List<SimpleOrderedMap<?>> exactSuggestions = null;
//		List<SimpleOrderedMap<?>> fuzzySuggestions = null;
		
		if((SimpleOrderedMap) exactMatchGroup.getVal(0) != null){
			exactSuggestions = (List<SimpleOrderedMap<?>>) ((SimpleOrderedMap) exactMatchGroup
				.getVal(0)).get(SuggestionFields.SUGGESTIONS);
		}
				
//		if((SimpleOrderedMap) fuzzyMatchGroup.getVal(0) != null){
//			fuzzySuggestions = (List<SimpleOrderedMap<?>>) ((SimpleOrderedMap) exactMatchGroup
//					.getVal(0)).get(SuggestionFields.SUGGESTIONS);
//		}		
					
//		List<T> beans = extractBeans(exactSuggestions, fuzzySuggestions, rows, entityPreviewClass);
		List<T> beans = extractBeans(exactSuggestions, null, rows, entityPreviewClass, language);

		resultSet.setResults(beans);
		resultSet.setResultSize(beans.size());

		return resultSet;
	}

	private <T extends EntityPreview> List<T> extractBeans(List<SimpleOrderedMap<?>> suggestions,
			List<SimpleOrderedMap<?>> fuzzySuggestions, int rows, Class<T> entityPreviewClass, String language)
					throws EntitySuggestionException {
		Set<String> ids = new HashSet<String>();
		List<T> beans = new ArrayList<T>();

		// add exact matches to list
		if (suggestions != null) 
			processSuggestionMap(suggestions, ids, beans, rows, entityPreviewClass, language);
		
		// add fuzzy matches to list but avoid dupplications with exact match
		if (fuzzySuggestions != null)
			processSuggestionMap(fuzzySuggestions, ids, beans, rows, entityPreviewClass, language);
		
		return beans;
	}

	private <T extends EntityPreview> void processSuggestionMap(List<SimpleOrderedMap<?>> suggestionMap, Set<String> ids,
			List<T> beans, int rows, Class<T> entityPreviewClass, String language) throws EntitySuggestionException {
		
		T preview;

		for (SimpleOrderedMap<?> entry : suggestionMap) {
			// cut to rows
			if (ids.size() == rows)
				return;

			preview = buildEntityPreview(entry, entityPreviewClass, language);

			if (!ids.contains(preview.getEntityId())) {
				beans.add(preview);
				ids.add(preview.getEntityId());
			} else {
				getLog().debug("Ignored dupplicated entry: " + preview.getEntityId() + "\nterm: " + preview.getPreferredLabel() 
				+ "; " + preview.getEntityId());
			}
		}
	}

	@SuppressWarnings("unchecked")
	private <T extends EntityPreview> T buildEntityPreview(SimpleOrderedMap<?> entry, Class<T> entityPreviewClass, String language)
			throws EntitySuggestionException {
		String term;
		String payload;
		T preview;
		term = (String) entry.get(SuggestionFields.TERM);

		preview = (T) buildEntityPreview(entityPreviewClass, term, language);
		payload = (String) entry.get(SuggestionFields.PAYLOAD);
		getSuggestionHelper().parsePayload(preview, payload);

		return preview;
	}

	private <T extends EntityPreview> EntityPreview buildEntityPreview(Class<T> entityPreviewClass, String term, String language)
			throws EntitySuggestionException {
		try {
			T preview = entityPreviewClass.newInstance();
			preview.setSearchedTerm(term);
			return preview;
		} catch (Exception e) {
			throw new EntitySuggestionException(
					"Cannot instantiate Entity Preview class" + entityPreviewClass.getCanonicalName(), e);
		}
	}

	public SuggestionUtils getSuggestionHelper() {
		if (suggestionHelper == null)
			suggestionHelper = new SuggestionUtils();
		return suggestionHelper;
	}

}
