import java.util.Iterator;

public class LinkedList<T> implements Iterable<T> {

    private Node head;
    private Node tail;
    private int size;

    public int size() {
        return this.size;
    }

    private void setSize(int size) {
        this.size = size;
    }

    public void addFirst(T item) {
        Node newNode = new Node(item);
        if (this.size == 0) {
            this.head = newNode;
            this.tail = newNode;
        } else {
            newNode.next = this.head;
            this.head = newNode;
        }
        this.size++;
    }

    public void addLast(T item) {
        Node newNode = new Node(item);
        if (this.size == 0) {
            this.head = newNode;
            this.tail = newNode;
        } else {
            this.tail.next = newNode;
            this.tail = newNode;
        }
        this.size++;
    }

    public T removeFirst() {
        if (this.size == 0) {
            throw new IllegalArgumentException();
        }
        T element = this.head.value;
        if (this.size == 1) {
            this.head = null;
            this.tail = null;
        } else {
            this.head = this.head.next;
        }
        this.size--;
        return element;
    }

    public T removeLast() {
        if (this.size == 0) {
            throw new IllegalArgumentException();
        }
        T element = this.tail.value;
        if (this.size == 1) {
            this.head = null;
            this.tail = null;
        } else {
            Node current = this.head;
            while (current.next != this.tail) {
                current = current.next;
            }
            current.next = null;
            this.tail = current;
        }
        this.size--;
        return element;
    }

    @Override
    public Iterator<T> iterator() {
        return new LinkedListIterator();
    }

    private class LinkedListIterator implements Iterator<T> {

        private Node current;

        private LinkedListIterator() {
            this.current = head;
        }

        @Override
        public boolean hasNext() {
            return this.current == tail;
        }

        @Override
        public T next() {
            T value = this.current.value;
            this.current = this.current.next;
            return value;
        }
    }

    private class Node {
        private T value;
        private Node next;

        public Node(T value) {
            this.value = value;
        }
    }
}
