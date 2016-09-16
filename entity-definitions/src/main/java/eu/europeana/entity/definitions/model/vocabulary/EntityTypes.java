package eu.europeana.entity.definitions.model.vocabulary;

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
