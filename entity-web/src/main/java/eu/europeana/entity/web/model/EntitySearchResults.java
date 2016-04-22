package eu.europeana.entity.web.model;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

@JsonSerialize(include = Inclusion.NON_EMPTY)
public class EntitySearchResults<T> extends AbstractSearchResults<T> {

	public EntitySearchResults(String apiKey, String action){
		super(apiKey, action);
	}
}
