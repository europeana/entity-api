package eu.europeana.entity.web.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.core.JsonProcessingException;

import eu.europeana.api.common.config.I18nConstants;
import eu.europeana.api.commons.definitions.search.result.ResultsPage;
import eu.europeana.api.commons.definitions.vocabulary.CommonLdConstants;
import eu.europeana.api.commons.definitions.vocabulary.ContextTypes;
import eu.europeana.api.commons.utils.ResultsPageSerializer;
import eu.europeana.api.commons.web.exception.ApplicationAuthenticationException;
import eu.europeana.api.commons.web.exception.HttpException;
import eu.europeana.api.commons.web.http.HttpHeaders;
import eu.europeana.entity.definitions.exceptions.ConceptSchemeProfileValidationException;
import eu.europeana.entity.definitions.exceptions.UnsupportedEntityTypeException;
import eu.europeana.entity.definitions.exceptions.UnsupportedFormatTypeException;
import eu.europeana.entity.definitions.formats.FormatTypes;
import eu.europeana.entity.definitions.model.ConceptScheme;
import eu.europeana.entity.definitions.model.Entity;
import eu.europeana.entity.definitions.model.search.SearchProfiles;
import eu.europeana.entity.definitions.model.vocabulary.EntityTypes;
import eu.europeana.entity.definitions.model.vocabulary.LdProfiles;
import eu.europeana.entity.definitions.model.vocabulary.SuggestAlgorithmTypes;
import eu.europeana.entity.definitions.model.vocabulary.WebEntityConstants;
import eu.europeana.entity.definitions.model.vocabulary.WebEntityFields;
import eu.europeana.entity.utils.serialize.ConceptSchemeLdSerializer;
import eu.europeana.entity.web.exception.ParamValidationException;
import eu.europeana.entity.web.exception.authentication.EntityAuthenticationException;
import eu.europeana.entity.web.exception.authorization.OperationAuthorizationException;
import eu.europeana.entity.web.http.EntityHttpHeaders;
import eu.europeana.entity.web.jsonld.EntityResultsPageSerializer;
import eu.europeana.entity.web.service.EntityService;
import eu.europeana.entity.web.service.authorization.AuthorizationService;

public abstract class BaseRest {

	@Resource
	private EntityService entityService;

	@Resource
	AuthorizationService authorizationService;
	
	public BaseRest() {
		super();
	}

	protected EntityService getEntityService() {
		return entityService;
	}

	public void setEntityService(EntityService entityService) {
		this.entityService = entityService;
	}
	
	public AuthorizationService getAuthorizationService() {
		return authorizationService;
	}

	public void setAuthorizationService(AuthorizationService authorizationService) {
		this.authorizationService = authorizationService;
	}

	Logger logger = LogManager.getLogger(getClass());
	
	public Logger getLogger() {
		return logger;
	}
	/**
	 * This method is used for validation of the provided api key
	 * 
	 * @param wsKey
	 * @throws EntityAuthenticationException
	 */
	protected void validateApiKey(String wsKey) throws EntityAuthenticationException {
		// throws exception if the wskey is not found
		if (wsKey == null)
			throw new EntityAuthenticationException(I18nConstants.MISSING_APIKEY, I18nConstants.MISSING_APIKEY, null,
					HttpStatus.UNAUTHORIZED);
		if (StringUtils.isEmpty(wsKey))
			throw new EntityAuthenticationException(null, I18nConstants.EMPTY_APIKEY, null);
		if (!wsKey.equals("apidemo"))
			throw new EntityAuthenticationException(null, I18nConstants.INVALID_APIKEY, new String[] { wsKey });
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
	 * Get entity type string list from comma separated entities string.
	 * 
	 * @param commaSepEntityTypes
	 *            Comma separated entities string
	 * @return Entity types string list
	 * @throws ParamValidationException
	 */
	protected EntityTypes[] getEntityTypesFromString(String commaSepEntityTypes) throws ParamValidationException {

		String[] splittedEntityTypes = commaSepEntityTypes.split(",");
		EntityTypes[] entityTypes = new EntityTypes[splittedEntityTypes.length];

		EntityTypes entityType = null;
		String typeAsString = null;

		try {
			for (int i = 0; i < splittedEntityTypes.length; i++) {
				typeAsString = splittedEntityTypes[i].trim();
				entityType = EntityTypes.getByInternalType(typeAsString);
				entityTypes[i] = entityType;
			}
		} catch (UnsupportedEntityTypeException e) {
			throw new ParamValidationException(I18nConstants.INVALID_PARAM_VALUE, WebEntityConstants.QUERY_PARAM_TYPE,
					typeAsString);
		}

		return entityTypes;
	}

	/**
	 * This method splits the list of values provided as concatenated string to the
	 * corresponding array representation
	 * 
	 * @param requestParam
	 * @return
	 */
	protected String[] toArray(String requestParam) {
		if (StringUtils.isEmpty(requestParam))
			return null;
		String[] array = StringUtils.splitByWholeSeparator(requestParam, ",");
		return StringUtils.stripAll(array);
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
	 * @param The
	 *            format string
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
					extension);
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
	 * This method takes user token from a HTTP header if it exists or from the
	 * passed request parameter.
	 * 
	 * @param paramUserToken
	 *            The HTTP request parameter
	 * @param request
	 *            The HTTP request with headers
	 * @return user token
	 * @throws ApplicationAuthenticationException
	 */
	public String getUserToken(String paramUserToken, HttpServletRequest request)
			throws ApplicationAuthenticationException {
		int USER_TOKEN_TYPE_POS = 0;
		int BASE64_ENCODED_STRING_POS = 1;
		String userToken = null;
		String userTokenHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
		if (userTokenHeader != null) {
			getLogger().trace("'Authorization' header value: " + userTokenHeader);
			String[] headerElems = userTokenHeader.split(" ");
			if (headerElems.length < 2)
				throw new ApplicationAuthenticationException(I18nConstants.INVALID_HEADER_FORMAT,
						I18nConstants.INVALID_HEADER_FORMAT, new String[] { userTokenHeader });

			String userTokenType = headerElems[USER_TOKEN_TYPE_POS];
			if (!EntityHttpHeaders.BEARER.equals(userTokenType)) {
				throw new ApplicationAuthenticationException(I18nConstants.UNSUPPORTED_TOKEN_TYPE,
						I18nConstants.UNSUPPORTED_TOKEN_TYPE, new String[] { userTokenType });
			}

			String encodedUserToken = headerElems[BASE64_ENCODED_STRING_POS];

			userToken = decodeBase64(encodedUserToken);
			getLogger().debug("Decoded user token: " + userToken);

		} else {
			// @deprecated to be removed in the next versions
			// fallback to URL param
			userToken = paramUserToken;
		}
		return userToken;
	}
	
	/**
	 * This method performs decoding of base64 string
	 * 
	 * @param base64Str
	 * @return decoded string
	 * @throws ApplicationAuthenticationException
	 */
	public String decodeBase64(String base64Str) throws ApplicationAuthenticationException {
		String res = null;
		try {
			byte[] decodedBase64Str = Base64.decodeBase64(base64Str);
			res = new String(decodedBase64Str);
		} catch (Exception e) {
			throw new ApplicationAuthenticationException(I18nConstants.BASE64_DECODING_FAIL,
					I18nConstants.BASE64_DECODING_FAIL, null);
		}
		return res;
	}

	/**
	 * This method serializes concept scheme and applies profile to the object.
	 * 
	 * @param profile
	 * @param storedConceptScheme
	 * @return serialized user set as a JsonLd string
	 * @throws IOException
	 */
//	protected String serializeConceptScheme(String profile, ConceptScheme storedConceptScheme) throws IOException {
	protected String serializeConceptScheme(LdProfiles profile, ConceptScheme storedConceptScheme) throws IOException {
		// apply linked data profile from header
		ConceptScheme resConceptScheme = applyProfile(storedConceptScheme, profile);

		// serialize ConceptScheme description in JSON-LD and respond with HTTP 201 if successful
		ConceptSchemeLdSerializer serializer = new ConceptSchemeLdSerializer();
		String serializedConceptSchemeJsonLdStr = serializer.serialize(resConceptScheme);
		return serializedConceptSchemeJsonLdStr;
	}

	/**
	 * This methods applies Linked Data profile to a concept scheme
	 * 
	 * @param conceptScheme
	 *            The given concept scheme
	 * @param profile
	 *            Provided Linked Data profile
	 * @return profiled user set value
	 */
//	public ConceptScheme applyProfile(ConceptScheme conceptScheme, String profile) {
	public ConceptScheme applyProfile(ConceptScheme conceptScheme, LdProfiles profile) {

		// check that not more then maximal allowed number of items are
		// presented
//		if (profile != LdProfiles.MINIMAL && userSet.getItems() != null) {
//			int itemsCount = userSet.getItems().size();
//			if (itemsCount > WebEntityFields.MAX_ITEMS_TO_PRESENT) {
//				List<String> itemsPage = userSet.getItems().subList(0, WebUserSetFields.MAX_ITEMS_TO_PRESENT);
//				userSet.setItems(itemsPage);
//				profile = LdProfiles.MINIMAL;
//				getLogger().debug("Profile switched to minimal, due to set size!");
//			}
//		}

		// set unnecessary fields to null - the empty fields will not be
		// presented
		switch (profile) {
		case STANDARD:
			// not for stadard profile
			break;
		case MINIMAL:
		default:
//			userSet.setItems(null);
			break;
		}

		return conceptScheme;
	}
	

	/**
	 * This method takes profile from a HTTP header if it exists or from the
	 * passed request parameter.
	 * 
	 * @param paramProfile
	 *            The HTTP request parameter
	 * @param request
	 *            The HTTP request with headers
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
			// get profile from param
			try {
				profile = LdProfiles.getByName(paramProfile);
			} catch (ConceptSchemeProfileValidationException e) {
				throw new ParamValidationException(I18nConstants.INVALID_PARAM_VALUE, I18nConstants.INVALID_PARAM_VALUE,
						new String[] { WebEntityFields.PROFILE, paramProfile }, HttpStatus.BAD_REQUEST, e);
			}
		}
		return profile;
	}
	
	/**
	 * This method retrieves view profile if provided within the "If-Match" HTTP
	 * header
	 * 
	 * @param request
	 * @return profile value
	 * @throws HttpException
	 */
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
						new String[] {HttpHeaders.PREFER,  preferHeader}, HttpStatus.BAD_REQUEST, null);
			} catch (Throwable th){
				throw new HttpException(I18nConstants.INVALID_HEADER_FORMAT, I18nConstants.INVALID_HEADER_FORMAT,
						new String[] {HttpHeaders.PREFER,  preferHeader}, HttpStatus.BAD_REQUEST, null);
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
	 * This method checks that only the admins and the owners of the user sets
	 * are allowed to delete the user set. in the case of regular users (not
	 * admins), the autorization method must check if the users that calls the
	 * deletion (i.e. identified by provided user token) is the same user as the
	 * creator of the user set
	 * 
	 * @param conceptScheme
	 * @param wsKey
	 * @param queryUser
	 * @throws ApplicationAuthenticationException
	 */
	public void hasModifyRights(ConceptScheme conceptScheme, String wsKey, String queryUser)
			throws OperationAuthorizationException {

//		if (!(isAdmin(wsKey, queryUser) || userSet.getCreator().getName().equals(queryUser))) {
//			throw new OperationAuthorizationException(I18nConstants.USER_NOT_AUTHORIZED,
//					I18nConstants.USER_NOT_AUTHORIZED, new String[] { "User ID: " + queryUser }, HttpStatus.FORBIDDEN);
//		}
	}	
}