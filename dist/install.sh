#!/bin/sh

dir="$HOME/DeMmAgeSoft"

if [ ! -d "$dir" ]
then
  mkdir "$dir"
fi

cp "../target/QNC*.jar" "$dir/QNC*.jar"
cp "./util/QNC.sh" "$dir/QNC.sh"

PATH=$PATH:~$dir

echo "Installation complete"