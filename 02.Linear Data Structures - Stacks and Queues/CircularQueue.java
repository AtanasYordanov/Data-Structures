import java.util.Arrays;

public class CircularQueue<T> {

    private static final int DEFAULT_CAPACITY = 4;
    private T[] array;
    private int size;
    private int capacity;
    private int startIndex;
    private int endIndex;

    public CircularQueue() {
        this(DEFAULT_CAPACITY);
    }

    public CircularQueue(int initialCapacity) {
        this.array = (T[]) new Object[initialCapacity];
        this.capacity = initialCapacity;
        this.size = 0;
        this.startIndex = 0;
        this.endIndex = 0;
    }

    public int size() {
        return this.size;
    }

    private void setSize(int size) {
        this.size = size;
    }

    public void enqueue(T element) {
        if (this.size == this.array.length) {
            this.grow();
        }
        this.array[endIndex] = element;
        this.endIndex = (this.endIndex + 1) % this.array.length;
        this.size++;
    }

    public T dequeue() {
        if (this.size == 0){
            throw new IllegalArgumentException();
        }
        T element = this.array[startIndex];
        this.startIndex = (this.startIndex + 1) % this.array.length;
        this.size--;
        return element;
    }

    public T[] toArray() {
        T[] resultArray = (T[]) new Object[this.size];
        copyAllElements(resultArray);
        return resultArray;
    }

    private void grow() {
        this.capacity *= 2;
        T[] newArray = (T[]) new Object[capacity];
        copyAllElements(newArray);
        this.startIndex = 0;
        this.endIndex = this.size;
        this.array = newArray;
    }

    private void copyAllElements(T[] resultArray) {
        int index = startIndex;
        for (int i = 0; i < this.size; i++) {
            resultArray[i] = this.array[index];
            index = (index + 1) % this.array.length;
        }
    }
}
