package eu.europeana.entity.solr.view;

import java.util.Date;

import eu.europeana.entity.definitions.model.vocabulary.EntityTypes;
import eu.europeana.entity.solr.model.SolrAgentImpl;
import eu.europeana.entity.solr.model.SolrConceptImpl;
import eu.europeana.entity.web.model.view.ConceptView;

public class AgentViewAdapter extends SolrAgentImpl implements ConceptView {

	@Override
	public String getEntityId() {
		return super.getEntityId();
	}

	@Override
	public Date getTimestampUpdated() {
		return super.getTimestamp();
	}

	@Override
	public String getEntityType() {
		return EntityTypes.valueOf(getInternalType()).getJsonValue();
	}

}
