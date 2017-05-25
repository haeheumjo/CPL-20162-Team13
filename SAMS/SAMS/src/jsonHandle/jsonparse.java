package jsonHandle;

import java.io.IOException;
import java.util.HashMap;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonToken;
import org.codehaus.jackson.map.JsonMappingException;

public class jsonparse {
	public static HashMap<String, String> jsonparsing(String a) {
		HashMap<String, String> pushusertbl = new HashMap<String, String>();
		try {
			JsonFactory jfactory = new JsonFactory();
			org.codehaus.jackson.JsonParser jParser = jfactory.createJsonParser(a);
			while (jParser.nextToken() != JsonToken.END_OBJECT) {
				String fieldname = jParser.getCurrentName();
				if ("id".equals(fieldname)) {
					jParser.nextToken();
					pushusertbl.put("id", jParser.getText());
//					 System.out.println(jParser.getText());
				}
				if ("pw".equals(fieldname)) {
					jParser.nextToken();
					pushusertbl.put("pw", jParser.getText());
//					 System.out.println(jParser.getText());
				}
				if ("rid".equals(fieldname)){
					jParser.nextToken();
					pushusertbl.put("rid", jParser.getText());
				}
				if ("pid".equals(fieldname)){
					jParser.nextToken();
					pushusertbl.put("pid", jParser.getText());
				}
				if ("sid".equals(fieldname)){
					jParser.nextToken();
					pushusertbl.put("sid", jParser.getText());
				}
			}
			jParser.close();
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return pushusertbl;
	}
}