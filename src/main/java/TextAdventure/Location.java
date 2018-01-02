package TextAdventure;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
	
	/**
	 * Gets the description of the room instance, including the items in it
	 *
	 * @param newLine Whether or not the description has a trailing newline at the start
	**/
	public String lookAround(boolean newLine) {

		// Get the description of the location from the file
		String description = raw.get("description").asString();

		// Add a list of the items available
		if (this.items.size() > 0) {
			description = description + "\nItems: ";
			int counter = 0;

			// Iterate through each of the items
			// This only looks complicated since I'm deciding between a comma, period, and the word and
			for (Item i : this.items) {
				counter++;
				description += i.mention();
				if (counter == this.items.size() - 1 && this.items.size() != 0) {
					description += ", and ";
				} else if (counter < this.items.size()) {
					description += ", ";
				} else {
					description += ".";
				}
			}
		}
		if (newLine) { return "\n" + description; } return description;
	}
	
	public String lookAround() {
		return this.lookAround(false);
	}

	/**
	 * Gets an item from this room's items via a regex search
	 * @param r The name being searched for in the item's regex
	**/
	public Item popItem(String userInput) {
		for (Item item : this.items) {
			String aliases = item.getAliases();
			Pattern p = Pattern.compile(aliases);
			Matcher m = p.matcher(userInput);
			if (m.find()) {
				this.items.remove(item);
				return item;
			}
		}
		return Item.invalid();
	}
}
