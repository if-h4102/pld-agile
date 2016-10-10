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

### Plugin IntelliJ Idea

Open the plugin repositories: **File | Settings.. | Plugins | Browse
Repositories...**.
 
Install `PlantUML Integration` and restart your IDE.


[plantuml]: http://plantuml.com/
[plantuml-download]: http://plantuml.com/download
[plantuml-cli]: http://plantuml.com/command-line
[plantuml-running]: http://plantuml.com/running
[planttext]: http://www.planttext.com/planttext

