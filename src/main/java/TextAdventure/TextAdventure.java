package TextAdventure;

import java.io.File;
import java.util.Scanner;
import org.fusesource.jansi.AnsiConsole;

public class TextAdventure {
	
	public static String gamePath;
	public static Game game;
	
	public static void main(String[] args) throws Exception {
		
		// Nice output text to start with
		System.out.println("Welcome to the Text Adventure thingy");
		Scanner in = new Scanner(System.in);
		AnsiConsole.systemInstall();
		
		// Determine the game path
		while (true) {
			System.out.println("What game do you want to run?");
			System.out.print("  > "); System.out.flush();
			String gameName = in.nextLine();
			TextAdventure.load(gameName);
			break;
		}

		// Create a new game object
		TextAdventure.game = new Game(TextAdventure.gamePath);
		String d = TextAdventure.game.getLocation().lookAround();
		System.out.println(d);
		
		// Determine the next thing the user says
		while (true) {
			System.out.print("  > "); System.out.flush();
			String line = in.nextLine();
			if (line.equals("exit")) {
				System.out.println("Exiting program.");
				break;
			} else {
				Parser p = Parser.parse(line);
				TextAdventure.game.run(p);
			}
		}
		in.close();
	}
	
	public static void load(String gameName) {
		File file = new File(gameName);
		TextAdventure.gamePath = file.getAbsolutePath() + "/";
	}
}
