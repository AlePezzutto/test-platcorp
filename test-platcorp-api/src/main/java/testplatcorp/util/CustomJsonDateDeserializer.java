package testplatcorp.util;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class CustomJsonDateDeserializer extends JsonDeserializer<Date> {

	@Override
	public Date deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String jsonDate = p.getText();
		
		try {
			return sdf.parse(jsonDate);
		}
		catch(ParseException e) {
			throw new RuntimeException(e);
		}
	}
}
