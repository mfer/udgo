#!/bin/bash
dirname="test"

#this selection clause handle the call at udgo/src/client
if [ "${PWD##*/}" == "client" ]; then
    cd ../../scripts
fi

#First getting the contiki-cooja stuff
if [ ! -d "../build/localcopy" ]; then
    mkdir ../build/
    mkdir ../build/localcopy
    cd ../build/localcopy
    git clone https://github.com/contiki-os/contiki.git
    cd contiki
    git submodule update --init     
    cd ../../../scripts
fi
if [ ! -d "../build/$dirname" ]; then
    mkdir ../build/$dirname
    \cp -R ../build/localcopy/* ../build/$dirname
    cd ../build/$dirname/contiki
    \cp ../../../src/contiki/tools/cooja/build.xml tools/cooja/
    \cp ../../../src/contiki/tools/cooja/config/cooja.html tools/cooja/config/
    \cp ../../../src/contiki/tools/cooja/config/cooja_applet.config tools/cooja/config/
    \cp ../../../src/contiki/tools/cooja/config/external_tools.config tools/cooja/config/
    \cp ../../../src/contiki/tools/cooja/java/org/contikios/cooja/radiomediums/UDGM.java tools/cooja/java/org/contikios/cooja/radiomediums/
    \cp ../../../src/contiki/examples/rime/example-neighbors.c examples/rime/
    \cp ../../../src/contiki/tools/cooja/java/org/contikios/cooja/plugins/VariableWatcher.java  tools/cooja/java/org/contikios/cooja/plugins/
    if [ ! -d "./tools/cooja/apps/udgo" ]; then
        mkdir tools/cooja/apps/udgo
        cd tools/cooja/apps/udgo
        ln -s ../../../../../../../src/contiki/tools/cooja/apps/udgo/* .
        cd ../../../../
    fi
    cd ../../../scripts
fi

cd ../src/sample/test/generate/
javac Position.java
java -cp . Position
javac CSCgen.java
java -cp . CSCgen
cp test.csc  ../../../../build/$dirname/contiki/tools/cooja/
cp test.sensor ../analize/
cd ../../../../build/$dirname/contiki/tools/cooja/
ant run_nogui -Dargs=$(pwd)/test.csc > /dev/null 2>&1 &
