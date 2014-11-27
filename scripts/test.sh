#!/bin/bash
phymem=$(awk '/MemTotal/{print $2}' /proc/meminfo)
echo $((($phymem/8000)*5))

g=3
mu=4
eps=24
dirname="g="$g"_mu="$mu"_eps="$eps

filename="../build/"$dirname"/contiki/tools/cooja/apps/udgo/java/org/contikios/udgo/UDGO.java"
echo $filename
find $filename -type f -print0 | xargs -0 sed -i 's/setTxRange(100)/setTxRange(20)/g'

filename="../build/"$dirname"/contiki/tools/cooja/apps/udgo/java/org/contikios/udgo/ChannelModel.java"
echo $filename
find $filename -type f -print0 | xargs -0 sed -i 's/source.distance(dest) < 100.0/source.distance(dest) < 20.0/g'

