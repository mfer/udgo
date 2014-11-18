#!/bin/bash
phymem=$(awk '/MemTotal/{print $2}' /proc/meminfo)
echo $((($phymem/8000)*5))

g=8
mu=6
eps=27
dirname="g="$g"_mu="$mu"_eps="$eps
filename=../build/$dirname"/contiki/tools/cooja/apps/udgo/java/org/contikios/udgo/UDGO.java"
echo $filename
find $filename -type f -print0 | xargs -0 sed -i 's/setTxRange(20)/setTxRange(100)/g'