import java.util.ArrayList;
import java.util.List;

public class BinaryHeap<T extends Comparable<T>> {

    private List<T> heap;

    public BinaryHeap() {
        this.heap = new ArrayList<>();
    }

    public int size() {
        return this.heap.size();
    }

    public void insert(T element) {
        this.heap.add(element);
        this.heapifyUp(this.heap.size() - 1);
    }

    private void heapifyUp(int index) {
        T parent = this.heap.get((index - 1) / 2);
        while (this.heap.get(index).compareTo(parent) > 0) {
            T temp = this.heap.get(index);
            this.heap.set(index, parent);
            this.heap.set((index - 1) / 2, temp);
            index = (index - 1) / 2;
            parent = this.heap.get((index - 1) / 2);
        }
    }

    public T peek() {
        if (this.heap.size() <= 0) {
            throw new IllegalArgumentException();
        }
        return this.heap.get(0);
    }

    public T pull() {
        if (this.heap.size() <= 0) {
            throw new IllegalArgumentException();
        }
        T element = this.heap.get(0);
        this.heap.set(0, this.heap.get(this.heap.size() - 1));
        this.heap.set(this.heap.size() - 1, element);
        this.heap.remove(this.heap.size() - 1);
        this.heapifyDown(0);
        return element;
    }

    private void heapifyDown(int index) {
        while (index < this.size() / 2) {
            int childIndex = 2 * index + 1;
            if (childIndex + 1 < this.size()
                    && this.heap.get(childIndex).compareTo(this.heap.get(childIndex + 1)) < 0){
                childIndex++;
            }
            if (this.heap.get(childIndex).compareTo(this.heap.get(index)) < 0){
                break;
            }
            T temp = this.heap.get(index);
            this.heap.set(index , this.heap.get(childIndex));
            this.heap.set(childIndex, temp);
            index = childIndex;
        }
    }
}
