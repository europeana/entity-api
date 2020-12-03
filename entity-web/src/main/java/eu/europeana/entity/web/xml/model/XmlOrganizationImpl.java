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

	public XmlOrganizationImpl(Organization organization) {
	    	super(organization);
	}
	
	@JacksonXmlElementWrapper(useWrapping=false)
	@JacksonXmlProperty(localName = XmlConstants.XML_EDM_ACRONYM)
	public List<XmlMultilingualString> getAcronym() {		
		return RdfXmlUtils.convertToXmlMultilingualString(getOrganization().getAcronym());
	}
	
	@JacksonXmlElementWrapper(useWrapping=false)
	@JacksonXmlProperty(localName = XmlConstants.XML_DC_DESCRIPTION)
	public List<XmlMultilingualString> getDescription() {
		return RdfXmlUtils.convertMapToXmlMultilingualString(getOrganization().getDescription());
	}
	
	@JacksonXmlProperty(localName = XmlConstants.XML_FOAF_LOGO)
	public EdmWebResource getLogo() {
	    	if(getOrganization().getLogo() == null)
	    	    return null;
		return new EdmWebResource(getOrganization().getLogo());
	}
	
	@JacksonXmlElementWrapper(useWrapping=false)
	@JacksonXmlProperty(localName = XmlConstants.XML_EDM_EUROPEANA_ROLE)
	public List<XmlMultilingualString> getEuropeanaRole() {
		return RdfXmlUtils.convertToXmlMultilingualString(getOrganization().getEuropeanaRole());
	}
	
	@JacksonXmlElementWrapper(useWrapping=false)
	@JacksonXmlProperty(localName = XmlConstants.XML_EDM_ORGANIZATION_DOMAIN)
	public List<XmlMultilingualString> getOrganizationDomain() {
		return RdfXmlUtils.convertToXmlMultilingualString(getOrganization().getOrganizationDomain());
	}
	
	@JacksonXmlElementWrapper(useWrapping=false)
	@JacksonXmlProperty(localName = XmlConstants.XML_EDM_GEOGRAPHIC_LEVEL)
	public List<XmlMultilingualString> getGeographicLevel() {
		return RdfXmlUtils.convertMapToXmlMultilingualString(getOrganization().getGeographicLevel());
	}
	
	@JacksonXmlProperty(localName = XmlConstants.XML_EDM_COUNTRY)
	public String getCountry() {
	    	if(getOrganization().getCountry() == null || getOrganization().getCountry().isEmpty())
	    	    return null;
		return getOrganization().getCountry();
	}
	
	@JacksonXmlProperty(localName = XmlConstants.XML_FOAF_HOMEPAGE)
	public RdfResource getHomepage() {
	    	if(getOrganization().getHomepage() == null)
	    	    return null;
		return new RdfResource(getOrganization().getHomepage());
	}
	
	@JacksonXmlElementWrapper(useWrapping=false)
	@JacksonXmlProperty(localName = XmlConstants.XML_FOAF_PHONE)
	public List<String> getPhone() {
	    	if(getOrganization().getPhone() == null || getOrganization().getPhone().size() == 0)
	    	    return null;
		return getOrganization().getPhone();
	}
	
	@JacksonXmlElementWrapper(useWrapping=false)
	@JacksonXmlProperty(localName = XmlConstants.XML_FOAF_MBOX)
	public List<String> getMbox() {
	    	if(getOrganization().getMbox() == null || getOrganization().getMbox().size() == 0)
	    	    return null;
		return getOrganization().getMbox();
	}
	
	@JacksonXmlElementWrapper(useWrapping=false)
	@JacksonXmlProperty(localName = XmlConstants.XML_DC_IDENTIFIER)
	public String[] getIdentifier() {
	    	return getOrganization().getIdentifier();
	}

	@JsonIgnore
	private Organization getOrganization() {
	    return (Organization)entity;
	}
	
	/*
	 *  ElementWrapper works only on lists and maps.
	 *  This conversion is needed, because we have a two level object 
	 *  <vcard:hasAddress><vcard:Address>....</vcard:hasAddress></vcard:Address>
	 */
	@JacksonXmlElementWrapper(localName = XmlConstants.XML_VCARD_HAS_ADDRESS)
	@JacksonXmlProperty(localName = XmlConstants.XML_VCARD_ADDRESS)
	public XmlAddressImpl[] getHasAddress() {
	    	XmlAddressImpl[] tmp = {new XmlAddressImpl(getOrganization())};
	    	return tmp;
	}
}
