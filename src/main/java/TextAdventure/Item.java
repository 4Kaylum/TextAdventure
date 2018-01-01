package TextAdventure;
import java.io.FileReader;
import java.io.IOException;
import com.eclipsesource.json.*;
import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.Ansi.*;

public class Item {
	public String name;
	public String id;
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
	
	public String mention() {
		return Ansi.ansi().bg(Color.RED).fg(Color.WHITE).a(this.name).reset().toString();
	}

}
