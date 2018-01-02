package TextAdventure;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.Ansi.Color;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class Game {
	private static String path;
	public static boolean debug = false;
	private List<Item> inventory = new ArrayList<Item>();
	private Map<String, Location> locations = new HashMap<String, Location>();  // id, Location
	private Location currentLocation;
	private JsonValue raw;
	private JsonObject settings;
	
	public Game(String path) throws IOException {
		
		Game.path = path;
		Item.path = path;
		Location.path = path;
		
		// Read the file
		FileReader file = new FileReader(Game.path + ".settings");
		this.raw = Json.parse(file);
		file.close();
		this.settings = raw.asObject();
		Game.debug = this.settings.getBoolean("debug", false);

		// Read all of the locations into memory
		File[] wholeDirectory = new File(Game.path).listFiles();
		for (File directoryFile : wholeDirectory) {
			if (directoryFile.toString().endsWith(".location")) {
				String filename = directoryFile.toString();  // Get the path name
				String[] split = filename.split(Pattern.quote(File.separator));  // Split the path by slash
				String locationNameWithEnding = split[split.length-1];  // Get the filename
				String locationName = locationNameWithEnding.split("\\.")[0];  // Get the filename without extension
				this.locations.put(locationName, new Location(locationName));  // Add location to memory
			}
		}
		
		// Work out where we're starting
		String locationName = this.settings.get("starting_location").asString();
		this.currentLocation = this.locations.get(locationName);
	}

	/**
	 * Runs a command from the user
	 *
	 * @param p The parser object that the main thread interpreted
	**/
	public void run(Parser p) {
		if (Game.debug) {
			System.out.println("\n" + Ansi.ansi().bg(Color.RED).fg(Color.WHITE).a(p.toString()).reset());
		}
		switch (p.action) {
			case CLEAR_CONSOLE:
				// Clear the screen
				LocalAnsi.clearScreen();
				break;
		
			case ROOM_EXAMINE:
				// Print out the description of the room
				String roomDescription = this.getLocation().lookAround();
				System.out.println(roomDescription);
				break;
				
			case ITEM_EXAMINE:
				// Examining an item is super neat
				boolean said = false;
				
				// Check your inventory first
				for (Item i : this.inventory) {
					Matcher m = i.getAliases().matcher(p.a);
					if (m.find()) {
						System.out.println(i.getDescription());
						said = true;
						break;
					}
				}
				if (said) { break; }
				
				// Now we check the room
				for (Item i : this.getLocation().getAllItems()) {
					Matcher m = i.getAliases().matcher(p.a);
					if (m.find()) {
						System.out.println(i.getDescription());
						said = true;
						break;
					}
				}
				if (said) { break; }
				System.out.println("That item doesn't seem to exist.");
				break;

			case MOVE:
				// Move the player to another room
				Location current = this.getLocation();
				String newLocation = current.checkMovement(p.a);
				if (newLocation == null) {
					System.out.println("That location is invalid.");
				} else {
					this.setLocation(newLocation);
				}
				break;

			case CHECK_INVENTORY:
				// Print the content of the inventory
				int counter = 0;
				String itemString = "Items: ";

				// Make sure it's not empty
				if (this.inventory.size() == 0) {
					System.out.println("You have no items in your inventory.");
					break;
				}

				// It's not - populate a string
				for (Item i : this.inventory) {
					counter++;
					itemString += i.mention();
					if (counter == this.inventory.size() - 1 && this.inventory.size() != 0) {
						itemString += ", and ";
					} else if (counter < this.inventory.size()) {
						itemString += ", ";
					} else {
						itemString += ".";
					}
				}
				System.out.println(itemString);
				break;

			case GET_ITEM:
				// Move an item from the location's inventory to the player's
				Item item = this.getLocation().popItem(p.a);
				if (item.display_name == null) {
					System.out.println("That item doesn't seem to exist.");
				} else {
					this.inventory.add(item);
					String itemDescription = item.getPickupText();
					System.out.println(itemDescription);					
				}
				break;
				
			default: 
				// Invalid action
				System.out.print("That action is invalid. ");
				System.out.println(p);
				break;
		}
	}
	
	public Location getLocation() {
		return this.currentLocation;
	}
	
	/**
	 * Moves the player to a given location
	 *
	 * @param newLocation The ID of the location to be moved to
	 * @param clearConsole Whether the console should be cleared
	 * @param lookAround Whether you should automatically look around
	 * @return The new location that you're moved to
	**/
	public Location setLocation(String newLocation, boolean clearConsole, boolean lookAround) {
		this.currentLocation = this.locations.get(newLocation);
		if (clearConsole) {
			LocalAnsi.clearScreen();
		}
		if (lookAround) {
			System.out.println(this.currentLocation.lookAround(false));
		}
		return this.getLocation();
	}
	
	public Location setLocation(String newLocation) {
		return this.setLocation(newLocation, true, true);
	}
}
