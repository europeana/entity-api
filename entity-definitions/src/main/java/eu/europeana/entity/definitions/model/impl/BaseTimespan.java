package eu.europeana.entity.definitions.model.impl;

import java.util.List;
import java.util.Map;

import eu.europeana.entity.definitions.model.Timespan;

public class BaseTimespan extends BaseEntity implements Timespan, eu.europeana.corelib.definitions.edm.entity.Timespan {

    private String[] isNextInSequence;
    private String begin;
    private String end;

    public String[] getIsNextInSequence() {
	return isNextInSequence;
    }

    public void setIsNextInSequence(String[] isNextInSequence) {
	this.isNextInSequence = isNextInSequence;
    }

    public void setBeginString(String begin) {
        this.begin = begin;
    }

    public void setEndString(String end) {
        this.end = end;
    }

    
    @Override
    public String getBeginString() {
	return begin;
    }

    @Override
    public String getEndString() {
	return end;
    }
    
    @Override
    @Deprecated
    public void setIsPartOf(Map<String, List<String>> isPartOf) {
	// TODO Auto-generated method stub
    }

    @Override
    @Deprecated
    public Map<String, List<String>> getBegin() {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    @Deprecated
    public Map<String, List<String>> getDctermsHasPart() {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    @Deprecated
    public Map<String, List<String>> getEnd() {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    @Deprecated
    public Map<String, List<String>> getIsPartOf() {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    @Deprecated
    public void setBegin(Map<String, List<String>> arg0) {
	// TODO Auto-generated method stub
	
    }

    @Override
    @Deprecated
    public void setDctermsHasPart(Map<String, List<String>> arg0) {
	// TODO Auto-generated method stub
	
    }

    @Override
    @Deprecated
    public void setEnd(Map<String, List<String>> arg0) {
	// TODO Auto-generated method stub
	
    }    
}
