package eu.europeana.entity.definitions.model.vocabulary;

public interface SkosConceptSolrFields {
	
	
	public static final String PREF_LABEL = "skos_prefLabel";
	public static final String PREF_LABEL_ALL = PREF_LABEL + ".*";
	public static final String ALT_LABEL = "skos_altLabel";
	public static final String ALT_LABEL_ALL = ALT_LABEL + ".*";
	public static final String CLOSE_MATCH = "skos_closeMatch";
	public static final String BROAD_MATCH = "skos_broadMatch";
	public static final String EXACT_MATCH = "skos_exactMatch";
	public static final String RELATED_MATCH = "skos_relatedMatch";
	public static final String IN_SCHEME = "skos_inScheme";
	public static final String BROADER = "skos_broader";
	public static final String RELATED = "skos_related";
	public static final String NARROWER = "skos_narrower";
	public static final String NARROW_MATCH = "skos_narrowMatch";
	public static final String NOTE = "skos_note";
	public static final String NOTE_ALL = NOTE + ".*";
	public static final String NOTATION = "skos_notation";
	public static final String NOTATION_ALL = NOTATION + ".*";
	
	public static final String DEFINITION = "skos_definition";
	public static final String HIDDEN_LABEL = "skos_hiddenLabel";
	
	public static final String RDF_ABOUT = "rdf_about";
	public static final String SAME_AS = "same_as";
	
	public static final String ENTITY_ID = "entity_id";
	public static final String INTERNAL_TYPE = "internal_type";
	public static final String TIMESTAMP = "timestamp";
		
	
}
