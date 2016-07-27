package eu.europeana.entity.solr.model.vocabulary;

public interface SuggestionFields {

	public static final String SUGGEST = "suggest";
	public static final String SUGGESTIONS = "suggestions";
	public static final String SUFFIX_EXACT = "_exact";
	public static final String SUFFIX_FUZZY = "_fuzzy";
	public static final String PREFIX_SUGGEST_ENTITY = "suggestEntity_";
	
	public static final String TERM = "term";
	public static final String PAYLOAD = "payload";
	public static final String ENTITY_ID = "entity_id";
	public static final String TIME_SPAN_START = "lifespanStart";
	public static final String TIME_SPAN_END = "lifespanEnd";
	public static final String BIRTH_DATE = "lifespanStart";
	public static final String DEATH_DATE = "lifespanEnd";
	public static final String LATITUDE = "latitude";
	public static final String LONGITUDE = "longitude";
	public static final String ROLE = "role";
	public static final String PREF_LABEL = "prefLabel";
	public static final String TYPE = "type";
	
	public static final String COUNTRY = "country";
	public static final String IS_PART_OF = "isPartOf";
	
	public static final String IN_SCHEME = "inScheme";
	
}
