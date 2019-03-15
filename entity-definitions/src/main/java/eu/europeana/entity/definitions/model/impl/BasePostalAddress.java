package eu.europeana.entity.definitions.model.impl;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import eu.europeana.corelib.definitions.edm.entity.Address;
import eu.europeana.entity.definitions.model.Place;
import eu.europeana.entity.definitions.model.vocabulary.WebEntityFields;

public class BasePostalAddress extends BaseEntity implements eu.europeana.corelib.definitions.edm.entity.Address {

    private String streetAddress;
    private String postalCode;
    private String postBox;
    private String locality;
    private String region;
    private String countryName;
    
    
    @Override
    public void setVcardPostOfficeBox(String vcardPostOfficeBox) {
	this.postBox = vcardPostOfficeBox;
    }

    @Override
    public String getVcardPostOfficeBox() {
	return postBox;
    }

    @Override
    public void setVcardCountryName(String vcardCountryName) {
	this.countryName = vcardCountryName;
    }

    @Override
    public String getVcardCountryName() {
	return countryName;
    }

    @Override
    public void setVcardPostalCode(String vcardPostalCode) {
	this.postalCode = vcardPostalCode;
    }

    @Override
    public String getVcardPostalCode() {
	return postalCode;
    }

    @Override
    public void setVcardLocality(String vcardLocality) {
	this.locality = vcardLocality;
    }

    @Override
    public String getVcardLocality() {
	return locality;
    }

    @Override
    public void setVcardStreetAddress(String vcardStreetAddress) {
	this.streetAddress = vcardStreetAddress;
    }

    @Override
    public String getVcardStreetAddress() {
	return streetAddress;
    }

    @Override
    public void setVcardHasGeo(String vcardHasGeo) {
	// TODO Auto-generated method stub
	
    }

    @Override
    public String getVcardHasGeo() {
	// TODO Auto-generated method stub
	return null;
    }

}
