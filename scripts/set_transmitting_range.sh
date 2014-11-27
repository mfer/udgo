#!/bin/bash

$old=$1
$new=$2

filename="../build/"$dirname"/contiki/tools/cooja/apps/udgo/java/org/contikios/udgo/UDGO.java"
echo $filename
find $filename -type f -print0 | xargs -0 sed -i 's/setTxRange($old)/setTxRange($new)/g'

filename="../build/"$dirname"/contiki/tools/cooja/apps/udgo/java/org/contikios/udgo/ChannelModel.java"
echo $filename
find $filename -type f -print0 | xargs -0 sed -i 's/source.distance(dest) < $old/source.distance(dest) < $new/g'

