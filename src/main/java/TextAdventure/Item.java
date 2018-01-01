package TextAdventure;
import java.io.FileReader;
import java.io.IOException;
import com.eclipsesource.json.*;


public class Item {
	public final String name;
	public final String id;
	private JsonValue rawJson;
	private JsonObject raw;

	public Item(String filename) throws IOException {
		 
		// Read the file
		FileReader file = new FileReader(filename);
		rawJson = Json.parse(file);
		file.close();
		raw = rawJson.asObject();
		
		// Get the name from it
		name = raw.get("name").asString();
		
		// Get its ID
		id = raw.get("id").asString();
	}

	


}
