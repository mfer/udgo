#!/bin/bash
g=$1
mu=$2
eps=$3
dirname="g="$g"_mu="$mu"_eps="$eps
if [ $# -eq 3 ]
  then
    #this selection caluse handle the call at udgo/src/client
    if [ "${PWD##*/}" == "client" ]; then
        cd ../../scripts
    fi
    rm -rf ../build/$dirname/
  else
    echo "No arguments supplied: usage: ./ends.sh g mu eps"
fi
