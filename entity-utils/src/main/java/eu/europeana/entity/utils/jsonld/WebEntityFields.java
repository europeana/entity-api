package eu.europeana.entity.utils.jsonld;

public interface WebEntityFields {

	//common fields
	public static final String CONTEXT = "@context";
	public static final String ENTITY_CONTEXT = "http://data.europeana.eu/entity/context.json";
	public static final String AT_ID = "@id";
	public static final String AT_TYPE = "@type";
//	public static final String IN_SCHEME = "inScheme";
	public static final String SAME_AS = "sameAs";
	
	//concept fields
	public static final String PREF_LABEL = "prefLabel";
	public static final String ALT_LABEL = "altLabel";
	public static final String NOTE = "note";
	public static final String NOTATION = "notation";
	public static final String RELATED = "related";
	public static final String BROADER = "broader";
	public static final String NARROWER = "narrower";
	
	//match fields
	public static final String EXACT_MATCH = "exactMatch";
	public static final String CLOSE_MATCH = "closeMatch";
	public static final String BROAD_MATCH = "broadMatch";
	public static final String NARROW_MATCH = "narrowMatch";
	public static final String RELATED_MATCH = "relatedMatch";
	

}
