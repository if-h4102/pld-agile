# Cofoja

> Contracts for Java, or Cofoja for short, is a contract programming framework
> and test tool for Java, which uses annotation processing and bytecode
> instrumentation to provide run-time checking. (In particular, this is not a
> static analysis tool.)

[Project home][cofoja]

Check [the Wikipedia page about _Design by Contract_][wiki-design-by-contract]
to learn more about it.

## Example

The following example demonstrate how Cofoja can perform runtime checks for a
pre-condition.

Directory structure:
```text
.
├── cofoja.asm.jar
└── Main.java
```

`Main.java`:
```java
import com.google.java.contract.Ensures;
import com.google.java.contract.Requires;

public class Main {
    public static void main(String[] args) {
        // 4 is even, it is a valid argument
        System.out.println("Half of 4:");
        System.out.println(half(4));

        // 11 is odd, it breaks the pre-condition "n is even"
        System.out.println("Half of 11:");
        System.out.println(half(11)); // This should throw an Exception
    }

    /**
     * Returns the half of the even integer n.
     *
     * @param n The input integer. It must be even.
     * @return The integer k such that `n = 2k`
     */
    @Requires("n % 2 == 0") // Check the pre-condition "n is even"
    @Ensures("n == 2 * result") // Check the post-condition "n = 2k"
    public static int half (int n) {
        return n / 2;
    }
}

```

Compile and run it with the Cofoja agent:
```shell
javac -classpath cofoja.asm.jar Main.java
java -javaagent:cofoja.asm.jar -classpath . Main
```

Expected output (prints `half(4)` but breaks on `half(11)`):
```text
Half of 4:
2
Half of 11:
Exception in thread "main" com.google.java.contract.PreconditionError: n % 2 == 0
        at Main.half.<pre>(Main.java:21)
        at Main.half(Main.java)
        at Main.main(Main.java:12)
```

Note that Cofoja is a debugging tool, the pre-conditions will not be checked if
you do a _normal_ execution of your Code (without the special _java agent_):

The following execution:
```shell
java -classpath . Main
```
Leads to an output with an invalid statement:
```text
Half of 4:
2
Half of 11:
5
```

## Installation

### Maven

[Maven](./apache-maven.md) should install and configure everything
automatically when you run `mvn test`.

### Intellij IDEA

1. Download the latest version of the Cofoja JAR
    
   - **Automatically (Maven)**: Run `mvn test` once, this will download the
     latest version of _Cofoja_ to your local Maven repository.
     
     By default, its path will be:
     ```text
     $HOME/.m2/repository/com/google/java/contract/cofoja/1.3/cofoja-1.3.jar
     ```
   
   - Manually: Download it from [the latest release page][cofoja-latest]. There
     are multiple versions (`cofoja`, `cofoja.asm`, `cofoja.contracts`, etc.),
     chose `cofoja` (simple). Place it in the `lib/` directory.

2. In the `Run/Debug Configurations` menu (button in the top-right area),
   edit of the `VM options` of the configurations that you want to run with
   _Cofoja_ enabled. Add the switch `-javaagent:<path-to-cofoja-jar>`, replace
   `<path-to-cofoja-jar>` by the path to the JAR you previously downloaded.
   
   **If you downloaded it with Maven**, use:
   
   ```text
   -javaagent:"$MAVEN_REPOSITORY$/com/google/java/contract/cofoja/1.3/cofoja-1.3.jar"
   ```

### Eclipse

Let's explain to you how to use CoFoJa using an example.

1. Create a basic console Java project in Eclipse, and add the following main class:
    
    ```java
    public class Main {
      public static void main(String[] args) {
        // Let's see how CoFoJa works
        System.out.println(squareRoot(-7));
      }

      public static double squareRoot(double number) {
        return Math.sqrt(number);
      }
    }
    ```
    
2. Run it. Of course it will throw a nice error, cause you tried to compute the square root of -7
    (you're probably bad at maths).
    
    But hey, after all, who said you need to be good at maths ?
    Why people who coded the `Math` library haven't prevented you from doing such an heresy ?
    
    That's where CoFoJa comes in.
    
3. Download **cofoja.asm-version.jar** from [the official repository](https://github.com/nhatminhle/cofoja/releases).

    **Be sure to take the latest version, and the one bundled with asm**
    (i.e., the one named cofoja.asm-version.jar).
    
4. Put in next to your `/src` folder, let's say under `/lib`.

5. Go under `Project > Properties > Java build path > Libraries > Add external JARs` and add the previously downloaded jar.

6. Go under `Project > Properties > Java compiler > Annotation processing > Factory path > Add external JARs` and add the previously dowloaded jar.

    You'll need to check *Enable project specific settings* first.

7. Go under `Project > Properties > Java compiler > Annotation processing` and add the following keys - values:

        com.google.java.contract.classoutput  : %PROJECT.DIR%/bin
        com.google.java.contract.classpath    : %PROJECT.DIR%/lib/cofoja.asm-version.jar
        com.google.java.contract.sourcepath   : %PROJECT.DIR%/src
        
    Of course, correct the values with the right ones if needed.
    
    You'll need to check *Enable project specific settings* first,
    as well as *Enable annotation processing* and *Enable processing in editor*.
    
8. Then, you can import CoFoJa annotations and add some, so the example class looks like this:

    ```java
    import com.google.java.contract.Requires;
    
    public class Main {
      public static void main(String[] args) {
        // Let's see how CoFoJa works
        System.out.println(squareRoot(-7));
      }

      @Requires("number > 0 && a <3")
      public static double squareRoot(double number) {
        return Math.sqrt(number);
      }
    }
    ```
    
9. Eclipse should now tell you that there's an error with your contract, because a is undefined.

    Congratulation, you've successfully added CoFoJa to the Eclipse project! 

10. When running, you still see an error, but related to the contract.

**Pro tip**: if CoFoJa still doesn't work, you can see what's wrong when building / cleaning the project
by showing the error log window: `Window > Show View > Error Log`.


[cofoja]: https://github.com/nhatminhle/cofoja
[cofoja-latest]: https://github.com/nhatminhle/cofoja/releases/latest
[wiki-design-by-contract]: https://en.wikipedia.org/wiki/Design_by_contract

