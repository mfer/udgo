#!/bin/bash
#parameter 1 g
#parameter 2 mu
#parameter 3 eps

cd
cp contiki/tools/cooja/build/COOJA.testlog udgo/sampleAnalize/$1-$2-$3.testlog
cd udgo/sampleAnalize
tail -n +$1 $1-$2-$3.testlog | head -n -3 > $1-$2-$3.testlog.new
mv $1-$2-$3.testlog.new $1-$2-$3.testlog
javac CTRgen.java
java CTRgen $1 $2 $3
mv $1-$2-$3.ctr CRT/
cd CRT
javac CRT.java
java CRT $1-$2-$3.ctr >> $1-$2-$3.ctrs
