public class Main extends Thread {

    public int delay(int n) {
        try {
            if(n == 0)
                n = 16;
            Thread.sleep(1 + (int)(n * Math.random()));
        } catch(InterruptedException e) {}

        return n < 512 ? n << 1 : n;
    }

    public void test() {
        int n = 0;
        try {
            Transaction transaction = Transaction.Transaction.get();

            transaction.begin();

            int val = transaction.read(0);
            transaction.write(0, val + 1);

            transaction.commit();

        } catch(TransactionAbort abort) {
            n = delay(n);
            test();
        }
    }

    public synchronized void testSync() {
        Memory.memory.values[0].value++;
    }

    public void run() {
        for(int i=0; i<10000; i++)
            test();
    }

    public static void main(String args[]) throws Exception {
        int n = 99;
        Thread threads[] = new Thread[n];

        for(int i=0; i<n; i++)
            (threads[i] = new Main()).start();

        (new Main()).run();

        for(int i=0; i<n; i++)
            threads[i].join();

        System.out.println("--> " + Memory.memory.values[0].value + " ("
                           + Transaction.commitSuccess + ", "
                           + Transaction.commitAbort + ")");
    }
}
