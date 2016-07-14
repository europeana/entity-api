package eu.europeana.entity.utils.jsonld;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.stanbol.commons.jsonld.JsonLd;
import org.apache.stanbol.commons.jsonld.JsonLdResource;

import eu.europeana.entity.definitions.model.Agent;
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

		jsonLdResource.putProperty(WebEntityFields.RDF_ABOUT, ((Agent) entity).getRdfAbout());
		jsonLdResource.putProperty(WebEntityFields.DERIVED_SCORE, ((Agent) entity).getDerivedScore());
		jsonLdResource.putProperty(WebEntityFields.WIKIPEDIA_CLICKS, ((Agent) entity).getWikipediaClicks());
		putAgentStringList(WebEntityFields.TEXT, ((Agent) entity).getText(), jsonLdResource);
		putAgentStringList(WebEntityFields.BIOGRAPHICAL_INFORMATION, ((Agent) entity).getBiographicalInformation(), jsonLdResource);
		putAgentStringList(WebEntityFields.IS_PART_OF, ((Agent) entity).getIsPartOf(), jsonLdResource);
		putAgentDateList(WebEntityFields.DATE_OF_DEATH, ((Agent) entity).getDateOfDeath(), jsonLdResource);
		putAgentDateList(WebEntityFields.DATE_OF_BIRTH, ((Agent) entity).getDateOfBirth(), jsonLdResource);
		putAgentDateList(WebEntityFields.BEGIN, ((Agent) entity).getBegin(), jsonLdResource);
		putAgentDateList(WebEntityFields.END, ((Agent) entity).getEnd(), jsonLdResource);

		put(jsonLdResource);

	}

	
	private void putAgentStringList(String fieldName, List<String> list, JsonLdResource jsonLdResource) {
		if (list != null) {
			String[] array = list.toArray(new String[0]);
			jsonLdResource.putProperty(buildArrayProperty(fieldName, array));
		}
	}
	

	private void putAgentDateList(String fieldName, List<Date> list, JsonLdResource jsonLdResource) {
		if (list != null) {
			List<String> stringList = convertDateListToStringList(list);
			putAgentStringList(fieldName, stringList, jsonLdResource);
		}
	}
	
	
	private List<String> convertDateListToStringList(List<Date> dateList) {
		
		List<String> stringList = new ArrayList<String>();
		
//		DateFormat simpleDateFormat = new SimpleDateFormat("yyyy");
	    for (Object date : dateList) {
        	stringList.add(date.toString());
//    	    for (Date date : dateList) {
//            	stringList.add(simpleDateFormat.format(date));
	    }	
	    
	    return stringList;
	}
	

}
