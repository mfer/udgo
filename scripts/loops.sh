#!/bin/bash
for g in 2;
do
	for mu in 6;
	do
		(./starts.sh $g $mu 10; wait)
		PID1=$!
		echo $PID1
		ps --ppid $PID1
		echo 'g='$g'_mu='$mu
	done
	echo "bla"
done
