package Exercises;

import org.junit.Assert;
import org.junit.Test;

public class PriorityQueueTests {

    @Test
    public void decreaseKey_SingleElement() {
         PriorityQueue<TestNode<Integer>> queue = new PriorityQueue<>();

        TestNode<Integer> testNode = new TestNode<>(1);

        queue.enqueue(testNode);
        queue.decreaseKey(testNode);

        Assert.assertEquals(1, queue.size());
        Assert.assertEquals(Integer.valueOf(1), queue.dequeue().value);
    }

    @Test
    public void decreaseKey_TwoElements() {
         PriorityQueue<TestNode<Integer>> queue = new PriorityQueue<>();

        TestNode<Integer> testNode1 = new TestNode<>(2);

        TestNode<Integer> testNode2 = new TestNode<>(3);

        queue.enqueue(testNode1);
        queue.enqueue(testNode2);

        testNode2.value = 1;
        queue.decreaseKey(testNode2);

        Assert.assertEquals(2, queue.size());
        Assert.assertEquals(Integer.valueOf(1), queue.dequeue().value);
    }

    @Test
    public void decreaseKey_MultipleElements() {
         PriorityQueue<TestNode<Integer>> queue = new PriorityQueue<>();

        TestNode<Integer> testNode1 = new TestNode<>(6);
        TestNode<Integer> testNode2 = new TestNode<>(3);
        TestNode<Integer> testNode3 = new TestNode<>(4);
        TestNode<Integer> testNode4 = new TestNode<>(2);
        TestNode<Integer> testNode5 = new TestNode<>(8);

        queue.enqueue(testNode1);
        queue.enqueue(testNode2);
        queue.enqueue(testNode3);
        queue.enqueue(testNode4);
        queue.enqueue(testNode5);

        testNode5.value = 1;
        queue.decreaseKey(testNode5);

        Assert.assertEquals(Integer.valueOf(1), queue.dequeue().value);
        Assert.assertEquals(Integer.valueOf(2), queue.dequeue().value);

        testNode1.value = 1;
        queue.decreaseKey(testNode1);
        Assert.assertEquals(Integer.valueOf(1), queue.dequeue().value);
    }

    class TestNode<T extends Comparable<T>> implements Comparable<TestNode<T>> {
        private T value;

        public TestNode(T value) {
            this.value = value;
        }

        @Override
        public int compareTo(TestNode<T> other) {
            return this.value.compareTo(other.value);
        }
    }
}