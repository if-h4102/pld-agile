#!/bin/bash

umls=$(find ../../../../src -name "*.png")

latexFile="uml.tex"

echo "%generated tex file" >$latexFile

for uml in $umls; do
	cp $uml ${uml##*/}
	
	umlName=${uml##*/}
	umlName=${umlName%.*}
	
	echo "\begin{figure}[h!]
    		\begin{center}
      			\includegraphics[width=\linewidth,height=.7\paperheight,keepaspectratio]{retrogenerated_uml/${uml##*/}}
      			\caption{Diagramme de classe du package ${umlName##*/}}
    		\end{center}
  	\end{figure}">>$latexFile
done
