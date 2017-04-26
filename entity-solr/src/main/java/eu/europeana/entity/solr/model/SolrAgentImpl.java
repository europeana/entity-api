package eu.europeana.entity.solr.model;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.beans.Field;

import eu.europeana.entity.definitions.model.Agent;
import eu.europeana.entity.definitions.model.impl.BaseAgent;
import eu.europeana.entity.definitions.model.vocabulary.AgentSolrFields;
import eu.europeana.entity.definitions.model.vocabulary.ConceptSolrFields;

public class SolrAgentImpl extends BaseAgent implements Agent {

	// Need to annotate concept fields as well as this class doesn't extend the
	// SolrConceptImpl
	@Override
	@Field(ConceptSolrFields.RDF_ABOUT)
	public void setAbout(String about) {
		super.setAbout(about);
	}
	
//	@Override
//	@Field(AgentSolrFields.SAME_AS)
//	public void setSameAs(String[] sameAs) {
//		super.setSameAs(sameAs);
//	}
	
	@Override
	@Field(AgentSolrFields.COREF)
	public void setCoref(String[] coref) {
		super.setCoref(coref);
	}

	@Override
	@Field(AgentSolrFields.INTERNAL_TYPE)
	public void setInternalType(String internalType) {
		super.setInternalType(internalType);
	}

	@Override
	@Field(AgentSolrFields.IDENTIFIER)
	public void setIdentifier(String[] identifier) {
		super.setIdentifier(identifier);
	}
	
	@Override
	@Field(ConceptSolrFields.ID)
	public void setEntityId(String entityId) {
		super.setEntityId(entityId);
	}
	
	/**
	 * Concept fields
	 */
	@Override
	@Field(AgentSolrFields.HAS_PART)
	public void setHasPart(String[] hasPart) {
		super.setHasPart(hasPart);
	}

	@Override
	@Field(AgentSolrFields.IS_PART_OF)
	public void setIsPartOf(String[] isPartOf) {
		super.setIsPartOf(isPartOf);
	}

	@Override
	@Field(AgentSolrFields.IS_RELATED_TO)
	public void setIsRelatedTo(String[] isRelatedTo) {
		super.setIsRelatedTo(isRelatedTo);
	}

	@Override
	@Field(ConceptSolrFields.DEFINITION)
	public void setDefinition(String definition) {
		setDefinition(definition);
	}

	@Override
	@Field(ConceptSolrFields.PREF_LABEL_ALL)
	public void setPrefLabel(Map<String, List<String>> prefLabel) {
		super.setPrefLabel(prefLabel);
	}

	@Override
	@Field(ConceptSolrFields.ALT_LABEL_ALL)
	public void setAltLabel(Map<String, List<String>> altLabel) {
		super.setAltLabel(altLabel);
	}

	@Override
	@Field(ConceptSolrFields.HIDDEN_LABEL)
	public void setHiddenLabel(Map<String, List<String>> hiddenLabel) {
		super.setHiddenLabel(hiddenLabel);
	}

	@Override
	@Field(ConceptSolrFields.NOTE_ALL)
	public void setNote(Map<String, List<String>> note) {
		super.setNote(note);
	}
	
	@Override
	@Field(ConceptSolrFields.DEPICTION)
	public void setDepiction(String depiction) {
		super.setDepiction(depiction);
	}
	

	// Agent Specific Fields
	@Override
	@Field(AgentSolrFields.BEGIN)
	public void setBegin(String[] begin) {
		super.setBegin(begin);
	}

	@Override
	@Field(AgentSolrFields.END)
	public void setEnd(String[] end) {
		super.setEnd(end);
	}

	@Override
	@Field(AgentSolrFields.HAS_MET)
	public void setHasMet(String hasMet) {
		super.setHasMet(hasMet);
	}
	@Override
	@Field(AgentSolrFields.NAME)
	public void setName(Map<String, String> name) {
		super.setName(name);
	}
	
	@Override
	@Field(AgentSolrFields.BIOGRAPHICAL_INFORMATION_ALL)
	public void setBiographicalInformation(Map<String, List<String>> biographicalInformation) {
		super.setBiographicalInformation(biographicalInformation);
	}

	@Override
	@Field(AgentSolrFields.DATE_OF_BIRTH_ALL)
	public void setDateOfBirth(String[] dateOfBirth) {
		super.setDateOfBirth(dateOfBirth);
	}

	@Override
	@Field(AgentSolrFields.DATE_OF_DEATH_ALL)
	public void setDateOfDeath(String[] dateOfDeath) {
		super.setDateOfDeath(dateOfDeath);
	}

	@Override
	@Field(AgentSolrFields.PLACE_OF_BIRTH_ALL)
	public void setPlaceOfBirth(Map<String, List<String>> placeOfBirth) {
		super.setPlaceOfBirth(placeOfBirth);
	}

	@Override
	@Field(AgentSolrFields.PLACE_OF_DEATH_ALL)
	public void setPlaceOfDeath(Map<String, List<String>> placeOfDeath) {
		super.setPlaceOfDeath(placeOfDeath);
	}

	@Override
	@Field(AgentSolrFields.DATE_OF_ESTABLISHMENT)
	public void setDateOfEstablishment(String dateOfEstablishment) {
		super.setDateOfEstablishment(dateOfEstablishment);
	}

	@Override
	@Field(AgentSolrFields.GENDER)
	public void setGender(String gender) {
		super.setGender(gender);
	}
	
	@Override
	@Field(AgentSolrFields.PROFESSION_OR_OCCUPATION_ALL)
	public void setProfessionOrOccupation(Map<String, List<String>> professionOrOccupation) {
		super.setProfessionOrOccupation(professionOrOccupation);
	}


	// Technical Fields
	@Override
	@Field(ConceptSolrFields.TIMESTAMP)
	public void setTimestamp(Date timestamp) {
		super.setTimestamp(timestamp);
	}

	@Override
	// @Field(SkosAgentSolrFields.WIKIPEDIA_CLICKS)
	public void setWikipediaClicks(int wikipediaClicks) {
		super.setWikipediaClicks(wikipediaClicks);
	}

	@Override
	// @Field(SkosAgentSolrFields.EUROPEANA_DOC_COUNT)
	public void setEuropeanaDocCount(int europeanaDocCount) {
		super.setEuropeanaDocCount(europeanaDocCount);
	}

	@Override
	// @Field(SkosAgentSolrFields.DERIVED_SCORE)
	public void setDerivedScore(float derivedScore) {
		super.setDerivedScore(derivedScore);
	}

	@Override
	@Field(AgentSolrFields.DATE)
	public void setDate(Date date) {
		super.setDate(date);
	}
}
