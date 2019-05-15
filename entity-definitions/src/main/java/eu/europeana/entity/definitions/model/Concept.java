package eu.europeana.entity.definitions.model;

import java.util.Date;

public interface Concept extends Entity, eu.europeana.corelib.definitions.edm.entity.Concept {

    void setTimestamp(Date timestamp);

    Date getTimestamp();

}
