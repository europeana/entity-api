package eu.europeana.entity.web.xml.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import eu.europeana.entity.definitions.model.Entity;
import eu.europeana.entity.definitions.model.impl.BaseEntity;;


@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
public class XmlBaseEntityImpl {

    	@JsonIgnore
    	Entity entity;
    	@JsonIgnore
    	String aggregationId;
    	/**
    	 * relatedElementsToSerialize - this list is maintained by each serialized entity and contains the entities that
    	 * need to be serialized in addition, outside of the given entity
    	 */
    	@JsonIgnore
    	List<Object> referencedWebResources;
    	
    	
    	public List<Object> getReferencedWebResources() {
			return referencedWebResources;
		}

		public XmlBaseEntityImpl(Entity entity) {
    	    	this.entity = entity;
    	    	aggregationId = entity.getAbout() + "#aggregation";
    	    	referencedWebResources = new ArrayList<Object>();
    	}
    	
	@JacksonXmlProperty(isAttribute= true, localName = XmlConstants.XML_RDF_ABOUT)
	public String getAbout() {
		return entity.getAbout();
	}
	
	@JacksonXmlProperty(localName = XmlConstants.XML_ORE_IS_AGGREGATED_BY)
	public XmlIsAggregatedByImpl getIsAggregatedBy() {
	    	if(entity.getCreated() == null && entity.getModified() == null)
	    	    return null;
		return new XmlIsAggregatedByImpl(aggregationId);
	}
	
	public XmlAggregationImpl createXmlAggregation() {
	    	if(entity.getCreated() == null && entity.getModified() == null)
	    	    return null;
		return new XmlAggregationImpl(entity);
	}
	
	@JacksonXmlProperty(localName = XmlConstants.XML_FOAF_DEPICTION)
	public EdmWebResource getDepiction() {
	    	if(entity.getDepiction() == null)
	    	    return null;
		return new EdmWebResource(entity.getDepiction());
	}
	
	@JacksonXmlElementWrapper(useWrapping=false)
	@JacksonXmlProperty(localName = XmlConstants.XML_SKOS_PREF_LABEL)
	public List<XmlMultilingualString> getPrefLabel() {		
		return RdfXmlUtils.convertToXmlMultilingualString(entity.getPrefLabel());
	}
	
	@JacksonXmlElementWrapper(useWrapping=false)
	@JacksonXmlProperty(localName = XmlConstants.XML_SKOS_ALT_LABEL)
	public List<XmlMultilingualString> getAltLabel() {
		return RdfXmlUtils.convertToXmlMultilingualString(entity.getAltLabel());
	}
	
	@JacksonXmlElementWrapper(useWrapping=false)
	@JacksonXmlProperty(localName = XmlConstants.XML_OWL_SAME_AS)
	public List<RdfResource> getSameAs(){
	    	return RdfXmlUtils.convertToRdfResource(entity.getSameAs());
	}
	
	@JacksonXmlElementWrapper(useWrapping=false)
	@JacksonXmlProperty(localName = XmlConstants.XML_EDM_IS_SHOWN_BY)
	public RdfResource getIsShownBy() {
		if (((BaseEntity)entity).getIsShownById()!=null)
		{
		    referencedWebResources.add(new XmlWebResourceImpl(((BaseEntity)entity).getIsShownById(),((BaseEntity)entity).getIsShownBySource(), ((BaseEntity)entity).getIsShownByThumbnail()));
	        return new RdfResource(((BaseEntity)entity).getIsShownById());
		}
		else
		{
			return null;
		}
	}

}
