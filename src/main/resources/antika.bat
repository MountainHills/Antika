@echo off
REM Path to Antika JAR file
set JAR_PATH="%~dp0Antika-2.0.0-all.jar"

REM Call java to run your JAR with passed arguments
java -jar %JAR_PATH% %*