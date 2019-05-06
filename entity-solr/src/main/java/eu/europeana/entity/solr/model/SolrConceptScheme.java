package eu.europeana.entity.solr.model;

import java.util.Map;

import eu.europeana.entity.definitions.model.ConceptScheme;

public interface SolrConceptScheme extends ConceptScheme {

    Map<String, String> getPrefixedPrefLabel();

    void setPrefixedDefinition(Map<String, String> definition);

    Map<String, String> getPrefixedDefinition();

    void setPrefixedPrefLabel(Map<String, String> prefLabel);

}
