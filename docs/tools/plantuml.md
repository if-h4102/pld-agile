# PlantUML

[_PlantUML_][plantuml] is a tool to generate UML diagrams from text definitions.

## Dependencies

- Graphviz

## Installation

1. From [the download page][plantuml-download], download the
   `PlantUML compiled Jar`.

2. Add it as a command-line program.

   - Windows
     
     1. Save `plantuml.jar` in `C:/Program Files/PlantUML/plantuml.jar`
     
     2. Create a file `plantuml.cmd` in the same directory with this content:
     
        ```batch
        @echo off
        java -jar "%~dp0plantuml.jar" -charset UTF-8 %*
        ```
     
     3. Add the directory to your `PATH` environment variable.

   - Linux: Check `alias` and send a PR to add instructions here.

3. Make sure that the `GRAPHVIZ_DOT` environment variables has the full path
   to the [Graphviz](./graphviz.md) `dot` executable.

## Usage

[Documentation][plantuml-cli]

Generate SVG output for each `puml` file:

```shell
plantuml -tsvg docs/**/*.puml
```

## Editor

### Planttext

[Planttext][planttext] is a web-application.

### Plugin for IntelliJ Idea

Open the plugin repositories: **File | Settings.. | Plugins | Browse
Repositories...**.
 
Install `PlantUML Integration` and restart your IDE.

### Plugin for Eclipse

[Official documentation][plantuml-eclipse]

1. Open the **Install** menu with **Help | Install New Sofwtare...**

2. Set `Work with` to `http://plantuml.sourceforge.net/updatesitejuno/`, then
   add it (with the **Add** button). You can use `PlantUML` as the Name for the
   addition.

3. Select the `PlantUML` plugin in the list.

4. **Next** to let Eclipse download some data.

5. **Next** to advance in the installation...

6. Accept the license and confirm with **Finish**.

7. During the installation, a warning (about _unsigned content_) might appear.
   You can simply acknowledge it with **OK**.
   
8. Restart Eclipse.

9. **Window | Show View | Other...** then choose `PlantUML/PlantUML`.

[plantuml]: http://plantuml.com/
[plantuml-download]: http://plantuml.com/download
[plantuml-eclipse]: http://plantuml.com/eclipse
[plantuml-cli]: http://plantuml.com/command-line
[plantuml-running]: http://plantuml.com/running
[planttext]: http://www.planttext.com/planttext

