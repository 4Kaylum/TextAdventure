package TextAdventure;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.fusesource.jansi.Ansi;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class Game {
	private static String path;
	private List<Item> inventory = new ArrayList<Item>();
	private Location location;
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
		
		// Work out where we're starting
		String locationName = this.settings.get("starting_location").asString();
		this.location = new Location(locationName);
	}

	public void run(Parser p) {
		switch (p.action) {
			case CLEAR_CONSOLE:
				// Clear the screen
				Ansi.ansi().eraseScreen();
				break;
		
			case ROOM_EXAMINE:
				// Print out the description of the room
				String roomDescription = this.getLocation().lookAround();
				System.out.println(roomDescription);
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
		return this.location;
	}
	
	public Location setLocation(Location newLocation) {
		this.location = newLocation;
		return this.getLocation();
	}
	
	public void getItem(String itemId) throws IOException {
		Item item = new Item(Game.path + itemId);
		this.inventory.add(item);
	}
}
