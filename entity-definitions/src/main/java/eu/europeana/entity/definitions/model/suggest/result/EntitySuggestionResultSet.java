/*
 * Copyright 2007-2012 The Europeana Foundation
 *
 *  Licenced under the EUPL, Version 1.1 (the "Licence") and subsequent versions as approved
 *  by the European Commission;
 *  You may not use this work except in compliance with the Licence.
 * 
 *  You may obtain a copy of the Licence at:
 *  http://joinup.ec.europa.eu/software/page/eupl
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under
 *  the Licence is distributed on an "AS IS" basis, without warranties or conditions of
 *  any kind, either express or implied.
 *  See the Licence for the specific language governing permissions and limitations under
 *  the Licence.
 */

package eu.europeana.entity.definitions.model.suggest.result;

import java.util.List;
import java.util.Map;

import eu.europeana.entity.definitions.model.search.Query;

/**
 * taken from the corelib-definitions/corelib-search and eliminated explicit solr dependencies
 * @author Sergiu Gordea @ait
 * @author Willem-Jan Boogerd <www.eledge.net/contact>
 */
public class EntitySuggestionResultSet<T> {

	/**
	 * The request query object
	 */
	private Query query;

	/**
	 * The list of result objects using exact match 
	 */
	private List<T> results;
	
	/**
	 * The list of result objects using fuzzy match 
	 */
	private List<T> fuzzyResults;

	/**
	 * The total number of results
	 */
	private long resultSize;

	/**
	 * The list of result objects using exact match 
	 */
	private long fuzzyResultSize;

	
	/**
	 * The time in millisecond how long the search has been taken
	 */
	private long searchTime;

	/**
	 * GETTERS & SETTTERS
	 */

	public List<T> getResults() {
		return results;
	}

	public EntitySuggestionResultSet<T> setResults(List<T> list) {
		this.results = list;
		return this;
	}

	public Query getQuery() {
		return query;
	}

	public EntitySuggestionResultSet<T> setQuery(Query query) {
		this.query = query;
		return this;
	}

	/**
	 * Gets the total number of results
	 * @return
	 */
	public long getResultSize() {
		return resultSize;
	}

	public EntitySuggestionResultSet<T> setResultSize(long resultSize) {
		this.resultSize = resultSize;
		return this;
	}

	public long getSearchTime() {
		return searchTime;
	}

	public EntitySuggestionResultSet<T> setSearchTime(long l) {
		this.searchTime = l;
		return this;
	}

	@Override
	public String toString() {
		return "ResultSet [query=" + query + ", results=" + results
				+ ", facetFields=" + getFuzzyResults() 
				+ ", resultSize=" + getResultSize() 
				+ ", fuzzyResultSize=" + getFuzzyResultSize() 
				+ ", searchTime=" + searchTime
				+ "]";
	}

	public List<T> getFuzzyResults() {
		return fuzzyResults;
	}

	public void setFuzzyResults(List<T> fuzzyResults) {
		this.fuzzyResults = fuzzyResults;
	}

	public long getFuzzyResultSize() {
		return fuzzyResultSize;
	}

	public void setFuzzyResultSize(long fuzzyResultSize) {
		this.fuzzyResultSize = fuzzyResultSize;
	}

}
