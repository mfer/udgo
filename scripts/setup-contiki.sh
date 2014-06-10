#!/bin/bash
#parameter 1 dir_name

if [ $# -eq 1 ]
  then
	  mkdir ../build/
	  mkdir ../build/$1
	  cd ../build/$1
	  git clone https://github.com/contiki-os/contiki.git
	  cd contiki
	  git submodule update --init

	  \cp ../../../src/contiki/tools/cooja/build.xml tools/cooja/
	  \cp ../../../src/contiki/tools/cooja/config/cooja.html tools/cooja/config/
	  \cp ../../../src/contiki/tools/cooja/config/cooja_applet.config tools/cooja/config/
	  \cp ../../../src/contiki/tools/cooja/config/external_tools.config tools/cooja/config/
	  \cp ../../../src/contiki/tools/cooja/java/org/contikios/cooja/radiomediums/UDGM.java tools/cooja/java/org/contikios/cooja/radiomediums/
	  \cp ../../../src/contiki/examples/rime/example-neighbors.c examples/rime/

	  mkdir tools/cooja/apps/udgo
	  cd tools/cooja/apps/udgo
	  ln -s ../../../../../../../src/contiki/tools/cooja/apps/udgo/* .
  else
    echo "No arguments supplied: usage: ./setup-contiki.sh dir_name"
fi


