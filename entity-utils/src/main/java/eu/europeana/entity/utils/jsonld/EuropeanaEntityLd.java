package eu.europeana.entity.utils.jsonld;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.stanbol.commons.jsonld.JsonLd;
import org.apache.stanbol.commons.jsonld.JsonLdProperty;
import org.apache.stanbol.commons.jsonld.JsonLdPropertyValue;
import org.apache.stanbol.commons.jsonld.JsonLdResource;

import eu.europeana.entity.definitions.exceptions.UnsupportedEntityTypeException;
import eu.europeana.entity.definitions.model.Agent;
import eu.europeana.entity.definitions.model.Concept;
import eu.europeana.entity.definitions.model.Entity;
import eu.europeana.entity.definitions.model.Place;
import eu.europeana.entity.definitions.model.Timespan;
import eu.europeana.entity.definitions.model.impl.BaseEntity;
import eu.europeana.entity.definitions.model.vocabulary.AgentSolrFields;
import eu.europeana.entity.definitions.model.vocabulary.ConceptSolrFields;
import eu.europeana.entity.definitions.model.vocabulary.EntityTypes;
import eu.europeana.entity.definitions.model.vocabulary.WebEntityFields;

public class EuropeanaEntityLd extends JsonLd {

	public EuropeanaEntityLd(Entity entity) throws UnsupportedEntityTypeException {
		super();
		setPropOrderComparator(new EntityJsonComparator());
		setEntity(entity);
	}

	public void setEntity(Entity entity) throws UnsupportedEntityTypeException {
		setUseTypeCoercion(false);
		setUseCuries(true);
		setUsedNamespaces(namespacePrefixMap);

		JsonLdResource jsonLdResource = new JsonLdResource();
		jsonLdResource.setSubject("");
		jsonLdResource.putProperty(WebEntityFields.CONTEXT, WebEntityFields.ENTITY_CONTEXT);

		//common EntityProperties
		jsonLdResource.putProperty(WebEntityFields.ID, entity.getEntityId());
		jsonLdResource.putProperty(WebEntityFields.TYPE, entity.getInternalType());
		putStringArrayProperty(WebEntityFields.IDENTIFIER, entity.getIdentifier(), jsonLdResource);
		putStringArrayProperty(WebEntityFields.SAME_AS, entity.getSameAs(), jsonLdResource);
		//jsonLdResource.putProperty(WebEntityFields.RDF_ABOUT, entity.getAbout());
		putStringArrayProperty(WebEntityFields.IS_RELATED_TO, entity.getIsRelatedTo(), jsonLdResource);

		if (!StringUtils.isEmpty(entity.getDepiction())) {	
			jsonLdResource.putProperty(createDepiction(entity));			
		}
		
		
		//common SKOS_Properties
//		putMapOfStringListProperty(WebEntityFields.PREF_LABEL, entity.getPrefLabel(), ConceptSolrFields.PREF_LABEL, jsonLdResource);
		putMapOfStringProperty(WebEntityFields.PREF_LABEL, entity.getPrefLabel(), ConceptSolrFields.PREF_LABEL, jsonLdResource);
		putMapOfStringListProperty(WebEntityFields.ALT_LABEL, entity.getAltLabel(), ConceptSolrFields.ALT_LABEL, jsonLdResource);
		putMapOfStringListProperty(WebEntityFields.NOTE, entity.getNote(), ConceptSolrFields.NOTE, jsonLdResource);

		// specific properties (by entity type)
		putSpecificProperties(entity, jsonLdResource);

		put(jsonLdResource);

	}

	private JsonLdProperty createDepiction(Entity entity) {
		JsonLdProperty depictionProperty = new JsonLdProperty(WebEntityFields.DEPICTION);
		JsonLdPropertyValue depictionValue = new JsonLdPropertyValue();
		
		depictionValue.putProperty(new JsonLdProperty(WebEntityFields.ID, entity.getDepiction()));
		assert entity.getDepiction().contains("Special:FilePath/");
		String sourceValue = entity.getDepiction().replace("Special:FilePath/", "File:");
		depictionValue.putProperty(new JsonLdProperty(WebEntityFields.DEPICTION_SOURCE, sourceValue));
		
		depictionProperty.addValue(depictionValue);		
		return depictionProperty;
	}

	private void putConceptSpecificProperties(Concept entity, JsonLdResource jsonLdResource) {
		putMapOfStringListProperty(WebEntityFields.NOTATION, entity.getNotation(), ConceptSolrFields.NOTATION, jsonLdResource);
		putStringArrayProperty(WebEntityFields.RELATED, entity.getRelated(), jsonLdResource);
		putStringArrayProperty(WebEntityFields.BROADER, entity.getBroader(), jsonLdResource);
		putStringArrayProperty(WebEntityFields.NARROWER, entity.getNarrower(), jsonLdResource);
		putStringArrayProperty(WebEntityFields.EXACT_MATCH, entity.getExactMatch(), jsonLdResource);
		putStringArrayProperty(WebEntityFields.CLOSE_MATCH, entity.getCloseMatch(), jsonLdResource);
		putStringArrayProperty(WebEntityFields.BROAD_MATCH, entity.getBroadMatch(), jsonLdResource);
		putStringArrayProperty(WebEntityFields.NARROW_MATCH, entity.getNarrowMatch(), jsonLdResource);
		putStringArrayProperty(WebEntityFields.RELATED_MATCH, entity.getRelatedMatch(), jsonLdResource);
	}

	private void putSpecificProperties(Entity entity, JsonLdResource jsonLdResource) throws UnsupportedEntityTypeException {

		EntityTypes entityType = EntityTypes.getByInternalType(entity.getInternalType());

		switch (entityType) {
		case Concept:
			putConceptSpecificProperties((Concept) entity, jsonLdResource);
			break;

		case Agent:
			putAgentSpecificProperties((Agent) entity, jsonLdResource);
			break;
		
		case Place:
			putPlaceSpecificProperties((Place) entity, jsonLdResource);
			break;
			
		case Timespan:
			putTimespanSpecificProperties((Timespan) entity, jsonLdResource);
			break;
			
		default:
			break;
		}

	}

	

	private void putTimespanSpecificProperties(Timespan entity, JsonLdResource jsonLdResource) {
		//TODO: in corelib these are maps of string list
		putStringArrayProperty(WebEntityFields.BEGIN, entity.getBegin(), jsonLdResource);
		putStringArrayProperty(WebEntityFields.END, entity.getEnd(), jsonLdResource);
		
	}

	private void putPlaceSpecificProperties(Place entity, JsonLdResource jsonLdResource) {
		putBaseEntityProperties((BaseEntity)entity, jsonLdResource);
		
		if(entity.getLatitude() != null)
			putStringProperty(WebEntityFields.LATITUDE, ""+entity.getLatitude(), jsonLdResource);
		if(entity.getLongitude() != null)
			putStringProperty(WebEntityFields.LONGITUDE, ""+entity.getLongitude(), jsonLdResource);
		if(entity.getAltitude() != null)
			putStringProperty(WebEntityFields.ALTITUDE, ""+entity.getAltitude(), jsonLdResource);
		
		putStringArrayProperty(WebEntityFields.IS_NEXT_IN_SEQUENCE, entity.getIsNextInSequence(), jsonLdResource);
	}

	private void putAgentSpecificProperties(Agent entity, JsonLdResource jsonLdResource) {
		
		putBaseEntityProperties((BaseEntity)entity, jsonLdResource);
		
		//Agent Props
		putMapOfStringProperty(WebEntityFields.NAME, entity.getName(), AgentSolrFields.NAME, jsonLdResource);
		putMapOfReferencesProperty(WebEntityFields.BIOGRAPHICAL_INFORMATION,
					entity.getBiographicalInformation(), AgentSolrFields.BIOGRAPHICAL_INFORMATION, jsonLdResource);
		putMapOfReferencesProperty(WebEntityFields.PROFESSION_OR_OCCUPATION,
				entity.getProfessionOrOccupation(), AgentSolrFields.PROFESSION_OR_OCCUPATION, jsonLdResource);
	
		putStringArrayProperty(WebEntityFields.DATE_OF_DEATH, entity.getDateOfDeath(), jsonLdResource);
		putStringArrayProperty(WebEntityFields.DATE_OF_BIRTH, entity.getDateOfBirth(), jsonLdResource);
		putStringArrayProperty(WebEntityFields.BEGIN, entity.getBegin(), jsonLdResource);
		putStringArrayProperty(WebEntityFields.END, entity.getEnd(), jsonLdResource);

		putMapOfReferencesProperty(WebEntityFields.PLACE_OF_BIRTH, entity.getPlaceOfBirth(), 
				AgentSolrFields.PLACE_OF_BIRTH, jsonLdResource);

		putMapOfReferencesProperty(WebEntityFields.PLACE_OF_DEATH,
					entity.getPlaceOfDeath(), AgentSolrFields.PLACE_OF_DEATH, jsonLdResource);

	}

	private void putBaseEntityProperties(BaseEntity entity, JsonLdResource jsonLdResource) {
		// COMMON Entity PROPERTIES?
		putStringArrayProperty(WebEntityFields.IS_PART_OF, entity.getIsPartOf(), jsonLdResource);
		putStringArrayProperty(WebEntityFields.HAS_PART, entity.getHasPart(), jsonLdResource);
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
	
}
