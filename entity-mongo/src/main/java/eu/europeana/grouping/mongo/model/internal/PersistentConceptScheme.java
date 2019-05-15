package eu.europeana.grouping.mongo.model.internal;

import java.util.Map;

import org.bson.types.ObjectId;

import eu.europeana.api.commons.nosql.entity.NoSqlEntity;
import eu.europeana.entity.definitions.model.ConceptScheme;

/**
 * @author GrafR
 *
 */
public interface PersistentConceptScheme extends ConceptScheme, NoSqlEntity {

    public static final String GENERATED_IDENTIFIER = "generatedIdentifier";
    
    public abstract ObjectId getObjectId();

    public boolean isDisabled();

    public void setDisabled(boolean disabled);
    
    public String getGeneratedIdentifier();

    public void setGeneratedIdentifier(String generatedIdentifier);

    public abstract void setPrefLabelStringMap(Map<String, String> prefLabelStringMap);
}