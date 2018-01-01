package TextAdventure;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class Location {
	public static String path;
	public String name;
	public String id;
	private JsonValue rawJson;
	private JsonObject raw;	
	private List<Item> items = new ArrayList<Item>();
	
	public Location(String filename) throws IOException {
		
		// Get its ID
		this.id = filename;
		
		// Read the file
		FileReader file = new FileReader(Location.path + filename + ".location");
		this.rawJson = Json.parse(file);
		file.close();
		this.raw = rawJson.asObject();
		
		// Get the name from it
		this.name = raw.get("name").asString();
		
		// Get the items
		JsonArray items = raw.get("items").asArray();
		for (JsonValue jsonItem : items) {
			String itemId = jsonItem.asString();
			Item item = new Item(itemId);
			this.items.add(item);
		}
	}
	
	public String lookAround() {
		String description = raw.get("description").asString();
		if (!this.items.isEmpty()) {
			description = description + "\nTHERE ARE ALSO ITEMS";
		}
		return description;
	}
}
