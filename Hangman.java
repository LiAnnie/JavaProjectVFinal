/**
 * @author annie
 * 06/21/2022
 * Hangman V1
 * A program that runs the hangman game.
 */
// Importing packages for easier utility
import java.io.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class Hangman {
	
	// Public variables to be used in multiple methods
	public static String guessed = "";
	public static JTextField guessField = new JTextField(25);
	public static JLabel guesses = new JLabel("", SwingConstants.CENTER);
	public static int numGuesses = 0;
	public static String key = "";
	public static String word = "";
	public static JLabel wordsofar = new JLabel(word, SwingConstants.CENTER);
	public static JLabel guessesRemainder = new JLabel("Guesses remaining: " + (15 - numGuesses));
	public static JLabel hangmanhook = new JLabel(new ImageIcon("images/hangmanhook.png"));
	public static JLayeredPane pane = new JLayeredPane();
	public static JFrame screen = new JFrame();
	public static textListener guessEnter = new textListener();
	public static JFrame win = new JFrame();
	public static JFrame error = new JFrame();
	public static JFrame lose = new JFrame();
	public static JFrame win1 = new JFrame();
	public static JLayeredPane pane2 = new JLayeredPane();

	/**
	 * This method shows the clue when you win the hangman game
	 */
	public static void win() {
		// Show win screen with clue
		screen.setVisible(false);
		win1.setTitle("Clue 2");
		win1.setSize(900,600);
		win1.setResizable(false);
		win1.setLocationRelativeTo(null);
		win1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		win1.setVisible(true);

		// Return to map button
		JButton map = new JButton("Map");
		pane2.add(map, JLayeredPane.PALETTE_LAYER);
		map.setSize(200,50);
		map.setLocation(25, 25);
		map.addActionListener(new mapListener());
		win1.add(pane2);

		// Background
		JLabel Background = new JLabel(new ImageIcon("images/Clue2.jpg"));
		pane2.add(Background, JLayeredPane.DEFAULT_LAYER);
		Background.setSize(1000,700);
		Background.setLocation(-60,-65);

	}
	static class instructionsListener implements ActionListener{
		/**
		 * Detects if user interacts with instructions button
		 * @param e
		 */
		public void actionPerformed(ActionEvent e) {
			HangmanInstructions.instructions();
			// Close all other screens
			screen.setVisible(false);
			win.setVisible(false);
			error.setVisible(false);
			lose.setVisible(false);
		}
	}
	static class mapListener implements ActionListener{
		/**
		 * Detects if user interacts with instructions button
		 * @param e
		 */
		public void actionPerformed(ActionEvent e) {
			Map.map();
			// Close all other screens
			screen.setVisible(false);
			win.setVisible(false);
			error.setVisible(false);
			lose.setVisible(false);
			win1.setVisible(false);
		}
	}
	static class backListener implements ActionListener{
		/**
		 * Detects if user interacts with back button
		 * @param e
		 */
		public void actionPerformed(ActionEvent e) {
			// Close all other screens
			screen.setVisible(false);
			win.setVisible(false);
			error.setVisible(false);
			lose.setVisible(false);
			Room2.room2();
		}
	}
	static class textListener implements ActionListener {
		/**
		 * Detects if user has decided to enter their guess
		 * @param e
		 */
		public void actionPerformed(ActionEvent e) {
			String userGuess = guessField.getText().toUpperCase();
			if (userGuess.equals("")) { // If user enters nothing
				error.setTitle("Error");
				error.setSize(250,100);
				error.setResizable(false);
				error.setLocationRelativeTo(null);

				error.add(new JLabel("Your guess cannot be empty!", SwingConstants.CENTER));
				error.setVisible(true);
			}
			else if (userGuess.length() == 1) { // Displays letter guessed and if it is in the word
				// If the letter has not been guessed.
				if (!guessed.contains(userGuess)) {
					guessed += userGuess + " ";
					guesses.setText(guessed);

					while (!numCharsEqual(userGuess, key, word)) {
						for (int i = 0; i < key.length(); i++) {
							if (key.substring(i, i+1).equals(userGuess)) {
								String newword = "";
								String cutword = word.substring(0, word.length());
								for (int j = 0; j < i; j++) {
									newword += cutword.substring(0, cutword.indexOf(" ") + 1);
									cutword = cutword.substring(cutword.indexOf(" ") + 1);
								}
								newword += userGuess + " ";
								newword += cutword.substring(cutword.indexOf(" ") + 1);
								word = newword;
							}
						}
					}
					wordsofar.setText(word);
					numGuesses++;
					guessesRemainder.setText("Guesses remaining: " + (15 - numGuesses));
				}
				// Reset the textfield
				guessField.setText("");
			}
			else {
				if (userGuess.equals(key)) {
					win.setTitle("You Win!");
					win.setSize(300,100);
					win.setResizable(false);
					win.setLocationRelativeTo(null);

					win.add(new JLabel("Congratulations, you won!", SwingConstants.CENTER));
					win();
					win.setVisible(true);
					word = userGuess;
					wordsofar.setText(word);
					guessesRemainder.setText("Guesses remaining: " + (15 - numGuesses));
					guessField.removeActionListener(guessEnter);
					
				}
				else {
					guessField.setText("");
					numGuesses++;
					guessesRemainder.setText("Guesses remaining: " + (15 - numGuesses));
				}
			}
			if (numGuesses >= 15) { // They get 15 guesses (5 free, 10 hangman) can be words or letters
				lose.setTitle("Game Lost");
				lose.setSize(300,100);
				lose.setResizable(false);
				lose.setLocationRelativeTo(null);

				lose.add(new JLabel("Unfortunately, you did not guess the word.", SwingConstants.CENTER), BorderLayout.NORTH);
				lose.add(new JLabel("The word was: " + key, SwingConstants.CENTER), BorderLayout.CENTER);
				lose.add(new JLabel("Please exit the room and retry.", SwingConstants.CENTER), BorderLayout.SOUTH);
				lose.setVisible(true);
			}
			switch(numGuesses) {
			case 6:
				hangmanhook = new JLabel(new ImageIcon("images/hangmanhook1.png"));
				break;
			case 7:
				hangmanhook = new JLabel(new ImageIcon("images/hangmanhook2.png"));
				break;
			case 8:
				hangmanhook = new JLabel(new ImageIcon("images/hangmanhook3.png"));
				break;
			case 9:
				hangmanhook = new JLabel(new ImageIcon("images/hangmanhook4.png"));
				break;
			case 10:
				hangmanhook = new JLabel(new ImageIcon("images/hangmanhook5.png"));
				break;
			case 11:
				hangmanhook = new JLabel(new ImageIcon("images/hangmanhook6.png"));
				break;
			case 12:
				hangmanhook = new JLabel(new ImageIcon("images/hangmanhook7.png"));
				break;
			case 13:
				hangmanhook = new JLabel(new ImageIcon("images/hangmanhook8.png"));
				break;
			case 14:
				hangmanhook = new JLabel(new ImageIcon("images/hangmanhook9.png"));
				break;
			case 15:
				hangmanhook = new JLabel(new ImageIcon("images/hangmanhook10.png"));
				break;
			default:
				hangmanhook = new JLabel(new ImageIcon("images/hangmanhook.png"));
				break;
			}
			pane.add(hangmanhook, JLayeredPane.PALETTE_LAYER);
			hangmanhook.setSize(900,550);
			hangmanhook.setLocation(-300,0);
		}
	}
	/**
	 * Checks if the number of a certain character in the word is equal to the keyword
	 * @param letter
	 * @param word1
	 * @param word2
	 * @return
	 */
	public static boolean numCharsEqual(String letter, String word1, String word2) {
		int count1 = 0;
		int count2 = 0;
		for (int i = 0; i < word1.length(); i++) {
			if (word1.substring(i, i+1).equals(letter)) {
				count1++;
			}
		}
		for (int i = 0; i < word2.length(); i++) {
			if (word2.substring(i, i+1).equals(letter)) {
				count2++;
			}
		}
		if (count1 == count2) {
			return true;
		}
		return false;
	}
	/**
	 * The main hangman class used to play hangman and display the game screen.
	 */
	public static void hangman() {

		// Reset public variables
		guessed = "";
		guessField = new JTextField(25);
		guesses = new JLabel("", SwingConstants.CENTER);
		numGuesses = 0;
		key = "";
		word = "";
		wordsofar = new JLabel(word, SwingConstants.CENTER);
		guessesRemainder = new JLabel("Guesses remaining: " + (15 - numGuesses));
		hangmanhook = new JLabel(new ImageIcon("images/hangmanhook.png"));
		pane = new JLayeredPane();
		screen = new JFrame();


		// Hangman Setup Section:
		// ------------------------------------------------------------------------------------------
		final int NUMOFWORDS = 3000; // Number of words the program is picking from
		final String FILENAME = "Texts/words.txt";

		// Pick a word
		String[] words = new String[NUMOFWORDS];
		try {
			FileReader file = new FileReader(FILENAME);
			BufferedReader in = new BufferedReader(file);

			// read each line and store in an array
			for (int i = 0; i < NUMOFWORDS; i++) {
				words[i] = in.readLine();
			}
			in.close(); // close the BufferedReader properly
		}
		catch (IOException iox) {
			System.out.println("Problem Reading "+FILENAME);
		}
		// Generate a random word from list of words
		String keyword = words[(int)Math.floor(Math.random()*NUMOFWORDS)];
		key = keyword.toUpperCase();
		// ------------------------------------------------------------------------------------------

		// GUI Display Section:
		// ------------------------------------------------------------------------------------------
		screen.setTitle("Hangman");
		screen.setSize(900,600);
		screen.setResizable(false);
		screen.setLocationRelativeTo(null);
		screen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Background
		JLabel hangmanBackground = new JLabel(new ImageIcon("images/HangmanBackground.jpg"));
		pane.add(hangmanBackground, JLayeredPane.DEFAULT_LAYER);
		hangmanBackground.setSize(900,575);
		hangmanBackground.setLocation(0,0);

		pane.add(hangmanhook, JLayeredPane.PALETTE_LAYER);
		hangmanhook.setSize(900,550);
		hangmanhook.setLocation(-300,0);

		// Buttons
		JButton backButton = new JButton("Room");
		pane.add(backButton, JLayeredPane.PALETTE_LAYER);
		backButton.setFont(new Font("Dialog", Font.BOLD, 16));
		backButton.setSize(150,50);
		backButton.setLocation(300,470);
		backButton.addActionListener(new backListener());

		JButton instructionsButton = new JButton("Instructions");
		pane.add(instructionsButton, JLayeredPane.PALETTE_LAYER);
		instructionsButton.setFont(new Font("Dialog", Font.BOLD, 16));
		instructionsButton.setSize(150,50);
		instructionsButton.setLocation(500,470);

		JButton mapButton = new JButton("Map");
		pane.add(mapButton, JLayeredPane.PALETTE_LAYER);
		mapButton.setFont(new Font("Dialog", Font.BOLD, 16));
		mapButton.setSize(150,50);
		mapButton.setLocation(700,470);
		mapButton.addActionListener(new mapListener());

		// Open Instructions page
		instructionsButton.addActionListener(new instructionsListener());

		// Main Game Screen - Basic Information
		JLabel wsf = new JLabel("Word so far:", SwingConstants.CENTER);
		pane.add(wsf, JLayeredPane.PALETTE_LAYER);
		wsf.setFont(new Font("Dialog", Font.BOLD, 16));
		wsf.setSize(200,50);
		wsf.setLocation(300,50);

		JLabel lg = new JLabel("Letters guessed:", SwingConstants.CENTER);
		pane.add(lg, JLayeredPane.PALETTE_LAYER);
		lg.setFont(new Font("Dialog", Font.BOLD, 16));
		lg.setSize(200,50);
		lg.setLocation(300,125);

		JLabel eyg = new JLabel("Enter your guess:", SwingConstants.CENTER);
		pane.add(eyg, JLayeredPane.PALETTE_LAYER);
		eyg.setFont(new Font("Dialog", Font.BOLD, 16));
		eyg.setSize(200,50);
		eyg.setLocation(300,300);

		pane.add(guessesRemainder, JLayeredPane.PALETTE_LAYER);
		guessesRemainder.setFont(new Font("Dialog", Font.BOLD, 16));
		guessesRemainder.setSize(200,50);
		guessesRemainder.setLocation(60,500);

		pane.add(guessField, JLayeredPane.PALETTE_LAYER);
		guessField.setSize(200,50);
		guessField.setLocation(600, 300);
		// ------------------------------------------------------------------------------------------

		// Display Words
		// ------------------------------------------------------------------------------------------
		for (int i = 0; i < keyword.length(); i++) {
			word += "__ ";
		}
		wordsofar.setText(word);
		pane.add(wordsofar, JLayeredPane.PALETTE_LAYER);
		wordsofar.setFont(new Font("Dialog", Font.BOLD, 16));
		wordsofar.setSize(500,50);
		wordsofar.setLocation(400,50);

		pane.add(guesses, JLayeredPane.PALETTE_LAYER);
		guesses.setFont(new Font("Dialog", Font.BOLD, 16));
		guesses.setSize(500,50);
		guesses.setLocation(400,125);
		// ------------------------------------------------------------------------------------------

		// User Interaction
		// ------------------------------------------------------------------------------------------
		guessField.addActionListener(guessEnter);

		// ------------------------------------------------------------------------------------------
		screen.add(pane);
		screen.setVisible(true);

	}

	public static void main(String[] args) {
		hangman();
	}
}
