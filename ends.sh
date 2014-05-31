cp build/COOJA.testlog ../../../udgo/sampleAnalize/2-8-10.testlog
cd ../../../udgo/sampleAnalize
tail -n +2 2-8-10.testlog | head -n -3 > 2-8-10.testlog.new && mv 2-8-10.testlog.new 2-8-10.testlog
javac CTRgen.java && java CTRgen 2 8 10
mv 2-8-10.ctr CRT/
cd CRT
javac CRT.java && java CRT 2-8-10.ctr >> 2-8-10.ctrs
