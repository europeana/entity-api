package eu.europeana.entity.web.jsonld;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.stanbol.commons.jsonld.JsonLd;
import org.apache.stanbol.commons.jsonld.JsonLdProperty;
import org.apache.stanbol.commons.jsonld.JsonLdPropertyValue;
import org.apache.stanbol.commons.jsonld.JsonLdResource;

import eu.europeana.entity.definitions.model.search.result.ResultSet;
import eu.europeana.entity.definitions.model.vocabulary.EntityTypes;
import eu.europeana.entity.web.controller.WebEntityConstants;
import eu.europeana.entity.web.model.view.EntityPreview;


public class SuggestionSetSerializer extends JsonLd {

	// private static final Logger logger =
	// LoggerFactory.getLogger(ConceptLd.class);

	ResultSet<? extends EntityPreview> conceptSet;

	public ResultSet<? extends EntityPreview> getConceptSet() {
		return conceptSet;
	}

	public void setConceptSet(ResultSet<? extends EntityPreview> conceptSet) {
		this.conceptSet = conceptSet;
	}


	/**
	 * @param conceptSet
	 */
	public SuggestionSetSerializer(ResultSet<? extends EntityPreview> conceptSet) {
		setConceptSet(conceptSet);
	}

	/**
	 * Adds the given concept to this JsonLd object using the resource's
	 * subject as key. If the key is NULL and there does not exist a resource
	 * with an empty String as key the resource will be added using an empty
	 * String ("") as key.
	 * 
	 * @param concept
	 */
	public String serialize() {

		setUseTypeCoercion(false);
		setUseCuries(true);
		setUsedNamespaces(namespacePrefixMap);

		JsonLdResource jsonLdResource = new JsonLdResource();
		jsonLdResource.setSubject("");
		jsonLdResource.putProperty(WebEntityConstants.AT_CONTEXT, WebEntityConstants.WA_CONTEXT);
		String[] oaType = new String[] { "BasicContainer", "Collection" };
		jsonLdResource.putProperty(buildArrayProperty(WebEntityConstants.AT_TYPE, oaType));
		jsonLdResource.putProperty(WebEntityConstants.TOTAL_ITEMS, getConceptSet().getResultSize());

		serializeItems(jsonLdResource);
		
		put(jsonLdResource);

		return toString(4);
	}

	
	
	protected void serializeItems(JsonLdResource jsonLdResource) {

		//do not serialize if empty
		if(getConceptSet().isEmpty())
			return;
		
		JsonLdProperty containsProperty = new JsonLdProperty(WebEntityConstants.CONTAINS);
		JsonLdPropertyValue propertyValue;
		
		for (EntityPreview entityPreview : getConceptSet().getResults()) {
				propertyValue = buildConceptViewPropertyValue(entityPreview);
				containsProperty.addValue(propertyValue);
		}
		
		jsonLdResource.putProperty(containsProperty);
			
	}

	private JsonLdPropertyValue buildConceptViewPropertyValue(EntityPreview entityPreview) {
		
		JsonLdPropertyValue entityPreviewPropValue = new JsonLdPropertyValue();
		entityPreviewPropValue.putProperty(new JsonLdProperty(WebEntityConstants.AT_ID, entityPreview.getEntityId()));
		entityPreviewPropValue.putProperty(new JsonLdProperty(WebEntityConstants.PREF_LABEL, entityPreview.getPreferredLabel()));
		
		String typeUri = entityPreview.getType();
		EntityTypes entityType = EntityTypes.getByHttpUri(typeUri);
		
		if(entityType!= null){
			entityPreviewPropValue.putProperty(new JsonLdProperty(WebEntityConstants.AT_TYPE, entityType.getHttpUri()));
			
			switch (entityType){
				case Concept:
					//add top concept, when available
					break;
				case Agent:
					if(entityPreview.getBirthDate() != null)
						entityPreviewPropValue.putProperty(new JsonLdProperty(WebEntityConstants.BIRTH_DATE, 
								convertDateToStr(entityPreview.getBirthDate())));
					
					if(entityPreview.getDeathDate() != null)
						entityPreviewPropValue.putProperty(new JsonLdProperty(WebEntityConstants.DEATH_DATE, 
								convertDateToStr(entityPreview.getDeathDate())));
				
					if(entityPreview.getRole() != null)
						entityPreviewPropValue.putProperty(new JsonLdProperty(WebEntityConstants.AT_TYPE, 
								entityType.getHttpUri()));
					
					break;
					
				case Place:
					if(entityPreview.getLatitude() != null)
						entityPreviewPropValue.putProperty(new JsonLdProperty(WebEntityConstants.GEO_LAT, 
								entityPreview.getLatitude()));
					
					if(entityPreview.getLongitude() != null)
						entityPreviewPropValue.putProperty(new JsonLdProperty(WebEntityConstants.GEO_LONG, 
								entityPreview.getLongitude()));
					break;
				
				case Timespan:
					if(entityPreview.getTimeSpanStart() != null)
						entityPreviewPropValue.putProperty(new JsonLdProperty(WebEntityConstants.TIME_SPAN_FROM, 
								entityPreview.getTimeSpanStart()));
					
					if(entityPreview.getTimeSpanEnd() != null)
						entityPreviewPropValue.putProperty(new JsonLdProperty(WebEntityConstants.TIME_SPAN_TO, 
								entityPreview.getTimeSpanEnd()));	
					break;
					
				default:
					break;
			}
			
			entityPreviewPropValue.putProperty(new JsonLdProperty(WebEntityConstants.AT_ID, entityPreview.getEntityId()));
		}
			
		return entityPreviewPropValue;
	}

	 public String convertDateToStr(Date date) {
	    	DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	    	return df.format(date);    
	 }

}
