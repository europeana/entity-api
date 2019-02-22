package eu.europeana.entity.solr.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.solr.client.solrj.beans.DocumentObjectBinder;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;

import eu.europeana.api.commons.definitions.search.FacetFieldView;
import eu.europeana.api.commons.definitions.search.ResultSet;
import eu.europeana.api.commons.definitions.search.impl.FacetFieldViewImpl;
import eu.europeana.entity.definitions.exceptions.UnsupportedEntityTypeException;
import eu.europeana.entity.definitions.model.Agent;
import eu.europeana.entity.definitions.model.Entity;
import eu.europeana.entity.definitions.model.Organization;
import eu.europeana.entity.definitions.model.vocabulary.ConceptSolrFields;
import eu.europeana.entity.definitions.model.vocabulary.EntityTypes;
import eu.europeana.entity.solr.model.factory.EntityObjectFactory;

public abstract class BaseEntityService{
	
	private final Logger log = LogManager.getLogger(getClass());

	@SuppressWarnings("unchecked")
	protected <T extends Entity> ResultSet<T> buildResultSet(QueryResponse rsp, String[] outLanguage) {
		
		ResultSet<T> resultSet = new ResultSet<>();
		
		DocumentObjectBinder binder = new DocumentObjectBinder();
		SolrDocumentList docList = rsp.getResults();
		String type;
		T entity;
		Class<T> entityClass;
		List<T> beans = new ArrayList<T>();
		
		for (SolrDocument doc : docList) {
			type = (String) doc.get(ConceptSolrFields.INTERNAL_TYPE);
			entityClass = (Class<T>) EntityObjectFactory.getInstance().getClassForType(type);
			entity = (T) binder.getBean(entityClass, doc);
			processLanguageMaps(entity, outLanguage);
			beans.add(entity);
		}
		
		if (rsp.getFacetFields() != null) {
			List<FacetFieldView> facetFields = new ArrayList<>(rsp.getFacetFields().size());
			for (FacetField solrFacetField : rsp.getFacetFields()){
				facetFields.add(new FacetFieldViewImpl(solrFacetField));
			}

			resultSet.setFacetFields(facetFields);
		}

//		if (rsp.getFacetQuery() != null)
//			resultSet.setQueryFacets(rsp.getFacetQuery());
		
		resultSet.setResults(beans);
		resultSet.setResultSize(rsp.getResults().getNumFound());

		return resultSet;
	}

	
	protected void processLanguageMaps(Entity entity, String[] outLanguage) {
		if(outLanguage == null || outLanguage.length == 0)
			return;
		
		filterLanguageMap(entity.getPrefLabelStringMap(), outLanguage);
		filterLanguageMap(entity.getAltLabel(), outLanguage);
		filterLanguageMap(entity.getHiddenLabel(), outLanguage);
		filterLanguageMap(entity.getNote(), outLanguage);	
		
		EntityTypes type;
		try {
			type = EntityTypes.getByInternalType(entity.getInternalType());
		} catch (UnsupportedEntityTypeException e) {
			//actually should never happen here
			throw new RuntimeException(e);
		}
		switch (type){
			case Concept:
				break; //has only common language maps
			case Agent:
				processSpecificLanguageMaps((Agent) entity, outLanguage);
				break;
			case Place:
				break;//has only common language maps
			case Timespan:
				break;//not supported yet
			case Organization:
				 processSpecificLanguageMaps((Organization) entity, outLanguage);
				 break;
			case All:
				break; //actually not possible at this stage
			default:
				break;//shouldn't occur as a runtime exception is thrown above
		}
		
	}

	void filterLanguageMap(Map<String, ?> languageMap, String[] outLanguage) {
		if(languageMap == null || languageMap.isEmpty())
			return;
		
		Set<String> keys = languageMap.keySet();
		String language;
		String[] parts;
		List<String> invalidKeys = new ArrayList<String>(outLanguage.length); 
		//identify invalid keys
		for (String key : keys) {
			parts = StringUtils.split(key, '.');//might have problems with skos_prefLabel.
			language = parts[parts.length-1];//last part
			if(!ArrayUtils.contains(outLanguage, language))
				invalidKeys.add(key);			
		}
		
		//remove invalid keys from map
		for (String invalidKey : invalidKeys) {
			languageMap.remove(invalidKey);
		}
		
	}


	void processSpecificLanguageMaps(Agent entity, String[] outLanguage) {
		filterLanguageMap(entity.getBiographicalInformation(), outLanguage);
		filterLanguageMap(entity.getName(), outLanguage);
		filterLanguageMap(entity.getPlaceOfBirth(), outLanguage);
		filterLanguageMap(entity.getPlaceOfDeath(), outLanguage);
		filterLanguageMap(entity.getProfessionOrOccupation(), outLanguage);
	}

	void processSpecificLanguageMaps(Organization entity, String[] outLanguage) {
		filterLanguageMap(entity.getAcronym(), outLanguage);
		filterLanguageMap(entity.getDescription(), outLanguage);	
	}
	
	public Logger getLog() {
		return log;
	}

}
