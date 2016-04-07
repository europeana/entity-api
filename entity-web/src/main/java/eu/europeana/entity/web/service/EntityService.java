package eu.europeana.entity.web.service;

import org.apache.http.HttpException;

import eu.europeana.entity.definitions.model.Concept;
import eu.europeana.entity.definitions.model.search.result.ResultSet;
import eu.europeana.entity.web.model.view.EntityPreview;

public interface EntityService {

	Concept retrieveByUrl(String type, String namespace, String identifier) throws HttpException;

	/**
	 * This method provides suggestions for auto-completion
	 * 
	 * @param text
	 * @param language
	 * @param type
	 * @param namespace
	 * @param rows
	 * @return
	 * @throws HttpException
	 * 
	 * e.g. GET /entity/suggest?text=leonard&language=en
	 */
	ResultSet<? extends EntityPreview> suggest(
			String text, String language, String type, String namespace, int rows) throws HttpException;

}
