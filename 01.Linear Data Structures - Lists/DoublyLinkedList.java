import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Consumer;

public class DoublyLinkedList<E> implements Iterable<E> {

    private int size;
    private Node<E> head;
    private Node<E> tail;

    public int size() {
        return this.size;
    }

    public void addFirst(E element) {
        if (this.head == null) {
            this.head = this.tail = new Node<>(element);
        } else {
            Node<E> newNode = new Node<>(element);
            newNode.next = this.head;
            this.head.prev = newNode;
            this.head = newNode;
        }
        this.size += 1;
    }

    public void addLast(E element) {
        if (this.head == null) {
            this.head = this.tail = new Node<>(element);
        } else {
            Node<E> newNode = new Node<>(element);
            newNode.prev = this.tail;
            this.tail.next = newNode;
            this.tail = newNode;
        }
        this.size += 1;
    }

    public E removeFirst() {
        if (this.size <= 0) {
            throw new IllegalArgumentException("Deque is empty");
        } else {
            E element = this.head.value;
            this.head = this.head.next;
            if (this.head != null) {
                this.head.prev = null;
            } else {
                this.tail = null;
            }
            this.size -= 1;
            return element;
        }
    }

    public E removeLast() {
        if (this.size <= 0) {
            throw new IllegalArgumentException("Deque is empty");
        } else {
            E element = this.tail.value;
            this.tail = this.tail.prev;
            if (this.tail != null) {
                this.tail.next = null;
            } else {
                this.head = null;
            }
            this.size -= 1;
            return element;
        }
    }

    public E[] toArray() {
        E[] array = (E[]) new Object[this.size];
        Node<E> currentNode = this.head;
        for (int i = 0; i < this.size; i++) {
            array[i] = currentNode.value;
            currentNode = currentNode.next;
        }
        return array;
    }

    @Override
    public Iterator<E> iterator() {
        return new ListIterator();
    }

    @Override
    public void forEach(Consumer<? super E> action) {
        Iterator iterator = this.iterator();
        while (iterator.hasNext()) {
            action.accept((E) iterator.next());
        }
    }

    private class Node<E> {

        private E value;
        private Node<E> next;
        private Node<E> prev;

        public Node(E value) {
            this.value = value;
        }
    }

    private class ListIterator implements Iterator<E> {

        Node<E> currentNode = head;

        @Override
        public boolean hasNext() {
            if (currentNode != null) {
                return true;
            } else {
                return false;
            }
        }

        @Override
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            E element = currentNode.value;
            currentNode = currentNode.next;
            return element;
        }
    }
}
