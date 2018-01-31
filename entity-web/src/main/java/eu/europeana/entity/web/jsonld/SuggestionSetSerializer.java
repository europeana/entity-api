package eu.europeana.entity.web.jsonld;

import java.util.List;

import org.apache.stanbol.commons.jsonld.JsonLd;
import org.apache.stanbol.commons.jsonld.JsonLdProperty;
import org.apache.stanbol.commons.jsonld.JsonLdPropertyValue;
import org.apache.stanbol.commons.jsonld.JsonLdResource;
import org.springframework.http.HttpStatus;

import eu.europeana.api.common.config.I18nConstants;
import eu.europeana.api.commons.web.exception.HttpException;
import eu.europeana.entity.definitions.exceptions.UnsupportedEntityTypeException;
import eu.europeana.entity.definitions.model.ResourcePreview;
import eu.europeana.entity.definitions.model.search.result.ResultSet;
import eu.europeana.entity.definitions.model.vocabulary.EntityTypes;
import eu.europeana.entity.definitions.model.vocabulary.WebEntityConstants;
import eu.europeana.entity.utils.jsonld.EntityJsonComparator;
import eu.europeana.entity.web.model.view.AgentPreview;
import eu.europeana.entity.web.model.view.EntityPreview;
import eu.europeana.entity.web.model.view.PlacePreview;
import eu.europeana.entity.web.model.view.TimeSpanPreview;

public class SuggestionSetSerializer extends JsonLd {

	public SuggestionSetSerializer(){
		super();
		setPropOrderComparator(new EntityJsonComparator());
	}

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
		super();
		setPropOrderComparator(new EntityJsonComparator());		
		registerContainerProperty(WebEntityConstants.IS_PART_OF);
		registerContainerProperty(WebEntityConstants.ITEMS);
		setConceptSet(entitySet);
	}

	/**
	 * Adds the given concept to this JsonLd object using the resource's subject
	 * as key. If the key is NULL and there does not exist a resource with an
	 * empty String as key the resource will be added using an empty String ("")
	 * as key.
	 * 
	 * @param concept
	 * @throws HttpException 
	 */
	public String serialize() throws HttpException {

		setUseTypeCoercion(false);
		setUseCuries(true);
		setUsedNamespaces(namespacePrefixMap);

		JsonLdResource jsonLdResource = new JsonLdResource();
		jsonLdResource.setSubject("");
		
		JsonLdProperty contextProperty = new JsonLdProperty(WebEntityConstants.AT_CONTEXT);
		contextProperty.getValues().add(new JsonLdPropertyValue(WebEntityConstants.LDP_CONTEXT));
		contextProperty.getValues().add(new JsonLdPropertyValue(WebEntityConstants.ENTITY_CONTEXT));

		jsonLdResource.putProperty(contextProperty);

		// TODO: update JSONLD output and add the @language:en to context

		// String[] type = new String[] { "BasicContainer", "Collection" };
		jsonLdResource.putProperty(WebEntityConstants.TYPE, WebEntityConstants.TYPE_BASIC_CONTAINER);
		jsonLdResource.putProperty(WebEntityConstants.TOTAL, getEntitySet().getResultSize());

		serializeItems(jsonLdResource);

		put(jsonLdResource);

		return toString(4);
	}

	protected void serializeItems(JsonLdResource jsonLdResource) throws HttpException {

		// do not serialize if empty
		if (getEntitySet().isEmpty())
			return;

		JsonLdProperty containsProperty = new JsonLdProperty(WebEntityConstants.ITEMS);
		JsonLdPropertyValue propertyValue;

		for (EntityPreview entityPreview : getEntitySet().getResults()) {
			propertyValue = buildEntityPreviewPropertyValue(entityPreview);
			containsProperty.addValue(propertyValue);
		}

		jsonLdResource.putProperty(containsProperty);

	}

	private JsonLdPropertyValue buildEntityPreviewPropertyValue(EntityPreview entityPreview) throws HttpException {

		JsonLdPropertyValue entityPreviewPropValue = new JsonLdPropertyValue();

		// id
		entityPreviewPropValue.putProperty(new JsonLdProperty(WebEntityConstants.ID, entityPreview.getEntityId()));
		JsonLdProperty prefLabelProp = buildMapOfStringsProperty(WebEntityConstants.PREF_LABEL, entityPreview.getPreferredLabel(), 
				"");
		entityPreviewPropValue.putProperty(prefLabelProp);
		
		// hiddenLabel
		if(entityPreview.getHiddenLabel() != null && !entityPreview.getHiddenLabel().isEmpty()){
			JsonLdProperty hiddenLabelProp = buildMapProperty(WebEntityConstants.HIDDEN_LABEL, entityPreview.getHiddenLabel(), 
					"");
			entityPreviewPropValue.putProperty(hiddenLabelProp);
		}
		else
			getLogger().warn("No hidden labels available for entity: " + entityPreview.getEntityId());
		
		// depiction
		if (entityPreview.getDepiction() != null)
			entityPreviewPropValue.putProperty(new JsonLdProperty(WebEntityConstants.DEPICTION, entityPreview.getDepiction()));

		String type = entityPreview.getType();
		EntityTypes entityType = null;
		try {
			entityType = EntityTypes.getByInternalType(type);
		} catch (UnsupportedEntityTypeException e) {
			throw new HttpException(null, I18nConstants.UNSUPPORTED_ENTITY_TYPE, new String[]{type}, HttpStatus.NOT_FOUND, null);
		}

		if (entityType != null) {
			entityPreviewPropValue
					.putProperty(new JsonLdProperty(WebEntityConstants.TYPE, entityType.getInternalType()));

			switch (entityType) {
			case Concept:
				// add top concept, when available
				break;
				
			case Agent:
				putAgentSpecificProperties((AgentPreview) entityPreview, entityPreviewPropValue);
				break;

			case Place:
				putPlaceSpecificProperties((PlacePreview) entityPreview, entityPreviewPropValue);
				break;

			case Timespan:
				putTimespanSpecificProperties((TimeSpanPreview) entityPreview, entityPreviewPropValue);
				break;

			default:
				break;
			}

			entityPreviewPropValue.putProperty(new JsonLdProperty(WebEntityConstants.ID, entityPreview.getEntityId()));
		}

		return entityPreviewPropValue;
	}

	private void putTimespanSpecificProperties(TimeSpanPreview entityPreview,
			JsonLdPropertyValue entityPreviewPropValue) {
		if (entityPreview.getBegin() != null)
			entityPreviewPropValue.putProperty(new JsonLdProperty(WebEntityConstants.BEGIN, entityPreview.getBegin()));

		if (entityPreview.getEnd() != null)
			entityPreviewPropValue.putProperty(new JsonLdProperty(WebEntityConstants.END, entityPreview.getEnd()));
	}

	private void putPlaceSpecificProperties(PlacePreview entityPreview, JsonLdPropertyValue entityPreviewPropValue) {

		List<ResourcePreview> partOfList = entityPreview.getIsPartOf();
		JsonLdProperty prefLabelProp;
		
		if (partOfList != null && !partOfList.isEmpty()) {
			JsonLdProperty isPartOfProp = new JsonLdProperty(WebEntityConstants.IS_PART_OF);
			JsonLdPropertyValue propValue;
			for (ResourcePreview resourcePreview : partOfList) {
				propValue = new JsonLdPropertyValue();
				propValue.getValues().put(WebEntityConstants.ID, resourcePreview.getHttpUri());
//				propValue.getValues().put(WebEntityConstants.PREF_LABEL, resourcePreview.getPrefLabel());
				prefLabelProp = buildMapOfStringsProperty(WebEntityConstants.PREF_LABEL, resourcePreview.getPrefLabel(), 
						"");
				propValue.getPropertyMap().put(WebEntityConstants.PREF_LABEL, prefLabelProp);
				
				isPartOfProp.addValue(propValue);
			}

			entityPreviewPropValue.putProperty(isPartOfProp);
		}
	}

	private void putAgentSpecificProperties(AgentPreview entityPreview, JsonLdPropertyValue entityPreviewPropValue) {
		if (entityPreview.getDateOfBirth() != null)
			entityPreviewPropValue
					.putProperty(new JsonLdProperty(WebEntityConstants.DATE_OF_BIRTH, entityPreview.getDateOfBirth()));

		if (entityPreview.getDateOfDeath() != null)
			entityPreviewPropValue
					.putProperty(new JsonLdProperty(WebEntityConstants.DATE_OF_DEATH, entityPreview.getDateOfDeath()));

		if (entityPreview.getProfessionOrOccuation() != null && !entityPreview.getProfessionOrOccuation().isEmpty())
			entityPreviewPropValue.putProperty(buildMapProperty(WebEntityConstants.PROFESSION_OR_OCCUPATION, entityPreview.getProfessionOrOccuation(), 
					""));
	}

	// public String convertDateToStr(Date date) {
	// DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	// return df.format(date);
	// }

}