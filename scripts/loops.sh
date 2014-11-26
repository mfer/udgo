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
		(./starts.sh $g $mu ${eps[$mu]}; wait)
		echo 'g='$g'_mu='$mu
	done
done
