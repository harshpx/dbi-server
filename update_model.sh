#!/bin/bash
set -euo pipefail

# --- Configurable Variables ---
MODEL_URL="$1"   # Pass the model URL as the first argument

# --- Cleanup ---
echo "Cleaning up old build and models..."
rm -rf ./target ./app.jar
rm -rf ./models/*

# --- Download Model ---
echo "Downloading model from: $MODEL_URL"
curl -L "$MODEL_URL" -o ./models/dbi_model.zip

# --- Extract Model ---
echo "Unzipping model..."
unzip -o ./models/dbi_model.zip -d ./models/dbi_model

echo "✅ Done! Model is available at ./models/dbi_model"
