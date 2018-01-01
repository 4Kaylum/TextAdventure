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

	public String toString() {
		return this.action + " a=\"" + this.a + "\" b=\"" + this.b + "\"";
	}
	
	/**
	 * Parses a string from the user, and interprets it into an easy-to-use instance
	 * of what needs to be processed.
	 *
	 * @param toParse The string from the user that is to be interpreted
	 * @return The Parser object of the interpreted string
	 * @throws Exception 
	**/
	public static Parser parse(String toParse) throws Exception {

		// Determine if they're moving
		String regex = "(go|walk|move)( to)? (.*)?";
		Pattern r = Pattern.compile(regex);
		Matcher m = r.matcher(toParse);
		if (m.find()) {
			return new Parser(Action.MOVE, m.group(3));
		}

		// Determine if they're looking at something
		regex = "(look at|examine|x) (.*)?";
		r = Pattern.compile(regex);
		m = r.matcher(toParse);
		if (m.find()) {
			return new Parser(Action.MOVE, m.group(2));
		}

		throw new Exception();
	}
}