import java.util.Random;
import java.util.Scanner;   // to use Scanner, nextLine, & other keyboard inputs

class Hangman 
{
  
  // Words that may be selected for the Hangman game
  String[] words = {"computer", "science", "java", "keyboard", "mouse", "loop", "conditional", "method", "operator", "array", "integer", "string", "boolean", "void"};
  
  // Stores the currently selected word
  String currentWord = "";
  // Stores the player's progress based on their guesses
  String currentProgress = "";
  
  Boolean gameWon = false;
  
  Random rand = new Random();
  Scanner scan = new Scanner(System.in);
  
  // Main method that allows user to play Hangman
  void play() 
  {
    System.out.println("WELCOME TO HANGMAN!");
    System.out.println("The dashes stand for letters. Begin by guessing a lowercase letter.");
    // Randomly selects a word from the words list
    currentWord = words[rand.nextInt(words.length)];
    // Sets currentProgress equal to the right number of dashes
    initCurrentProgress();
    // Loops while user has not yet guessed the word
    while (gameWon == false) 
    {
      System.out.println(currentProgress);
      System.out.print("Guess: ");
      char guess = scan.next().charAt(0);
      // Checks if the letter is in the word
      if (charInWord(guess, currentWord)) 
      {
        // Updates the user's progress
        currentProgress = updateProgress(guess, currentWord, currentProgress);
      }
      // Checks if the user's progress equals the current word
      checkForWin();
    }
    System.out.println("The answer is " + currentWord + "!");
    System.out.println("YOU WIN!");
  }
  
  // Sets the user's initial progress equal to the same number of dashes as in the selected words
  void initCurrentProgress() 
  {
    // Loops through the current word to get its length and set progress equal to the appropriate number of dashes
    for (int i = 0; i < currentWord.length(); i++) 
    {
      currentProgress += "-";
    }
  }
  
  // Checks if character x is in the String word
  Boolean charInWord(char x, String word) 
  {
    if (word.indexOf(x) >= 0) 
    {
      return true;
    }
    return false;
  }
  
  // Updates the appropriate dashes in the user's progress to become the character x based on where x appears in the original word
  String updateProgress(char x, String originalWord, String progress) 
  {
    // Loops through each character in the original word
    for (int i = 0; i < originalWord.length(); i++) 
    {
      // If the current character in the original word is character x
      if (originalWord.charAt(i) == x) 
      {
        // Update the progress string to contain character x instead of a dash
        progress = progress.substring(0, i) + x + progress.substring(i+1, progress.length());
      }
    }
    return progress;
  }
  
  // Checks if the user's progress is the same as the current word, indicating victory
  void checkForWin() 
  {
    if (currentProgress.equals(currentWord)) 
    {
      gameWon = true;
    }
  }
  
}
