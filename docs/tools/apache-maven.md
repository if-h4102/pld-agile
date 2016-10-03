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

### Check the installation

The following command should return the installed version:

```shell
mvn --version
```

## Getting started

## Editor integration

[Official-documentation][maven-ide]

The following IDE offer support for Maven:

- **Eclipse**: Requires [the M2Eclipse plugin][m2eclipse].
- **IntelliJ IDEA**: Native support.
- **Netbeans**: Native support.

[maven]: https://maven.apache.org/index.html
[maven-download]: https://maven.apache.org/download.cgi
[maven-ide]: https://maven.apache.org/ide.html
[m2eclipse]: http://www.eclipse.org/m2e/
