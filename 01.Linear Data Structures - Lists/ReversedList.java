import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Consumer;

public class ReversedList<T> implements Iterable<T> {
    private T[] elements;
    private int count;
    private int capacity;

    public ReversedList() {
        this.elements = (T[]) new Object[2];
        this.count = 0;
        this.capacity = 2;
    }

    public int count(){
        return this.count;
    }

    public int capacity(){
        return this.capacity;
    }

    public void add(T element) {
        this.elements[this.count++] = element;
        if (this.count >= capacity) {
            this.resize();
        }
    }

    private void resize() {
        T[] newArray = (T[]) new Object[this.capacity * 2];
        System.arraycopy(elements, 0, newArray, 0, capacity);
        this.capacity *= 2;
        elements = newArray;
    }

    public T get(int index) {
        if (index < 0 || index >= this.count) {
            throw new IndexOutOfBoundsException();
        }
        return this.elements[this.count - 1 - index];
    }

    public T removeAt(int index) {
        if (index < 0 || index >= this.count) {
            throw new IndexOutOfBoundsException();
        }
        int reversedIndex = this.count - 1 - index;
        T element = this.elements[reversedIndex];
        this.elements[reversedIndex] = null;
        for (int i = reversedIndex + 1; i < this.count; i++) {
            elements[i - 1] = elements[i];
        }
        elements[this.count - 1] = null;
        this.count--;
        return element;
    }

    @Override
    public Iterator<T> iterator() {
        return new ArrayIterator();
    }

    @Override
    public void forEach(Consumer<? super T> action) {
        Iterator iterator = this.iterator();
        while (iterator.hasNext()) {
            action.accept((T) iterator.next());
        }
    }

    public int getCount() {
        return this.count;
    }

    public int getCapacity() {
        return this.capacity;
    }

    class ArrayIterator implements Iterator<T> {

        int current = 0;

        @Override
        public boolean hasNext() {
            if (current < count) {
                return true;
            } else {
                return false;
            }
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return elements[current++];
        }
    }
}
