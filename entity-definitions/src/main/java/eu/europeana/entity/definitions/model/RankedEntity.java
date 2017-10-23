package eu.europeana.entity.definitions.model;

import java.util.Date;

public interface RankedEntity {

	//TODO: move timestamp to another interface
	void setTimestamp(Date timestamp);

	Date getTimestamp();

	void setDerivedScore(float derivedScore);

	float getDerivedScore();

	void setEuropeanaDocCount(int europeanaDocCount);

	int getEuropeanaDocCount();

	void setWikipediaClicks(int wikipediaClicks);

	int getWikipediaClicks();

}
