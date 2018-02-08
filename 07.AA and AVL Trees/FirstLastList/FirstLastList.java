package FirstLastList;

import java.util.ArrayList;
import java.util.List;

public class FirstLastList<T extends Comparable<T>> implements IFirstLastList<T> {
    private ArrayList<T> elements;
    private AVL<T> avlTree;

    public FirstLastList() {
        this.elements = new ArrayList<>();
        this.avlTree = new AVL<T>();
    }

    @Override
    public void add(T element) {
        this.elements.add(element);
        this.avlTree.insert(element);
    }

    @Override
    public int getCount() {
        return this.elements.size();
    }

    @Override
    public Iterable<T> first(int count) {
        if (count > this.elements.size()) {
            throw new IllegalArgumentException();
        }
        List<T> sublist = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            sublist.add(this.elements.get(i));
        }
        return sublist;
    }

    @Override
    public Iterable<T> last(int count) {
        if (count > this.elements.size()) {
            throw new IllegalArgumentException();
        }
        List<T> sublist = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            sublist.add(this.elements.get(this.elements.size() - i));
        }
        return sublist;
    }

    @Override
    public Iterable<T> min(int count) {
        if (count > this.elements.size()) {
            throw new IllegalArgumentException();
        }
        return this.avlTree.min(count);
    }

    @Override
    public Iterable<T> max(int count) {
        if (count > this.elements.size()) {
            throw new IllegalArgumentException();
        }
        return this.avlTree.max(count);
    }

    @Override
    public void clear() {
        this.elements.clear();
        this.avlTree = new AVL<T>();
    }

    @Override
    public int removeAll(T element) {
        int count = 0;
        for (int i = 0; i < this.elements.size(); i++) {
            if (this.elements.get(i).compareTo(element) == 0) {
                this.elements.remove(i);
                i--;
                count++;
            }
        }
        this.avlTree.delete(element);
        return count;
    }
}