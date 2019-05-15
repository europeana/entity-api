package eu.europeana.grouping.mongo.model.internal;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

/**
 * This class is used to generate the Grouping IDs in form of europeanaId/GroupingId, where the 
 * Grouping numbers are generated using sequencies 
 * 
 * @author GrafR
 *
 */
@Entity(value="sequenceGenerator", noClassnameStored=true)
public class GeneratedGroupingIdImpl {

	/**
	 * 
	 */
	@SuppressWarnings("unused")
	private static final long serialVersionUID = -3554805997975526594L;
	
	@Id
	private String id;
	private Long groupingId;
	
	public Long getGroupingId() {
		return groupingId;
	}

	public void setGroupingId(Long groupingId) {
		this.groupingId = groupingId;
	}

	public static final String SEQUENCE_COLUMN_NAME = "groupingId";
	
	/**
	 * This constructor must be use only by morphia 
	 */
	protected GeneratedGroupingIdImpl(){
		
	}
	
	/**
	 * 
	 * @param provider
	 * @param identifier - must be a long number
	 */
	public GeneratedGroupingIdImpl(String provider, String identifier){
		this(provider, Long.parseLong(identifier));
	}
	
	public GeneratedGroupingIdImpl(String provider, Long groupingId){
		this.id=provider;
		this.groupingId = groupingId;
	}
	
	public String getIdentifier() {
		return ""+getGroupingId();
	}

	public void setIdentifier(String identifier) {
		setGroupingId(Long.parseLong(identifier));
	}
	
}
