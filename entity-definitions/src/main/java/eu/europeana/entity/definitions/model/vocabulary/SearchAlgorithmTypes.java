package eu.europeana.entity.definitions.model.vocabulary;

import eu.europeana.entity.definitions.exceptions.UnsupportedAlgorithmTypeException;

public enum SearchAlgorithmTypes {

	suggest, searchByLabel;
	
	public static SearchAlgorithmTypes getByName(String name) throws UnsupportedAlgorithmTypeException{

		for(SearchAlgorithmTypes algorithmType : SearchAlgorithmTypes.values()){
			if(algorithmType.name().equalsIgnoreCase(name))
				return algorithmType;
		}
		throw new UnsupportedAlgorithmTypeException(name);
	}	
	
}
