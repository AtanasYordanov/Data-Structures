public class LinkedQueue<E> {

    private int size;
    private QueueNode<E> head;
    private QueueNode<E> tail;

    public int size() {
        return this.size;
    }

    public void enqueue(E element) {
        if (this.head == null){
            this.head = new QueueNode<>();
            this.head.value = element;
            this.tail = this.head;
        }else{
            QueueNode<E> newTail = new QueueNode<>();
            newTail.value = element;
            this.tail.next = newTail;
            this.tail = newTail;
        }
        this.size += 1;
    }

    public E dequeue() {
        if (this.head == null){
            this.tail = null;
            throw new IllegalArgumentException("Queue is empty");
        }
        E value = this.head.value;
        this.head = this.head.next;
        this.size -= 1;
        return value;
    }

    public E[] toArray() {
        QueueNode<E> currentNode = head;
        E[] array = (E[]) new Object[this.size];
        for (int i = 0; i < this.size; i++) {
            array[i] = currentNode.value;
            currentNode = currentNode.next;
        }
        return array;
    }

    private class QueueNode<E> {
        private E value;
        private QueueNode<E> next;
        private QueueNode<E> prev;
    }
}