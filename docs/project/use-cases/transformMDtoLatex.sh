#!/bin/bash

mdFiles=$(echo *.md)

for file in $mdFiles; do
	kramdown -o latex $file >${file%%.*}.tex
done
