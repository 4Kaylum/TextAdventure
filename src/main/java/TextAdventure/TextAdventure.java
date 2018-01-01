package TextAdventure;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class TextAdventure {
	
	public static String gamePath;
	public static Location currentLocation;
	public static boolean firstRun;  // Whether or not it's the first time you enter a room
	public static Item[] inventory;
	
	public static void main(String[] args) throws IOException {
		
		// Nice output text to start with
		System.out.println("Welcome to the Text Adventure thingy");
		Scanner in = new Scanner(System.in);
		
		// Determine the game path
		while (true) {
			System.out.println("What game do you want to run?");
			String gameName = in.nextLine();
			load(gameName);
			break;
		}
		
		// Determine the next thing the user says
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
		gamePath = file.getAbsolutePath();
	}
}
