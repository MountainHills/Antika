# Antika
A simple command line interface (CLI) application that prepares the workflow of the user built with Java.

Antika is a 'scratch your own itch' project where its main goals is to solve a personal problem of manually opening applications related to the task the professional uses.

So, instead of manually opening the applications one by one, it can be completed in one single command.

See the following example:
```bash
antika --mode writing
```

It would open all the applications related to writing workflow as configured by the professional in the workflow file. It can be a Google Doc to write, Wikipedia to search for references, or a simple notepad to store temporary notes.

## Features
- Generates `workflow.csv` upon initialization to allow the user to configure the tools needed to be open for a given workflow.
- Opens all the tools (websites and desktop applications) upon command.

## Prerequisites
- Java 21 or higher
- Gradle

## Installation
1. Clone the repository:
```bash
git clone https://github.com/MountainHills/Antika.git
```

2. Navigate to the project directory:
```bash
cd Antika
```

3. Build the project using `gradle` and the `shadowJar` plugin:
```bash
./gradlew clean shadowJar
```

4. Run the JAR:
```bash
java -jar build/libs/Antika-1.0.0-all.jar
```

***Note:*** Create a batch file or shell script for running the application to make it convenient to run.

Batch file:
```bat
@echo off
REM Path to Antika JAR file
set JAR_PATH="%~dp0Antika-1.0.0-all.jar"

REM Call java to run your JAR with passed arguments
java -jar %JAR_PATH% %*
```

Shell script:
```
#!/bin/bash
# Path to Antika JAR file
JAR_PATH="$(dirname "$0")/Antika-1.0.0-all.jar"

# Call java to run your JAR with passed arguments
java -jar "$JAR_PATH" "$@"
```

## Usage
Run the following CLI with the following syntax:
```bash
antika [option]
```
### Options:
```
 -h,--help              List out the options available for Antika
 -i,--init              Create the initial workflow file for Antika
 -m,--mode <workflow>   Select the current workflow mode
```

### Examples:

***IMPORTANT:*** This option is important to create the initial `workflow.csv`. Upon initialization, it would have a pre-generated workflow named `example`.
```bash
antika --init
```

Opens the tools related to given workflow
```bash
antika --mode example
```

## Contributing
Feel free to submit issues or pull requests! Before contributing, please ensure that:
- Your code follows the project’s coding style.
- You write unit tests for new functionality.

## License
This project is licensed under the MIT License - see the [LICENSE](https://github.com/MountainHills/Antika/blob/main/LICENSE) file for details.