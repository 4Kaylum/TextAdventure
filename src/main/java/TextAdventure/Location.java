package TextAdventure;

import java.io.FileReader;
import java.io.IOException;
import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class Location {
	public String name;
	public String id;
	private JsonValue rawJson;
	private JsonObject raw;	
	public Item[] items;
	
	public Location(String filename) throws IOException {
		
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
	
	public String getDescription() {
		String description = raw.get("description").asString();
		return description;
	}
}
