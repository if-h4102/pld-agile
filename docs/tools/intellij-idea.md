# IntelliJ IDEA

It just works.

_IntelliJ IDEA_ is a smart IDE is an ergonomic IDE that understands most of
your project automatically so you don't lose time in configuration. It has
many well integrated tools (embedded terminal, version control, ...) and offers
powerful refactoring capabilities.

# Installation

Simply download it follow instructions [from the download
page][intellij-download].

There are two editions:

- **Community**: this is a free version with most of the features

- **Ultimate**: normally paid, but you can [apply for free student
  licenses][jetbrains-student]. It includes better support for SQL, web and
  other frameworks.

[jetbrains-student]: https://www.jetbrains.com/student/
[intellij-download]: https://www.jetbrains.com/idea/download/


## Run configuration

### Main

In the **Run/Debug Configurations** menu, add a new `Application`
configuration.

Set `Main class` to `main.Application`.

Add a new configuration to `Before launch`. **Add | Run Maven Goal**, then
check that `Working directory` is set to your project root and set `Command
line` to `process-ressources`. Move it after the `Make` configuration.

### Test

In the **Run/Debug Configurations** menu, add a new `JUnit`
configuration.

Set `Test kind` to `All in directory` and choose the `src/test` directory.
