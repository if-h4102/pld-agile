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

[Maven](./apache-maven.md) is already configured to compile with Cofoja and
enable contract checks during the tests (`mvn test`).

### Intellij IDEA

_IntelliJ IDEA_ imports the configuration from _Maven_, you do not need to
configure anything: **it just works**.

Just like Maven, it only enables contract checks for tests. Normally, this
should be enough. If you want to enforce these checks in other run
configurations, add the VM option `-javaagent:lib/cofoja.asm-1.3.jar` (in
the `Run/Debug Configurations` menu).

### Eclipse

You need to enable Annotation Processing (to generate the contracts during the
compilation) and then configure the execution to enable runtime verification of
the contracts.

If you encounter an error, display the logs with **Window | Show View
| Error Log**.

#### Compilation configuration

You need to enable Annotation processing.

1. Open the _Annotation Processing_ menu: **Project | Properties
   | Java Compiler | Annotation Processing**.

2. Check **Enable project specific settings**.

3. Check **Enable annotation processing**.

4. Check **Enable processing in editor**.

5. Set the value of **Generated source directory** to:
   `target/generated-sources/annotations`.

6. Use the `New` button to add the following properties:

   | Key                                    | Value                                  |
   | -------------------------------------- | -------------------------------------- |
   | `com.google.java.contract.classoutput` | `%PROJECT.DIR%/target/classes`         |
   | `com.google.java.contract.classpath`   | `%PROJECT.DIR%/lib/cofoja.asm-1.3.jar` |
   | `com.google.java.contract.sourcepath`  | `%PROJECT.DIR%/src`                    |

7. Apply changes with the `Apply` button.

8. Open the _Annotation Factory Path_ menu: **Project | Properties
   | Java Compiler | Annotation Processing | Factory Path**.

9. Check **Enable project specific settings**.

10. Use the `Add JAR` button to add `cofoja.asm-1.3.jar` (located in the `lib`
   directory of this project).

11. Apply changes with the `Apply` button.

12. Configure the execution (see next section).

#### Execution configuration

To enable the runtime evaluation of Cofoja contracts, you have to add a
VM options.

1. Open the _Run configurations_ menu: **Run | Run configurations...**

2. Select or create a configuration.

3. Open the **Arguments** tab.

4. Add the following parameter to the **VM options** text box:

   ```text
   -javaagent:lib/cofoja.asm-1.3.jar
   ```


[cofoja]: https://github.com/nhatminhle/cofoja
[cofoja-v1.3]: https://github.com/nhatminhle/cofoja/releases/tag/v1.3
[wiki-design-by-contract]: https://en.wikipedia.org/wiki/Design_by_contract
