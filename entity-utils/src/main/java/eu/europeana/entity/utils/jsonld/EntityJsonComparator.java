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
		//AGENTS
		propOrderMap.put(WebEntityConstants.ID, 20);
		propOrderMap.put(WebEntityConstants.TYPE, 30);
		propOrderMap.put(WebEntityConstants.TOTAL, 40);
		propOrderMap.put(WebEntityConstants.DEPICTION, 50);
		propOrderMap.put(WebEntityConstants.PREF_LABEL, 60);
		propOrderMap.put(WebEntityConstants.ALT_LABEL, 70);
		propOrderMap.put(WebEntityConstants.HIDDEN_LABEL, 80);
		propOrderMap.put(WebEntityConstants.NAME, 90);
		propOrderMap.put(WebEntityConstants.BEGIN, 100);
		propOrderMap.put(WebEntityConstants.DATE_OF_BIRTH, 110);
		propOrderMap.put(WebEntityConstants.DATE_OF_ESTABLISHMENT, 120);
		propOrderMap.put(WebEntityConstants.END, 130);
		propOrderMap.put(WebEntityConstants.DATE_OF_DEATH, 140);
		propOrderMap.put(WebEntityConstants.DATE_OF_TERMINATION, 150);
		propOrderMap.put(WebEntityConstants.DATE, 160);
		propOrderMap.put(WebEntityConstants.PLACE_OF_BIRTH, 170);
		propOrderMap.put(WebEntityConstants.PLACE_OF_DEATH, 180);
		propOrderMap.put(WebEntityConstants.GENDER, 190);
		propOrderMap.put(WebEntityConstants.PROFESSION_OR_OCCUPATION, 200);
		propOrderMap.put(WebEntityConstants.BIOGRAPHICAL_INFORMATION, 210);
		//Place
		propOrderMap.put(WebEntityConstants.LATITUDE, 220);
		propOrderMap.put(WebEntityConstants.LONGITUDE, 230);
		propOrderMap.put(WebEntityConstants.ALTITUDE, 240);
//		
		//Agent
		propOrderMap.put(WebEntityConstants.NOTE, 250);
		
		//Concept
		propOrderMap.put(WebEntityConstants.NOTATION, 260);
		propOrderMap.put(WebEntityConstants.BROADER, 270);
		propOrderMap.put(WebEntityConstants.NARROWER, 280);
		propOrderMap.put(WebEntityConstants.RELATED, 290);
		propOrderMap.put(WebEntityConstants.BROAD_MATCH, 300);
		propOrderMap.put(WebEntityConstants.NARROW_MATCH, 310);
		propOrderMap.put(WebEntityConstants.RELATED_MATCH, 320);
		propOrderMap.put(WebEntityConstants.CLOSE_MATCH, 330);
		propOrderMap.put(WebEntityConstants.EXACT_MATCH, 340);
		
		//Organizations
		propOrderMap.put(WebEntityConstants.DESCRIPTION, 350);
		propOrderMap.put(WebEntityConstants.ACRONYM, 360);
		propOrderMap.put(WebEntityConstants.FOAF_LOGO, 370);
		propOrderMap.put(WebEntityConstants.EUROPEANA_ROLE, 380);
		propOrderMap.put(WebEntityConstants.ORGANIZATION_DOMAIN, 390);
		propOrderMap.put(WebEntityConstants.GEOGRAPHIC_LEVEL, 400);
		propOrderMap.put(WebEntityConstants.COUNTRY, 410);
		propOrderMap.put(WebEntityConstants.FOAF_HOMEPAGE, 420);
		propOrderMap.put(WebEntityConstants.FOAF_PHONE, 430);
		propOrderMap.put(WebEntityConstants.FOAF_MBOX, 440);
		propOrderMap.put(WebEntityConstants.HAS_ADDRESS, 450);
		
		//Agent - relations
		propOrderMap.put(WebEntityConstants.HAS_PART, 460);
		propOrderMap.put(WebEntityConstants.IS_PART_OF, 470);
		propOrderMap.put(WebEntityConstants.HAS_MET, 480);
		propOrderMap.put(WebEntityConstants.IS_RELATED_TO, 490);
		propOrderMap.put(WebEntityConstants.WAS_PRESENT_AT, 500);
		propOrderMap.put(WebEntityConstants.IDENTIFIER, 510);
		//generic/other relations
		propOrderMap.put(WebEntityConstants.IS_NEXT_IN_SEQUENCE, 520);
		propOrderMap.put(WebEntityConstants.IN_SCHEME, 530);
		propOrderMap.put(WebEntityConstants.SAME_AS, 540);
		//address
		propOrderMap.put(WebEntityConstants.STREET_ADDRESS, 1010);
		propOrderMap.put(WebEntityConstants.LOCALITY, 1020);
		propOrderMap.put(WebEntityConstants.REGION, 1030);
		propOrderMap.put(WebEntityConstants.POSTAL_CODE, 1040);
		propOrderMap.put(WebEntityConstants.COUNTRY_NAME, 1050);
		propOrderMap.put(WebEntityConstants.POST_OFFICE_BOX, 1060);
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
