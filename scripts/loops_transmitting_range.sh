#!/bin/bash
old=$2
new=$3

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
    dirname="g="$g"_mu="$mu"_eps="${eps[$mu]}

    filename="../build/"$dirname"/contiki/tools/cooja/apps/udgo/java/org/contikios/udgo/UDGO.java"
    echo $filename
    find $filename -type f -print0 | xargs -0 sed -i 's/setTxRange('$old')/setTxRange('$new')/g'

    filename="../build/"$dirname"/contiki/tools/cooja/apps/udgo/java/org/contikios/udgo/ChannelModel.java"
    echo $filename
    find $filename -type f -print0 | xargs -0 sed -i 's/source.distance(dest) < '$old'/source.distance(dest) < '$new'/g'    
  done
done




