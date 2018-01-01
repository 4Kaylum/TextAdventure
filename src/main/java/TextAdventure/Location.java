package TextAdventure;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class Location {
	public String name;
	public String id;
	private JsonValue rawJson;
	private JsonObject raw;	
	public List<Item> items;
	
	public Location(String filename) throws IOException {
		
		// Get its ID
		this.id = filename;
		
		// Read the file
		FileReader file = new FileReader(filename + ".location");
		this.rawJson = Json.parse(file);
		file.close();
		this.raw = rawJson.asObject();
		
		// Get the name from it
		this.name = raw.get("name").asString();
	}
	
	public String lookAround() {
		String description = raw.get("description").asString();
		return description;
	}
}
