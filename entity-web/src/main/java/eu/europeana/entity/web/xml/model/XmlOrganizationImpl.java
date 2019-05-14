package eu.europeana.entity.web.xml.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import eu.europeana.entity.definitions.model.Organization;;

@JacksonXmlRootElement(localName = XmlConstants.XML_EDM_ORGANIZATION)
@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder({XmlConstants.XML_FOAF_DEPICTION, XmlConstants.XML_SKOS_PREF_LABEL, XmlConstants.XML_EDM_ACRONYM, XmlConstants.XML_SKOS_ALT_LABEL, 
    	XmlConstants.XML_DC_DESCRIPTION, XmlConstants.XML_FOAF_LOGO, XmlConstants.XML_EDM_EUROPEANA_ROLE, XmlConstants.XML_EDM_ORGANIZATION_DOMAIN,
    	XmlConstants.XML_EDM_GEOGRAPHIC_LEVEL, XmlConstants.XML_EDM_COUNTRY, XmlConstants.XML_FOAF_HOMEPAGE, XmlConstants.XML_FOAF_PHONE,
    	XmlConstants.XML_FOAF_MBOX, XmlConstants.XML_VCARD_HAS_ADDRESS, XmlConstants.XML_VCARD_ADDRESS, XmlConstants.XML_DC_IDENTIFIER, 
    	XmlConstants.XML_OWL_SAME_AS, XmlConstants.XML_ORE_IS_AGGREGATED_BY})
public class XmlOrganizationImpl extends XmlBaseEntityImpl {

	@JsonIgnore
	private Organization organization;
    
	public XmlOrganizationImpl(Organization organization) {
	    	super(organization);
	    	this.organization = organization;
	}
	
	@JacksonXmlElementWrapper(useWrapping=false)
	@JacksonXmlProperty(localName = XmlConstants.XML_EDM_ACRONYM)
	public List<XmlMultilingualString> getAcronym() {		
		return RdfXmlUtils.convertToXmlMultilingualString(organization.getAcronym());
	}
	
	@JacksonXmlElementWrapper(useWrapping=false)
	@JacksonXmlProperty(localName = XmlConstants.XML_DC_DESCRIPTION)
	public List<XmlMultilingualString> getDescription() {
		return RdfXmlUtils.convertMapToXmlMultilingualString(organization.getDescription());
	}
	
	@JacksonXmlProperty(localName = XmlConstants.XML_FOAF_LOGO)
	public EdmWebResource getLogo() {
	    	if(organization.getLogo() == null)
	    	    return null;
		return new EdmWebResource(organization.getLogo());
	}
	
	@JacksonXmlElementWrapper(useWrapping=false)
	@JacksonXmlProperty(localName = XmlConstants.XML_EDM_EUROPEANA_ROLE)
	public List<XmlMultilingualString> getEuropeanaRole() {
		return RdfXmlUtils.convertToXmlMultilingualString(organization.getEuropeanaRole());
	}
	
	@JacksonXmlElementWrapper(useWrapping=false)
	@JacksonXmlProperty(localName = XmlConstants.XML_EDM_ORGANIZATION_DOMAIN)
	public List<XmlMultilingualString> getOrganizationDomain() {
		return RdfXmlUtils.convertToXmlMultilingualString(organization.getOrganizationDomain());
	}
	
	@JacksonXmlElementWrapper(useWrapping=false)
	@JacksonXmlProperty(localName = XmlConstants.XML_EDM_GEOGRAPHIC_LEVEL)
	public List<XmlMultilingualString> getGeographicLevel() {
		return RdfXmlUtils.convertMapToXmlMultilingualString(organization.getGeographicLevel());
	}
	
	@JacksonXmlProperty(localName = XmlConstants.XML_EDM_COUNTRY)
	public String getCountry() {
	    	if(organization.getCountry() == null || organization.getCountry().isEmpty())
	    	    return null;
		return organization.getCountry();
	}
	
	@JacksonXmlProperty(localName = XmlConstants.XML_FOAF_HOMEPAGE)
	public RdfResource getHomepage() {
	    	if(organization.getHomepage() == null)
	    	    return null;
		return new RdfResource(organization.getHomepage());
	}
	
	@JacksonXmlElementWrapper(useWrapping=false)
	@JacksonXmlProperty(localName = XmlConstants.XML_FOAF_PHONE)
	public List<String> getPhone() {
	    	if(organization.getPhone() == null || organization.getPhone().size() == 0)
	    	    return null;
		return organization.getPhone();
	}
	
	@JacksonXmlElementWrapper(useWrapping=false)
	@JacksonXmlProperty(localName = XmlConstants.XML_FOAF_MBOX)
	public List<String> getMbox() {
	    	if(organization.getMbox() == null || organization.getMbox().size() == 0)
	    	    return null;
		return organization.getMbox();
	}
	
	@JacksonXmlElementWrapper(useWrapping=false)
	@JacksonXmlProperty(localName = XmlConstants.XML_DC_IDENTIFIER)
	public String[] getIdentifier() {
	    	return organization.getIdentifier();
	}
	
	@JacksonXmlElementWrapper(useWrapping=false)
	@JacksonXmlProperty(localName = XmlConstants.XML_OWL_SAME_AS)
	public List<RdfResource> getSameAs(){
	    	return RdfXmlUtils.convertToRdfResource(organization.getSameAs());
	}

	/*
	 *  ElementWrapper works only on lists and maps.
	 *  This conversion is needed, because we have a two level object 
	 *  <vcard:hasAddress><vcard:Address>....</vcard:hasAddress></vcard:Address>
	 */
	@JacksonXmlElementWrapper(localName = XmlConstants.XML_VCARD_HAS_ADDRESS)
	@JacksonXmlProperty(localName = XmlConstants.XML_VCARD_ADDRESS)
	public XmlAddressImpl[] getHasAddress() {
	    	XmlAddressImpl[] tmp = {new XmlAddressImpl(organization)};
	    	return tmp;
	}
}
