import java.util.Scanner;

public class Hangman2 {
  private static final boolean testingMode = true;


  private String updateString(String oldString, int index, char newChar) {
    String newString = "";
    for (int i = 0; i < oldString.length(); i++) {
      if (i == index) {
        newString = newString + newChar;
      } else {
        newString = newString + oldString.charAt(i);
      }
    }
    return newString;
  }

  public static void main(String[] args) {
    Hangman2 hangman2 = new Hangman2();
    int guesses = 2;
    int totalScore = 0;
    int roundScore = 0;
    Scanner scanner = new Scanner(System.in);

    char playPrompt = ' ';

    while (playPrompt != 'n') {

      String randomWord = RandomWord.newWord();
      String puzzleWord = "";
      for (int i = 0; i < randomWord.length(); i++) {
        puzzleWord = puzzleWord + "-";
      }
      System.out.print("The word is: " + puzzleWord);
      System.out.print("\n");
      if (Hangman2.testingMode) {
        System.out.println("The secret word is: " + randomWord);
      }

      int spaces = 0;
      while (spaces == 0 || spaces > randomWord.length()) {
        System.out.println("Enter the number of spaces allowed");
        spaces = scanner.nextInt();
        if (spaces == 0 || spaces > randomWord.length()) {
          System.out.println("Invalid input. Try again.");
        }
      }

      String updatedPuzzleWord = puzzleWord;

      while (guesses != 0) {
        boolean isSpacesValid;
        String checkSpaces = "";
        char guessedLetter = ' ';
        boolean inputIteration = false;
        boolean isGuessInWord = false;

        while (!inputIteration) {
          int spacesCount = 0;
          System.out.println("Please enter the letter you want to guess: ");
          guessedLetter = scanner.next().charAt(0);
          System.out.println("Please enter spaces you want to check (separated by spaces):");
          scanner.nextLine();
          checkSpaces = scanner.nextLine();
          checkSpaces = checkSpaces.replaceAll("\\s", "");
          for (int i = 0; i < checkSpaces.length(); i++) {
            if (Character.isDigit(checkSpaces.charAt(i))) {
              spacesCount = spacesCount + 1;
            }
          }

          if (spacesCount == spaces) {
            isSpacesValid = true;
          } else {
            isSpacesValid = false;
          }

          if (Character.isLetter(guessedLetter) && isSpacesValid) {
            inputIteration = true;
          } else {
            System.out.println("Your input is not valid. Try again.");
            System.out.println("Guesses Remaining: " + guesses);
            inputIteration = false;
          }
        }

        for (int i = 0; i < checkSpaces.length(); i++) {
          int index = Character.getNumericValue(checkSpaces.charAt(i));
          if (randomWord.charAt(index) == guessedLetter) {
            updatedPuzzleWord = hangman2.updateString(updatedPuzzleWord, index, guessedLetter);
            isGuessInWord = true;
          }
        }

        if (updatedPuzzleWord.trim().equals(randomWord)) {
          System.out.println("You have guessed the word! Congratulations");
          roundScore = (guesses * 10) / spaces;
          totalScore = totalScore + roundScore;
          System.out.println("Score for this round: " + roundScore);
          System.out.println("Total Score: " + totalScore);
          break;
        }

        if (isGuessInWord) {
          System.out.println("Your Guess is in the word!");
          System.out.println("The Updated Word is: " + updatedPuzzleWord);
          System.out.println("Guesses Remaining: " + guesses);
        } else {
          guesses = guesses - 1;
          System.out.println("Your letter was not found in the spaces you provided.");
          System.out.println("Guesses Remaining: " + guesses);
        }

      }

      if (guesses == 0) {
        System.out.println("You have failed to guess the word... :(");
        roundScore = 0;
        totalScore = totalScore + roundScore;
        System.out.println("Score for this round: " + roundScore);
        System.out.println("Total Score: " + totalScore);
      }

      System.out.println("Would you like to play again? Yes(y) No(n)");
      playPrompt = scanner.next().charAt(0);

    }

  }
}
