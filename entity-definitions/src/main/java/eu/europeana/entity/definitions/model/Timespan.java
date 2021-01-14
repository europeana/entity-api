package eu.europeana.entity.definitions.model;

public interface Timespan extends Entity {

    void setIsNextInSequence(String[] isNextInSequence);

    String[] getIsNextInSequence();

    public String getBeginString();

    public void setBeginString(String begin);

    public String getEndString();

    public void setEndString(String end);

    public String[] getIsPartOfArray();

    public void setIsPartOfArray(String[] isPartOf);
   
}
