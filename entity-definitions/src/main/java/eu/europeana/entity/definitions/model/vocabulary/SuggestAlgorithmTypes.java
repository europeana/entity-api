package eu.europeana.entity.definitions.model.vocabulary;

import eu.europeana.entity.definitions.exceptions.UnsupportedAlgorithmTypeException;

public enum SuggestAlgorithmTypes {

	suggest, suggestByLabel, suggestForLanguage;
	
	public static SuggestAlgorithmTypes getByName(String name) throws UnsupportedAlgorithmTypeException{

		for(SuggestAlgorithmTypes algorithmType : SuggestAlgorithmTypes.values()){
			if(algorithmType.name().equalsIgnoreCase(name))
				return algorithmType;
		}
		throw new UnsupportedAlgorithmTypeException(name);
	}	
	
}
