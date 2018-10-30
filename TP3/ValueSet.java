public class ValueSet {
    public Value map[];
    public int elmts[];
    public int top;
    
    public ValueSet(int max) {
        this.map = new Value[max];
        this.elmts = new int[max];
    }
    
    public boolean checkRead(int clock) {
        for (int i = 0; i < top; i++) {
            int idx = elmts[i];
            if (Memory.memory.values[idx].counter >= clock) {
                return false;
            }
            map[idx] = null;
        }
        top = 0;
        return true;
    }
    
    public boolean checkWrite(int clock) {
        for (int i = 0; i < top; i++) {
            int idx = elmts[i];
            if (Memory.memory.values[idx].counter >= clock) {
                return false;
            }
        }
        
        for (int i = 0; i < top; i++) {
            int idx = elmts[i];
            Value tmp = map[idx];
            tmp.counter = Memory.memory.clock;
            map[idx] = null;
            Memory.memory.values[idx] = tmp;
        }
        top = 0;
        return true;
    }
    
    Value get(int idx) {
        return map[idx];
    }
    
    boolean hash(int idx, Value val) {
        if (get(idx) != null) {
            return true;
        }
        elmts[top++] = idx;
        map[idx] = val;
        return false;
    }
    
    void clear() {
        for (int i = 0; i < top; i++)
            map[elmts[i]] = null;
        top = 0;
    }
}