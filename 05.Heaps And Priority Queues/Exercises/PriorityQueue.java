package Exercises;

import java.util.ArrayList;
import java.util.List;

public class PriorityQueue<T extends Comparable<T>> {

    private List<T> heap;

    public PriorityQueue() {
        this.heap = new ArrayList<>();
    }

    public int size() {
        return this.heap.size();
    }

    public void enqueue(T item) {
        this.heap.add(item);
        this.heapifyUp(this.heap.size() - 1);
    }

    public T peek() {
        if (this.size() <= 0) {
            throw new IllegalArgumentException("Queue is empty!");
        }
        return this.heap.get(0);
    }

    public T dequeue() {
        if (this.size() <= 0) {
            throw new IllegalArgumentException("Queue is empty!");
        }
        T item = this.heap.get(0);
        this.swap(0, this.heap.size() - 1);
        this.heap.remove(this.heap.size() - 1);
        this.heapifyDown(0);
        return item;
    }

    public void decreaseKey(T item) {
        int index = this.heap.indexOf(item);
        heapifyUp(index);
    }

    private void heapifyUp(int index) {
        while (index > 0 && isLess(index, parent(index))) {
            this.swap(index, parent(index));
            index = parent(index);
        }
    }
    private void heapifyDown(int index) {
        while (index < this.heap.size() / 2) {
            int child = left(index);
            if (hasChild(child + 1) && isLess(child + 1, child)) {
                child = child + 1;
            }
            if (isLess(index, child)) {
                break;
            }
            this.swap(index, child);
            index = child;
        }
    }

    public boolean contains(T element){
        for (int i = 0; i < this.size(); i++) {
            T current = this.heap.get(i);
            if (element.equals(current)){
                return true;
            }
        }
        return false;
    }

    public T getElement(T element){
        for (int i = 0; i < this.size(); i++) {
            T current = this.heap.get(i);
            if (element.compareTo(current) == 0){
                return current;
            }
        }
        return null;
    }

    private boolean hasChild(int child) {
        return child < this.heap.size();
    }

    private static int parent(int index) {
        return (index - 1) / 2;
    }

    private static int left(int index) {
        return 2 * index + 1;
    }

    private boolean isLess(int a, int b) {
        return this.heap.get(a).compareTo(this.heap.get(b)) < 0;
    }

    private void swap(int a, int b) {
        T temp = this.heap.get(a);
        this.heap.set(a, this.heap.get(b));
        this.heap.set(b, temp);
    }
}
