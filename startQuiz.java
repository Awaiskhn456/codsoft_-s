import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.*;

class Question {
    private String text;
    private String[] options;
    private int correctAnswerIndex;

    public Question(String text, String[] options, int correctAnswerIndex) {
        this.text = text;
        this.options = options;
        this.correctAnswerIndex = correctAnswerIndex;
    }

    public String getText() {
        return text;
    }

    public String[] getOptions() {
        return options;
    }

    public boolean isCorrectAnswer(int index) {
        return index == correctAnswerIndex;
    }
}

class Quiz {
    private List<Question> questions;
    private int score;

    public Quiz() {
        questions = new ArrayList<>();
        score = 0;

       
        questions.add(new Question("What is the capital of France?", new String[]{"1. Berlin", "2. Madrid", "3. Paris", "4. Rome"}, 2));
        questions.add(new Question("What is 2 + 2?", new String[]{"1. 3", "2. 4", "3. 5", "4. 6"}, 1));
        questions.add(new Question("Which planet is known as the Red Planet?", new String[]{"1. Earth", "2. Mars", "3. Jupiter", "4. Venus"}, 1));
    }

    public void startQuiz() {
        Scanner scanner = new Scanner(System.in);
        ExecutorService executor = Executors.newSingleThreadExecutor();
        int totalQuestions = questions.size();
        int currentQuestionIndex = 0;

        while (currentQuestionIndex < totalQuestions) {
            Question question = questions.get(currentQuestionIndex);
            System.out.println("\nQuestion " + (currentQuestionIndex + 1) + ": " + question.getText());
            String[] options = question.getOptions();
            for (String option : options) {
                System.out.println(option);
            }

            Future<Integer> future = executor.submit(() -> {
                Scanner inputScann = new Scanner(System.in);
                System.out.print("Enter your answer (1-4): ");
                return inputScann.nextInt() - 1; 
            });

            try {
                
                int userAnswer = future.get(10, TimeUnit.SECONDS); 
                if (question.isCorrectAnswer(userAnswer)) {
                    System.out.println("Correct!");
                    score++;
                } else {
                    System.out.println("Incorrect. The correct answer was: " + (question.getOptions().length - 1) + ".");
                }
            } catch (TimeoutException e) {
                System.out.println("\nTime's up! Moving to the next question.");
                future.cancel(true);
            } catch (Exception e) {
                System.out.println("Error occurred: " + e.getMessage());
            }

            currentQuestionIndex++;
        }

        executor.shutdown();
        displayResults();
        scanner.close();
    }

    private void displayResults() {
        System.out.println("\nQuiz completed!");
        System.out.println("Your final score is: " + score + "/" + questions.size());
    }
}

public class startQuiz {
    public static void main(String[] args) {
        Quiz quiz = new Quiz();
        quiz.startQuiz();
    }
}
