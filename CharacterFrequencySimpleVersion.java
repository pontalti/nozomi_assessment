

import java.time.Duration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * A simple utility class for identifying duplicate characters in a character array.
 * <p>
 * This version uses a single-threaded approach with basic collections
 * and is best suited for small input datasets due to its sequential nature.
 *
 * <p><b>Time Complexity:</b> O(N + K)
 * <br>Where:
 * <ul>
 *     <li><b>N</b> is the total number of characters in the input</li>
 *     <li><b>K</b> is the number of unique characters</li>
 * </ul>
 *
 * <p><b>Space Complexity:</b> O(K)
 * <br>Where:
 * <ul>
 *     <li><b>K</b> is the number of unique characters stored in maps/sets</li>
 * </ul>
 */
public class CharacterFrequencySimpleVersion {

    /**
     * Main method for running the example with default or user-supplied input.
     * <p>
     * Measures and prints the execution time for finding duplicate characters.
     *
     * @param args Optional command-line input; uses default example if none is provided
     */
    public static void main(String[] args) {
        var input = (args.length > 0)
                ? String.join("", args).toCharArray()
                : new char[] { 'c', 'a', 'i', 'o', 'p', 'a' };

        var startTime = System.nanoTime();

        var result = findDuplicates(input);
        System.out.println("Characters appearing at least twice: " + result);

        var endTime = System.nanoTime();
        var durationMs = elapsedMillis(startTime, endTime);

        System.out.printf("Execution time for %d characters - %d Millis%n%n", input.length, durationMs);
    }

    /**
     * Identifies characters that appear at least twice in the given input array.
     * <p>
     * This implementation uses a HashMap to count occurrences and a HashSet
     * to collect duplicates.
     *
     * @param input The array of characters to analyze
     * @return A set of characters that appear two or more times
     */
    public static Set<Character> findDuplicates(char[] input) {
        Map<Character, Integer> frequencyMap = new HashMap<>();
        Set<Character> duplicates = new HashSet<>();

        for (char c : input) {
            frequencyMap.put(c, frequencyMap.getOrDefault(c, 0) + 1);
        }

        for (Map.Entry<Character, Integer> entry : frequencyMap.entrySet()) {
            if (entry.getValue() >= 2) {
                duplicates.add(entry.getKey());
            }
        }

        return duplicates;
    }

    /**
     * Calculates the elapsed time in milliseconds between two time points in nanoseconds.
     *
     * @param startTime The start time in nanoseconds
     * @param endTime   The end time in nanoseconds
     * @return The elapsed time in milliseconds
     */
    private static long elapsedMillis(long startTime, long endTime) {
        return Duration.ofNanos(endTime - startTime).toMillis();
    }
}
