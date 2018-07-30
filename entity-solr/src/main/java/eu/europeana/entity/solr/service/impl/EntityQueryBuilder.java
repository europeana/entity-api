package eu.europeana.entity.solr.service.impl;

import org.apache.solr.client.solrj.SolrQuery;

import eu.europeana.api.commons.definitions.search.Query;
import eu.europeana.api.commons.search.util.QueryBuilder;
import eu.europeana.entity.definitions.model.vocabulary.EntityTypes;
import eu.europeana.entity.definitions.model.vocabulary.WebEntityConstants;
import eu.europeana.entity.solr.model.vocabulary.SuggestionFields;
import eu.europeana.entity.solr.service.SolrEntityService;

public class EntityQueryBuilder extends QueryBuilder{

	
	public SolrQuery toSolrQuery(Query searchQuery, String searchHandler, EntityTypes[] entityTypes, String scope) {
		SolrQuery solrQuery = super.toSolrQuery(searchQuery, searchHandler);
		addQueryFilterParam(solrQuery, entityTypes, scope);
		return solrQuery;
	}
	
	
	private void addQueryFilterParam(SolrQuery query, EntityTypes[] entityTypes, String scope) {
		
		if(SolrEntityService.HANDLER_SUGGEST.equals(query.getRequestHandler()))
			addFiltersToSuggestQuery(query, entityTypes, scope);
		else if(SolrEntityService.HANDLER_SELECT.equals(query.getRequestHandler()))
			addFiltersToSearchQuery(query, entityTypes, scope);
	}

	private boolean hasScopeEuropeana(String scope) {
		return WebEntityConstants.PARAM_SCOPE_EUROPEANA.equalsIgnoreCase(scope);
	}
	
	private void addFiltersToSearchQuery(SolrQuery query, EntityTypes[] entityTypes, String scope) {
		if(hasScopeEuropeana(scope)) 
			query.addFilterQuery("suggest_filters:"+ SuggestionFields.FILTER_IN_EUROPEANA);
		
		String typeCondition = buildEntityTypeCondition(entityTypes);
		if(typeCondition != null)
			query.addFilterQuery("suggest_filters:"+ typeCondition);
		
	}

	private void addFiltersToSuggestQuery(SolrQuery query, EntityTypes[] entityTypes, String scope) {
		//build entityType filter
		String entityTypeFilter = buildEntityTypeCondition(entityTypes);
		
		//build scopeFilter
		String scopeFilter = hasScopeEuropeana(scope)? SuggestionFields.FILTER_IN_EUROPEANA : null;
				
		//add filters to query
		String filter = null;
		if(entityTypeFilter == null && scopeFilter == null)
			return;
		//append entity type filter
		if(entityTypeFilter != null)
			filter = entityTypeFilter;
		
		//append scope filter
		if(scopeFilter != null){
			filter = (filter == null)? scopeFilter: " AND " + scopeFilter; 		
		} 
		
		if(filter != null)
			query.add("suggest.cfq", filter);
	}

	private String buildEntityTypeCondition(EntityTypes[] entityTypes) {
		if(entityTypes == null || entityTypes.length == 0 || EntityTypes.arrayHasValue(entityTypes, EntityTypes.All))
			return null;
		
		if(entityTypes.length == 1)
				return entityTypes[0].getInternalType();
		
		String disjunction = String.join(" or ", EntityTypes.toStringArray(entityTypes));
		return "(" + disjunction + ")";
		
	}

}
