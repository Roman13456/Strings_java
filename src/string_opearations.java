import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Main class to perform word search in sentences.
 */
public class string_opearations {

    /**
     * The main method serves as the entry point of the program.
     * It prompts the user to input text and words to search for within the text.
     *
     * @param args command-line arguments (not used).
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        StringBuffer text = new StringBuffer();
        while (text.length() == 0) {
            System.out.println("Please enter the text:");
            String inputText = scanner.nextLine().trim();
            if (!inputText.isEmpty()) {
                text = new StringBuffer(inputText);
                if (!endsWithPunctuation(text)) {
                    text.append('.');
                    System.out.println("Text was missing a sentence-ending punctuation, a period was added.");
                }
            } else {
                System.out.println("Text cannot be empty. Please try again.");
            }
        }

        int wordCount = 0;
        while (wordCount <= 0) {
            System.out.println("Enter the number of words to search for (positive integer):");
            if (scanner.hasNextInt()) {
                wordCount = scanner.nextInt();
                if (wordCount <= 0) {
                    System.out.println("The number of words must be positive. Please try again.");
                }
            } else {
                System.out.println("Please enter a valid number.");
                scanner.next(); 
            }
        }

        scanner.nextLine(); // Consume the leftover newline character

        StringBuffer[] words = new StringBuffer[wordCount];
        for (int i = 0; i < wordCount; i++) {
            while (true) {
                System.out.println("Enter word #" + (i + 1) + ":");
                String inputWord = scanner.nextLine().trim();
                if (!inputWord.isEmpty()) {
                    words[i] = new StringBuffer(inputWord);
                    break;
                } else {
                    System.out.println("Word cannot be empty. Please enter again.");
                }
            }
        }
        countWordOccurrencesInSentences(text, words);
        scanner.close();
    }

    /**
     * Checks if the given text ends with sentence-ending punctuation (. ! ?).
     *
     * @param text the text to check.
     * @return true if the text ends with punctuation, false otherwise.
     */
    public static boolean endsWithPunctuation(StringBuffer text) {
        char lastChar = text.charAt(text.length() - 1);
        return lastChar == '.' || lastChar == '!' || lastChar == '?';
    }

    /**
     * Counts occurrences of specified words in sentences from the given text.
     *
     * @param text  the text to be processed into sentences.
     * @param words the array of words to search for.
     */
    public static void countWordOccurrencesInSentences(StringBuffer text, StringBuffer[] words) {
        List<StringBuffer> sentences = new ArrayList<>();
        StringBuffer currentSentence = new StringBuffer();

        // Split text into sentences
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            currentSentence.append(c);
            if (c == '.' || c == '!' || c == '?') {
                sentences.add(currentSentence);
                currentSentence = new StringBuffer();
            }
        }

        System.out.println(System.lineSeparator() + "Word(s):");

        // Search for each word in sentences
        for (StringBuffer word : words) {
            int count = 0;
            for (StringBuffer sentence : sentences) {
                if (containsWord(sentence, word)) {
                    count++;
                }
            }
            System.out.println("\"" + word + "\" is in " + count + " sentence(s)");
        }
    }

    /**
     * Checks if a word exists within a sentence using manual comparison (without regex or String conversion).
     *
     * @param sentence the sentence in which to search.
     * @param word     the word to search for.
     * @return true if the word exists in the sentence, false otherwise.
     */
    public static boolean containsWord(StringBuffer sentence, StringBuffer word) {
        int wordLength = word.length();
        int sentenceLength = sentence.length();

        // Search for the word in the sentence
        for (int i = 0; i <= sentenceLength - wordLength; i++) {
            if (isWordAtPosition(sentence, word, i)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Determines if a word starts at a given position within a sentence,
     * ensuring it is not part of a larger word (e.g., "rtx" does not match "rtx3070").
     *
     * @param sentence the sentence to search in.
     * @param word     the word to look for.
     * @param position the starting position to check for the word.
     * @return true if the word starts at the given position, false otherwise.
     */
    public static boolean isWordAtPosition(StringBuffer sentence, StringBuffer word, int position) {
        int wordLength = word.length();

        // Check that the characters before and after the word are not letters or digits
        if (position > 0 && Character.isLetterOrDigit(sentence.charAt(position - 1))) {
            return false;
        }
        if (position + wordLength < sentence.length() &&
                Character.isLetterOrDigit(sentence.charAt(position + wordLength))) {
            return false;
        }

        // Compare the word with the sentence character by character
        for (int i = 0; i < wordLength; i++) {
            if (Character.toLowerCase(sentence.charAt(position + i)) != Character.toLowerCase(word.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}