package TextAdventure;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import org.fusesource.jansi.AnsiConsole;

public class TextAdventure {
	
	public static String gamePath;
	
	public static void main(String[] args) throws IOException {
		
		// Nice output text to start with
		System.out.println("Welcome to the Text Adventure thingy");
		Scanner in = new Scanner(System.in);
		AnsiConsole.systemInstall();
		
		// Determine the game path
		while (true) {
			System.out.println("What game do you want to run?");
			String gameName = in.nextLine();
			TextAdventure.load(gameName);
			break;
		}
		
		// Determine the next thing the user says
		Game game = new Game(TextAdventure.gamePath);
		while (true) {
			String line = in.nextLine();
			if (line.equals("exit")) {
				System.out.println("Exiting program.");
				break;
			}
		}
		in.close();
	}
	
	public static void load(String gameName) {
		File file = new File("./" + gameName);
		TextAdventure.gamePath = file.getAbsolutePath() + "/";
	}
}
