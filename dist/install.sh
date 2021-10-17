#!/bin/sh

dir="$HOME/DeMmAgeSoft"

if [ ! -d "$dir" ]; then
  echo "Creating directories..."
  mkdir "$dir"
fi

echo "Copying files..."
find . -name "QNC*.jar" -exec cp {} "$dir" \;
find .. -name "QNC*.jar" -exec cp {} "$dir" \;
cp "./util/QNC.sh" "$dir/QNC.sh"

case ":$PATH:" in
  *:$dir*) echo Already in PATH;;
  *) PATH=$PATH:$dir;;
esac

echo "INSTALLATION SUCCESSFUL"