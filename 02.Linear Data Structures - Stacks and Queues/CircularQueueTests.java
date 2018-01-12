import org.junit.Assert;
import org.junit.Test;

public class CircularQueueTests {

    @Test
    public void enqueue_emptyQueue_shouldAddElement() {
        CircularQueue<Integer> queue = new CircularQueue<>();

        queue.enqueue(5);

        int expectedSize = 1;
        Assert.assertEquals(expectedSize, queue.size());
    }

    @Test
    public void enqueueDeque_shouldWorkCorrectly() {
        CircularQueue<String> queue = new CircularQueue<>();
        String element = "some value";

        queue.enqueue(element);
        String elementFromQueue = queue.dequeue();

        int expectedSize = 0;
        Assert.assertEquals(expectedSize, queue.size());
        Assert.assertEquals(element, elementFromQueue);
    }

    @Test(expected = IllegalArgumentException.class)
    public void dequeue_emptyQueue_throwsException() {
        CircularQueue<Integer> queue = new CircularQueue<>();

        queue.dequeue();
    }

    @Test
    public void enqueueDequeue100Elements_shouldWorkCorrectly() {
        CircularQueue<Integer> queue = new CircularQueue<>();
        int numberOfElements = 1000;

        for (int i = 0; i < numberOfElements; i++) {
            queue.enqueue(i);
        }

        for (int i = 0; i < numberOfElements; i++) {
            int expectedSize = numberOfElements - i;

            Assert.assertEquals(expectedSize, queue.size());
            int element = queue.dequeue();
            Assert.assertEquals(i, element);

            expectedSize = numberOfElements - i - 1;
            Assert.assertEquals(expectedSize, queue.size());
        }
    }

    @Test
    public void circularQueue_enqueueDequeueManyChunks_shouldWorkCorrectly() {
        CircularQueue<Integer> queue = new CircularQueue<>();
        int chunks = 100;

        int value = 1;
        for (int i = 0; i < chunks; i++) {
            Assert.assertEquals(0, queue.size());
            int chunkSize = i + 1;
            for (int counter = 0; counter < chunkSize; counter++) {
                Assert.assertEquals(value - 1, queue.size());
                queue.enqueue(value);
                Assert.assertEquals(value, queue.size());
                value++;
            }

            for (int counter = 0; counter < chunkSize; counter++) {
                value--;
                Assert.assertEquals(value, queue.size());
                queue.dequeue();
                Assert.assertEquals(value - 1, queue.size());
            }

            Assert.assertEquals(0, queue.size());
        }
    }

    @Test
    public void enqueue500Elements_toArray_shouldWorkCorrectly() {
        Object[] array = new Object[500];
        for (int i = 0; i < 500; i++) {
            array[i] = i;
        }

        CircularQueue<Object> queue = new CircularQueue<>();

        for (int i = 0; i < array.length; i++) {
            queue.enqueue(array[i]);
        }

        Object[] arrayFromQueue = queue.toArray();

        Assert.assertArrayEquals(array, arrayFromQueue);
    }

    @Test
    public void initialCapacity1_enqueueDequeue20Elements_shouldWorkCorrectly() {
        int elementsCount = 20;
        int initialCapacity = 1;

        CircularQueue<Integer> queue = new CircularQueue<>(initialCapacity);
        for (int i = 0; i < elementsCount; i++) {
            queue.enqueue(i);
        }

        Assert.assertEquals(elementsCount, queue.size());
        for (int i = 0; i < elementsCount; i++) {
            int elementFromQueue = queue.dequeue();
            Assert.assertEquals(i, elementFromQueue);
        }

        Assert.assertEquals(0, queue.size());
    }

}
