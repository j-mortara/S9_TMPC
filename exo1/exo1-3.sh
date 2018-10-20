#!/bin/bash

make clean
for i in {1..100}
do
	make PADDING=$i padding
	t=$(echo $(TIMEFORMAT='%lR'; time (./exo1-3) 2>&1 1>/dev/null) | sed -E 's/.*m(.*)s/\1/')
	echo $t
	echo "$i;$t" >> res.csv	
done