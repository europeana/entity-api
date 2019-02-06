package eu.europeana.entity.solr.model;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.solr.client.solrj.beans.Field;

import eu.europeana.entity.definitions.model.Concept;
import eu.europeana.entity.definitions.model.impl.BaseConcept;
import eu.europeana.entity.definitions.model.vocabulary.AgentSolrFields;
import eu.europeana.entity.definitions.model.vocabulary.ConceptSolrFields;


public class SolrConceptImpl extends BaseConcept implements Concept{

	@Override
	@Field(ConceptSolrFields.BROADER)
	public void setBroader(String[] broader) {
		super.setBroader(broader);
	}
	
	@Override
	@Field(ConceptSolrFields.NARROWER)
	public void setNarrower(String[] narrower) {
		super.setNarrower(narrower);
	}
	
	@Override
	@Field(ConceptSolrFields.RELATED)
	public void setRelated(String[] related) {
		super.setRelated(related);
	}
	
	@Override
	@Field(ConceptSolrFields.BROAD_MATCH)
	public void setBroadMatch(String[] broadMatch) {
		super.setBroadMatch(broadMatch);
	}
	
	@Override
	@Field(ConceptSolrFields.NARROW_MATCH)
	public void setNarrowMatch(String[] narrowMatch) {
		super.setNarrowMatch(narrowMatch);
	}
	
	@Override
	@Field(ConceptSolrFields.EXACT_MATCH)
	public void setExactMatch(String[] exactMatch) {
		super.setExactMatch(exactMatch);
	}
	
	@Override
	@Field(ConceptSolrFields.RELATED_MATCH)
	public void setRelatedMatch(String[] relatedMatch) {
		super.setRelatedMatch(relatedMatch);
	}
	
	@Override
	@Field(ConceptSolrFields.CLOSE_MATCH)
	public void setCloseMatch(String[] closeMatch) {
		setCloseMatch(closeMatch);
	}
	
	@Override
	@Field(ConceptSolrFields.NOTATION_ALL)
	public void setNotation(Map<String, List<String>> notation) {
		super.setNotation(notation);
	}
	
	@Override
	@Field(ConceptSolrFields.IN_SCHEME)
	public void setInScheme(String[] inScheme) {
		super.setInScheme(inScheme);
	}
	
	@Override
	@Field(ConceptSolrFields.RDF_ABOUT)
	public void setAbout(String about) {
		super.setAbout(about);
	}
	
	@Override
	@Field(ConceptSolrFields.DEFINITION)
	public void setDefinition(String definition) {
		setDefinition(definition);
	}
	
	@Override
	@Field(ConceptSolrFields.PREF_LABEL_ALL)
	public void setPrefLabelStringMap(Map<String, String> prefLabel) {
		Map<String, String> normalizedPrefLabel = normalizePrefLabel(prefLabel);
		super.setPrefLabelStringMap(normalizedPrefLabel);
	}
	
	/**
	 * This method removes unnecessary prefixes from the fields e.g. "skos_prefLabel"
	 * @param prefLabel
	 * @return
	 */
	private Map<String, String> normalizePrefLabel(Map<String, String> prefLabel) {
		Map<String, String> res;
		int prefixLen = ConceptSolrFields.PREF_LABEL.length() + 1;
		if (prefLabel.keySet().iterator().next().startsWith(ConceptSolrFields.PREF_LABEL)) {
			res = prefLabel.entrySet().stream().collect(Collectors.toMap(
					entry -> entry.getKey().substring(prefixLen), 
					entry -> entry.getValue())
			);	
		} else {
			res = prefLabel;
		}
		return res;
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
	@Field(ConceptSolrFields.ID)
	public void setEntityId(String entityId) {
		super.setEntityId(entityId);
	}

	@Override
	@Field(ConceptSolrFields.TIMESTAMP)
	public void setTimestamp(Date timestamp) {
		super.setTimestamp(timestamp);
	}
	
	@Override
	@Field(ConceptSolrFields.INTERNAL_TYPE)
	public void setInternalType(String internalType) {
		super.setInternalType(internalType);
	}
	
	@Override
	@Field(AgentSolrFields.IS_RELATED_TO)
	public void setIsRelatedTo(String[] isRelatedTo) {
		super.setIsRelatedTo(isRelatedTo);
	}

	@Override
	@Field(ConceptSolrFields.DEPICTION)
	public void setDepiction(String depiction) {
		super.setDepiction(depiction);
	}

	@Override
	@Field(AgentSolrFields.SAME_AS)
	public void setSameAs(String[] sameAs) {
		super.setSameAs(sameAs);
	}
}
