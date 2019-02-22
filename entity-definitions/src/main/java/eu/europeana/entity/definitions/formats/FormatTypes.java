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

package eu.europeana.entity.definitions.formats;

import eu.europeana.entity.definitions.exceptions.UnsupportedFormatTypeException;

/**
 * Enumerates the formats
 * 
 * @author GrafR
 *
 */
public enum FormatTypes {
	jsonld("jsonld"), // default format
	schema("schema.jsonld"); // a schema.org format
	
	private String extension;

	public String getExtention() {
		return extension;
	}

	FormatTypes(String extention){
		this.extension = extention;
	}
		
	/**
	 * This method selects format type by Format type name.
	 * @param extension
	 * @return The format type
	 * @throws UnsupportedFormatTypeException
	 */
	public static FormatTypes getByExtention(String extention) throws UnsupportedFormatTypeException{

		for(FormatTypes formatType : FormatTypes.values()){
			if(formatType.getExtention().equalsIgnoreCase(extention))
				return formatType;
		}
		throw new UnsupportedFormatTypeException(extention);
	}	

	
	/**
	 * Get format type by name.
	 * @param name
	 * @return The FormatTypes field
	 * @throws UnsupportedFormatTypeException
	 */
	public static FormatTypes getByName(String name) throws UnsupportedFormatTypeException{

		for(FormatTypes formatType : FormatTypes.values()) {
			if(formatType.name().equalsIgnoreCase(name))
				return formatType;
		}
		throw new UnsupportedFormatTypeException(name);
	}	
	
	/**
	 * This method is used for format validation
	 * @param formatType
	 * @return true if exists
	 */
	public static boolean contains(String formatType) {

	    for (FormatTypes field : FormatTypes.values()) {
	        if (field.getExtention().equalsIgnoreCase(formatType)) {
	            return true;
	        }
	    }

	    return false;
	}	
		
}
