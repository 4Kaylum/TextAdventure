package TextAdventure;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.Ansi.Color;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonObject.Member;
import com.eclipsesource.json.JsonValue;

public class Location {
	public static String path;
	public String id;
	private JsonValue rawJson;
	private JsonObject raw;	
	private List<Item> items = new ArrayList<Item>();
	private Map<String, Entry<Pattern, String>> leadingTo = new HashMap<String, Entry<Pattern, String>>();
	
	public Location(String filename) throws IOException {
		
		// Get its ID
		this.id = filename;
		
		// Read the file
		FileReader file = new FileReader(Location.path + filename + ".location");
		this.rawJson = Json.parse(file);
		file.close();
		this.raw = rawJson.asObject();
		
		// Get the items
		JsonArray items = raw.get("items").asArray();
		for (JsonValue jsonItem : items) {
			String itemId = jsonItem.asString();
			Item item = new Item(itemId);
			this.items.add(item);
		}
		
		// Get where it leads to
		JsonObject leads = raw.get("leading_to").asObject();
		for (Member leadObject : leads) {
			JsonObject j = leadObject.getValue().asObject(); 
			Entry<Pattern, String> l = new SimpleEntry<Pattern, String> (
				Pattern.compile(j.get("player_string").asString()),
				j.get("display_name").asString()
			);
			this.leadingTo.put(leadObject.getName(), l);
		}
	}
	
	public List<Item> getAllItems() {
		return this.items;
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
		if (this.leadingTo.size() > 0) {
			description = description + "\nLeading to: ";
			int counter = 0;
			for (Entry<String, Entry<Pattern, String>> entry : this.leadingTo.entrySet()) {
				counter++;
				String display = entry.getValue().getValue();
				description += Ansi.ansi().bg(Color.WHITE).fg(Color.MAGENTA).a(display).reset();
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
	 *
	 * @param r The name being searched for in the item's regex
	**/
	public Item popItem(String userInput) {
		for (Item item : this.items) {
			Pattern p = item.getAliases();
			Matcher m = p.matcher(userInput);
			if (m.find()) {
				this.items.remove(item);
				return item;
			}
		}
		return Item.invalid();
	}

	public String checkMovement(String userInput) {
		for (Entry<String, Entry<Pattern, String>> entry : this.leadingTo.entrySet()) {
			Matcher m = entry.getValue().getKey().matcher(userInput);
			if (m.find()) { return entry.getKey(); }
		}
		return null;
	}
}
