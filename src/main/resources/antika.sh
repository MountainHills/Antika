#!/bin/bash
# Path to Antika JAR file
JAR_PATH="$(dirname "$0")/Antika-2.1.0-all.jar"

# Call java to run your JAR with passed arguments
java -jar "$JAR_PATH" "$@"