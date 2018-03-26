package eu.europeana.entity.utils.jsonld;

import org.apache.commons.lang3.StringUtils;
import org.apache.stanbol.commons.jsonld.JsonLd;
import org.apache.stanbol.commons.jsonld.JsonLdProperty;
import org.apache.stanbol.commons.jsonld.JsonLdPropertyValue;
import org.apache.stanbol.commons.jsonld.JsonLdResource;

import eu.europeana.entity.definitions.exceptions.UnsupportedEntityTypeException;
import eu.europeana.entity.definitions.model.Agent;
import eu.europeana.entity.definitions.model.Concept;
import eu.europeana.entity.definitions.model.Entity;
import eu.europeana.entity.definitions.model.Organization;
import eu.europeana.entity.definitions.model.Place;
import eu.europeana.entity.definitions.model.Timespan;
import eu.europeana.entity.definitions.model.impl.BaseEntity;
import eu.europeana.entity.definitions.model.vocabulary.AgentSolrFields;
import eu.europeana.entity.definitions.model.vocabulary.ConceptSolrFields;
import eu.europeana.entity.definitions.model.vocabulary.EntityTypes;
import eu.europeana.entity.definitions.model.vocabulary.WebEntityFields;


public class EuropeanaEntityLd extends JsonLd {

	JsonLdResource ldResource = new JsonLdResource();
	
	public EuropeanaEntityLd(Entity entity) throws UnsupportedEntityTypeException {
		super();
		setPropOrderComparator(new EntityJsonComparator());
		setEntity(entity);
	}

	public JsonLdResource setEntity(Entity entity) throws UnsupportedEntityTypeException {
		setUseTypeCoercion(false);
		setUseCuries(true);
		setUsedNamespaces(namespacePrefixMap);

		ldResource.setSubject("");
		ldResource.putProperty(WebEntityFields.CONTEXT, WebEntityFields.ENTITY_CONTEXT);

		//common EntityProperties
		ldResource.putProperty(WebEntityFields.ID, entity.getEntityId());
		ldResource.putProperty(WebEntityFields.TYPE, entity.getInternalType());
		putStringArrayProperty(WebEntityFields.IDENTIFIER, entity.getIdentifier(), ldResource);
		putStringArrayProperty(WebEntityFields.SAME_AS, entity.getSameAs(), ldResource);
		//jsonLdResource.putProperty(WebEntityFields.RDF_ABOUT, entity.getAbout());
		putStringArrayProperty(WebEntityFields.IS_RELATED_TO, entity.getIsRelatedTo(), ldResource);

		if (!StringUtils.isEmpty(entity.getDepiction())) {	
			ldResource.putProperty(createDepiction(entity));			
		}

		//common SKOS_Properties
//		putMapOfStringListProperty(WebEntityFields.PREF_LABEL, entity.getPrefLabel(), ConceptSolrFields.PREF_LABEL, jsonLdResource);
		putMapOfStringProperty(WebEntityFields.PREF_LABEL, entity.getPrefLabel(), ConceptSolrFields.PREF_LABEL, ldResource);
		putMapOfStringListProperty(WebEntityFields.ALT_LABEL, entity.getAltLabel(), ConceptSolrFields.ALT_LABEL, ldResource);
		putMapOfStringListProperty(WebEntityFields.NOTE, entity.getNote(), ConceptSolrFields.NOTE, ldResource);

		// specific properties (by entity type)
		putSpecificProperties(entity, ldResource);

		put(ldResource);
		
		return ldResource;
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
		case Organization:
			putOrganizationSpecificProperties((Organization) entity, jsonLdResource);
			break;

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

	private void putOrganizationSpecificProperties(Organization entity, JsonLdResource jsonLdResource) {
		
		putBaseEntityProperties((BaseEntity)entity, jsonLdResource);
		
		// Organization properties
		putMapOfReferencesProperty(WebEntityFields.EDM_ACRONYM, entity.getAcronym(), 
				WebEntityFields.EDM_ACRONYM, ldResource);		
		if (!StringUtils.isEmpty(entity.getLogo())) 			
			ldResource.putProperty(WebEntityFields.FOAF_LOGO, entity.getLogo());
		if (!StringUtils.isEmpty(entity.getHomepage())) 			
			ldResource.putProperty(WebEntityFields.FOAF_HOMEPAGE, entity.getHomepage());
		putMapOfReferencesProperty(WebEntityFields.EDM_EUROPEANA_ROLE, entity.getEuropeanaRole(), 
				WebEntityFields.EDM_EUROPEANA_ROLE, ldResource);
		putMapOfStringProperty(WebEntityFields.EDM_ORGANIZATION_DOMAIN, 
				entity.getOrganizationDomain(), WebEntityFields.EDM_ORGANIZATION_DOMAIN, ldResource);
		putMapOfStringProperty(WebEntityFields.EDM_ORGANIZATION_SECTOR, 
				entity.getOrganizationSector(), WebEntityFields.EDM_ORGANIZATION_SECTOR, ldResource);
		putMapOfStringProperty(WebEntityFields.EDM_ORGANIZATION_SCOPE, 
				entity.getOrganizationScope(), WebEntityFields.EDM_ORGANIZATION_SCOPE, ldResource);
		putMapOfStringProperty(WebEntityFields.EDM_GEOGRAPHIC_LEVEL, 
				entity.getGeographicLevel(), WebEntityFields.EDM_GEOGRAPHIC_LEVEL, ldResource);
		if (!StringUtils.isEmpty(entity.getStreetAddress())) 			
			ldResource.putProperty(WebEntityFields.VCARD_STREET, entity.getStreetAddress());
		if (!StringUtils.isEmpty(entity.getCity())) 			
			ldResource.putProperty(WebEntityFields.VCARD_CITY, entity.getCity());
		if (!StringUtils.isEmpty(entity.getPostalCode())) 			
			ldResource.putProperty(WebEntityFields.VCARD_POSTAL_CODE, entity.getPostalCode());
		if (!StringUtils.isEmpty(entity.getCountry())) 			
			ldResource.putProperty(WebEntityFields.VCARD_COUNTRY, entity.getCountry());
		if (!StringUtils.isEmpty(entity.getPostBox())) 			
			ldResource.putProperty(WebEntityFields.VCARD_POST_OFFICE_BOX, entity.getPostBox());
	}

	private void putBaseEntityProperties(BaseEntity entity, JsonLdResource jsonLdResource) {
		// COMMON Entity PROPERTIES?
		putStringArrayProperty(WebEntityFields.IS_PART_OF, entity.getIsPartOf(), jsonLdResource);
		putStringArrayProperty(WebEntityFields.HAS_PART, entity.getHasPart(), jsonLdResource);
	}

	public JsonLdResource getLdResource() {
		return ldResource;
	}
	

//	/**
//	 * TODO: move to json ld
//	 * @param fieldName
//	 * @param list
//	 * @param jsonLdResource
//	 */
//	private void putDateList(String fieldName, List<Date> list, JsonLdResource jsonLdResource) {
//		if (list != null) {
//			List<String> stringList = convertDateListToStringList(list);
//			putListProperty(fieldName, stringList, jsonLdResource);
//		}
//	}

//	private List<String> convertDateListToStringList(List<Date> dateList) {
//
//		List<String> stringList = new ArrayList<String>();
//
//		// DateFormat simpleDateFormat = new SimpleDateFormat("yyyy");
//		for (Object date : dateList) {
//			stringList.add(date.toString());
//			// for (Date date : dateList) {
//			// stringList.add(simpleDateFormat.format(date));
//		}
//
//		return stringList;
//	}
//	
}
