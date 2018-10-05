#include<stdio.h>
#include<stdlib.h>
#include<pthread.h>
#include<sched.h>
#define _GNU_SOURCE

void change_affinity(int cpu, pthread_t thread) {
    cpu_set_t mask; 
    __CPU_ZERO( &mask ); 
    __CPU_SET( cpu, &mask ); 
    pthread_setaffinity_np(thread, sizeof(mask), &mask);   
}

void* mange_cpu() {
    /*
     On mange du CPU en mettant du code qui ne devrait pas
     etre supprimé par le compilateur
     */
    int i=1;
   	while (1) {
    	i=i*2+1;
    	if (i%2==0)
      		break;
  	} 
}

int main() {
	pthread_t thread1, thread2;
	pthread_create( &thread1, NULL, mange_cpu, NULL);
	
}