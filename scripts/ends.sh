#!/bin/bash
#parameter 1 dir_name
#parameter 2 g
#parameter 3 mu
#parameter 4 eps

if [ $# -eq 4 ]
  then
    cp ../build/$1/contiki/tools/cooja/build/COOJA.testlog ../src/sample/analize/$2-$3-$4.testlog
    cd ../src/sample/analize
    tail -n +$2 $2-$3-$4.testlog | head -n -3 > $2-$3-$4.testlog.new
    mv $2-$3-$4.testlog.new $2-$3-$4.testlog
    javac CTRgen.java
    java CTRgen $2 $3 $4
    mv $2-$3-$4.ctr CTR/
    cd CTR
    javac CTR.java
    java CTR $2-$3-$4.ctr >> $2-$3-$4.ctrs
  else
    echo "No arguments supplied: usage: ./setup-contiki.sh dir_name g mu eps"
fi
