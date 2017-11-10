/*
* Licensed to the Apache Software Foundation (ASF) under one or more
* contributor license agreements.  See the NOTICE file distributed with
* this work for additional information regarding copyright ownership.
* The ASF licenses this file to You under the Apache License, Version 2.0
* (the "License"); you may not use this file except in compliance with
* the License.  You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package eu.europeana.entity.utils.jsonld;

import java.util.Comparator;
import java.util.HashMap;
//import org.apache.stanbol.commons.jsonld.JsonLdCommon;

import org.apache.stanbol.commons.jsonld.JsonLdCommon;

import eu.europeana.entity.definitions.model.vocabulary.WebEntityConstants;


/**
 * This class is specific to annotations must be moved to (europeana) annotation
 * repository A comparator for JSON-LD maps to ensure the order of certain key
 * elements like '#', '@', 'a' in JSON-LD output.
 *
 * @author Jan Roerden
 */

public class EntityJsonComparator implements Comparator<Object> {

	static final HashMap<String, Integer> propOrderMap = new HashMap<String, Integer>();
	static {
		propOrderMap.put(JsonLdCommon.CONTEXT, 10);
		propOrderMap.put(WebEntityConstants.ID, 20);
		propOrderMap.put(WebEntityConstants.TYPE, 30);
		propOrderMap.put(WebEntityConstants.TOTAL, 40);
		propOrderMap.put(WebEntityConstants.DEPICTION, 50);
		propOrderMap.put(WebEntityConstants.PREF_LABEL, 60);
		propOrderMap.put(WebEntityConstants.ALT_LABEL, 70);
		propOrderMap.put(WebEntityConstants.HIDDEN_LABEL, 75);
		propOrderMap.put(WebEntityConstants.GEO_LAT, 80);
		propOrderMap.put(WebEntityConstants.GEO_LONG, 90);
		propOrderMap.put(WebEntityConstants.ALTITUDE, 100);
		propOrderMap.put(WebEntityConstants.NAME, 110);
		propOrderMap.put(WebEntityConstants.BEGIN, 120);
		propOrderMap.put(WebEntityConstants.DATE_OF_BIRTH, 130);
		propOrderMap.put(WebEntityConstants.END, 140);
		propOrderMap.put(WebEntityConstants.DATE_OF_DEATH, 150);
		propOrderMap.put(WebEntityConstants.PLACE_OF_BIRTH, 160);
		propOrderMap.put(WebEntityConstants.PLACE_OF_DEATH, 170);
		propOrderMap.put(WebEntityConstants.DATE_OF_ESTABLISHMENT, 180);
		propOrderMap.put(WebEntityConstants.DATE_OF_TERMINATION, 190);
		propOrderMap.put(WebEntityConstants.DATE, 200);
		propOrderMap.put(WebEntityConstants.GENDER, 210);
		propOrderMap.put(WebEntityConstants.PROFESSION_OR_OCCUPATION, 220);
		propOrderMap.put(WebEntityConstants.BIOGRAPHICAL_INFORMATION, 230);
		propOrderMap.put(WebEntityConstants.NOTE, 240);
		propOrderMap.put(WebEntityConstants.HAS_PART, 250);
		propOrderMap.put(WebEntityConstants.IS_PART_OF, 260);
		propOrderMap.put(WebEntityConstants.HAS_MET, 270);
		propOrderMap.put(WebEntityConstants.IS_RELATED_TO, 280);
		propOrderMap.put(WebEntityConstants.IDENTIFIER, 290);
		propOrderMap.put(WebEntityConstants.IS_NEXT_IN_SEQUENCE, 300);
//		propOrderMap.put(WebEntityConstants.CONCEPT, 310);
		propOrderMap.put(WebEntityConstants.NOTATION, 320);
		propOrderMap.put(WebEntityConstants.BROADER, 330);
		propOrderMap.put(WebEntityConstants.RELATED, 340);
		propOrderMap.put(WebEntityConstants.BROAD_MATCH, 350);
		propOrderMap.put(WebEntityConstants.NARROW_MATCH, 360);
		propOrderMap.put(WebEntityConstants.RELATED_MATCH, 370);
		propOrderMap.put(WebEntityConstants.CLOSE_MATCH, 380);
		propOrderMap.put(WebEntityConstants.EXACT_MATCH, 390);
		propOrderMap.put(WebEntityConstants.SAME_AS, 400);
	}

	@Override
	public int compare(Object arg0, Object arg1) {
		Integer leftOrder = propOrderMap.get(arg0);
		Integer rightOrder = propOrderMap.get(arg1);
		if (leftOrder == null)
			leftOrder = Math.abs(arg0.hashCode());
		if (rightOrder == null)
			rightOrder = Math.abs(arg1.hashCode());

		return Integer.compare(leftOrder, rightOrder);
	}

}
