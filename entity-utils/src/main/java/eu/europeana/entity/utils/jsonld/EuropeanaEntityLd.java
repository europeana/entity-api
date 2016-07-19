package eu.europeana.entity.utils.jsonld;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.stanbol.commons.jsonld.JsonLd;
import org.apache.stanbol.commons.jsonld.JsonLdProperty;
import org.apache.stanbol.commons.jsonld.JsonLdPropertyValue;
import org.apache.stanbol.commons.jsonld.JsonLdResource;

import eu.europeana.entity.definitions.model.Agent;
import eu.europeana.entity.definitions.model.Concept;
import eu.europeana.entity.definitions.model.vocabulary.EntityTypes;
import eu.europeana.entity.definitions.model.vocabulary.SkosAgentSolrFields;
import eu.europeana.entity.definitions.model.vocabulary.SkosConceptSolrFields;

public class EuropeanaEntityLd extends JsonLd {

	public EuropeanaEntityLd(Concept entity) {
		setEntity(entity);
	}

	public void setEntity(Concept entity) {
		setUseTypeCoercion(false);
		setUseCuries(true);
		// addNamespacePrefix(WebAnnotationFields.OA_CONTEXT,
		// WebAnnotationFields.OA);
		// TODO: verify if the following check is needed
		// if(isApplyNamespaces())
		setUsedNamespaces(namespacePrefixMap);

		JsonLdResource jsonLdResource = new JsonLdResource();
		jsonLdResource.setSubject("");
		jsonLdResource.putProperty(WebEntityFields.CONTEXT, WebEntityFields.ENTITY_CONTEXT);

		// EntityProperties
		jsonLdResource.putProperty(WebEntityFields.AT_ID, entity.getEntityId());
		jsonLdResource.putProperty(WebEntityFields.AT_TYPE, entity.getInternalType());
		putStringProperty(WebEntityFields.IDENTIFIER, entity.getIdentifier(), jsonLdResource);
		
		// SKOS_Properties
		putMapOfStringListProperty(WebEntityFields.PREF_LABEL, entity.getPrefLabel(), SkosConceptSolrFields.PREF_LABEL, jsonLdResource);
		putMapOfStringListProperty(WebEntityFields.ALT_LABEL, entity.getAltLabel(), SkosConceptSolrFields.ALT_LABEL, jsonLdResource);
		putMapOfStringListProperty(WebEntityFields.NOTE, entity.getNote(), SkosConceptSolrFields.NOTE, jsonLdResource);
		putMapOfStringListProperty(WebEntityFields.NOTATION, entity.getNotation(), SkosConceptSolrFields.NOTATION, jsonLdResource);

		putStringArrayProperty(WebEntityFields.RELATED, entity.getRelated(), jsonLdResource);
		putStringArrayProperty(WebEntityFields.BROADER, entity.getBroader(), jsonLdResource);
		putStringArrayProperty(WebEntityFields.NARROWER, entity.getNarrower(), jsonLdResource);
		putStringArrayProperty(WebEntityFields.EXACT_MATCH, entity.getExactMatch(), jsonLdResource);
		putStringArrayProperty(WebEntityFields.CLOSE_MATCH, entity.getCloseMatch(), jsonLdResource);
		putStringArrayProperty(WebEntityFields.BROAD_MATCH, entity.getBroadMatch(), jsonLdResource);
		putStringArrayProperty(WebEntityFields.NARROW_MATCH, entity.getNarrowMatch(), jsonLdResource);
		putStringArrayProperty(WebEntityFields.RELATED_MATCH, entity.getRelatedMatch(), jsonLdResource);
		putStringArrayProperty(WebEntityFields.SAME_AS, entity.getSameAs(), jsonLdResource);

		// RDF properties
		jsonLdResource.putProperty(WebEntityFields.RDF_ABOUT, entity.getAbout());
		
		// specific properties (by entity type)
		putSpecificProperties(entity, jsonLdResource);

		put(jsonLdResource);

	}

	private void putSpecificProperties(Concept entity, JsonLdResource jsonLdResource) {

		EntityTypes entityType = EntityTypes.getByInternalType(entity.getInternalType());

		switch (entityType) {
		case Agent:

			putCommonEntityProperties((Agent) entity, jsonLdResource);

			putAgentSpecificProperties((Agent) entity, jsonLdResource);
			break;

		default:
			break;
		}

	}

	private void putCommonEntityProperties(Agent entity, JsonLdResource jsonLdResource) {
		// COMMON Entity PROPERTIES
		putStringArrayProperty(WebEntityFields.IS_PART_OF, entity.getIsPartOf(), jsonLdResource);
		putStringArrayProperty(WebEntityFields.IS_RELATED_TO, entity.getIsRelatedTo(), jsonLdResource);
		putStringArrayProperty(WebEntityFields.HAS_PART, entity.getHasPart(), jsonLdResource);
	}

	private void putAgentSpecificProperties(Agent entity, JsonLdResource jsonLdResource) {
		// putAgentStringList(WebEntityFields.TEXT, ((Agent) entity).getText(),
		// jsonLdResource);
		putMapOfStringProperty(WebEntityFields.NAME, entity.getName(), SkosAgentSolrFields.NAME, jsonLdResource);
		putMapOfReferencesProperty(WebEntityFields.BIOGRAPHICAL_INFORMATION,
					entity.getBiographicalInformation(), SkosAgentSolrFields.BIOGRAPHICAL_INFORMATION, jsonLdResource);
		putMapOfReferencesProperty(WebEntityFields.PROFESSION_OR_OCCUPATION,
				entity.getProfessionOrOccupation(), SkosAgentSolrFields.PROFESSION_OR_OCCUPATION, jsonLdResource);
	
		putStringArrayProperty(WebEntityFields.DATE_OF_DEATH, entity.getDateOfDeath(), jsonLdResource);
		putStringArrayProperty(WebEntityFields.DATE_OF_BIRTH, entity.getDateOfBirth(), jsonLdResource);
		putStringArrayProperty(WebEntityFields.BEGIN, entity.getBegin(), jsonLdResource);
		putStringArrayProperty(WebEntityFields.END, entity.getEnd(), jsonLdResource);

		putMapOfReferencesProperty(WebEntityFields.PLACE_OF_BIRTH, entity.getPlaceOfBirth(), 
				SkosAgentSolrFields.PLACE_OF_BIRTH, jsonLdResource);

		putMapOfReferencesProperty(WebEntityFields.PLACE_OF_DEATH,
					entity.getPlaceOfDeath(), SkosAgentSolrFields.PLACE_OF_DEATH, jsonLdResource);

	}

	/**
	 * TODO: move to JSONLD
	 * @param propName
	 * @param mapOfReferences
	 * @param mapKeyPrefix
	 * @param jsonLdResource
	 */
	private void putMapOfReferencesProperty(String propName, Map<String, List<String>> mapOfReferences,
			String mapKeyPrefix, JsonLdResource jsonLdResource) {
		if (mapOfReferences != null && !mapOfReferences.isEmpty()) {
			jsonLdResource.putProperty(buildMapOfEntityReferenceProperty(propName,
					mapOfReferences, mapKeyPrefix));
		}
	}

	/**
	 * TODO: move to JSONLD
	 * @param propName
	 * @param mapOfString
	 * @param mapKeyPrefix
	 * @param jsonLdResource
	 */
	private void putMapOfStringProperty(String propName, Map<String, String> mapOfString, String mapKeyPrefix,
			JsonLdResource jsonLdResource) {
		if (mapOfString != null && !mapOfString.isEmpty()) {
			jsonLdResource.putProperty(
					buildMapOfStringsProperty(propName, mapOfString, mapKeyPrefix));
		}
	}

	/**
	 * TODO: move to AnnotationLd
	 * @param fieldName
	 * @param list
	 * @param jsonLdResource
	 */
	private void putListProperty(String fieldName, List<String> list, JsonLdResource jsonLdResource) {
		if (list != null) {
			String[] array = list.toArray(new String[0]);
			putStringArrayProperty(fieldName, array, jsonLdResource);
		}
	}

	/**
	 * TODO: move to JsonLd 
	 * @param fieldName
	 * @param array
	 * @param jsonLdResource
	 */
	private void putStringArrayProperty(String fieldName, String[] array, JsonLdResource jsonLdResource) {
		JsonLdProperty arrayProperty = buildArrayProperty(fieldName, array);
		if(arrayProperty != null)
			jsonLdResource.putProperty(arrayProperty);
	}

	/**
	 * TODO: move to json ld
	 * @param fieldName
	 * @param list
	 * @param jsonLdResource
	 */
	private void putDateList(String fieldName, List<Date> list, JsonLdResource jsonLdResource) {
		if (list != null) {
			List<String> stringList = convertDateListToStringList(list);
			putListProperty(fieldName, stringList, jsonLdResource);
		}
	}

	private List<String> convertDateListToStringList(List<Date> dateList) {

		List<String> stringList = new ArrayList<String>();

		// DateFormat simpleDateFormat = new SimpleDateFormat("yyyy");
		for (Object date : dateList) {
			stringList.add(date.toString());
			// for (Date date : dateList) {
			// stringList.add(simpleDateFormat.format(date));
		}

		return stringList;
	}

	/**
	 * move to JsonLd class
	 * 
	 * @param propertyName
	 * @param values
	 * @param solrFieldPrefix
	 * @return
	 * @deprecated
	 */
	protected JsonLdProperty buildMapOfStringsProperty(String propertyName, Map<String, String> values,
			String solrFieldPrefix) {

		if (values == null)
			return null;

		String key;
		// remove the key prefix e.g. "prefLabel" + "."
		int prefixLength = solrFieldPrefix.length() + 1;

		JsonLdProperty mapProperty = new JsonLdProperty(propertyName);
		JsonLdPropertyValue mapPropertyValue = new JsonLdPropertyValue();
		JsonLdProperty entryProperty;

		for (Map.Entry<String, String> entry : values.entrySet()) {
			key = entry.getKey();
			if (solrFieldPrefix != null) {
				key = key.substring(prefixLength);
			}

			entryProperty = new JsonLdProperty(key);
			entryProperty.addSingleValue(entry.getValue());

			mapPropertyValue.putProperty(entryProperty);
		}

		mapProperty.addValue(mapPropertyValue);
		return mapProperty;
	}

	/**
	 * build appropriate property representation for string arrays
	 * 
	 * @param propertyName
	 * @param valueList
	 * @return
	 */
	protected JsonLdProperty buildMapOfEntityReferenceProperty(String propertyName, Map<String, List<String>> values,
			String solrFieldPrefix) {

		if (values == null)
			return null;

		String language;
		// remove the key prefix e.g. "prefLabel" + "."
		int prefixLength = solrFieldPrefix.length() + 1;

		JsonLdProperty mainProperty = new JsonLdProperty(propertyName);
		// we don't know how many entries in advance
		List<JsonLdPropertyValue> references = new ArrayList<JsonLdPropertyValue>();

		JsonLdPropertyValue referenceValue;
		JsonLdProperty referenceProperty;
		JsonLdPropertyValue multilingualValue;

		// build values and ad to references list
		for (Map.Entry<String, List<String>> entry : values.entrySet()) {
			language = entry.getKey();
			if (solrFieldPrefix != null) {
				language = language.substring(prefixLength);
			}

			// for each entry
			for (String listEntry : entry.getValue()) {
				if (isUrl(listEntry)) {
					referenceProperty = new JsonLdProperty("@id", listEntry);
					referenceValue = new JsonLdPropertyValue();
					referenceValue.putProperty(referenceProperty);
					references.add(referenceValue);
				} else {
					JsonLdProperty langProp = new JsonLdProperty("@language", language);
					JsonLdProperty valueProp = new JsonLdProperty("@value", listEntry);
					multilingualValue = new JsonLdPropertyValue();
					multilingualValue.putProperty(langProp);
					multilingualValue.putProperty(valueProp);
					references.add(multilingualValue);
					// mapPropertyValue.putProperty(multilingualProperty);
				}
			}
		}

		// serialize references list
		if (references.size() == 1) {
			mainProperty.addValue(references.get(0));
		} else {

			for (JsonLdPropertyValue jsonLdProperty : references) {
				// propValue = new JsonLdPropertyValue();
				// propValue.putProperty(jsonLdProperty);

				mainProperty.addValue(jsonLdProperty);
			}

		}

		return mainProperty;
	}
	
	/**
	 * TODO: move to JsonLd
	 * @param propName
	 * @param mapOfStringList
	 * @param mapKeyPrefix
	 * @param jsonLdResource
	 */
	private void putMapOfStringListProperty(String propName, Map<String, List<String>> mapOfStringList,
			String mapKeyPrefix, JsonLdResource jsonLdResource) {
		if (mapOfStringList != null && !mapOfStringList.isEmpty()) {
			jsonLdResource.putProperty(buildMapProperty(propName, mapOfStringList,
					mapKeyPrefix));
		}
	}

	/**
	 * TODO: move to JsonLd
	 * @param propName
	 * @param propValue
	 * @param jsonLdResource
	 */
	private void putStringProperty(String propName, String propValue, JsonLdResource jsonLdResource) {
		if (StringUtils.isNotEmpty(propValue)) {
			jsonLdResource.putProperty(propName, propValue);
		}
	}

	/**
	 * TODO move to AnnotationLd
	 * @param listEntry
	 * @return
	 */
	private boolean isUrl(String listEntry) {
		try {
			new URL(listEntry);
			return true;
		} catch (Exception e) {
			// return false;
		}
		return false;
	}

}
