package eu.europeana.entity.web.model.view;

import java.util.List;
import java.util.Map;

import eu.europeana.entity.definitions.model.search.result.IdBean;

public interface ConceptView extends IdBean {

	Map<String, List<String>> getPrefLabel();
	
	String getEntityType();
}
