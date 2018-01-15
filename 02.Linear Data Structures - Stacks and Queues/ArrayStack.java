public class ArrayStack<T> {

    private static final int INITIAL_CAPACITY = 16;

    private T[] elements;
    private int size;

    public ArrayStack() {
        this.elements = (T[]) new Object[INITIAL_CAPACITY];
        this.size = 0;
    }

    public ArrayStack(int capacity) {
        this.elements = (T[]) new Object[capacity];
        this.size = 0;
    }

    public int size() {
        return this.size;
    }

    public void push(T element) {
        this.elements[this.size] = element;
        this.size += 1;
        if (this.elements.length <= this.size) {
            this.grow();
        }
    }

    public T pop() {
        if (this.size == 0) {
            throw new IllegalArgumentException("Stack is empty");
        }
        this.size -= 1;
        T element = this.elements[this.size];
        this.elements[this.size] = null;
        return element;
    }

    public T[] toArray() {
        T[] array = (T[]) new Object[this.size];
        for (int i = 0; i < this.size; i++) {
            array[i] = this.elements[this.size - 1 - i];
        }
        return array;
    }

    private void grow() {
        T[] newArray = (T[]) new Object[this.size * 2];
        for (int i = 0; i < this.size; i++) {
            newArray[i] = this.elements[i];
        }
        this.elements = newArray;
    }
}