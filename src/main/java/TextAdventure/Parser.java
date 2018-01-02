package TextAdventure;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {
	public Action action;
	public String a;  // Item/location/whatever
	public String b;  // Optional, item to use on
	
	public Parser(Action x, String a, String b) {
		this.a = a;
		this.b = b;
		this.action = x;
	}
	
	public Parser(Action x, String a) {
		this.a = a;
		this.action = x;
	}

	public Parser(Action x) {
		this.action = x;
	}

	public String toString() {
		return this.action + " a=\"" + this.a + "\" b=\"" + this.b + "\"";
	}
	
	/**
	 * Parses a string from the user, and interprets it into an easy-to-use instance
	 * of what needs to be processed.
	 *
	 * @param toParse The string from the user that is to be interpreted
	 * @return The Parser object of the interpreted string
	**/
	public static Parser parse(String toParse) {
		toParse = toParse.trim();

		// Determine if they're moving
		String regex = "^(go|walk|move)( to)? (.*)?$";
		Pattern r = Pattern.compile(regex);
		Matcher m = r.matcher(toParse);
		if (m.find()) {
			return new Parser(Action.MOVE, m.group(3));
		}

		// Determine if they're looking at the room
		regex = "^(look|examine|x)( at| around)?( room| around)?$";
		r = Pattern.compile(regex);
		m = r.matcher(toParse);
		if (m.find()) {
			return new Parser(Action.ROOM_EXAMINE);
		}

		// Determine if they're looking at something
		regex = "^(look|examine|x)( at)? (.*)?$";
		r = Pattern.compile(regex);
		m = r.matcher(toParse);
		if (m.find()) {
			return new Parser(Action.ITEM_EXAMINE, m.group(3));
		}

		// See if they want to clear the console
		regex = "^(clear|cls)$";
		r = Pattern.compile(regex);
		m = r.matcher(toParse);
		if (m.find()) {
			return new Parser(Action.CLEAR_CONSOLE);
		}

		// See if they want to clear the console
		regex = "^(i|inventory|bag|inv)$";
		r = Pattern.compile(regex);
		m = r.matcher(toParse);
		if (m.find()) {
			return new Parser(Action.CHECK_INVENTORY);
		}

		// Determine if they're trying to pick up an item
		regex = "^(get|pick)( up)? (.*)?$";
		r = Pattern.compile(regex);
		m = r.matcher(toParse);
		if (m.find()) {
			return new Parser(Action.GET_ITEM, m.group(3));
		}

		// Invalid option
		return new Parser(Action.INVALID);
	}
}
