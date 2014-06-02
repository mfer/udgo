#!/bin/bash
#parameter 1 g
#parameter 2 mu
#parameter 3 eps

cd
cd udgo/sampleGen
javac Position.java
java Position $1 $2 $3
javac CSCgen.java
java CSCgen $1 $2 $3
cp $1-$2-$3.csc ../../contiki/tools/cooja/
cp $1-$2-$3.sensor ../sampleAnalize/
cd ../../contiki/tools/cooja
ant run_nogui -Dargs=/home/mfer/contiki/tools/cooja/$1-$2-$3.csc &
