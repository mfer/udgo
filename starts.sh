#parameter 1 g
#parameter 2 mu
#parameter 3 eps

echo cd
echo cd udgo/sampleGen
echo javac Position.java
echo java Position $1 $2 $3
echo javac CSCgen.java
echo java CSCgen $1 $2 $3
echo cp $1-$2-$3.csc ../../contiki/tools/cooja/
echo cp $1-$2-$3.sensor ../sampleAnalize/
echo cd ../../contiki/tools/cooja
echo ant run_nogui -Dargs=/home/mfer/contiki/tools/cooja/$1-$2-$3.csc &
