package eu.europeana.entity.definitions.core;

/*
 * Copyright 2007-2012 The Europeana Foundation
 *
 *  Licenced under the EUPL, Version 1.1 (the "Licence") and subsequent versions as approved
 *  by the European Commission;
 *  You may not use this work except in compliance with the Licence.
 * 
 *  You may obtain a copy of the Licence at:
 *  http://joinup.ec.europa.eu/software/page/eupl
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under
 *  the Licence is distributed on an "AS IS" basis, without warranties or conditions of
 *  any kind, either express or implied.
 *  See the Licence for the specific language governing permissions and limitations under
 *  the Licence.
 */

import java.util.List;
import java.util.Map;

/**
 * Interface representing contextual classes in EDM (Agent, Place, Timespan,
 * Concept)
 * 
 * @author Yorgos.Mamakis@ kb.nl
 * 
 */
public interface CoreContextualClass extends CoreAbstractEdmEntity {

	/**
	 * Retrieves the Preferable Label for a Contextual Class (language,value)
	 * format
	 * 
	 * @return A Map<String,List<List<String>>> for the Preferable Labels of a contextual
	 *         class (one per language)
	 */
	Map<String, String> getPrefLabelStringMap();

	/**
	 * Retrieves the Alternative Label for a Contextual Class (language,value)
	 * format
	 * 
	 * @return A Map<String,List<List<String>>> for the Alternative Labels of a contextual
	 *         class (one per language)
	 */
	Map<String, List<String>> getAltLabel();

	/**
	 * Retrieves the skos:note fields of a Contextual Class
	 * 
	 * @return A string array with notes for the Contextual Class
	 */
	Map<String,List<String>> getNote();

	/**
	 * Set the altLabel for a Contextual Class
	 * 
	 * @param altLabel
	 *            A Map<String,List<List<String>>> for the Alternative Labels of a
	 *            contextual class (one per language)
	 */
	void setAltLabel(Map<String, List<String>> altLabel);

	/**
	 * Set the notes for a Contextual Class
	 * 
	 * @param note
	 *            A String array with notes for the Contextual Class
	 */
	void setNote(Map<String,List<String>> note);

	/**
	 * Set the prefLabel for a Contextual Class
	 * 
	 * @param prefLabel
	 *            A Map<String,List<List<String>>> for the Preferable Labels of a contextual
	 *            class (one per language)
	 */
	void setPrefLabelStringMap(Map<String, String> prefLabel);

	/**
	 * sets the skos:hiddenLabel for a contextual class
	 * @param hiddenLabel
	 */
	void setHiddenLabel(Map<String, List<String>> hiddenLabel);

	/**
	 * 
	 * @return the skos:hiddenLabel for the contextual class
	 */
	Map<String, List<String>> getHiddenLabel();

}
