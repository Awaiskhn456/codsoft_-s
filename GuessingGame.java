import java.util.Random;
import java.util.Scanner;

public class GuessingGame {

    private static final int MAX_ATTEMPTS = 5; 
    private static final int MIN_NUMBER = 1;
    private static final int MAX_NUMBER = 100; 
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        boolean playAgain = true;
        int totalRounds = 0;
        int totalWins = 0;

        while (playAgain) {
            int numberToGuess = random.nextInt(MAX_NUMBER - MIN_NUMBER + 1) + MIN_NUMBER;
            boolean guessedCorrectly = false;
            int attempts = 0;

            System.out.println("A new number between " + MIN_NUMBER + " and " + MAX_NUMBER + " has been generated.");
            System.out.println("You have " + MAX_ATTEMPTS + " attempts to guess the number.");

            while (attempts < MAX_ATTEMPTS) {
                System.out.print("Enter your guess: ");
                int userGuess = scanner.nextInt();
                attempts++;

                if (userGuess < MIN_NUMBER || userGuess > MAX_NUMBER) {
                    System.out.println("Please enter a number between " + MIN_NUMBER + " and " + MAX_NUMBER + ".");
                } else if (userGuess < numberToGuess) {
                    System.out.println("Too low! Try again.");
                } else if (userGuess > numberToGuess) {
                    System.out.println("Too high! Try again.");
                } else {
                    guessedCorrectly = true;
                    break;
                }
            }

            if (guessedCorrectly) {
                System.out.println("Congratulations! You guessed the number in " + attempts + " attempts.");
                totalWins++;
            } else {
                System.out.println("Sorry, you've used all your attempts. The correct number was " + numberToGuess + ".");
            }

            totalRounds++;
            System.out.println("You have played " + totalRounds + " rounds and won " + totalWins + " times.");
            
            System.out.print("Do you want to play again? (yes/no): ");
            String response = scanner.next();
            playAgain = response.equalsIgnoreCase("yes");
        }

        System.out.println("Thank you for playing!");
        scanner.close();
    }
}
