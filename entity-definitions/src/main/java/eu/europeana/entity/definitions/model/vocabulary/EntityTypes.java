package eu.europeana.entity.definitions.model.vocabulary;

import java.util.ArrayList;
import java.util.List;

public enum EntityTypes implements EntityKeyword{

	Concept("skos", "Concept", "https://www.w3.org/2009/08/skos-reference/skos.html#Concept"), 
	Agent("edm", "Agent", "http://www.europeana.eu/schemas/edm/Agent"), 
	Place("edm", "Place", "http://www.europeana.eu/schemas/edm/Place"), 
	Timespan("edm", "Timespan", "http://www.europeana.eu/schemas/edm/Timespan"), 
	All("*", "All", "http://www.europeana.eu/schemas/edm/Entity");
	
	
	private String modelNameSpace;
	private String internalType;
	private String httpUri;
	
	
	public String getModelNameSpace() {
		return modelNameSpace;
	}

	
	public String getInternalType() {
		return internalType;
	}

	EntityTypes(String modelNameSpace, String internalType, String uri){
		this.modelNameSpace = modelNameSpace;
		this.internalType = internalType;
		this.httpUri = uri;
	}
	
	
	public static boolean contains(String modelType) {

	    for (EntityTypes field : EntityTypes.values()) {
	        if (field.getInternalType().equalsIgnoreCase(modelType)) {
	            return true;
	        }
	    }

	    return false;
	}	
	
	

	/**
	 * Check if an array of EntityTypes contains an Entity type
	 * @param entityTypes Array of EntityTypes 
	 * @param entityType Single EntityTypes for which it is verified if it is contained in the EntityTypes array
	 * @return True if the EntityTypes object is contained in the EntityTypes array
	 */
	public static boolean arrayHasValue(EntityTypes[] entityTypes, EntityTypes entityType) {
		for (EntityTypes entType : entityTypes) {
	        if (entType.equals(entityType)) {
	            return true;
	        }
	    }
	    return false;
	}
	

	/**
	 * Convert an array of EntityTypes into an array of strings
	 * @param entityTypes array of EntityTypes 
	 * @return array of strings
	 */
	public static String[] toStringArray(EntityTypes[] entityTypes) {
		String[] internalEntityTypes = new String[entityTypes.length]; 
		for (int i = 0; i < entityTypes.length; i++) {
			internalEntityTypes[i] = entityTypes[i].getInternalType();
	    }
	    return internalEntityTypes;
	}

	
	/**
	 * Get entity type string list from comma separated entities string.
	 * @param commaSepEntityTypes Comma separated entities string
	 * @return Entity types string list
	 */
	public static EntityTypes[] getEntityTypesFromString(String commaSepEntityTypes) {
		List<EntityTypes> entityTypes = new ArrayList<EntityTypes>();
		String commaSepEntityTypesWithoutBlanks = commaSepEntityTypes.replaceAll("\\s","");
		EntityTypes entityType = null;
		if(!commaSepEntityTypes.contains(",")) {
			entityType = EntityTypes.getByInternalType(commaSepEntityTypesWithoutBlanks);
			if(entityType != null)
				entityTypes.add(entityType);
		} else {
			String[] splittedEntityTypes = commaSepEntityTypesWithoutBlanks.split(",");
			for(String splittedEntityType : splittedEntityTypes) {
				entityType = EntityTypes.getByInternalType(splittedEntityType);
				if(entityType != null)
					entityTypes.add(entityType);
			}
		}
		return entityTypes.toArray(new EntityTypes[entityTypes.size()]);
	}
	
	

	/**
	 * Identifying agent type by the json value.
	 * For user friendliness the the comparison is case insensitive  
	 * @param jsonValue
	 * @return
	 */
	public static EntityTypes getByJsonValue(String jsonValue){
		
		String[] values = jsonValue.split(":", 2);
		//last token
		String ignoreNamespace  = values[values.length -1];
		
		for(EntityTypes agentType : EntityTypes.values()){
			if(agentType.getJsonValue().equalsIgnoreCase(ignoreNamespace))
				return agentType;
		}
		return null;
	}
	
	/**
	 * Identifying agent type by the HTTP URI value.
	 * For user friendliness the the comparison is case insensitive  
	 * @param jsonValue
	 * @return
	 */
	public static EntityTypes getByHttpUri(String uri){
		
		
		for(EntityTypes agentType : EntityTypes.values()){
			if(agentType.getHttpUri().equalsIgnoreCase(uri))
				return agentType;
		}
		return null;
	}
	
	public static String getInternalTypeByModel(String jsonValue){

		String[] values = jsonValue.split(":", 2);
		//last token
		String ignoreNamespace  = values[values.length -1];
		
		for(EntityTypes agentType : EntityTypes.values()){
			if(agentType.getJsonValue().equalsIgnoreCase(ignoreNamespace))
				return agentType.getInternalType();
		}
		return null;
	}	
		
	public static EntityTypes getByInternalType(String internalType){

		for(EntityTypes agentType : EntityTypes.values()){
			if(agentType.getInternalType().equalsIgnoreCase(internalType))
				return agentType;
		}
		return null;
	}	
	
	@Override
	public String getJsonValue() {
		return getModelNameSpace() + ":" + getInternalType();
	}


	public String getHttpUri() {
		return httpUri;
	}
	
	
}
