#!/bin/bash

echo "Cleaning build..."
rm -rf bin
mkdir -p bin

echo "Compiling Java files..."
javac -d bin \
    src/clinic/*.java \
    src/clinic/adt/*.java \
    src/clinic/entity/*.java \
    src/clinic/control/*.java \
    src/clinic/boundary/*.java \
    src/clinic/util/*.java

if [ $? -ne 0 ]; then
    echo "Compilation failed."
    exit 1
fi

echo "Running application..."
java -cp bin clinic.Main
