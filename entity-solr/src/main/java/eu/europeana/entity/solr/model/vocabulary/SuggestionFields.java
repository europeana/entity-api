package eu.europeana.entity.solr.model.vocabulary;

import eu.europeana.entity.definitions.vocabulary.WebEntityFields;

public interface SuggestionFields {

	public static final String SUGGEST = "suggest";
	public static final String SUGGESTIONS = "suggestions";
	public static final String SUFFIX_EXACT = "_exact";
	public static final String SUFFIX_FUZZY = "_fuzzy";
	public static final String PREFIX_SUGGEST_ENTITY = "suggestEntity_";
	
	public static final String FILTER_IN_EUROPEANA = "in_europeana";
	public static final String PARAM_EUROPEANA = "europeana";
	
	
	public static final String TERM = "term";
	public static final String PAYLOAD = "payload";
	
	//TODO: update to correct values from specs
	public static final String TIME_SPAN_START = "lifespanStart";
	public static final String TIME_SPAN_END = "lifespanEnd";
	public static final String IN_SCHEME = "inScheme";
	
	public static final String ID = WebEntityFields.ID; // "id";
	
	public static final String DATE_OF_BIRTH = WebEntityFields.DATE_OF_BIRTH; // "lifespanStart";
	public static final String DATE_OF_DEATH = WebEntityFields.DATE_OF_DEATH; // "lifespanEnd";
	public static final String LATITUDE = WebEntityFields.LATITUDE; // "latitude";
	public static final String LONGITUDE = WebEntityFields.LONGITUDE; // "longitude";
	public static final String PREF_LABEL = WebEntityFields.PREF_LABEL; // "prefLabel";
	public static final String TYPE = WebEntityFields.TYPE; // "type";
	public static final String PROFESSION_OR_OCCUPATION = WebEntityFields.PROFESSION_OR_OCCUPATION;
	
//TODO: remove .. not supported anymore	
//	public static final String COUNTRY =  WebEntityFields.COUNTRY;
	public static final String IS_PART_OF = WebEntityFields.IS_PART_OF;
	
	
}
