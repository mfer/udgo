#!/bin/bash
dirname="test"

#this selection caluse handle the call at udgo/src/client
if [ "${PWD##*/}" == "client" ]; then
    cd ../../scripts
fi
cp ../build/$dirname/contiki/tools/cooja/build/COOJA.testlog ../src/sample/test/analize/test.testlog
cd ../src/sample/test/analize
tail -n +10 test.testlog | head -n -3 > test.testlog.new
mv test.testlog.new test.testlog
javac -cp . CTRgen.java
java -cp . CTRgen
mv test.ctr CTR/
cd CTR
javac -cp . CTR.java
java  -cp . CTR test.ctr >> test.ctrs



