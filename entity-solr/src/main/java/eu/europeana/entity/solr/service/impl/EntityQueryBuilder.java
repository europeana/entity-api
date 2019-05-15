package eu.europeana.entity.solr.service.impl;

import java.util.List;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.common.params.CommonParams;
import org.apache.solr.common.params.HighlightParams;
import org.apache.solr.common.params.SimpleParams;

import eu.europeana.api.commons.definitions.search.Query;
import eu.europeana.api.commons.definitions.vocabulary.CommonApiConstants;
import eu.europeana.api.commons.search.util.QueryBuilder;
import eu.europeana.entity.definitions.model.vocabulary.ConceptSolrFields;
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

	private void addFiltersToSearchQuery(SolrQuery query, List<EntityTypes> entityTypes, String scope, boolean suggest) {

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
	 * @return Solr query
	 */
	public SolrQuery buildSuggestByLabelQuery(String text, List<EntityTypes> entityTypes, String scope, int rows,
			int snippets) {

		String query = OrganizationSolrFields.LABEL + ":(" + text + "*)";
		SolrQuery solrQuery = new SolrQuery(query);
		solrQuery.setRequestHandler(SolrEntityService.HANDLER_SELECT);
		addQueryFilterParam(solrQuery, entityTypes, scope, true);

		String[] fields;

		// ?q=label%3AMoz*&sort=derived_score+desc&rows=100&fl=payload%2C+id%2C+derived_score&wt=json&indent=true&hl=true&hl.fl=label&hl.q=Moz*&hl.method=unified&hl.tag.pre=%3Cb%3E&&hl.tag.post=%3C/b%3E
		fields = new String[] { OrganizationSolrFields.ID, OrganizationSolrFields.PAYLOAD,
				OrganizationSolrFields.DERIVED_SCORE };
		solrQuery.set(CommonParams.SORT, ConceptSolrFields.DERIVED_SCORE + " " + DESC);
		solrQuery.set(CommonApiConstants.QUERY_PARAM_ROWS,
				Integer.toString(Math.min(rows, Query.DEFAULT_MAX_PAGE_SIZE)));
		solrQuery.set(HighlightParams.HIGHLIGHT, "true");
		solrQuery.set(HighlightParams.FIELDS, OrganizationSolrFields.LABEL);
		solrQuery.set(HighlightParams.METHOD, "unified");
		solrQuery.set(HighlightParams.TAG_PRE, WebEntityConstants.HIGHLIGHT_START_MARKER);
		solrQuery.set(HighlightParams.TAG_POST, WebEntityConstants.HIGHLIGHT_END_MARKER);
		if (snippets > 0) {
			solrQuery.set(HighlightParams.SNIPPETS, snippets);
		}
		solrQuery.set(HighlightParams.Q, query);
		solrQuery.setFields(fields);

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
		if (entityTypes.isEmpty()) {
			return null;
		}

		if (entityTypes.size() == 1)
			return entityTypes.get(0).getInternalType();

		String disjunction = String.join(OR, toStringArray(entityTypes));
		return "(" + disjunction + ")";

	}

	/**
	 * Convert a list of EntityTypes into an array of strings
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

}
