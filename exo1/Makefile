all:
	gcc -o exo1 -lpthread exo1.c

opti:
	gcc -o exo1 -O$(NB) -lpthread exo1.c

padding:
	gcc -DPADDING=$(PADDING) -o exo1-3 -lpthread exo1-3.c

clean:
	rm exo1
	rm exo1-3