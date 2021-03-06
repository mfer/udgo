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
\cp ../udgo/contiki/examples/rime/example-neighbors.c examples/rime/

mkdir tools/cooja/apps/udgo
cd tools/cooja/apps/udgo
ln -s ../../../../../udgo/contiki/tools/cooja/apps/udgo/* .


cd ../../../../tools/cooja/
# tail -n +2 2-16-20.testlog | head -n -3 > 2-16-20.testlog.new && mv 2-16-20.testlog.new 2-16-20.testlog
# mv build/COOJA.testlog ~/udgo/3-5-10.testlog
# 
