package eu.europeana.entity.definitions.model.vocabulary;

public interface ConceptSolrFields extends EntitySolrFields{
	
	//TODO: move common fields to EntitySolr Fields
	public static final String ALT_LABEL = "skos_altLabel";
	public static final String ALT_LABEL_ALL = ALT_LABEL + ".*";
	public static final String CLOSE_MATCH = "skos_closeMatch";
	public static final String BROAD_MATCH = "skos_broadMatch";
	public static final String EXACT_MATCH = "skos_exactMatch";
	public static final String RELATED_MATCH = "skos_relatedMatch";
	public static final String BROADER = "skos_broader";
	public static final String RELATED = "skos_related";
	public static final String NARROWER = "skos_narrower";
	public static final String NARROW_MATCH = "skos_narrowMatch";
	public static final String NOTE = "skos_note";
	public static final String NOTE_ALL = NOTE + ".*";
	public static final String NOTATION = "skos_notation";
	public static final String NOTATION_ALL = NOTATION + ".*";
	public static final String HIDDEN_LABEL = "skos_hiddenLabel";
	
	public static final String IDENTIFIER               = "dc_identifier";
	//Fields common to some entities
	public static final String HAS_PART                 = "dcterms_hasPart";
	public static final String IS_PART_OF               = "dcterms_isPartOf";
	public static final String IS_NEXT_IN_SEQUENCE = "edm_isNextInSequence";
	
	public static final String IN_SCHEME = "inScheme";

	public static final String BEGIN                    = "edm_begin";
	public static final String END                      = "edm_end";
}
