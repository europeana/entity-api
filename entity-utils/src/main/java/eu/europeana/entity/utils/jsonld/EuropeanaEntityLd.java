package eu.europeana.entity.utils.jsonld;

import java.util.List;
import java.util.Map;

import org.apache.stanbol.commons.jsonld.JsonLd;
import org.apache.stanbol.commons.jsonld.JsonLdProperty;
import org.apache.stanbol.commons.jsonld.JsonLdPropertyValue;
import org.apache.stanbol.commons.jsonld.JsonLdResource;

import eu.europeana.entity.definitions.model.Concept;
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

		jsonLdResource.putProperty(WebEntityFields.AT_ID, entity.getEntityId());
		jsonLdResource.putProperty(WebEntityFields.AT_TYPE, entity.getInternalType());

		if (entity.getSameAs() != null)
			jsonLdResource.putProperty(buildArrayProperty(WebEntityFields.SAME_AS, entity.getSameAs()));

		if (entity.getPrefLabel() != null && !entity.getPrefLabel().isEmpty())
			jsonLdResource.putProperty(buildMapProperty(WebEntityFields.PREF_LABEL, entity.getPrefLabel(),
					SkosConceptSolrFields.PREF_LABEL));

		if (entity.getAltLabel() != null && !entity.getAltLabel().isEmpty())
			jsonLdResource.putProperty(
					buildMapProperty(WebEntityFields.ALT_LABEL, entity.getAltLabel(), SkosConceptSolrFields.ALT_LABEL));

		if (entity.getNote() != null && !entity.getNote().isEmpty())
			jsonLdResource
					.putProperty(buildMapProperty(WebEntityFields.NOTE, entity.getNote(), SkosConceptSolrFields.NOTE));

		if(entity.getNotation() != null && !entity.getNotation().isEmpty())
		jsonLdResource.putProperty(
				buildMapProperty(WebEntityFields.NOTATION, entity.getNotation(), SkosConceptSolrFields.NOTATION));

		if (entity.getRelated() != null)
			jsonLdResource.putProperty(buildArrayProperty(WebEntityFields.RELATED, entity.getRelated()));

		if (entity.getBroader() != null)
			jsonLdResource.putProperty(buildArrayProperty(WebEntityFields.BROADER, entity.getBroader()));

		if (entity.getNarrower() != null)
			jsonLdResource.putProperty(buildArrayProperty(WebEntityFields.NARROWER, entity.getNarrower()));

		if (entity.getExactMatch() != null)
			jsonLdResource.putProperty(buildArrayProperty(WebEntityFields.EXACT_MATCH, entity.getExactMatch()));

		if (entity.getCloseMatch() != null)
			jsonLdResource.putProperty(buildArrayProperty(WebEntityFields.CLOSE_MATCH, entity.getCloseMatch()));

		if (entity.getBroadMatch() != null)
			jsonLdResource.putProperty(buildArrayProperty(WebEntityFields.BROAD_MATCH, entity.getBroadMatch()));

		if (entity.getNarrowMatch() != null)
			jsonLdResource.putProperty(buildArrayProperty(WebEntityFields.NARROW_MATCH, entity.getNarrowMatch()));

		if (entity.getRelatedMatch() != null)
			jsonLdResource.putProperty(buildArrayProperty(WebEntityFields.RELATED_MATCH, entity.getRelatedMatch()));

		put(jsonLdResource);

	}

	/**
	 * build appropriate property representation for string arrays
	 * 
	 * @param propertyName
	 * @param valueList
	 * @return
	 */
	protected JsonLdProperty buildMapProperty(String propertyName, Map<String, List<String>> values,
			String solrFieldPrefix) {

		if (values == null)
			return null;

		String key;
		//remove the key prefix e.g. "prefLabel" + "."   
		int prefixLength = solrFieldPrefix.length() +1 ;
		
		JsonLdProperty mapProperty = new JsonLdProperty(propertyName);
		JsonLdPropertyValue mapPropertyValue = new JsonLdPropertyValue();
		JsonLdProperty entryProperty;

		for (Map.Entry<String, List<String>> entry : values.entrySet()) {
			key = entry.getKey();
			if(solrFieldPrefix != null) {
				key = key.substring(prefixLength);
			}
			
			entryProperty = new JsonLdProperty(key);
			for (String listEntry : entry.getValue()) {
				entryProperty.addSingleValue(listEntry);
			}
			
			mapPropertyValue.putProperty(entryProperty);			
		}
		
		mapProperty.addValue(mapPropertyValue);
		return mapProperty;
	}

}
