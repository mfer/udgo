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
	for mu in $2;
	do
		tail -n 20 "../build/g="$g"_mu="$mu"_eps="${eps[$mu]}"/contiki/tools/cooja/build/COOJA.log"
		tail -f "../build/g="$g"_mu="$mu"_eps="${eps[$mu]}"/contiki/tools/cooja/build/COOJA.testlog"
	done
done
