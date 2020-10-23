package eu.europeana.entity.definitions.model.vocabulary;

import eu.europeana.entity.definitions.exceptions.UnsupportedEntityTypeException;

public enum EntityTypes implements EntityKeyword{

	Organization("edm", "Organization", "http://www.europeana.eu/schemas/edm/Organization"), 
	Concept("skos", "Concept", "https://www.w3.org/2009/08/skos-reference/skos.html#Concept"), 
	Agent("edm", "Agent", "http://www.europeana.eu/schemas/edm/Agent"), 
	Place("edm", "Place", "http://www.europeana.eu/schemas/edm/Place"), 
	Timespan("edm", "Timespan", "http://www.europeana.eu/schemas/edm/Timespan"), 
	ConceptScheme("edm", "ConceptScheme", "http://www.europeana.eu/schemas/edm/ConceptScheme"), 
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
	 * Identifying agent type by the json value.
	 * For user friendliness the the comparison is case insensitive  
	 * @param jsonValue
	 * @return
	 * @throws UnsupportedEntityTypeException 
	 */
	static EntityTypes getByJsonValue(String jsonValue) throws UnsupportedEntityTypeException{
		
		String[] values = jsonValue.split(":", 2);
		//last token
		String ignoreNamespace  = values[values.length -1];
		
		for(EntityTypes agentType : EntityTypes.values()){
			if(agentType.getJsonValue().equalsIgnoreCase(ignoreNamespace))
				return agentType;
		}
		throw new UnsupportedEntityTypeException(jsonValue);
	}
			
	public static EntityTypes getByInternalType(String internalType) throws UnsupportedEntityTypeException{

		for(EntityTypes entityType : EntityTypes.values()){
			if(entityType.getInternalType().equalsIgnoreCase(internalType))
				return entityType;
		}
		throw new UnsupportedEntityTypeException(internalType);
	}	
	
	@Override
	public String getJsonValue() {
		return getModelNameSpace() + ":" + getInternalType();
	}


	public String getHttpUri() {
		return httpUri;
	}
	
	
}
