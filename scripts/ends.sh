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
    cp ../build/$dirname/contiki/tools/cooja/build/COOJA.testlog ../src/sample/analize/$g-$mu-$eps.testlog
    cd ../src/sample/analize
    tail -n +$g $g-$mu-$eps.testlog | head -n -3 > $g-$mu-$eps.testlog.new
    mv $g-$mu-$eps.testlog.new $g-$mu-$eps.testlog
    javac -cp . CTRgen.java
    java -cp . CTRgen $g $mu $eps
    mv $g-$mu-$eps.ctr CTR/
    cd CTR
    javac -cp . CTR.java
    java  -cp . CTR $g-$mu-$eps.ctr >> $g-$mu-$eps.ctrs
  else
    echo "No arguments supplied: usage: ./ends.sh g mu eps"
fi
