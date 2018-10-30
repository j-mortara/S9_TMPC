# TMPC - Rendu TP2

_Thomas CANAVA - Johann MORTARA_

## TD2

### Exercice 1

#### 1.1
Avec 4 threads sur 4 cœurs : environ 5000 ms

```bash
$ ./bench.sh stack_mutex.c 
[INFO] Usage: bench.sh SOURCE_FILE N_THREADS USE_CPUS
4 4
time: 5096
time: 4908
time: 4939
```

Cette tendance se confirme sur plusieurs essais successifs.

#### 1.2

Avec 4 threads sur 4 cœurs : environ 2000 ms

```bash
$ ./bench.sh stack_spinlock.c 4 4
[INFO] Usage: bench.sh SOURCE_FILE N_THREADS USE_CPUS
4 4
time: 1939
time: 1957
time: 1938
```

Cette tendance se confirme sur plusieurs essais successifs.

#### 1.3

Le mutex avec 1000 threads sur 1 cœur :

```bash
$ ./bench.sh stack_mutex.c 1000 1
[INFO] Usage: bench.sh SOURCE_FILE N_THREADS USE_CPUS
1000 1
time: 3321
time: 3300
time: 3370
```


Le spinlock avec 1000 threads sur 1 cœur :
```bash
$ ./bench.sh stack_spinlock.c 1000 1
[INFO] Usage: bench.sh SOURCE_FILE N_THREADS USE_CPUS
1000 1
time: 2995
time: 2904
time: 2980
```

Ce ne sont pas les résultats auxquels on pouvait s'attendre, en effet les mutex sont censés être plus rapide que l'attente active sur un seul cœur.
