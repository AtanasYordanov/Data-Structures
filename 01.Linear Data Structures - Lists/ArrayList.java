import java.util.Arrays;

public class ArrayList<T> {

    private T[] arr;
    private int size;
    private int capacity;

    public ArrayList(int capacity) {
        this.capacity = capacity;
        this.size = 0;
        this.arr = (T[]) new Object[capacity];
    }

    public ArrayList() {
        this(4);
    }

    public int getCount() {
        return this.size;
    }

    public T get(int index) {
        if (index < 0 || index >= this.size) {
            throw new IllegalArgumentException();
        }
        return this.arr[index];
    }

    public void add(T element) {
        if (this.size >= this.capacity) {
            this.grow();
        }
        this.arr[size++] = element;
    }

    private void grow() {
        this.capacity *= 2;
        T[] newArr = Arrays.copyOf(this.arr, this.capacity);
        this.arr = newArr;
    }

    private void shrink() {
        this.capacity /= 2;
        T[] newArr = Arrays.copyOf(this.arr, this.capacity);
        this.arr = newArr;
    }

    public T removeAt(int index) {
        if (index < 0 || index >= this.size) {
            throw new IllegalArgumentException();
        }
        this.shiftLeft(index);
        if (this.size-- < this.capacity / 3) {
            this.shrink();
        }
        return this.arr[index];
    }

    private void shiftLeft(int index) {
        for (int i = index; i < this.size - 1; i++)
        {
            this.arr[i] = this.arr[i + 1];
        }
        this.arr[this.size - 1] = null;
    }

    public void set(int i, T item) {
        if (i < 0 || i >= this.size) {
            throw new IllegalArgumentException();
        }
        this.arr[i] = item;
    }
}
