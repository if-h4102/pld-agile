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

## Advanced

### Maven integration

Even if the `pom.xml` file lists a dependency on `cofoja`, we are effectively
using a manually download JAR file (in the `lib` directory) instead of the
version from the repository. There are two reasons: there is no officially
published package for Cofoja and configuration with the path to the JAR file
is tricky.

Maven (the build tool) looks for dependency on the public "official" repository
called _Maven Central_ ([about Central][about-maven-central]). There is [an
entry for Cofoja][maven-central-cofoja] on Central, but [it was published by a
third-party][cofoja#44] and now the author doesn't have access to it:

> I didn't publish the previous version; someone else did.

What's worse, this version is obsolete (1.1, published in 2013).

Now, it is still possible to install the latest version (1.3) thanks to a
contributor who set up a secondary repository. Currently it works, but it is
not a viable solution (will this contributor keep updating this version ? it
should be owned by the author...). You can use it with the following
configuration in your `pom.xml`:

```xml
<project>
    <!-- [...] -->
    <repositories>
        <repository>
            <!-- Private repository for Cofoja (see cofoja#2 and cofoja#44) -->
            <id>maven-cofoja-repo</id>
            <url>http://maven-cofoja.github.io/maven</url>
        </repository>
    </repositories>
    
    <dependencies>
        <!-- [...] -->
        <dependency>
            <!-- This will be resolved against the private repository -->
            <groupId>com.google.java.contract</groupId>
            <artifactId>cofoja</artifactId>
            <version>1.3</version>
        </dependency>
        <dependency>
            <!--
            Transitive dependency required for the execution. I hoped it would
            be downloaded automatically (transitive dependency ?) but looks
            like we have to add it manually.
            -->
            <groupId>org.ow2.asm</groupId>
            <artifactId>asm-all</artifactId>
            <version>5.0.3</version>
        </dependency>
    </dependencies>
    
    <!-- [...] -->
</project>
```

With the above configuration, Maven should download `cofoja-1.3.jar` to the
the local Maven repository. By default, the directory will be 
`$HOME/.m2/repository/com/google/java/contract/cofoja/1.3/`.

The second pain point with the Maven integration is to actually use this JAR
file, and more specifically to get a platform and IDE independent way to
reference this file.

If you only use Maven, it is possible to achieve this thanks to the
`properties` goal of the `dependency` plugin (`maven-dependency-plugin`).
It adds properties (variables) to your Maven configuration based on the
dependencies, like the path to the JAR file. [See the
documentation][maven-dependency-properties]. You have to execute this goal
during the initialization phase so other plugins can use it. Here is the
configuration to achieve this:

```xml
<project>
    <!-- [...] -->
    <build>
        <!-- [...] -->
        <pluginManagement>
            <plugins>
                <!-- [...] -->
                <plugin>
                    <groupId>org.apache.maven.plugins</groupId>
                    <artifactId>maven-dependency-plugin</artifactId>
                    <version>2.10</version>
                    <executions>
                        <!-- [...] -->
                        <execution>
                            <phase>initialize</phase>
                            <goals>
                                <goal>properties</goal>
                            </goals>
                        </execution>
                    </executions>
                </plugin>
            </plugins>
        </pluginManagement>
    </build>
</project>
```

You can reference the path of the Cofoja JAR with
`${com.google.java.contract:cofoja:jar}` and use it in the rest of your file.
Maven will understand it. Only Maven... (but we'll come back to this later)

This allows you to configure annotation processing during the compilation:
```xml
<project>
    <!-- [...] -->
    <build>
        <!-- [...] -->
        <pluginManagement>
            <!-- [...] -->
            <plugins>
                <plugin>
                    <groupId>org.apache.maven.plugins</groupId>
                    <artifactId>maven-compiler-plugin</artifactId>
                    <version>3.1</version>
                    <configuration>
                        <source>1.8</source>
                        <target>1.8</target>
                        <showDeprecation>true</showDeprecation>
                        <showWarnings>true</showWarnings>
                        <compilerArgs>
                            <arg>-Acom.google.java.contract.classoutput=${basedir}/target/classes</arg>
                            <!-- Use the property defined by the dependency plugin: -->
                            <arg>-Acom.google.java.contract.classpath=${com.google.java.contract:cofoja:jar}</arg>
                            <arg>-Acom.google.java.contract.sourcepath=${basedir}/src/main/java</arg>
                        </compilerArgs>
                    </configuration>
                </plugin>
            </plugins>
        </pluginManagement>
    </build>
</project>
```

The build step now uses Cofoja, to finish the configuration we still need to
configure the configuration. The Cofoja contacts are ignored at runtime unless
you use tell your Java VM to use a special _java agent_. You need to use the
VM option `-javaagent:path/to/cofoja.jar`. Accessing the path to Cofoja is a
solved problem, but passing options to the VM happened to be a new challenge...
After many hours of searching I was able to set it while running tests but I
couldn't figure out how to set it for a standard execution (with
`mvn exec:java`, using the `exec` plugin). The closest I could get was manually
specifying the command-line ([example of how to use
`<arguments>`][maven-exec-exec-vm-options]) but I had to hard code the path
because it did not interpolate `${com.google.java.contract:cofoja:jar}` all of
a sudden...

Well, at least I could make it work for tests so I guess it's the most
important, here are the parameters to do it:

```xml
<project>
    <!-- [...] -->
    <build>
        <!-- [...] -->
        <pluginManagement>
            <plugins>
                <!-- [...] -->
                <plugin>
                    <groupId>org.apache.maven.plugins</groupId>
                    <artifactId>maven-surefire-plugin</artifactId>
                    <version>2.15</version>
                    <configuration>
                        <argLine>-javaagent:${com.google.java.contract:cofoja:jar}</argLine>
                    </configuration>
                </plugin>
            </plugins>
        </pluginManagement>
    </build>
</project>
```

With the above configuration, Maven works good enough: it downloads Cofoja
from a repository, compiles with annotation processing and perform runtime
checks during the tests.

Now, you can get a deal of new issues when you try to use it with an IDE. The
main reason is the ~~voodoo magic~~ `dependency:properties` goal we used to get
the actual path of the Cofoja JAR. It already lead to some issues with normal
execution, but this property is pretty confusing for IDEs. Eclipse goes into
full panic mode over it so I decided to rather spend my time on making it 
work with IntelliJ IDEA.

IntelliJ IDEA was able to automatically set most of its parameters, except for
the annotation processing parameter `` and the VM option for the execution.

After some search, I figured out that there was no easy way to get it to use
the dynamically defined property of the `dependency` plugin, but if we assumed
that the path _inside_ of the local Maven repository was the same across all
platforms, then it was possible to deal with it thanks to a [path
variable][idea-path-variables] (it's pretty under-documented in my opinion).
`$MAVEN_REPOSITORY$/com/google/java/contract/cofoja/1.3/cofoja-1.3.jar` would
expand to the right path thanks to the path variable `$MAVEN_REPOSITORY$` (it
should be set automatically).

You have to set `com.google.java.contract.classpath` to
`$MAVEN_REPOSITORY$/com/google/java/contract/cofoja/1.3/cofoja-1.3.jar`
in **File | Settings... | Build, Execution, Deployment | Compiler
| Annotation Processors**.

And then edit any _Run/Debug configuration_ you want and add the VM option:
`-javaagent:$MAVEN_REPOSITORY$/com/google/java/contract/cofoja/1.3/cofoja-1.3.jar`

This should work... Until you reload the IDE configuration from the `pom.xml`.

This should not happen that often but I'd prefer to avoid that a refresh change
a value I set in a dark corner of my IDE any time I edit the `pom.xml`. Well,
it was a nice try but until Maven or IntelliJ sort this out, it's not possible
to use it this way.

This is why I chose to simply use a manually download JAR checked in to the
version control of the project.
- There is no official repository for Cofoja
- Configuring the compilation and execution is tricky
- The IDEs are not ready for this

### `cofoja.jar` or `cofoja.asm.jar` ?

Cofoja has two optional flavors `asm` and `contracts`.
In this section I would like address the effect of the `asm` variant: what is
the difference between `cofoja.jar` and `cofoja.asm.jar`.

`asm` refers to the [`asm-all`][maven-central-asm-all] package:
- `cofoja.jar` only contains the core code for Cofoja
- `cofoja.jar` bundles both the core code for Cofoja and the `asm-all` package

`asm-all` is only required for the execution (when Cofoja acts as a Java
agent). It helps Cofoja to deal with bytecode.

If you want, you could have used the simple `cofoja.jar` in the example at the
top, but then you would have to add `asm-all.jar` to your classpath.

Directory structure
```text
.
├── cofoja.jar
├── asm-all.jar
└── Main.java
```

Build and run
```shell
javac -classpath cofoja.jar Main.java
java -javaagent:cofoja.jar -classpath . Main
```


[cofoja]: https://github.com/nhatminhle/cofoja
[cofoja#44]: https://github.com/nhatminhle/cofoja/issues/44
[cofoja-v1.3]: https://github.com/nhatminhle/cofoja/releases/tag/v1.3
[wiki-design-by-contract]: https://en.wikipedia.org/wiki/Design_by_contract
[about-maven-central]: http://central.sonatype.org/
[maven-central-cofoja]: https://mvnrepository.com/artifact/com.google.java.contract/cofoja/1.1-r150
[maven-central-asm-all]: https://mvnrepository.com/artifact/org.ow2.asm/asm-all
[maven-dependency-properties]: http://maven.apache.org/plugins/maven-dependency-plugin/properties-mojo.html
[maven-exec-exec-vm-options]: http://stackoverflow.com/questions/5396749/pass-command-line-params-in-mvn-execexec
[idea-path-variables]: https://www.jetbrains.com/help/idea/2016.2/path-variables.html
