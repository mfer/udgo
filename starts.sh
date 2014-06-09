cd
cd udgo/sampleGen
javac Position.java && java Position 6 18 10
javac CSCgen.java && java CSCgen 6 18 10
cp 6-18-10.csc ../../contiki/tools/cooja/
cp 6-18-10.sensor ../sampleAnalize/
cd ../../contiki/tools/cooja
ant run_nogui -Dargs=/home/mfer/contiki/tools/cooja/6-18-10.csc &
