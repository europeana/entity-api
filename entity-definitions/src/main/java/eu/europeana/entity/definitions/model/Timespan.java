package eu.europeana.entity.definitions.model;

public interface Timespan extends Entity {

    void setIsNextInSequence(String[] isNextInSequence);

    String[] getIsNextInSequence();

    public String getBeginString();

    public void setBegin(String begin);

    public String getEndString();

    public void setEnd(String end);

}
