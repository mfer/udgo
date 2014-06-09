#!/bin/bash
phymem=$(awk '/MemTotal/{print $2}' /proc/meminfo)
echo $((($phymem/8000)*5))