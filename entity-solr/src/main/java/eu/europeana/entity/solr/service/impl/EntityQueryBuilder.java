package eu.europeana.entity.solr.service.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.common.params.CommonParams;
import org.apache.solr.common.params.HighlightParams;
import org.apache.solr.common.params.SimpleParams;
import org.springframework.util.MultiValueMap;

import eu.europeana.api.commons.definitions.search.Query;
import eu.europeana.api.commons.definitions.search.impl.QueryImpl;
import eu.europeana.api.commons.definitions.vocabulary.CommonApiConstants;
import eu.europeana.api.commons.search.util.QueryBuilder;
import eu.europeana.entity.definitions.model.search.SearchProfiles;
import eu.europeana.entity.definitions.model.vocabulary.ConceptSolrFields;
import eu.europeana.entity.definitions.model.vocabulary.EntitySolrFields;
import eu.europeana.entity.definitions.model.vocabulary.EntityTypes;
import eu.europeana.entity.definitions.model.vocabulary.OrganizationSolrFields;
import eu.europeana.entity.definitions.model.vocabulary.WebEntityConstants;
import eu.europeana.entity.solr.model.vocabulary.SuggestionFields;
import eu.europeana.entity.solr.service.SolrEntityService;

public class EntityQueryBuilder extends QueryBuilder {

    public static final String DESC = "desc";
    public static final String OR = " " + SimpleParams.OR_OPERATOR + " ";
    public static final String AND = " " + SimpleParams.AND_OPERATOR + " ";

    public SolrQuery toSolrQuery(Query searchQuery, String searchHandler, List<EntityTypes> entityTypes, String scope) {
	SolrQuery solrQuery = super.toSolrQuery(searchQuery, searchHandler);
	addQueryFilterParam(solrQuery, entityTypes, scope, false);
	//with solr 7 default param is not available in index configurations anymore
	solrQuery.set(PARAM_QUERY_OPERATOR, SimpleParams.AND_OPERATOR);
	return solrQuery;
    }

    private void addQueryFilterParam(SolrQuery query, List<EntityTypes> entityTypes, String scope, boolean suggest) {

	if (SolrEntityService.HANDLER_SUGGEST.equals(query.getRequestHandler()))
	    addFiltersToSuggestQuery(query, entityTypes, scope);
	else if (SolrEntityService.HANDLER_SELECT.equals(query.getRequestHandler()))
	    addFiltersToSearchQuery(query, entityTypes, scope, suggest);
    }

    private boolean hasScopeEuropeana(String scope) {
	return WebEntityConstants.PARAM_SCOPE_EUROPEANA.equalsIgnoreCase(scope);
    }

    private void addFiltersToSearchQuery(SolrQuery query, List<EntityTypes> entityTypes, String scope,
	    boolean suggest) {

	if (hasScopeEuropeana(scope))
	    query.addFilterQuery("suggest_filters:" + SuggestionFields.FILTER_IN_EUROPEANA);

	String typeCondition = buildEntityTypeCondition(entityTypes);
	if (typeCondition != null)
	    query.addFilterQuery("suggest_filters:" + typeCondition);
    }

    private void addFiltersToSuggestQuery(SolrQuery query, List<EntityTypes> entityTypes, String scope) {

	// build entityType filter
	String entityTypeFilter = buildEntityTypeCondition(entityTypes);

	// build scopeFilter
	String scopeFilter = hasScopeEuropeana(scope) ? SuggestionFields.FILTER_IN_EUROPEANA : null;

	// add filters to query
	String filter = null;

	if (entityTypeFilter != null & scopeFilter != null) {
	    // combine filters
	    filter = entityTypeFilter + AND + scopeFilter;
	} else if (entityTypeFilter != null) {
	    filter = entityTypeFilter;
	} else if (scopeFilter != null) {
	    filter = scopeFilter;
	}

	if (filter != null)
	    query.add("suggest.cfq", filter);
    }

    /**
     * This method builds Solr query for suggest by label request method
     *
     * @param text
     * @param entityTypes
     * @param scope
     * @param rows
     * @param snippets
     * @param languages
     * @return Solr query
     */
    public SolrQuery buildSuggestByLabelQuery(String text, List<EntityTypes> entityTypes, String scope, int rows,
	    int snippets, List<String> languages) {

	String highlightFields = OrganizationSolrFields.LABEL;
	String query = OrganizationSolrFields.LABEL + ":(" + text + "*)";
	
	return buildSuggestQuery(entityTypes, scope, rows, snippets, query, highlightFields);
    }

    public SolrQuery buildSuggesForLanguageQuery(String text, List<EntityTypes> entityTypes, String scope, int rows,
	    int snippets, List<String> languages) {

	List<String> searchFields = generateLanguageSpecificFields(OrganizationSolrFields.LABEL, languages);
	String highlightFields = String.join(",", searchFields.toArray(new String[0]));
	
	List<String> fieldQueries = generateLanguageSpecificQueries(text, searchFields);
	String query = "(" +  String.join(OR, fieldQueries.toArray(new String[0])) + ")";
	
	return buildSuggestQuery(entityTypes, scope, rows, snippets, query, highlightFields);
    }

    private List<String> generateLanguageSpecificFields(String fieldPrefix, List<String> languages) {
	List<String> res = new ArrayList<String>(languages.size());
	for (String language : languages) {
	    res.add(fieldPrefix + "." + language); 
	}
	return res;
    }

    private List<String> generateLanguageSpecificQueries(String text, List<String> searchFields) {
	List<String> res = new ArrayList<String>(searchFields.size());
	for (String field : searchFields) {
	    res.add(field + ":(" + text + "*)" ); 
	}
	return res;
    }
    
    private SolrQuery buildSuggestQuery(List<EntityTypes> entityTypes, String scope, int rows, int snippets,
	    String query, String highlightFields) {
	SolrQuery solrQuery = new SolrQuery(query);
	solrQuery.setRequestHandler(SolrEntityService.HANDLER_SELECT);
	addQueryFilterParam(solrQuery, entityTypes, scope, true);

	String[] fields;

	// ?q=(label.de%3AMo* OR
	// label.it%3AMo*)&sort=derived_score+desc&rows=100&fl=payload%2C+id%2C+derived_score&wt=json&indent=true&hl=true&hl.fl=label&hl.q=Moz*&hl.method=unified&hl.tag.pre=%3Cb%3E&&hl.tag.post=%3C/b%3E
	fields = new String[] { OrganizationSolrFields.ID, OrganizationSolrFields.PAYLOAD,
		OrganizationSolrFields.DERIVED_SCORE };
	solrQuery.set(CommonParams.SORT, ConceptSolrFields.DERIVED_SCORE + " " + DESC);
	solrQuery.set(CommonApiConstants.QUERY_PARAM_ROWS,
		Integer.toString(Math.min(rows, Query.DEFAULT_MAX_PAGE_SIZE)));
	solrQuery.set(HighlightParams.HIGHLIGHT, "true");
	solrQuery.set(HighlightParams.FIELDS, highlightFields);
	solrQuery.set(HighlightParams.METHOD, "unified");
	solrQuery.set(HighlightParams.TAG_PRE, WebEntityConstants.HIGHLIGHT_START_MARKER);
	solrQuery.set(HighlightParams.TAG_POST, WebEntityConstants.HIGHLIGHT_END_MARKER);
	if (snippets > 0) {
	    solrQuery.set(HighlightParams.SNIPPETS, snippets);
	}
	solrQuery.set(HighlightParams.Q, query);
	solrQuery.setFields(fields);
	
	//with solr 7 default param is not available in index configurations anymore
	solrQuery.set(PARAM_QUERY_OPERATOR, SimpleParams.AND_OPERATOR);

	return solrQuery;
    }
  

    /**
     * This method selects supported entity types, which should be included in the
     * response
     * 
     * @param entityTypes
     * @return Solr query filter for entity types
     */
    private String buildEntityTypeCondition(List<EntityTypes> entityTypes) {
	if (entityTypes == null || entityTypes.isEmpty()) {
	    return null;
	}

	if (entityTypes.size() == 1)
	    return entityTypes.get(0).getInternalType();

	String disjunction = String.join(OR, toStringArray(entityTypes));
	return "(" + disjunction + ")";

    }

    /**
     * Convert a list of EntityTypes into an array of strings
     * 
     * @param entityTypes array of EntityTypes
     * @return array of strings
     */
    static String[] toStringArray(List<EntityTypes> entityTypes) {
	String[] internalEntityTypes = new String[entityTypes.size()];
	for (int i = 0; i < entityTypes.size(); i++) {
	    internalEntityTypes[i] = entityTypes.get(i).getInternalType();
	}
	return internalEntityTypes;
    }

    protected void verifySortField(String fieldName) {
	// TODO: implement when field list is specified
    }

    /**
     * Search concepts that match the ConceptScheme parse parameters from is defined
     * by URL
     * 
     * @param uriString
     * @param sort
     * @return solr query
     * @throws UnsupportedEncodingException
     */
    public Query buildParameterSearchQuery(MultiValueMap<String, String> parameters, String sort)
	    throws UnsupportedEncodingException {
	Query searchQuery = null;

	String queryString = parameters.getFirst(CommonApiConstants.QUERY_PARAM_QUERY);
	List<String> qfList = parameters.get(CommonApiConstants.QUERY_PARAM_QF);
	String pageSize = parameters.getFirst(CommonApiConstants.QUERY_PARAM_PAGE_SIZE);
	String page = parameters.getFirst(CommonApiConstants.QUERY_PARAM_PAGE);
//		String type = parameters.getFirst(WebEntityConstants.QUERY_PARAM_TYPE);
//		String scope = parameters.getFirst(WebEntityConstants.QUERY_PARAM_SCOPE);

	// process fl
	String[] retFields = toArray(EntitySolrFields.ID);

	if (retFields != null) {
	    retFields = buildCustomSelectionFields(retFields);
	}

	// process sort param
	String[] sortCriteria = toArray(sort);

	// process query
	if (StringUtils.isNotBlank(queryString))
	    queryString = URLDecoder.decode(queryString, StandardCharsets.UTF_8.name());

	// process qf
	String[] qf = null;
	if (qfList != null && qfList.size() > 0) {
	    qf = qfList.toArray(new String[qfList.size()]);
	}

	// process pageSize
	if (StringUtils.isEmpty(page))
	    page = "" + Query.DEFAULT_PAGE;

	// process pageSize
	if (StringUtils.isEmpty(pageSize))
	    pageSize = "" + Query.DEFAULT_PAGE_SIZE;

	searchQuery = buildSearchQuery(queryString, qf, null, sortCriteria, Integer.valueOf(page),
		Integer.valueOf(pageSize), null, retFields);

	return searchQuery;
    }

    /**
     * @param queryString
     * @param qf
     * @param facets
     * @param sort
     * @param page
     * @param pageSize
     * @param profile
     * @param retFields
     * @return
     */
    public Query buildSearchQuery(String queryString, String[] qf, String[] facets, String[] sort, int page,
	    int pageSize, SearchProfiles profile, String[] retFields) {

	QueryBuilder builder = new QueryBuilder();
	int maxPageSize = Query.DEFAULT_MAX_PAGE_SIZE;
	String profileName = null;
	if (profile != null) {
	    profileName = profile.name();
	}

	if (retFields != null) {
	    retFields = buildCustomSelectionFields(retFields);
	}

	Query query = builder.buildSearchQuery(queryString, qf, facets, retFields, sort, page, pageSize, maxPageSize,
		profileName);
	return query;
    }

    /**
     * @param queryString
     * @param filters
     * @param rows
     * @return
     */
    public Query buildSearchQuery(String queryString, String[] filters, int pageSize) {

	Query searchQuery = new QueryImpl();
	searchQuery.setQuery(queryString);
	searchQuery.setPageSize(Math.min(pageSize, Query.DEFAULT_MAX_PAGE_SIZE));
	searchQuery.setFilters(filters);

	return searchQuery;
    }

    /**
     * This method splits the list of values provided as concatenated string to the
     * corresponding array representation
     * 
     * @param concatenatedStrings
     * @return
     */
    public String[] toArray(String concatenatedStrings) {
	if (StringUtils.isEmpty(concatenatedStrings))
	    return null;
	String[] array = StringUtils.splitByWholeSeparator(concatenatedStrings, ",");
	return StringUtils.stripAll(array);
    }

    /**
     * This method enriches provided custom selection fields by required fields if
     * they are not already provided in input array.
     * 
     * @param inputArray
     * @return enriched array
     */
    public String[] buildCustomSelectionFields(String[] inputFields) {
	List<String> fieldList = new ArrayList<String>();
	Collections.addAll(fieldList, inputFields);
	// add mandatory fields
	if (!fieldList.contains(ConceptSolrFields.ID))
	    fieldList.add(ConceptSolrFields.ID);
	if (!fieldList.contains(ConceptSolrFields.TYPE))
	    fieldList.add(ConceptSolrFields.TYPE);

	return fieldList.toArray(new String[fieldList.size()]);
    }

    public Query buildEntitiesInSchemeQuery(String conceptSchemeId) {
	String inSchemeQuery = EntitySolrFields.IN_SCHEME + ":\"" + conceptSchemeId + "\"";
	Query existingQuery = buildSearchQuery(inSchemeQuery, null, 0);
	// TODO: update logic for buildSearchQuery to handle internal and external max
	// page sizes
	int INTERNAL_MAX_PAGE_SIZE = 1000;
	existingQuery.setPageSize(INTERNAL_MAX_PAGE_SIZE);
	return existingQuery;
    }
}
