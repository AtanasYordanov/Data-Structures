public class LinkedStack<E> {

    private Node<E> head;
    private int size;

    public LinkedStack() {
        this.size = 0;
    }

    public int size() {
        return this.size;
    }

    public void push(E element) {
        if (this.head == null) {
            this.head = new Node<>(element);
        } else {
            Node<E> node = new Node<>(element);
            node.next = this.head;
            this.head = node;
        }
        this.size += 1;
    }

    public E pop() {
        if (this.head == null) {
            throw new IllegalArgumentException("Stack is empty");
        } else {
            E returnElement = this.head.value;
            this.head = this.head.next;
            this.size -= 1;
            return returnElement;
        }
    }

    public E[] toArray() {
        E[] array = (E[]) new Object[this.size];
        Node<E> currentElement = this.head;
        for (int i = 0; i < this.size; i++) {
            array[i] = currentElement.value;
            currentElement = currentElement.next;
        }
        return array;
    }

    private class Node<E> {

        private E value;
        private Node<E> next;

        public Node(E value) {
            this.value = value;
        }
    }
}