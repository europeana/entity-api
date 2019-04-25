package eu.europeana.entity.definitions.model.vocabulary;

public interface EntitySolrFields {

    public static final String ID = "id";
    @Deprecated
    /** TODO: to be replaced by type */
    public static final String INTERNAL_TYPE = "internal_type";
    public static final String DEPICTION = "foaf_depiction";
    
    public static final String PREF_LABEL = "skos_prefLabel";
    public static final String PREF_LABEL_ALL = PREF_LABEL + ".*";
    public static final String SAME_AS = "owl_sameAs";
    public static final String COREF = "coref";
    
    public static final String WIKIPEDIA_CLICKS = "wikipedia_clicks";
    public static final String EUROPEANA_DOC_COUNT = "europeana_doc_count";
    public static final String DERIVED_SCORE = "derived_score";

    
    public static final String TIMESTAMP = "timestamp";
    public static final String CREATED = "created";
    public static final String MODIFIED = "modified";
    
}
