package eu.europeana.entity.web.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.core.JsonProcessingException;

import eu.europeana.api.common.config.I18nConstants;
import eu.europeana.api.commons.definitions.search.result.ResultsPage;
import eu.europeana.api.commons.definitions.vocabulary.CommonApiConstants;
import eu.europeana.api.commons.definitions.vocabulary.CommonLdConstants;
import eu.europeana.api.commons.definitions.vocabulary.ContextTypes;
import eu.europeana.api.commons.utils.ResultsPageSerializer;
import eu.europeana.api.commons.web.controller.BaseRestController;
import eu.europeana.api.commons.web.exception.HttpException;
import eu.europeana.api.commons.web.http.HttpHeaders;
import eu.europeana.entity.definitions.exceptions.ConceptSchemeProfileValidationException;
import eu.europeana.entity.definitions.exceptions.UnsupportedEntityTypeException;
import eu.europeana.entity.definitions.exceptions.UnsupportedFormatTypeException;
import eu.europeana.entity.definitions.formats.FormatTypes;
import eu.europeana.entity.definitions.model.ConceptScheme;
import eu.europeana.entity.definitions.model.Entity;
import eu.europeana.entity.definitions.model.search.SearchProfiles;
import eu.europeana.entity.definitions.model.vocabulary.LdProfiles;
import eu.europeana.entity.definitions.model.vocabulary.SuggestAlgorithmTypes;
import eu.europeana.entity.definitions.model.vocabulary.WebEntityConstants;
import eu.europeana.entity.utils.jsonld.EuropeanaEntityLd;
import eu.europeana.entity.web.exception.ParamValidationException;
import eu.europeana.entity.web.jsonld.EntityResultsPageSerializer;
import eu.europeana.entity.web.jsonld.EntitySchemaOrgSerializer;
import eu.europeana.entity.web.service.EntityService;
import eu.europeana.entity.web.service.authorization.AuthorizationService;
import eu.europeana.entity.web.xml.EntityXmlSerializer;

public abstract class BaseRest extends BaseRestController {

    @Resource
    private EntityService entityService;

    @Resource
    AuthorizationService authorizationService;

    @Resource
    EntityXmlSerializer entityXmlSerializer;

    Logger logger = LogManager.getLogger(getClass());
    
    Pattern pattern = null;

    public BaseRest() {
	super();
	String regexPattern = "[^A-Za-z0-9]";
	pattern = Pattern.compile(regexPattern);
    }

    public AuthorizationService getAuthorizationService() {
	return authorizationService;
    }

    protected EntityService getEntityService() {
	return entityService;
    }

    public void setEntityService(EntityService entityService) {
	this.entityService = entityService;
    }

    public Logger getLogger() {
	return logger;
    }

    /**
     * @return
     */
    protected String getDefaultUserToken() {
	return getAuthorizationService().getConfiguration().getUserToken();
    }

    public String getApiVersion() {
    	return getAuthorizationService().getConfiguration().getApiVersion();
    }
    
    /**
     * This method returns the json-ld serialization for the given results page,
     * according to the specifications of the provided search profile
     * 
     * @param resPage
     * @param profile
     * @return
     * @throws JsonProcessingException
     */
    protected String searializeResultsPage(ResultsPage<? extends Entity> resPage, SearchProfiles profile)
	    throws JsonProcessingException {
	ResultsPageSerializer<? extends Entity> serializer = new EntityResultsPageSerializer<>(resPage,
		ContextTypes.ENTITY.getJsonValue(), CommonLdConstants.RESULT_PAGE);
	String profileVal = (profile == null) ? null : profile.name();
	return serializer.serialize(profileVal);
    }

    /**
     * This method verifies if the provided scope parameter is a valid one
     * 
     * @param scope
     * @return
     * @throws ParamValidationException
     */
    protected String validateScopeParam(String scope) throws ParamValidationException {
	if (StringUtils.isBlank(scope))
	    return null;

	if (!WebEntityConstants.PARAM_SCOPE_EUROPEANA.equalsIgnoreCase(scope))
	    throw new ParamValidationException(I18nConstants.INVALID_PARAM_VALUE, WebEntityConstants.QUERY_PARAM_SCOPE,
		    scope);

	return WebEntityConstants.PARAM_SCOPE_EUROPEANA;
    }

    /**
     * This method verifies if the provided format parameter is a valid one
     * 
     * @param The format string
     * @return The format type
     * @throws ParamValidationException
     */
    protected FormatTypes getFormatType(String extension) throws ParamValidationException {

	// default format, when none provided
	if (extension == null)
	    return FormatTypes.jsonld;

	try {
	    return FormatTypes.getByExtention(extension);
	} catch (UnsupportedFormatTypeException e) {
	    throw new ParamValidationException(I18nConstants.INVALID_PARAM_VALUE, WebEntityConstants.QUERY_PARAM_FORMAT,
		    extension, HttpStatus.NOT_FOUND, null);
	}

    }

    /**
     * This method verifies that the provided text parameter is a valid one. It
     * should not contain field names e.g. "who:mozart"
     * 
     * @param text
     * @return validated text
     * @throws ParamValidationException
     */
    protected String validateTextParam(String text) throws ParamValidationException {
	if (StringUtils.isBlank(text)) {
	    return null;
	}

	if (text.contains(WebEntityConstants.FIELD_DELIMITER)) {
	    throw new ParamValidationException(I18nConstants.INVALID_PARAM_VALUE, WebEntityConstants.QUERY_PARAM_TEXT,
		    text);
	}

	return text;
    }
    
    /**
     * This method verifies that the provided text parameter is a valid one. It
     * should not contain field names e.g. "who:mozart" and special characters e.g. " or (
     * 
     * @param text
     * @return validated text
     * @throws ParamValidationException
     */    
    protected String preProcessQuery(String text) throws ParamValidationException {
	String res = validateTextParam(text);
	
	StringBuffer sb = new StringBuffer(); 
	Matcher m = pattern.matcher(res);
	while (m.find())
	{
	    String repString = "";
	    if (repString != null)    
	        m.appendReplacement(sb, repString);
	}
	m.appendTail(sb);
	String resSpecChar = sb.toString();
	if (StringUtils.isNotBlank(resSpecChar))
	    res = resSpecChar;
	return res;
    }

    /**
     * This method verifies if the provided algorithm parameter is a valid one
     * 
     * @param algorithm
     * @return validated algorithm
     * @throws ParamValidationException
     */
    protected SuggestAlgorithmTypes validateAlgorithmParam(String algorithm) throws ParamValidationException {
	try {
	    return SuggestAlgorithmTypes.getByName(algorithm);
	} catch (Exception e) {
	    throw new ParamValidationException(I18nConstants.INVALID_PARAM_VALUE,
		    WebEntityConstants.QUERY_PARAM_ALGORITHM, algorithm);
	}
    }
 

    /**
     * This methods applies Linked Data profile to a concept scheme
     * 
     * @param conceptScheme The given concept scheme
     * @param profile       Provided Linked Data profile
     * @return profiled user set value
     */
    public ConceptScheme applyProfile(ConceptScheme conceptScheme, LdProfiles profile) {

	// set unnecessary fields to null - the empty fields will not be
	// presented
	switch (profile) {
	case STANDARD:
	    // not for stadard profile
	    break;
	case MINIMAL:
	default:
	    break;
	}

	return conceptScheme;
    }

    /**
     * This method takes profile from a HTTP header if it exists or from the passed
     * request parameter.
     * 
     * @param paramProfile The HTTP request parameter
     * @param request      The HTTP request with headers
     * @return profile value
     * @throws HttpException
     * @throws ConceptSchemeProfileValidationException
     */
    public LdProfiles getProfile(String paramProfile, HttpServletRequest request) throws HttpException {

	LdProfiles profile = null;
	String preferHeader = request.getHeader(HttpHeaders.PREFER);
	if (preferHeader != null) {
	    // identify profile by prefer header
	    profile = getProfile(preferHeader);
	    getLogger().debug("Profile identified by prefer header: " + profile.name());
	} else {
	    if (paramProfile == null)
		return LdProfiles.MINIMAL;
	    // get profile from param
	    try {
		profile = LdProfiles.getByName(paramProfile);
	    } catch (ConceptSchemeProfileValidationException e) {
		throw new ParamValidationException(I18nConstants.INVALID_PARAM_VALUE, I18nConstants.INVALID_PARAM_VALUE,
			new String[] { CommonApiConstants.QUERY_PARAM_PROFILE, paramProfile }, HttpStatus.BAD_REQUEST,
			e);
	    }
	}
	return profile;
    }

    /**
     * This method returns the json-ld serialization for the given results page,
     * according to the specifications of the provided search profile
     * 
     * @param resPage
     * @param profile
     * @return
     * @throws JsonProcessingException
     */
    protected String serializeResultsPage(ResultsPage<? extends Entity> resPage, SearchProfiles profile)
	    throws JsonProcessingException {
	ResultsPageSerializer<? extends Entity> serializer = new EntityResultsPageSerializer<>(resPage,
		ContextTypes.ENTITY.getJsonValue(), CommonLdConstants.RESULT_PAGE);
	String profileVal = (profile == null) ? null : profile.name();
	return serializer.serialize(profileVal);
    }

    /**
     * This method retrieves view profile if provided within the "If-Match" HTTP
     * header
     * 
     * @param request
     * @return profile value
     * @throws HttpException
     */
    // TODO have generic implementation in API-Commons
    LdProfiles getProfile(String preferHeader) throws HttpException {
	LdProfiles ldProfile = null;
	String ldPreferHeaderStr = null;
	String INCLUDE = "include";

	if (StringUtils.isNotEmpty(preferHeader)) {
	    // log header for debuging
	    getLogger().debug("'Prefer' header value: " + preferHeader);

	    try {
		Map<String, String> preferHeaderMap = parsePreferHeader(preferHeader);
		ldPreferHeaderStr = preferHeaderMap.get(INCLUDE).replace("\"", "");
		ldProfile = LdProfiles.getByHeaderValue(ldPreferHeaderStr.trim());
	    } catch (ConceptSchemeProfileValidationException e) {
		throw new HttpException(I18nConstants.INVALID_HEADER_VALUE, I18nConstants.INVALID_HEADER_VALUE,
			new String[] { HttpHeaders.PREFER, preferHeader }, HttpStatus.BAD_REQUEST, null);
	    } catch (Throwable th) {
		throw new HttpException(I18nConstants.INVALID_HEADER_FORMAT, I18nConstants.INVALID_HEADER_FORMAT,
			new String[] { HttpHeaders.PREFER, preferHeader }, HttpStatus.BAD_REQUEST, null);
	    }
	}

	return ldProfile;
    }

    /**
     * This method parses prefer header in keys and values
     * 
     * @param preferHeader
     * @return map of prefer header keys and values
     */
    // TODO: move this method to API-Commons
    public Map<String, String> parsePreferHeader(String preferHeader) {
	String[] headerParts = null;
	String[] contentParts = null;
	int KEY_POS = 0;
	int VALUE_POS = 1;

	Map<String, String> resMap = new HashMap<String, String>();

	headerParts = preferHeader.split(";");
	for (String headerPart : headerParts) {
	    contentParts = headerPart.split("=");
	    resMap.put(contentParts[KEY_POS], contentParts[VALUE_POS]);
	}
	return resMap;
    }

    /**
     * This method selects serialization method according to provided format.
     * 
     * @param entity The entity
     * @param format The format extension
     * @return entity in jsonLd format
     * @throws UnsupportedEntityTypeException
     * @throws HttpException 
     */
    protected String serialize(Entity entity, FormatTypes format) throws UnsupportedEntityTypeException, HttpException {

	String responseBody = null;

	if (FormatTypes.jsonld.equals(format)) {
	    EuropeanaEntityLd entityLd = new EuropeanaEntityLd(entity);
	    return entityLd.toString(4);
	} else if (FormatTypes.schema.equals(format)) {
	    responseBody = (new EntitySchemaOrgSerializer()).serializeEntity(entity);
	} else if (FormatTypes.xml.equals(format)) {
	    responseBody = entityXmlSerializer.serializeXml(entity);
	}
	return responseBody;
    }

}
