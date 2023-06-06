import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class WordSearchGenerator {
   private char[][] wordSearch;
   private List<String> words;

  
    public static void main(String[] args) {
      WordSearchGenerator generator = new WordSearchGenerator();
      generator.printIntro(); 
      Scanner scanner = new Scanner(System.in);
      String choice; 
      do {
         System.out.println("Please select an option");
         System.out.println("Generate a new word search (g)");
         System.out.println("Print out your word search (p)");
         System.out.println("Show the solution to your word search (s)");
         System.out.println("Quit the program (q)");      
         choice = scanner.nextLine();      
         switch (choice.toLowerCase()) {
            case "g":
               generator.generate();
               break;
            case "p":
               generator.print();
               break;
            case "s":
               generator.showSolution();
               break;
            case "q":
               System.out.println("Goodbye!");
               break;
            default:
               System.out.println("Invalid choice. Please try again.");
         }
      } while (!choice.equalsIgnoreCase("q"));
   }
  
   public WordSearchGenerator() {
      wordSearch = new char[0][0];
      words = new ArrayList<>();
   }
  
   public void printIntro() {
      System.out.println("Welcome to my word search generator!");
      System.out.println("This program will allow you to generate your own word search puzzle");
   }
  
   public void generate() {
      words.clear();
      wordSearch = new char[0][0];   
      Scanner scanner = new Scanner(System.in);   
      System.out.print("How many words would you like to enter? ");
      int numWords = scanner.nextInt();
      scanner.nextLine();   
      for (int i = 0; i < numWords; i++) {
         System.out.print("Enter word " + (i + 1) + ": ");
         String word = scanner.nextLine().toUpperCase();
         words.add(word);
      }   
      // Calculate the maximum word length to determine the grid size
      int maxWordLength = words.stream()
             .mapToInt(String::length)
             .max()
             .orElse(0); 
      // Adjust the grid size to accommodate the words
      int gridSize = maxWordLength + 2; 
      // Initialize the word search grid with empty spaces
      wordSearch = new char[gridSize][gridSize];
      for (char[] row : wordSearch) {
         for (int i = 0; i < row.length; i++) {
            row[i] = ' ';
         }
      }   
      // Place each word in the word search grid 
      for (String word : words) {
         if (!placeWord(word)) {
            System.out.println("Failed to place the word: " + word);
         }
      }
   }
  
   private boolean placeWord(String word) {
      int[] dx = {0, 0, 1, -1, 1, -1, 1, -1};
      int[] dy = {1, -1, 0, 0, 1, -1, -1, 1};
      int gridSize = wordSearch.length;
      Random random = new Random();
      // Attempt to place the word in the grid within 100 tries
      for (int attempt = 0; attempt < 100; attempt++) {
         // Generate random starting position and direction
         int row = random.nextInt(gridSize);
         int col = random.nextInt(gridSize);
         int dir = random.nextInt(dx.length);
         // Calculate the ending position of the word 
         int endRow = row + dx[dir] * (word.length() - 1);
         int endCol = col + dy[dir] * (word.length() - 1);
         // Check if the ending position is within the grid boundaries 
         if (endRow >= 0 && endRow < gridSize && endCol >= 0 && endCol < gridSize) {
            boolean valid = true;
            // Check if the word can be placed without overlapping existing letters 
            for (int i = 0; i < word.length(); i++) {
               char cell = wordSearch[row + i * dx[dir]][col + i * dy[dir]];
               if (cell != ' ' && cell != word.charAt(i)) {
                  valid = false;
                  break;
               }
            }
            // If the word placement is valid, place it in the grid
            if (valid) {
               for (int i = 0; i < word.length(); i++) {
                  wordSearch[row + i * dx[dir]][col + i * dy[dir]] = word.charAt(i);
               }
               return true;
            }
         }
      }  
      // Failed to place the word within 100 tries
      return false;
   }
  
   public void print() {
      // Print the word search grid
      for (char[] row : wordSearch) {
         for (char cell : row) {
            System.out.print(cell + " ");
         }
         System.out.println();
      }
   }
  
   public void showSolution() {
      // Print the solution to the word search grid
      for (char[] row : wordSearch) { //enhanced for-loop (for-each loop)
      // Loop through each element in the row
         for (char cell : row) {
         // Perform operations with the current element (cell)
            if (cell == ' ') {
               System.out.print("x ");
            } else {
               System.out.print(cell + " ");
            }
         }
         System.out.println();
      }
   }
}
