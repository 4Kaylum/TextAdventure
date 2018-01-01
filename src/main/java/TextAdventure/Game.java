package TextAdventure;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class Game {
	private static String path;
	private List<Item> inventory;
	private Location location;
	private JsonValue raw;
	private JsonObject settings;
	
	public Game(String path) throws IOException {
		
		Game.path = path;
		
		// Read the file
		FileReader file = new FileReader(Game.path + ".settings");
		this.raw = Json.parse(file);
		file.close();
		this.settings = raw.asObject();
		
		// Work out where we're starting
		String locationName = this.settings.get("Start").asString();
		this.location = new Location(Game.path + locationName);
	}
	
	public Location getLocation() {
		return this.location;
	}
	
	public Location setLocation(Location newLocation) {
		this.location = newLocation;
		return this.getLocation();
	}
	
	public String getLocationDescription() {
		return this.location.getDescription();
	}
	
	public void getItem(String itemId) throws IOException {
		Item item = new Item(Game.path + itemId);
		this.inventory.add(item);
	}
}
