#!/bin/bash
g=$1
mu=$2
eps=$3
dirname="g="$g"_mu="$mu"_eps="$eps
if [ $# -eq 3 ]
	then

	#this selection caluse handle the call at udgo/src/client
	if [ "${PWD##*/}" == "client" ]; then
		cd ../../scripts
	fi

	if [ ! -d "../build/$dirname" ]; then
		echo $dirname
	  	mkdir ../build/
	  	mkdir ../build/$dirname
	fi

  	cd ../build/$dirname
  	git clone https://github.com/contiki-os/contiki.git
  	cd contiki
  	git submodule update --init

	\cp ../../../src/contiki/tools/cooja/build.xml tools/cooja/
	\cp ../../../src/contiki/tools/cooja/config/cooja.html tools/cooja/config/
	\cp ../../../src/contiki/tools/cooja/config/cooja_applet.config tools/cooja/config/
	\cp ../../../src/contiki/tools/cooja/config/external_tools.config tools/cooja/config/
	\cp ../../../src/contiki/tools/cooja/java/org/contikios/cooja/radiomediums/UDGM.java tools/cooja/java/org/contikios/cooja/radiomediums/
	\cp ../../../src/contiki/examples/rime/example-neighbors.c examples/rime/

	if [ ! -d "./tools/cooja/apps/udgo" ]; then
		mkdir tools/cooja/apps/udgo
		cd tools/cooja/apps/udgo
		ln -s ../../../../../../../src/contiki/tools/cooja/apps/udgo/* .
	fi

else
	echo "No arguments supplied: usage: ./setup-contiki.sh g mu eps"
fi


