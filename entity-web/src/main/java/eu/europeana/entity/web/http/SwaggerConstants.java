package eu.europeana.entity.web.http;

public interface SwaggerConstants {

	/**
	 * Implementation Notes
	 */
	public static final String SAMPLES_JSONLD = "Please find JSON-LD samples for grouping in <a href=\"../jsp/template/jsonld.jsp\" target=\"_blank\">templates</a>. ";
	public static final String UPDATE_SAMPLES_JSONLD = "Please find JSON-LD samples for concept scheme in <a href=\"../jsp/template/jsonld.jsp\" target=\"_blank\">templates</a>. " +
			"Please create your JSON update request using selected fields you are going to update. E.g. 'prefLabel' and 'definition' example:  "
			+ "{\"id\": \"http://data.europeana.eu/scheme/12345\",\r\n" +
		  "\"type\": \"ConceptScheme\",\r\n" +
		  "\"prefLabel\": {\r\n" +
		     "\"en\": \"Photography Genre\",\r\n" +
		     "\"fr\": \"New Label\"\r\n" +
		  "},\r\n" +
		  "\"definition\": {\r\n" +
		     "\"en\": \"Genres of Photography\",\r\n" +
		     "\"fr\": \"New Description\"\r\n" +
		  "},\r\n" +
		  "\"isDefinedBy\": \"https://www.europeana.eu/api/entities/search.json?query=skos_broader:(*/106+OR+*/1719+OR+*/1683)\",\r\n" +
		  "\"sameAs\": \"http://www.wikidata.org/entity/Q3100808\"\r\n" +
		"}\r\n";
 }
