package eu.europeana.entity.web.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.core.JsonProcessingException;

import eu.europeana.api.common.config.I18nConstants;
import eu.europeana.api.commons.definitions.search.result.ResultsPage;
import eu.europeana.api.commons.definitions.vocabulary.CommonLdConstants;
import eu.europeana.api.commons.definitions.vocabulary.ContextTypes;
import eu.europeana.api.commons.utils.ResultsPageSerializer;
import eu.europeana.entity.definitions.exceptions.UnsupportedEntityTypeException;
import eu.europeana.entity.definitions.model.Entity;
import eu.europeana.entity.definitions.model.search.SearchProfiles;
import eu.europeana.entity.definitions.model.vocabulary.EntityTypes;
import eu.europeana.entity.definitions.model.vocabulary.SuggestAlgorithmTypes;
import eu.europeana.entity.definitions.model.vocabulary.WebEntityConstants;
import eu.europeana.entity.web.exception.ParamValidationException;
import eu.europeana.entity.web.exception.authentication.EntityAuthenticationException;
import eu.europeana.entity.web.jsonld.EntityResultsPageSerializer;

public abstract class BaseRest{

	public BaseRest() {
		super();
	}

	/**
	 * This method is used for validation of the provided api key
	 * @param wsKey
	 * @throws EntityAuthenticationException
	 */
	protected void validateApiKey(String wsKey) throws EntityAuthenticationException {
		// throws exception if the wskey is not found
		if (wsKey == null)
			throw new EntityAuthenticationException(I18nConstants.MISSING_APIKEY, I18nConstants.MISSING_APIKEY, null, HttpStatus.UNAUTHORIZED);
		if (StringUtils.isEmpty(wsKey))
			throw new EntityAuthenticationException(null, I18nConstants.EMPTY_APIKEY, null);
		if (!wsKey.equals("apidemo"))
			throw new EntityAuthenticationException(null, I18nConstants.INVALID_APIKEY,  new String[]{wsKey});
	}

	/**
	 * This method returns the json-ld serialization for the given results page, according to the specifications of the provided search profile
	 * @param resPage
	 * @param profile
	 * @return
	 * @throws JsonProcessingException
	 */
	protected String searializeResultsPage(ResultsPage<? extends Entity> resPage, SearchProfiles profile)
			throws JsonProcessingException {
		ResultsPageSerializer<? extends Entity> serializer = new EntityResultsPageSerializer<>(resPage,
				ContextTypes.ENTITY.getJsonValue(), CommonLdConstants.RESULT_PAGE);
		String profileVal = (profile == null)? null : profile.name(); 
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
	 * This method splits the list of values provided as concatenated string to the corresponding array representation 
	 * @param requestParam
	 * @return
	 */
	protected String[] toArray(String requestParam){
		if(StringUtils.isEmpty(requestParam))
			return null;
		String[] array = StringUtils.splitByWholeSeparator(requestParam, ",");
		return StringUtils.stripAll(array);	
	}
	
	/**
	 * This method verifies if the provided scope parameter is a valid one
	 * @param scope
	 * @return
	 * @throws ParamValidationException
	 */
	protected String validateScopeParam(String scope) throws ParamValidationException {
		if (StringUtils.isBlank(scope))
			return null;
				
		if (!WebEntityConstants.PARAM_SCOPE_EUROPEANA.equalsIgnoreCase(scope))
			throw new ParamValidationException(I18nConstants.INVALID_PARAM_VALUE,
					WebEntityConstants.QUERY_PARAM_SCOPE, scope);
		
		return WebEntityConstants.PARAM_SCOPE_EUROPEANA;
	}

	/**
	 * This method verifies that the provided text parameter is a valid one.
	 * It should not contain field names e.g. "who:mozart"
	 * @param text
	 * @return validated text 
	 * @throws ParamValidationException
	 */
	protected String validateTextParam(String text) throws ParamValidationException {
		if (StringUtils.isBlank(text)) {
			return null;
		}
				
		if (text.contains(WebEntityConstants.FIELD_DELIMITER)) {
			throw new ParamValidationException(I18nConstants.INVALID_PARAM_VALUE,
					WebEntityConstants.QUERY_PARAM_TEXT, text);
		}
		
		return text;
	}

	/**
	 * This method verifies if the provided algorithm parameter is a valid one
	 * @param algorithm
	 * @return validated algorithm
	 * @throws ParamValidationException
	 */
	protected SuggestAlgorithmTypes validateAlgorithmParam(String algorithm) throws ParamValidationException {
		try {
			return SuggestAlgorithmTypes.getByName(algorithm);
		} catch(Exception e) {
			throw new ParamValidationException(I18nConstants.INVALID_PARAM_VALUE,
					WebEntityConstants.QUERY_PARAM_ALGORITHM, algorithm);
		}
	}
}