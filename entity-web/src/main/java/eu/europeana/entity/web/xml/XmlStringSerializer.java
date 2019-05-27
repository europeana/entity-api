package eu.europeana.entity.web.xml;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdScalarSerializer;

public class XmlStringSerializer extends StdScalarSerializer<String>{

    
    protected XmlStringSerializer() {
	super(String.class);
	// TODO Auto-generated constructor stub
    }
    
//    protected XmlStringSerializer(Class<String> t) {
//	super(t);
//	// TODO Auto-generated constructor stub
//    }

    @Override
    public void serialize(String value, JsonGenerator gen, SerializerProvider provider) throws IOException {
	// TODO Auto-generated method stub
	gen.writeRaw(value);
    }
    
}
