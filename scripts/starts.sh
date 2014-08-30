#!/bin/bash
#parameter 1 dirname
#parameter 2 g
#parameter 3 mu
#parameter 4 eps
if [ $# -eq 4 ]
  then
    if [ ! -d "../build/$1" ]; then 
      ./setup-contiki.sh $1
    fi
    cd ../src/sample/generate/
    javac Position.java
    java -cp . Position $2 $3 $4
    javac CSCgen.java
    java -cp . CSCgen $2 $3 $4
    cp $2-$3-$4.csc  ../../../build/$1/contiki/tools/cooja/
    cp $2-$3-$4.sensor ../analize/
    cd ../../../build/$1/contiki/tools/cooja/
    var=$(pwd)
    ant run_nogui -Dargs=$var/$2-$3-$4.csc > /dev/null 2>&1 &
  else
    echo "No arguments supplied: usage: ./starts.sh dirname g mu eps"
fi
