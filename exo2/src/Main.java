import java.util.Arrays;

public class Main {
    
    public static void main(String[] args) {
        long startTime = System.nanoTime();
        Thread threads[] = new Thread[4];
        for (int i = 0; i < 4; i++) {
            int finalI = i;
            threads[i] = new Thread(() -> new Exercice2(finalI).f());
            threads[i].start();
        }
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(Arrays.toString(Exercice2.infos));
        System.out.println("Total time " + (System.nanoTime() - startTime)/1000000+" ms");
        
    }
}
