# Take Home Test in Java (8, of course)
## Set Up
1. Install requirements:
  1. [Oracle JDK 8](http://www.oracle.com/technetwork/java/javase/downloads/index.html) (or an alternative like [OpenJDK](http://openjdk.java.net/projects/jdk8/))
2. Install Build and Dev tools:

  Yum or APT may work well on linux, but you will need to download these things from their respective websites for Mac OS X unless you want to mess with [Homebrew Versions](https://github.com/Homebrew/homebrew-versions) or [Homebrew Cask](https://github.com/caskroom/homebrew-cask/). They are not in Homebrew by default. [#SorryNotSorry](https://twitter.com/search?q=%23sorrynotsorry).
  * IDE route:
    1. Eclipse (preferably [Spring Tool Suite](http://spring.io/tools)) or another IDE
    2. [Lombok](https://projectlombok.org/download.html) (only needed if you plan to use an IDE)
  * Command line route:
    * [Maven](https://maven.apache.org/download.cgi) (only needed if you plan to build from command line - available in Homebrew!)
3. Import the project into your IDE, or `cd` into the path.
4. ...
5. Profit (or, at least employment).

## Running It
So far, I just run it from the Eclipse run configurations using `CLI.java` as the main class, `-f --path ~/github/take-home-test-python/tests` as `Program arguments`, and `-Dorg.slf4j.simpleLogger.defaultLogLevel=trace` as `VM arguments`.
You can also run it from the command line after building a JAR.

Build it

    cd ~/path/to/pom.xml
    mvn install
Run it with any of the below commands

    java -jar take-home-test-java-0.0.1-SNAPSHOT.jar --help
    java -jar take-home-test-java-0.0.1-SNAPSHOT.jar -f --path ~/path/to/files
    java -jar take-home-test-java-0.0.1-SNAPSHOT.jar --path ~/path/to/files -Dorg.slf4j.simpleLogger.defaultLogLevel=trace

## Current Limitations
1. Doesn't do much yet!
