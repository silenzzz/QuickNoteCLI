#!/bin/sh
$dir

for entry in "$HOME"/*
do
  if [ "$entry" = "QNC*" ]
  then
    dir=$entry
  fi
done

java -jar "$dir" "$@"