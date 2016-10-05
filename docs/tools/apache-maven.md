# Apache Maven

[_Apache Maven_][maven] (or simply _Maven_) is a project management tool.

## Dependencies

- Java Development Kit (JDK)

## Installation

Download the binary archive from [the official download page][maven-download],
extract it and add its `bin` directory to your `PATH`.

### Detailed steps for Windows

1. Choose `apache-maven-3.3.9-bin.zip` from [the download page][maven-download]

2. Extract the archive, you should get a directory `apache-maven-3.3.9-bin`.

3. Create the directory `<programs>\Apache\Maven` where `<program>` is your
   Program Files directory (by default: `C:\Program Files (x86)`).
   
   Example: `C:\Program Files (x86)\Apache\Maven`.

4. Copy all the extracted content of `apache-maven-3.3.9-bin` to the newly
   created `<programs>\Apache\Maven` directory.
   
   Check that the following path leads a `cmd.exe` script:
   `<program>\Apache\Maven\bin\mvn.cmd`.
   
5. Add `<program>\Apache\Maven\bin\` to your `PATH` environment variable.

   - GUI: `System properties`/`Advanced system settings`/`Environment variables ...` (`Advanced` tab) then edit the value of `PATH`.
   
   - Command line:
   
   ```shell
   setx path "%path%;<program>\Apache\Maven\bin\"
   ```
   
   Example: `setx path "%path%;C:\Program Files (x86)\Apache\Maven\bin\"`

### Check the installation

The following command should return the installed version:

```shell
mvn --version
```

Currently (2016-10-04), the stable version is `3.3.9`.

## Getting started

### Compile a project

```shell
mvn compile
```

### Test

```
mvn test
```

### Execute the main target

```shell
mvn exec:java 
```

**This will not rebuild the project, even if changes occurred.**
Use the following command if you want to ensure that you run the latest version:

```shell
mvn compile && mvn exec:java
```

### Build a Java ARchive (JAR)

```shell
mvn jar:jar
```

### Clean

```
mvn clean
```

### Generate project files for Eclipse

```
mvn eclipse:eclipse
```

## Editor integration

[Official-documentation][maven-ide]

The following IDE offer support for Maven:

- **Eclipse**: Native support in newer versions (confirmed for _Neon_), else
  requires the _m2e_ plugin.
- **IntelliJ IDEA**: Native support.
- **Netbeans**: Native support.


[maven]: https://maven.apache.org/index.html
[maven-download]: https://maven.apache.org/download.cgi
[maven-ide]: https://maven.apache.org/ide.html
[m2eclipse]: http://www.eclipse.org/m2e/
