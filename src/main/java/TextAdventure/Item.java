package TextAdventure;
import java.io.FileReader;
import java.io.IOException;
import com.eclipsesource.json.*;
import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.Ansi.*;

public class Item {
	public static String path;
	public String display_name;
	public String id;
	private JsonValue rawJson;
	private JsonObject raw;

	public Item(String filename) throws IOException {
		 
		// Read the file
		FileReader file = new FileReader(Item.path + filename + ".item");
		this.rawJson = Json.parse(file);
		file.close();
		this.raw = rawJson.asObject();
		
		// Get the name from it
		this.display_name = raw.get("display_name").asString();
		
		// Get its ID
		this.id = filename;
	}
	
	public Item() {}
	
	public String mention() {
		return Ansi.ansi().bg(Color.RED).fg(Color.WHITE).a(this.display_name).reset().toString();
	}
	
	public String getAliases() {
		return this.raw.get("aliases").asString();
	}

	public static Item invalid() {
		return new Item();
	}

	public String getPickupText() {
		return this.raw.get("pickup_text").asString().replaceAll("\\{\\{name\\}\\}", this.mention());
	}

}
