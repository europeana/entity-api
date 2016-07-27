package eu.europeana.entity.web.service;

import eu.europeana.entity.definitions.model.Entity;
import eu.europeana.entity.definitions.model.search.result.ResultSet;
import eu.europeana.entity.definitions.model.vocabulary.EntityTypes;
import eu.europeana.entity.web.exception.HttpException;
import eu.europeana.entity.web.model.view.EntityPreview;

public interface EntityService {

	Entity retrieveByUrl(String type, String namespace, String identifier) throws HttpException;

	/**
	 * This method provides suggestions for auto-completion
	 * 
	 * @param text
	 * @param language
	 * @param internalEntityType
	 * @param namespace
	 * @param rows
	 * @return
	 * @throws HttpException
	 * 
	 * e.g. GET /entity/suggest?text=leonard&language=en
	 */
//	List<? extends Concept> suggest(
	ResultSet<? extends EntityPreview> suggest(
			String text, String language, EntityTypes internalEntityType, String namespace, int rows) throws HttpException;

}
