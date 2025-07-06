
# Assessment Q&A

## 1. Write an algorithm (choose the language you prefer) that given a string of characters, for example {'c','a','i','o','p','a'}, will print out the list of characters appearing at least 2 times. In this specific example, it would return {'a'}. Afterwards, comment out the cost in terms of space and time.

- 📘 **For a comprehensive explanation of question 1, please consult the [question_1.md](./question_1.md) file.**

---

## 2. What is a unit test? And an integration test? What is the difference? What about a system test?

- **Unit Test**: Tests a single component (e.g., method or class) in isolation from the rest of the system. It ensures that the smallest parts of the application behave as expected.
- **Integration Test**: Tests the interaction between multiple components or modules to ensure they work together correctly.
- **System Test**: Validates the entire application as a whole, ensuring that all integrated parts function correctly in the complete environment.

**Difference**: Unit tests are focused on individual parts in isolation, integration tests check how components interact, and system tests verify the entire system's functionality.

---

## 3. Do you know design patterns? Describe one briefly.

Yes. One commonly used design pattern is the **Builder Pattern**.

- **Builder Pattern**: It separates the construction of a complex object from its representation, allowing the same construction process to create different representations.

### Example:

```java
public class User {
    private final String name;
    private final int age;

    private User(Builder builder) {
        this.name = builder.name;
        this.age = builder.age;
    }

    public static class Builder {
        private String name;
        private int age;

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder age(int age) {
            this.age = age;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }
}
```

Usage:

```java
User user = new User.Builder().name("Alice").age(30).build();
```

---

## 4. Describe the pros and cons of writing a multi-process vs a multi-thread application

### Multi-threaded Application:

**Pros:**
- Lower memory usage (shared memory space).
- Faster context switching compared to processes.
- Easier communication via shared memory.

**Cons:**
- Prone to race conditions and deadlocks.
- Difficult to debug and maintain.
- One thread crashing can bring down the entire process.

### Multi-process Application:

**Pros:**
- Better isolation (one process crash doesn't affect others).
- Easier to scale across CPU cores.
- More secure (separate memory space).

**Cons:**
- More memory consumption.
- Higher overhead for inter-process communication.
- Slower context switching.

---

## 5. What's the most boring thing about your job? And what's the most satisfying?

**What's the most boring thing about your job?**

While I genuinely enjoy working as a Java back-end developer, one of the more monotonous aspects of the role can be dealing with repetitive boilerplate code or maintaining legacy systems. These tasks can feel less stimulating at times, especially when the work doesn't immediately translate into visible improvements.

That said, I see even these situations as opportunities — whether it's identifying areas to refactor, proposing improvements, or automating recurring patterns. They often lead to long-term benefits for the team and the codebase.

**What's the most satisfying?**

The most fulfilling part of my job is designing and delivering robust, scalable back-end solutions that make a real difference in system performance and reliability. I take pride in solving complex technical problems, mentoring junior developers, and seeing how a well-architected back-end contributes to the overall success of a product.

Knowing that my work enables other teams to move faster and customers to have a better experience is highly rewarding.

---

## 6. Explain and comment this Dockerfile

This Dockerfile demonstrates how to:
- Generate a file with secure random content.
- Clean up files through a scripted removal.
- Use multi-stage shell scripting inside a Docker image.

```dockerfile
# Use Bitnami's lightweight Debian-based image as the base
FROM bitnami/minideb:latest

# Create a temporary authentication file with 32 random printable characters
RUN bash -c "echo "$(< /dev/urandom tr -cd '[:print:]' | head -c 32; echo)" > /tmp/auth"

# Create a script named remove on / that will delete the /tmp/auth file if it exists
RUN <<EOF cat > /remove
#!/usr/bin/env bash
test -f /tmp/auth && rm -f /tmp/auth
EOF

# Make the remove script executable and run it to delete /tmp/auth
RUN chmod +x /remove; \
    bash /remove

# Set the default command to print "done" when the container starts
CMD ["/usr/bin/echo", "done"]
```