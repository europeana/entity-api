package eu.europeana.entity.web.service.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.ListUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.databind.ObjectMapper;

import eu.europeana.api.common.config.I18nConstants;
import eu.europeana.api.commons.definitions.search.Query;
import eu.europeana.api.commons.definitions.search.ResultSet;
import eu.europeana.api.commons.definitions.search.result.ResultsPage;
import eu.europeana.api.commons.definitions.search.result.impl.ResultsPageImpl;
import eu.europeana.api.commons.definitions.vocabulary.CommonApiConstants;
import eu.europeana.api.commons.web.exception.HttpException;
import eu.europeana.entity.definitions.exceptions.EntityAttributeInstantiationException;
import eu.europeana.entity.definitions.exceptions.EntityInstantiationException;
import eu.europeana.entity.definitions.exceptions.UnsupportedEntityTypeException;
import eu.europeana.entity.definitions.model.ConceptScheme;
import eu.europeana.entity.definitions.model.Entity;
import eu.europeana.entity.definitions.model.vocabulary.EntityTypes;
import eu.europeana.entity.definitions.model.vocabulary.SuggestAlgorithmTypes;
import eu.europeana.entity.definitions.model.vocabulary.WebEntityConstants;
import eu.europeana.entity.definitions.model.vocabulary.WebEntityFields;
import eu.europeana.entity.solr.exception.EntityRetrievalException;
import eu.europeana.entity.solr.exception.EntityServiceException;
import eu.europeana.entity.solr.exception.EntitySuggestionException;
import eu.europeana.entity.solr.service.SolrEntityService;
import eu.europeana.entity.solr.service.impl.EntityQueryBuilder;
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

/**
 * @author GrafR
 *
 */
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

    /*
     * (non-Javadoc)
     * 
     * @see eu.europeana.entity.web.service.EntityService#
     */
    @Override
    public ResultSet<? extends EntityPreview> suggest(String text, String[] language, List<EntityTypes> entityTypes,
	    String scope, String namespace, int rows, SuggestAlgorithmTypes algorithm)
	    throws InternalServerException, ParamValidationException {

	Query query = null;
	ResultSet<? extends EntityPreview> res;
	try {
	    switch (algorithm) {
	    case suggest:
		EntityQueryBuilder builder = new EntityQueryBuilder();
		query = builder.buildSearchQuery(text, null, rows);
		res = solrEntityService.suggest(query, language, entityTypes, scope, rows);
		break;
	    case suggestByLabel:
		res = solrEntityService.suggestByLabel(text, language, entityTypes, scope, rows);
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
    public ResultSet<? extends Entity> search(Query query, String[] outLanguage, List<EntityTypes> entityTypes,
	    String scope) throws HttpException {

	return solrEntityService.search(query, outLanguage, entityTypes, scope);
    }

    /**
     * @param entityTypes
     * @param suggest
     * @throws ParamValidationException
     */
    public List<EntityTypes> validateEntityTypes(List<EntityTypes> entityTypes, boolean suggest) throws ParamValidationException {
	// search
	if (!suggest) {
	    if (isEmptyOrAll(entityTypes)) {
		//no filtering needed
		return null;
	    }
	} else {// suggest

	    if (isEmptyOrAll(entityTypes)) {
		entityTypes.clear();
		entityTypes.add(EntityTypes.Concept);
		entityTypes.add(EntityTypes.Agent);
		entityTypes.add(EntityTypes.Place);
		entityTypes.add(EntityTypes.Organization);
	    }
	    
	    // ConceptScheme Not Supported in suggester
	    if (entityTypes.contains(EntityTypes.ConceptScheme))
		throw new ParamValidationException(I18nConstants.INVALID_PARAM_VALUE,
			WebEntityConstants.QUERY_PARAM_TYPE, EntityTypes.ConceptScheme.getInternalType());
	}
	
	return entityTypes;
    }

    private boolean isEmptyOrAll(List<EntityTypes> entityTypes) {
	return entityTypes == null || entityTypes.isEmpty() || entityTypes.contains(EntityTypes.All);
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
     * check if the update test must merge the properties or if it simply overwrites
     * it
     * 
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
	    getLogger().error("Cannot remove concept scheme from solr index: " + existingConceptScheme.getEntityId(),
		    e);
	}

	return updateConceptScheme((PersistentConceptScheme) existingConceptScheme, existingConceptScheme);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * eu.europeana.entity.web.service.EntityService#performAtomicUpdate(java.lang.
     * String, java.util.List, java.util.List)
     */
    public void updateConceptSchemeForEntities(String conceptSchemeId, List<String> addToEntities,
	    List<String> removeFromEntities) {

	try {
	    getSolrService().performAtomicUpdate(conceptSchemeId, addToEntities, removeFromEntities);
	} catch (EntityServiceException e) {
	    getLogger().error("Cannot perform atomic update for concept scheme identifier: " + conceptSchemeId
		    + " for entities. addition: " + addToEntities.toString() + ", removal:"
		    + removeFromEntities.toString(), e);
	}

    }

    @Override
    public List<String> searchEntityIds(Query searchQuery, String scope, List<EntityTypes> entityTypes)
	    throws HttpException {

	List<String> matchingEntityIds = new ArrayList<String>();
	ResultSet<? extends Entity> results = search(searchQuery, null, entityTypes, scope);
	for (Entity searchRes : results.getResults()) {
	    matchingEntityIds.add(searchRes.getEntityId());
	}
	return matchingEntityIds;
    }

    @Override
    public ConceptScheme updateEntitiesWithConceptScheme(ConceptScheme storedConceptScheme)
	    throws HttpException, UnsupportedEncodingException, UnsupportedEntityTypeException{
	EntityQueryBuilder queryBuilder = new EntityQueryBuilder();

	// parse and process parameters from is defined by URL
	MultiValueMap<String, String> parameters = UriComponentsBuilder
		.fromUriString(storedConceptScheme.getIsDefinedBy()).build().getQueryParams();

	String scope = parameters.getFirst(WebEntityConstants.QUERY_PARAM_SCOPE);
	String sort = parameters.getFirst(CommonApiConstants.QUERY_PARAM_SORT);

	String type = parameters.getFirst(WebEntityConstants.QUERY_PARAM_TYPE);
	List<EntityTypes> entityTypes = getEntityTypesFromString(type);
	// we need to process entity types, for building the correct filter
	entityTypes = validateEntityTypes(entityTypes, false);

	// Search concepts that match the ConceptScheme
	Query searchQuery = queryBuilder.buildParameterSearchQuery(parameters, sort);
	List<String> matchingEntityIds = searchEntityIds(searchQuery, scope, entityTypes);
	getLogger().debug("Matching concepts for scheme : " + storedConceptScheme.getEntityId() + ", #results: "
		+ matchingEntityIds.size());

	// search entities that are already in the current ConceptScheme
	// first page with max size
	String conceptSchemeId = storedConceptScheme.getEntityId();
	Query existingQuery = queryBuilder.buildEntitiesInSchemeQuery(conceptSchemeId);

	List<String> existingEntityIds = searchEntityIds(existingQuery, null, null);
	getLogger().debug("existing before update: " + existingEntityIds.size());

	// Compute ADD and REMOVE lists
	@SuppressWarnings("unchecked")
	List<String> removeList = ListUtils.subtract(existingEntityIds, matchingEntityIds);
	@SuppressWarnings("unchecked")
	List<String> addList = ListUtils.subtract(matchingEntityIds, existingEntityIds);

	// perform atomic updates for entities
	updateConceptSchemeForEntities(conceptSchemeId, addList, removeList);

	// update concept scheme, add total (update modified)
	// fire a new search query to check the actual number of entities in scheme
	existingQuery.setPageSize(0);
	ResultSet<? extends Entity> storedResults = search(existingQuery, null, entityTypes, scope);
	// number of (existing) entities in scheme after atomic update
	long inSchemeCount = storedResults.getResultSize();
	getLogger().debug("matching after update: " + inSchemeCount);

	storedConceptScheme.setTotal((int) inSchemeCount);
	return updateConceptScheme((PersistentConceptScheme) storedConceptScheme, null);
    }

    /**
     * process type from URI params map
     * 
     * @param parameters
     * @return entity types
     * @throws ParamValidationException
     * @throws UnsupportedEncodingException
     * @throws UnsupportedEntityTypeException
     */
//    @Override
//    public List<EntityTypes> extractEntityTypes(MultiValueMap<String, String> parameters)
//	    throws UnsupportedEncodingException, UnsupportedEntityTypeException {
//	List<EntityTypes> entityTypes = null;
//	String type = parameters.getFirst(WebEntityConstants.QUERY_PARAM_TYPE);
//
//	if (StringUtils.isBlank(type))
//	    type = EntityTypes.All.name();
//	else
//	    type = URLDecoder.decode(type, StandardCharsets.UTF_8.name());
//
//	entityTypes = getEntityTypesFromString(type);
//	return entityTypes;
//    }
    
    /**
     * Get entity type string list from comma separated entities string.
     * 
     * @param commaSepEntityTypes Comma separated entities string
     * @return Entity types string list
     * @throws UnsupportedEntityTypeException
     * @throws ParamValidationException
     */
    @Override
    public List<EntityTypes> getEntityTypesFromString(String commaSepEntityTypes)
	    throws UnsupportedEntityTypeException {

	if(StringUtils.isBlank(commaSepEntityTypes)) {
	    return null;
	}
	    
	String[] splittedEntityTypes = commaSepEntityTypes.split(",");
	List<EntityTypes> entityTypes = new ArrayList<EntityTypes>();

	EntityTypes entityType = null;
	String typeAsString = null;

	for (int i = 0; i < splittedEntityTypes.length; i++) {
	    typeAsString = splittedEntityTypes[i].trim();
	    entityType = EntityTypes.getByInternalType(typeAsString);
	    entityTypes.add(entityType);
	}

	return entityTypes;
    }
}
