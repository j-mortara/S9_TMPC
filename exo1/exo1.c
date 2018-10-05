#include<stdio.h>
#include<stdlib.h>
#include<pthread.h>
#include <sys/time.h>

static unsigned long x, y=362436069, z=521288629;

unsigned long results[4];

unsigned long xorshf96(void) {
    unsigned long t;

    x ^= x << 16;
    x ^= x >> 5;
    x ^= x << 1;

    t = x;
    x = y;
    y = z;
    z = t ^ x ^ y;

    return z;
}

void* f(void* input) {
	int i = *((int *) input);
	struct timeval time;
	gettimeofday(&time, NULL);
	printf("%ld, %d\n", time.tv_usec, i);
}

int main() {
	pthread_t threads[4];
	int* nbs[4];
	for (int i=0 ; i < 4 ; i++){
		nbs[i] = malloc(sizeof(int));
		*nbs[i] = i;
		pthread_create(threads+i, NULL, f, nbs[i]);
	}
	for (int i=0 ; i < 4 ; i++){
		pthread_join(threads[i], NULL);
	}
	free(&id);
}