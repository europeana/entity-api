package eu.europeana.entity.web.controller;

/**
 * Dataset types for that search can be applied
 * 
 * agents, places, concepts and time spans
 * 
 */
public enum DatasetTypes {

	  agents(WebEntityFields.PARAM_AGENTS,  WebEntityFields.PARAM_AGENTS)
	, places(WebEntityFields.PARAM_PLACES, WebEntityFields.PARAM_PLACES)
	, concepts(WebEntityFields.PARAM_CONCEPTS, WebEntityFields.PARAM_CONCEPTS)
	, timespans(WebEntityFields.PARAM_TIMESPANS, WebEntityFields.PARAM_TIMESPANS)
	, all(WebEntityFields.PARAM_ALL, WebEntityFields.PARAM_ALL); 

	
	private String modelType;
	private String solrType;
	
	public String getModelType() {
		return modelType;
	}

	
	DatasetTypes(String modelType, String solrType){
		this.modelType = modelType;
		this.solrType = solrType;
	}
	
	public String getSolrType() {
		return solrType;
	}
	
	
	public static boolean contains(String query) {

	    for (DatasetTypes field : DatasetTypes.values()) {
	        if (field.getModelType().equals(query)) {
	            return true;
	        }
	    }

	    return false;
	}	
	
	
	public static String getSolrTypeByModel(String model) {

		String res = "";
	    for (DatasetTypes field : DatasetTypes.values()) {
	        if (field.getModelType().equals(model)) {
	            res = field.getSolrType();
	            break;
	        }
	    }

	    return res;
	}	
	
	
	@Override
	public String toString() {
		return getModelType();
	}
		
}
