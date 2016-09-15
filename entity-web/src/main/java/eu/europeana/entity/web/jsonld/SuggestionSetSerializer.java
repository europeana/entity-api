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
import eu.europeana.entity.web.model.view.AgentPreview;
import eu.europeana.entity.web.model.view.EntityPreview;
import eu.europeana.entity.web.model.view.PlacePreview;
import eu.europeana.entity.web.model.view.TimeSpanPreview;

public class SuggestionSetSerializer extends JsonLd {

	// private static final Logger logger =
	// LoggerFactory.getLogger(ConceptLd.class);

	ResultSet<? extends EntityPreview> entitySet;

	public ResultSet<? extends EntityPreview> getEntitySet() {
		return entitySet;
	}

	public void setConceptSet(ResultSet<? extends EntityPreview> entitySet) {
		this.entitySet = entitySet;
	}

	/**
	 * @param conceptSet
	 */
	public SuggestionSetSerializer(ResultSet<? extends EntityPreview> entitySet) {
		setConceptSet(entitySet);
	}

	/**
	 * Adds the given concept to this JsonLd object using the resource's subject
	 * as key. If the key is NULL and there does not exist a resource with an
	 * empty String as key the resource will be added using an empty String ("")
	 * as key.
	 * 
	 * @param concept
	 */
	public String serialize() {

		setUseTypeCoercion(false);
		setUseCuries(true);
		setUsedNamespaces(namespacePrefixMap);

		JsonLdResource jsonLdResource = new JsonLdResource();
		jsonLdResource.setSubject("");
//		jsonLdResource.putProperty(WebEntityConstants.AT_CONTEXT, WebEntityConstants.WA_CONTEXT);
		
		String[] contextValues = new String[]{WebEntityConstants.LDP_CONTEXT, WebEntityConstants.ENTITY_CONTEXT};
		JsonLdProperty contextProperty = buildArrayProperty(
				WebEntityConstants.AT_CONTEXT, contextValues);
		
		String language = getEntitySet().getLanguage();
		
		if(language != null){
			JsonLdPropertyValue languageProp = new JsonLdPropertyValue();
			languageProp.putProperty(new JsonLdProperty(WebEntityConstants.AT_LANGUAGE, language));
			contextProperty.getValues().add(languageProp);
		}
			
		
		jsonLdResource.putProperty(contextProperty);
		
		//TODO: update annotation LD and add the @language:en to context 
		
		//String[] type = new String[] { "BasicContainer", "Collection" };
		jsonLdResource.putProperty(WebEntityConstants.TYPE, WebEntityConstants.TYPE_BASIC_CONTAINER);
		jsonLdResource.putProperty(WebEntityConstants.TOTAL_ITEMS, getEntitySet().getResultSize());

		serializeItems(jsonLdResource);

		put(jsonLdResource);

		return toString(4);
	}

	protected void serializeItems(JsonLdResource jsonLdResource) {

		// do not serialize if empty
		if (getEntitySet().isEmpty())
			return;

		JsonLdProperty containsProperty = new JsonLdProperty(WebEntityConstants.CONTAINS);
		JsonLdPropertyValue propertyValue;

		for (EntityPreview entityPreview : getEntitySet().getResults()) {
			propertyValue = buildEntityPreviewPropertyValue(entityPreview);
			containsProperty.addValue(propertyValue);
		}

		jsonLdResource.putProperty(containsProperty);

	}

	private JsonLdPropertyValue buildEntityPreviewPropertyValue(EntityPreview entityPreview) {

		JsonLdPropertyValue entityPreviewPropValue = new JsonLdPropertyValue();
		entityPreviewPropValue.putProperty(new JsonLdProperty(WebEntityConstants.ID, entityPreview.getEntityId()));
		entityPreviewPropValue
				.putProperty(new JsonLdProperty(WebEntityConstants.PREF_LABEL, entityPreview.getPreferredLabel()));

		String type = entityPreview.getType();
		EntityTypes entityType = EntityTypes.getByInternalType(type);

		if (entityType != null) {
			entityPreviewPropValue.putProperty(new JsonLdProperty(WebEntityConstants.TYPE, entityType.getInternalType()));

			switch (entityType) {
			case Concept:
				// add top concept, when available
				break;
			case Agent:
				putAgentSpecificProperties((AgentPreview)entityPreview, entityPreviewPropValue);

				break;

			case Place:
				putPlaceSpecificProperties((PlacePreview)entityPreview, entityPreviewPropValue);
				break;

			case Timespan:
				putTimespanSpecificProperties((TimeSpanPreview)entityPreview, entityPreviewPropValue);
				break;

			default:
				break;
			}

			entityPreviewPropValue
					.putProperty(new JsonLdProperty(WebEntityConstants.ID, entityPreview.getEntityId()));
		}

		return entityPreviewPropValue;
	}

	private void putTimespanSpecificProperties(TimeSpanPreview entityPreview,
			JsonLdPropertyValue entityPreviewPropValue) {
		if (entityPreview.getBegin() != null)
			entityPreviewPropValue.putProperty(
					new JsonLdProperty(WebEntityConstants.BEGIN, entityPreview.getBegin()));

		if (entityPreview.getEnd() != null)
			entityPreviewPropValue
					.putProperty(new JsonLdProperty(WebEntityConstants.END, entityPreview.getEnd()));
	}

	//TODO: replace by using the put<Type> methods used in entity serializer
	private void putPlaceSpecificProperties(PlacePreview entityPreview, JsonLdPropertyValue entityPreviewPropValue) {
		if (entityPreview.getCountry() != null)
			entityPreviewPropValue
					.putProperty(new JsonLdProperty(WebEntityConstants.COUNTRY, entityPreview.getCountry()));

		String[] propertyValue = entityPreview.getIsPartOf();
		String propertyName = WebEntityConstants.IS_PART_OF;
		putArrayProperty(entityPreviewPropValue, propertyName, propertyValue);		 
	}
	

	private void putAgentSpecificProperties(AgentPreview entityPreview, JsonLdPropertyValue entityPreviewPropValue) {
		if (entityPreview.getDateOfBirth() != null)
			entityPreviewPropValue
					.putProperty(new JsonLdProperty(WebEntityConstants.DATE_OF_BIRTH, entityPreview.getDateOfBirth()));

		if (entityPreview.getDateOfDeath() != null)
			entityPreviewPropValue
					.putProperty(new JsonLdProperty(WebEntityConstants.DATE_OF_DEATH, entityPreview.getDateOfDeath()));

		if (entityPreview.getProfessionOrOccuation() != null)
			entityPreviewPropValue.putProperty(new JsonLdProperty(WebEntityConstants.PROFESSION_OR_OCCUPATION,
					entityPreview.getProfessionOrOccuation()));
	}

	public String convertDateToStr(Date date) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		return df.format(date);
	}

	
}
