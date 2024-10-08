import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class string_operations {

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

        scanner.nextLine();

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
    
    public static boolean endsWithPunctuation(StringBuffer text) {
        char lastChar = text.charAt(text.length() - 1);
        return lastChar == '.' || lastChar == '!' || lastChar == '?';
    }
    
    public static void countWordOccurrencesInSentences(StringBuffer text, StringBuffer[] words) {
        List<StringBuffer> sentences = new ArrayList<>();
        StringBuffer currentSentence = new StringBuffer();
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            currentSentence.append(c);
            if (c == '.' || c == '!' || c == '?') {
                sentences.add(currentSentence); 
                currentSentence = new StringBuffer(); 
            }
        }

        System.out.println(System.lineSeparator()+"Word(s):");
        for (StringBuffer word : words) {
            int count = 0; 
            for (StringBuffer sentence : sentences) {
                if (containsWord(sentence, word)) {
                    count++;
                }
            }
            System.out.println("\"" +word + "\" is in " + count + " sentence(s)");
        }
    }
    
   

    public static boolean containsWord(StringBuffer sentence, StringBuffer word) {
        String sentenceStr = sentence.toString();
        String wordStr = word.toString();
        String regex = "\\b" + Pattern.quote(wordStr) + "\\b"; 
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(sentenceStr);
        return matcher.find();
    }
}
