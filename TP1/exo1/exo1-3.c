#include<stdio.h>
#include<stdlib.h>
#include<pthread.h>
#include <sys/time.h>

static __thread unsigned long x, y, z;

struct results {
    unsigned long result0;
    char padding1[PADDING];
    unsigned long result1;
    char padding2[PADDING];
    unsigned long result2;
    char padding3[PADDING];
    unsigned long result3;
} __attribute__ ((packed)) results __attribute__ ((aligned (64)));;

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
	unsigned long *result;
	switch(i) {
		case 0:
			result = &(results.result0);
			break;
		case 1:
			result = &(results.result1);
			break;
		case 2:
			result = &(results.result2);
			break;
		case 3:
			result = &(results.result3);
			break;
	}
	for(int j = 0 ; j < (100000000) ; j++){
		*result += (xorshf96()%2);
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
	printf("[%lu, %lu, %lu, %lu]\n", results.result0, results.result1, results.result2, results.result3);
}