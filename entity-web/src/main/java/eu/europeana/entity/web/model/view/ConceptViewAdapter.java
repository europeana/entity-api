package eu.europeana.entity.web.model.view;

import java.util.Date;

import eu.europeana.entity.solr.model.SolrConceptImpl;

public class ConceptViewAdapter extends SolrConceptImpl implements ConceptView {

	@Override
	public String getEntityId() {
		return getEntityId();
	}

	@Override
	public Date getTimestampUpdated() {
		return null;
	}

}
