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
		propOrderMap.put(WebEntityConstants.IS_SHOWN_BY, 51);
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
//		propOrderMap.put(WebEntityConstants.IS_SHOWN_BY, 215); //2200
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
		propOrderMap.put(WebEntityConstants.ACRONYM, 65);
		propOrderMap.put(WebEntityConstants.DESCRIPTION, 360);
		propOrderMap.put(WebEntityConstants.FOAF_LOGO, 370);
		propOrderMap.put(WebEntityConstants.EUROPEANA_ROLE, 380);
		propOrderMap.put(WebEntityConstants.ORGANIZATION_DOMAIN, 390);
		propOrderMap.put(WebEntityConstants.GEOGRAPHIC_LEVEL, 400);
		propOrderMap.put(WebEntityConstants.COUNTRY, 410);
		propOrderMap.put(WebEntityConstants.FOAF_HOMEPAGE, 420);
		propOrderMap.put(WebEntityConstants.FOAF_PHONE, 430);
		propOrderMap.put(WebEntityConstants.FOAF_MBOX, 440);
		//Organization - relations
		propOrderMap.put(WebEntityConstants.HAS_ADDRESS, 510);
		
		//Agent - relations
		propOrderMap.put(WebEntityConstants.HAS_PART, 460);
		propOrderMap.put(WebEntityConstants.IS_PART_OF, 470);
		propOrderMap.put(WebEntityConstants.HAS_MET, 480);
		propOrderMap.put(WebEntityConstants.IS_RELATED_TO, 490);
		propOrderMap.put(WebEntityConstants.WAS_PRESENT_AT, 500);
		
		//generic/other relations
		propOrderMap.put(WebEntityConstants.IDENTIFIER, 620);
		propOrderMap.put(WebEntityConstants.IS_NEXT_IN_SEQUENCE, 630);
		propOrderMap.put(WebEntityConstants.IN_SCHEME, 640);
		propOrderMap.put(WebEntityConstants.SAME_AS, 650);
		//address
		propOrderMap.put(WebEntityConstants.STREET_ADDRESS, 1010);
		propOrderMap.put(WebEntityConstants.POSTAL_CODE, 1020);
		propOrderMap.put(WebEntityConstants.POST_OFFICE_BOX, 1030);
		propOrderMap.put(WebEntityConstants.LOCALITY, 1040);
		propOrderMap.put(WebEntityConstants.REGION, 1050);
		propOrderMap.put(WebEntityConstants.COUNTRY_NAME, 1060);
		
		//Page
		propOrderMap.put(WebEntityConstants.PART_OF, 2050);
		propOrderMap.put(WebEntityConstants.PREV, 2090);
		propOrderMap.put(WebEntityConstants.NEXT, 2100);
		propOrderMap.put(WebEntityConstants.FACETS, 2110);
		propOrderMap.put(WebEntityConstants.ITEMS, 2120);
		
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
