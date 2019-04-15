package eu.europeana.entity.web.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.databind.ObjectMapper;

import eu.europeana.api.common.config.I18nConstants;
import eu.europeana.api.commons.definitions.search.Query;
import eu.europeana.api.commons.definitions.search.ResultSet;
import eu.europeana.api.commons.definitions.search.impl.QueryImpl;
import eu.europeana.api.commons.definitions.search.result.ResultsPage;
import eu.europeana.api.commons.definitions.search.result.impl.ResultsPageImpl;
import eu.europeana.api.commons.definitions.vocabulary.CommonApiConstants;
import eu.europeana.api.commons.search.util.QueryBuilder;
import eu.europeana.api.commons.web.exception.HttpException;
import eu.europeana.entity.definitions.exceptions.EntityAttributeInstantiationException;
import eu.europeana.entity.definitions.exceptions.EntityInstantiationException;
import eu.europeana.entity.definitions.exceptions.UnsupportedEntityTypeException;
import eu.europeana.entity.definitions.model.ConceptScheme;
import eu.europeana.entity.definitions.model.Entity;
import eu.europeana.entity.definitions.model.search.SearchProfiles;
import eu.europeana.entity.definitions.model.vocabulary.ConceptSolrFields;
import eu.europeana.entity.definitions.model.vocabulary.EntityTypes;
import eu.europeana.entity.definitions.model.vocabulary.SuggestAlgorithmTypes;
import eu.europeana.entity.definitions.model.vocabulary.WebEntityConstants;
import eu.europeana.entity.definitions.model.vocabulary.WebEntityFields;
import eu.europeana.entity.solr.exception.EntityRetrievalException;
import eu.europeana.entity.solr.exception.EntitySuggestionException;
import eu.europeana.entity.solr.service.SolrEntityService;
import eu.europeana.entity.web.controller.exception.EntityIndexingException;
import eu.europeana.entity.web.exception.InternalServerException;
import eu.europeana.entity.web.exception.ParamValidationException;
import eu.europeana.entity.web.exception.RequestBodyValidationException;
import eu.europeana.entity.web.exception.response.ConceptSchemeNotFoundException;
import eu.europeana.entity.web.model.WebConceptSchemeImpl;
import eu.europeana.entity.web.model.view.EntityPreview;
import eu.europeana.entity.web.service.EntityService;
import eu.europeana.grouping.mongo.model.internal.PersistentConceptScheme;
import ioinformarics.oss.jackson.module.jsonld.JsonldModule;

public class EntityServiceImpl extends BaseEntityServiceImpl implements EntityService {

    public final String BASE_URL_DATA = "http://data.europeana.eu/";

    @Resource
    SolrEntityService solrEntityService;

    @Override
    public Entity retrieveByUrl(String type, String namespace, String identifier) throws HttpException {

	StringBuilder stringBuilder = new StringBuilder();

	stringBuilder.append(BASE_URL_DATA);
	if (StringUtils.isNotEmpty(type))
	    stringBuilder.append(type.toLowerCase() + "/");
	if (StringUtils.isNotEmpty(namespace) && !EntityTypes.Organization.getInternalType().equalsIgnoreCase(type))
	    stringBuilder.append(namespace + "/");
	if (StringUtils.isNotEmpty(identifier))
	    stringBuilder.append(identifier);

	String entityUri = stringBuilder.toString();
	Entity result;
	try {
	    result = solrEntityService.searchByUrl(type, entityUri);
	} catch (EntityRetrievalException e) {
	    throw new HttpException(e.getMessage(), I18nConstants.SERVER_ERROR_CANT_RETRIEVE_URI,
		    new String[] { entityUri }, HttpStatus.INTERNAL_SERVER_ERROR, e);
	} catch (UnsupportedEntityTypeException e) {
	    throw new HttpException(null, I18nConstants.UNSUPPORTED_ENTITY_TYPE, new String[] { type },
		    HttpStatus.NOT_FOUND, null);
	}
	// if not found send appropriate error message
	if (result == null)
	    throw new HttpException(null, I18nConstants.RESOURCE_NOT_FOUND, new String[] { entityUri },
		    HttpStatus.NOT_FOUND, null);

	return result;
    }

    /**
     * @deprecated use QueryBuilder
     * @param queryString
     * @param filters
     * @param rows
     * @return
     */
    protected Query buildSearchQuery(String queryString, String[] filters, int pageSize) {

	Query searchQuery = new QueryImpl();
	searchQuery.setQuery(queryString);
	searchQuery.setPageSize(Math.min(pageSize, Query.DEFAULT_MAX_PAGE_SIZE));
	searchQuery.setFilters(filters);

	return searchQuery;
    }

    /*
     * (non-Javadoc)
     * 
     * @see eu.europeana.entity.web.service.EntityService#
     */
    @Override
    public ResultSet<? extends EntityPreview> suggest(String text, String[] language, EntityTypes[] internalEntityTypes,
	    String scope, String namespace, int rows, SuggestAlgorithmTypes algorithm)
	    throws InternalServerException, ParamValidationException {

	Query query = null;
	ResultSet<? extends EntityPreview> res;
	try {
	    switch (algorithm) {
	    case suggest:
		query = buildSearchQuery(text, null, rows);
		res = solrEntityService.suggest(query, language, internalEntityTypes, scope, rows);
		break;
	    case suggestByLabel:
		res = solrEntityService.suggestByLabel(text, language, internalEntityTypes, scope, rows);
		break;
	    default:
		throw new ParamValidationException(WebEntityConstants.ALGORITHM, "" + algorithm);

	    }
	} catch (EntitySuggestionException e) {
	    throw new InternalServerException(e);
	}

	return res;
    }

    @Override
    public String resolveByUri(String uri) throws HttpException {
	String result;
	try {
	    result = solrEntityService.searchByCoref(uri);
	} catch (EntityRetrievalException e) {
	    throw new HttpException(e.getMessage(), I18nConstants.SERVER_ERROR_CANT_RESOLVE_SAME_AS_URI,
		    new String[] { uri }, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	// if not found send appropriate error message
	if (result == null)
	    throw new HttpException(null, I18nConstants.CANT_FIND_BY_SAME_AS_URI, new String[] { uri },
		    HttpStatus.NOT_FOUND);

	return result;
    }

    @Override
    public ResultSet<? extends Entity> search(Query query, String[] outLanguage, EntityTypes[] internalEntityTypes,
	    String scope) throws HttpException {

	return solrEntityService.search(query, outLanguage, internalEntityTypes, scope);
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
     * This method enriches provided custom selection fields by required fields if
     * they are not already provided in input array.
     * 
     * @param inputArray
     * @return enriched array
     */
    protected String[] buildCustomSelectionFields(String[] inputFields) {
	List<String> fieldList = new ArrayList<String>();
	Collections.addAll(fieldList, inputFields);
	// add mandatory fields
	if (!fieldList.contains(ConceptSolrFields.ID))
	    fieldList.add(ConceptSolrFields.ID);
	if (!fieldList.contains(ConceptSolrFields.INTERNAL_TYPE))
	    fieldList.add(ConceptSolrFields.INTERNAL_TYPE);

	return fieldList.toArray(new String[fieldList.size()]);
    }

    // TODO: consider usage of a helper class for helper methods
    public <T extends Entity> ResultsPage<T> buildResultsPage(Query searchQuery, ResultSet<T> results,
	    StringBuffer requestUrl, String reqParams) {
	@SuppressWarnings({ "rawtypes", "unchecked" })
	ResultsPage<T> resPage = new ResultsPageImpl();

	resPage.setItems(results.getResults());
	resPage.setFacetFields(results.getFacetFields());

	resPage.setTotalInPage(results.getResults().size());
	resPage.setTotalInCollection(results.getResultSize());

	String collectionUrl = buildCollectionUrl(searchQuery, requestUrl, reqParams);
	resPage.setCollectionUri(collectionUrl);

	int currentPage = searchQuery.getPageNr();
	String currentPageUrl = buildPageUrl(collectionUrl, currentPage, searchQuery.getPageSize());
	resPage.setCurrentPageUri(currentPageUrl);

	if (currentPage > 0) {
	    String prevPage = buildPageUrl(collectionUrl, currentPage - 1, searchQuery.getPageSize());
	    resPage.setPrevPageUri(prevPage);
	}

	// if current page is not the last one
	boolean isLastPage = resPage.getTotalInCollection() <= (currentPage + 1) * searchQuery.getPageSize();
	if (!isLastPage) {
	    String nextPage = buildPageUrl(collectionUrl, currentPage + 1, searchQuery.getPageSize());
	    resPage.setNextPageUri(nextPage);
	}

	return resPage;
    }

    private String buildPageUrl(String collectionUrl, int page, int pageSize) {
	StringBuilder builder = new StringBuilder(collectionUrl);
	builder.append("&").append(CommonApiConstants.QUERY_PARAM_PAGE).append("=").append(page);

	builder.append("&").append(CommonApiConstants.QUERY_PARAM_PAGE_SIZE).append("=").append(pageSize);

	return builder.toString();
    }

    private String buildCollectionUrl(Query searchQuery, StringBuffer requestUrl, String queryString) {

	// queryString = removeParam(WebAnnotationFields.PARAM_WSKEY,
	// queryString);

	// remove out of scope parameters
	queryString = removeParam(CommonApiConstants.QUERY_PARAM_PAGE, queryString);
	queryString = removeParam(CommonApiConstants.QUERY_PARAM_PAGE_SIZE, queryString);

	// avoid duplication of query parameters
	queryString = removeParam(CommonApiConstants.QUERY_PARAM_PROFILE, queryString);

	// add mandatory parameters
	if (StringUtils.isNotBlank(searchQuery.getSearchProfile())) {
	    queryString += ("&" + CommonApiConstants.QUERY_PARAM_PROFILE + "=" + searchQuery.getSearchProfile());
	}

	return requestUrl.append("?").append(queryString).toString();
    }

    protected String removeParam(final String queryParam, String queryParams) {
	String tmp;
	// avoid name conflicts search "queryParam="
	int startPos = queryParams.indexOf(queryParam + "=");
	int startEndPos = queryParams.indexOf("&", startPos + 1);

	if (startPos >= 0) {
	    // make sure to remove the "&" if not the first param
	    if (startPos > 0)
		startPos--;
	    tmp = queryParams.substring(0, startPos);

	    if (startEndPos > 0)
		tmp += queryParams.substring(startEndPos);
	} else {
	    tmp = queryParams;
	}
	return tmp;
    }

    @Override
    public ConceptScheme parseConceptSchemeLd(String groupingJsonLdStr) throws HttpException {

	JsonParser parser;
	ObjectMapper mapper = new ObjectMapper();
	mapper.registerModule(new JsonldModule());
	mapper.configure(Feature.AUTO_CLOSE_SOURCE, true);

	JsonFactory jsonFactory = mapper.getFactory();

	/**
	 * parse JsonLd string using JsonLdParser
	 */
	try {
	    parser = jsonFactory.createParser(groupingJsonLdStr);
	    ConceptScheme conceptScheme = mapper.readValue(parser, WebConceptSchemeImpl.class);
	    return conceptScheme;
	} catch (EntityAttributeInstantiationException e) {
	    throw new RequestBodyValidationException(I18nConstants.CONCEPT_SCHEME_CANT_PARSE_BODY,
		    new String[] { e.getMessage() }, e);
	} catch (JsonParseException e) {
	    throw new EntityInstantiationException("Json formating exception! " + e.getMessage(), e);
	} catch (IOException e) {
	    throw new EntityInstantiationException("Json reading exception! " + e.getMessage(), e);
	}
    }

    @Override
    public ConceptScheme storeConceptScheme(ConceptScheme conceptScheme) {

	updateTechnicalFields(conceptScheme);
	// store in mongo database
	ConceptScheme res = getMongoPersistence().store(conceptScheme);

	// add solr indexing here
	try {
	    getSolrService().store(res);
	} catch (Exception e) {
	    getLogger().warn("The concept scheme was stored correctly into the Mongo, but it was not indexed yet. ", e);
	}

	return res;
    }

    private void updateTechnicalFields(ConceptScheme conceptScheme) {
	Date now = new Date();
	conceptScheme.setModified(now);
	// if create
	if (conceptScheme.getCreated() == null)
	    conceptScheme.setCreated(now);

	conceptScheme.setInternalType(EntityTypes.ConceptScheme.getInternalType());
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * eu.europeana.entity.web.service.EntityService#validateWebConceptScheme(eu.
     * europeana.entity.definitions.model.ConceptScheme)
     */
    public void validateWebConceptScheme(ConceptScheme webConceptScheme) throws RequestBodyValidationException {

	// validate prefLabel
	if (webConceptScheme.getPrefLabel() == null) {
	    throw new RequestBodyValidationException(I18nConstants.ENTITY_VALIDATION_MANDATORY_PROPERTY,
		    new String[] { WebEntityFields.PREF_LABEL });
	}

	// validate isDefinedBy
	if (webConceptScheme.getIsDefinedBy() == null) {
	    throw new RequestBodyValidationException(I18nConstants.ENTITY_VALIDATION_MANDATORY_PROPERTY,
		    new String[] { WebEntityFields.IS_DEFINED_BY });
	}
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * eu.europeana.entity.web.service.EntityService#getConceptSchemeById(java.lang.
     * String)
     */
    @Override
    public ConceptScheme getConceptSchemeById(String conceptSchemeId) throws ConceptSchemeNotFoundException {
	ConceptScheme res = getMongoPersistence().getByIdentifier(conceptSchemeId);
	if (res == null) {
	    throw new ConceptSchemeNotFoundException(I18nConstants.ENTITY_NOT_FOUND, I18nConstants.ENTITY_NOT_FOUND,
		    new String[] { conceptSchemeId });
	}
	return res;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * eu.europeana.entity.web.service.EntityService#deleteConceptScheme(java.lang.
     * String)
     */
    public void deleteConceptScheme(String conceptSchemeId) throws ConceptSchemeNotFoundException {

	getMongoPersistence().deleteByIdentifier(conceptSchemeId);
    }

    /*
     * (non-Javadoc)
     * 
     * @see eu.europeana.entity.web.service.EntityService#updateConceptScheme(eu.
     * europeana.grouping.mongo.model.internal.PersistentConceptScheme,
     * eu.europeana.entity.definitions.model.ConceptScheme)
     */
    @Override
    public ConceptScheme updateConceptScheme(PersistentConceptScheme persistentConceptScheme,
	    ConceptScheme webConceptScheme) {
	mergeConceptSchemeProperties(persistentConceptScheme, webConceptScheme);

	Date now = new Date();
	persistentConceptScheme.setModified(now);

	ConceptScheme res = getMongoPersistence().update(persistentConceptScheme);

	// reindex concept scheme
	try {
	    // index concept scheme only if not disabled
	    if (!((WebConceptSchemeImpl) res).isDisabled()) {
	        reindexConceptScheme(res, res.getModified());
	    }
	} catch (EntityIndexingException e) {
	    getLogger().warn("The concept scheme could not be reindexed successfully: " + res.getEntityIdentifier(), e);
	}

	return res;
    }

    /**
     * Returns true by successful reindexing.
     * 
     * @param res
     * @return reindexing success status
     * @throws EntityIndexingException
     */
    protected boolean reindexConceptScheme(ConceptScheme res, Date lastIndexing) throws EntityIndexingException {
	boolean success = false;

	try {
	    getSolrService().update(res);
	    success = true;
	} catch (Exception e) {
	    throw new EntityIndexingException("cannot reindex concept scheme with ID: " + res.getEntityIdentifier(), e);
	}

	return success;
    }

    /**
     * check if the update test must merge the properties or if it simply overwrites it
     * @param ConceptScheme
     * @param updatedWebConceptScheme
     */
    private void mergeConceptSchemeProperties(PersistentConceptScheme conceptScheme,
	    ConceptScheme updatedWebConceptScheme) {

	if (updatedWebConceptScheme != null) {
	   
	    if (updatedWebConceptScheme.getIsDefinedBy() != null) {
		conceptScheme.setIsDefinedBy(updatedWebConceptScheme.getIsDefinedBy());
	    }

	    if (updatedWebConceptScheme.getDefinition() != null) {
		conceptScheme.setDefinition(updatedWebConceptScheme.getDefinition());
	    }

	    conceptScheme.setPrefLabelStringMap(updatedWebConceptScheme.getPrefLabelStringMap());
	}

    }

    /*
     * (non-Javadoc)
     * 
     * @see eu.europeana.entity.web.service.EntityService#disableConceptScheme(eu.
     * europeana.entity.definitions.model.ConceptScheme)
     */
    public ConceptScheme disableConceptScheme(ConceptScheme existingConceptScheme) {
	((WebConceptSchemeImpl) existingConceptScheme).setDisabled(true);
	try {
	    getSolrService().delete(existingConceptScheme.getEntityId());
	} catch (Exception e) {
	    getLogger().error(
		"Cannot remove concept scheme from solr index: " + existingConceptScheme.getEntityId(), e);
	}

	return updateConceptScheme((PersistentConceptScheme) existingConceptScheme, existingConceptScheme);
    }

}
