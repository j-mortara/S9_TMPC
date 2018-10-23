#include<stdio.h>
#include<stdlib.h>
#include<pthread.h>
#include <sys/time.h>

static __thread unsigned long x, y, z;

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
	y = time.tv_usec * (i+1);
	z = time.tv_usec + (i+1);
	for(int j = 0 ; j < (100000000) ; j++){
		results[i] += (xorshf96()%2);
	}
}

int main() {
	pthread_t threads[4];
	int nbs[4];
	for (int i=0 ; i < 4 ; i++){
		nbs[i] = i;
		pthread_create(threads+i, NULL, f, nbs+i);
	}
	for (int i=0 ; i < 4 ; i++){
		pthread_join(threads[i], NULL);
	}
	printf("[%lu, %lu, %lu, %lu]\n", results[0], results[1], results[2], results[3]);
}