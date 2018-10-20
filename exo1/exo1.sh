#!/bin/bash

make clean
echo "No optimisation"
make
time ./exo1
for i in {1..3}
do
	echo "Optimisation $i"
	make NB=$i opti
	time ./exo1
done