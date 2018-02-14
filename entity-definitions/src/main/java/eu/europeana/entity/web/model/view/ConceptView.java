package eu.europeana.entity.web.model.view;

import java.util.Map;

import eu.europeana.entity.definitions.model.search.result.IdBean;

@Deprecated
/**
 * needs to be updated/refactored when search functionality is completely specified
 * @author GordeaS
 *
 */
public interface ConceptView extends IdBean {

	String getEntityType();
	
//	Map<String, List<String>> getPrefLabel();
	Map<String, String> getPrefLabel();
}
