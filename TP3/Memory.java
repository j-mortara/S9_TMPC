public class Memory {
    Value values[];
    int clock;
    
    public Memory(int memory_size) {
        this.values = new Value[memory_size];
        for (int i = 0; i < memory_size; i++)
            this.values[i] = new Value();
        clock = 1;
    }
    
    public static final Memory memory = new Memory(1024);
}