#!/bin/bash

# Ensure Java and Maven are installed
echo "Checking Java version..."
java -version

echo "Building project with Maven..."
mvn clean package

echo "Starting Deadside Discord Bot..."
java -jar target/deadside-discord-bot-1.0-SNAPSHOT.jar