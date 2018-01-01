package TextAdventure;

public class Parser {
	public Action action;
	public String a;  // Item/location/whatever
	public String b;  // Optional, item to use on
	
	public Parser(Action x, String a, String b) {
		this.a = a;
		this.b = b;
		this.action = x;
	}
	
	public Parser parse(String toParse) {
		return new Parser(Action.USE, "test", "post");
	}
}
