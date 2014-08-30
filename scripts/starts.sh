#!/bin/bash
#parameter 1 dirname
#parameter 2 g
#parameter 3 mu
#parameter 4 eps
if [ $# -eq 4 ]
  then
    cd ../src/sample/generate/
    javac Position.java
    java Position $2 $3 $4
    javac CSCgen.java
    java CSCgen $2 $3 $4
    cp $2-$3-$4.csc  ../../../build/$1/contiki/tools/cooja/
    cp $2-$3-$4.sensor ../analize/
    cd ../../../build/$1/contiki/tools/cooja/
    var=$(pwd)
    ant run_nogui -Dargs=$var/$2-$3-$4.csc > /dev/null 2>&1 &
  else
    echo "No arguments supplied: usage: ./setup-contiki.sh dirname g mu eps"
fi
