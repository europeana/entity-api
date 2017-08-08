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

package eu.europeana.entity.web.jsonld;

import java.util.Comparator;
import java.util.HashMap;
import org.apache.stanbol.commons.jsonld.JsonLdCommon;

/**
 * This class is specific to annotations must be moved to (europeana) annotation
 * repository A comparator for JSON-LD maps to ensure the order of certain key
 * elements like '#', '@', 'a' in JSON-LD output.
 *
 * @author Jan Roerden
 */

public class EntityJsonComparator implements Comparator<Object> {

	public static final String _ID = "id";
	public static final String _TYPE = "type";
	public static final String DEPICTION = "depiction";
	public static final String PREF_LABEL = "prefLabel";
	public static final String ALT_LABEL = "altLabel";
	public static final String LAT = "lat";
	public static final String LONG = "long";
	public static final String ALT = "alt";
	public static final String NAME = "name";
	public static final String BEGIN = "begin";
	public static final String DATE_OF_BIRTH = "dateOfBirth";
	public static final String END = "end";
	public static final String DATEOFDEATH="rdaGr2:dateofdeath";
	public static final String DATE="date";
	public static final String PLACEOFBIRTH="rdaGr2:placeOfBirth";
	public static final String PLACEOFDEATH="rdaGr2:placeOfDeath";
	public static final String DATEOFESTABLISHMENT="rdaGr2:dateOfEstablishment";
	public static final String DATEOFTERMINATION="rdaGr2:dateOfTermination";
	public static final String GENDER="gender";
	public static final String PROFESSIONOROCCUPATION="professionOrOccupation";
	public static final String NOTE="note";
	public static final String BIOGRAPHICALINFORMATION="biographicalInformation";
	public static final String HASPART="hasPart";
	public static final String ISPARTOF="isPartOf";
	public static final String HASMET="hasMet";
	public static final String ISRELATEDTO="isRelatedTo";
	public static final String IDENTIFIER="identifier";
	public static final String ISNEXTINSEQUENCE="isNextInSequence";
	public static final String CONCEPT="Concept";
	public static final String NOTATION="notation";
	public static final String BROADER="broader";
	public static final String RELATED="related";
	public static final String BROADMATCH="broadMatch";
	public static final String NARROWMATCH="narrowMatch";
	public static final String RELATEDMATCH="relatedMatch";
	public static final String EXACTMATCH="exactMatch";
	public static final String CLOSEMATCH="closeMatch";
	public static final String SAMEAS="sameAs";

	static final HashMap<String, Integer> propOrderMap = new HashMap<String, Integer>();
	static {
		propOrderMap.put(JsonLdCommon._ID, 10);
		propOrderMap.put(JsonLdCommon._TYPE, 20);
		propOrderMap.put(DEPICTION, 30);
		propOrderMap.put(PREF_LABEL, 40);
		propOrderMap.put(ALT_LABEL, 50);
		propOrderMap.put(LAT, 60);
		propOrderMap.put(LONG, 70);
		propOrderMap.put(ALT, 80);
		propOrderMap.put(NAME, 90);
		propOrderMap.put(BEGIN, 100);
		propOrderMap.put(DATE_OF_BIRTH, 110);
		propOrderMap.put(END, 120);
		propOrderMap.put(DATEOFDEATH, 130);
		propOrderMap.put(DATE, 140);
		propOrderMap.put(PLACEOFBIRTH, 150);
		propOrderMap.put(PLACEOFDEATH, 160);
		propOrderMap.put(DATEOFESTABLISHMENT, 170);
		propOrderMap.put(DATEOFTERMINATION, 180);
		propOrderMap.put(GENDER, 190);
		propOrderMap.put(PROFESSIONOROCCUPATION, 200);
		propOrderMap.put(NOTE, 210);
		propOrderMap.put(BIOGRAPHICALINFORMATION, 220);
		propOrderMap.put(HASPART, 230);
		propOrderMap.put(ISPARTOF, 240);
		propOrderMap.put(HASMET, 250);
		propOrderMap.put(ISRELATEDTO, 260);
		propOrderMap.put(IDENTIFIER, 270);
		propOrderMap.put(ISNEXTINSEQUENCE, 280);
		propOrderMap.put(CONCEPT, 290);
		propOrderMap.put(NOTATION, 300);
		propOrderMap.put(BROADER, 310);
		propOrderMap.put(RELATED, 320);
		propOrderMap.put(BROADMATCH, 330);
		propOrderMap.put(NARROWMATCH, 340);
		propOrderMap.put(RELATEDMATCH, 350);
		propOrderMap.put(EXACTMATCH, 360);
		propOrderMap.put(CLOSEMATCH, 370);
		propOrderMap.put(SAMEAS, 380);
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
