@echo off
echo Cleaning build...
if exist bin rmdir /s /q bin
mkdir bin

echo Compiling Java files...
javac -d bin ^
    src/clinic/*.java ^
    src/clinic/adt/*.java ^
    src/clinic/entity/*.java ^
    src/clinic/control/*.java ^
    src/clinic/boundary/*.java ^
    src/clinic/util/*.java

if %ERRORLEVEL% NEQ 0 (
    echo Compilation failed.
    pause
    exit /b
)

echo Running application...
java -cp bin clinic.Main

pause
