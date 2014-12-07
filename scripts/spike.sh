#!/bin/bash
phymem=$(awk '/MemTotal/{print $2}' /proc/meminfo)
echo $((($phymem/8000)*5))

filename=" ../src/sample/test/generate/template_3"
echo $filename
find $filename -type f -print0 | xargs -0 sed -i 's/TIMEOUT(5000)/TIMEOUT(50000)/g'
