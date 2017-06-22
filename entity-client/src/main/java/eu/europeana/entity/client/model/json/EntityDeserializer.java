package eu.europeana.entity.client.model.json;

import java.lang.reflect.Type;
import java.util.Date;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import eu.europeana.entity.definitions.model.Concept;
import eu.europeana.entity.definitions.model.impl.BaseConcept;

public class EntityDeserializer implements JsonDeserializer<Concept>{

	Gson gson = null;
	
	@Override
	public Concept deserialize(JsonElement json, Type typeOfT,
			JsonDeserializationContext context) throws JsonParseException {

		Class<? extends Concept> concreteClass = null;

		JsonObject jsonObj = json.getAsJsonObject();
//		for (Map.Entry<String, JsonElement> attribute : jsonObj.entrySet()) {
//			if (ModelConst.TYPE.equals(attribute.getKey())) {
//				concreteClass = ConceptObjectFactory.getInstance().getClassForType((attribute.getValue().getAsString()));
//				break;
//			}
//		}
		concreteClass = BaseConcept.class;

		return (Concept) getGson().fromJson(jsonObj, concreteClass);
	}

	private Gson getGson() {
		if(gson == null){
		
		GsonBuilder gsonBuilder = new GsonBuilder();
//		gsonBuilder.registerTypeAdapter(Date.class, new DateDeserializer());//setDateFormat(ModelConst.GSON_DATE_FORMAT);
		gsonBuilder.registerTypeAdapter(Date.class, new JsonDateDeserializer());
		//		gsonBuilder.setDateFormat(ModelConst.GSON_DATE_FORMAT);
		gson = gsonBuilder.create();			
		}
		return gson;
	}
		
	public class JsonDateDeserializer implements JsonDeserializer<Date> {
		
	   public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) 
			   throws JsonParseException {
	      String s = json.getAsJsonPrimitive().getAsString();
	      long l = Long.parseLong(s.substring(6, s.length() - 2));
	      Date d = new Date(l);
	      return d; 
	   } 
	}

}
