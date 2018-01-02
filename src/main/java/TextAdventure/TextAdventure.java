package TextAdventure;

import java.io.File;
import java.util.Scanner;

import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.Ansi.Color;
import org.fusesource.jansi.AnsiConsole;

public class TextAdventure {
	
	public static String gamePath;
	public static Game game;
	private static Scanner in;
	
	public static void main(String[] args) throws Exception {
		
		// Nice output text to start with
		System.out.println("Welcome to the Text Adventure thingy");
		TextAdventure.in = new Scanner(System.in);
		AnsiConsole.systemInstall();
		
		// Determine the game path
		while (true) {
			System.out.println("What game do you want to run?");
			String gameName = input();
			TextAdventure.load(gameName);
			break;
		}

		// Create a new game object
		Ansi.ansi().eraseScreen();
		TextAdventure.game = new Game(TextAdventure.gamePath);
		String d = TextAdventure.game.getLocation().lookAround();
		System.out.println(d);
		
		// Determine the next thing the user says
		while (true) {
			String line = input();
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

	public static String input() {
		System.out.print("  > " + Ansi.ansi().fg(Color.GREEN)); System.out.flush();
		String line = TextAdventure.in.nextLine();
		System.out.print(Ansi.ansi().reset()); System.out.flush();
		return line;
	}
}
