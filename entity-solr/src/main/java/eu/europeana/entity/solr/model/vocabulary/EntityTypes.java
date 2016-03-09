package eu.europeana.entity.solr.model.vocabulary;

import eu.europeana.entity.definitions.model.vocabulary.EntityKeyword;

public enum EntityTypes implements EntityKeyword{

	Concept("skos", "Concept"), Agent("edm", "Agent"), Place("edm", "Place"), Timespan("edm", "Timespan"), All("*", "all");
	
	
	private String modelNameSpace;
	private String internalType;
	
	public String getModelNameSpace() {
		return modelNameSpace;
	}

	
	public String getInternalType() {
		return internalType;
	}

	EntityTypes(String modelNameSpace, String internalType){
		this.modelNameSpace = modelNameSpace;
		this.internalType = internalType;
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
		
	
	@Override
	public String getJsonValue() {
		return getModelNameSpace() + ":" + getInternalType();
	}
	
	
}
