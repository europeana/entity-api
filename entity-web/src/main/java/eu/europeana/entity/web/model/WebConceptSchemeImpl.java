package eu.europeana.entity.web.model;

import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import eu.europeana.entity.definitions.model.vocabulary.WebEntityFields;
import eu.europeana.entity.mongo.model.PersistentConceptSchemeImpl;
import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldProperty;


@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(value = {WebEntityFields.CONTEXT, WebEntityFields.TYPE, WebEntityFields.ID})
public class WebConceptSchemeImpl extends PersistentConceptSchemeImpl {

    
//    @JsonldProperty(WebConceptSchemeModelFields.TYPE)
//    public String getType() {
//	return super.getType();
//    }

    @JsonldProperty(WebEntityFields.PREF_LABEL)
    public Map<String, String> getPrefLabelStringMap() {
	return super.getPrefLabelStringMap();
    }
    
    @Override
    @JsonldProperty(WebEntityFields.PREF_LABEL)
    public void setPrefLabelStringMap(Map<String, String> prefLabel) {
        super.setPrefLabelStringMap(prefLabel);
    }
    
    @Override
    @JsonIgnore
    public void setPrefLabel(Map<String, List<String>> prefLabel) {
//        super.setPrefLabel(prefLabel);
    }

    @JsonldProperty(WebEntityFields.DEFINITION)
    public Map<String, String> getDefinition() {
	return super.getDefinition();
    }


    @JsonldProperty(WebEntityFields.IS_DEFINED_BY)
    public String getIsDefinedBy() {
	return super.getIsDefinedBy();
    }

    @JsonldProperty(WebEntityFields.SAME_AS)
    public String[] getSameAs() {
	return super.getSameAs();
    }

   
    @JsonldProperty(WebEntityFields.TOTAL)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    public int getTotal() {
	return super.getTotal();
    }

    @JsonIgnore
    public boolean isDisabled() {
	return super.isDisabled();
    }

    public String toString() {
	StringBuilder resBuilder = new StringBuilder();
	resBuilder.append("WebConceptScheme [");
	resBuilder.append("PrefLabel: ").append(getPrefLabelStringMap());
	resBuilder.append(", EntityIdentifier: ").append(getEntityIdentifier());
	resBuilder.append("]");
	return resBuilder.toString();
    }

    @Override
    @JsonIgnore
    public ObjectId getObjectId() {
	return super.getObjectId();
    }

}
