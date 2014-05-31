cd
cd udgo/sampleGen
javac Position.java && java Position 8 6 10
javac CSCgen.java && java CSCgen 8 6 10
cp 8-6-10.csc ../../contiki/tools/cooja/
cp 8-6-10.sensor ../sampleAnalize/
cd ../../contiki/tools/cooja
ant run_nogui -Dargs=/home/mfer/contiki/tools/cooja/8-6-10.csc &
