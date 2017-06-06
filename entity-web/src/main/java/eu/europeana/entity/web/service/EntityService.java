package eu.europeana.entity.web.service;

import eu.europeana.api.commons.definitions.model.Entity;
import eu.europeana.api.commons.web.exception.HttpException;
import eu.europeana.entity.definitions.model.search.result.ResultSet;
import eu.europeana.entity.definitions.model.vocabulary.EntityTypes;
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
			String text, String language, EntityTypes[] internalEntityTypes, String scope, String namespace, int rows) throws HttpException;

	
	/**
	 * Performs a lookup for the entity in all 4 datasets:
	 * 
	 *    agents, places, concepts and time spans 
	 * 
	 * using an alternative uri for an entity (lookup will happen within the owl:sameAs properties).
	 * 
	 * @param uri
	 * @return
	 * @throws HttpException
	 */
	String resolveByUri(String uri) throws HttpException;

}
