
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Utility class for analyzing character frequencies using concurrent processing.
 * Uses Java Virtual Threads and optimized merging for high performance on large datasets.
 */
public class CharacterFrequencyVTVersion {

    /**
     * Finds and prints characters that appear at least twice in the given character array,
     * using Java Virtual Threads and ConcurrentHashMap.merge for efficient parallel processing.
     *
     * <p><b>Time Complexity:</b> O(N/P + K)
     * <br>Where:
     * <ul>
     *     <li><b>N</b> is the total number of characters</li>
     *     <li><b>P</b> is the number of virtual threads (usually based on available CPU cores)</li>
     *     <li><b>K</b> is the number of unique characters</li>
     * </ul>
     *
     * <p><b>Space Complexity:</b> O(numChunks * K_chunk + K_global)
     * <br>Where:
     * <ul>
     *     <li><b>K_chunk</b> is the number of unique characters per chunk</li>
     *     <li><b>K_global</b> is the total number of unique characters merged</li>
     * </ul>
     *
     * @param characters The input character array to process
     */
    public static void printCharactersAppearingAtLeastTwice(char[] characters) {
        List<Future<Map<Character, Integer>>> futures = new ArrayList<>();
        
        //Thread-safe map to accumulate total character frequencies
        ConcurrentHashMap<Character, Integer> globalCharCounts = new ConcurrentHashMap<>();

        int numChunks = Math.max(1, Math.min(Runtime.getRuntime().availableProcessors(), characters.length));
        int chunkSize = (int) Math.ceil((double) characters.length / numChunks);

        // Submit tasks for each chunk of the array
        try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();){
	        for (int i = 0; i < characters.length; i += chunkSize) {
	            final int start = i;
	            final int end = Math.min(characters.length, i + chunkSize);
	
	            if (start >= end) continue;
	
	            Callable<Map<Character, Integer>> task = () -> {
	                Map<Character, Integer> localCharCounts = new HashMap<>();
	                for (int j = start; j < end; j++) {
	                    localCharCounts.put(characters[j],
	                        localCharCounts.getOrDefault(characters[j], 0) + 1);
	                }
	                return localCharCounts;
	            };
	            futures.add(executor.submit(task));
	        }
	
	        /**
	         * Merging is done using ConcurrentHashMap.merge to avoid race conditions
	         * Parallel merge of futures using parallelStream (safe with exceptions)
	         */
            futures.parallelStream()
		            .map(future -> {
		                try {
		                    return future.get();
		                } catch (InterruptedException | ExecutionException e) {
		                    throw new RuntimeException("Error retrieving future result", e);
		                }
		            })
		            .forEach(localMap ->
		                localMap.forEach((key, value) ->
		                    globalCharCounts.merge(key, value, Integer::sum)
		                )
		            );
	    } catch (Exception e) {
	        System.err.println("Error during Virtual Thread execution: " + e.getMessage());
	        e.printStackTrace();
	    }

        // Find and print characters that appear at least twice
        Set<Character> duplicates = globalCharCounts.entrySet().stream()
        	    .filter(entry -> entry.getValue() >= 2)
        	    .map(Map.Entry::getKey)
        	    .collect(Collectors.toSet());

        System.out.print("Characters appearing at least 2 times (Virtual Threads): ");
        System.out.println(
        	    duplicates.stream()
        	              .map(ch -> "'" + ch + "'")
        	              .collect(Collectors.joining(", ", "{", "}"))
        	);
    }

    /**
     * Main method for testing with multiple example cases, including a long input to
     * demonstrate the performance benefit of virtual threads and concurrent merging.
     *
     * @param args Command-line arguments (not used)
     */
    public static void main(String[] args) {
        var example1 = (args.length > 0)
                ? String.join("", args).toCharArray()
                : new char[] { 'c', 'a', 'i', 'o', 'p', 'a' };

        System.out.println("--- Example_1 ---");
        var startTime = System.nanoTime();
        printCharactersAppearingAtLeastTwice(example1);
        var endTime = System.nanoTime();
        var durationMs1 = elapsedMillis(startTime, endTime);
        
        System.out.printf("Example_1 execution time for %d characters - %d Millis \n\n", example1.length, durationMs1);
        System.out.println("--- Executing example_2 with a very long string to demonstrate Virtual Threads potential ---");

        var arraySize = 1_000_000_000;
        var longString = new char[arraySize];
        IntStream.range(0, arraySize)
                .parallel()
                .forEach(i -> longString[i] = (char) ('a' + (i % 26)));

        // Measure execution time for example 2
        startTime = System.nanoTime();
        printCharactersAppearingAtLeastTwice(longString);
        endTime = System.nanoTime();
        var durationMs2 = elapsedMillis(startTime, endTime);
        
        System.out.printf("Example_2 execution time for %d characters - %d Millis \n", longString.length, durationMs2);
    }

	private static long elapsedMillis(long startTime, long endTime) {
		return Duration.ofNanos(endTime - startTime).toMillis();
	}
}
