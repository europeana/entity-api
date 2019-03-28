package eu.europeana.grouping.mongo.model.internal;

import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;

import eu.europeana.api.commons.nosql.entity.NoSqlEntity;
import eu.europeana.entity.definitions.model.ConceptScheme;


/**
 * @author GrafR
 *
 */
public interface PersistentConceptScheme extends ConceptScheme, NoSqlEntity {

	public final static String FIELD_IDENTIFIER = "conceptSchemeId";
//	public final static String FIELD_IDENTIFIER = "groupingId";
//	public final static String FIELD_IDENTIFIER = "identifier";
//		public static final String FIELD_TITLE = "title";
//		public static final String FIELD_DESCRIPTION = "description";
//		public static final String FIELD_SET_TYPE = "setType";
		
	public abstract ObjectId getObjectId();		
//		public abstract String getIdentifier();		
//		public abstract void setIdentifier(String sequenceIdentifier);
	public abstract String getType();
	public abstract void setType(String type);
	public abstract String getConceptSchemeId();		
	public abstract void setConceptSchemeId(String id);
	public abstract Map<String, String> getPrefLabel();		
	public abstract void setPrefLabel(Map<String, String> prefLabel);
	public abstract Map<String, String> getDefinition();		
	public abstract void setDefinition(Map<String, String> definition);
	public abstract String getIsDefinedBy();		
	public abstract void setIsDefinedBy(String query);
	public abstract String getSameAs();
	public abstract void setSameAs(String sameAs);
//	public abstract String[] getInScheme();
//	public abstract void setInScheme(String[] inScheme);	
	public abstract List<String> getInScheme();
	public abstract void setInScheme(List<String> inScheme);	
}