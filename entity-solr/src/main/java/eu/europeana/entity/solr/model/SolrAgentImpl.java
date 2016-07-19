package eu.europeana.entity.solr.model;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.beans.Field;

import eu.europeana.entity.definitions.model.Agent;
import eu.europeana.entity.definitions.model.impl.BaseAgent;
import eu.europeana.entity.definitions.model.vocabulary.SkosAgentSolrFields;
import eu.europeana.entity.definitions.model.vocabulary.SkosConceptSolrFields;

public class SolrAgentImpl extends BaseAgent implements Agent {

	// Need to annotate concept fields as well as this class doesn't extend the
	// SolrConceptImpl
	@Override
	@Field(SkosConceptSolrFields.RDF_ABOUT)
	public void setAbout(String about) {
		super.setAbout(about);
	}
	
	@Override
	@Field(SkosAgentSolrFields.SAME_AS)
	public void setSameAs(String[] sameAs) {
		super.setSameAs(sameAs);
	}

	@Override
	@Field(SkosAgentSolrFields.INTERNAL_TYPE)
	public void setInternalType(String internalType) {
		super.setInternalType(internalType);
	}

	@Override
	@Field(SkosAgentSolrFields.IDENTIFIER)
	public void setIdentifier(String identifier) {
		super.setIdentifier(identifier);
	}
	
	@Override
	@Field(SkosConceptSolrFields.ENTITY_ID)
	public void setEntityId(String entityId) {
		super.setEntityId(entityId);
	}
	
	/**
	 * Concept fields
	 */
	@Override
	@Field(SkosAgentSolrFields.HAS_PART)
	public void setHasPart(String[] hasPart) {
		super.setHasPart(hasPart);
	}

	@Override
	@Field(SkosAgentSolrFields.IS_PART_OF)
	public void setIsPartOf(String[] isPartOf) {
		super.setIsPartOf(isPartOf);
	}

	@Override
	@Field(SkosAgentSolrFields.IS_RELATED_TO)
	public void setIsRelatedTo(String[] isRelatedTo) {
		super.setIsRelatedTo(isRelatedTo);
	}

	@Override
	@Field(SkosConceptSolrFields.BROADER)
	public void setBroader(String[] broader) {
		super.setBroader(broader);
	}

	@Override
	@Field(SkosConceptSolrFields.NARROWER)
	public void setNarrower(String[] narrower) {
		super.setNarrower(narrower);
	}

	@Override
	@Field(SkosConceptSolrFields.RELATED)
	public void setRelated(String[] related) {
		super.setRelated(related);
	}

	@Override
	@Field(SkosConceptSolrFields.BROAD_MATCH)
	public void setBroadMatch(String[] broadMatch) {
		super.setBroadMatch(broadMatch);
	}

	@Override
	@Field(SkosConceptSolrFields.NARROW_MATCH)
	public void setNarrowMatch(String[] narrowMatch) {
		super.setNarrowMatch(narrowMatch);
	}

	@Override
	@Field(SkosConceptSolrFields.EXACT_MATCH)
	public void setExactMatch(String[] exactMatch) {
		super.setExactMatch(exactMatch);
	}

	@Override
	@Field(SkosConceptSolrFields.RELATED_MATCH)
	public void setRelatedMatch(String[] relatedMatch) {
		super.setRelatedMatch(relatedMatch);
	}

	@Override
	@Field(SkosConceptSolrFields.CLOSE_MATCH)
	public void setCloseMatch(String[] closeMatch) {
		setCloseMatch(closeMatch);
	}

	@Override
	@Field(SkosConceptSolrFields.NOTATION_ALL)
	public void setNotation(Map<String, List<String>> notation) {
		super.setNotation(notation);
	}

	@Override
	@Field(SkosConceptSolrFields.IN_SCHEME)
	public void setInScheme(String[] inScheme) {
		super.setInScheme(inScheme);
	}

	@Override
	@Field(SkosConceptSolrFields.DEFINITION)
	public void setDefinition(String definition) {
		setDefinition(definition);
	}

	@Override
	@Field(SkosConceptSolrFields.PREF_LABEL_ALL)
	public void setPrefLabel(Map<String, List<String>> prefLabel) {
		super.setPrefLabel(prefLabel);
	}

	@Override
	@Field(SkosConceptSolrFields.ALT_LABEL_ALL)
	public void setAltLabel(Map<String, List<String>> altLabel) {
		super.setAltLabel(altLabel);
	}

	@Override
	@Field(SkosConceptSolrFields.HIDDEN_LABEL)
	public void setHiddenLabel(Map<String, List<String>> hiddenLabel) {
		super.setHiddenLabel(hiddenLabel);
	}

	@Override
	@Field(SkosConceptSolrFields.NOTE_ALL)
	public void setNote(Map<String, List<String>> note) {
		super.setNote(note);
	}

	

	// Agent Specific Fields
	@Override
	@Field(SkosAgentSolrFields.BEGIN)
	public void setBegin(String[] begin) {
		super.setBegin(begin);
	}

	@Override
	@Field(SkosAgentSolrFields.END)
	public void setEnd(String[] end) {
		super.setEnd(end);
	}

	@Override
	@Field(SkosAgentSolrFields.HAS_MET)
	public void setHasMet(String hasMet) {
		super.setHasMet(hasMet);
	}
	@Override
	@Field(SkosAgentSolrFields.NAME)
	public void setName(Map<String, String> name) {
		super.setName(name);
	}
	
	@Override
	@Field(SkosAgentSolrFields.BIOGRAPHICAL_INFORMATION_ALL)
	public void setBiographicalInformation(Map<String, List<String>> biographicalInformation) {
		super.setBiographicalInformation(biographicalInformation);
	}

	@Override
	@Field(SkosAgentSolrFields.DATE_OF_BIRTH)
	public void setDateOfBirth(String[] dateOfBirth) {
		super.setDateOfBirth(dateOfBirth);
	}

	@Override
	@Field(SkosAgentSolrFields.DATE_OF_DEATH)
	public void setDateOfDeath(String[] dateOfDeath) {
		super.setDateOfDeath(dateOfDeath);
	}

	@Override
	@Field(SkosAgentSolrFields.PLACE_OF_BIRTH_ALL)
	public void setPlaceOfBirth(Map<String, List<String>> placeOfBirth) {
		super.setPlaceOfBirth(placeOfBirth);
	}

	@Override
	@Field(SkosAgentSolrFields.PLACE_OF_DEATH_ALL)
	public void setPlaceOfDeath(Map<String, List<String>> placeOfDeath) {
		super.setPlaceOfDeath(placeOfDeath);
	}

	@Override
	@Field(SkosAgentSolrFields.DATE_OF_ESTABLISHMENT)
	public void setDateOfEstablishment(String dateOfEstablishment) {
		super.setDateOfEstablishment(dateOfEstablishment);
	}

	@Override
	@Field(SkosAgentSolrFields.GENDER)
	public void setGender(String gender) {
		super.setGender(gender);
	}
	
	@Override
	@Field(SkosAgentSolrFields.PROFESSION_OR_OCCUPATION_ALL)
	public void setProfessionOrOccupation(Map<String, List<String>> professionOrOccupation) {
		super.setProfessionOrOccupation(professionOrOccupation);
	}


	// Technical Fields
	@Override
	@Field(SkosConceptSolrFields.TIMESTAMP)
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
	@Field(SkosAgentSolrFields.DATE)
	public void setDate(Date date) {
		super.setDate(date);
	}
}
