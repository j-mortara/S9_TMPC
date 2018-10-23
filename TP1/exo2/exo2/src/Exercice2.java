import static java.lang.Math.abs;

public class Exercice2 {
    static ThreadInfo[] infos = new ThreadInfo[4];
    private long x, y, z;
    private int id;
    
    public Exercice2(int id) {
        this.id = id;
    }
    
    private long xorshf96() {
        long t;
        x ^= x << 16;
        x ^= x >> 5;
        x ^= x << 1;
        
        t = x;
        x = y;
        y = z;
        z = t ^ x ^ y;
        
        return z;
    }
    
    void f() {
        this.y = System.currentTimeMillis() * (id + 1);
        this.z = System.currentTimeMillis() + (id + 1);
        infos[id] = new ThreadInfo();
        for (int j = 0; j < (100000000); j++) {
            infos[id].result += abs(xorshf96() % 2);
            infos[id].progress++;
        }
    }
    
    static class ThreadInfo {
        volatile long progress = 0;
        char[] padding = new char[100];
        long result = 0;
        
        @Override
        public String toString() {
            return "ThreadInfo{" +
                    "progress=" + progress +
                    ", result=" + result +
                    '}';
        }
    }
}
