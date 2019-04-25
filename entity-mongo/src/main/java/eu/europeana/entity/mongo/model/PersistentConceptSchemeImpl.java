package eu.europeana.entity.mongo.model;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Field;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Index;
import org.mongodb.morphia.annotations.IndexOptions;
import org.mongodb.morphia.annotations.Indexes;
import org.mongodb.morphia.annotations.Property;

import eu.europeana.entity.definitions.model.impl.BaseConceptScheme;
import eu.europeana.grouping.mongo.model.internal.PersistentConceptScheme;

@Entity("concept_scheme")
@Indexes(@Index(fields = {@Field(PersistentConceptScheme.GENERATED_IDENTIFIER)}, options = @IndexOptions(unique = true)))
public class PersistentConceptSchemeImpl extends BaseConceptScheme implements PersistentConceptScheme {

    @Id
    private ObjectId id;
    @Property(PersistentConceptScheme.GENERATED_IDENTIFIER)
    private String generatedIdentifier;

    // Indicates whether the set is disabled in database
    private boolean disabled;

    
    @Override
    public boolean isDisabled() {
	return disabled;
    }

    @Override
    public void setDisabled(boolean disabled) {
	this.disabled = disabled;
    }
    
    
    public ObjectId getObjectId() {
	return id;
    }

    public void setObjectId(ObjectId id) {
	this.id = id;
    }
    
    @Override
    public String getGeneratedIdentifier() {
        return generatedIdentifier;
    }

    @Override
    public void setGeneratedIdentifier(String generatedIdentifier) {
        this.generatedIdentifier = generatedIdentifier;
    }


    @Override
    public String toString() {
	return "PersistentConceptScheme [ObjectId:" + getObjectId() + ", Entity Id:" + getEntityId() + "]";
    }

   
}