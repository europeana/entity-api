package eu.europeana.entity.utils.jsonld;

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
import eu.europeana.entity.definitions.model.Organization;
import eu.europeana.entity.definitions.model.Place;
import eu.europeana.entity.definitions.model.Timespan;
import eu.europeana.entity.definitions.model.impl.BaseEntity;
import eu.europeana.entity.definitions.model.vocabulary.AgentSolrFields;
import eu.europeana.entity.definitions.model.vocabulary.ConceptSolrFields;
import eu.europeana.entity.definitions.model.vocabulary.EntityTypes;
import eu.europeana.entity.definitions.model.vocabulary.OrganizationSolrFields;
import eu.europeana.entity.definitions.model.vocabulary.WebEntityConstants;
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
			ldResource.putProperty(createWikimediaResource(
					entity.getDepiction(), WebEntityFields.DEPICTION));	
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

	private JsonLdProperty createWikimediaResource(String wikimediaCommonsId, String field) {
		
		JsonLdProperty depictionProperty = new JsonLdProperty(field);
		JsonLdPropertyValue depictionValue = new JsonLdPropertyValue();
		
		depictionValue.putProperty(new JsonLdProperty(WebEntityFields.ID, wikimediaCommonsId));
		assert wikimediaCommonsId.contains("Special:FilePath/");
		String sourceValue = wikimediaCommonsId.replace("Special:FilePath/", "File:");
		depictionValue.putProperty(new JsonLdProperty(WebEntityFields.SOURCE, sourceValue));
		
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
		putMapOfStringProperty(WebEntityFields.DESCRIPTION, entity.getDescription(), OrganizationSolrFields.DC_DESCRIPTION, ldResource);
		
		putMapOfStringListProperty(WebEntityFields.ACRONYM, entity.getAcronym(), 
				OrganizationSolrFields.EDM_ACRONYM, ldResource);		
		
		if (!StringUtils.isEmpty(entity.getLogo())){ 			
			ldResource.putProperty(createWikimediaResource(
					entity.getLogo(), WebEntityFields.FOAF_LOGO));	
		}
	
		if (!StringUtils.isEmpty(entity.getHomepage())) 			
			ldResource.putProperty(WebEntityFields.FOAF_HOMEPAGE, entity.getHomepage());
		
		if(entity.getPhone() != null)
			putListProperty(WebEntityFields.FOAF_PHONE, entity.getPhone(), jsonLdResource);
		if(entity.getMbox() != null)
			putListProperty(WebEntityFields.FOAF_MBOX, entity.getMbox(), jsonLdResource);
				
		if(entity.getEuropeanaRole() != null){
			//"en" is mandatory
			List<String> europeanaRole = entity.getEuropeanaRole().get(OrganizationSolrFields.EUROPEANA_ROLE_EN);
			putListProperty(WebEntityFields.EUROPEANA_ROLE, europeanaRole, ldResource);
		}			
		
		if(entity.getOrganizationDomain() != null){
			//"en" is mandatory
			List<String> europeanaDomain = entity.getOrganizationDomain().get(OrganizationSolrFields.ORGANIZATION_DOMAIN_EN);
			putListProperty(WebEntityFields.ORGANIZATION_DOMAIN, 
					europeanaDomain, ldResource);	
		}
		
		if(entity.getGeographicLevel() != null){
			//"en" is mandatory
			String geoLevel = entity.getGeographicLevel().get(OrganizationSolrFields.GEOGRAPHIC_LEVEL_EN);
			ldResource.putProperty(WebEntityFields.GEOGRAPHIC_LEVEL, geoLevel);
		}
		
		if (!StringUtils.isEmpty(entity.getCountry())) 			
			ldResource.putProperty(WebEntityFields.COUNTRY, entity.getCountry());
		
		putAddressProperty(entity, ldResource);
				
	}

	private void putAddressProperty(Organization entity, JsonLdResource ldResource) {
		
		if(StringUtils.isEmpty(entity.getLocality()))
			return;
		
		//build address object (the (json) value of the hasAddress property)
		JsonLdPropertyValue vcardAddress = new JsonLdPropertyValue(); 
		//id is mapped to rdf:about
		vcardAddress.putProperty(new JsonLdProperty(WebEntityFields.ID, entity.getHasAddress()));
		vcardAddress.putProperty(new JsonLdProperty(WebEntityFields.TYPE, WebEntityFields.ADDRESS_TYPE));
		
		if (!StringUtils.isEmpty(entity.getStreetAddress())) 			
			vcardAddress.putProperty(
					new JsonLdProperty(WebEntityFields.STREET_ADDRESS, entity.getStreetAddress()));
		if (!StringUtils.isEmpty(entity.getLocality())) 			
			vcardAddress.putProperty(
					new JsonLdProperty(WebEntityFields.LOCALITY, entity.getLocality()));
		if (!StringUtils.isEmpty(entity.getRegion())) 			
			vcardAddress.putProperty(
					new JsonLdProperty(WebEntityFields.REGION, entity.getRegion()));
		if (!StringUtils.isEmpty(entity.getPostalCode())) 			
			vcardAddress.putProperty(
					new JsonLdProperty(WebEntityFields.POSTAL_CODE, entity.getPostalCode()));
		if (!StringUtils.isEmpty(entity.getCountryName())) 			
			vcardAddress.putProperty(
					new JsonLdProperty(WebEntityFields.COUNTRY_NAME, entity.getCountryName()));
		if (!StringUtils.isEmpty(entity.getPostBox())) 			
			vcardAddress.putProperty(
					new JsonLdProperty(WebEntityFields.POST_OFFICE_BOX, entity.getPostBox()));
		
		if (!StringUtils.isEmpty(entity.getHasGeo())) 			
			vcardAddress.putProperty(
					new JsonLdProperty(WebEntityFields.HAS_GEO, getGeoUri(entity.getHasGeo())));
		
		JsonLdProperty hasAddress = new JsonLdProperty(WebEntityFields.HAS_ADDRESS);
		hasAddress.addValue(vcardAddress);
		ldResource.putProperty(hasAddress);
	}

	protected String getGeoUri(String latLon){
		return WebEntityConstants.PROTOCOL_GEO + latLon;
	}
	
	private void putBaseEntityProperties(BaseEntity entity, JsonLdResource jsonLdResource) {
		// COMMON Entity PROPERTIES?
		putStringArrayProperty(WebEntityFields.IS_PART_OF, entity.getIsPartOf(), jsonLdResource);
		putStringArrayProperty(WebEntityFields.HAS_PART, entity.getHasPart(), jsonLdResource);
	}

	public JsonLdResource getLdResource() {
		return ldResource;
	}
}
