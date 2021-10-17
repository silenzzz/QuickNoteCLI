#!/bin/sh

dir="$HOME/DeMmAgeSoft"

if [ ! -d "$dir" ]; then
  echo "Creating directories..."
  mkdir "$dir"
fi

echo "Copying files..."
find . -name "QNC*.jar" -exec cp {} "/usr/local/bin" \;
find .. -name "QNC*.jar" -exec cp {} "/usr/local/bin" \;
cp "./util/QNC.sh" "/usr/local/bin/QNC.sh"

#export DEMMAGESOFT_HOME=$dir
#case ":$PATH:" in
#  *:$dir*) echo Already in PATH;;
#  *) PATH=$PATH:$DEMMAGESOFT_HOME;;
#esac

echo "INSTALLATION SUCCESSFUL"