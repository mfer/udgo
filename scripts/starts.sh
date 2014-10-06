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
    ./setup-contiki.sh $g $mu $eps
    cd ../src/sample/generate/
    javac Position.java
    java -cp . Position $g $mu $eps
    javac CSCgen.java
    java -cp . CSCgen $g $mu $eps
    cp $g-$mu-$eps.csc  ../../../build/$dirname/contiki/tools/cooja/
    cp ../../contiki/tools/cooja/java/org/contikios/cooja/plugins/VariableWatcher.java  ../../../build/$dirname/contiki/tools/cooja/java/org/contikios/cooja/plugins/
    cp $g-$mu-$eps.sensor ../analize/
    cd ../../../build/$dirname/contiki/tools/cooja/
    var=$(pwd)
    ant run_nogui -Dargs=$var/$g-$mu-$eps.csc > /dev/null 2>&1 &
  else
    echo "No arguments supplied: usage: ./starts.sh g mu eps"
fi
