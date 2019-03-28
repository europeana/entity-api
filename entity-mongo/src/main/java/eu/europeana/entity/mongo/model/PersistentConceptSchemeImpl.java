package eu.europeana.entity.mongo.model;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Index;
import org.mongodb.morphia.annotations.Indexes;

import eu.europeana.entity.definitions.model.impl.BaseConceptScheme;
import eu.europeana.grouping.mongo.model.internal.PersistentConceptScheme;

@Entity("grouping")
@Indexes(@Index(value = PersistentConceptScheme.FIELD_IDENTIFIER, unique = true))
public class PersistentConceptSchemeImpl extends BaseConceptScheme implements PersistentConceptScheme {

	@Id
	private ObjectId id;

	
    public ObjectId getObjectId() {
		return id;
	}

	public void setObjectId(ObjectId id) {
		this.id = id;
	}
		
	@Override
	public String toString() {
		return "PersistentGrouping [Id:" + getObjectId() + ", Identifier:" + getConceptSchemeId() + "]";
	}

}