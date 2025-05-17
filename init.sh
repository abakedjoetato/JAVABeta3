#!/bin/bash

# STEP 0 - PROJECT INIT script for Deadside Discord Bot
echo "--- DEADSIDE DISCORD BOT INITIALIZATION ---"
echo "Starting initialization process..."

# Check if zip file exists
ZIP_FILES=$(ls *.zip 2>/dev/null)
if [ -z "$ZIP_FILES" ]; then
    echo "ERROR: No zip file found in the current directory."
    echo "Please upload the Deadside bot zip file and try again."
    exit 1
fi

# Use the first zip file found
ZIP_FILE=$(echo $ZIP_FILES | cut -d' ' -f1)
echo "Found zip file: $ZIP_FILE"

# Create a temp directory for extraction
mkdir -p temp_extract
echo "Extracting $ZIP_FILE to temporary directory..."
unzip -q "$ZIP_FILE" -d temp_extract

# Find the main folder in the extracted content
MAIN_FOLDER=$(ls -d temp_extract/*/ 2>/dev/null | head -1)
if [ -z "$MAIN_FOLDER" ]; then
    # Try to use the temp_extract if no subfolder
    MAIN_FOLDER="temp_extract"
fi

echo "Moving contents from $MAIN_FOLDER to project root..."
# Move all files/directories from extraction directory to root
mv $MAIN_FOLDER/* . 2>/dev/null
mv $MAIN_FOLDER/.* . 2>/dev/null 2>/dev/null  # Move hidden files too

# Clean up
echo "Cleaning up temporary files..."
rm -rf temp_extract
rm -f "$ZIP_FILE"  # Remove the zip file after extraction

# Run project scanner
echo "Scanning project structure..."
python3 scan_project.py

# Prepare environment configuration
echo "Preparing environment configuration..."
python3 prepare_env.py

# Make start script executable
chmod +x start_bot.sh

echo "Initialization complete. Run './start_bot.sh' to start the bot."
