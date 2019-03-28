package eu.europeana.entity.definitions.model.vocabulary.fields;

public interface WebConceptSchemeModelFields {

	
	/**
	 * Model attribute names
	 */
	//** common fields **/
	public static final String ID = "id";
	public static final String TYPE = "type";
	public static final String PREF_LABEL = "prefLabel";
	public static final String IS_DEFINED_BY = "isDefinedBy";

	
	//** concept scheme fields **/
	public static final String AT_CONTEXT = "@context";
	
	public static final String VALUE_CONTEXT_EUROPEANA_COLLECTION = "https://www.europeana.eu/schemas/context/entity.jsonld";
	
}
