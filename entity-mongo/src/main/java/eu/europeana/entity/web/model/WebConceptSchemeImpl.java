package eu.europeana.entity.web.model;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import eu.europeana.entity.definitions.model.vocabulary.WebEntityFields;
import eu.europeana.entity.mongo.model.PersistentConceptSchemeImpl;
import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldProperty;

@JsonPropertyOrder({ "id", "type", "prefLabel", "definition", "isDefinedBy", "sameAs", "created", "modified", "total" })
@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
public class WebConceptSchemeImpl extends PersistentConceptSchemeImpl {
		
//	@JsonldProperty("conceptSchemeId")
//	public void setConceptSchemeId(String conceptSchemeId) {
//		super.setConceptSchemeId(conceptSchemeId);
//	}
	
	@JsonIgnore
	public String getConceptSchemeId() {
		return super.getConceptSchemeId();
	}
		
	@JsonldProperty("type")
	public void setType(String type) {
		super.setType(type);
	}
	
	@JsonldProperty("prefLabel")
	public Map<String, String> getPrefLabel() {
		return super.getPrefLabel();
	}

	@JsonldProperty("definition")
	public Map<String, String> getDefinition() {
		return super.getDefinition();
	}		

	@JsonldProperty("@context")	
	@JsonIgnore
	public String getContext() {
		return super.getContext();
	}
	
	@JsonldProperty("isDefinedBy")	
	public String getIsDefinedBy() {
		return super.getIsDefinedBy();
	}
	
	@JsonldProperty("sameAs")	
	public String getSameAs() {
		return super.getSameAs();
	}
	
	@JsonProperty("@context")
	@Override
	public void setContext(String context) {
		super.setContext(context);
	}
	
	/**
	 * This method presents IP as URL.
	 * @param id The user set id
	 * @param base The base URL
	 * @return string presenting ID as URL
	 */
	@JsonldProperty("id")
	public String getId() {
		StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append(WebEntityFields.BASE_CONCEPT_SCHEME_URL); 
        urlBuilder.append(super.getConceptSchemeId()); 
        return urlBuilder.toString();
	}
	
	@JsonldProperty("total")
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
		if (getPrefLabel() != null && StringUtils.isNotEmpty(getPrefLabel().toString())) {
			resBuilder.append("PrefLabel: ");
			resBuilder.append(getPrefLabel());
		}
		if (StringUtils.isNotEmpty(getConceptSchemeId())) {
			resBuilder.append(", ConceptSchemeId: ");
			resBuilder.append(getConceptSchemeId());
		}
		resBuilder.append("]");
        return resBuilder.toString();		
	}
	
	@Override
	@JsonIgnore
	public ObjectId getObjectId() {
		return super.getObjectId();
	}
	
}
