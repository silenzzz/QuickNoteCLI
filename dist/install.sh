dir="$HOME/DeMmAgeSoft"

if [ ! -d "$dir" ]; then
  echo "Creating directories..."
  mkdir "$dir"
fi

echo "Copying files..."
find .. -name "QNC*.jar" -exec cp {} "$dir" \;
cp "./util/QNC.sh" "$dir/QNC.sh"

if [ ! "$PATH" =~ $dir ]; then
  echo Adding to PATH...
  PATH=$PATH:~$dir
fi

echo "INSTALLATION SUCCESSFUL"