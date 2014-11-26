#!/bin/bash
eps[4]="48"
eps[6]="42"
eps[8]="38"
eps[10]="34"
eps[12]="30"
eps[14]="28"
eps[16]="26"
eps[18]="24"

for g in $1;
do
	for mu in 4 6 8 10 12 14 16 18;
	do
		./ends.sh $g $mu ${eps[$mu]} && tail -n 4 ../src/sample/analize/CTR/$g-$mu-${eps[$mu]}.ctrs
		tail -n 8 "../build/g="$g"_mu="$mu"_eps="${eps[$mu]}"/contiki/tools/cooja/build/COOJA.log"
	done
done
