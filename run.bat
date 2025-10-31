@echo off
echo [INFO] Compiling project...

REM Create a bin directory if it doesn't exist
if not exist "bin" mkdir bin

REM Compile all .java files into the bin directory
REM Generate a list of all .java source files
dir /s /b src\*.java > sources.txt

REM Compile all .java files from the list into the bin directory
javac -d bin -cp "lib/mysql-connector-j-8.3.0.jar" @sources.txt

REM Clean up the temporary sources file
del sources.txt

echo [INFO] Starting Personal Finance Manager...

REM Run the application
java -cp "bin;lib/mysql-connector-j-8.3.0.jar" personalfinancemanager.app.FinanceApp