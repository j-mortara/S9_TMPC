package src;

class Transaction {
    public static int commitSuccess;
    public static int commitAbort;
    
    private ValueSet writeSet;
    private ValueSet readSet;
    private int clock;
    
    private Transaction() {
        int memory_size = Memory.memory.values.length;
        this.writeSet = new ValueSet(memory_size);
        this.readSet = new ValueSet(memory_size);
    }
    
    public static ThreadLocal<Transaction> tx = new ThreadLocal<Transaction>() {
        protected synchronized Transaction initialValue() {
            return new Transaction();
        }
    };
    
    public void abort() throws TransactionAbort {
        commitAbort++;
        throw new TransactionAbort();
    }
    
    public void begin() {
        this.clock = Memory.memory.clock;
    }
    
    public int read(int idx) throws TransactionAbort {
        Value copy = writeSet.get(idx);
    
        if (copy == null) {
            System.out.println("première lecture");
            Value tmp = Memory.memory.values[idx];
        
            readSet.hash(idx, tmp);
        
            if (tmp.counter < clock) {
                return tmp.value;
            } else {
                System.out.println("abort déclanché par read");
                abort();
                return 0;
            }
        } else {
            return copy.value;
        }
    }
    
    public void write(int idx, int value) throws TransactionAbort {
        Value copy = writeSet.get(idx);
        
        if (copy == null) {
            System.out.println("première écriture");
            Value tmp = Memory.memory.values[idx];
            if (tmp.counter < clock) {
                writeSet.hash(idx, copy = new Value(tmp.value, tmp.counter));
            } else {
                System.out.println("abort déclanché par write");
                abort();
            }
        }
        copy.value = value;
    }
    
    public void commit() throws TransactionAbort {
        synchronized (Memory.memory) {
            System.out.println("clock: " + clock);
            if (!readSet.checkRead(clock)) {
                System.out.println("abort: read set");
                readSet.clear();
                writeSet.clear();
                abort();
            }
            if (!writeSet.checkWrite(clock)) {
                System.out.println("abort: write set");
                writeSet.clear();
                abort();
            }
            System.out.println("commit");
            Memory.memory.clock++;
            commitSuccess++;
        }
    }
}
