import java.util.Scanner;
import java.util.Arrays.*;

public class Battleship {

	public static void main(String[] args) {

		// Start string and char array
		// First for loop displays the starting letters for the rows, then the second
		// that's called upon
		// --will be the one that is default for constructing the appearance for whether
		// or not the space is filled or not.

		int[][] targetBoard = new int[6][6];
		int[][] shotsTaken = new int[6][6];

		// ----- Patrol Boat
		targetBoard[0][0] = 2;
		targetBoard[1][0] = 2;
		// ----- Destroyer
		targetBoard[1][4] = 3;
		targetBoard[2][4] = 3;
		targetBoard[3][4] = 3;
		// ----- Battleship
		targetBoard[5][2] = 4;
		targetBoard[5][3] = 4;
		targetBoard[5][4] = 4;
		targetBoard[5][5] = 4;
		System.out.println("Welcome! Please enter a letter followed by a number (such as A3 or F6)");
		boardDraw(shotsTaken, targetBoard);

		boolean gameComplete = false;
		// PRIMARY LOOP

		// Scanner scanner = new Scanner(System.in);
		while (gameComplete == false) {

			// It says the scanner needs to be closed, but I found that has some severe
			// instability on the loop
			Scanner scanner = new Scanner(System.in);
			String scannerInput = scanner.nextLine();
			// To be honest, I have no clue why taking 97 away works for all of the alphabet
			// (I tested almost the entire alphabet in a for loop already) but it works
			// somehow
			int parsedInputLetter = (char) scannerInput.charAt(0) - 97;
			// The parsedInput variable is now ready to be passed onto the other methods for
			// the battleship display, then afterwards into the array logic

			// Make sure letter is within bounds
			if (parsedInputLetter < 0 || parsedInputLetter > 5) {
				System.out.println(
						"Sorry, try again but this time with the correct letter at the start (Anything A --> F)");
				System.exit(0);
			}

			// Make sure number is in bounds (with the length of the array given for
			// targets)
			int parsedInputNumber = scannerInput.charAt(1) - 49;

			if (parsedInputNumber < 0 || parsedInputNumber > targetBoard.length) {
				System.out.println(
						"Sorry, try again but this time with the correct number at the end (Anything 1 --> 6)");
				System.exit(0);
			}

			String input = "" + parsedInputLetter + " " + parsedInputNumber; // I guess the null specifier works without
																				// playing with casting or any other
																				// methods

			// Execute Logic
			// If false continue
			if (moveQuery(parsedInputLetter, parsedInputNumber, shotsTaken, targetBoard) == false) {
				gameComplete = false;
			} else {
				System.out.println("Congratulations, you've won the game :) ");
				gameComplete = true;
			}
			// Print Board
			boardDraw(shotsTaken, targetBoard);

		}

		System.exit(0);
	}

	public static void boardDraw(int[][] shotsTaken, int[][] targetBoard) {
		System.out.print("    ");
		for (int n = 1; n <= shotsTaken.length; n++) {
			// System.out.println(" 1 2 3 4 5 6");
			System.out.print(n + "   ");

		}
		System.out.println();

		char ch = 'A';
		for (int row = 0; row < shotsTaken.length; row++) {
			System.out.print(ch + " | ");
			for (int col = 0; col < shotsTaken[row].length; col++) {
				if (shotsTaken[row][col] == 0) {
					System.out.print("-");
				} else if (shotsTaken[row][col] == 1 && targetBoard[row][col] != 0) {
					System.out.print("X");
				} else if (shotsTaken[row][col] == 1) {
					System.out.print("O");

				}

				System.out.print(" | ");
				// System.out.print(targetBoard[row][col] + " | ");
			}
			ch++;
			System.out.println();

		}
	}

	// Must be true to exit game
	public static boolean moveQuery(int parsedInputLetter, int parsedInputNumber, int[][] shotsTaken,
			int[][] targetBoard) {
		System.out.println("           ENEMY           ");
		System.out.println("Hit " + nameCheck(targetBoard[parsedInputLetter][parsedInputNumber]) + "!");

		for (int row = 0; row < shotsTaken.length; row++) {
			for (int col = 0; col < shotsTaken.length; col++) {
				// I know there's alot of jibberish code in here, and that the full array sort
				// doesn't work all that well, but I know I would have to compare both arrays
				// Based on the [row][col] and compare whether or not there was A) the int that
				// was stored in targetBoard is present in this loop and B) that matches up with
				// a pair of shotsTaken and targetBoard and if there are any that arent pairs.
				// If there are all pairs then that means they're all sunk
				if (targetBoard[parsedInputLetter][parsedInputLetter] != 0
						&& shotsTaken[parsedInputLetter][parsedInputLetter] == 0) {
					System.out.print("");
				} else if (targetBoard[parsedInputLetter][parsedInputLetter] == 0
						&& shotsTaken[parsedInputLetter][parsedInputLetter] == 1) {
					System.out.print("");
				} else {
					// System.out.print(nameCheck(targetBoard[parsedInputLetter][parsedInputLetter])
					// + " has been sunk!");
				}
			}
		}

		// Need to have hit and array-write logic here before it's drawn again
		// Must be false to return end game call
		if (targetBoardCheck(shotsTaken, targetBoard) == false) {
			System.out.println("YOU HAVE WON THE GAME! Terminating Process.");
			return true;
		} else {

		}
		// Check if move already taken
		if (shotsTaken[parsedInputLetter][parsedInputNumber] == 1) {
			System.out.println("You've already taken that move, please try again");
			return false;
		}

		// Only perform for loop if move not taken yet
		if (shotsTaken[parsedInputLetter][parsedInputNumber] == 0) {
			shotsTaken[parsedInputLetter][parsedInputNumber] = 1;

			// Perform check for available hit in (int targetBoard[][])
			if (targetBoard[parsedInputLetter][parsedInputNumber] != 0) {

				shotsTaken[parsedInputLetter][parsedInputNumber] = 1;

			} else {
				// Sets the move to true
				System.out.println("");
				shotsTaken[parsedInputLetter][parsedInputNumber] = 1;
				return false;
			}
		}
		shotsTaken[parsedInputLetter][parsedInputNumber] = 1;
		return false;

	}

	// must be false to trigger game over
	public static boolean targetBoardCheck(int[][] shotsTaken, int[][] targetBoard) {

		// ONLY RETURN TRUE IF FOR LOOP FOR ALL OF targetBoard[][] RETURNS NOTHING
		// Primary Check for if all of the ships have been hit
		for (int row = 0; row < targetBoard.length; row++) {
			for (int col = 0; col < targetBoard[row].length; col++) {
				if (shotsTaken[row][col] == 0 && targetBoard[row][col] != 0) {
					return true;
				}
			}
		}
		return false;

	}

	public static String nameCheck(int n) {
		// The error handling at the end is, I kind of admit it, ghetto but it works
		if (n == 2)
			return "Patrol Boat";
		if (n == 3)
			return "Destroyer";
		if (n == 4)
			return "Battleship";
		return "missed";

	}
}