# PLD-Agile

Java app developed using SCRUM - Delivery helper

## DEV - Setting up the project

Regarding the project's constraints, a good choice is to develop using Eclipse.
The following tutorial will teach you how to use basic tools with Eclipse.

### Eclipse

[Eclipse](https://eclipse.org) is an open source IDE for Java's technologies.
You can download the latest version [here](https://eclipse.org/downloads/).

To develop in Java, you'll need a JDK.
**Having one or more JREs beside a JDK will cause conflicts**, so the best way to avoid them is probably to delete all JREs and only keep the JDK.
Once done, if you already had Eclipse installed, it probably won't start anymore.
You'll then need to follow these steps (assuming you're using Windows; otherwise, just do the equivalent):

1. Find `eclipse.ini`, located somewhere like `C:\users\me\eclipse\java-neon\eclipse\eclipse.ini`.
2. Add the following lines (correct the last one with the right path):

        -vm
        C:\Program Files\Java\jdk1.8.0_101\bin\javaw.exe
        
3. Remove the following path from either from PATH or an existing environment variable:

        C:\ProgramData\Oracle\Java\javapath;
        
4. Add the JDK's bin folder to your PATH (correct the path if needed):

        PATH=%PATH%;C:\Program Files\Java\jdk1.8.0_25\bin
        
5. Start Eclipse

### JUnit

Good news, JUnit is already integrated in Eclipse Neon.1 !
All you have to do is to enable it.

1. Go under `Project > Properties > Java Build Path > Add library` and select JUnit.
2. Add a run configuration based on JUnit to run your tests.
3. Congratulations!

### CoFoJa

CoFoJa mean *Contracts for Java*.
You'll find more information about design by contract [here](https://en.wikipedia.org/wiki/Design_by_contract).

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
    
2. Run it. Of course it will throw a nice error, cause you tried to calcul the square root of -7
    (you're probably bad at maths).
    
    But hey, after all, who said you need to be good at maths ?
    Why people who coded the `Math` library haven't prevented you from doing such an heresy ?
    
    That's where CoFoJa comes in.
    
**Here comes the configuration part**
    
3. Download CoFoJa from [the official repository](https://github.com/nhatminhle/cofoja/releases).

    Be sure to take the latest version, and the one bundled with asm
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





