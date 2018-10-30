package src;

public class Value {
    int value;
    int counter;
    
    Value(int value, int counter) {
        this.value = value;
        this.counter = counter;
    }
    
    Value() {
        this.value = 0;
        this.counter = 0;
    }
    
    public String toString() {
        return value + "(" + counter + ")";
    }
}