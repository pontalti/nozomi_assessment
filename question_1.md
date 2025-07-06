# 🔍 Character Frequency Analyzer in Java

This repository contains two Java implementations to detect characters that appear **at least twice** in a given character array:

- ✅ `CharacterFrequencySimpleVersion`: a straightforward, single-threaded version.
- 🚀 `CharacterFrequencyVTVersion`: an optimized, high-performance version leveraging **Java Virtual Threads** and concurrent processing.

---

## 📊 Comparison: Simple vs VirtualThreads

| Feature                       | Simple Version                         | VirtualThreads Version                          |
|------------------------------|----------------------------------------|-------------------------------------------------|
| **Concurrency**              | No                                     | Yes (Virtual Threads with parallel merging)     |
| **Performance on small data**| Fast and sufficient                    | Slightly more overhead                          |
| **Performance on large data**| ❌ Not scalable                         | ✅ Optimized for large inputs                    |
| **Thread Safety**            | Not applicable                         | Uses `ConcurrentHashMap`                        |
| **Parallelism**              | None                                   | Parallel chunk processing and merging           |
| **Error handling**           | Minimal                                | Robust with `Future.get()` handling             |
| **Output formatting**        | Raw `Set` output                       | Sorted and formatted with braces and quotes     |

---

## 📘 Documentation Overview

### `CharacterFrequencySimpleVersion`

- ✅ Easy to read and understand
- ✅ Great for short input arrays
- ❌ Not suitable for billions of characters or CPU-intensive contexts

```java
/**
 * Counts character frequency in a single-threaded approach.
 * 
 * Time Complexity: O(N)
 * Space Complexity: O(K)
 * Where:
 *  N = number of characters
 *  K = number of unique characters
 */
```

### `CharacterFrequencyVTVersion`

- ✅ Designed for scalability
- ✅ Leverages Virtual Threads for concurrent chunk processing
- ✅ Uses `ConcurrentHashMap.merge` to handle merging safely

```java
/**
 * Analyzes character frequencies using Java Virtual Threads.
 * 
 * Time Complexity: O(N/P + K)
 *   - N = total characters
 *   - P = available processors / threads
 *   - K = number of unique characters
 *
 * Space Complexity: O(numChunks * K_chunk + K_global)
 */
```

---

## 🛠️ How to Compile and Run

Both classes are written in **Java 21+** and require a compatible JDK installed.

### 📦 Requirements

- Java 21 or later
- Terminal or command prompt
- `javac` and `java` available in your system `PATH`

---

### ⚙️ Compile the Source

Assuming the sources are under `assessment/`:

```bash
javac -d out CharacterFrequencySimpleVersion.java CharacterFrequencyVTVersion.java
```

---

### ▶️ Run the Simple Version

#### Without parameters:
```bash
java -cp out CharacterFrequencySimpleVersion
```

#### With parameters:
```bash
java -cp out CharacterFrequencySimpleVersion hello world test
```

---

### 🚀 Run the VirtualThreads Version

#### Without parameters:
```bash
java -cp out CharacterFrequencyVTVersion
```

#### With parameters:
```bash
java -cp out CharacterFrequencyVTVersion hello world test
```

---

## 🧪 Example simple version Output without parameters

```text
java -cp out CharacterFrequencySimpleVersion
Characters appearing at least twice: [a]
Execution time for 6 characters - 7 Millis
```

## 🧪 Example simple version Output with parameters

```text
java -cp out CharacterFrequencySimpleVersion hello world test
Characters appearing at least twice: [t, e, l, o]
Execution time for 14 characters - 6 Millis
```

## 🧪 Example VTVersion Output without parameters

```text
java -cp out CharacterFrequencyVTVersion
--- Example_1 ---
Characters appearing at least 2 times (Virtual Threads): {'a'}
Example_1 execution time for 6 characters - 44 Millis

--- Executing example_2 with a very long string to demonstrate Virtual Threads potential ---
Characters appearing at least 2 times (Virtual Threads): {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'}
Example_2 execution time for 1000000000 characters - 6624 Millis
```

## 🧪 Example VTVersion Output with parameters

```text
java -cp out CharacterFrequencyVTVersion hello world test
--- Example_1 ---
Characters appearing at least 2 times (Virtual Threads): {'t', 'e', 'l', 'o'}
Example_1 execution time for 14 characters - 49 Millis

--- Executing example_2 with a very long string to demonstrate Virtual Threads potential ---
Characters appearing at least 2 times (Virtual Threads): {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'}
Example_2 execution time for 1000000000 characters - 6529 Millis
```

> ⏱ Execution time may vary based on your machine's specs.

---
