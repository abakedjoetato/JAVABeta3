#!/bin/bash

# Display Java version
echo "Checking for Java installation..."
java -version

# Create environment variables file for Maven
echo "Setting up environment for Maven build..."

# Compile the project
echo "Building the project with mvn..."
mvn clean package -DskipTests

# Check if build was successful
if [ $? -ne 0 ]; then
    echo "Maven build failed. Checking for dependency issues..."
    mvn dependency:tree
    echo "Attempting to resolve dependencies..."
    mvn dependency:resolve
    echo "Retrying build..."
    mvn clean package -DskipTests
    
    if [ $? -ne 0 ]; then
        echo "Build failed again. Exiting."
        exit 1
    fi
fi

echo "Build successful. Starting the Deadside Discord Bot..."
# Run the application with explicit classpath
java -cp "target/classes:target/deadside-discord-bot-1.0-SNAPSHOT.jar:target/dependency/*" com.deadside.bot.Main