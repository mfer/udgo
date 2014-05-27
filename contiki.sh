#!/bin/bash
#after --> git clone https://github.com/mfer/udgo.git
#cd udgo
#./contiki.sh
cd ../
git clone https://github.com/contiki-os/contiki.git
cd contiki
git submodule update --init

\cp ../udgo/contiki/tools/cooja/build.xml tools/cooja/
\cp ../udgo/contiki/tools/cooja/config/cooja.html tools/cooja/config/
\cp ../udgo/contiki/tools/cooja/config/cooja_applet.config tools/cooja/config/
\cp ../udgo/contiki/tools/cooja/config/external_tools.config tools/cooja/config/
\cp ../udgo/contiki/tools/cooja/java/org/contikios/cooja/radiomediums/UDGM.java tools/cooja/java/org/contikios/cooja/radiomediums/
\cp ../udgo/contiki/examples/rime/examples-neighbors.c examples/rime/

mkdir tools/cooja/apps/udgo
cd tools/cooja/apps/udgo
ln -s ../../../../../udgo/contiki/tools/cooja/apps/udgo/* .


cd ../../../../tools/cooja/
ant run_bigmem
