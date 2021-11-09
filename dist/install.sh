#!/bin/sh

dir="/usr/local/bin/DeMmAgeSoftInc"

if [ ! -d "$dir" ]; then
  echo "Creating directories..."
  mkdir "$dir"
fi

echo "Copying files..."
find . -name "QNC*.jar" -exec sudo cp {} "/usr/local/bin/DeMmAgeSoftInc" \;
sudo cp "./util/QNC.sh" "/usr/local/bin/DeMmAgeSoftInc/QNC.sh"
alias qnc="java -jar /usr/local/bin/DeMmAgeSoftInc/QNC-0.1.0.jar"

echo "INSTALLATION SUCCESSFUL"