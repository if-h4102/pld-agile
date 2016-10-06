# Eclipse

[Eclipse](https://eclipse.org) is an open source IDE for Java's technologies.

## Installation

### Ubuntu

To install the last version of eclipse on Ubuntu and derivate, you can use ubuntu-make tool:

        umake ide eclipse

You may need to install ubuntu-make first
        
        sudo apt-get install ubuntu-make


### Windows

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
